package com.softserveinc.action.managebook;

/**
 * Class is a common Wrapper for entities.
 * May use in UI component.
 * Class provides possibility to select entity on UI.
 *
 */
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
