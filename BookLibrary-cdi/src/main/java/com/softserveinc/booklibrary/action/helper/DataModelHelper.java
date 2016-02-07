package com.softserveinc.booklibrary.action.helper;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.ExtendedDataModel;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;

/**
 * This abstract class used for working with Richfaces dataTable. Class gives
 * possibility to use richfaces scroller.
 *
 * @param <T>
 *            Entity
 */
public abstract class DataModelHelper<T> extends ExtendedDataModel<T> implements Arrangeable {

	private ArrangeableState arrangeableState;
	private Object rowId;

	@Override
	public void arrange(FacesContext context, ArrangeableState state) {
		arrangeableState = state;
	}

	protected ArrangeableState getArrangeableState() {
		return arrangeableState;
	}

	@Override
	public void setRowKey(Object key) {
		this.rowId = key;

	}

	@Override
	public Object getRowKey() {
		return rowId;
	}

	@Override
	public boolean isRowAvailable() {
		return rowId != null;
	}

	@Override
	public int getRowIndex() {
		return -1;
	}

	@Override
	public void setRowIndex(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWrappedData(Object arg0) {
		throw new UnsupportedOperationException();
	}

}
