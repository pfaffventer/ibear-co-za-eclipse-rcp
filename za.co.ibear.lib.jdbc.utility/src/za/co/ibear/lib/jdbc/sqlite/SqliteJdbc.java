package za.co.ibear.lib.jdbc.sqlite;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import za.co.ibear.lib.jdbc.utility.ConnectionRecord;

public class SqliteJdbc {
	
	private Connection db = null;
	private Statement stUpd = null;

	public SqliteJdbc() throws Exception {
		connect();
	}

	public SqliteJdbc(ConnectionRecord dummy) throws Exception {
		connect();
	}

	private void connect() throws Exception {
		
		
//		File f = new File(".");
		
//		System.out.println(":)" + f.getAbsolutePath());
//		System.out.println(":)" + f.getCanonicalPath() + "/ibear.db");

		@SuppressWarnings("unused")
		Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
		db = DriverManager.getConnection("jdbc:sqlite:D:/neon/rcp-neon/ibear-workspace/ibear-co-za-eclipse-rcp/za.co.ibear.playground/ibear.db");
		stUpd = db.createStatement();
	}
	
	public Statement updateStatement() throws Exception {
		return db.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
	}

	public void update(String q) throws Exception {
		stUpd.executeUpdate(q);
	}

	public void drop(String q) throws Exception {
		try {
			stUpd.executeUpdate(q);
		} catch (SQLException e) {
			if(!(e.getErrorCode()==(3701))) {
				throw new Exception(e);
			}
		}
	}
	
	public void disconnect() throws Exception {
		if (stUpd != null)
			stUpd.close();
		if (db != null)
			db.close();
	}
	
	public Connection getConnection() {
		return db;
	}
	
	public ResultSet result(String q) throws Exception {
		return db.createStatement().executeQuery(q);
	}

}
