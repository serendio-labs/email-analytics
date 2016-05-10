package com.serendio.examples;

import com.serendio.configuration.AppConfigurations;
import com.serendio.runner.DatasetIngestionRunner;
import org.apache.james.mime4j.MimeException;
import javax.mail.MessagingException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ApplicationRunner {

	public static void DataIngestion(AppConfigurations conf)
			throws MessagingException, IOException, InterruptedException,
			MimeException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String option = null;
		System.out.println("Email Ingestion Module:-");
		System.out.println("------------------------");
		conf = setInputDatasetPath(conf);
		conf = setDatasetType(conf);
		System.out.println();
		System.out
				.println("Enable Topic Extraction and Sentiment Analysis? If yes than make sure that DisKoverer is running. (Enter 'y' for yes)");
		option = br.readLine();
		option = option.toLowerCase();
		if (option.equals("y")) {
			AppConfigurations.setTOPIC_EXTRACTION(true);
			AppConfigurations.setSENTIMENT_ANALYSIS(true);
		}
		DatasetIngestionRunner runner = new DatasetIngestionRunner();
		runner.run(conf);
		System.out.println();
	}

	public static AppConfigurations setInputDatasetPath(AppConfigurations conf)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = null;
		System.out.println("Enter Absolute path of input email dataset:");
		path = br.readLine();
		conf.setINPUT_PATH(path);
		return conf;
	}

	public static AppConfigurations setDatasetType(AppConfigurations conf)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String type = null;
		System.out.println("Select Email Dataset Type:");
		boolean exitMenu = true;
		do {
			System.out.println("1 : EML");
			System.out.println("2 : MBOX");
			System.out.println("3 : PST");
			System.out.println("Enter Option No:");
			type = br.readLine();
			if (Integer.parseInt(type) == 1) {
				conf.setDatasetType(AppConfigurations.EmailDatasetType.EML);
				exitMenu = true;
			} else if (Integer.parseInt(type) == 2) {
				conf.setDatasetType(AppConfigurations.EmailDatasetType.MBOX);
				exitMenu = true;
			} else if (Integer.parseInt(type) == 3) {
				conf.setDatasetType(AppConfigurations.EmailDatasetType.PST);
				exitMenu = true;
			} else
				exitMenu = false;
		} while (!exitMenu);
		return conf;
	}

	public static void main(String[] args) throws MessagingException,
			IOException, InterruptedException, MimeException {
		AppConfigurations conf = new AppConfigurations();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean exitMenu = false;
		String input = null;
		System.out.println("Application Runner Started:");
		do {
			System.out.println("Menu:");
			System.out.println("1 : EmailIngestion");
			System.out.println("2 : Exit");
			System.out.println("Enter Option NO:");
			input = br.readLine();
			System.out.println();
			if (Integer.parseInt(input) == 1) {
				DataIngestion(conf);
			} else if (Integer.parseInt(input) == 2) {
				exitMenu = true;
			}
			System.out.println();
		} while (!exitMenu);
		System.out.println("Exiting The Program.");
	}
}