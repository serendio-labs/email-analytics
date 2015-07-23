package serendio.application;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.james.mime4j.MimeException;

import com.diskoverorta.tamanager.TextManager;
import com.diskoverorta.vo.TAConfig;

import serendio.dataset.mail.eml.EmlToNeo4j;
import serendio.dataset.mail.mbox.MessageToEmailDoc;
import serendio.dataset.mail.mbox.MboxToNeo4j;
import serendio.dataset.mail.pst.PstToNeo4j;
import serendio.dataset.mail.text.EmailDocReader;
import serendio.graphdb.neo4j.*;;


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
        /*

	    //String example = "Sachin Tendulkar was the winner of Worldcup event in South AFrica during the year 2014";
	    //String example= "The requirements.txt file specifies the software packages along with their versions to be installed. Execute the below command to install all python related dependencies for the Sentiment and Topics.";
	    String example = "West Position Attached is the Delta position for 1/18, 1/31, 6/20, 7/16, 9/24 Let me know if you have any questions. Heather";
	    TextManager obj = new TextManager();
	    TAConfig config = new TAConfig();


	    config.analysisConfig.put("Topic", "TRUE");

	    System.out.println(obj.tagUniqueTextAnalyticsComponentsINJSON(example, config));
         */
		
		
		 
		//setINPUT_PATH("C:/Users/vartikatewari/Desktop/enron_mail_20110402/maildir");
		setINPUT_PATH("/home/serendio/Desktop/enron_mail_20110402/maildir/harris-s/inbox/");
		//setINPUT_PATH("C:/Users/vartikatewari/Documents/sample mail dataset/SampleEmailDataSet.mbox");
		//ConstantVariables.setDbPath("/home/serendio/Desktop/Enron.db");
		//ConstantVariables.setDbPath("/home/nishant/Software/neo4j-community-2.2.1/data/graph.db");
		ConstantVariables.setDbPath("/home/serendio/Desktop/Test.db");
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
