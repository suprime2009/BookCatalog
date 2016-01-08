package com.softserveinc.action.managebook;


public abstract class UIWrapper {
	
	private boolean selected;

	
	public UIWrapper() {

	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
}
