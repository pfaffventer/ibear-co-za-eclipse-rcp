package za.co.ibear.data.unit.purchaseorderline;

import za.co.ibear.property.model.PropertyModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "purchaseorderline")
public class PurchaseOrderLineModel extends PropertyModel {

	public String LOCK_FIELD = " set UnitSequence = UnitSequence";
	private int unitsequence = 0;
	private int elementsequence = 0;
	private String purchaseorderline = null;
	private String product = null;
	private String description = null;
	private float volume = 0;
	private float price = 0;
	private float total = 0;
	private String timecreated = null;

	public PurchaseOrderLineModel() {
		setUnitSequence(0);
		setElementSequence(0);
		setPurchaseOrderLine("");
		setProduct("");
		setDescription("");
		setTimeCreated("2014-01-10 12:00:00");
	}

	public PurchaseOrderLineModel(boolean blank) {
		setUnitSequence(0);
		setElementSequence(0);
		setPurchaseOrderLine("");
		setProduct("");
		setDescription("");
		setTimeCreated(null);
	}

	public String keyString() {
		return " WHERE UnitSequence = " + getUnitSequence() + " AND ElementSequence = " + getElementSequence() + "";
	}

	public void setUnitSequence(int unitsequence) {
		firePropertyChange("unitsequence", this.unitsequence, this.unitsequence = unitsequence);
	}

	public int getUnitSequence() {
		return this.unitsequence;
	}

	public void setElementSequence(int elementsequence) {
		firePropertyChange("elementsequence", this.elementsequence, this.elementsequence = elementsequence);
	}

	public int getElementSequence() {
		return this.elementsequence;
	}

	public void setPurchaseOrderLine(String purchaseorderline) {
		firePropertyChange("purchaseorderline", this.purchaseorderline, this.purchaseorderline = purchaseorderline);
	}

	public String getPurchaseOrderLine() {
		return this.purchaseorderline;
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

	public void setVolume(float volume) {
		firePropertyChange("volume", this.volume, this.volume = volume);
	}

	public float getVolume() {
		return this.volume;
	}

	public void setPrice(float price) {
		firePropertyChange("price", this.price, this.price = price);
	}

	public float getPrice() {
		return this.price;
	}

	public void setTotal(float total) {
		firePropertyChange("total", this.total, this.total = total);
	}

	public float getTotal() {
		return this.total;
	}

	public void setTimeCreated(String timecreated) {
		firePropertyChange("timecreated", this.timecreated, this.timecreated = timecreated);
	}

	public String getTimeCreated() {
		return this.timecreated;
	}

	public String toInsertSql(int next) {
		setUnitSequence(next);
		return "insert into PurchaseOrderLine values ("
				+ " " +  getUnitSequence() + " ,"
				+ " " +  getElementSequence() + " ,"
				+ "'" +  getPurchaseOrderLine() + "',"
				+ "'" +  getProduct() + "',"
				+ "'" +  getDescription() + "',"
				+ " " +  getVolume() + " ,"
				+ " " +  getPrice() + " ,"
				+ " " +  getTotal() + " ,"
				+ "'" +  getTimeCreated() + "')";
	}

	public String toLockReadSql() {
		return "select * from PurchaseOrderLine" + keyString() + " FOR UPDATE NOWAIT";
	}

	public String toLockSql() {
		return "update PurchaseOrderLine"+ LOCK_FIELD + keyString();
	}

	public String toDeleteSql() {
		return "delete from PurchaseOrderLine" + keyString();
	}

	public String toSaveSql() {
		return "update PurchaseOrderLine set "
				+ "UnitSequence =  " +  getUnitSequence() + " ,"
				+ "ElementSequence =  " +  getElementSequence() + " ,"
				+ "PurchaseOrderLine =  '" +  getPurchaseOrderLine() + "',"
				+ "Product =  '" +  getProduct() + "',"
				+ "Description =  '" +  getDescription() + "',"
				+ "Volume =  " +  getVolume() + " ,"
				+ "Price =  " +  getPrice() + " ,"
				+ "Total =  " +  getTotal() + " ,"
				+ "TimeCreated = '" +  getTimeCreated() + "'"
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