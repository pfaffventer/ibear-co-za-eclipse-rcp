package za.co.ibear.code.data.dictionary.definition.sample.data;

import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class SampleSuppliers {
	
	public SampleSuppliers() throws Exception {
		SqliteJdbc db = null;
		try {
			db = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);

			db.update("INSERT INTO Supplier VALUES (1,'NFS001','Nome Fisheries AK','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
			db.update("INSERT INTO Supplier VALUES (2,'GBH001','Glacier Bay Home Depot','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
			db.update("INSERT INTO Supplier VALUES (3,'ASS001','Alaska Sausage & Seafood Company','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
			db.update("INSERT INTO Supplier VALUES (4,'GBN001','Glacier Bay National Park','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
			db.update("INSERT INTO Supplier VALUES (5,'WAK001','Wild Alaska','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
			db.update("INSERT INTO Supplier VALUES (6,'GAS001','Great Alaska Seafood','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
			db.update("INSERT INTO Supplier VALUES (7,'MSB001','Midnight Sun Brewing Company','Address1','Address2','Address3','Address4','Address5','JOHANV','2014-01-01 00:00:00');");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(db!=null) {
				db.disconnect();
			}
		}
	}

	protected static void p(String v) {
		System.out.println("SampleSuppliers:) "+ v);
	}

}
