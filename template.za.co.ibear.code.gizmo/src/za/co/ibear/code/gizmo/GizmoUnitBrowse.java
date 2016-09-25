package za.co.ibear.code.gizmo;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;

import za.co.ibear.code.data.dictionary.system.field.JavaType;

public class GizmoUnitBrowse {

	protected JavaType typeMap = new JavaType();
	protected String className = null;
	protected String content = "";
	protected String packagePrefix = "";
	protected String pathPrefix = "";
	protected String fileExtension = ".java";
	
	public GizmoUnitBrowse(String className,String packagePrefix, String pathPrefix) {
		super();
		this.className = className;
		this.packagePrefix = packagePrefix;
		this.pathPrefix = pathPrefix;
		this.content = "package " + packagePrefix + "browse" + ";\n\n";
	}

	protected String createFile() throws Exception {
		String fileName = new File("..").getCanonicalPath()
				+ File.separator
				+ pathPrefix
				+ File.separator
				+ "browse" 
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

	protected void p(String v) {
		System.out.println("GizmoUnitBrowse:) " + v);
	}

}
