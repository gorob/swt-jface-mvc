package com.gorob.gui.binding;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;

import com.gorob.gui.model.util.StringUtil;

public class ActionBinding extends AbstractBinding {
	private Button target;
	private SelectionAdapter action;
	private Object model;
	private String modelPropertyActionActive;
	

	public ActionBinding(Button target, Object model, String modelPropertyActionActive) {
		this(target, null, model, modelPropertyActionActive);
	}
		
	public ActionBinding(Button target, SelectionAdapter action, Object model, String modelPropertyActionActive) {
		this.target = target;
		this.action = action;
		this.model = model;
		this.modelPropertyActionActive = modelPropertyActionActive;
		this.bind();
	}
	
	private void bind() {
		if (getAction()!=null) {
			getTarget().addSelectionListener(getAction());			
		}
	}

	public void updateModelToTarget() {
		if (!StringUtil.isNullOrEmpty(getModelPropertyActionActive())) {
			Object value = getModelValue(getModel(), getModelPropertyActionActive());
			getTarget().setEnabled((Boolean)value);
		}
	}

	public void updateTargetToModel() {
		// nothing to do
	}

	public Button getTarget() {
		return target;
	}

	public Object getModel() {
		return model;
	}
	
	public String getModelPropertyActionActive() {
		return modelPropertyActionActive;
	}
	
	public SelectionAdapter getAction() {
		return action;
	}
}
