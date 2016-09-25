# Database application with Eclipse RCP

Demo application: 
![alt text](https://github.com/pfaffventer/ibear-co-za-eclipse-rcp/blob/master/readme.resource/overview_000.PNG "Demo application screenshot")

A Windows 10 build of the demo app can be downloaded [here](https://github.com/pfaffventer/ibear-co-za-eclipse-rcp-demo-build-windows.git). The purpose of the application is to get a feel for the SWT components and modified data does not persist when the application terminates, I converted a PostgreSQL based application to SQLite in memory, to simplify deployment for demo purposes.

Unzip the demo.zip file to a temporary folder and double click the ‘app’ shortcut to start the application, the program works with Java 1.8 on windows 10, I have not tried it on anything else.

#About
I have been using Eclipse for java development since 2003, this project is a collection of plugins used for writhing database applications.

The idea was to use a configuration class to define a domain object field once and then reuse this field throughout the system, for example;
```Java
public static Field PRODUCT = new Field("Product",TypeConstant.VARCHAR + " (30)","width=120>default= ");
```

The fields are then joined in an application configuration class for example;

```Java
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

		Set<String> returnSet = new TreeSet<String>();
		returnSet.add(SystemFields.PRODUCT_CATEGORY.getName());
		EDITOR.add(new UnitEditor(new ProductCategory(),SystemFields.PRODUCT_CATEGORY,EditConstant.UNIT_EDIT,false,returnSet));

		EDITOR.add(new UnitEditor(null,SystemFields.TIME_CREATED,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.VOLUME,EditConstant.TEXT,true));
		EDITOR.add(new UnitEditor(null,SystemFields.USER,EditConstant.TEXT,true));

		PRIMARY_KEY.add(new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.PRODUCT));

		ENTITY_SEQUENCE.add(new Sequence(SystemFields.PRODUCT.getName(),15));

		INDEX.add(new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.PRODUCT));

		unitIndex();
	}

}
```

From this a database schema as well as and Eclipse RCP application is generated, the application can then be extended to add business logic and validation.

The first tab of the demo application represents a trade item or "product" master file, the second a supplier master file and the third demonstrates how these can be used to create a purchase order application. 

#User Interface
Each master file windows consists of a sash with tree panels, a filter panel on the left, a data grid in the centre and an editor panel on the right for entering data related to a record selected in the grid. 

The __filter panel__ consists of a list of components used to find records in the data grid, including;

![alt text](https://github.com/pfaffventer/ibear-co-za-eclipse-rcp/blob/master/readme.resource/filter_panel_000.PNG "Filter panel")

* The "Search" widget will filter for any field in the grid matching the text entered in the search field.

* A query widget opens a model similar to the main window, without the editor panel on the right, allow the user to filter and select one or more records for editing.

![alt text](https://github.com/pfaffventer/ibear-co-za-eclipse-rcp/blob/master/readme.resource/filter_panel_001.PNG "Filter panel")

* Column filter

![alt text](https://github.com/pfaffventer/ibear-co-za-eclipse-rcp/blob/master/readme.resource/filter_panel_002.PNG "Filter panel")

* Date range
![alt text](https://github.com/pfaffventer/ibear-co-za-eclipse-rcp/blob/master/readme.resource/filter_panel_003.PNG "Filter panel")






