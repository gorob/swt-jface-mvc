package com.gorob.gui.binding;

public interface IBinding {
	void updateModelToTarget();
	void updateTargetToModel();
	Object getTarget();
}
