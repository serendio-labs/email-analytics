package com.serendio.application;

import org.apache.james.mime4j.MimeException;
import com.serendio.dataset.mail.eml.EmlToNeo4j;
import com.serendio.graphdb.neo4jEmbd.ConstantVariables;

import javax.mail.MessagingException;
import java.io.IOException;



public class CopyOfApplicationRunner 
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
		//setINPUT_PATH("C:/Users/vartikatewari/Desktop/enron_mail_20110402/maildir");
		setINPUT_PATH("/home/serendio/Desktop/enron_mail_20110402/maildir/harris-s/inbox/");
		//setINPUT_PATH("C:/Users/vartikatewari/Documents/sample mail dataset/SampleEmailDataSet.mbox");
		//ConstantVariables.setDbPath("/home/serendio/Desktop/Enron.db");
		//ConstantVariables.setDbPath("/home/nishant/Software/neo4jEmbd-community-2.2.1/data/graph.db");
		ConstantVariables.setDbPath("/home/serendio/Desktop/Test2.db");
		//MboxToNeo4j mbox = new MboxToNeo4j();
		//mbox.ingestMbox(getINPUT_PATH());
		
		//PstToNeo4j pst = new PstToNeo4j();
		//pst.ingestPst(getINPUT_PATH());
		
		EmlToNeo4j eml = new EmlToNeo4j();
		eml.ingestEml(getINPUT_PATH());
		//EmailDocReader eml = new EmailDocReader(getINPUT_PATH()); 
		//eml.processDoc();
		//Neo4jTraversalQuery a =new Neo4jTraversalQuery();
		//String result;
		//result=a.UserMailReceivedCount("vartika.tewari@gmail.com");
		//result=a.AvgMailSent();
		//result=a.WhOToWhomCount("troy@narrativewave.com");
		//System.out.println(result);
		 
		 
	}
}
