package za.co.ibear.data.unit.purchaseorder;

import za.co.ibear.property.model.PropertyModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "purchaseorder")
public class PurchaseOrderModel extends PropertyModel {

	public String LOCK_FIELD = " set UnitSequence = UnitSequence";
	private int unitsequence = 0;
	private String purchaseorder = null;
	private String supplier = null;
	private String name = null;
	private String user = null;
	private String timecreated = null;

	public PurchaseOrderModel() {
		setUnitSequence(0);
		setPurchaseOrder("");
		setSupplier("");
		setName("");
		setUser("SYSTEM");
		setTimeCreated("2014-01-10 12:00:00");
	}

	public PurchaseOrderModel(boolean blank) {
		setUnitSequence(0);
		setPurchaseOrder("");
		setSupplier("");
		setName("");
		setUser("");
		setTimeCreated(null);
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

	public void setPurchaseOrder(String purchaseorder) {
		firePropertyChange("purchaseorder", this.purchaseorder, this.purchaseorder = purchaseorder);
	}

	public String getPurchaseOrder() {
		return this.purchaseorder;
	}

	public void setSupplier(String supplier) {
		firePropertyChange("supplier", this.supplier, this.supplier = supplier);
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

	public String getName() {
		return this.name;
	}

	public void setUser(String user) {
		firePropertyChange("user", this.user, this.user = user);
	}

	public String getUser() {
		return this.user;
	}

	public void setTimeCreated(String timecreated) {
		firePropertyChange("timecreated", this.timecreated, this.timecreated = timecreated);
	}

	public String getTimeCreated() {
		return this.timecreated;
	}

	public String toInsertSql(int next) {
		setUnitSequence(next);
		setPurchaseOrder(String.format("%010d",next));
		return "insert into PurchaseOrder values ("
				+ " " +  getUnitSequence() + " ,"
				+ "'" +  getPurchaseOrder() + "',"
				+ "'" +  getSupplier() + "',"
				+ "'" +  getName() + "',"
				+ "'" +  getUser() + "',"
				+ "'" +  getTimeCreated() + "')";
	}

	public String toLockReadSql() {
		return "select * from PurchaseOrder" + keyString() + " FOR UPDATE NOWAIT";
	}

	public String toLockSql() {
		return "update PurchaseOrder"+ LOCK_FIELD + keyString();
	}

	public String toDeleteSql() {
		return "delete from PurchaseOrder" + keyString();
	}

	public String toSaveSql() {
		return "update PurchaseOrder set "
				+ "UnitSequence =  " +  getUnitSequence() + " ,"
				+ "PurchaseOrder =  '" +  getPurchaseOrder() + "',"
				+ "Supplier =  '" +  getSupplier() + "',"
				+ "Name =  '" +  getName() + "',"
				+ "User =  '" +  getUser() + "',"
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