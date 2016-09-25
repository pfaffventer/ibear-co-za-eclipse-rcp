package za.co.ibear.code.data.dictionary.system.field;

public class SampleFields {
	
	public static Field GTIN = new Field("Gtin",TypeConstant.CHAR + " (13)","width=190>default= ");
	public static Field COMMODITY = new Field("Commodity",TypeConstant.CHAR + " (2)","width=70>default= ");
	public static Field VARIETY = new Field("Variety",TypeConstant.CHAR + " (3)","width=70>default= ");
	public static Field GRADE = new Field("Grade",TypeConstant.CHAR + " (2)","width=70>default= ");
	public static Field PACK = new Field("Pack",TypeConstant.CHAR + " (4)","width=70>default= ");
	public static Field MARK = new Field("Mark",TypeConstant.CHAR + " (5)","width=70>default= ");
	public static Field INVENTORY = new Field("Inventory",TypeConstant.CHAR + " (2)","width=140>default= ");
	public static Field SIZE = new Field("Size",TypeConstant.CHAR + " (3)","width=70>default= ");

	
	public static Field SALES_ORDER = new Field("SalesOrder",TypeConstant.CHAR + " (6)","width=70>default= ");
	public static Field REVISION = new Field("Revision",TypeConstant.INT,"width=30>default=0");
	
	
}
