package za.co.ibear.code.gizmo.factory;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;

import za.co.ibear.code.data.dictionary.definition.sample.data.SampleProducts_TEST;
import za.co.ibear.code.data.dictionary.definition.sample.data.SampleSuppliers;
import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class Factory {

	public Factory() throws Exception {

//		createDatabase();
//		createSchema();
		
		new FactoryData(true);
		new FactorySwt();
	
		new SampleProducts_TEST();
		new SampleSuppliers();
		
	}
	
	public void createDatabase() throws Exception {
		SqliteJdbc db = null;
		try {
			db = new SqliteJdbc(DatabaseConstant.DB_ROOT_CONNECTION);
			db.update("CREATE DATABASE ibear" 
					  + " WITH OWNER = postgres"
					  + " ENCODING = 'UTF8'"
				      + " TABLESPACE = pg_default"
				      + " LC_COLLATE = 'English_South Africa.1252'"
				      + " LC_CTYPE = 'English_South Africa.1252'"
				      + " CONNECTION LIMIT = -1");
		} catch (Exception e) {
			throw(e);
		} finally {
			if(db!=null) {
				db.disconnect();
			}
		}
	}

	public void createSchema() throws Exception {
		SqliteJdbc db = null;
		try {
			db = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);
			String q = null;
			String schemaSource = "/template.za.co.ibear.code.data.dictionary.system/src/za/co/ibear/code/data/dictionary/system/schema/SchemaConstant.java";
			BufferedReader reader = new BufferedReader(new FileReader(new File(new File("..").getCanonicalFile() + schemaSource)));
			String line;
			while ((line=reader.readLine())!=null) {
				if(line.trim().startsWith("public static")) {
					q = "CREATE SCHEMA _00 AUTHORIZATION postgres";
					db.update(q.replaceAll("_00",line.split(" ")[5].replaceAll("","").replaceAll(";","")));
				}
			}
			reader.close();
		} catch (Exception e) {
			throw(e);
		} finally {
			if(db!=null) {
				db.disconnect();
			}
		}
	}

	protected static void p(String v) {
		System.out.println("Factory:) " + v);
	}

	public static void main(String[] args) {
		try {
			new Factory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
