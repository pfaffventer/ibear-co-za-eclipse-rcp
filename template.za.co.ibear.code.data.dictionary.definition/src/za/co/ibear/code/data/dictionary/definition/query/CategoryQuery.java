package za.co.ibear.code.data.dictionary.definition.query;

import za.co.ibear.code.data.dictionary.system.field.SystemFields;
import za.co.ibear.code.data.dictionary.system.query.Query;
import za.co.ibear.swt.control.combo.BComboConstant;

public class CategoryQuery extends Query {
	
	
	public CategoryQuery() {
		super("select *,'name' as Name from ProductCategory order by ProductCategory");

		FIELD.add(SystemFields.PRODUCT_CATEGORY);
		FIELD.add(SystemFields.DESCRIPTION);
		FIELD.add(SystemFields.NAME);

		VISIBLE_COLUMN.add(SystemFields.PRODUCT_CATEGORY);
		VISIBLE_COLUMN.add(SystemFields.DESCRIPTION);
		VISIBLE_COLUMN.add(SystemFields.NAME);

		BCOMBO.add(SystemFields.PRODUCT_CATEGORY.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.DESCRIPTION.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.NAME.getName() + ">>" + BComboConstant.MULTI);

	}

}
