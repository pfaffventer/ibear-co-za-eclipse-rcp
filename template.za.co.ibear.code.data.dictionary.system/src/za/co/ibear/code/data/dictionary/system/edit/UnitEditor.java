package za.co.ibear.code.data.dictionary.system.edit;

import java.util.Set;

import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.query.Query;
import za.co.ibear.code.data.dictionary.system.unit.Unit;

public class UnitEditor {

	private Unit unit = null;
	private Query query = null;
	private Field field = null;
	private int editor = 0;
	private Set<String> returnSet = null;
	private boolean readOnly = false;
	private boolean selectQuery = false;
	
	public boolean isSelectQuery() {
		return selectQuery;
	}

	public void setSelectQuery(boolean selectQuery) {
		this.selectQuery = selectQuery;
	}

	public UnitEditor(Unit unit,Field field, int editor, boolean readOnly) {
		super();
		this.unit = unit;
		this.field = field;
		this.editor = editor;
		this.readOnly = readOnly;
	}

	public UnitEditor(Unit unit,Field field, int editor, boolean readOnly,Set<String> returnSet) {
		super();
		this.unit = unit;
		this.field = field;
		this.editor = editor;
		this.readOnly = readOnly;
		this.returnSet = returnSet;
	}

	public UnitEditor(Query query,Field field, int editor, boolean readOnly,Set<String> returnSet) {
		super();
		this.query = query;
		this.field = field;
		this.editor = editor;
		this.readOnly = readOnly;
		this.returnSet = returnSet;
		this.setSelectQuery(true);
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
		return "UnitEditor [field=" + field + ", editor=" + editor + "]";
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

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	protected void p(String v) {
		System.out.println(this.getClass().getName() + ":) "+ v);
	}

}
