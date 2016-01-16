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
import com.softserveinc.action.managebook.UIWrapper;
import com.softserveinc.model.persist.entity.BookConstantsHolder;
import com.softserveinc.model.persist.entity.EntityConstant;
import com.softserveinc.model.session.util.DataTableSearchHolder;

/**
 * This abstract class used to work with dataTable.
 * Class provides sorting and filtering possibilities
 * for dataTable. 
 *
 * @param <T> entity UIWrapper
 */
public abstract class DataTableHelper<T> extends PaginationHelper implements Serializable{


	private static final long serialVersionUID = -3683225876764573771L;

	private static Logger log = LoggerFactory.getLogger(DataTableHelper.class);

	private List<T> entities;
	private Map<EntityConstant, SortOrder> sortOrders = Maps.newHashMap();
	private Map<EntityConstant, String> filterValues = Maps.newHashMap();
	private EntityConstant sortProperty;
	private boolean selectAll;
	private String idEntityToDelete;
	private List<T> listEntitiesToDelete;

	protected DataTableHelper() {
		for (EntityConstant constant : getEntityConstantInstance().getListConstants()) {
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
	 * Method deletes from Map empty filter values or filter values,
	 * which starts with whitespace.
	 */
	private void deleteEmptyFilterValues() {
		for (Iterator<Map.Entry<EntityConstant, String>> it = filterValues.entrySet().iterator(); it.hasNext();) {
			Map.Entry<EntityConstant, String> entry = it.next();
			if (entry.getKey().equals(BookConstantsHolder.RATING) && entry.getValue().equals("0")) {
				it.remove();
			}
			if (entry.getValue().equals("") || entry.getValue().startsWith(" ")) {
				it.remove();
			}
		}
	}

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
	 * Method is a toggle for sorting columns.
	 * If method call for some column, method toggle sort Order.
	 * @param constant EntityConstant
	 */
	public void toggleSort(EntityConstant constant) {
		log.error("toggleSort");
		sortProperty = constant;
		for (Entry<EntityConstant, SortOrder> entry : sortOrders.entrySet()) {
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
	 * Method gathers current requirements for dataTable.
	 * @return DataTableSearchHolder instance.
	 */
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
	
	/**
	 * Method gets entity constants for current Entity.
	 * @return EntityConstant
	 */
	public abstract EntityConstant getEntityConstantInstance();

	/**
	 * Method is a action of selectAll. Method sets all entities in list
	 * as selected.
	 */
	public void selectAllAction() {
		System.out.println("select all action");
		
		for (T b :  entities) {
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
		for (T b :  entities) {
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

	public EntityConstant getSortProperty() {
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

	public void setSortProperty(EntityConstant sortPropety) {
		this.sortProperty = sortPropety;
	}

	public Map<EntityConstant, SortOrder> getSortOrders() {
		log.error("getSortOrders");
		return sortOrders;
	}

	public Map<EntityConstant, String> getFilterValues() {
		log.info("Current values for filtering = {}", filterValues.size());
		return filterValues;
	}

}
