package com.gorob.gui.binding;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

public class CheckboxBinding extends AbstractBinding {
	private Button target;
	private Object model;
	private String modelProperty;
	private ISelectionAction selectionAction;

	public CheckboxBinding(Button target, Object model, String modelProperty, ISelectionAction selectionAction) {
		this.target = target;
		this.model = model;
		this.modelProperty = modelProperty;
		this.selectionAction = selectionAction;
		this.bind();
	}
	
	private void bind() {
		getTarget().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateTargetToModel();
				
				if (getSelectionAction()!=null) {
					getSelectionAction().execute();
				}
			}
		});
	}

	public Button getTarget() {
		return target;
	}

	public Object getModel() {
		return model;
	}
	
	public String getModelProperty() {
		return modelProperty;
	}
	
	public ISelectionAction getSelectionAction() {
		return selectionAction;
	}
	
	public void updateModelToTarget() {
		Object value = getModelValue(getModel(), getModelProperty());
		getTarget().setSelection((Boolean)value);
	}

	public void updateTargetToModel() {
		setModelValue(getModel(), getModelProperty(), getTarget().getSelection());
	}
}
