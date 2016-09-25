package za.co.ibear.code.data.dictionary.definition.sample.data;

import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class SampleProducts_TEST {
	
	public SampleProducts_TEST() throws Exception {
		SqliteJdbc db = null;
		try {
			db = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);

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

			db.update("INSERT INTO ProductCategory VALUES (1,'CLASS A','PRODUCT CLASS A');");
			db.update("INSERT INTO ProductCategory VALUES (2,'CLASS B','PRODUCT CLASS B');");
			db.update("INSERT INTO ProductCategory VALUES (3,'CLASS C','PRODUCT CLASS C');");
			db.update("INSERT INTO ProductCategory VALUES (4,'CLASS D','PRODUCT CLASS D');");
			db.update("INSERT INTO ProductCategory VALUES (5,'CLASS E','PRODUCT CLASS E');");
			db.update("INSERT INTO ProductCategory VALUES (6,'CLASS F','PRODUCT CLASS F');");
			db.update("INSERT INTO ProductCategory VALUES (7,'CLASS G','PRODUCT CLASS G');");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(db!=null) {
				db.disconnect();
			}
		}
	}

	protected static void p(String v) {
		System.out.println("SampleProducts:) "+ v);
	}

}
