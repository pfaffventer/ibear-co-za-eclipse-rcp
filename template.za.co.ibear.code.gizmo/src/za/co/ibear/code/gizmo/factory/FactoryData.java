package za.co.ibear.code.gizmo.factory;

import java.util.LinkedHashSet;

import java.util.Set;

import za.co.ibear.code.data.dictionary.definition.query.CategoryQuery;
import za.co.ibear.code.data.dictionary.definition.query.StockQuery;
import za.co.ibear.code.data.dictionary.definition.unit.Product;
import za.co.ibear.code.data.dictionary.definition.unit.ProductCategory;
import za.co.ibear.code.data.dictionary.definition.unit.PurchaseOrder;
import za.co.ibear.code.data.dictionary.definition.unit.PurchaseOrderLine;
import za.co.ibear.code.data.dictionary.definition.unit.Supplier;
import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.code.data.dictionary.system.sequence.UnitSequence;
import za.co.ibear.code.data.dictionary.system.sequence.Sequence;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.code.gizmo.query.data.GizmoQueryModel;
import za.co.ibear.code.gizmo.unit.data.GizmoUnitModel;
import za.co.ibear.code.gizmo.unit.data.GizmoUnitSql;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;

public class FactoryData {

	protected boolean RE_CREATE = false;
	
	public FactoryData(boolean re_create) throws Exception {
		this.RE_CREATE = re_create;

		//System
		new GizmoUnitSql(new UnitSequence(),RE_CREATE);

		//Unit
		Set<Unit> units = new LinkedHashSet<Unit>();
		units.add(new Supplier());
		units.add(new Product());
		units.add(new ProductCategory());
		units.add(new PurchaseOrder());
		units.add(new PurchaseOrderLine());
		for(Unit unit:units) {
			new GizmoUnitModel(unit,RE_CREATE);
		}

		if(RE_CREATE) {
			SqliteJdbc db = null;
			try {
				db = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);
				for(Unit unit:units) {
					if(unit.ENTITY_SEQUENCE.size()>0) {
						UnitSequence e = new UnitSequence();
						Sequence seq = (Sequence) unit.ENTITY_SEQUENCE.toArray()[0];
						String q = "insert into " + e.FULL_NAME + " values ('" + seq.getName() + "'," + seq.getSequence() + ");";
						db.update(q);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(db!=null) {
					db.disconnect();
				}
			}
		}

		//Query
		new GizmoQueryModel(new StockQuery());
		new GizmoQueryModel(new CategoryQuery());
		
	}
	
	protected static void p(String v) {
		System.out.println("FactoryData:) " + v);
	}

}
