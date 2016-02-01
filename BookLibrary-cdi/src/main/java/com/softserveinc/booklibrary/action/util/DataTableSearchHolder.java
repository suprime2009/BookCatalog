package com.softserveinc.booklibrary.action.util;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;

/**
 * Class DataTableHelper used for group dataTable requirements like firstRow,
 * rowsPerPage, sortColumn, sortOrder, filterValues in one case.
 *
 */
public class DataTableSearchHolder {

	private int firstRow;
	private int rowsPerPage;
	private EntityFieldHolder sortColumn;
	private String sortOrder;
	private Map<EntityFieldHolder, String> filterValues;

	public DataTableSearchHolder() {
	}

	public DataTableSearchHolder(int firstRow, int rowsPerPage) {
		super();
		this.firstRow = firstRow;
		this.rowsPerPage = rowsPerPage;
	}

	public DataTableSearchHolder(int firstRow, int rowsPerPage, EntityFieldHolder sortColumn, String sortOrder) {
		super();
		this.firstRow = firstRow;
		this.rowsPerPage = rowsPerPage;
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
	}
	
	public DataTableSearchHolder(int firstRow, int rowsPerPage, EntityFieldHolder sortColumn, String sortOrder,
			Map<EntityFieldHolder, String> filterValues) {
		super();
		this.firstRow = firstRow;
		this.rowsPerPage = rowsPerPage;
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
		this.filterValues = filterValues;
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

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<EntityFieldHolder, String> getFilterValues() {
		return filterValues;
	}

	public void setFilterValues(Map<EntityFieldHolder, String> filterValues) {
		this.filterValues = filterValues;
	}

}
