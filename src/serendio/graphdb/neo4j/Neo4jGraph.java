package serendio.graphdb.neo4j;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

public class Neo4jGraph {

	public DBConnection connection;
	static Label label = DynamicLabel.label("User");
	public void init(String Path)
	{
		connection = new DBConnection();
		connection.createEmbDb(Path);
	}

	public void closeDatabase()
	{
		connection.getDbService().shutdown();
	}
	
	public void createEmailNode(String Name,String Message_ID, String Date, String Subject, String Content)
	{
		if(isEmailNodeExist(Message_ID))
			return;
		Node myNode = null;
		try(Transaction tx = connection.getDbService().beginTx())
		{
			myNode = connection.getDbService().createNode(ConstantVariables.NodeLabel.EMAIL);
			myNode.setProperty("Name", Name);
			myNode.setProperty("Message_ID", Message_ID);
			myNode.setProperty("Date", Date);
			myNode.setProperty("Subject", Subject);
			myNode.setProperty("Content", Content);
			tx.success();
		}
	}
	
	private boolean isEmailNodeExist(String Message_ID) 
	{
		boolean exist = false;
		try(Transaction tx = connection.getDbService().beginTx())
		{
			try(ResourceIterator<Node> emailNode = connection.getDbService().findNodes(ConstantVariables.NodeLabel.EMAIL, "Message_ID", Message_ID))
			{
				exist = emailNode.hasNext()?true:false;
			}
	}
		return exist;
	}
	public void createUniqueLink(String SourceEmail,String DestinationMail_ID, String Direction, String Relation)
	{
		Node userNode = getUserNode(SourceEmail);
		Node mailNode = getEmailNode(DestinationMail_ID);
		System.out.println(userNode);
		System.out.println(mailNode);
		try(Transaction tx = connection.getDbService().beginTx())
		{
			Relationship relationship = userNode.createRelationshipTo(mailNode, ConstantVariables.RelationType.FROM);
			relationship.setProperty("MailHeader", Relation);
			tx.success();
		}
	}
	
	public Node getUserNode(String Email)
	{
		try(Transaction tx = connection.getDbService().beginTx())
		{
			try(ResourceIterator<Node> user = connection.getDbService().findNodes(ConstantVariables.NodeLabel.USER, "Email", Email))
			{
				return user.hasNext()?user.next():null;
			}
		}
	}
	
	public Node getEmailNode(String Email_ID)
	{
		try(Transaction tx = connection.getDbService().beginTx())
		{
			try(ResourceIterator<Node> mailNode = connection.getDbService().findNodes(ConstantVariables.NodeLabel.EMAIL, "Message_ID", Email_ID))
			{
				return mailNode.hasNext()?mailNode.next():null;
			}
		}
	}

	public void createUserNode(String Name,String Email)
	{
		if(isUserNodeExist(Email))
			return;
		Node myNode = null;
		try(Transaction tx = connection.getDbService().beginTx())
		{
			myNode = connection.getDbService().createNode(ConstantVariables.NodeLabel.USER);
			myNode.setProperty("Email", Email);
			myNode.setProperty("Name", Name);
			tx.success();
		}
	}
	public boolean isUserNodeExist(String Email)
	{
		boolean exist = false;
		try(Transaction tx = connection.getDbService().beginTx())
		{
			try(ResourceIterator<Node> user = connection.getDbService().findNodes(ConstantVariables.NodeLabel.USER, "Email", Email))
			{
				exist = user.hasNext()?true:false;
			}
		}
		return exist;
	}
}
