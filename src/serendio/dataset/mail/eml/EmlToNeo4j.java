package serendio.dataset.mail.eml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.mail.MessagingException;

import serendio.dataset.domain.EmailDoc;
import serendio.dataset.mail.text.EmailDocReader;
import serendio.graphdb.neo4j.EmailDocToNeo4j;


public class EmlToNeo4j {

	public static long filecount = 0;
	
	public EmlToNeo4j() {
		System.out.println("EML MAIL INGEST STARTED:");
		System.out.println("=========================");
	}
	
	
	public void ingestEml(String inputPath) throws MessagingException, IOException
	{
	    File[] files = new File(inputPath).listFiles();
	    EmailDocToNeo4j neo4jInterface = new EmailDocToNeo4j();
	    showFiles(files,neo4jInterface);
	    System.out.println(filecount);
	}

	public static void showFiles(File[] files, EmailDocToNeo4j neo4jInterface) throws MessagingException, IOException {
	   String inputPath;
//	   int mailCounter = 0;
		for (File file : files) {
	        if (file.isDirectory()) {
	            inputPath = file.getAbsolutePath();
	        	showFiles(file.listFiles(),neo4jInterface); // Calls same method again.
	        } else {
	//	    	mailCounter++;
		    	filecount++;
		    	System.out.println("Processing File Count: "+filecount);
	        	inputPath = file.getAbsolutePath();
	        	System.out.println("Processing File: "+file.getAbsolutePath());
	        	if(filecount > 0)
	        	{
	        		EmailDocReader eml=new EmailDocReader(inputPath);
	        		EmailDoc emailObject = new EmailDoc();
	        		try{
	            	
	        			emailObject = eml.processDoc();
	        			neo4jInterface.pushToNeo4j(emailObject);
	        			FileWriter writer = new FileWriter("FilesProcesses.txt");
		            	BufferedWriter bufferedWriter = new BufferedWriter(writer);
		            	bufferedWriter.append("FileCount:"+filecount+"\t"+file.getAbsolutePath());
		            	bufferedWriter.newLine();
		            	bufferedWriter.close();
	        		}
	        		catch(Exception e)
	        		{
//	            	File logger = new File("logger.txt");
	            	FileWriter writer = new FileWriter("ErrorFiles.txt");
	            	BufferedWriter bufferedWriter = new BufferedWriter(writer);
	            	bufferedWriter.append(file.getAbsolutePath());
	            	bufferedWriter.newLine();
	            	bufferedWriter.close();
	        		}
	        	}
	        }
	    }

	}

}
