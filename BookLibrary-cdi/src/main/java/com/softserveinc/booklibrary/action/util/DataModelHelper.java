package com.softserveinc.booklibrary.action.util;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.ExtendedDataModel;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;



public abstract class DataModelHelper<T> extends ExtendedDataModel<T> implements Arrangeable {

	private ArrangeableState arrangeableState;
	private Object rowId;

	@Override
	public void arrange(FacesContext context, ArrangeableState state) {
		arrangeableState = state;
	}

	protected ArrangeableState getArrangeableState() {
		System.out.println("getArrangeableState");
		return arrangeableState;
	}
	
	@Override
	public void setRowKey(Object key) {
		this.rowId =  key;
		
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
