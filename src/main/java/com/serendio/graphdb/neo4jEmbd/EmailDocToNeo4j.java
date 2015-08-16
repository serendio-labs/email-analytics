package com.serendio.graphdb.neo4jEmbd;

import com.diskoverorta.tamanager.TextManager;
import com.diskoverorta.vo.TAConfig;
import com.serendio.dataset.domain.EmailDoc;

import java.io.FileNotFoundException;

public class EmailDocToNeo4j {

	public Neo4jGraphCreation graph;

	public EmailDocToNeo4j() 
	{
		 this.graph = new Neo4jGraphCreation();
		 this.graph.init();
	}
	
	public void pushToNeo4j(EmailDoc emailObject) throws FileNotFoundException 
	{
		
		// Topic Modeling and Sentiment Extraction 
				String Content = emailObject.getContent();
				TextManager obj = new TextManager();
			    TAConfig config = new TAConfig();
			    config.analysisConfig.put("Topic", "TRUE");
			    config.analysisConfig.put("Sentiment", "TRUE");
			    //System.out.println(obj.tagUniqueTextAnalyticsComponentsINJSON(Content, config));
			    String[] topics = obj.tagTopic(Content, config);
			    String Topic = null;
			    for (String topic : topics){
                	if(Topic == null){
                		Topic = topic ;
                	}
                	else{
                		Topic += "," + topic;
                	}
			    	
                } 
				emailObject.setTopic(Topic);
			    String sentiment = obj.findSentiment(Content, config);
			    emailObject.setSentiment(sentiment);
			    
			    
		//Neo4j 	    
		        processUserNodes(emailObject);
		        processEmailNodes(emailObject);
		        processLinks(emailObject);
		        //emailObject.printTopicSentiment();
		        System.out.println("EmailDOc Pushed to Neo4j");
		  /*      
		//Make log(json) file
		        
		        String json = Utils.ToJson(emailObject);
		        PrintWriter out = null;
		        if ( f.exists() && !f.isDirectory() ) {
		            out = new PrintWriter(new FileOutputStream(new File("/home/serendio/Desktop/output.log"), true));
		            out.append(json);
		            out.close();
		        }
		        else {
		            out = new PrintWriter("/home/serendio/Desktop/output.log");
		            out.println(json);
		            out.close();
		        }
		        */
		        
	}   
	
	public void processLinks(EmailDoc emailObject)
	{
		if(emailObject.getFrom() != null)
		this.graph.createUniqueLink(emailObject.getFrom(),emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.FORWARD.toString(),ConstantVariables.RelationType.FROM.toString());
		
			if(emailObject.getTo() != null)
			for(String toAddress: emailObject.getTo().toArray(new String[emailObject.getTo().size()]))
			{
				
				this.graph.createUniqueLink(toAddress, emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.BACKWORD.toString(),ConstantVariables.RelationType.TO.toString());
			}
			
			if(emailObject.getCc() != null)
			for(String ccAddress: emailObject.getCc().toArray(new String[emailObject.getCc().size()]))
			{
				this.graph.createUniqueLink(ccAddress, emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.BACKWORD.toString(),ConstantVariables.RelationType.CC.toString());
			}
			
			if(emailObject.getBcc() != null)
			for(String bccAddress: emailObject.getBcc().toArray(new String[emailObject.getBcc().size()]))
			{
				this.graph.createUniqueLink(bccAddress, emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.BACKWORD.toString(),ConstantVariables.RelationType.BCC.toString());
			}
			
			
			if(emailObject.getReplyMessage_ID() != null)
			{
				this.graph.createUniqueLink( emailObject.getReplyMessage_ID(),emailObject.getMessage_ID(),ConstantVariables.EdgeDirection.FORWARD.toString(),ConstantVariables.RelationType.RESPONSE.toString());
			}
		
	
	}
	public void processEmailNodes(EmailDoc emailObject)
	{
		this.graph.createEmailNode(emailObject.getMessage_ID(),emailObject.getDate(), emailObject.getEpochTimeStamp(), emailObject.getSubject(), emailObject.getContent(),emailObject.getReplyMessage_ID(),emailObject.getTopic(),emailObject.getSentiment());
	}
	
	public void processUserNodes(EmailDoc emailObject)
	{
		this.graph.createUserNode(emailObject.getName(), emailObject.getFrom());
		
		if(emailObject.getTo() != null)
		for(String toAddress: emailObject.getTo().toArray(new String[emailObject.getTo().size()]))
		{
			this.graph.createUserNode(null, toAddress);
		}
		
		if(emailObject.getCc() != null)
		for(String toAddress: emailObject.getCc().toArray(new String[emailObject.getCc().size()]))
		{
			this.graph.createUserNode(null, toAddress);
		}
		
		if(emailObject.getBcc() != null)
		for(String toAddress: emailObject.getBcc().toArray(new String[emailObject.getBcc().size()]))
		{
			this.graph.createUserNode(null, toAddress);
		}
	}

	protected void finalize()
	{
		this.graph.closeDatabase();
	}
	
	public void closedb()
	{
		this.graph.closeDatabase();
	}
}
