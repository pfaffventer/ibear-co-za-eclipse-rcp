
package za.co.ibear.code.data.dictionary.definition.query;

import za.co.ibear.code.data.dictionary.system.field.SystemFields;
import za.co.ibear.code.data.dictionary.system.query.Query;
import za.co.ibear.swt.control.combo.BComboConstant;


public class StockQuery extends Query {

	public StockQuery() {
		super("select * from Product order by Product");

		FIELD.add(SystemFields.PRODUCT);
		FIELD.add(SystemFields.DESCRIPTION);
		
		VISIBLE_COLUMN.add(SystemFields.PRODUCT);
		VISIBLE_COLUMN.add(SystemFields.DESCRIPTION);
		
		BCOMBO.add(SystemFields.PRODUCT.getName() + ">>" + BComboConstant.MULTI);
		BCOMBO.add(SystemFields.DESCRIPTION.getName() + ">>" + BComboConstant.MULTI);

	}

	
	

}
