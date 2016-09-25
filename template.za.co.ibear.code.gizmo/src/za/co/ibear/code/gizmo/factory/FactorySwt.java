package za.co.ibear.code.gizmo.factory;

import za.co.ibear.code.data.dictionary.definition.query.CategoryQuery;
import za.co.ibear.code.data.dictionary.definition.query.StockQuery;
import za.co.ibear.code.data.dictionary.definition.unit.Product;
import za.co.ibear.code.data.dictionary.definition.unit.ProductCategory;
import za.co.ibear.code.data.dictionary.definition.unit.PurchaseOrder;
import za.co.ibear.code.data.dictionary.definition.unit.PurchaseOrderLine;
import za.co.ibear.code.data.dictionary.definition.unit.Supplier;
import za.co.ibear.code.gizmo.query.swt.GizmoQueryBrowser;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitBrowseCombo;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitBrowseComposite;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitBrowseDialog;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitBrowseDialogState;
import za.co.ibear.code.gizmo.unit.swt.GizmoEditCombo_Unit;
import za.co.ibear.code.gizmo.unit.swt.GizmoEditComposite_Unit;
import za.co.ibear.code.gizmo.unit.swt.GizmoEditDialog_Unit;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitEditor;

public class FactorySwt {

	public FactorySwt() throws Exception {

		//Generic
		new GizmoUnitBrowseCombo();
		new GizmoUnitBrowseComposite();
		new GizmoUnitBrowseDialog();
		new GizmoUnitBrowseDialogState();

		new GizmoEditCombo_Unit();
		new GizmoEditComposite_Unit();
		new GizmoEditDialog_Unit();
		
		//Unit
		new GizmoUnitEditor(new Supplier());
		new GizmoUnitEditor(new Product());
		new GizmoUnitEditor(new ProductCategory());
		new GizmoUnitEditor(new PurchaseOrder());
		new GizmoUnitEditor(new PurchaseOrderLine());

		//Query
		new GizmoQueryBrowser(new StockQuery());
		new GizmoQueryBrowser(new CategoryQuery());
		
	}
	
	protected static void p(String v) {
		System.out.println("SwtFactory:) " + v);
	}

}
