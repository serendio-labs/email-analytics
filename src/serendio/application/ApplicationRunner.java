package serendio.application;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.james.mime4j.MimeException;

import serendio.dataset.mail.mbox.IterateOverMbox;
import serendio.dataset.mail.text.EmailDocReader;
import serendio.graphdb.neo4j.DBConnection;
import serendio.graphdb.neo4j.Neo4jGraph;

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
		Process p = Runtime.getRuntime().exec("/home/nishant/Software/neo4j-community-2.2.1/bin/neo4j stop");
		p.waitFor();
	//	DBConnection connect = new DBConnection();
	//	connect.createEmbDb("/home/nishant/Neo4jDBs/Test1.db");
		//Neo4jGraph db = new Neo4jGraph();
		//db.init("/home/nishant/Software/neo4j-community-2.2.1/data/graph.db");
	//	db.createNode();
		setMBOX_PATH("/home/nishant/Serendio/smaple mail dataset/testlist.mbox");
		
		IterateOverMbox mbox = new IterateOverMbox();
		mbox.printMbox(getMBOX_PATH());
		//db.createUserNode("Mr.X","x@y.com");
		//db.createEmailNode("Nishant", "11111", "15/12/90", "Hi", "mesahe is ckjsv!!");
		//db.createUniqueLink("x@y.com", "11111", "UP", "From");
		//db.closeDatabase();
		
		//Process p1 = Runtime.getRuntime().exec("/home/nishant/Software/neo4j-community-2.2.1/bin/neo4j start");
		//p1.waitFor();
	//	EmailDocReader docReader = new EmailDocReader("/home/nishant/Serendio/Enron Email/enron_mail_20110402/dump/allen-p/sent/1.");
	//	docReader.processDoc();
	}
}
