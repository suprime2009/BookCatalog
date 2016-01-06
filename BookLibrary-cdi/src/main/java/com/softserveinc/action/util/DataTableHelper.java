package com.softserveinc.action.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.softserveinc.model.session.util.DataTableSearchHolder;

public abstract class DataTableHelper<T> extends PaginationHelper implements Serializable{


	private static final long serialVersionUID = -3683225876764573771L;

	private static Logger log = LoggerFactory.getLogger(DataTableHelper.class);

	private List<T> entities;
	private Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(1);
	private Map<String, String> filterValues = Maps.newHashMap();
	private String sortProperty;
	private boolean selectAll;
	private String idEntityToDelete;

	protected DataTableHelper() {
		for (String s : getColumns()) {
			sortOrders.put(s, SortOrder.unsorted);
		}
	}
	
	public void refreshPage() {
		selectAll = false;
		pageFirst();

	}
	
	public void cleanFilters() {
		filterValues.clear();
	}

	private void deleteEmptyFilterValues() {
		for (Iterator<Map.Entry<String, String>> it = filterValues.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			if (entry.getValue().equals("") || entry.getValue().startsWith(" ")) {
				it.remove();
			}
		}
	}

	public void searchAction() {
		refreshPage();
	}
		
	public void cleanListEntities() {
		entities.clear();
	}

	public void toggleSort(String column) {
		log.error("toggleSort");
		sortProperty = column;
		for (Entry<String, SortOrder> entry : sortOrders.entrySet()) {
			SortOrder newOrder;

			if (entry.getKey().equals(sortProperty)) {
				if (entry.getValue() == SortOrder.ascending) {
					newOrder = SortOrder.descending;
				} else {
					newOrder = SortOrder.ascending;
				}
			} else {
				newOrder = SortOrder.unsorted;
			}

			entry.setValue(newOrder);
		}
		refreshPage();
	}

	protected DataTableSearchHolder getCurrentRequirementsForDataTable() {
		
		deleteEmptyFilterValues();

		DataTableSearchHolder datatableSearchHolder = new DataTableSearchHolder();
		datatableSearchHolder.setFirstRow(getFirstRow());
		datatableSearchHolder.setRowsPerPage(getRowsPerPage());
		datatableSearchHolder.setSortColumn(sortProperty);
		datatableSearchHolder.setSortOrder(sortOrders.get(sortProperty));
		datatableSearchHolder.setFilterValues(filterValues);

		return datatableSearchHolder;
	}
	
	public abstract List<String> getColumns();

	public abstract void selectAllAction();

	public abstract void deleteEntity();

	public abstract void deleteListEntities();
	
	public List<T> getEntities() {
		log.error("getEntities");
		if (entities == null) {
			entities = new ArrayList<T>();
			refreshPage();
		}
		return entities;
	}

	public boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public String getSortProperty() {
		log.error("getSortProperty");
		return sortProperty;
	}

	public String getIdEntityToDelete() {
		return idEntityToDelete;
	}

	public void setIdEntityToDelete(String idEntityToDelete) {
		System.out.println("bookIdToDelete SETTER" + idEntityToDelete);
		this.idEntityToDelete = idEntityToDelete;
	}

	public void setSortProperty(String sortPropety) {
		this.sortProperty = sortPropety;
	}

	public Map<String, SortOrder> getSortOrders() {
		log.error("getSortOrders");
		return sortOrders;
	}

	public Map<String, String> getFilterValues() {
		log.info("Current values for filtering = {}", filterValues.size());
		return filterValues;
	}

}
