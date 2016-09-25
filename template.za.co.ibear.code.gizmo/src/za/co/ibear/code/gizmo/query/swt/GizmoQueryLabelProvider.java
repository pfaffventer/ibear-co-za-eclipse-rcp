package za.co.ibear.code.gizmo.query.swt;

import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.query.Query;
import za.co.ibear.code.gizmo.GizmoQuery;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitEditorConstant;

public class GizmoQueryLabelProvider extends GizmoQuery {
	
	public GizmoQueryLabelProvider(Query query) throws Exception {
		super(query,query.NAME + "LabelProvider",query.NAME + "Model",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);

		content = content + "import org.eclipse.jface.viewers.ILabelProviderListener;\n";
		content = content + "import org.eclipse.jface.viewers.ITableLabelProvider;\n";
		content = content + "import org.eclipse.swt.graphics.Image;\n";

		content = content + "import za.co.ibear.data.unit." + query.NAME.toLowerCase() + "." + query.NAME + "Model;\n";

		content = content + "public class " + query.NAME + "LabelProvider implements ITableLabelProvider {\n";

		content = content + "	public Image getColumnImage(Object element, int columnIndex) {\n";
		content = content + "		return null;\n";
		content = content + "	}\n";

		content = content + "	public String getColumnText(Object element, int columnIndex) {\n";
		content = content + "		" + query.NAME + "Model " + query.NAME.toLowerCase() + " = (" + query.NAME + "Model) element;\n";
		content = content + "		switch (columnIndex) {\n";

		int case_count = 0;
		for(Field field:query.VISIBLE_COLUMN) {
			content = content + "\t\tcase " + case_count + ":\n";
			if(field.getAnsiDbType().equals("DATE")||field.getAnsiDbType().equals("TIMESTAMP")) {
				content = content + "\t\t\treturn String.valueOf(" + query.NAME.toLowerCase() + ".get" + field.getName() + "ToString());\n";
				case_count++;
				continue;
			}
			content = content + "\t\t\treturn String.valueOf(" + query.NAME.toLowerCase() + ".get" + field.getName() + "());\n";	
			case_count++;
		}

		content = content + "		}\n";
		content = content + "		return null;\n";
		content = content + "	}\n";

		content = content + "	public void dispose() {\n";
		content = content + "	}\n";

		content = content + "	public boolean isLabelProperty(Object element, String property) {\n";
		content = content + "		return false;\n";
		content = content + "	}\n";

		content = content + "	@Override\n";
		content = content + "	public void addListener(ILabelProviderListener listener) {\n";
		content = content + "	}\n";

		content = content + "	@Override\n";
		content = content + "	public void removeListener(ILabelProviderListener listener) {\n";
		content = content + "	}\n";

		content = content + "}\n";

		
		
		createFile();		
	}

}
