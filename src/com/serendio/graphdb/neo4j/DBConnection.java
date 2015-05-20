package com.serendio.graphdb.neo4j;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

import com.serendio.Utils.Utils;
enum NodeType implements Label {
	Name;
}

public class DBConnection {
	public static GraphDatabaseFactory dbFactory = null;
	public static GraphDatabaseService db = null;
	static Label label = DynamicLabel.label("User");
	public static void createConnection()
	{
		 dbFactory = new GraphDatabaseFactory();
		//db = dbFactory.newEmbeddedDatabase("/home/nishant/Serendio/Enron Email/TPNeo4jDB");
		 db = dbFactory.newEmbeddedDatabase("/home/nishant/BigData Stuff/Ne04j/neo4j-community-2.2.1/data/graph.db");
		/* IndexDefinition indexDefinition;
		 try( Transaction tx = db.beginTx())
		 {
		//	 Schema schema = db.schema();
		//	 indexDefinition = schema.indexFor(DynamicLabel.label("User")).on("Email").create();
			 tx.success();
		 }*/
	}
	public static void init()
	{
		String list[] = Utils.getEmployeeList();
		for(int i = 0; i < Utils.getEmployeeCount(); i++)
		{
			createNode(list[i]);
		}
	}
	public static boolean checkNodeExist(String Email)
	{
		try(Transaction tx = db.beginTx())
		{
			try(ResourceIterator<Node> user = db.findNodes(label,"Email",Email))
			{
				if(user.hasNext())
					return true;
				else
					return false;
			}
		}
	}
	public static void createNode(String Email)
	{
	//	Label label = DynamicLabel.label("User");
		if(!checkNodeExist(Email))
		{
			try(Transaction tx = db.beginTx())
			{
				Node myNode = db.createNode(label);
				myNode.setProperty("Email",Email);
				tx.success();
			}
		}
	}
}
