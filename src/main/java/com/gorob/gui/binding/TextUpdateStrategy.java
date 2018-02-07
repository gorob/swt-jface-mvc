package com.gorob.gui.binding;

public enum TextUpdateStrategy {
	DIREKT_WRITING, UPDATE_ON_FOCUS_LOST, UPDATE_ON_FOCUS_LOST_OR_ENTER;
	
	public boolean isDirectWriting() {
		return this.equals(DIREKT_WRITING);
	}
	
	public boolean isUpdateOnFocusLost() {
		return this.equals(UPDATE_ON_FOCUS_LOST);
	}

	
	public boolean isUpdateOnFocusLostOrEnter() {
		return this.equals(UPDATE_ON_FOCUS_LOST_OR_ENTER);
	}

}
