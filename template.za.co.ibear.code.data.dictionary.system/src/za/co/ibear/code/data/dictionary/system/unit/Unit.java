package za.co.ibear.code.data.dictionary.system.unit;

import java.util.LinkedHashSet;

import java.util.Set;

import za.co.ibear.code.data.dictionary.system.edit.UnitEditor;
import za.co.ibear.code.data.dictionary.system.edit.UnitSelector;
import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.field.SystemFields;
import za.co.ibear.code.data.dictionary.system.index.Index;
import za.co.ibear.code.data.dictionary.system.index.IndexConstant;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.code.data.dictionary.system.sequence.Sequence;

public class Unit {

	public String SCHEMA = null;
	public String NAME = null;
	public String FULL_NAME = null;

	public String QUERY = null;

	public Set<Field> FIELD = new LinkedHashSet<Field>();
	public Set<Field> VISIBLE_COLUMN = new LinkedHashSet<Field>();
	public Set<String> BCOMBO = new LinkedHashSet<String>();
	public Set<UnitEditor> EDITOR = new LinkedHashSet<UnitEditor>();
	public Set<Index> INDEX = new LinkedHashSet<Index>();
	public Set<PrimaryKey> PRIMARY_KEY = new LinkedHashSet<PrimaryKey>();
	public Set<Sequence> ENTITY_SEQUENCE = new LinkedHashSet<Sequence>();
	
	public boolean IS_ELEMENT = false;
	public int PARENT_UNIT_SEQUENCE = 0;
	public UnitSelector ELEMENT_SELECTOR = null;
	
	public boolean IS_READ_ONLY = false;
	
	public Unit(String schema,boolean IS_READ_ONLY) {
		this.SCHEMA = schema;
		this.NAME = this.getClass().getSimpleName();
		this.FULL_NAME = this.SCHEMA + "." + this.getClass().getSimpleName() + "";
		this.FIELD.add(SystemFields.UNIT_SEQUENCE);
		this.IS_READ_ONLY = IS_READ_ONLY;
	}

	public Unit(String schema,boolean IS_ELEMENT,boolean IS_READ_ONLY) {
		this.SCHEMA = schema;
		this.IS_ELEMENT = IS_ELEMENT;
		this.NAME = this.getClass().getSimpleName();
		this.FULL_NAME = this.SCHEMA + "." + this.getClass().getSimpleName() + "";
		this.FIELD.add(SystemFields.UNIT_SEQUENCE);
		this.FIELD.add(SystemFields.ELEMENT_SEQUENCE);
		this.IS_READ_ONLY = IS_READ_ONLY;
	}

	public Field getField(String name) {
		for(Field field:FIELD) {
			if(field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}
	
	public void unitIndex() {
		INDEX.add(new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.UNIT_SEQUENCE));
	}
	
	public void elementIndex() {
		Index index = new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.UNIT_SEQUENCE);
		index.FIELD.add(SystemFields.ELEMENT_SEQUENCE);
		INDEX.add(index);
	}

	public void elementKey() {
		PrimaryKey primaryKey = new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"01",SystemFields.UNIT_SEQUENCE);
		primaryKey.FIELD.add(SystemFields.ELEMENT_SEQUENCE);
		PRIMARY_KEY.add(primaryKey);
	}
}
