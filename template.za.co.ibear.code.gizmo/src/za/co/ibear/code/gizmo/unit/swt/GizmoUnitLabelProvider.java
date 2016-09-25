package za.co.ibear.code.gizmo.unit.swt;

import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.code.gizmo.GizmoUnit;

public class GizmoUnitLabelProvider extends GizmoUnit {
	
	public GizmoUnitLabelProvider(Unit unit) throws Exception {
		super(unit,unit.NAME + "LabelProvider",unit.NAME + "Model",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);
		content = content + "import org.eclipse.jface.viewers.ILabelProviderListener;\n";
		content = content + "import org.eclipse.jface.viewers.ITableLabelProvider;\n";
		content = content + "import org.eclipse.swt.graphics.Image;\n\n";
		content = content + "import za.co.ibear.data.unit." + unit.NAME.toLowerCase() + "." + modelName + ";\n\n";
		content = content + "public class " + className + " implements ITableLabelProvider {\n\n";
		content = content + "\tpublic Image getColumnImage(Object element, int columnIndex) {\n";
		content = content + "\t\treturn null;\n";
		content = content + "\t}\n\n";
		content = content + "\tpublic String getColumnText(Object element, int columnIndex) {\n";
		content = content + "\t\t" + modelName + " " + unit.NAME.toLowerCase() + " = (" + modelName + ") element;\n";
		content = content + "\t\tswitch (columnIndex) {\n";
		int case_count = 0;
		for(Field field:unit.VISIBLE_COLUMN) {
			content = content + "\t\tcase " + case_count + ":\n";
			if(field.getAnsiDbType().equals("DATE")||field.getAnsiDbType().equals("TIMESTAMP")) {
				content = content + "\t\t\treturn String.valueOf(" + unit.NAME.toLowerCase() + ".get" + field.getName() + "ToString());\n";
				case_count++;
				continue;
			}
			content = content + "\t\t\treturn String.valueOf(" + unit.NAME.toLowerCase() + ".get" + field.getName() + "());\n";	
			case_count++;
		}
		content = content + "\t\t}\n";
		content = content + "\t\treturn null;\n";
		content = content + "\t}\n\n";
		content = content + "\tpublic void dispose() {\n\t}\n\n";
		content = content + "\tpublic boolean isLabelProperty(Object element, String property) {\n\t\treturn false;\n\t}\n\n";
		content = content + "\t@Override\n";
		content = content + "\tpublic void addListener(ILabelProviderListener listener) {\n\t}\n\n";
		content = content + "\t@Override\n";
		content = content + "\tpublic void removeListener(ILabelProviderListener listener) {\n\t}\n\n";
		content = content + "}";
		createFile();		
	}

}
