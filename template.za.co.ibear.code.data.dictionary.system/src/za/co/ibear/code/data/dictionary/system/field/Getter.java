package za.co.ibear.code.data.dictionary.system.field;

public class Getter {
	
	public String NAME = null;
	public String FORMAT = null;
	public boolean IS_QOTE_TYPE = false;
	
	
	public Getter(String n, boolean q,String f) {
		super();
		this.NAME = n;
		this.FORMAT = f;
		this.IS_QOTE_TYPE = q;
	}

}
