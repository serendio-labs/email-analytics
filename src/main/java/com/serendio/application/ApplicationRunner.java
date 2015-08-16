package com.serendio.application;

import org.apache.james.mime4j.MimeException;
import com.serendio.dataset.mail.eml.EmlToNeo4j;
import com.serendio.graphdb.neo4jEmbd.ConstantVariables;

import javax.mail.MessagingException;
import java.io.IOException;


public class ApplicationRunner
{
	public static String INPUT_PATH;
	public static String getINPUT_PATH() {
		return INPUT_PATH;
	}
	public static void setINPUT_PATH(String File_PATH) {
		INPUT_PATH = File_PATH;
	}

	public static void main(String[] args) throws MessagingException, IOException, InterruptedException, MimeException
	{
		setINPUT_PATH("/home/serendio/Desktop/enron_mail_20110402/maildir");

		ConstantVariables.setDbPath("/home/serendio/Desktop/Enron.db");

		EmlToNeo4j eml = new EmlToNeo4j();
		eml.ingestEml(getINPUT_PATH());

		 
	}
}
