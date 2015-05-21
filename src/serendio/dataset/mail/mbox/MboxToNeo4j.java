package serendio.dataset.mail.mbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.james.mime4j.MimeException;

import serendio.dataset.process.EmailDoc;
import serendio.graphdb.neo4j.Neo4jGraph;

public class MboxToNeo4j {

	private final static CharsetEncoder ENCODER = Charset.forName("UTF-8").newEncoder();
	
	
	public void mBox_Iterator(String inputPath) throws FileNotFoundException, IOException, MimeException
	{
		File mbox = new File(inputPath);
		MboxToEmailDoc emailObjectConverter = new MboxToEmailDoc();
		EmailDoc emailObject = new EmailDoc();
	    for(CharBufferWrapper message : MboxIterator.fromFile(mbox).charset(ENCODER.charset()).build())
	    {
	    	emailObject = emailObjectConverter.messageToemailDoc(message.asInputStream(ENCODER.charset()));
	    	pushToNeo4j(emailObject);
	    }
	}
	public void pushToNeo4j(EmailDoc emailObject)
	{
		processUserNodes(emailObject);
		processEmailNodes(emailObject);
	}
	
	public void processLinks(EmailDoc emailObject)
	{
		
	}
	public void processEmailNodes(EmailDoc emailObject)
	{
		Neo4jGraph graph = new Neo4jGraph();
		graph.init();
		graph.createEmailNode(emailObject.getMessage_ID(),emailObject.getDate(),emailObject.getSubject(), emailObject.getContent());
		graph.closeDatabase();
	}
	public void processUserNodes(EmailDoc emailObject)
	{
		Neo4jGraph graph = new Neo4jGraph();
		graph.init();
		graph.createUserNode(emailObject.getName(), emailObject.getFrom());
		
		if(emailObject.getTo() != null)
		for(String toAddress: emailObject.getTo().toArray(new String[emailObject.getTo().size()]))
		{
			graph.createUserNode(null, toAddress);
		}
		
		if(emailObject.getCc() != null)
		for(String toAddress: emailObject.getCc().toArray(new String[emailObject.getCc().size()]))
		{
			graph.createUserNode(null, toAddress);
		}
		
		if(emailObject.getBcc() != null)
		for(String toAddress: emailObject.getBcc().toArray(new String[emailObject.getBcc().size()]))
		{
			graph.createUserNode(null, toAddress);
		}
		
		graph.closeDatabase();
	}
}
