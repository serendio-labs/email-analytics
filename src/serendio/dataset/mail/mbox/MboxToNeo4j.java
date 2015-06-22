package serendio.dataset.mail.mbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.james.mime4j.MimeException;

import serendio.dataset.process.EmailDoc;
import serendio.graphdb.neo4j.ConstantVariables;
import serendio.graphdb.neo4j.Neo4jGraphCreation;

public class MboxToNeo4j {

	private final static CharsetEncoder ENCODER = Charset.forName("UTF-8").newEncoder();
	
	
	public void mBox_Iterator(String inputPath) throws FileNotFoundException, IOException, MimeException
	{
		File mbox = new File(inputPath);
		MessageToEmailDoc emailObjectConverter = new MessageToEmailDoc();
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
		processLinks(emailObject);
	}
	
	public void processLinks(EmailDoc emailObject)
	{
		Neo4jGraphCreation graph = new Neo4jGraphCreation();
		graph.init();
		//graph.createEmailNode(emailObject.getMessage_ID(),emailObject.getDate(),emailObject.getSubject(), emailObject.getContent());
		
		if(emailObject.getFrom() != null)
		graph.createUniqueLink(emailObject.getFrom(),emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.FORWARD.toString(),ConstantVariables.RelationType.FROM.toString());
		
			if(emailObject.getTo() != null)
			for(String toAddress: emailObject.getTo().toArray(new String[emailObject.getTo().size()]))
			{
				graph.createUniqueLink(toAddress, emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.BACKWORD.toString(),ConstantVariables.RelationType.TO.toString());
			}
			
			if(emailObject.getCc() != null)
			for(String ccAddress: emailObject.getCc().toArray(new String[emailObject.getCc().size()]))
			{
				graph.createUniqueLink(ccAddress, emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.BACKWORD.toString(),ConstantVariables.RelationType.CC.toString());
			}
			
			if(emailObject.getBcc() != null)
			for(String bccAddress: emailObject.getBcc().toArray(new String[emailObject.getBcc().size()]))
			{
				graph.createUniqueLink(bccAddress, emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.BACKWORD.toString(),ConstantVariables.RelationType.BCC.toString());
			}
			if(emailObject.getReplyTo() != null)
				for(String ReplyAddress: emailObject.getReplyTo().toArray(new String[emailObject.getReplyTo().size()]))
				{
					graph.createUniqueLink(ReplyAddress, emailObject.getReplyMessage_ID(),ConstantVariables.EdgeDirection.BACKWORD.toString(),ConstantVariables.RelationType.RESPONSE.toString());
				}
		
		
		graph.closeDatabase();
	}
	public void processEmailNodes(EmailDoc emailObject)
	{
		Neo4jGraphCreation graph = new Neo4jGraphCreation();
		graph.init();
		graph.createEmailNode(emailObject.getMessage_ID(),emailObject.getDate(), emailObject.getEpochTimeStamp(), emailObject.getSubject(), emailObject.getContent(),emailObject.getReplyTo());
		graph.closeDatabase();
	}
	
	public void processUserNodes(EmailDoc emailObject)
	{
		Neo4jGraphCreation graph = new Neo4jGraphCreation();
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
		if(emailObject.getReplyTo() != null)
			for(String ReplyAddress: emailObject.getReplyTo().toArray(new String[emailObject.getReplyTo().size()]))
			{
				graph.createUserNode(null, ReplyAddress);
			}
			
		graph.closeDatabase();
	}
}
