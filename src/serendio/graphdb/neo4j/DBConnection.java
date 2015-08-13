package serendio.graphdb.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

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
	public void dbconnect(String PathToDb)
	{
		dbService = new GraphDatabaseFactory().
		newEmbeddedDatabaseBuilder( PathToDb ).
		setConfig( GraphDatabaseSettings.node_keys_indexable, "Content,Date,Email,EpochTimestamp,Message_ID,Name,Relation,Sentiment,Subject,Topic" ).
	    setConfig( GraphDatabaseSettings.relationship_keys_indexable, "Link" ).
	    setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
	    setConfig( GraphDatabaseSettings.relationship_auto_indexing, "true" ).
	    newGraphDatabase();
	}
	public void dbShutdown()
	{
		this.dbService.shutdown();
	}
	protected void finalize()
	{
		this.dbService.shutdown();
	}
}
