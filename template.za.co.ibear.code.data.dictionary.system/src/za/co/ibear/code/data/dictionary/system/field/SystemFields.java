package za.co.ibear.code.data.dictionary.system.field;

public class SystemFields {

	public static Field USER = new Field("User",TypeConstant.VARCHAR + " (30)","width=70>default=SYSTEM");

	public static Field UNIT_NAME = new Field("UnitName","Unit Name",TypeConstant.VARCHAR + " (70)","width=70>default= ");
	public static Field UNIT_SEQUENCE = new Field("UnitSequence","Unit Sequence",TypeConstant.INT,"width=90>default=0");
	public static Field ELEMENT_SEQUENCE = new Field("ElementSequence","Element Sequence",TypeConstant.INT,"width=90>default=0");

	public static Field NEXT_SEQUENCE = new Field("NextSequence","Next Sequence",TypeConstant.INT,"width=90>default=0");

	public static Field SUPPLIER = new Field("Supplier",TypeConstant.VARCHAR + " (30)","width=120>default= ");
	public static Field CUSTOMER = new Field("Customer",TypeConstant.VARCHAR + " (30)","width=70>default= ");
	public static Field PRODUCT = new Field("Product",TypeConstant.VARCHAR + " (30)","width=120>default= ");

	public static Field PURCHASE_ORDER = new Field("PurchaseOrder",TypeConstant.VARCHAR + " (30)","width=120>default= ");
	public static Field PURCHASE_ORDER_LINE = new Field("PurchaseOrderLine",TypeConstant.VARCHAR + " (30)","width=120>default= ");
	
	
	public static Field QUALITY_CLASS = new Field("QualityClass","Quality Class",TypeConstant.VARCHAR + " (30)","width=90>default= ");

	public static Field CATEGORY = new Field("Category",TypeConstant.VARCHAR + " (30)","width=90>default= ");
	public static Field PRODUCT_CATEGORY = new Field("ProductCategory","Category",TypeConstant.VARCHAR + " (30)","width=170>default= ");
	public static Field CATEGORY_QUERY = new Field("CategoryQuery","Category Query",TypeConstant.VARCHAR + " (30)","width=170>default= ");
	public static Field STOCK_QUERY = new Field("StockQuery","Stock Query",TypeConstant.VARCHAR + " (30)","width=170>default= ");

	public static Field DESCRIPTION = new Field("Description",TypeConstant.VARCHAR + " (70)","width=270>default= ");

	public static Field NAME = new Field("Name",TypeConstant.VARCHAR + " (70)","width=270>default= ");
	public static Field ADDRESS1 = new Field("Address1",TypeConstant.VARCHAR + " (50)","width=210>default= ");
	public static Field ADDRESS2 = new Field("Address2",TypeConstant.VARCHAR + " (50)","width=210>default= ");
	public static Field ADDRESS3 = new Field("Address3",TypeConstant.VARCHAR + " (50)","width=210>default= ");
	public static Field ADDRESS4 = new Field("Address4",TypeConstant.VARCHAR + " (50)","width=210>default= ");
	public static Field ADDRESS5 = new Field("Address5",TypeConstant.VARCHAR + " (50)","width=210>default= ");

	public static Field TIME_CREATED = new Field("TimeCreated","Time Created",TypeConstant.TIMESTAMP,"width=210>default=now");

	public static Field DATE_CREATED = new Field("DateCreated","Date Created",TypeConstant.DATE,"width=170>default=today");

	public static Field DATED = new Field("Dated",TypeConstant.DATE,"width=170>default=today");
	
	public static Field VOLUME = new Field("Volume",TypeConstant.FLOAT,"width=70");
	public static Field VALUE = new Field("Value",TypeConstant.FLOAT,"width=70");
	public static Field PRICE = new Field("Price",TypeConstant.FLOAT,"width=70");
	public static Field TOTAL = new Field("Total",TypeConstant.FLOAT,"width=70");

	public static Field DOCUMENT = new Field("Document",TypeConstant.VARCHAR + " (30)","width=70>default= ");
	public static Field TRANSATION = new Field("Transaction",TypeConstant.VARCHAR + " (30)","width=70>default= ");
	
	public static Field DEAL_NUMBER = new Field("DealNumber","Deal Number",TypeConstant.VARCHAR + " (30)","width=70>default= ");

	public static Field LINE_NUMBER = new Field("LineNumber","Line Number",TypeConstant.INT,"width=30>default=0");

	public static Field GTIN = new Field("Gtin",TypeConstant.VARCHAR + " (30)","width=70>default= ");
	
	
}
