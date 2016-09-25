package za.co.ibear.code.data.dictionary.definition.unit;

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

public class Supplier extends Unit {
	
	public Supplier() {
		super(SchemaConstant.CREDITORS,false);

		FIELD.add(SystemFields.SUPPLIER);
		FIELD.add(SystemFields.NAME);
		FIELD.add(SystemFields.ADDRESS1);
		FIELD.add(SystemFields.ADDRESS2);
		FIELD.add(SystemFields.ADDRESS3);
		FIELD.add(SystemFields.ADDRESS4);
		FIELD.add(SystemFields.ADDRESS5);
		FIELD.add(SystemFields.USER);
		FIELD.add(SystemFields.TIME_CREATED);

		VISIBLE_COLUMN.add(SystemFields.SUPPLIER);
		VISIBLE_COLUMN.add(SystemFields.NAME);
		VISIBLE_COLUMN.add(SystemFields.ADDRESS1);
		VISIBLE_COLUMN.add(SystemFields.ADDRESS2);
		VISIBLE_COLUMN.add(SystemFields.ADDRESS3);
		VISIBLE_COLUMN.add(SystemFields.ADDRESS4);
		VISIBLE_COLUMN.add(SystemFields.ADDRESS5);
		VISIBLE_COLUMN.add(SystemFields.USER);
		VISIBLE_COLUMN.add(SystemFields.TIME_CREATED);

		BCOMBO.add(SystemFields.SUPPLIER.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.NAME.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.ADDRESS1.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.ADDRESS2.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.ADDRESS3.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.ADDRESS4.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.ADDRESS5.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.USER.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.TIME_CREATED.getName() + ">>" + BComboConstant.DATE);

		EDITOR.add(new UnitEditor(null,SystemFields.SUPPLIER,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.NAME,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.ADDRESS1,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.ADDRESS2,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.ADDRESS3,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.ADDRESS4,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.ADDRESS5,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.USER,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.TIME_CREATED,EditConstant.TEXT,true));

		PRIMARY_KEY.add(new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.SUPPLIER));

		ENTITY_SEQUENCE.add(new Sequence(SystemFields.SUPPLIER.getName(),7));

		INDEX.add(new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.SUPPLIER));

		unitIndex();
	}

}
