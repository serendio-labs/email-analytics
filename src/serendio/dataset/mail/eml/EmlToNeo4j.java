package serendio.dataset.mail.eml;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import serendio.dataset.domain.EmailDoc;
import serendio.dataset.mail.text.EmailDocReader;
import serendio.graphdb.neo4j.EmailDocToNeo4j;


public class EmlToNeo4j {

	public EmlToNeo4j() {
		System.out.println("EML MAIL INGEST STARTED:");
		System.out.println("=========================");
	}
	
	
	public void ingestEml(String inputPath) throws MessagingException, IOException
	{
	    File[] files = new File(inputPath).listFiles();
	    EmailDocToNeo4j neo4jInterface = new EmailDocToNeo4j();
	    showFiles(files,neo4jInterface);
	    
	}

	public static void showFiles(File[] files, EmailDocToNeo4j neo4jInterface) throws MessagingException, IOException {
	   String inputPath;
	   int mailCounter = 0;
	   
		for (File file : files) {
	        if (file.isDirectory()) {
	            
	            inputPath = file.getAbsolutePath();
	            
	        	showFiles(file.listFiles(),neo4jInterface); // Calls same method again.
	        } else {
		    	mailCounter++;
		    	System.out.println("Processing Email: "+mailCounter);
	        	inputPath = file.getAbsolutePath();
	            EmailDocReader eml=new EmailDocReader(inputPath);
	            EmailDoc emailObject = new EmailDoc();
	            emailObject = eml.processDoc();
	            
	            neo4jInterface.pushToNeo4j(emailObject);
	            
	        }
	    }

	}

}
