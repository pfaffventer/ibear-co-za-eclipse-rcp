package za.co.ibear.code.data.dictionary.system.field;

import java.util.HashMap;
import java.util.Map;

public class JavaType {

	private Map<String,String> TYPE_MAP = new HashMap<String,String>();
	
	public JavaType() {
		
		TYPE_MAP.put(TypeConstant.CHAR,"String null");
		TYPE_MAP.put(TypeConstant.VARCHAR,"String null");
		TYPE_MAP.put(TypeConstant.INT,"int 0");
		TYPE_MAP.put(TypeConstant.BOOLEAN,"boolean false");
		TYPE_MAP.put(TypeConstant.DATE,"Date null java.util.Date");
		TYPE_MAP.put(TypeConstant.TIMESTAMP,"String null java.sql.String>java.util.Date");
		TYPE_MAP.put(TypeConstant.FLOAT,"float 0");

	}

	public String mapType(String type) {
		return TYPE_MAP.get(type).split(" ")[0];
	}

	public String mapDefault(String type) {
		return TYPE_MAP.get(type).split(" ")[1];
	}

	public String mapImport(String type) {
		if(TYPE_MAP.get(type).split(" ").length>2) {
			return TYPE_MAP.get(type).split(" ")[2];
		}
		return null; 
	}
	
	public  boolean isQuoteType(String type) {
		if(type.equals(TypeConstant.FLOAT)) {
			return false;
		}
		if(type.equals(TypeConstant.INT)) {
			return false;
		}
		return true;
	}

}
