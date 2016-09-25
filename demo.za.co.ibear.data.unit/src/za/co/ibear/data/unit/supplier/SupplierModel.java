package za.co.ibear.data.unit.supplier;

import za.co.ibear.property.model.PropertyModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "supplier")
public class SupplierModel extends PropertyModel {

	public String LOCK_FIELD = " set UnitSequence = UnitSequence";
	private int unitsequence = 0;
	private String supplier = null;
	private String name = null;
	private String address1 = null;
	private String address2 = null;
	private String address3 = null;
	private String address4 = null;
	private String address5 = null;
	private String user = null;
	private String timecreated = null;

	public SupplierModel() {
		setUnitSequence(0);
		setSupplier("");
		setName("");
		setAddress1("");
		setAddress2("");
		setAddress3("");
		setAddress4("");
		setAddress5("");
		setUser("SYSTEM");
		setTimeCreated("2014-01-10 12:00:00");
	}

	public SupplierModel(boolean blank) {
		setUnitSequence(0);
		setSupplier("");
		setName("");
		setAddress1("");
		setAddress2("");
		setAddress3("");
		setAddress4("");
		setAddress5("");
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

	public void setAddress1(String address1) {
		firePropertyChange("address1", this.address1, this.address1 = address1);
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress2(String address2) {
		firePropertyChange("address2", this.address2, this.address2 = address2);
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress3(String address3) {
		firePropertyChange("address3", this.address3, this.address3 = address3);
	}

	public String getAddress3() {
		return this.address3;
	}

	public void setAddress4(String address4) {
		firePropertyChange("address4", this.address4, this.address4 = address4);
	}

	public String getAddress4() {
		return this.address4;
	}

	public void setAddress5(String address5) {
		firePropertyChange("address5", this.address5, this.address5 = address5);
	}

	public String getAddress5() {
		return this.address5;
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
		setSupplier(String.format("%010d",next));
		return "insert into Supplier values ("
				+ " " +  getUnitSequence() + " ,"
				+ "'" +  getSupplier() + "',"
				+ "'" +  getName() + "',"
				+ "'" +  getAddress1() + "',"
				+ "'" +  getAddress2() + "',"
				+ "'" +  getAddress3() + "',"
				+ "'" +  getAddress4() + "',"
				+ "'" +  getAddress5() + "',"
				+ "'" +  getUser() + "',"
				+ "'" +  getTimeCreated() + "')";
	}

	public String toLockReadSql() {
		return "select * from Supplier" + keyString() + " FOR UPDATE NOWAIT";
	}

	public String toLockSql() {
		return "update Supplier"+ LOCK_FIELD + keyString();
	}

	public String toDeleteSql() {
		return "delete from Supplier" + keyString();
	}

	public String toSaveSql() {
		return "update Supplier set "
				+ "UnitSequence =  " +  getUnitSequence() + " ,"
				+ "Supplier =  '" +  getSupplier() + "',"
				+ "Name =  '" +  getName() + "',"
				+ "Address1 =  '" +  getAddress1() + "',"
				+ "Address2 =  '" +  getAddress2() + "',"
				+ "Address3 =  '" +  getAddress3() + "',"
				+ "Address4 =  '" +  getAddress4() + "',"
				+ "Address5 =  '" +  getAddress5() + "',"
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