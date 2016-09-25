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

public class Product extends Unit {

	public Product() {
		super(SchemaConstant.STOCK,false);

		FIELD.add(SystemFields.PRODUCT);
		FIELD.add(SystemFields.DESCRIPTION);
		FIELD.add(SystemFields.PRODUCT_CATEGORY);
		FIELD.add(SystemFields.TIME_CREATED);
		FIELD.add(SystemFields.VOLUME);
		FIELD.add(SystemFields.USER);

		VISIBLE_COLUMN.add(SystemFields.PRODUCT);
		VISIBLE_COLUMN.add(SystemFields.DESCRIPTION);
		VISIBLE_COLUMN.add(SystemFields.PRODUCT_CATEGORY);
		VISIBLE_COLUMN.add(SystemFields.TIME_CREATED);
		VISIBLE_COLUMN.add(SystemFields.USER);

		QUERY = SystemFields.STOCK_QUERY.getName();
		
		BCOMBO.add(SystemFields.PRODUCT.getName() + ">>" + BComboConstant.QUERY + ">>" + SystemFields.STOCK_QUERY.getName() + ">>" + SystemFields.STOCK_QUERY.getDescription());

		BCOMBO.add(SystemFields.PRODUCT.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.DESCRIPTION.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.PRODUCT_CATEGORY.getName() + ">>" + BComboConstant.UNIT_BROWSE);
		BCOMBO.add(SystemFields.TIME_CREATED.getName() + ">>" + BComboConstant.DATE);
		BCOMBO.add(SystemFields.USER.getName() + ">>" + BComboConstant.MULTI);

		EDITOR.add(new UnitEditor(null,SystemFields.PRODUCT,EditConstant.TEXT,false));
		EDITOR.add(new UnitEditor(null,SystemFields.DESCRIPTION,EditConstant.TEXT,false));

		//FIXME BCOMBO_SINGLE syntax
//		EDITOR.add(new Editor(new ProductCategory(),SystemFields.PRODUCT_CATEGORY,EditConstant.BCOMBO_SINGLE,false));
		
		//FIXME UNIT_EDIT syntax
		Set<String> returnSet = new TreeSet<String>();
		returnSet.add(SystemFields.PRODUCT_CATEGORY.getName());
		EDITOR.add(new UnitEditor(new ProductCategory(),SystemFields.PRODUCT_CATEGORY,EditConstant.UNIT_EDIT,false,returnSet));

		//FIXME QUERY syntax
//		Set<String> returnSet = new TreeSet<String>();
//		returnSet.add(SystemFields.PRODUCT_CATEGORY.getName());
//		EDITOR.add(new UnitEditor(new CategoryQuery(),SystemFields.CATEGORY_QUERY,EditConstant.QUERY,false,returnSet));

		EDITOR.add(new UnitEditor(null,SystemFields.TIME_CREATED,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.VOLUME,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.USER,EditConstant.TEXT,true));

		PRIMARY_KEY.add(new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.PRODUCT));

		ENTITY_SEQUENCE.add(new Sequence(SystemFields.PRODUCT.getName(),15));

		INDEX.add(new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.PRODUCT));

		unitIndex();
	}

}
