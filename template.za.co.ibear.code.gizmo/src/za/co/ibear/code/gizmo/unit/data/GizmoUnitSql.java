package za.co.ibear.code.gizmo.unit.data;

import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.index.Index;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.code.gizmo.GizmoUnit;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class GizmoUnitSql extends GizmoUnit {

	public GizmoUnitSql(Unit unit,boolean reCreate) throws Exception {
		super(unit,unit.NAME + "Schema",unit.NAME + "Model",GizmoUnitDataConstant.PACKAGE_PREFIX,GizmoUnitDataConstant.PATH_PREFIX,".sql");
		String name = unit.FULL_NAME;

		String drop = "DROP TABLE " +  name;

		if(reCreate) {
			databaseExecute(drop,true);
		}
		content = "-- " + drop + ";\n\n";
		
		String table = null;
		table = "CREATE TABLE " + name + " (\n";
		for(Field field:unit.FIELD) {
			table = table + "\t" + field.getName() + " "  + field.getAnsiDbType() + field.getAnsiDbSize() + ",\n";
		}
		table  = table.substring(0,table.length()-2) + ")";

		if(reCreate) {
			databaseExecute(table,false);
		}

		content = content + table + ";\n\n";

		for(Index index:unit.INDEX) {
			if(reCreate) {
				databaseExecute(index.toString(),false);
			}
			content = content + "" + index.toString() + ";\n"; 	
		}

		content = content + "\n";
		
		for(PrimaryKey key:unit.PRIMARY_KEY) {
			if(reCreate) {
				databaseExecute(key.toString(),false);
			}
			content = content + "" + key.toString() + ";\n\n";
		}

		createFile();		
	}

	private void databaseExecute(String query, boolean ignoreError) throws Exception {
		SqliteJdbc db = null;
		try {
			db = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);
			db.update(query);
			
		} catch (Exception e) {
			if(!ignoreError) {
				throw(e);
			}
		} finally {
			if(db!=null) {
				db.disconnect();
			}
		}
	}

	protected static void p(String v) {
		System.out.println("GizmoSql:) " + v);
	}

}
