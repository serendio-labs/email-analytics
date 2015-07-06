package serendio.application;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.james.mime4j.MimeException;

import serendio.dataset.mail.mbox.MessageToEmailDoc;
import serendio.dataset.mail.mbox.MboxToNeo4j;
import serendio.dataset.mail.text.EmailDocReader;
import serendio.graphdb.neo4j.ConstantVariables;
import serendio.graphdb.neo4j.DBConnection;
import serendio.graphdb.neo4j.*;;


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
		setINPUT_PATH("/home/nishant/Serendio/sample mail dataset/SampleEmailDataSet.mbox");
		//setMBOX_PATH("C:/Users/vartikatewari/Documents/sample mail dataset/SampleEmailDataSet.mbox");
		//ConstantVariables.setDbPath("C:/Users/vartikatewari/Documents/Neo4j/arch2.graphdb");
		ConstantVariables.setDbPath("/home/nishant/Software/neo4j-community-2.2.1/data/graph.db");
		
		MboxToNeo4j mbox = new MboxToNeo4j();
		mbox.mBox_Iterator(getINPUT_PATH());
		//Neo4jTraversalQuery a =new Neo4jTraversalQuery();
		//String result;
		//result=a.UserMailReceivedCount("vartika.tewari@gmail.com");
		//result=a.AvgMailSent();
		//result=a.WhOToWhomCount("troy@narrativewave.com");
		//System.out.println(result);
	}
}
