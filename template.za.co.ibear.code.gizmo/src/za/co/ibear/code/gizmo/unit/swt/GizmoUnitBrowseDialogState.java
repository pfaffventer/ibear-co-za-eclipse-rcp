package za.co.ibear.code.gizmo.unit.swt;

import za.co.ibear.code.gizmo.GizmoUnitBrowse;

public class GizmoUnitBrowseDialogState extends GizmoUnitBrowse {

	public GizmoUnitBrowseDialogState() throws Exception {
		super("UnitBrowseDialogState",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);

		content = content + "import java.util.ArrayList;\n";
		content = content + "import java.util.Date;\n";
		content = content + "import java.util.HashMap;\n";
		content = content + "import java.util.List;\n";
		content = content + "import java.util.Map;\n";
		content = content + "import java.util.Set;\n";
		content = content + "import java.util.TreeSet;\n";
		content = content + "public class UnitBrowseDialogState {\n";
		content = content + "	private String unitName;\n";
		content = content + "	@SuppressWarnings(rawtypes)\n";
		content = content + "	private List data = new ArrayList();\n";
		content = content + "	@SuppressWarnings(rawtypes)\n";
		content = content + "	private List filterData = new ArrayList();\n";
		content = content + "	\n";
		content = content + "	private int[] savedSelectionIndex = {};\n";
		content = content + "	\n";
		content = content + "	private Map<String,TreeSet<String>> selection = new HashMap<String,TreeSet<String>>();\n";
		content = content + "	\n";
		content = content + "	private Map<String, List<String>> savedFilterData = new HashMap<String, List<String>>();\n";
		content = content + "	private Map<String, Set<String>> savedFilterSelection = new HashMap<String, Set<String>>();\n";
		content = content + "	private Map<String, Map<String, Date>> savedDateSelection = new HashMap<String, Map<String, Date>>();\n";
		content = content + "	\n";
		content = content + "	public UnitBrowseDialogState() {\n";
		content = content + "		super();\n";
		content = content + "	}\n";
		content = content + "	public UnitBrowseDialogState(String unitName,\n";
		content = content + "			int[] savedSelectionIndex,\n";
		content = content + "			Map<String, List<String>> savedFilterData,\n";
		content = content + "			Map<String, Set<String>> savedFilterSelection,\n";
		content = content + "			Map<String, Map<String, Date>> savedDateSelection) {\n";
		content = content + "		super();\n";
		content = content + "		this.unitName = unitName;\n";
		content = content + "		this.setSavedSelectionIndex(savedSelectionIndex);\n";
		content = content + "		this.savedFilterData = savedFilterData;\n";
		content = content + "		this.savedFilterSelection = savedFilterSelection;\n";
		content = content + "		this.savedDateSelection = savedDateSelection;\n";
		content = content + "	}\n";
		content = content + "	public String getUnitName() {\n";
		content = content + "		return unitName;\n";
		content = content + "	}\n";
		content = content + "	public void setUnitName(String columnName) {\n";
		content = content + "		this.unitName = columnName;\n";
		content = content + "	}\n";
		content = content + "	public int[] getSavedSelectionIndex() {\n";
		content = content + "		return savedSelectionIndex;\n";
		content = content + "	}\n";
		content = content + "	public void setSavedSelectionIndex(int[] savedSelectionIndex) {\n";
		content = content + "		this.savedSelectionIndex = savedSelectionIndex;\n";
		content = content + "	}\n";
		content = content + "	public Map<String, List<String>> getSavedFilterData() {\n";
		content = content + "		return savedFilterData;\n";
		content = content + "	}\n";
		content = content + "	public void setSavedFilterData(Map<String, List<String>> savedFilterData) {\n";
		content = content + "		this.savedFilterData = savedFilterData;\n";
		content = content + "	}\n";
		content = content + "	public Map<String, Set<String>> getSavedFilterSelection() {\n";
		content = content + "		return savedFilterSelection;\n";
		content = content + "	}\n";
		content = content + "	public void setSavedFilterSelection(\n";
		content = content + "			Map<String, Set<String>> savedFilterSelection) {\n";
		content = content + "		this.savedFilterSelection = savedFilterSelection;\n";
		content = content + "	}\n";
		content = content + "	public Map<String, Map<String, Date>> getSavedDateSelection() {\n";
		content = content + "		return savedDateSelection;\n";
		content = content + "	}\n";
		content = content + "	public void setSavedDateSelection(\n";
		content = content + "			Map<String, Map<String, Date>> savedDateSelection) {\n";
		content = content + "		this.savedDateSelection = savedDateSelection;\n";
		content = content + "	}\n";
		content = content + "	@SuppressWarnings(rawtypes)\n";
		content = content + "	public List getData() {\n";
		content = content + "		return data;\n";
		content = content + "	}\n";
		content = content + "	@SuppressWarnings(rawtypes)\n";
		content = content + "	public void setData(List savedData) {\n";
		content = content + "		this.data = savedData;\n";
		content = content + "	}\n";
		content = content + "	@SuppressWarnings(rawtypes)\n";
		content = content + "	public List getFilterData() {\n";
		content = content + "		return filterData;\n";
		content = content + "	}\n";
		content = content + "	@SuppressWarnings(rawtypes)\n";
		content = content + "	public void setFilterData(List filterData) {\n";
		content = content + "		this.filterData = filterData;\n";
		content = content + "	}\n";
		content = content + "	public Map<String,TreeSet<String>> getSelection() {\n";
		content = content + "		return selection;\n";
		content = content + "	}\n";
		content = content + "	public void setSelection(Map<String,TreeSet<String>> selection) {\n";
		content = content + "		this.selection = selection;\n";
		content = content + "	}\n";
		content = content + "}\n";

		createFile();		
	}
	
	@Override
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":) " + v);
	}

	
}
