package snippet;

import java.sql.ResultSet;

import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class TestSqlite {
	
	public TestSqlite() throws Exception {

		SqliteJdbc db = new SqliteJdbc();
		
//		db.update("create table tbl (name varchar(10));");
		
//		db.update("insert into tbl values ('001');");
//		db.update("insert into tbl values ('002');");
//		db.update("insert into tbl values ('003');");
		
//		db.update("drop table tbl;");
		
		ResultSet rs = db.result("select * from tbl");
		while(rs.next()) {
			System.out.println(rs.getString("name").trim());
		}
		
		
		
		
		
		db.disconnect();
		
		System.out.println("Opened database successfully");
		
	}

	public static void main(String[] args) {
		try {
			
			new TestSqlite();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);

	}

}
