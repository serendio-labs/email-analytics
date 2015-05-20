package serendio.graphdb.neo4j;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;

public class ConstantVariables {
	public static String dbPath = null;
	static enum NodeLabel implements Label
	{
		USER,EMAIL;
	}
	static enum RelationType implements RelationshipType
	{
		TO,FROM,CC,BCC;
	}
	public static String getDbPath() {
		return dbPath;
	}

	public static void setDbPath(String dbPath) {
		ConstantVariables.dbPath = dbPath;
	}
}
