package za.co.ibear.data.unit.purchaseorderline;

import java.util.HashMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.code.data.dictionary.system.field.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import gk.jfilter.JFilter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import za.co.ibear.property.model.PropertyModel;
import za.co.ibear.swt.control.combo.BCombo;
import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;
public class PurchaseOrderLineLink extends PropertyModel {
	
	private SqliteJdbc databasePurchaseOrderLineLink = null;
	
	private List<PurchaseOrderLineModel> data = new ArrayList<PurchaseOrderLineModel>();
	private List<PurchaseOrderLineModel> filterData = new ArrayList<PurchaseOrderLineModel>();
	private Set<BCombo> multiSet = new LinkedHashSet<BCombo>();
	private Set<BCombo> unitSet = new LinkedHashSet<BCombo>();
	private Set<BCombo> querySet = new LinkedHashSet<BCombo>();
	private Set<BCombo> dateSet = new LinkedHashSet<BCombo>();
	
	protected JFilter<PurchaseOrderLineModel> filter;
	
	public PurchaseOrderLineLink() throws Exception {
		try {
			databasePurchaseOrderLineLink = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	public void disconnect() {
		try {
			databasePurchaseOrderLineLink.disconnect();
		} catch (Exception e) {
		}
	}
	public List<PurchaseOrderLineModel> pop() throws Exception {
		String query = "select * from PurchaseOrderLine order by PurchaseOrderLine";
		QueryRunner qr = new QueryRunner();
		setData((List<PurchaseOrderLineModel>) qr.query(databasePurchaseOrderLineLink.getConnection(),query,new BeanListHandler<PurchaseOrderLineModel>(PurchaseOrderLineModel.class)));
		return getData();
	}
	public List<PurchaseOrderLineModel> toPurchaseOrderLineModelList(String where) throws Exception {
		String query = "select * from PurchaseOrderLine";
		if(where!=null) {
			query = query + where;
		}
		query = query + " order by PurchaseOrderLine";
		QueryRunner qr = new QueryRunner();
		setData((List<PurchaseOrderLineModel>) qr.query(databasePurchaseOrderLineLink.getConnection(),query,new BeanListHandler<PurchaseOrderLineModel>(PurchaseOrderLineModel.class)));
		return getData();
	}
	public void stringSearch(String searchString) {
		Class<PurchaseOrderLineModel> model = PurchaseOrderLineModel.class;
		Method[] methods = model.getDeclaredMethods();
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		List<PurchaseOrderLineModel> tempSet = new ArrayList<PurchaseOrderLineModel>();
		List<PurchaseOrderLineModel> resultSet = new ArrayList<PurchaseOrderLineModel>();
		boolean filtered = false;
		String[] values = searchString.split(" ");
		for(String value:values) {
			if(value.trim().length()>0) {
				for (Method method : methods) {
					if(method.getReturnType().getSimpleName().equals("String")) {
						if(method.getName().startsWith("get")) {
							tempSet.addAll(filter.filter("{'" + method.getName() + "':{'$cts':'?1'}}",value.trim()).out(new ArrayList<PurchaseOrderLineModel>()));
						}
					}
				}
				resultSet = tempSet;
				filter = new JFilter<PurchaseOrderLineModel>(resultSet, PurchaseOrderLineModel.class);
				tempSet = new ArrayList<PurchaseOrderLineModel>();
				filtered=true;
			}
		}
		if(filtered) {
			setFilterData(resultSet);
		} else {
			setFilterData(data);
			return;
		}
		for(BCombo bcombo:multiSet) {
			try {
				reduceBComboItems(bcombo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		reduceUnitBrowse();
	}
	public void filter(String column,List<String> values) {
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		List<PurchaseOrderLineModel> tempSet = new ArrayList<PurchaseOrderLineModel>();
		List<PurchaseOrderLineModel> resultSet = new ArrayList<PurchaseOrderLineModel>();
		boolean filtered = false;
		if(values.size()>0) {
			for(String value:values) {
				tempSet.addAll(filter.filter("{'get" + column.trim() + "':{'trim':'?1'}}",value.trim()).out(new ArrayList<PurchaseOrderLineModel>()));
			}
			resultSet = tempSet;
			filter = new JFilter<PurchaseOrderLineModel>(resultSet, PurchaseOrderLineModel.class);
			tempSet = new ArrayList<PurchaseOrderLineModel>();
			filtered=true;
		}
		if(filtered) {
			setData(resultSet);
		} else {
			setData(data);
		}
	}
	public List<PurchaseOrderLineModel> toPurchaseOrderLineModelList(String where,Map<String,List<String>> initFilter) throws Exception {
		String query = "select * from PurchaseOrderLine order by PurchaseOrderLine";
		if(where!=null) {
			query = query + where;
		}
		QueryRunner qr = new QueryRunner();
		initFilter(initFilter,(List<PurchaseOrderLineModel>) qr.query(databasePurchaseOrderLineLink.getConnection(),query,new BeanListHandler<PurchaseOrderLineModel>(PurchaseOrderLineModel.class)));
		return getData();
	}
	public void initFilter(Map<String,List<String>> initFilter,List<PurchaseOrderLineModel> allData) {
		filter = new JFilter<PurchaseOrderLineModel>(allData, PurchaseOrderLineModel.class);
		List<PurchaseOrderLineModel> tempSet = new ArrayList<PurchaseOrderLineModel>();
		List<PurchaseOrderLineModel> resultSet = new ArrayList<PurchaseOrderLineModel>();
		boolean filtered = false;
		for(Entry<String,List<String>> column:initFilter.entrySet()) {
			String columnName = column.getKey().trim();
			List<String> values = column.getValue();
			if(values.size()>0) {
				for(String value:values) {
					tempSet.addAll(filter.filter("{'get" + columnName.trim() + "':{'trim':'?1'}}",value.trim()).out(new ArrayList<PurchaseOrderLineModel>()));
				}
				resultSet = tempSet;
				filter = new JFilter<PurchaseOrderLineModel>(resultSet, PurchaseOrderLineModel.class);
				tempSet = new ArrayList<PurchaseOrderLineModel>();
				filtered=true;
			}
		}
		if(filtered) {
			setData(resultSet);
		} else {
			setData(data);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> toDistinctColumnArray(String column) throws Exception {
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		Set<String> itemTree = new TreeSet<String>();
		itemTree.addAll(filter.filter("{'$not':[{'get" + column + "':{'$sw':'?1'}}]}", "_iBear_").map(column).out(new TreeSet()));
		return new ArrayList<String>(Arrays.asList(itemTree.toArray(new String[itemTree.size()])));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> toDistinctColumnArray(String column,String whereColumn,String whereValue) throws Exception {
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		Set<String> itemTree = new TreeSet<String>();
		itemTree.addAll(filter.filter("{'get" + whereColumn + "':{'$eq':'?1'}}", whereValue).map(column).out(new TreeSet()));
		return new ArrayList<String>(Arrays.asList(itemTree.toArray(new String[itemTree.size()])));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<String> toDistinctFilterSet(String column) throws Exception {
		filter = new JFilter<PurchaseOrderLineModel>(filterData, PurchaseOrderLineModel.class);
		Set<String> itemTree = new TreeSet<String>();
		try {
			itemTree.addAll(filter.filter("{'$not':[{'get" + column + "':{'$sw':'?1'}}]}", "_iBear_").map(column).out(new TreeSet()));
		} catch (Exception e) {
		}
		return itemTree;
	}
	
	public void doDeleteElement(PurchaseOrderLineModel unit,boolean isFiltered) throws Exception {
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		setData(filter.filter("{'$not':[{'getElementSequence':{'$eq':'?1'}}]}", unit.getElementSequence()).out(new ArrayList<PurchaseOrderLineModel>()));
		if(isFiltered) {
			filter = new JFilter<PurchaseOrderLineModel>(filterData, PurchaseOrderLineModel.class);
			setFilterData(filter.filter("{'$not':[{'getElementSequence':{'$eq':'?1'}}]}", unit.getElementSequence()).out(new ArrayList<PurchaseOrderLineModel>()));
		}
	}
	public void doDelete(PurchaseOrderLineModel unit,boolean isFiltered) throws Exception {
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		setData(filter.filter("{'$not':[{'getUnitSequence':{'$eq':'?1'}}]}", unit.getUnitSequence()).out(new ArrayList<PurchaseOrderLineModel>()));
		if(isFiltered) {
			filter = new JFilter<PurchaseOrderLineModel>(filterData, PurchaseOrderLineModel.class);
			setFilterData(filter.filter("{'$not':[{'getUnitSequence':{'$eq':'?1'}}]}", unit.getUnitSequence()).out(new ArrayList<PurchaseOrderLineModel>()));
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void reduceBComboItems(BCombo bcombo) throws Exception {
		filter = new JFilter<PurchaseOrderLineModel>(filterData, PurchaseOrderLineModel.class);
		Set<String> itemTree = new TreeSet<String>();
		itemTree.addAll(filter.filter("{'$not':[{'get" + bcombo.getColumnName() + "':{'$sw':'?1'}}]}", "_iBear_").map(bcombo.getColumnName()).out(new TreeSet()));
		bcombo.setItems(new ArrayList<String>(Arrays.asList(itemTree.toArray(new String[itemTree.size()]))));
	}
	public void doQuery(String column, List<String> queryItems) {
		if(queryItems==null) {
			setFilterData(data);
			return;
		}
		if(queryItems.size()<1) {
			setFilterData(data);
			return;
		}
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		List<PurchaseOrderLineModel> tempSet = new ArrayList<PurchaseOrderLineModel>();
		for(String selected:queryItems) {
			tempSet.addAll(filter.filter("{'get" + column + "':{'trim':'?1'}}",selected.trim()).out(new ArrayList<PurchaseOrderLineModel>()));
		}
		setFilterData(tempSet);
		for(BCombo bcombo:multiSet) {
			try {
				reduceBComboItems(bcombo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		reduceUnitBrowse();
	}
	public void doSelect() {
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		List<PurchaseOrderLineModel> tempSet = new ArrayList<PurchaseOrderLineModel>();
		List<PurchaseOrderLineModel> resultSet = new ArrayList<PurchaseOrderLineModel>();
		boolean filtered = false;
		for(BCombo bcombo:multiSet) {
			if(bcombo.getSelection().size()>0) {
				for(String selected:bcombo.getSelection()) {
					tempSet.addAll(filter.filter("{'get" + bcombo.getColumnName() + "':{'trim':'?1'}}",selected.trim()).out(new ArrayList<PurchaseOrderLineModel>()));
				}
				resultSet = tempSet;
				filter = new JFilter<PurchaseOrderLineModel>(resultSet, PurchaseOrderLineModel.class);
				tempSet = new ArrayList<PurchaseOrderLineModel>();
				filtered=true;
			}
		}
		for(BCombo bcombo:unitSet) {
			if(bcombo.getBrowseSelection().size()>0) {
				for(Entry<String, TreeSet<String>> selection:bcombo.getBrowseSelection().entrySet()) {
					String columnName = selection.getKey();
					TreeSet<String> columnData = selection.getValue();
					if(columnData.size()>0) {
						for(String data:columnData) {
							tempSet.addAll(filter.filter("{'get" + columnName.trim() + "':{'trim':'?1'}}",data.trim()).out(new ArrayList<PurchaseOrderLineModel>()));
						}
						resultSet = tempSet;
						filter = new JFilter<PurchaseOrderLineModel>(resultSet, PurchaseOrderLineModel.class);
						tempSet = new ArrayList<PurchaseOrderLineModel>();
						filtered=true;
					}
				}
			}
		}
		for(BCombo bcombo:dateSet) {
			if(bcombo.getDatedDialog().getSelection().size()>0) {
				Date from = bcombo.getDatedDialog().getSelection().get("from");
				Date to = bcombo.getDatedDialog().getSelection().get("to");
				String query = "";
				if(bcombo.getDatedDialog().getSelection().get("from").compareTo(bcombo.getDatedDialog().getSelection().get("to"))==0) {
					Calendar calculator = Calendar.getInstance();
					calculator.setTime(to);
					calculator.add(Calendar.DATE,1);
					to = calculator.getTime();
					query = "{'$and':[{'get" + bcombo.getColumnName() + "':{'$ge':'?1'}}, {'get" + bcombo.getColumnName() + "':{'$lt':'?2'}}]}";
				} else {
					query= "{'$and':[{'get" + bcombo.getColumnName() + "':{'$ge':'?1'}}, {'get" + bcombo.getColumnName() + "':{'$le':'?2'}}]}";
				}
				tempSet.addAll(filter.filter(query,from,to).out(new ArrayList<PurchaseOrderLineModel>()));
				resultSet = tempSet;
				filter = new JFilter<PurchaseOrderLineModel>(resultSet, PurchaseOrderLineModel.class);
				tempSet = new ArrayList<PurchaseOrderLineModel>();
				filtered=true;
			}
		}
		if(filtered) {
			setFilterData(resultSet);
		} else {
			setFilterData(data);
			return;
		}
		for(BCombo bcombo:multiSet) {
			try {
				reduceBComboItems(bcombo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		reduceUnitBrowse();
	}
	public void reduceUnitBrowse() {
		for(BCombo bcombo:unitSet) {
			Map<String,TreeSet<String>> selection = new HashMap<String,TreeSet<String>>();
			String name = "za.co.ibear.code.data.dictionary.definition.unit." + bcombo.getColumnName();
			try {
				@SuppressWarnings("rawtypes")
				Class meta = Class.forName(name);
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Constructor constructor = meta.getConstructor();
				Unit unitMeta = (Unit) constructor.newInstance();
				Set<PrimaryKey> keySet = unitMeta.PRIMARY_KEY;
				for(PrimaryKey pk:keySet) {
					for(Field field:pk.FIELD) {
						selection.put(field.getName(), (TreeSet<String>) toDistinctFilterSet(field.getName()));
					}
				}
				bcombo.setBrowseSelection(selection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void doReduceUnitBrowse(Map<String,TreeSet<String>> selection) {
		filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
		List<PurchaseOrderLineModel> tempSet = new ArrayList<PurchaseOrderLineModel>();
		List<PurchaseOrderLineModel> resultSet = new ArrayList<PurchaseOrderLineModel>();
		boolean filtered = false;
		if(selection.size()>0) {
			for(Entry<String, TreeSet<String>> entry:selection.entrySet()) {
				String columnName = entry.getKey();
				TreeSet<String> columnData = entry.getValue();
				if(columnData.size()>0) {
					for(String data:columnData) {
						tempSet.addAll(filter.filter("{'get" + columnName.trim() + "':{'trim':'?1'}}",data.trim()).out(new ArrayList<PurchaseOrderLineModel>()));
					}
					resultSet = tempSet;
					filter = new JFilter<PurchaseOrderLineModel>(resultSet, PurchaseOrderLineModel.class);
					tempSet = new ArrayList<PurchaseOrderLineModel>();
					filtered=true;
				}
			}
		}
		if(filtered) {
			setFilterData(resultSet);
		} else {
			setFilterData(data);
		}
		for(BCombo bcombo:multiSet) {
			try {
				reduceBComboItems(bcombo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void doReset() {
		setFilterData(data);
		for(BCombo bcombo:multiSet) {
			bcombo.setAllSelected();
			try {
				reduceBComboItems(bcombo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bcombo.getIndicator().setText("> ");
		}
		for(BCombo bcombo:unitSet) {
			bcombo.crearState();
			bcombo.clearUnitBrowse();
			bcombo.getIndicator().setText("> ");
		}
		for(BCombo bcombo:querySet) {
			bcombo.crearState();
			bcombo.clearUnitBrowse();
			bcombo.getIndicator().setText("> ");
		}
		for(BCombo bcombo:dateSet) {
			bcombo.getDatedDialog().clear();
			bcombo.getIndicator().setText("> ");
		}
		expandUnitBrowse();
	}
	public void expandUnitBrowse() {
		for(BCombo bcombo:unitSet) {
			Map<String,TreeSet<String>> selection = new HashMap<String,TreeSet<String>>();
			bcombo.setBrowseSelection(selection);
		}
	}
	public void doExpandUnitBrowse() {
		setFilterData(data);
	}
	public List<PurchaseOrderLineModel> getData() {
		return data;
	}
	public void setData(List<PurchaseOrderLineModel> data) {
		firePropertyChange("data", this.data, this.data = data);
	}
	public List<PurchaseOrderLineModel> getFilterData() {
		return filterData;
	}
	public void setFilterData(List<PurchaseOrderLineModel> filterData) {
		this.filterData = filterData;
	}
	public Set<BCombo> getMultiSet() {
		return multiSet;
	}
	public void setMultiSet(Set<BCombo> filterSet) {
		this.multiSet = filterSet;
	}
	public Set<BCombo> getUnitSet() {
		return unitSet;
	}
	public void setUnitSet(Set<BCombo> unitSet) {
		this.unitSet = unitSet;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}
	public Set<BCombo> getDateSet() {
		return dateSet;
	}
	public void setDateSet(Set<BCombo> dateSet) {
		this.dateSet = dateSet;
	}
	public void setQuerySet(Set<BCombo> querySet) {
		this.querySet = querySet;
	}
	public int nextUnitSequence() throws Exception {
		SqliteJdbc nextConnect;
		int next = 0;
		try {
			nextConnect = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);
			nextConnect.update("BEGIN");
		} catch (Exception e) {
			throw e;
		}
		try {
			nextConnect.update("SAVEPOINT next_product_sequence");
			ResultSet rsNext = nextConnect.result("select * from UnitSequence where UnitName = 'PurchaseOrderLine'");
			if(rsNext.next()) {
				next = rsNext.getInt("NextSequence");
			}
			rsNext.close();
			next++;
			nextConnect.update("update UnitSequence set NextSequence = " + next + " where UnitName = 'PurchaseOrderLine'");
		} catch (Exception e) {
			nextConnect.update("ROLLBACK TO next_purchaseorderline_sequence");
			throw e;
		}
		nextConnect.update("COMMIT");
		nextConnect.disconnect();
		return next;
	}
	public int nextElementSequence() throws Exception {
		int next = 0;
		
		try {
			filter = new JFilter<PurchaseOrderLineModel>(data, PurchaseOrderLineModel.class);
			next = filter.<Integer> map("ElementSequence").max()+1;
		} catch (Exception e) {
		}
		return next;
	}
}
