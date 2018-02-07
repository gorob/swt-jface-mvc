package com.gorob.gui.binding;

import java.util.List;

import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

import com.gorob.gui.model.ILabeledModel;
import com.gorob.gui.model.util.Datum;
import com.gorob.gui.model.util.StringUtil;
import com.gorob.gui.model.util.Timestamp;

public class ListViewerBinding extends AbstractBinding {
	private AbstractListViewer target;
	private Object model; 
	private String modelPropertyList;
	private String modelPropertySelection;
	private String modelPropertyEnabled;
	private ISelectionAction selectionAction;

	
	public ListViewerBinding(AbstractListViewer viewer, Object model, String modelPropertyList, String modelPropertySelection) {
		this(viewer, model, modelPropertyList, modelPropertySelection, null);
	}

	public ListViewerBinding(AbstractListViewer viewer, Object model, String modelPropertyList, String modelPropertySelection, ISelectionAction selectionAction) {
		this(viewer, model, modelPropertyList, modelPropertySelection, null, selectionAction);		
	}

	public ListViewerBinding(AbstractListViewer viewer, Object model, String modelPropertyList, String modelPropertySelection, String modelPropertyEnabled, ISelectionAction selectionAction) {
		this.target = viewer;
		this.model = model;
		this.modelPropertyList = modelPropertyList;
		this.modelPropertySelection = modelPropertySelection;
		this.modelPropertyEnabled = modelPropertyEnabled;
		this.selectionAction = selectionAction;
		this.bind();
	}

	public Object getModel() {
		return model;
	}
	
	public String getModelPropertyList() {
		return modelPropertyList;
	}
	
	public String getModelPropertySelection() {
		return modelPropertySelection;
	}
	
	public String getModelPropertyEnabled() {
		return modelPropertyEnabled;
	}

	public AbstractListViewer getTarget() {
		return target;
	}

	public ISelectionAction getSelectionAction() {
		return selectionAction;
	}
	
	private void bind() {
		getTarget().setContentProvider(ArrayContentProvider.getInstance());
		getTarget().setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ILabeledModel) {
					return ((ILabeledModel)element).getLabelText();
				}
				return super.getText(element);
			}
		});
		getTarget().setInput(getListFromModel());
		
		getTarget().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateTargetToModel();
				
				if (getSelectionAction()!=null) {
					getSelectionAction().execute();
				}
			}
		});
		
		if (isEditierbarerComboViewer()) {
			final ComboViewer comboViewer = (ComboViewer)getTarget();
			
			comboViewer.getControl().addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					onFocusLostComboViewer();
				}
			});			
		}
	}
	
	protected void onFocusLostComboViewer() {
		final ComboViewer comboViewer = (ComboViewer)getTarget();
		comboViewer.getCombo().clearSelection();					
		updateTargetToModel();  // Eingaben in Model schreiben lassen
		updateModelToTarget();  // Änderungen Model-Wert (z.B. Leerzeichen per Trim raus) sichtbar machen		
	}
	
	protected boolean isEditierbarerComboViewer() {
		return (getTarget() instanceof ComboViewer) && !(SWT.READ_ONLY==(getTarget().getControl().getStyle() & SWT.READ_ONLY));
	}
	
	protected boolean isTargetValueEmpty() {
		Object targetValue = getTargetValue();
		
		if ((targetValue instanceof String) && StringUtil.isNullOrEmpty((String)targetValue)) {
			return true;
		}
		
		return targetValue==null;
	}

	protected Object getTargetSelection() {
		return ((IStructuredSelection)getTarget().getSelection()).getFirstElement();
	}
	
	protected Object getTargetValue() {
		String parameterTypName = getModelGetterValueType(getModel(), getModelPropertySelection());
		String eingabeText = ((ComboViewer)getTarget()).getCombo().getText();
		
		Object value = null;
		if (parameterTypName.equals("String")) {
			value = eingabeText==null ? null : eingabeText.trim();
		} else if (parameterTypName.equals("int")) {
			value = convertToInt(eingabeText);
		} else if (parameterTypName.equals("Integer")) {
			value = convertToInteger(eingabeText);
		} else if (parameterTypName.equals("long")) {
			value = convertToLong(eingabeText);
		} else if (parameterTypName.equals("Long")) {
			value = convertToLongGross(eingabeText);
		} else if (parameterTypName.equals("Datum")) {
			value = Datum.convertToDatum(eingabeText);
		} else if (parameterTypName.equals("Timestamp")) {
			value = Timestamp.convertToTimestamp(eingabeText);
		}

		return value;
	}

	private Integer convertToInteger(String text) {
		try {
			text = StringUtil.isNullOrEmpty(text) ? "" : text.trim();
			return new Integer(text);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	private int convertToInt(String text) {
		Integer value = convertToInteger(text);
		return value==null ? 0 : value;
	}

	private long convertToLong(String text) {
		Long value = convertToLongGross(text);
		return value==null ? 0 : value;
	}

	private Long convertToLongGross(String text) {
		try {
			text = StringUtil.isNullOrEmpty(text) ? "" : text.trim();
			return new Long(text);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	protected void setTargetSelection() {
		Object selectionFromModel = getSelectionFromModel();
		StructuredSelection selection = (selectionFromModel==null) ? null : new StructuredSelection(selectionFromModel);
		getTarget().setSelection(selection);
	}
	
	private Object getListFromModel() {
		return getModelValue(getModel(), getModelPropertyList());
	}
	
	private Object getSelectionFromModel() {
		return getModelValue(getModel(), getModelPropertySelection());
	}
	
	private Boolean getEnabledStateFromModel() {
		return (Boolean)getModelValue(getModel(), getModelPropertyEnabled());
	}
	
	@Override
	public void updateModelToTarget() {
		getTarget().setInput(getListFromModel());
		getTarget().refresh();
		setTargetSelection();
		
		if (getModelPropertyEnabled()!=null) {
			getTarget().getControl().setEnabled(getEnabledStateFromModel());			
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateTargetToModel() {
		if (isEditierbarerComboViewer()) {
			Object targetValue = getTargetValue();
			setModelValue(getModel(), getModelPropertySelection(), targetValue);
			
			List listFromModel = (List)getListFromModel();
			
			if (!isTargetValueEmpty() && !listFromModel.contains(targetValue)) {
				listFromModel.add(targetValue);
			}
		} else {
			setModelValue(getModel(), getModelPropertySelection(), getTargetSelection());
		}
	}
}
