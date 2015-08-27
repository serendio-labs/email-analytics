package com.serendio.application;

import com.serendio.configuration.AppConfigurations;
import com.serendio.graphdb.neo4jEmbd.Neo4jTraversalQuery;
import com.serendio.runner.DatasetIngestionRunner;

import org.apache.james.mime4j.MimeException;

import javax.mail.MessagingException;

import java.io.IOException;



public class ApplicationRunner
{


	public static void main(String[] args) throws MessagingException, IOException, InterruptedException, MimeException
	{
		AppConfigurations conf = new AppConfigurations();
		conf.setNeo4jDbPath("/home/nishant/Desktop/test.db");
		conf.setINPUT_PATH("/home/nishant/Desktop/input");
		conf.setDatasetType(AppConfigurations.EmailDatasetType.EML);
		DatasetIngestionRunner runner = new DatasetIngestionRunner();
		runner.run(conf);

		Neo4jTraversalQuery a = new Neo4jTraversalQuery();
		System.out.println(a.TotalEmailProcessed());


	}
}

