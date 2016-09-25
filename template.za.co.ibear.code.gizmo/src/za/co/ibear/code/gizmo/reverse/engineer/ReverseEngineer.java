package za.co.ibear.code.gizmo.reverse.engineer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import za.co.ibear.property.model.PropertyModel;

public class ReverseEngineer extends PropertyModel {

	private File file;

	private String input = "";
	
	public void process() throws Exception {

//		setOutput(input + file.getCanonicalPath() + "\n\n");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line=reader.readLine())!=null) {

			p(line.replace("","\\"));
			
//			p(line);
			
			if(line.length()>0) {
//				setOutput(input + "content = content + " + line + "\\n;\n");
				
				setOutput(input + "content = content + " + line.replace("","\\") + "\\n;\n");
				
			} else {
				setOutput(input + line + "\n");
			}
		}
		reader.close();
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		firePropertyChange("file", this.file, this.file = file);
	}

	public String getOutput() {
		return input;
	}

	public void setOutput(String output) {
		firePropertyChange("input", this.input, this.input = output);
	}

	protected static void p(String v) {
		System.out.println("ReverseEngineer:) " + v);
	}
}
