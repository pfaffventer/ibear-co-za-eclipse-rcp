package za.co.ibear.code.data.dictionary.system.query;

import java.util.LinkedHashSet;
import java.util.Set;

import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;

public class Query {

	public String NAME = null;
	public String SQL = null;
	public Set<Field> FIELD = new LinkedHashSet<Field>();
	public Set<Field> VISIBLE_COLUMN = new LinkedHashSet<Field>();
	public Set<String> BCOMBO = new LinkedHashSet<String>();

	public Set<PrimaryKey> RETURN_KEY = new LinkedHashSet<PrimaryKey>();

	public Query(String SQL) {
		this.SQL = SQL;
		this.NAME = this.getClass().getSimpleName();
	}
	
	public Field getField(String name) {
		for(Field field:FIELD) {
			if(field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

}
