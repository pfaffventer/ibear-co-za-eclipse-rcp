package za.co.ibear.code.data.dictionary.system.field;

import java.util.HashMap;
import java.util.Map;

public class Field {

	private String name = null;
	private String description = null;
	private String ansiDataType = null;

	private Map<String,String> property = new HashMap<String,String>();
	
	public Field(String name, String description, String ansiDbType,String initProperty) {
		set(name,ansiDbType,initProperty);
		this.description = description;
	}

	public Field(String name, String ansiDbType,String initProperty) {
		set(name,ansiDbType,initProperty);
		this.description = name;
	}

	private void set(String name, String ansiDbType,String initProperty) {
		this.name = name;
		this.ansiDataType = ansiDbType;
		String[] props = initProperty.split(">");
		for(String p:props) {
			this.property.put(p.split("=")[0],p.split("=")[1]);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnsiDbType() {
		return ansiDataType.split(" ")[0];
	}

	public String getAnsiDbSize() {
		if(ansiDataType.split(" ").length>1) {
			return ansiDataType.split(" ")[1];	
		} else {
			return "";
		}
	}

	public void setAnsiDbType(String ansiDbType) {
		this.ansiDataType = ansiDbType;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", description=" + description + ", ansiDbType=" + ansiDataType + "]";
	}

	public String getProperty(String k) {
		return property.get(k);
	}

	public Map<String,String> getProperty() {
		return property;
	}

	public void setProperty(Map<String,String> property) {
		this.property.entrySet().addAll(property.entrySet());
	}

	public void putProperty(String k,String v) {
		this.property.put(k,v);
	}

	protected static void p(String v) {
		System.out.println("Field:) " + v);
	}

}
