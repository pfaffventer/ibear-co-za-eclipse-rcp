package demo.database;

//import java.sql.ResultSet;

import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class FactorySqlite {

	private SqliteJdbc db;
	
	public FactorySqlite()  throws Exception {

		try {
			db = new SqliteJdbc();

			
			
			
			
			dropSchema();
			createSchema();
			loadSampleData();

//			ResultSet rs = db.result("select * from Supplier");
//			while(rs.next()) {
//				System.out.println(rs.getString("Name").trim());
//			}

		} catch (Exception e) {
			throw(e);
		} finally {
			db.disconnect();
		}

	}

	private void loadSampleData() throws Exception {
		
		//Product
		db.update("INSERT INTO Product VALUES (1,'0000000001','Honey','CLASS A','2014-01-10 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (2,'0000000002','Steelhead Salmon','CLASS A','2014-02-11 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (3,'0000000003','Sockeye Salmon','CLASS A','2014-03-12 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (4,'0000000004','Rainbow Trout','CLASS A','2014-04-13 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (5,'0000000005','Sarsaparilla Berries','CLASS B','2014-05-14 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (6,'0000000006','Juneberries','CLASS B','2014-06-15 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (7,'0000000007','Huckleberries','CLASS B','2014-07-16 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (8,'0000000008','Chokecherry','CLASS C','2014-08-17 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (9,'0000000009','Buffaloberry','CLASS C','2014-09-18 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (10,'0000000010','Moose Calves','CLASS B','2014-10-02 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (11,'0000000011','Deer Fawns','CLASS B','2014-11-03 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (12,'0000000012','Ground Squirrels','CLASS C','2014-12-04 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (13,'0000000013','Bees (Adults & Larvae)','CLASS C','2014-01-02 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (14,'0000000014','Carrion','CLASS C','2014-07-07 12:00:00',0,'SYSTEM');");
		db.update("INSERT INTO Product VALUES (15,'0000000015','Aquatic Plants','CLASS D','2014-12-12 12:00:00',0,'SYSTEM');");

		//ProductCategory
		db.update("INSERT INTO ProductCategory VALUES (1,'CLASS A','PRODUCT CLASS A');");
		db.update("INSERT INTO ProductCategory VALUES (2,'CLASS B','PRODUCT CLASS B');");
		db.update("INSERT INTO ProductCategory VALUES (3,'CLASS C','PRODUCT CLASS C');");
		db.update("INSERT INTO ProductCategory VALUES (4,'CLASS D','PRODUCT CLASS D');");
		db.update("INSERT INTO ProductCategory VALUES (5,'CLASS E','PRODUCT CLASS E');");
		db.update("INSERT INTO ProductCategory VALUES (6,'CLASS F','PRODUCT CLASS F');");
		db.update("INSERT INTO ProductCategory VALUES (7,'CLASS G','PRODUCT CLASS G');");

		//Supplier
		db.update("INSERT INTO Supplier VALUES (1,'NFS001','Nome Fisheries AK','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		db.update("INSERT INTO Supplier VALUES (2,'GBH001','Glacier Bay Home Depot','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		db.update("INSERT INTO Supplier VALUES (3,'ASS001','Alaska Sausage & Seafood Company','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		db.update("INSERT INTO Supplier VALUES (4,'GBN001','Glacier Bay National Park','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		db.update("INSERT INTO Supplier VALUES (5,'WAK001','Wild Alaska','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		db.update("INSERT INTO Supplier VALUES (6,'GAS001','Great Alaska Seafood','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
		db.update("INSERT INTO Supplier VALUES (7,'MSB001','Midnight Sun Brewing Company','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");

	}

	private void dropSchema() throws Exception {
		try {
			db.update("DROP TABLE Product;");
			db.update("DROP TABLE ProductCategory;");
			db.update("DROP TABLE PurchaseOrder;");
			db.update("DROP TABLE PurchaseOrderLine;");
			db.update("DROP TABLE Supplier;");
			db.update("DROP TABLE UnitSequence;");
		} catch (Exception e) {
			if(!e.getMessage().startsWith("no such table:")) {
				System.out.println(":) NO TABLE");
				throw(e);
			}
		}
	}

	private void createSchema() throws Exception {
		
		//Product
		db.update("CREATE TABLE Product ( UnitSequence INTEGER, Product VARCHAR(30) PRIMARY KEY ASC, Description VARCHAR(70), ProductCategory VARCHAR(30), TimeCreated VARCHAR(30), Volume FLOAT, User VARCHAR(30));");
		db.update("CREATE UNIQUE INDEX i_Product_01 ON Product (Product);");
		db.update("CREATE UNIQUE INDEX i_Product_00 ON Product (UnitSequence);");

		//ProductCategory
		db.update("CREATE TABLE ProductCategory ( UnitSequence INT, ProductCategory VARCHAR(30) PRIMARY KEY ASC, Description VARCHAR(70));");
		db.update("CREATE UNIQUE INDEX i_ProductCategory_01 ON ProductCategory (ProductCategory);");
		db.update("CREATE UNIQUE INDEX i_ProductCategory_00 ON ProductCategory (UnitSequence);");
		
		//PurchaseOrder
		db.update("CREATE TABLE PurchaseOrder ( UnitSequence INTEGER, PurchaseOrder VARCHAR(30) PRIMARY KEY ASC, Supplier VARCHAR(30), Name VARCHAR(70), User VARCHAR(30), TimeCreated VARCHAR(30)); ");
		db.update("CREATE UNIQUE INDEX i_PurchaseOrder_01 ON PurchaseOrder (PurchaseOrder);");
		db.update("CREATE UNIQUE INDEX i_PurchaseOrder_00 ON PurchaseOrder (UnitSequence);");
		
		//PurchaseOrderLine
		db.update("CREATE TABLE PurchaseOrderLine ( UnitSequence INTEGER, ElementSequence INT, PurchaseOrderLine VARCHAR(30), Product VARCHAR(30), Description VARCHAR(70), Volume FLOAT, Price FLOAT, Total FLOAT, TimeCreated VARCHAR(30), PRIMARY KEY (UnitSequence, ElementSequence)); ");
		db.update("CREATE UNIQUE INDEX i_PurchaseOrderLine_00 ON PurchaseOrderLine (UnitSequence,ElementSequence);");
		
		//Supplier
		db.update("CREATE TABLE Supplier ( UnitSequence INTEGER, Supplier VARCHAR(30) PRIMARY KEY ASC, Name VARCHAR(70), Address1 VARCHAR(50), Address2 VARCHAR(50), Address3 VARCHAR(50), Address4 VARCHAR(50), Address5 VARCHAR(50), User VARCHAR(30), TimeCreated VARCHAR(30));");
		db.update("CREATE UNIQUE INDEX i_Supplier_01 ON Supplier (Supplier);");
		db.update("CREATE UNIQUE INDEX i_Supplier_00 ON Supplier (UnitSequence);");
		
		//UnitSequence
		db.update("CREATE TABLE UnitSequence ( UnitName VARCHAR(70) PRIMARY KEY ASC, NextSequence INT); ");
		db.update("CREATE UNIQUE INDEX i_UnitSequence_00 ON UnitSequence (UnitName);");
		
	}

	public static void main(String[] args) {
		try {
			
			new FactorySqlite();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
