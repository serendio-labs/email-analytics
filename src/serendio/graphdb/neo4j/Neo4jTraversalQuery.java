package serendio.graphdb.neo4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.helpers.collection.IteratorUtil;
public class Neo4jTraversalQuery {

	
	public void mailSentCountperUser()
	{
		DBConnection connection = new DBConnection();
		connection.createEmbDb(ConstantVariables.getDbPath());
		String query = null;
		query = "match (n:USER) -[r:Link {Relation: 'FROM'}]->(c) return n.Email, count(*) as Sent_Mail";
			 connection.getDbService().execute(query);
				        			
	}
	public String ExecuteQuery(String query)
	{
		String result;
		DBConnection connection = new DBConnection();
		connection.createEmbDb(ConstantVariables.getDbPath());
		result=connection.getDbService().execute(query).resultAsString();
		return(result);
	}
	
	public String ExecuteQuery(String query, Map<String,Object> parameters)
	{
		String result;
		DBConnection connection = new DBConnection();
		connection.createEmbDb(ConstantVariables.getDbPath());
		result=connection.getDbService().execute(query,parameters).resultAsString();
		return(result);
	}
	public String DisplayAllUsers()
	{
		String query = "match (user : USER) return user";
	    String result= ExecuteQuery(query);
	    return result;
 	
	}
	public String DisplayCompanyUsers(String CompanyId)
	{
		String sub=".*"+CompanyId+"$";
		String query="match (user: USER) WHERE user.Email =~ {sub} RETURN user";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sub", sub);
		String result =ExecuteQuery(query,parameters);
		return result;
	}
	
	public String UserMailSentCount(String Email)
	{
		String query="MATCH (user:USER)-[:Link {Relation:'FROM'}]->(email) WHERE user.Email = {Email} RETURN count(*)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Email", Email);
		String result= ExecuteQuery(query,parameters);
		return result;
	}

	public String UserMailReceivedCount(String Email)
	{
		String query="MATCH (email:Email)-[:Link {Relation:'TO'}]->(user) WHERE user.Email = {Email} RETURN count(*)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Email", Email);
		String result= ExecuteQuery(query,parameters);
		return result;
	}
	public String AvgMailSent()
	{
		String query="match (n:USER),(n)-[r:Link]->(p:Email) with n.Email as email, count(r) as count return avg(count)";
		String result= ExecuteQuery(query);
		return result;
		
	}
}
