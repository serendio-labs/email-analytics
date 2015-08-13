package serendio.graphdb.neo4j;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;

public class ConstantVariables {
	public static String dbPath = null;
	public static enum NodeLabel implements Label
	{
		USER,EMAIL,REPLY;
	}
	public static enum RelationType implements RelationshipType
	{
		TO,FROM,CC,BCC,RESPONSE;
	}
	public static enum EdgeDirection
	{
		FORWARD,BACKWORD;
	}
	public static String getDbPath() {
		return dbPath;
	}

	public static void setDbPath(String dbPath) {
		ConstantVariables.dbPath = dbPath;
	}
}
