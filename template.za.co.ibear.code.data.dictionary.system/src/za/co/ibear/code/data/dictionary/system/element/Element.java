package za.co.ibear.code.data.dictionary.system.element;

import java.util.LinkedHashSet;

import java.util.Set;

import za.co.ibear.code.data.dictionary.system.edit.ElementEditor;
import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.field.SystemFields;
import za.co.ibear.code.data.dictionary.system.index.Index;
import za.co.ibear.code.data.dictionary.system.index.IndexConstant;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.code.data.dictionary.system.sequence.Sequence;

public class Element {

	public String SCHEMA = null;
	public String NAME = null;
	public String FULL_NAME = null;
	public Set<Field> FIELD = new LinkedHashSet<Field>();
	public Set<Field> VISIBLE_COLUMN = new LinkedHashSet<Field>();
	public Set<String> BCOMBO = new LinkedHashSet<String>();
	public Set<ElementEditor> EDITOR = new LinkedHashSet<ElementEditor>();
	public Set<Index> INDEX = new LinkedHashSet<Index>();
	public Set<PrimaryKey> PRIMARY_KEY = new LinkedHashSet<PrimaryKey>();
	public Set<Sequence> ENTITY_SEQUENCE = new LinkedHashSet<Sequence>();
	
	public Element(String schema) {
		this.SCHEMA = schema;
		this.NAME = this.getClass().getSimpleName();
		this.FULL_NAME = this.SCHEMA + "." + this.getClass().getSimpleName() + "";
		this.FIELD.add(SystemFields.UNIT_SEQUENCE);
		this.FIELD.add(SystemFields.ELEMENT_SEQUENCE);
	}
	
	public Field getField(String name) {
		for(Field field:FIELD) {
			if(field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}
	
	public void elementIndex() {
		Index sequenceIndex = new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.UNIT_SEQUENCE);
		sequenceIndex.FIELD.add(SystemFields.ELEMENT_SEQUENCE);
		INDEX.add(sequenceIndex);
	}

	public void elementKey() {
		PrimaryKey elementKey = new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.UNIT_SEQUENCE);
		elementKey.FIELD.add(SystemFields.ELEMENT_SEQUENCE);
		PRIMARY_KEY.add(elementKey);
	}
}
