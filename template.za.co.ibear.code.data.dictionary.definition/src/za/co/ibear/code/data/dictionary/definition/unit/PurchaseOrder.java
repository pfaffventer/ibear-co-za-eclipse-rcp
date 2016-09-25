package za.co.ibear.code.data.dictionary.definition.unit;

import java.util.Set;
import java.util.TreeSet;

import za.co.ibear.code.data.dictionary.system.edit.EditConstant;
import za.co.ibear.code.data.dictionary.system.edit.UnitEditor;
import za.co.ibear.code.data.dictionary.system.field.SystemFields;
import za.co.ibear.code.data.dictionary.system.index.Index;
import za.co.ibear.code.data.dictionary.system.index.IndexConstant;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.code.data.dictionary.system.schema.SchemaConstant;
import za.co.ibear.code.data.dictionary.system.sequence.Sequence;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.swt.control.combo.BComboConstant;

public class PurchaseOrder extends Unit {

	public PurchaseOrder() {
		super(SchemaConstant.CREDITORS,false);
		
		FIELD.add(SystemFields.PURCHASE_ORDER);
		FIELD.add(SystemFields.SUPPLIER);
		FIELD.add(SystemFields.NAME);
		FIELD.add(SystemFields.USER);
		FIELD.add(SystemFields.TIME_CREATED);

		VISIBLE_COLUMN.add(SystemFields.PURCHASE_ORDER);
		VISIBLE_COLUMN.add(SystemFields.SUPPLIER);
		VISIBLE_COLUMN.add(SystemFields.NAME);
		VISIBLE_COLUMN.add(SystemFields.USER);
		VISIBLE_COLUMN.add(SystemFields.TIME_CREATED);

		BCOMBO.add(SystemFields.PURCHASE_ORDER.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.SUPPLIER.getName() + ">>" + BComboConstant.UNIT_BROWSE);
		BCOMBO.add(SystemFields.NAME.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.USER.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.TIME_CREATED.getName() + ">>" + BComboConstant.DATE);

		EDITOR.add(new UnitEditor(null,SystemFields.PURCHASE_ORDER,EditConstant.TEXT,false));

		//FIXME debug returnSet
		Set<String> returnSet = new TreeSet<String>();
		returnSet.add(SystemFields.SUPPLIER.getName());
		returnSet.add(SystemFields.NAME.getName());
		EDITOR.add(new UnitEditor(new Supplier(),SystemFields.SUPPLIER,EditConstant.UNIT_EDIT,false,returnSet));
		EDITOR.add(new UnitEditor(null,SystemFields.NAME,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.USER,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.TIME_CREATED,EditConstant.TEXT,true));

		PRIMARY_KEY.add(new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.PURCHASE_ORDER));

		ENTITY_SEQUENCE.add(new Sequence(SystemFields.PURCHASE_ORDER.getName(),1));

		INDEX.add(new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.PURCHASE_ORDER));

		unitIndex();
	}

}
