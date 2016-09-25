package za.co.ibear.lib.jdbc.microsoft;

import java.sql.Connection;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MsSqlServerJdbc {

	private Connection db = null;
	private Statement stUpd = null;
	protected String dbName = "";

	public MsSqlServerJdbc(boolean debug) throws Exception {
		connect(debug);
	}

	private void connect(boolean debug) throws Exception {
		@SuppressWarnings("unused")
		Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		String connectionUrl;
		if(!debug) {
			connectionUrl = "jdbc:sqlserver://192.168.249.201:1433;databaseName=SysproCompany0;integratedSecurity=false;";
		} else {
			connectionUrl = "jdbc:sqlserver://127.0.0.1\\JVSQL_DELL;databaseName=SysproCompany0;integratedSecurity=false;";
//			connectionUrl = "jdbc:sqlserver://192.168.249.105\\DEVSQL;databaseName=SysproCompany0;integratedSecurity=false;";
		}
		db = DriverManager.getConnection(connectionUrl,"sa","Hornet123");
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
