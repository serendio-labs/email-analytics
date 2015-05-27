package serendio.graphdb.neo4j;

import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

public class Neo4jTraversalQuery {

	
	public void mailSentCountperUser()
	{
		DBConnection connection = new DBConnection();
		connection.createEmbDb(ConstantVariables.getDbPath());
		String query = null;
		query = "match (n:USER) -[r:Link {Relation: 'FROM'}]->(c) return n.Email, count(*) as Sent_Mail";
		try (Transaction ignored = connection.getDbService().beginTx(); 
		Result res = connection.getDbService().execute(query))
		{
			
		}
		
		
	}
}
