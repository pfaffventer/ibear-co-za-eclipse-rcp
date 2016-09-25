package za.co.ibear.data.unit.product;

import za.co.ibear.property.model.PropertyModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
public class ProductModel extends PropertyModel {

	public String LOCK_FIELD = " set UnitSequence = UnitSequence";
	private int unitsequence = 0;
	private String product = null;
	private String description = null;
	private String productcategory = null;
	private String timecreated = null;
	private float volume = 0;
	private String user = null;

	public ProductModel() {
		setUnitSequence(0);
		setProduct("");
		setDescription("");
		setProductCategory("");
		setTimeCreated("2014-01-10 12:00:00");
		setUser("SYSTEM");
	}

	public ProductModel(boolean blank) {
		setUnitSequence(0);
		setProduct("");
		setDescription("");
		setProductCategory("");
		setTimeCreated(null);
		setUser("");
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

	public void setProduct(String product) {
		firePropertyChange("product", this.product, this.product = product);
	}

	public String getProduct() {
		return this.product;
	}

	public void setDescription(String description) {
		firePropertyChange("description", this.description, this.description = description);
	}

	public String getDescription() {
		return this.description;
	}

	public void setProductCategory(String productcategory) {
		firePropertyChange("productcategory", this.productcategory, this.productcategory = productcategory);
	}

	public String getProductCategory() {
		return this.productcategory;
	}

	public void setTimeCreated(String timecreated) {
		firePropertyChange("timecreated", this.timecreated, this.timecreated = timecreated);
	}

	public String getTimeCreated() {
		return this.timecreated;
	}

	public void setVolume(float volume) {
		firePropertyChange("volume", this.volume, this.volume = volume);
	}

	public float getVolume() {
		return this.volume;
	}

	public void setUser(String user) {
		firePropertyChange("user", this.user, this.user = user);
	}

	public String getUser() {
		return this.user;
	}

	public String toInsertSql(int next) {
		setUnitSequence(next);
		setProduct(String.format("%010d",next));
		return "insert into Product values ("
				+ " " + getUnitSequence() + " ,"
				+ "'" + getProduct() + "',"
				+ "'" + getDescription() + "',"
				+ "'" + getProductCategory() + "',"
				+ "'" + getTimeCreated() + "',"
				+ " " + getVolume() + " ,"
				+ "'" + getUser() + "')";
	}

	public String toLockReadSql() {
		return "select * from Product" + keyString() + " FOR UPDATE NOWAIT";
	}

	public String toLockSql() {
		return "update Product"+ LOCK_FIELD + keyString();
	}

	public String toDeleteSql() {
		return "delete from Product" + keyString();
	}

	public String toSaveSql() {
		return "update Product set "
				+ "UnitSequence =  " +  getUnitSequence() + " ,"
				+ "Product =  '" +  getProduct() + "',"
				+ "Description =  '" +  getDescription() + "',"
				+ "ProductCategory =  '" +  getProductCategory() + "',"
				+ "TimeCreated = '" +  getTimeCreated() + "',"
				+ "Volume =  " +  getVolume() + " ,"
				+ "User =  '" +  getUser() + "'"
				+ keyString();
	}

	public String getTimeCreatedToString() {
		String d = null;
		try {
			d = getTimeCreated();
		} catch (Exception e) {
		}
		return d;
	}

}