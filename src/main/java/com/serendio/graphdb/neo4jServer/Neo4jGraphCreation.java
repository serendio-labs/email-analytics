package com.serendio.graphdb.neo4jServer;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.util.HashMap;
import java.util.Map;

public class Neo4jGraphCreation {

	public Driver driver;
	public Session session;

	Neo4jGraphCreation() {
		this.driver = GraphDatabase.driver("bolt://localhost",
				AuthTokens.basic("neo4j", "123456"));
		this.session = driver.session();
	}

	public void closeConnection() {
		this.session.close();
	}

	public void createEmailNode(String Message_ID, String Date,
			long EpochTimestamp, String Subject, String Content,
			String ReplyMessage_ID, String Topic, String Sentiment) {
		String queryString = null;
		if (ReplyMessage_ID != null)
			queryString = "MERGE (n:Email:Reply {Message_ID: {Message_ID} , Date: {Date} , EpochTimestamp: {EpochTimestamp}, Subject: {Subject} , Content: {Content} , Topic: {Topic} , Sentiment: {Sentiment}}) RETURN n";
		else
			queryString = "MERGE (n:Email {Message_ID: {Message_ID} , Date: {Date} , EpochTimestamp: {EpochTimestamp}, Subject: {Subject} , Content: {Content} , Topic: {Topic} , Sentiment: {Sentiment}}) RETURN n";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Message_ID", Message_ID);
		parameters.put("Date", Date);
		parameters.put("EpochTimestamp", EpochTimestamp);
		parameters.put("Topic", Topic);
		parameters.put("Sentiment", Sentiment);
		if (Subject != null) {
			parameters.put("Subject", Subject);
		} else {
			parameters.put("Subject", " ");
		}
		if (Content != null) {
			parameters.put("Content", Content);
		} else {
			parameters.put("Content", " ");
		}
		this.session.run(queryString, parameters);
	}

	public void createUniqueLink(String SourceEmail, String DestinationMail_ID,
			String Direction, String Relation) {
		String query = null;
		if (Relation.equals("RESPONSE")) {
			query = "match (a:Email),(b:Email) Where a.Message_ID=\""
					+ SourceEmail + "\"AND b.Message_ID=\""
					+ DestinationMail_ID + "\" merge (a)-[r:Link {Relation: '"
					+ Relation + "'}]->(b)";
		} else {
			if (Direction.equals("FORWARD"))
				query = "match (a:User),(b:Email) Where a.Email=\""
						+ SourceEmail + "\"AND b.Message_ID=\""
						+ DestinationMail_ID
						+ "\" merge (a)-[r:Link {Relation: '" + Relation
						+ "'}]->(b)";
			else if (Direction.equals("BACKWORD"))
				query = "match (a:User),(b:Email) Where a.Email=\""
						+ SourceEmail + "\"AND b.Message_ID=\""
						+ DestinationMail_ID
						+ "\" merge (a)<-[r:Link {Relation: '" + Relation
						+ "'}]-(b)";
		}
		this.session.run(query);
	}

	public void createUserNode(String Name, String Email) {
		String queryString = "MERGE (n:User {Email: {Email}}) RETURN n";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Email", Email);
		this.session.run(queryString, parameters);
		if (Name != null) {
			queryString = "MATCH (n { Email: {Email} }) SET n.Name = {Name} RETURN n";
			parameters.put("Email", Email);
			parameters.put("Name", Name);
			this.session.run(queryString, parameters);
		}
	}
}
