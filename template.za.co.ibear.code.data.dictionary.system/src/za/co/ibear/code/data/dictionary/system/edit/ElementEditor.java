package za.co.ibear.code.data.dictionary.system.edit;

import java.util.Set;

import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.unit.Unit;

public class ElementEditor {

	private Unit unit = null;
	private Field field = null;
	private int editor = 0;
	private Set<String> returnSet = null;
	private boolean readOnly = false;
	
	public ElementEditor(Unit unit,Field field, int editor, boolean readOnly) {
		super();
		this.unit = unit;
		this.field = field;
		this.editor = editor;
		this.readOnly = readOnly;
	}

	public ElementEditor(Unit unit,Field field, int editor, boolean readOnly,Set<String> returnSet) {
		super();
		this.unit = unit;
		this.field = field;
		this.editor = editor;
		this.readOnly = readOnly;
		this.returnSet = returnSet;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public int getEditor() {
		return editor;
	}

	public void setEditor(int editor) {
		this.editor = editor;
	}

	@Override
	public String toString() {
		return "Editor [field=" + field + ", editor=" + editor + "]";
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Set<String> getReturnSet() {
		return returnSet;
	}

	public void setReturnSet(Set<String> returnSet) {
		this.returnSet = returnSet;
	}


}
