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

public class ProductCategory extends Unit {

	public ProductCategory() {
		super(SchemaConstant.STOCK,false);
		
		FIELD.add(SystemFields.PRODUCT_CATEGORY);
		FIELD.add(SystemFields.DESCRIPTION);
		
		VISIBLE_COLUMN.add(SystemFields.PRODUCT_CATEGORY);
		VISIBLE_COLUMN.add(SystemFields.DESCRIPTION);
		
		BCOMBO.add(SystemFields.PRODUCT_CATEGORY.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.DESCRIPTION.getName() + ">>" + BComboConstant.MULTI);

		EDITOR.add(new UnitEditor(null,SystemFields.PRODUCT_CATEGORY,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.DESCRIPTION,EditConstant.TEXT,false));

		PRIMARY_KEY.add(new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.PRODUCT_CATEGORY));

		ENTITY_SEQUENCE.add(new Sequence(SystemFields.PRODUCT_CATEGORY.getName(),7));

		INDEX.add(new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.PRODUCT_CATEGORY));

		unitIndex();
	}

}
