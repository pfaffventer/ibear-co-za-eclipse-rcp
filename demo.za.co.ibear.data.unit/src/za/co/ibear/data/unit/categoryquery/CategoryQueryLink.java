package za.co.ibear.data.unit.categoryquery;

import java.lang.reflect.Constructor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import gk.jfilter.JFilter;
import za.co.ibear.code.data.dictionary.definition.query.CategoryQuery;
import za.co.ibear.code.data.dictionary.system.database.DatabaseConstant;
import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.code.data.dictionary.system.unit.Unit;
import za.co.ibear.lib.jdbc.sqlite.SqliteJdbc;
import za.co.ibear.property.model.PropertyModel;
import za.co.ibear.swt.control.combo.BCombo;

public class CategoryQueryLink extends PropertyModel {
	
	private SqliteJdbc databaseCategoryQueryLink = null;
	
	private List<CategoryQueryModel> data = new ArrayList<CategoryQueryModel>();
	private List<CategoryQueryModel> filterData = new ArrayList<CategoryQueryModel>();
	private Set<BCombo> multiSet = new LinkedHashSet<BCombo>();
	private Set<BCombo> unitSet = new LinkedHashSet<BCombo>();
	private Set<BCombo> dateSet = new LinkedHashSet<BCombo>();
	
	protected JFilter<CategoryQueryModel> filter;
	private CategoryQuery query = new CategoryQuery();
	public CategoryQueryLink() throws Exception {
		try {
			databaseCategoryQueryLink = new SqliteJdbc(DatabaseConstant.DB_CONNECTION);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	public void disconnect() {
		try {
			databaseCategoryQueryLink.disconnect();
		} catch (Exception e) {
		}
	}
	public List<CategoryQueryModel> pop() throws Exception {
		QueryRunner qr = new QueryRunner();
		setData((List<CategoryQueryModel>) qr.query(databaseCategoryQueryLink.getConnection(),this.query.SQL,new BeanListHandler<CategoryQueryModel>(CategoryQueryModel.class)));
		return getData();
	}
	public List<CategoryQueryModel> toCategoryQueryModelList(String where) throws Exception {
		String sql = this.query.SQL;
		if(where!=null) {
			sql = sql + where;
		}
		QueryRunner qr = new QueryRunner();
		setData((List<CategoryQueryModel>) qr.query(databaseCategoryQueryLink.getConnection(),sql,new BeanListHandler<CategoryQueryModel>(CategoryQueryModel.class)));
		return getData();
	}
	public void stringSearch(String searchString) {
		filter = new JFilter<CategoryQueryModel>(data, CategoryQueryModel.class);
		List<CategoryQueryModel> resultSet = new ArrayList<CategoryQueryModel>();
		boolean filtered = false;
		Class<CategoryQueryModel> model = CategoryQueryModel.class;
		String[] values = searchString.split(" ");
		Method[] methods = model.getDeclaredMethods();
		for(String value:values) {
			if(value.trim().length()>0) {
				for (Method method : methods) {
		        	if(method.getReturnType().getSimpleName().equals("String")) {
		        		if(method.getName().startsWith("get")) {
		        			List<CategoryQueryModel> result = new ArrayList<CategoryQueryModel>();
		        			result.addAll(filter.filter("{'" + method.getName() + "':{'$cts':'?1'}}",value.trim()).out(new ArrayList<CategoryQueryModel>()));
		        			resultSet.removeAll(result);
		        			resultSet.addAll(result);
		        		}
		        	}
		        }
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
		filter = new JFilter<CategoryQueryModel>(data, CategoryQueryModel.class);
		List<CategoryQueryModel> tempSet = new ArrayList<CategoryQueryModel>();
		List<CategoryQueryModel> resultSet = new ArrayList<CategoryQueryModel>();
		boolean filtered = false;
		if(values.size()>0) {
			for(String value:values) {
				tempSet.addAll(filter.filter("{'get" + column.trim() + "':{'trim':'?1'}}",value.trim()).out(new ArrayList<CategoryQueryModel>()));
			}
			resultSet = tempSet;
			filter = new JFilter<CategoryQueryModel>(resultSet, CategoryQueryModel.class);
			tempSet = new ArrayList<CategoryQueryModel>();
			filtered=true;
		}
		if(filtered) {
			setData(resultSet);
		} else {
			setData(data);
		}
	}
	public List<CategoryQueryModel> toCategoryQueryModelList(String where,Map<String,List<String>> initFilter) throws Exception {
		String sql = this.query.SQL;
		if(where!=null) {
			sql = sql + where;
		}
		QueryRunner qr = new QueryRunner();
		initFilter(initFilter,(List<CategoryQueryModel>) qr.query(databaseCategoryQueryLink.getConnection(),sql,new BeanListHandler<CategoryQueryModel>(CategoryQueryModel.class)));
		return getData();
	}
	public void initFilter(Map<String,List<String>> initFilter,List<CategoryQueryModel> allData) {
		filter = new JFilter<CategoryQueryModel>(allData, CategoryQueryModel.class);
		List<CategoryQueryModel> tempSet = new ArrayList<CategoryQueryModel>();
		List<CategoryQueryModel> resultSet = new ArrayList<CategoryQueryModel>();
		boolean filtered = false;
		for(Entry<String,List<String>> column:initFilter.entrySet()) {
			String columnName = column.getKey().trim();
			List<String> values = column.getValue();
			if(values.size()>0) {
				for(String value:values) {
					tempSet.addAll(filter.filter("{'get" + columnName.trim() + "':{'trim':'?1'}}",value.trim()).out(new ArrayList<CategoryQueryModel>()));
				}
				resultSet = tempSet;
				filter = new JFilter<CategoryQueryModel>(resultSet, CategoryQueryModel.class);
				tempSet = new ArrayList<CategoryQueryModel>();
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
		filter = new JFilter<CategoryQueryModel>(data, CategoryQueryModel.class);
		Set<String> itemTree = new TreeSet<String>();
		itemTree.addAll(filter.filter("{'$not':[{'get" + column + "':{'$sw':'?1'}}]}", "_iBear_").map(column).out(new TreeSet()));
		return new ArrayList<String>(Arrays.asList(itemTree.toArray(new String[itemTree.size()])));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void reduceBComboItems(BCombo bcombo) throws Exception {
		filter = new JFilter<CategoryQueryModel>(filterData, CategoryQueryModel.class);
		Set<String> itemTree = new TreeSet<String>();
		itemTree.addAll(filter.filter("{'$not':[{'get" + bcombo.getColumnName() + "':{'$sw':'?1'}}]}", "_iBear_").map(bcombo.getColumnName()).out(new TreeSet()));
		bcombo.setItems(new ArrayList<String>(Arrays.asList(itemTree.toArray(new String[itemTree.size()]))));
	}
	public void doSelect() {
		filter = new JFilter<CategoryQueryModel>(data, CategoryQueryModel.class);
		List<CategoryQueryModel> tempSet = new ArrayList<CategoryQueryModel>();
		List<CategoryQueryModel> resultSet = new ArrayList<CategoryQueryModel>();
		boolean filtered = false;
		for(BCombo bcombo:multiSet) {
			if(bcombo.getSelection().size()>0) {
				for(String selected:bcombo.getSelection()) {
					tempSet.addAll(filter.filter("{'get" + bcombo.getColumnName() + "':{'trim':'?1'}}",selected.trim()).out(new ArrayList<CategoryQueryModel>()));
				}
				resultSet = tempSet;
				filter = new JFilter<CategoryQueryModel>(resultSet, CategoryQueryModel.class);
				tempSet = new ArrayList<CategoryQueryModel>();
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
							tempSet.addAll(filter.filter("{'get" + columnName.trim() + "':{'trim':'?1'}}",data.trim()).out(new ArrayList<CategoryQueryModel>()));
						}
						resultSet = tempSet;
						filter = new JFilter<CategoryQueryModel>(resultSet, CategoryQueryModel.class);
						tempSet = new ArrayList<CategoryQueryModel>();
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
				tempSet.addAll(filter.filter(query,from,to).out(new ArrayList<CategoryQueryModel>()));
				resultSet = tempSet;
				filter = new JFilter<CategoryQueryModel>(resultSet, CategoryQueryModel.class);
				tempSet = new ArrayList<CategoryQueryModel>();
				filtered=true;
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
		filter = new JFilter<CategoryQueryModel>(data, CategoryQueryModel.class);
		List<CategoryQueryModel> tempSet = new ArrayList<CategoryQueryModel>();
		List<CategoryQueryModel> resultSet = new ArrayList<CategoryQueryModel>();
		boolean filtered = false;
		if(selection.size()>0) {
			for(Entry<String, TreeSet<String>> entry:selection.entrySet()) {
				String columnName = entry.getKey();
				TreeSet<String> columnData = entry.getValue();
				if(columnData.size()>0) {
					for(String data:columnData) {
						tempSet.addAll(filter.filter("{'get" + columnName.trim() + "':{'trim':'?1'}}",data.trim()).out(new ArrayList<CategoryQueryModel>()));
					}
					resultSet = tempSet;
					filter = new JFilter<CategoryQueryModel>(resultSet, CategoryQueryModel.class);
					tempSet = new ArrayList<CategoryQueryModel>();
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<String> toDistinctFilterSet(String column) throws Exception {
		filter = new JFilter<CategoryQueryModel>(filterData, CategoryQueryModel.class);
		Set<String> itemTree = new TreeSet<String>();
		itemTree.addAll(filter.filter("{'$not':[{'get" + column + "':{'$sw':'?1'}}]}", "_iBear_").map(column).out(new TreeSet()));
		return itemTree;
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
	public List<CategoryQueryModel> getData() {
		return data;
	}
	public void setData(List<CategoryQueryModel> data) {
		firePropertyChange("data", this.data, this.data = data);
	}
	public List<CategoryQueryModel> getFilterData() {
		return filterData;
	}
	public void setFilterData(List<CategoryQueryModel> filterData) {
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
	public Set<BCombo> getDateSet() {
		return dateSet;
	}
	public void setDateSet(Set<BCombo> dateSet) {
		this.dateSet = dateSet;
	}
	public CategoryQuery getQuery() {
		return query;
	}
	public void setQuery(CategoryQuery query) {
		this.query = query;
	}
	protected void p(String v) {
		System.out.println(this.getClass().getSimpleName() + ":)" + v);
	}
}
