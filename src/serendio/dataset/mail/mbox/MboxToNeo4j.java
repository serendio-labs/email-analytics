package serendio.dataset.mail.mbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.james.mime4j.MimeException;

import serendio.dataset.domain.EmailDoc;
import serendio.graphdb.neo4j.ConstantVariables;
import serendio.graphdb.neo4j.EmailDocToNeo4j;
import serendio.graphdb.neo4j.Neo4jGraphCreation;
import serendio.Utils.*;


public class MboxToNeo4j {

	private final static CharsetEncoder ENCODER = Charset.forName("UTF-8").newEncoder();
	/*public Neo4jGraphCreation graph;*/
	
	
	public MboxToNeo4j() 
	{
	 System.out.println("MBOX MAIL INGEST STARTED:");
	 System.out.println("=========================");
	 
	}

	public void ingestMbox(String inputPath) throws FileNotFoundException, IOException, MimeException
	{
		File mbox = new File(inputPath);
		MessageToEmailDoc emailObjectConverter = new MessageToEmailDoc();
		EmailDoc emailObject = new EmailDoc();
		PrintWriter out = new PrintWriter("E:/output.log");
		int mailCounter = 0;
	    EmailDocToNeo4j neo4jInterface = new EmailDocToNeo4j();
		for(CharBufferWrapper message : MboxIterator.fromFile(mbox).charset(ENCODER.charset()).build())
	    {
	    	mailCounter++;
	    	System.out.println("Processing Email: "+mailCounter);
	    	emailObject = emailObjectConverter.messageToemailDoc(message.asInputStream(ENCODER.charset()));
	    	//Converting to Json for use in elasticsearch
	    	
			String json=Utils.ToJson(emailObject);
			out.println(json);
	    	//emailObject.printMailObject();
	    	neo4jInterface.pushToNeo4j(emailObject);
	    }
		out.close();
	}
}
