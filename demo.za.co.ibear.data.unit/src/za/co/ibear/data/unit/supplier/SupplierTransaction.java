package za.co.ibear.data.unit.supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;
public class SupplierTransaction {
	private SqliteJdbc ibearDb = null;
	private SupplierModel unit = null;
	public SupplierTransaction(SupplierModel unit) throws Exception {
		this.unit = unit;
		try {
			if(unit==null) {
				throw new Exception("Unit cannot be null for database transactions.");
			}
			ibearDb = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public void begin() throws Exception {
		try {
//			ibearDb.update("BEGIN");
//			ResultSet rsCheckLock = ibearDb.result(unit.toLockReadSql());
//			if(!rsCheckLock.next()) {
//				rsCheckLock.close();
//			}
//			rsCheckLock.close();
			ibearDb.update(unit.toLockSql());
		} catch (Exception e) {
			rollback();
			throw new Exception("This record is in use, please try again later.");
		}
	}
	public void rollback() throws Exception {
//		ibearDb.update("ROLLBACK");
	}
	public void commit() throws Exception {
		ibearDb.update(unit.toSaveSql());
//		ibearDb.update("COMMIT");
	}
	public void insert(SupplierModel newSupplier) throws Exception {
		ResultSet rs = ibearDb.result("SELECT MAX(UnitSequence) [UnitSequence] from Supplier");
		if(!rs.next()) {
			rs.close();
		}
		int next = rs.getInt("UnitSequence");
		rs.close();
		next = next+1;
		ibearDb.update(newSupplier.toInsertSql(next));
	}
	public void update() throws Exception {
		ibearDb.update(unit.toSaveSql());
	}
	public void update(String sql) throws Exception {
		ibearDb.update(sql);
	}
	public void delete() throws Exception {
		try {
//			ResultSet rsCheckLock = ibearDb.result(unit.toLockReadSql());
//			if(!rsCheckLock.next()) {
//				rsCheckLock.close();
//			}
//			rsCheckLock.close();
			ibearDb.update(unit.toDeleteSql());
		} catch (Exception e) {
			rollback();
			throw new Exception("This record is in use, please try again later.");
		}
	}
	public void disconnect() {
		try {
			if(ibearDb==null) {
				p("DEBUG: [ibearDb==null]...");
				return;
			}
			if(ibearDb.getConnection()==null) {
				p("DEBUG: [ibearDb.getConnection()==null]...");
				return;
			}
			if(ibearDb.getConnection().isClosed()) {
				p("DEBUG: [ibearDb.getConnection().isClosed()]...");
				return;
			}
			ibearDb.disconnect();
			ibearDb = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public PreparedStatement getPreparedStatement(String sql) throws Exception {
		return ibearDb.getConnection().prepareStatement(sql);
	}
	
	public SupplierModel getUnit() {
		return unit;
	}
	public void setUnit(SupplierModel unit) {
		this.unit = unit;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}
}
	
