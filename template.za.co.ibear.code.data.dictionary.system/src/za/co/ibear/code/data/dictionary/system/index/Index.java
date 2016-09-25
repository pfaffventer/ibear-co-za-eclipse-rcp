package za.co.ibear.code.data.dictionary.system.index;

import java.util.LinkedHashSet;
import java.util.Set;

import za.co.ibear.code.data.dictionary.system.field.Field;

public class Index {

	public String NAME = null;
	public String ENTITY = null;
	public String DISTINCT = null;
	public String SEQUENCE = null;
	public Set<Field> FIELD = new LinkedHashSet<Field>();

	public Index(String name,String unit, String distinct, String sequence,Field field) {
		super();
		NAME = "i_" + name + "_" + sequence;
		ENTITY = unit;
		DISTINCT = distinct;
		SEQUENCE = sequence;
		FIELD.add(field);
	}

	public String fieldToString() {
		String re  = "";
		for(Field f:FIELD) {
			re = re + "" + f.getName() + ",";
		}
		return re.substring(0,re.length()-1);
	}

	@Override
	public String toString() {
		return "CREATE " + DISTINCT + " INDEX " + NAME + " ON " + ENTITY + " (" + fieldToString() + ")";
	}

}
