package za.co.ibear.lib.jdbc.utility;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {
	
	private Connection db = null;
	private Statement stUpd = null;
	
	protected String dbName = "";
	
	private String url = null;
	private String user = null;
	private String password = null;

	public JdbcConnection() throws Exception {
		connect();
	}

	public JdbcConnection(String db) throws Exception {
		this.dbName= db;
		connect();
	}

	public JdbcConnection(ConnectionRecord connection) throws Exception {
		this.dbName = connection.getDatabasName();
		this.url = connection.getUrl();
		this.user = connection.getUser();
		this.password = connection.getPassword();
		connect("record");
	}

	private void connect(String record) throws Exception {
		@SuppressWarnings("unused")
		Driver d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
		String connectionUrl = url + dbName;
		db = DriverManager.getConnection(connectionUrl,user,password);
		stUpd = db.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
	}

	private void connect() throws Exception {
		@SuppressWarnings("unused")
		Driver d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
		String connectionUrl = "jdbc:postgresql://localhost:5432/" + dbName;
		db = DriverManager.getConnection(connectionUrl,"postgres","password");
		stUpd = db.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
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
