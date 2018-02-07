package com.gorob.gui.binding;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;

import com.gorob.client.view.SWTResourceManager;
import com.gorob.gui.model.ITreeNode;
import com.gorob.gui.model.util.StringUtil;

public class TreeViewerBinding extends AbstractBinding {
	private TreeViewer target;
	private Object modelTree; 
	private String modelPropertyTree;
	private Object modelSelection;
	private String modelPropertySelection;
	private String[] modelPropertiesColumnHeaders;
	private String[] modelPropertiesColumnValues;
	private int[] initialColumnWidths;
	private String[][] modelPropertiesImage;
	private String[][] modelPropertiesBackground;
	private String[][] modelPropertiesForeground;
	private ISelectionAction selectionAction;
	private Object modelKontextMenu;
	private String modelPropertyKontextMenuActions;
	private IDoubleClickAction doubleClickAction;
	
	public TreeViewerBinding(TreeViewer viewer, 
			Object modelTree, String modelPropertyTree, 
			Object modelSelection, String modelPropertySelection,
			String[] modelPropertiesColumnHeaders,
			String[] modelPropertiesColumnValues,
			int[] initialColumnWidths,
			String[][] modelPropertiesImage,
			String[][] modelPropertiesBackground, 
			String[][] modelPropertiesForeground, 
			ISelectionAction selectionAction, 
			IDoubleClickAction doubleClickAction,
			Object modelKontextMenu,
			String modelPropertyKontextMenuActions) {		
		this.target = viewer;
		this.modelTree = modelTree;
		this.modelPropertyTree = modelPropertyTree;
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
		this.modelKontextMenu = modelKontextMenu;
		this.modelPropertyKontextMenuActions = modelPropertyKontextMenuActions;
		this.bind();
	}

	public Object getModelTree() {
		return modelTree;
	}
	
	public String getModelPropertyTree() {
		return modelPropertyTree;
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
	
	public TreeViewer getTarget() {
		return target;
	}

	public ISelectionAction getSelectionAction() {
		return selectionAction;
	}
	
	public IDoubleClickAction getDoubleClickAction() {
		return doubleClickAction;
	}
	
	public Object getModelKontextMenu() {
		return modelKontextMenu;
	}

	public String getModelPropertyKontextMenuActions() {
		return modelPropertyKontextMenuActions;
	}
	
	private void bind() {
		getTarget().setContentProvider(new TreeContentProvider());
		
		for (int i=0; i<getModelPropertiesColumnHeaders().length; i++) {
			TreeViewerColumn column = new TreeViewerColumn(getTarget(), SWT.NONE);
			column.getColumn().setText(getModelPropertiesColumnHeaders()[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.getColumn().setAlignment(SWT.LEFT);
			column.getColumn().setWidth(getInitialColumnWidths()[i]);
			final int columnIndex = i;
			column.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					if (getModelPropertiesColumnValues()[columnIndex]==null || getModelPropertiesColumnValues()[columnIndex].isEmpty()) { return null; }
					Object property = getModelValue(element, getModelPropertiesColumnValues()[columnIndex]);
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
		
		getTarget().setAutoExpandLevel(1);
		getTarget().setInput(getTreeInut());
		
//		for (TreeColumn column : getTarget().getTree().getColumns()) {
//			column.pack();
//		}
		
		getTarget().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				setSelectionInModel();
				
				if (getSelectionAction()!=null) {
					getSelectionAction().execute();
				}
				
				if (getModelPropertyKontextMenuActions()!=null) {
					List<Action> kontextMenuActions = getKontextMenuActions();
					
					if (kontextMenuActions.isEmpty()) {
						getTarget().getControl().setMenu(null);
						return;
					}
					
					MenuManager menuMgr = new MenuManager();
					Menu menu = menuMgr.createContextMenu(getTarget().getControl());
					getTarget().getControl().setMenu(menu);
					
					for (Action kontextMenuAction : kontextMenuActions) {
						menuMgr.add(kontextMenuAction);						
					}
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

	private class TreeContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			return ((ITreeNode) inputElement).getChildTreeNodes().toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element == null) { return null; }
			return ((ITreeNode) element).getParentTreeNode();
		}

		@Override
		public boolean hasChildren(Object element) {
			return !((ITreeNode) element).getChildTreeNodes().isEmpty();
		}
	}
	
	private Object getTreeInut() {
		return StringUtil.isNullOrEmpty(getModelPropertyTree()) ? getModelTree() : (Object)getModelValue(getModelTree(), getModelPropertyTree());
	}
	
	private void setSelectionInModel() {
		setModelValue(getModelSelection(), getModelPropertySelection(), getTargetSelection());
	}
	
	protected Object getTargetSelection() {
		return ((IStructuredSelection)getTarget().getSelection()).getFirstElement();
	}
	
	protected void setTargetSelection() {
		Object selectionFromModel = getSelectionFromModelSelection();
		StructuredSelection selection = (selectionFromModel==null) ? null : new StructuredSelection(selectionFromModel);
		getTarget().setSelection(selection);

		Tree tree = ((Tree)getTarget().getControl());
		if (tree.getSelectionCount()>0) {
			tree.setTopItem(tree.getSelection()[0]);			
		}
	}
	
	private Object getSelectionFromModelSelection() {
		return getModelValue(getModelSelection(), getModelPropertySelection());
	}
	
	@Override
	public void updateModelToTarget() {
		getTarget().setInput(getTreeInut());
		getTarget().refresh();
		setTargetSelection();
	}

	@Override
	public void updateTargetToModel() {
		setSelectionInModel();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Action> getKontextMenuActions() {
		if (getModelPropertyKontextMenuActions()==null) {
			return new ArrayList<Action>();
		}

		Object modelValue = getModelValue(getModelKontextMenu(), getModelPropertyKontextMenuActions());
		
		if (!(modelValue instanceof List) || ((List)modelValue).isEmpty() || !(((List)modelValue).get(0) instanceof Action)) {
			return new ArrayList<Action>();
		}
		
		return (List<Action>)modelValue;
	}
	
}
