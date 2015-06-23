package serendio.application;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.james.mime4j.MimeException;

import serendio.dataset.mail.mbox.MessageToEmailDoc;
import serendio.dataset.mail.mbox.MboxToNeo4j;
import serendio.dataset.mail.text.EmailDocReader;
import serendio.graphdb.neo4j.ConstantVariables;
import serendio.graphdb.neo4j.DBConnection;
import serendio.graphdb.neo4j.Neo4jGraphCreation;

//import com.aspose.email.*;

public class ApplicationRunner 
{
	public static String MBOX_PATH;
	

	public static String getMBOX_PATH() {
		return MBOX_PATH;
	}

	public static void setMBOX_PATH(String mBOX_PATH) {
		MBOX_PATH = mBOX_PATH;
	}

	public static void main(String[] args) throws MessagingException, IOException, InterruptedException, MimeException 
	{
		//setMBOX_PATH("/home/nishant/Serendio/sample mail dataset/SampleEmailDataSet.mbox");
		setMBOX_PATH("C:/Users/vartikatewari/Documents/sample mail dataset/SampleEmailDataSet.mbox");
		ConstantVariables.setDbPath("C:/Users/vartikatewari/Documents/Neo4j/arch2.graphdb");
		//ConstantVariables.setDbPath("/home/nishant/Software/neo4j-community-2.2.1/data/graph.db");
		
		MboxToNeo4j mbox = new MboxToNeo4j();
		mbox.mBox_Iterator(getMBOX_PATH());
	}
}
