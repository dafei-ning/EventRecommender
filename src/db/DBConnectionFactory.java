package db;

public class DBConnectionFactory {
	// This should change based on the pipeline.
	private static final String DEFAULT_DB = "mysql";
	
	public static DBConnection getConnection(String db) {
		switch (db) {
		case "mysql":
			return null;
		case "mongodb":
			return null;
		default:
			throw new IllegalArgumentException("Invalid db:" + db);
		}

	}
	
	public static DBConnection getConnection() {
		return null;
		
	}
}

