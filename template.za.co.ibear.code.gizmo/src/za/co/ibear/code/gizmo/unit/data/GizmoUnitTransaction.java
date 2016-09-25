package za.co.ibear.code.gizmo.unit.data;

import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.code.gizmo.GizmoUnit;

public class GizmoUnitTransaction extends GizmoUnit {

	public GizmoUnitTransaction(Unit unit) throws Exception {
		super(unit,unit.NAME + "Transaction",unit.NAME + "Model",GizmoUnitDataConstant.PACKAGE_PREFIX,GizmoUnitDataConstant.PATH_PREFIX);
		
		/**
		 * Check for hard coded strings e.g. search and replace (P)(p)roduct. + b_Stk ??  >> (P)(p)roductCategory << 
		 * 
		 * " + unit.NAME.toLowerCase() + "
		 * " + unit.NAME + "
		 * " + unit.SCHEMA + "
		 *  
		 */

		content = content + "import java.sql.PreparedStatement;\n";
		content = content + "import java.sql.ResultSet;\n";

		content = content + "import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;\n";
		content = content + "import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;\n";
		content = content + "public class " + unit.NAME + "Transaction {\n";
		content = content + "	private SqliteJdbc ibearDb = null;\n";
		content = content + "	private " + unit.NAME + "Model unit = null;\n";
		content = content + "	public " + unit.NAME + "Transaction(" + unit.NAME + "Model unit) throws Exception {\n";
		content = content + "		this.unit = unit;\n";
		content = content + "		try {\n";
		content = content + "			if(unit==null) {\n";
		content = content + "				throw new Exception(Unit cannot be null for database transactions.);\n";
		content = content + "			}\n";
		content = content + "			ibearDb = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);\n";
		content = content + "		} catch (Exception e) {\n";
		content = content + "			e.printStackTrace();\n";
		content = content + "			throw e;\n";
		content = content + "		}\n";
		content = content + "	}\n";
		content = content + "	public void begin() throws Exception {\n";
		content = content + "		try {\n";
		content = content + "			ibearDb.update(BEGIN);\n";
		content = content + "			ResultSet rsCheckLock = ibearDb.result(unit.toLockReadSql());\n";
		content = content + "			if(!rsCheckLock.next()) {\n";
		content = content + "				rsCheckLock.close();\n";
		content = content + "			}\n";
		content = content + "			rsCheckLock.close();\n";
		content = content + "			ibearDb.update(unit.toLockSql());\n";
		content = content + "		} catch (Exception e) {\n";
		content = content + "			rollback();\n";
		content = content + "			throw new Exception(This record is in use, please try again later.);\n";
		content = content + "		}\n";
		content = content + "	}\n";
		content = content + "	public void rollback() throws Exception {\n";
		content = content + "		ibearDb.update(ROLLBACK);\n";
		content = content + "	}\n";
		content = content + "	public void commit() throws Exception {\n";
		content = content + "		ibearDb.update(unit.toSaveSql());\n";
		content = content + "		ibearDb.update(COMMIT);\n";
		content = content + "	}\n";
		content = content + "	public void insert(" + unit.NAME + "Model new" + unit.NAME + ") throws Exception {\n";
		content = content + "		ibearDb.update(new" + unit.NAME + ".toInsertSql());\n";
		content = content + "	}\n";
		content = content + "	public void update() throws Exception {\n";
		content = content + "		ibearDb.update(unit.toSaveSql());\n";
		content = content + "	}\n";
		content = content + "	public void update(String sql) throws Exception {\n";
		content = content + "		ibearDb.update(sql);\n";
		content = content + "	}\n";
		content = content + "	public void delete() throws Exception {\n";
		content = content + "		try {\n";
		content = content + "			ResultSet rsCheckLock = ibearDb.result(unit.toLockReadSql());\n";
		content = content + "			if(!rsCheckLock.next()) {\n";
		content = content + "				rsCheckLock.close();\n";
		content = content + "			}\n";
		content = content + "			rsCheckLock.close();\n";
		content = content + "			ibearDb.update(unit.toDeleteSql());\n";
		content = content + "		} catch (Exception e) {\n";
		content = content + "			rollback();\n";
		content = content + "			throw new Exception(This record is in use, please try again later.);\n";
		content = content + "		}\n";
		content = content + "	}\n";
		content = content + "	public void disconnect() {\n";
		content = content + "		try {\n";
		content = content + "			if(ibearDb==null) {\n";
		content = content + "				p(DEBUG: [ibearDb==null]...);\n";
		content = content + "				return;\n";
		content = content + "			}\n";
		content = content + "			if(ibearDb.getConnection()==null) {\n";
		content = content + "				p(DEBUG: [ibearDb.getConnection()==null]...);\n";
		content = content + "				return;\n";
		content = content + "			}\n";
		content = content + "			if(ibearDb.getConnection().isClosed()) {\n";
		content = content + "				p(DEBUG: [ibearDb.getConnection().isClosed()]...);\n";
		content = content + "				return;\n";
		content = content + "			}\n";
		content = content + "			ibearDb.disconnect();\n";
		content = content + "			ibearDb = null;\n";
		content = content + "		} catch (Exception e) {\n";
		content = content + "			e.printStackTrace();\n";
		content = content + "		}\n";
		content = content + "	}\n";

		content = content + "	public PreparedStatement getPreparedStatement(String sql) throws Exception {\n";
		content = content + "		return ibearDb.getConnection().prepareStatement(sql);\n";
		content = content + "	}\n";
		content = content + "	\n";
		content = content + "	public " + unit.NAME + "Model getUnit() {\n";
		content = content + "		return unit;\n";
		content = content + "	}\n";
		content = content + "	public void setUnit(" + unit.NAME + "Model unit) {\n";
		content = content + "		this.unit = unit;\n";
		content = content + "	}\n";
		content = content + "	protected void p(String v) {\n";
		content = content + "		System.out.println(this.getClass().getSimpleName() + :) + v);\n";
		content = content + "	}\n";
		content = content + "}\n";
		content = content + "	\n";

		createFile();		

	}

}
