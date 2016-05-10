package com.serendio.graphdb.neo4jServer;

import com.serendio.dataset.domain.EmailDoc;
import java.io.FileNotFoundException;
import org.apache.http.client.ClientProtocolException;
import java.io.IOException;

public class EmailDocToNeo4j {

	public Neo4jGraphCreation graph;

	public void pushToNeo4j(EmailDoc emailObject) throws FileNotFoundException,
			ClientProtocolException, IOException {
		processUserNodes(emailObject);
		processEmailNodes(emailObject);
		processLinks(emailObject);
		System.out.println("EmailDoc Pushed to Neo4j");
	}

	public void processLinks(EmailDoc emailObject) {
		Neo4jGraphCreation graphi = new Neo4jGraphCreation();

		if (emailObject.getFrom() != null)
			graphi.createUniqueLink(emailObject.getFrom(),
					emailObject.getMessage_ID(), "FORWARD", "FROM");

		if (emailObject.getTo() != null)
			for (String toAddress : emailObject.getTo().toArray(
					new String[emailObject.getTo().size()])) {

				graphi.createUniqueLink(toAddress, emailObject.getMessage_ID(),
						"BACKWORD", "TO");
			}

		if (emailObject.getCc() != null)
			for (String ccAddress : emailObject.getCc().toArray(
					new String[emailObject.getCc().size()])) {
				graphi.createUniqueLink(ccAddress, emailObject.getMessage_ID(),
						"BACKWORD", "CC");
			}

		if (emailObject.getBcc() != null)
			for (String bccAddress : emailObject.getBcc().toArray(
					new String[emailObject.getBcc().size()])) {
				graphi.createUniqueLink(bccAddress,
						emailObject.getMessage_ID(), "BACKWORD", "BCC");
			}

		if (emailObject.getReplyMessage_ID() != null) {
			graphi.createUniqueLink(emailObject.getReplyMessage_ID(),
					emailObject.getMessage_ID(), "FORWARD", "RESPONSE");
		}
		graphi.closeConnection();

	}

	public void processEmailNodes(EmailDoc emailObject) {
		Neo4jGraphCreation graphi = new Neo4jGraphCreation();
		graphi.createEmailNode(emailObject.getMessage_ID(),
				emailObject.getDate(), emailObject.getEpochTimeStamp(),
				emailObject.getSubject(), emailObject.getContent(),
				emailObject.getReplyMessage_ID(), emailObject.getTopic(),
				emailObject.getSentiment());
		graphi.closeConnection();
	}

	public void processUserNodes(EmailDoc emailObject) {
		Neo4jGraphCreation graphi = new Neo4jGraphCreation();

		graphi.createUserNode(emailObject.getName(), emailObject.getFrom());

		if (emailObject.getTo() != null)
			for (String toAddress : emailObject.getTo().toArray(
					new String[emailObject.getTo().size()])) {
				graphi.createUserNode(null, toAddress);
			}

		if (emailObject.getCc() != null)
			for (String toAddress : emailObject.getCc().toArray(
					new String[emailObject.getCc().size()])) {
				graphi.createUserNode(null, toAddress);
			}

		if (emailObject.getBcc() != null)
			for (String toAddress : emailObject.getBcc().toArray(
					new String[emailObject.getBcc().size()])) {
				graphi.createUserNode(null, toAddress);
			}
		graphi.closeConnection();
	}
}
