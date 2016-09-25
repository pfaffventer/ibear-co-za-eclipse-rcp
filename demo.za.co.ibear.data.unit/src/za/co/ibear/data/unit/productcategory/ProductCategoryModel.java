package za.co.ibear.data.unit.productcategory;

import za.co.ibear.property.model.PropertyModel;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "productcategory")
public class ProductCategoryModel extends PropertyModel {

	public String LOCK_FIELD = " set UnitSequence = UnitSequence";
	private int unitsequence = 0;
	private String productcategory = null;
	private String description = null;

	public ProductCategoryModel() {
		setUnitSequence(0);
		setProductCategory("");
		setDescription("");
	}

	public ProductCategoryModel(boolean blank) {
		setUnitSequence(0);
		setProductCategory("");
		setDescription("");
	}

	public String keyString() {
		return " WHERE UnitSequence = " + getUnitSequence() + "";
	}

	public void setUnitSequence(int unitsequence) {
		firePropertyChange("unitsequence", this.unitsequence, this.unitsequence = unitsequence);
	}

	public int getUnitSequence() {
		return this.unitsequence;
	}

	public void setProductCategory(String productcategory) {
		firePropertyChange("productcategory", this.productcategory, this.productcategory = productcategory);
	}

	public String getProductCategory() {
		return this.productcategory;
	}

	public void setDescription(String description) {
		firePropertyChange("description", this.description, this.description = description);
	}

	public String getDescription() {
		return this.description;
	}

	public String toInsertSql(int next) {
		setUnitSequence(next);
		return "insert into ProductCategory values ("
				+ " " +  getUnitSequence() + " ,"
				+ "'" +  getProductCategory() + "',"
				+ "'" +  getDescription() + "')";
	}

	public String toLockReadSql() {
		return "select * from ProductCategory" + keyString() + " FOR UPDATE NOWAIT";
	}

	public String toLockSql() {
		return "update ProductCategory"+ LOCK_FIELD + keyString();
	}

	public String toDeleteSql() {
		return "delete from ProductCategory" + keyString();
	}

	public String toSaveSql() {
		return "update ProductCategory set "
				+ "UnitSequence =  " +  getUnitSequence() + " ,"
				+ "ProductCategory =  '" +  getProductCategory() + "',"
				+ "Description =  '" +  getDescription() + "'"
				+ keyString();
	}


}