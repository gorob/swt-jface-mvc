package com.gorob.gui.binding;

import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import com.gorob.gui.model.util.StringUtil;

@SuppressWarnings("rawtypes")
public class ButtonGroupBinding extends AbstractBinding {
	private List<Button> target;
	private Object model;
	private String modelProperty;
	private List modelValues;
	private String modelPropertyActionActive;
	private ISelectionAction selectionAction;
	
	public ButtonGroupBinding(List<Button> target, Object model, String modelProperty, List modelValues, String modelPropertyActionActive, ISelectionAction selectionAction) {
		this.target = target;
		this.model = model;
		this.modelProperty = modelProperty;
		this.modelValues = modelValues;
		this.modelPropertyActionActive = modelPropertyActionActive;
		this.selectionAction = selectionAction;
		this.checkAnzahl();
		this.bind();
	}
	
	public List<Button> getTarget() {
		return target;
	}
	
	public Object getModel() {
		return model;
	}
	
	public String getModelProperty() {
		return modelProperty;
	}
	
	public List getModelValues() {
		return modelValues;
	}
	
	public ISelectionAction getSelectionAction() {
		return selectionAction;
	}

	public String getModelPropertyActionActive() {
		return modelPropertyActionActive;
	}
	
	private void checkAnzahl() {
		if (getTarget().size()!=getModelValues().size()) {
			throw new RuntimeException("Given target count differs from given model count!");
		}
	}
	
	private void bind() {
		for (Button button : getTarget()) {
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateTargetToModel();

					if (getSelectionAction()!=null) {
						getSelectionAction().execute();
					}
				}
				
			});
		}
	}
		
	@Override
	public void updateModelToTarget() {
		deselectAllButtons();

		int index = getModelValues().indexOf(getModelValue(getModel(), getModelProperty()));
		
		if (index>=0) {
			getTarget().get(index).setSelection(true);
		}
		
		if (!StringUtil.isNullOrEmpty(getModelPropertyActionActive())) {
			Object value = getModelValue(getModel(), getModelPropertyActionActive());
			
			for (Button button : getTarget()) {
				button.setEnabled((Boolean)value);				
			}
		}
	}
	
	private void deselectAllButtons() {
		for (Button	button : getTarget()) {
			button.setSelection(false);
		}		
	}

	@Override
	public void updateTargetToModel() {
        int index = getIndexSelectedTarget();
        
        Object value;
        
        if (index==-1) {
        	String parameterTypName = getModelGetterValueType(getModel(), getModelProperty());
        	
		    if (parameterTypName.equals("int")) {
			    value = 0;
		    } else if (parameterTypName.equals("long")) {
			    value = 0L;
		    } else {
		    	value = null;
		    }
		} else {
			value = getModelValues().get(index);
		}

        setModelValue(getModel(), getModelProperty(), value);
	}
	
	private int getIndexSelectedTarget() {
		for (int i=0; i<getTarget().size(); i++) {
			if (getTarget().get(i).getSelection()) {
				return i;
			}
		}
		return -1;
	}

}
