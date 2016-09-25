package za.co.ibear.code.data.dictionary.definition.unit;

import java.util.Set;
import java.util.TreeSet;

import za.co.ibear.code.data.dictionary.system.edit.EditConstant;
import za.co.ibear.code.data.dictionary.system.edit.UnitEditor;
import za.co.ibear.code.data.dictionary.system.edit.UnitSelector;
import za.co.ibear.code.data.dictionary.system.field.SystemFields;
import za.co.ibear.code.data.dictionary.system.schema.SchemaConstant;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.swt.control.combo.BComboConstant;

public class PurchaseOrderLine extends Unit {

	public PurchaseOrderLine() {
		super(SchemaConstant.CREDITORS,true,false);

		FIELD.add(SystemFields.PURCHASE_ORDER_LINE);
		FIELD.add(SystemFields.PRODUCT);
		FIELD.add(SystemFields.DESCRIPTION);
		FIELD.add(SystemFields.VOLUME);
		FIELD.add(SystemFields.PRICE);
		FIELD.add(SystemFields.TOTAL);
		FIELD.add(SystemFields.TIME_CREATED);

		VISIBLE_COLUMN.add(SystemFields.PRODUCT);
		VISIBLE_COLUMN.add(SystemFields.DESCRIPTION);
		VISIBLE_COLUMN.add(SystemFields.VOLUME);
		VISIBLE_COLUMN.add(SystemFields.PRICE);
		VISIBLE_COLUMN.add(SystemFields.TOTAL);

		BCOMBO.add(SystemFields.PRODUCT.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.DESCRIPTION.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.TIME_CREATED.getName() + ">>" + BComboConstant.DATE);

		Set<String> returnSet = new TreeSet<String>();
		returnSet.add(SystemFields.PRODUCT.getName());
		returnSet.add(SystemFields.DESCRIPTION.getName());

		ELEMENT_SELECTOR = new UnitSelector(new Product(),returnSet);
		
		EDITOR.add(new UnitEditor(new Product(),SystemFields.PRODUCT,EditConstant.UNIT_EDIT,false,returnSet));
		EDITOR.add(new UnitEditor(null,SystemFields.DESCRIPTION,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.VOLUME,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.PRICE,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.TOTAL,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.TIME_CREATED,EditConstant.TEXT,true));

		elementIndex();
		elementKey();
	}

}
