package com.gorob.gui.binding;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.gorob.client.view.SWTResourceManager;

public class TableViewerBinding extends AbstractBinding {
	private TableViewer target;
	private Object modelList; 
	private String modelPropertyList;
	private Object modelSelection; 
	private String modelPropertySelection;
	private String[] modelPropertiesColumnHeaders;
	private String[] modelPropertiesColumnValues;
	private int[] initialColumnWidths;
	private String[][] modelPropertiesImage;
	private String[][] modelPropertiesBackground;
	private String[][] modelPropertiesForeground;	
	private ISelectionAction selectionAction;
	private IDoubleClickAction doubleClickAction;

	public TableViewerBinding(TableViewer viewer, 
			                  Object model, 
			                  String modelPropertyList, 
			                  String modelPropertySelection,
			      			  String[] modelPropertiesColumnHeaders,
			    			  String[] modelPropertiesColumnValues,
			    			  int[] initialColumnWidths,
			    			  String[][] modelPropertiesImage,
			    			  String[][] modelPropertiesBackground, 
			    			  String[][] modelPropertiesForeground, 
			                  ISelectionAction selectionAction, 
			                  IDoubleClickAction doubleClickAction) {
		this(viewer, model, modelPropertyList, model, modelPropertySelection, 
				modelPropertiesColumnHeaders, modelPropertiesColumnValues, 
				initialColumnWidths, modelPropertiesImage, 
				modelPropertiesBackground, modelPropertiesForeground,
				selectionAction, doubleClickAction);
	}
	
	protected TableViewerBinding(TableViewer viewer, 
			Object modelList, String modelPropertyList, 
			Object modelSelection, String modelPropertySelection, 
			String[] modelPropertiesColumnHeaders,
			String[] modelPropertiesColumnValues,
			int[] initialColumnWidths,
			String[][] modelPropertiesImage,
			String[][] modelPropertiesBackground, 
			String[][] modelPropertiesForeground, 
			ISelectionAction selectionAction, 
			IDoubleClickAction doubleClickAction) {		
		this.target = viewer;
		this.modelList = modelList;
		this.modelPropertyList = modelPropertyList;
		this.modelSelection = modelSelection;
		this.modelPropertySelection = modelPropertySelection;
		this.modelPropertiesColumnHeaders = modelPropertiesColumnHeaders;
		this.modelPropertiesColumnValues = modelPropertiesColumnValues;
		this.initialColumnWidths = initialColumnWidths;
		this.modelPropertiesImage = modelPropertiesImage;
		this.modelPropertiesBackground = modelPropertiesBackground;
		this.modelPropertiesForeground = modelPropertiesForeground;		
		this.selectionAction = selectionAction;
		this.doubleClickAction = doubleClickAction;
		this.bind();
	}

	public Object getModelList() {
		return modelList;
	}
	
	public String getModelPropertyList() {
		return modelPropertyList;
	}
	
	public Object getModelSelection() {
		return modelSelection;
	}
	
	public String getModelPropertySelection() {
		return modelPropertySelection;
	}

	public String[] getModelPropertiesColumnHeaders() {
		return modelPropertiesColumnHeaders;
	}
	
	public String[] getModelPropertiesColumnValues() {
		return modelPropertiesColumnValues;
	}
	
	public int[] getInitialColumnWidths() {
		return initialColumnWidths;
	}
	
	public String[][] getModelPropertiesImage() {
		return modelPropertiesImage;
	}
	
	public String[][] getModelPropertiesBackground() {
		return modelPropertiesBackground;
	}
	
	public String[][] getModelPropertiesForeground() {
		return modelPropertiesForeground;
	}

	public TableViewer getTarget() {
		return target;
	}

	public ISelectionAction getSelectionAction() {
		return selectionAction;
	}
	
	public IDoubleClickAction getDoubleClickAction() {
		return doubleClickAction;
	}
		
	private void bind() {
		getTarget().setContentProvider(ArrayContentProvider.getInstance());
				
		for (int i=0; i<getModelPropertiesColumnHeaders().length; i++) {
			TableViewerColumn column = new TableViewerColumn(getTarget(), SWT.NONE);
			column.getColumn().setText(getModelPropertiesColumnHeaders()[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.getColumn().setAlignment(SWT.LEFT);
			column.getColumn().setWidth(getInitialColumnWidths()[i]);
			final int columnIndex = i;
			column.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					String modelProperty = getModelPropertiesColumnValues()[columnIndex];
					if (modelProperty==null || modelProperty.isEmpty()) { return null; }

					Object property = null;
					if (modelProperty.contains("(") && modelProperty.contains(")")) {
						int index = new Integer(modelProperty.substring(modelProperty.indexOf("(")+1, modelProperty.indexOf(")")));
						modelProperty = modelProperty.substring(0, modelProperty.indexOf("("));
						property = getModelValue(element, modelProperty, index);
					} else {
						property = getModelValue(element, modelProperty);						
					}
					
					return property==null ? "" : property.toString();
				}
				
				@Override
				public Image getImage(Object element) {
					if (getModelPropertiesImage()[columnIndex]==null || getModelPropertiesImage()[columnIndex].length==0) { return null; }
					
					int anzDecisions = getModelPropertiesImage()[columnIndex].length / 2;
					
					for (int i=0; i<anzDecisions; i++) {
						Boolean decision = (Boolean)getModelValue(element, getModelPropertiesImage()[columnIndex][i*2]);
						if (decision) { 
						    return SWTResourceManager.getImage(this.getClass(), getModelPropertiesImage()[columnIndex][(i*2)+1]);
						}
					}
					
					return null;
				}
				
				@Override
				public Color getBackground(Object element) {
					if (getModelPropertiesBackground()[columnIndex]==null || getModelPropertiesBackground()[columnIndex].length==0) { return null; }
					
					int anzDecisions = getModelPropertiesBackground()[columnIndex].length / 2;
					
					for (int i=0; i<anzDecisions; i++) {
						Boolean decision = (Boolean)getModelValue(element, getModelPropertiesBackground()[columnIndex][i*2]);
						if (decision) { 
						    String valueStr = getModelPropertiesBackground()[columnIndex][(i*2)+1];
						    
						    if (valueStr.contains(",")) {
						    	String[] colorValues = valueStr.split(",");
						    	return SWTResourceManager.getColor(new Integer(colorValues[0].trim()), new Integer(colorValues[1].trim()), new Integer(colorValues[2].trim()));
						    } else {
						    	return SWTResourceManager.getColor(new Integer(valueStr));						    	
						    }
						}
					}
					
					return null;
				}
				
				@Override
				public Color getForeground(Object element) {
					if (getModelPropertiesForeground()[columnIndex]==null || getModelPropertiesForeground()[columnIndex].length==0) { return null; }
					
					int anzDecisions = getModelPropertiesForeground()[columnIndex].length / 2;
					
					for (int i=0; i<anzDecisions; i++) {
						Boolean decision = (Boolean)getModelValue(element, getModelPropertiesForeground()[columnIndex][i*2]);
						if (decision) { 
						    String valueStr = getModelPropertiesForeground()[columnIndex][(i*2)+1];
						    
						    if (valueStr.contains(",")) {
						    	String[] colorValues = valueStr.split(",");
						    	return SWTResourceManager.getColor(new Integer(colorValues[0].trim()), new Integer(colorValues[1].trim()), new Integer(colorValues[2].trim()));
						    } else {
						    	return SWTResourceManager.getColor(new Integer(valueStr));						    	
						    }
						}
					}
					
					return null;
				}
			});
		}

		getTarget().setInput(getListFromModelList());
		
		getTarget().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				setSelectionInModel();
				
				if (getSelectionAction()!=null) {
					getSelectionAction().execute();
				}
			}
		});
		
		getTarget().addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				if (getDoubleClickAction()!=null) {
					getDoubleClickAction().execute();
				}
			}
		});		
	}
	
	private void setSelectionInModel() {
		setModelValue(getModelSelection(), getModelPropertySelection(), getTargetSelection());
	}

	protected boolean isMultiSelection() {
		return (SWT.MULTI==(getTarget().getTable().getStyle() & SWT.MULTI));
	}
	
	protected Object getTargetSelection() {
		if (isMultiSelection()) {
			return ((IStructuredSelection)getTarget().getSelection()).toList();
		} else {
			return ((IStructuredSelection)getTarget().getSelection()).getFirstElement();
		}
	}

	@SuppressWarnings("rawtypes")
	protected void setTargetSelection() {
		Object selectionFromModel = getSelectionFromModelSelection();
		StructuredSelection selection = (selectionFromModel==null) ? null : (isMultiSelection() ? new StructuredSelection(((List)selectionFromModel)) : new StructuredSelection(selectionFromModel));
		getTarget().setSelection(selection);
	}
	
	@SuppressWarnings("rawtypes")
	private List getListFromModelList() {
		return (List)getModelValue(getModelList(), getModelPropertyList());
	}
	
	private Object getSelectionFromModelSelection() {
		return getModelValue(getModelSelection(), getModelPropertySelection());
	}
	
	@Override
	public void updateModelToTarget() {
		getTarget().setInput(getListFromModelList());
		getTarget().refresh();
		setTargetSelection();
	}

	@Override
	public void updateTargetToModel() {
		setSelectionInModel();
	}
}
