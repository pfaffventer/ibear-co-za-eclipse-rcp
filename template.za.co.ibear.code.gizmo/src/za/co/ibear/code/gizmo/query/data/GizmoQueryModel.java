package za.co.ibear.code.gizmo.query.data;

import java.util.LinkedHashSet;
import java.util.Set;

import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.field.Getter;
import za.co.ibear.code.data.dictionary.system.query.Query;
import za.co.ibear.code.gizmo.GizmoQuery;
import za.co.ibear.code.gizmo.unit.data.GizmoUnitDataConstant;
import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;

public class GizmoQueryModel extends GizmoQuery {

	private Set<String> imports = new LinkedHashSet<String>();
	private Set<Getter> getters = new LinkedHashSet<Getter>();

	public GizmoQueryModel(Query query) throws Exception {
		super(query,query.NAME + "Model",query.NAME + "Model",GizmoUnitDataConstant.PACKAGE_PREFIX,GizmoUnitDataConstant.PATH_PREFIX);

		new GizmoQueryLink(query);

		content = content + "import za.co.ibear.property.model.PropertyModel;\n\n";
		content = content + "import javax.xml.bind.annotation.XmlRootElement;\n";
		for(Field field:query.FIELD) {
			if(typeMap.mapType(field.getAnsiDbType()).equals("Date")) {
				content = content + "import java.text.SimpleDateFormat;\n";
			}
		}
		content = content + "\n";
		for(Field field:query.FIELD) {
			String i = typeMap.mapImport(field.getAnsiDbType());
			if(!(i==null)) {
				String[] ip = i.split(">");
				for(String v:ip) {
					imports.add(v);
				}
			}
		}
		for(String i:imports) {
			content = content + "import " + i + ";\n";
		}
		content = content + "\n";
		content = content + "@XmlRootElement(name = " + query.NAME.toLowerCase() + ")\n";
		content = content + "public class " + query.NAME + "Model extends PropertyModel {\n\n";

		String eName;
		String vName;
		for(Field field:query.FIELD) {
			eName = field.getName();
			vName = eName.toLowerCase();
			content = content + "\tprivate " + typeMap.mapType(field.getAnsiDbType()) + " " + vName + " = " + typeMap.mapDefault(field.getAnsiDbType()) + ";\n";
		}
		content = content + "\n";
		content = content + "\tpublic " + query.NAME + "Model() {\n";

		for(Field field:query.FIELD) {
			String s_default = field.getProperty("default");
			if(s_default!=null) {
				if(typeMap.mapType(field.getAnsiDbType()).equals("String")) {
					content = content + "\t\tset" + field.getName()  + "(" + field.getProperty("default").trim() + ");\n";
				}
				if(typeMap.mapType(field.getAnsiDbType()).equals("int")) {
					content = content + "\t\tset" + field.getName()  + "(" + Integer.valueOf(field.getProperty("default")) + ");\n";
				}
				if(typeMap.mapType(field.getAnsiDbType()).equals("Date")) {
					content = content + "\t\tset" + field.getName()  + "(new Date());\n";
				}
				if(typeMap.mapType(field.getAnsiDbType()).equals("String")) {
					content = content + "\t\tset" + field.getName()  + "(new String(new Date().getTime()));\n";
				}
			}
		}
		content = content + "\t}\n\n";

		for(Field field:query.FIELD) {
			eName = field.getName();
			vName = eName.toLowerCase();
			content = content + "\n\tpublic void"
					+ " set" + eName
					+ "(" + typeMap.mapType(field.getAnsiDbType()) + " " + vName + ") {\n"
					+ "\t\tfirePropertyChange(" + vName + ", this." + vName + ", this." + vName + " = " + vName + ");\n\t}\n";	
			content = content + "\n\tpublic "
					+ typeMap.mapType(field.getAnsiDbType())
					+ " get" + eName
					+ "() {\n"
					+ "\t\treturn this." + vName + ";\n\t}\n";
			
			getters.add(new Getter(" get" + eName + "()",typeMap.isQuoteType(field.getAnsiDbType()),null));	
		}
		content = content + "\n";
		
		for(Field field:query.FIELD) {
			if(field.getAnsiDbType().equals("DATE")) {
				eName = field.getName();
				vName = eName.toLowerCase();
				content = content + "\tpublic void set" + eName + "FromString(String d) {\n";
				content = content + "\t\tDate " + vName + " = null;\n";
				content = content + "\t\ttry {\n";
				content = content + "\t\t\t" + vName + " = new SimpleDateFormat(" + DatabaseConstant.DB_DATE_FORMAT + ").parse(d);\n";
				content = content + "\t\t} catch (Exception e) {\n";
				content = content + "\t\t\te.printStackTrace();\n";
				content = content + "\t\t}\n";
				content = content + "\t\tfirePropertyChange(" + vName + ", this." + vName + ", this." + vName + " = " + vName + ");\n";
				content = content + "\t}\n\n";
				content = content + "\tpublic String get" + eName + "ToString() {\n";
				content = content + "\t\tString d = null;\n";
				content = content + "\t\ttry {\n";
				content = content + "\t\t\td = new SimpleDateFormat(" + DatabaseConstant.DB_DATE_FORMAT + ").format(get" + eName + "());\n";
				content = content + "\t\t} catch (Exception e) {\n";
				content = content + "\t\t}\n";
				content = content + "\t\treturn d;\n";
				content = content + "\t}\n";
			}
			if(field.getAnsiDbType().equals("TIMESTAMP")) {
				eName = field.getName();
				vName = eName.toLowerCase();
				content = content + "\tpublic String get" + eName + "ToString() {\n";
				content = content + "\t\tString d = null;\n";
				content = content + "\t\ttry {\n";
				content = content + "\t\t\td = new java.text.SimpleDateFormat(" + DatabaseConstant.DB_DATE_TIME_FORMAT + ").format(get" + eName + "());\n";
				content = content + "\t\t} catch (Exception e) {\n";
				content = content + "\t\t}\n";
				content = content + "\t\treturn d;\n";
				content = content + "\t}\n";
			}
		}
		content = content + "\n}";
		createFile();		
	}

}
