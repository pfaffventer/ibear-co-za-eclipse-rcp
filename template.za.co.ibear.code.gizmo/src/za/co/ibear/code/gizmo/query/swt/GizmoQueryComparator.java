package za.co.ibear.code.gizmo.query.swt;

import za.co.ibear.code.data.dictionary.system.field.Field;

import za.co.ibear.code.data.dictionary.system.query.Query;
import za.co.ibear.code.gizmo.GizmoQuery;
import za.co.ibear.code.gizmo.unit.swt.GizmoUnitEditorConstant;

public class GizmoQueryComparator extends GizmoQuery {

	public GizmoQueryComparator(Query query) throws Exception {
		super(query,query.NAME + "Comparator",query.NAME + "Model",GizmoUnitEditorConstant.PACKAGE_PREFIX,GizmoUnitEditorConstant.PATH_PREFIX);

		/**
		 * Check for hard coded strings e.g. search and replace (P)(p)roduct.
		 * 
		 * " + unit.NAME.toLowerCase() + "
		 * " + unit.NAME + "
		 *  
		 */

		content = content + "import org.eclipse.jface.viewers.Viewer;\n";
		content = content + "import org.eclipse.jface.viewers.ViewerComparator;\n";
		content = content + "import org.eclipse.swt.SWT;\n";

		content = content + "import za.co.ibear.data.unit." + query.NAME.toLowerCase() + "." + query.NAME + "Model;\n";

		content = content + "public class " + query.NAME + "Comparator extends ViewerComparator {\n";

		content = content + "	private int propertyIndex;\n";
		content = content + "	private static final int DESCENDING = 1;\n";
		content = content + "	private int direction = DESCENDING;\n";

		content = content + "	public " + query.NAME + "Comparator() {\n";
		content = content + "		this.propertyIndex = 0;\n";
		content = content + "		direction = DESCENDING;\n";
		content = content + "	}\n";

		content = content + "	public int getDirection() {\n";
		content = content + "		return direction == 1 ? SWT.DOWN : SWT.UP;\n";
		content = content + "	}\n";

		content = content + "	public void setColumn(int column) {\n";
		content = content + "		if (column == this.propertyIndex) {\n";
		content = content + "			direction = 1 - direction;\n";
		content = content + "		} else {\n";
		content = content + "			this.propertyIndex = column;\n";
		content = content + "			direction = DESCENDING;\n";
		content = content + "		}\n";
		content = content + "	}\n";

		content = content + "	@Override\n";
		content = content + "	public int compare(Viewer viewer, Object e1, Object e2) {\n";
		content = content + "		" + query.NAME + "Model model1 = (" + query.NAME + "Model) e1;\n";
		content = content + "		" + query.NAME + "Model model2 = (" + query.NAME + "Model) e2;\n";
		content = content + "		int rc = 0;\n";
		content = content + "		switch (propertyIndex) {\n";
		
		int case_count = 0;
		for(Field field:query.VISIBLE_COLUMN) {
			content = content + "		case " + case_count + ":\n";
			if(field.getAnsiDbType().equals("INT")||field.getAnsiDbType().equals("FLOAT")) {
				content = content + "			if(model1.get" + field.getName() + "()>model2.get" + field.getName() + "()) {\n";
				content = content + "				rc = 1;\n";
				content = content + "			} else {\n";
				content = content + "				rc = -1;\n";
				content = content + "			}\n";
				content = content + "			break;\n";
				case_count++;
				continue;
			}
			content = content + "			rc = model1.get" + field.getName() + "().compareTo(model2.get" + field.getName() + "());\n";	
			content = content + "			break;\n";
			case_count++;
		}

		content = content + "		default:\n";
		content = content + "			rc = 0;\n";
		content = content + "		}\n";
		content = content + "		if (direction == DESCENDING) {\n";
		content = content + "			rc = -rc;\n";
		content = content + "		}\n";
		content = content + "		return rc;\n";
		content = content + "	}\n";

		content = content + "	protected void p(String v) {\n";
		content = content + "		System.out.println(this.getClass().getSimpleName() + :) + v);\n";
		content = content + "	}\n";

		content = content + "}\n";

		createFile();		
	}

}
