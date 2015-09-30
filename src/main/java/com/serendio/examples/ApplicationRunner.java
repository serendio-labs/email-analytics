package com.serendio.examples;

import com.serendio.configuration.AppConfigurations;
import com.serendio.graphdb.neo4jEmbd.Neo4jTraversalQuery;
import com.serendio.runner.DatasetIngestionRunner;
import org.apache.james.mime4j.MimeException;
import javax.mail.MessagingException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ApplicationRunner
{

	public static void DataIngestion(AppConfigurations conf) throws MessagingException, IOException,InterruptedException,MimeException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String option = null;
		System.out.println("Email Ingestion Module:-");
		System.out.println("------------------------");
		conf = setInputDatasetPath(conf);
		conf = setDatasetType(conf);
		System.out.println();
		System.out.println("Enable Topic Extraction and Sentiment Analysis? If yes than make sure that DisKoverer is running. (Enter 'y' for yes)");
		option = br.readLine();
		option = option.toLowerCase();
		if(option.equals("y"))
		{
			conf.setTOPIC_EXTRACTION(true);
			conf.setSENTIMENT_ANALYSIS(true);
		}
		DatasetIngestionRunner runner = new DatasetIngestionRunner();
		runner.run(conf);
		System.out.println();
	}

	public static void runQuery() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String companyDomain = null;
		String option = null;
		String email = null;
		boolean exitMenu = false;
		String query = null;
		String [] resultArray = null;
		Neo4jTraversalQuery a = new Neo4jTraversalQuery();

		System.out.println("Email Analytics Module:-");
		System.out.println("-------------------------");
		System.out.println();
		System.out.println("Enter company email domain(i.e. for xyz@company.com, enter company.com):");
		companyDomain = br.readLine();
		System.out.println();
		System.out.println("General Statistics:-");
		System.out.println("--------------------");
		System.out.println("Total Emails:" + a.TotalEmailProcessed());
		System.out.println("Employees count by Unique Email Addresses:" + a.CompanyUsersCount(companyDomain));
		System.out.println("Avg Emails sent by Employees:" + a.AvgMailSentByEmployees(companyDomain));
		System.out.println("Avg Emails received by Employees:" + a.AvgMailReceivedByEmployees(companyDomain));
		System.out.println();

		do
		{
			System.out.println("Analytics Option Menu:-");
			System.out.println("1 : Enter Custom Cypher Query");
			System.out.println("2 : List Company Employees");
			System.out.println("3 : List Everyone");
			System.out.println("4 : Mail Sent Count for Selected Employee");
			System.out.println("5 : Mail Received Count for Selected Employee");
			System.out.println("6 : Sent Table for Selected Email");
			System.out.println("99 : Exit");
			System.out.println("Enter Option No:");
			option = br.readLine();
			System.out.println();

			if (Integer.parseInt(option) == 1)
			{
				  System.out.println("Enter the cypher query:");
					query = br.readLine();
					System.out.println(a.ExecuteQuery(query));
			}
			else if (Integer.parseInt(option) == 2)
			{
				resultArray = a.DisplayCompanyUsers(companyDomain);
				for (String result_it:resultArray)
					System.out.println(result_it);
			}
			else if (Integer.parseInt(option) == 3)
			{
				resultArray = a.DisplayAllUsers();
				for (String result_it:resultArray)
					System.out.println(result_it);
			}
			else if (Integer.parseInt(option) == 4)
			{
				System.out.println("Enter Email Address:");
				email = br.readLine();
				System.out.println(a.UserMailSentCount(email));
			}
			else if (Integer.parseInt(option) == 5)
			{
				System.out.println("Enter Email Address:");
				email = br.readLine();
				System.out.println(a.UserMailReceivedCount(email));
			}
			else if (Integer.parseInt(option) == 6)
			{
				System.out.println("Mail Sent Table:-");
				System.out.println("Enter Email Address:");
				email = br.readLine();
				System.out.println(a.WhoToWhomCount(email));
			}
			else if (Integer.parseInt(option) == 99)
			{
				exitMenu = true;
			}
			else
			{
				System.out.println("Invalid Selection!! Please try again.");
				exitMenu = false;
			}
			System.out.println();
		}while(!exitMenu);

	}
	public static AppConfigurations setInputDatasetPath(AppConfigurations conf) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = null;
		System.out.println("Enter Absolute path of input email dataset:");
		path = br.readLine();
		conf.setINPUT_PATH(path);
		return conf;
	}
	public static AppConfigurations setDatasetType(AppConfigurations conf)  throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String type = null;
		System.out.println("Select Email Dataset Type:");
		boolean exitMenu = true;
		do
		{
			System.out.println("1 : EML");
			System.out.println("2 : MBOX");
			System.out.println("3 : PST");
			System.out.println("Enter Option No:");
			type = br.readLine();
			if (Integer.parseInt(type) == 1)
			{
					conf.setDatasetType(AppConfigurations.EmailDatasetType.EML);
					exitMenu = true;
			}
			else if (Integer.parseInt(type) == 2)
			{
				conf.setDatasetType(AppConfigurations.EmailDatasetType.MBOX);
				exitMenu = true;
			}
			else if (Integer.parseInt(type) == 3)
			{
				conf.setDatasetType(AppConfigurations.EmailDatasetType.PST);
				exitMenu = true;
			}
			else
					exitMenu = false;
		}
		while(!exitMenu);
		return conf;
	}

	public static void main(String[] args) throws MessagingException, IOException, InterruptedException, MimeException
	{
		AppConfigurations conf = new AppConfigurations();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String dbPath = null;
		boolean exitMenu = false;
		String input = null;

		System.out.println("Application Runner Started:");
		System.out.println("Please provide db path:");
		input = br.readLine();
		conf.setNeo4jDbPath(input);
		do
		{
			System.out.println("Menu:");
			System.out.println("1 : EmailIngestion");
			System.out.println("2 : EmailAnalytics");
			System.out.println("3 : Exit");
			System.out.println("Enter Option NO:");
			input = br.readLine();
			System.out.println();
			if (Integer.parseInt(input) == 1)
			{
				DataIngestion(conf);
			}
			else if (Integer.parseInt(input) == 2)
			{
				runQuery();
			}
			else if (Integer.parseInt(input) == 3)
			{
				exitMenu = true;
			}
			System.out.println();
		}while(!exitMenu);
		System.out.println("Exiting The Program.");
	}
}
