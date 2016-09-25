package za.co.ibear.code.gizmo.unit.data;

import java.util.LinkedHashSet;
import java.util.Set;

import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.field.Getter;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.code.gizmo.GizmoUnit;

public class GizmoUnitModel extends GizmoUnit {

	private Set<String> imports = new LinkedHashSet<String>();
	private Set<Getter> getters = new LinkedHashSet<Getter>();

	public GizmoUnitModel(Unit unit,boolean reCreate) throws Exception {
		super(unit,unit.NAME + "Model",unit.NAME + "Model",GizmoUnitDataConstant.PACKAGE_PREFIX,GizmoUnitDataConstant.PATH_PREFIX);

		new GizmoUnitSql(unit,reCreate);
		new GizmoUnitLink(unit);
		new GizmoUnitTransaction(unit);

		content = content + "import za.co.ibear.property.model.PropertyModel;\n\n";
		content = content + "import javax.xml.bind.annotation.XmlRootElement;\n";

		boolean DATE_FORMAT = false;
		for(Field field:unit.FIELD) {
			if(DATE_FORMAT) {
				break;
			}
			if(typeMap.mapType(field.getAnsiDbType()).equals("Date")||typeMap.mapType(field.getAnsiDbType()).equals("String")) {
				content = content + "import java.text.SimpleDateFormat;\n";
				DATE_FORMAT = true;
			}
		}
		content = content + "\n";
		for(Field field:unit.FIELD) {
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
		content = content + "@XmlRootElement(name = " + unit.NAME.toLowerCase() + ")\n";
		content = content + "public class " + unit.NAME + "Model extends PropertyModel {\n\n";

		content = content + "\tpublic String LOCK_FIELD =  set \\" + unit.FIELD.iterator().next().getName() + "\\ = \\" + unit.FIELD.iterator().next().getName() + "\\;\n";

		String eName;
		String vName;
		for(Field field:unit.FIELD) {
			eName = field.getName();
			vName = eName.toLowerCase();
			content = content + "\tprivate " + typeMap.mapType(field.getAnsiDbType()) + " " + vName + " = " + typeMap.mapDefault(field.getAnsiDbType()) + ";\n";
		}
		content = content + "\n";
		content = content + "\tpublic " + unit.NAME + "Model() {\n";

		for(Field field:unit.FIELD) {
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

		
		content = content + "\tpublic " + unit.NAME + "Model(boolean blank) {\n";

		for(Field field:unit.FIELD) {
			String s_default = field.getProperty("default");
			if(s_default!=null) {
				if(typeMap.mapType(field.getAnsiDbType()).equals("String")) {
					content = content + "\t\tset" + field.getName()  + "();\n";
				}
				if(typeMap.mapType(field.getAnsiDbType()).equals("int")) {
					content = content + "\t\tset" + field.getName()  + "(0);\n";
				}
				if(typeMap.mapType(field.getAnsiDbType()).equals("Date")) {
					content = content + "\t\tset" + field.getName()  + "(null);\n";
				}
				if(typeMap.mapType(field.getAnsiDbType()).equals("String")) {
					content = content + "\t\tset" + field.getName()  + "(null);\n";
				}
			}
		}
		content = content + "\t}\n\n";

		if(unit.IS_ELEMENT) {
			content = content + "\tpublic String keyString() {\n";
			content = content + "\t\treturn  WHERE \\UnitSequence\\ =  + getUnitSequence() +  AND \\ElementSequence\\ =  + getElementSequence() + ;\n";
			content = content + "\t}\n";
		} else {
			content = content + "\tpublic String keyString() {\n";
			content = content + "\t\treturn  WHERE \\UnitSequence\\ =  + getUnitSequence() + ;\n";
			content = content + "\t}\n";
		}

		for(Field field:unit.FIELD) {
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
			String format = null;
			if(field.getAnsiDbType().equals("DATE")) {
				format  = "DATE";
			}
			if(field.getAnsiDbType().equals("TIMESTAMP")) {
				format  = "TIMESTAMP";
			}
			if(format==null) {
				getters.add(new Getter(" get" + eName + "()",typeMap.isQuoteType(field.getAnsiDbType()),null));
			} else  {
				getters.add(new Getter(" get" + eName + "()",typeMap.isQuoteType(field.getAnsiDbType()),format));
			}
		}
		content = content + "\n";
		
		content = content + "\tpublic String toInsertSql() {\n\t\treturn insert into " + unit.SCHEMA + ".\\" + unit.NAME + "\\ values (\n";
		int i = 0;
		for(Getter g:getters) {
			String temp = "\t\t\t\t+";
			if(g.IS_QOTE_TYPE) {
				if(g.FORMAT!=null) {
					if(g.FORMAT.equals("DATE")) {
						temp = temp + " ' +  new SimpleDateFormat(" + DatabaseConstant.DB_DATE_FORMAT + ").format(" + g.NAME.trim() + ") + ',";
					}
					if(g.FORMAT.equals("TIMESTAMP")) {
						temp = temp + " ' +  new SimpleDateFormat(" + DatabaseConstant.DB_DATE_TIME_FORMAT + ").format(" + g.NAME.trim() + ") + ',";
					}
				} else {
					temp = temp + " ' + " + g.NAME + " + ',"; 
				}
			} else {
				temp = temp + "   + " + g.NAME + " +  ,"; 
			}
			if(i==getters.size()-1) {
				temp = temp.substring(0,temp.length()-2) + ");";
			}
			content = content + temp + "\n";
			i++;
		}
		content = content + "\t}\n\n";

		content = content + "\tpublic String toLockReadSql() {\n";
		content = content + "\t\treturn select * from " + unit.SCHEMA + ".\\" + unit.NAME + "\\ + keyString() +  FOR UPDATE NOWAIT;\n";
		content = content + "\t}\n\n";

		content = content + "\tpublic String toLockSql() {\n";
		content = content + "\t\treturn update " + unit.SCHEMA + ".\\" + unit.NAME + "\\+ LOCK_FIELD + keyString();\n";
		content = content + "\t}\n\n";

		content = content + "\tpublic String toDeleteSql() {\n";
		content = content + "\t\treturn delete from " + unit.SCHEMA + ".\\" + unit.NAME + "\\ + keyString();\n";
		content = content + "\t}\n\n";

		content = content + "\tpublic String toSaveSql() {\n\t\treturn update " + unit.SCHEMA + ".\\" + unit.NAME + "\\ set \n";
		i = 0;
		for(Getter g:getters) {
			String temp = "\t\t\t\t+";
			if(g.IS_QOTE_TYPE) {
				if(g.FORMAT!=null) {
					if(g.FORMAT.equals("DATE")) {
						temp = temp + " \\" + g.NAME.substring(4,g.NAME.length()-2) + "\\ = ' +  new SimpleDateFormat(" + DatabaseConstant.DB_DATE_FORMAT + ").format(" + g.NAME.trim() + ") + ',";
					}
					if(g.FORMAT.equals("TIMESTAMP")) {
						temp = temp + " \\" + g.NAME.substring(4,g.NAME.length()-2) + "\\ = ' +  new SimpleDateFormat(" + DatabaseConstant.DB_DATE_TIME_FORMAT + ").format(" + g.NAME.trim() + ") + ',";
					}
				} else {
					temp = temp + " \\" + g.NAME.substring(4,g.NAME.length()-2) + "\\ =  ' + " + g.NAME + " + ',"; 
				}
			} else {
				temp = temp + " \\" + g.NAME.substring(4,g.NAME.length()-2) + "\\ =   + " + g.NAME + " +  ,"; 
			}
			if(i==getters.size()-1) {
				temp = temp.substring(0,temp.length()-2) + "";
			}
			content = content + temp + "\n";
			i++;
		}
		content = content + "\t\t\t\t+ keyString();\n";
		content = content + "\t}\n\n";

		for(Field field:unit.FIELD) {
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
