package com.serendio.dataset.mail.mbox;

import org.apache.james.mime4j.MimeException;
import com.serendio.dataset.domain.EmailDoc;
import com.serendio.graphdb.neo4jEmbd.EmailDocToNeo4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;


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
		//PrintWriter out = new PrintWriter("E:/output.log");
		int mailCounter = 0;
	    	EmailDocToNeo4j neo4jInterface = new EmailDocToNeo4j();
		for(CharBufferWrapper message : MboxIterator.fromFile(mbox).charset(ENCODER.charset()).build())
	    	{
	    		mailCounter++;
	    		System.out.println("Processing Email: "+mailCounter);
	    		emailObject = emailObjectConverter.messageToemailDoc(message.asInputStream(ENCODER.charset()));
	    	/*
	    	 //Converting to Json for use in elasticsearch
	    	 
			String json=Utils.ToJson(emailObject);
			out.println(json);
			*/
	    	
	    	//emailObject.printMailObject();
	    		neo4jInterface.pushToNeo4j(emailObject);
	    	}
		neo4jInterface.closedb();
		//out.close();
	}
}
