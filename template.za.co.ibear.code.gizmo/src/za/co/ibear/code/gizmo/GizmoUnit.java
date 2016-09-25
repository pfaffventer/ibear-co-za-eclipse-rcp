package za.co.ibear.code.gizmo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import za.co.ibear.code.data.dictionary.system.field.JavaType;
import za.co.ibear.code.data.dictionary.system.unit.Unit;

public class GizmoUnit {

	protected JavaType typeMap = new JavaType();

	protected Unit unit = null;
	protected String className = null;
	protected String modelName = null;
	protected String content = "";
	protected String packagePrefix = "";
	protected String pathPrefix = "";
	protected String fileExtension = ".java";
	
	public GizmoUnit(Unit unit, String className, String modelName,String packagePrefix, String pathPrefix) {
		super();
		this.unit = unit;
		this.className = className;
		this.modelName = modelName;
		this.packagePrefix = packagePrefix;
		this.pathPrefix = pathPrefix;
		this.content = "package " + packagePrefix + unit.NAME.toLowerCase() + ";\n\n";
	}

	public GizmoUnit(Unit unit, String className, String modelName,String packagePrefix, String pathPrefix,String fileExtension) {
		super();
		this.unit = unit;
		this.className = className;
		this.modelName = modelName;
		this.packagePrefix = packagePrefix;
		this.pathPrefix = pathPrefix;
		this.fileExtension = fileExtension;
	}

	protected String createFile() throws Exception {
		String fileName = new File("..").getCanonicalPath()
				+ File.separator
				+ pathPrefix
				+ unit.NAME.toLowerCase()
				+ File.separator
				+ className + fileExtension;

		File classFile = new File(fileName);
		classFile.mkdirs();
		if(classFile.exists()) {
			classFile.delete();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(classFile));
		out.write(content);
		out.close();
		return ":) " + fileName;
	}

	protected static void p(String v) {
		System.out.println("GizmoUnit:) " + v);
	}

}
