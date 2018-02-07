package com.gorob.gui.binding;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class SektionBinding extends AbstractBinding {
	private Composite target;
	private Object model; 
	private String modelPropertySektionTitel;
	private String sektionEnabledProperty;

	public SektionBinding(Group sektionComposite, Object model, String enabledProperty) {
		this((Composite)sektionComposite, model, enabledProperty);
	}

	public SektionBinding(Composite sektionComposite, Object model, String enabledProperty) {
		this(sektionComposite, model, null, enabledProperty);
	}
	
	public SektionBinding(Group sektionComposite, Object model, String modelPropertySektionTitel, String enabledProperty) {
		this((Composite)sektionComposite, model, modelPropertySektionTitel, enabledProperty);
	}

	private SektionBinding(Composite sektionComposite, Object model, String modelPropertySektionTitel, String enabledProperty) {
		this.target = sektionComposite;
		this.model = model;
		this.modelPropertySektionTitel = modelPropertySektionTitel;
		this.sektionEnabledProperty = enabledProperty;
		checkGetterExists();
	}

	public Object getModel() {
		return model;
	}

	public String getModelPropertySektionTitel() {
		return modelPropertySektionTitel;
	}
	
	public String getSektionEnabledProperty() {
		return sektionEnabledProperty;
	}

	@Override
	public Composite getTarget() {
		return target;
	}
	
	protected void checkGetterExists() {
		if (getModelPropertySektionTitel()!=null) {
			getGetterMethod(getModel(), getModelPropertySektionTitel());			
		}
		if (getSektionEnabledProperty()!=null) {
			getGetterMethod(getModel(), getSektionEnabledProperty());			
		}
	}
	
	public void updateModelToTarget() {
		if (getModelPropertySektionTitel()!=null && (getTarget() instanceof Group)) {
			((Group)getTarget()).setText(" " + getModelValue(getModel(), getModelPropertySektionTitel()).toString() + " ");			
		}
		
		if (getSektionEnabledProperty()!=null) {
			getTarget().setEnabled((Boolean)getModelValue(getModel(), getSektionEnabledProperty()));			
		}
	}
	
	public void updateTargetToModel() {
	}
}
