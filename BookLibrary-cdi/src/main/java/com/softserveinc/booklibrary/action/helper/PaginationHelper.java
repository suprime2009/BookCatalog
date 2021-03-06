package com.softserveinc.booklibrary.action.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PaginationHelper<T> abstract class that describe logic of dataTable page
 * pagination. Class has methods that produce page navigation by buttons and by
 * links with page numbers, possibility to change page size (count rows on each
 * page). Class has attributes firstRow and rowsPerPage that can be used to
 * create query to database based on limits.
 *
 * @param <T>
 *            Entity
 */
public abstract class PaginationHelper implements Serializable {

	private static final long serialVersionUID = -5368444911902570386L;

	private static Logger log = LoggerFactory.getLogger(PaginationHelper.class);

	private int totalRows;
	private int firstRow;
	private Integer rowsPerPage;
	private int totalPages;
	private int pageRange;
	private Integer[] pages;
	private int currentPage;

	private List<Integer> pageSizeValues;

	protected PaginationHelper() {
		pageSizeValues = new ArrayList<Integer>();
		pageSizeValues.add(new Integer(20));
		pageSizeValues.add(new Integer(50));
		pageSizeValues.add(new Integer(100));
		rowsPerPage = 20;
		pageRange = 5;
	}

	/**
	 * Method returns entities based on current dataTable requirements.
	 * 
	 * @return List<T> of Entities .
	 */
	public abstract void getEntitiesForCurrentPage();

	/**
	 * Method returns count entities based on current dataTable requirements.
	 * 
	 * @return int count entities that meet the requirements.
	 */
	public abstract int getCountEntitiesForCurrentRequirements();

	/**
	 * Method set List<T> of entities and totalRows based on current dataTable
	 * requirements, currentPage, calculate totalPages.
	 */
	public void load() {

		getEntitiesForCurrentPage();
		totalRows = getCountEntitiesForCurrentRequirements();

		// Set currentPage, totalPages and pages.
		currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
		if (totalRows == 0) {
			currentPage = 0;
		}
		totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
		int pagesLength = Math.min(pageRange, totalPages);
		pages = new Integer[pagesLength];

		// firstPage must be greater than 0 and lesser than
		// totalPages-pageLength.
		int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

		// Create pages (page numbers for page links).
		for (int i = 0; i < pagesLength; i++) {
			pages[i] = ++firstPage;
		}

		log.debug("Method done. CurrrentPage=={}, totalPages=={}", currentPage, totalPages);
	}

	// Page actions
	// --------------------------------------------------------------------------------------------------------
	public void pageFirst() {
		page(0);
	}

	public void pageNext() {
		page(firstRow + rowsPerPage);
	}

	public void pagePrevious() {
		page(firstRow - rowsPerPage);
	}

	public void pageLast() {
		page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
	}

	public void page(ActionEvent event) {
		page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
	}

	/**
	 * Method set firstRow and fires load().
	 * 
	 * @param int
	 *            firstRow number of firstRow
	 */
	private void page(int firstRow) {
		this.firstRow = firstRow;
		load();
	}

	public boolean isDisabledNext() {
		if ((firstRow + rowsPerPage) >= totalRows) {
			return true;
		}
		return false;
	}

	public boolean isDisabledLast() {
		if (totalPages == 0 || currentPage == totalPages) {
			return true;
		}
		return false;
	}

	public boolean isDisabledFirst() {
		if (firstRow == 0) {
			return true;
		}
		return false;
	}

	public boolean isDisabledPrevious() {
		if (firstRow == 0) {
			return true;
		}
		return false;
	}

	/* etters and setters */
	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public Integer getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public List<Integer> getPageSizeValues() {
		return pageSizeValues;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageRange() {
		return pageRange;
	}

	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}

	public Integer[] getPages() {
		return pages;
	}

	public void setPages(Integer[] pages) {
		this.pages = pages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
