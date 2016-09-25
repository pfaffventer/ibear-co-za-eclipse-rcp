package za.co.ibear.lib.file.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {
	
	private File input = null;
	private List<File> output = new ArrayList<File>();
	protected byte[] buffer = new byte[2048];
	
	public UnZip(File input) {
		this.input = input;
	}

	public void doUnzip() throws Exception {
		ZipInputStream zipFileInStream = new ZipInputStream(new FileInputStream(input));
    	ZipEntry zipEntry = zipFileInStream.getNextEntry();
    	while(zipEntry!=null){
    		File file = new File(input.getParentFile() + File.separator + zipEntry.getName()); 
    		new File(file.getParent()).mkdirs();
    		FileOutputStream unzippedOutStream = new FileOutputStream(file);             
    		int len;
    		while ((len = zipFileInStream.read(buffer)) > 0) {
    			unzippedOutStream.write(buffer, 0, len);
    		}
    		unzippedOutStream.close();   
         	output.add(file);
    		zipEntry = zipFileInStream.getNextEntry();
     	}
        zipFileInStream.closeEntry();
     	zipFileInStream.close();
	}

	public File getInput() {
		return input;
	}

	public void setInput(File input) {
		this.input = input;
	}

	public List<File> getOutput() {
		return output;
	}

	public void setOutput(List<File> output) {
		this.output = output;
	}
	
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}

}
