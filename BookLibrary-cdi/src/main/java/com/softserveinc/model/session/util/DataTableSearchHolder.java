package com.softserveinc.model.session.util;

import java.util.Map;

import org.richfaces.component.SortOrder;

import com.softserveinc.model.persist.entity.EntityFieldHolder;

/**
 * Class DataTableHelper used for group dataTable requirements like firstRow, rowsPerPage, sortColumn,
 * sortOrder, filterValues in one case. 
 *
 */
public class DataTableSearchHolder {
	
	private int firstRow;
	private int rowsPerPage;
	private EntityFieldHolder sortColumn;
	private SortOrder sortOrder;
	private Map<EntityFieldHolder, String> filterValues;
	
	public DataTableSearchHolder(){}
	
	public DataTableSearchHolder(int firstRow, int rowsPerPage) {
		super();
		this.firstRow = firstRow;
		this.rowsPerPage = rowsPerPage;
	}
	
	public DataTableSearchHolder(int firstRow, int rowsPerPage, EntityFieldHolder sortColumn, SortOrder sortOrder) {
		super();
		this.firstRow = firstRow;
		this.rowsPerPage = rowsPerPage;
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public EntityFieldHolder getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(EntityFieldHolder sortColumn) {
		this.sortColumn = sortColumn;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<EntityFieldHolder, String> getFilterValues() {
		return filterValues;
	}

	public void setFilterValues(Map<EntityFieldHolder, String> filterValues) {
		this.filterValues = filterValues;
	}
	
	
	
	
	
	

}
