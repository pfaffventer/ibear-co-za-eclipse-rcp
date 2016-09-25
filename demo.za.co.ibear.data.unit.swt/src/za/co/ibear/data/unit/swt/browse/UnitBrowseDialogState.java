package za.co.ibear.data.unit.swt.browse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
public class UnitBrowseDialogState {
	private String unitName;
	@SuppressWarnings("rawtypes")
	private List data = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filterData = new ArrayList();
	
	private int[] savedSelectionIndex = {};
	
	private Map<String,TreeSet<String>> selection = new HashMap<String,TreeSet<String>>();
	
	private Map<String, List<String>> savedFilterData = new HashMap<String, List<String>>();
	private Map<String, Set<String>> savedFilterSelection = new HashMap<String, Set<String>>();
	private Map<String, Map<String, Date>> savedDateSelection = new HashMap<String, Map<String, Date>>();
	
	public UnitBrowseDialogState() {
		super();
	}
	public UnitBrowseDialogState(String unitName,
			int[] savedSelectionIndex,
			Map<String, List<String>> savedFilterData,
			Map<String, Set<String>> savedFilterSelection,
			Map<String, Map<String, Date>> savedDateSelection) {
		super();
		this.unitName = unitName;
		this.setSavedSelectionIndex(savedSelectionIndex);
		this.savedFilterData = savedFilterData;
		this.savedFilterSelection = savedFilterSelection;
		this.savedDateSelection = savedDateSelection;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String columnName) {
		this.unitName = columnName;
	}
	public int[] getSavedSelectionIndex() {
		return savedSelectionIndex;
	}
	public void setSavedSelectionIndex(int[] savedSelectionIndex) {
		this.savedSelectionIndex = savedSelectionIndex;
	}
	public Map<String, List<String>> getSavedFilterData() {
		return savedFilterData;
	}
	public void setSavedFilterData(Map<String, List<String>> savedFilterData) {
		this.savedFilterData = savedFilterData;
	}
	public Map<String, Set<String>> getSavedFilterSelection() {
		return savedFilterSelection;
	}
	public void setSavedFilterSelection(
			Map<String, Set<String>> savedFilterSelection) {
		this.savedFilterSelection = savedFilterSelection;
	}
	public Map<String, Map<String, Date>> getSavedDateSelection() {
		return savedDateSelection;
	}
	public void setSavedDateSelection(
			Map<String, Map<String, Date>> savedDateSelection) {
		this.savedDateSelection = savedDateSelection;
	}
	@SuppressWarnings("rawtypes")
	public List getData() {
		return data;
	}
	@SuppressWarnings("rawtypes")
	public void setData(List savedData) {
		this.data = savedData;
	}
	@SuppressWarnings("rawtypes")
	public List getFilterData() {
		return filterData;
	}
	@SuppressWarnings("rawtypes")
	public void setFilterData(List filterData) {
		this.filterData = filterData;
	}
	public Map<String,TreeSet<String>> getSelection() {
		return selection;
	}
	public void setSelection(Map<String,TreeSet<String>> selection) {
		this.selection = selection;
	}
}
