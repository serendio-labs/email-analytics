package serendio.graphdb.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class DBConnection {

	private GraphDatabaseFactory dbFactory;
	private GraphDatabaseService dbService; 
	
	public GraphDatabaseFactory getDbFactory() 
	{
		return dbFactory;
	}
	public void setDbFactory(GraphDatabaseFactory dbFactory) 
	{
		this.dbFactory = dbFactory;
	}
	public GraphDatabaseService getDbService() 
	{
		return dbService;
	}
	public void setDbService(GraphDatabaseService dbService) 
	{
		this.dbService = dbService;
	}
	public DBConnection()
	{
		this.dbFactory = new GraphDatabaseFactory();
		this.dbService = null;
	}
	public void createEmbDb(String PathToDb)
	{
		ConstantVariables.setDbPath(PathToDb);
		this.dbService = this.dbFactory.newEmbeddedDatabase(PathToDb);
		//		dbService.shutdown();
	}
}
