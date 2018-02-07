package com.gorob.gui.binding;

import org.eclipse.swt.widgets.Label;

public class LabelBinding extends AbstractBinding {
	private Label target;
	private Object model; 
	private String modelProperty;
	private String enabledProperty;

	public LabelBinding(Label label, Object model, String modelProperty) {
		this(label, model, modelProperty, null);
	}

	public LabelBinding(Label label, Object model, String modelProperty, String enabledProperty) {
		this.target = label;
		this.model = model;
		this.modelProperty = modelProperty;
		this.enabledProperty = enabledProperty;
		checkGetterExists();
	}
	
	public Object getModel() {
		return model;
	}
	
	public String getModelProperty() {
		return modelProperty;
	}
	
	public String getEnabledProperty() {
		return enabledProperty;
	}
	
	public Label getTarget() {
		return target;
	}
	
	protected void checkGetterExists() {
		if (getModelProperty()!=null) {
			getGetterMethod(getModel(), getModelProperty());			
		}
		if (getEnabledProperty()!=null) {
			getGetterMethod(getModel(), getEnabledProperty());			
		}
	}
	
	public void updateModelToTarget() {
		if (getModelProperty()!=null) {
			getTarget().setText(getModelValue(getModel(), getModelProperty()).toString());			
		}
		
		if (getEnabledProperty()!=null) {
			getTarget().setEnabled((Boolean)getModelValue(getModel(), getEnabledProperty()));			
		}
	}
	
	public void updateTargetToModel() {
	}
}
