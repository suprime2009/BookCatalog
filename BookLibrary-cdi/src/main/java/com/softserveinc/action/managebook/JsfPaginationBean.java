package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean
@SessionScoped
public abstract class JsfPaginationBean implements Serializable {


    /**
     * pagination stuff
     */
    private int totalRows;
    private int firstRow;
    private Integer rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;

    /**
     * Creates a new instance of JsfPaginationBean
     */
    public JsfPaginationBean() {
        // Set default values somehow (properties files?).
        rowsPerPage = 15; // Default rows per page (max amount of rows to be displayed at once).
        pageRange = 10; // Default page range (max amount of page links to be displayed at once).
    }


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


    public void load() {
    	System.out.println("load of JsfPaginationBean");
        // Set currentPage, totalPages and pages.
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
        int pagesLength = Math.min(pageRange, totalPages);
        pages = new Integer[pagesLength];

        // firstPage must be greater than 0 and lesser than totalPages-pageLength.
        int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

        // Create pages (page numbers for page links).
        for (int i = 0; i < pagesLength; i++) {
            pages[i] = ++firstPage;
        }
    }

    // Paging actions -----------------------------------------------------------------------------
    public void pageFirst() {
    	System.out.println("pageFirst method");
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

    public abstract void page(int firstRow);
    
    public boolean isDisabledNext() {
		if ((firstRow + rowsPerPage) >= totalRows) {
			return true;
		}
		return false;
	}
    
    public boolean isDisabledLast() {
    	if (currentPage == totalPages) {
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
}
