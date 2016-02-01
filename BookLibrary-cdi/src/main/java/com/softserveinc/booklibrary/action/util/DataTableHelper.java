package com.softserveinc.booklibrary.action.util;

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
import com.softserveinc.booklibrary.session.util.holders.BookFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;

/**
 * This abstract class used to work with dataTable. Class provides sorting and
 * filtering possibilities for dataTable.
 *
 * @param <T>
 *            entity UIWrapper
 */
public abstract class DataTableHelper<T> extends PaginationHelper implements Serializable {

	private static final long serialVersionUID = -3683225876764573771L;

	private static Logger log = LoggerFactory.getLogger(DataTableHelper.class);

	private List<T> entities;
	private Map<EntityFieldHolder, SortOrder> sortOrders = Maps.newHashMap();
	private Map<EntityFieldHolder, String> filterValues = Maps.newHashMap();
	private EntityFieldHolder sortProperty;
	private boolean selectAll;
	private String idEntityToDelete;
	private List<T> listEntitiesToDelete;

	protected DataTableHelper() {
		for (EntityFieldHolder constant : getEntityConstantInstances()) {
			sortOrders.put(constant, SortOrder.unsorted);
		}
	}

	/**
	 * Method sets pagination to first page and loads data.
	 */
	public void refreshPage() {
		selectAll = false;
		pageFirst();

	}

	/**
	 * Method cleans all filtering values.
	 */
	public void cleanFilters() {

		filterValues.clear();
	}

	/**
	 * Method deletes from Map empty filter values or filter values, which
	 * starts with whitespace.
	 */
	public abstract void deleteEmptyFilterValues();

	public abstract EntityFieldHolder getFieldHolderForColumn(String column);

	public void searchAction() {
		refreshPage();
	}

	/**
	 * Method cleans List of entities.
	 */
	public void cleanListEntities() {
		if (entities == null) {
			getEntities();
		}
		entities.clear();
	}

	/**
	 * Method is a toggle for sorting columns. If method call for some column,
	 * method toggle sort Order.
	 * 
	 * @param constant
	 *            EntityConstant
	 */
	public void toggleSort(EntityFieldHolder constant) {
		sortProperty = constant;
		for (Entry<EntityFieldHolder, SortOrder> entry : sortOrders.entrySet()) {
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
	
	/**
	 * Method gathers current requirements for dataTable as like sorting (field
	 * for sorting and sort order), filtering (fields and values) and pagination
	 * (first row and page size). Method returns {@link DataTableSearchHolder},
	 * that is a DTO holder for current requirements for dataTable.
	 * 
	 * @return DataTableSearchHolder instance.
	 */
	protected DataTableSearchHolder getCurrentRequirementsForDataTable() {
		deleteEmptyFilterValues();
		String sortorder = null;
		if (sortOrders.get(sortProperty) != null) {
			if (sortOrders.get(sortProperty).equals(SortOrder.ascending)) {
				sortorder = " ASC ";
			} else {
				sortorder = " DESC ";
			}
		}
		return new DataTableSearchHolder(getFirstRow(), getRowsPerPage(), sortProperty, sortorder, filterValues);
	}

	/**
	 * Method gets entity constants for current Entity.
	 * 
	 * @return EntityConstant
	 */
	public abstract EntityFieldHolder[] getEntityConstantInstances();

	/**
	 * Method is a action of selectAll. Method sets all entities in list as
	 * selected.
	 */
	public void selectAllAction() {
		System.out.println("select all action");

		for (T b : entities) {
			UIWrapper wrapp = (UIWrapper) b;
			if (getSelectAll()) {
				wrapp.setSelected(true);
			} else {
				wrapp.setSelected(false);
			}

		}
	}

	public void createListEntitiesToDelete() {
		System.out.println("createListEntitiesToDelete");
		listEntitiesToDelete = new ArrayList<T>();
		for (T b : entities) {
			UIWrapper wrapp = (UIWrapper) b;
			if (wrapp.isSelected()) {
				listEntitiesToDelete.add((T) wrapp);
			}
		}
	}

	/**
	 * Method runs on delete single entity action.
	 */
	public abstract void deleteEntity();

	/**
	 * Method provides bulk deleting operation for selected entities.
	 */
	public abstract void deleteListEntities();

	// getters and setters
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

	public List<T> getListEntitiesToDelete() {
		return listEntitiesToDelete;
	}

	public EntityFieldHolder getSortProperty() {
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

	public void setSortProperty(EntityFieldHolder sortPropety) {
		this.sortProperty = sortPropety;
	}

	public Map<EntityFieldHolder, SortOrder> getSortOrders() {
		log.error("getSortOrders");
		return sortOrders;
	}

	public Map<EntityFieldHolder, String> getFilterValues() {
		log.info("Current values for filtering = {}", filterValues.size());
		return filterValues;
	}

}
