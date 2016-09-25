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

		@SuppressWarnings("unused")
		Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
		
//		db = DriverManager.getConnection("jdbc:sqlite:D:/neon/rcp-neon/ibear-workspace/ibear-co-za-eclipse-rcp/za.co.ibear.playground/ibear.db");

		db = DriverManager.getConnection("jdbc:sqlite::memory:");
		stUpd = db.createStatement();
		
		createSchema();
		loadSampleData();

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
	
	
	private void loadSampleData() throws Exception {
		
		//Product
		update("INSERT INTO Product VALUES (1,'0000000001','Honey','CLASS A','2014-01-10 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (2,'0000000002','Steelhead Salmon','CLASS A','2014-02-11 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (3,'0000000003','Sockeye Salmon','CLASS A','2014-03-12 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (4,'0000000004','Rainbow Trout','CLASS A','2014-04-13 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (5,'0000000005','Sarsaparilla Berries','CLASS B','2014-05-14 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (6,'0000000006','Juneberries','CLASS B','2014-06-15 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (7,'0000000007','Huckleberries','CLASS B','2014-07-16 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (8,'0000000008','Chokecherry','CLASS C','2014-08-17 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (9,'0000000009','Buffaloberry','CLASS C','2014-09-18 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (10,'0000000010','Moose Calves','CLASS B','2014-10-02 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (11,'0000000011','Deer Fawns','CLASS B','2014-11-03 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (12,'0000000012','Ground Squirrels','CLASS C','2014-12-04 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (13,'0000000013','Bees (Adults & Larvae)','CLASS C','2014-01-02 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (14,'0000000014','Carrion','CLASS C','2014-07-07 12:00:00',0,'SYSTEM');");
		update("INSERT INTO Product VALUES (15,'0000000015','Aquatic Plants','CLASS D','2014-12-12 12:00:00',0,'SYSTEM');");

		//ProductCategory
		update("INSERT INTO ProductCategory VALUES (1,'CLASS A','PRODUCT CLASS A');");
		update("INSERT INTO ProductCategory VALUES (2,'CLASS B','PRODUCT CLASS B');");
		update("INSERT INTO ProductCategory VALUES (3,'CLASS C','PRODUCT CLASS C');");
		update("INSERT INTO ProductCategory VALUES (4,'CLASS D','PRODUCT CLASS D');");
		update("INSERT INTO ProductCategory VALUES (5,'CLASS E','PRODUCT CLASS E');");
		update("INSERT INTO ProductCategory VALUES (6,'CLASS F','PRODUCT CLASS F');");
		update("INSERT INTO ProductCategory VALUES (7,'CLASS G','PRODUCT CLASS G');");

		//Supplier
		update("INSERT INTO Supplier VALUES (1,'NFS001','Nome Fisheries AK','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		update("INSERT INTO Supplier VALUES (2,'GBH001','Glacier Bay Home Depot','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		update("INSERT INTO Supplier VALUES (3,'ASS001','Alaska Sausage & Seafood Company','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		update("INSERT INTO Supplier VALUES (4,'GBN001','Glacier Bay National Park','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		update("INSERT INTO Supplier VALUES (5,'WAK001','Wild Alaska','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		update("INSERT INTO Supplier VALUES (6,'GAS001','Great Alaska Seafood','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		update("INSERT INTO Supplier VALUES (7,'MSB001','Midnight Sun Brewing Company','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");

	}

//	private void dropSchema() throws Exception {
//		try {
//			update("DROP TABLE Product;");
//			update("DROP TABLE ProductCategory;");
//			update("DROP TABLE PurchaseOrder;");
//			update("DROP TABLE PurchaseOrderLine;");
//			update("DROP TABLE Supplier;");
//			update("DROP TABLE UnitSequence;");
//		} catch (Exception e) {
//			if(!e.getMessage().startsWith("no such table:")) {
//				System.out.println(":) NO TABLE");
//				throw(e);
//			}
//		}
//	}

	private void createSchema() throws Exception {
		
		//Product
		update("CREATE TABLE Product ( UnitSequence INTEGER, Product VARCHAR(30) PRIMARY KEY ASC, Description VARCHAR(70), ProductCategory VARCHAR(30), TimeCreated VARCHAR(30), Volume FLOAT, User VARCHAR(30));");
		update("CREATE UNIQUE INDEX i_Product_01 ON Product (Product);");
		update("CREATE UNIQUE INDEX i_Product_00 ON Product (UnitSequence);");

		//ProductCategory
		update("CREATE TABLE ProductCategory ( UnitSequence INT, ProductCategory VARCHAR(30) PRIMARY KEY ASC, Description VARCHAR(70));");
		update("CREATE UNIQUE INDEX i_ProductCategory_01 ON ProductCategory (ProductCategory);");
		update("CREATE UNIQUE INDEX i_ProductCategory_00 ON ProductCategory (UnitSequence);");
		
		//PurchaseOrder
		update("CREATE TABLE PurchaseOrder ( UnitSequence INTEGER, PurchaseOrder VARCHAR(30) PRIMARY KEY ASC, Supplier VARCHAR(30), Name VARCHAR(70), User VARCHAR(30), TimeCreated VARCHAR(30)); ");
		update("CREATE UNIQUE INDEX i_PurchaseOrder_01 ON PurchaseOrder (PurchaseOrder);");
		update("CREATE UNIQUE INDEX i_PurchaseOrder_00 ON PurchaseOrder (UnitSequence);");
		
		//PurchaseOrderLine
		update("CREATE TABLE PurchaseOrderLine ( UnitSequence INTEGER, ElementSequence INT, PurchaseOrderLine VARCHAR(30), Product VARCHAR(30), Description VARCHAR(70), Volume FLOAT, Price FLOAT, Total FLOAT, TimeCreated VARCHAR(30), PRIMARY KEY (UnitSequence, ElementSequence)); ");
		update("CREATE UNIQUE INDEX i_PurchaseOrderLine_00 ON PurchaseOrderLine (UnitSequence,ElementSequence);");
		
		//Supplier
		update("CREATE TABLE Supplier ( UnitSequence INTEGER, Supplier VARCHAR(30) PRIMARY KEY ASC, Name VARCHAR(70), Address1 VARCHAR(50), Address2 VARCHAR(50), Address3 VARCHAR(50), Address4 VARCHAR(50), Address5 VARCHAR(50), User VARCHAR(30), TimeCreated VARCHAR(30));");
		update("CREATE UNIQUE INDEX i_Supplier_01 ON Supplier (Supplier);");
		update("CREATE UNIQUE INDEX i_Supplier_00 ON Supplier (UnitSequence);");
		
		//UnitSequence
		update("CREATE TABLE UnitSequence ( UnitName VARCHAR(70) PRIMARY KEY ASC, NextSequence INT); ");
		update("CREATE UNIQUE INDEX i_UnitSequence_00 ON UnitSequence (UnitName);");
		
	}


}
