package com.softserveinc.model.session.util;

import java.util.Map;

import org.richfaces.component.SortOrder;

import com.softserveinc.model.persist.entity.EntityConstant;

/**
 * Class DataTableHelper used for group dataTable requirements like firstRow, rowsPerPage, sortColumn,
 * sortOrder, filterValues in one case. 
 *
 */
public class DataTableSearchHolder {
	
	private int firstRow;
	private int rowsPerPage;
	private EntityConstant sortColumn;
	private SortOrder sortOrder;
	private Map<EntityConstant, String> filterValues;
	
	public DataTableSearchHolder(){}
	
	public DataTableSearchHolder(int firstRow, int rowsPerPage) {
		super();
		this.firstRow = firstRow;
		this.rowsPerPage = rowsPerPage;
	}
	
	public DataTableSearchHolder(int firstRow, int rowsPerPage, EntityConstant sortColumn, SortOrder sortOrder) {
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

	public EntityConstant getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(EntityConstant sortColumn) {
		this.sortColumn = sortColumn;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<EntityConstant, String> getFilterValues() {
		return filterValues;
	}

	public void setFilterValues(Map<EntityConstant, String> filterValues) {
		this.filterValues = filterValues;
	}
	
	
	
	
	
	

}
