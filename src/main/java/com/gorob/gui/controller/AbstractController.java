package com.gorob.gui.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.gorob.client.logging.AbstractLogging;
import com.gorob.gui.binding.ActionBinding;
import com.gorob.gui.binding.ButtonGroupBinding;
import com.gorob.gui.binding.CheckboxBinding;
import com.gorob.gui.binding.IBinding;
import com.gorob.gui.binding.IDoubleClickAction;
import com.gorob.gui.binding.ISelectionAction;
import com.gorob.gui.binding.LabelBinding;
import com.gorob.gui.binding.ListViewerBinding;
import com.gorob.gui.binding.SektionBinding;
import com.gorob.gui.binding.TableViewerBinding;
import com.gorob.gui.binding.TextBinding;
import com.gorob.gui.binding.TextUpdateStrategy;
import com.gorob.gui.binding.TreeViewerBinding;
import com.gorob.gui.dialog.AbstractDialog;
import com.gorob.gui.model.AbstractFacade;

@SuppressWarnings("rawtypes")
public abstract class AbstractController<V extends Composite, F extends AbstractFacade> {
	AbstractDialog parentDialog;
	
	private AbstractLogging log;
	
	private V view;
	private F facade;
	
	private List<IBinding> bindings;

	public AbstractController(V view, F facade, AbstractLogging log) {
		this(view, facade, null, log);
	}
	
	public AbstractController(V view, F facade, AbstractDialog parentDialog, AbstractLogging log) {
		this.view = view;
		this.facade = facade;
		this.parentDialog = parentDialog;
		this.log = log;
		this.bindings = new ArrayList<IBinding>();

		this.initModel();
		this.bindControls(this.view);
		this.initController();
		this.updateModelToTarget();
	}
	
	public AbstractLogging getLog() {
		return log;
	}
	
	protected void log(String logText) {
		getLog().log(logText);
	}
	
	public V getView() {
		return view;
	}
	
	public F getFacade() {
		return facade;
	}
	
	public AbstractDialog getParentDialog() {
		return parentDialog;
	}
	
	public List<IBinding> getBindings() {
		return bindings;
	}
	
	protected abstract void bindControls(V view);

	protected void initController() {
	   // Standard keine Aktion
	}
	
	protected final void initModel() {
		this.getFacade().initFacadeWithParameter();
	}
	
	protected void updateModelToTarget() {
		for (IBinding binding : getBindings()) {
			binding.updateModelToTarget();
		}
	}

	protected void updateModelToTarget(Object target) {
		IBinding binding = getBindingForTarget(target);
		
		if (binding!=null) {
			binding.updateModelToTarget();
		}
	}

	protected void updateTargetToModel() {
		for (IBinding binding : getBindings()) {
			binding.updateTargetToModel();
		}
	}
	
	public void updateModelToTargetOKButton() {
		for (IBinding binding : getBindings()) {
			if (((binding.getTarget() instanceof Button) && ((Button)binding.getTarget()).getData("OK_BUTTON")!=null)) {
				binding.updateModelToTarget();
			}
		}
		
	}

	protected void updateTargetToModel(Object target) {
		IBinding binding = getBindingForTarget(target);

		if (binding!=null) {
			binding.updateTargetToModel();
		}
	}

	protected IBinding getBindingForTarget(Object target) {
		for (IBinding binding : getBindings()) {
			if (binding.getTarget() instanceof List) {
				for (Object singleTarget : (List)binding.getTarget()) {
					if (singleTarget==target) {
						return binding;
					}									
				}
			} else {
				if (binding.getTarget()==target) {
					return binding;
				}				
			}
		}
		return null;
	}
	
	protected void bindViewer(AbstractListViewer viewer, String modelPropertyList, String modelPropertySelection, String modelPropertyEnabled) {
		bindViewer(viewer, getFacade(), modelPropertyList, modelPropertySelection, modelPropertyEnabled, null);
	}

	protected void bindViewer(AbstractListViewer viewer, String modelPropertyList, String modelPropertySelection) {
		bindViewer(viewer, modelPropertyList, modelPropertySelection, (ISelectionAction)null);
	}

	protected void bindViewer(AbstractListViewer viewer, String modelPropertyList, String modelPropertySelection, ISelectionAction selectionAction) {
		bindViewer(viewer, getFacade(), modelPropertyList, modelPropertySelection, null, selectionAction);
	}

	protected void bindViewer(AbstractListViewer viewer, String modelPropertyList, String modelPropertySelection, String modelPropertyEnabled, ISelectionAction selectionAction) {
		bindViewer(viewer, getFacade(), modelPropertyList, modelPropertySelection, modelPropertyEnabled, selectionAction);
	}

	protected void bindViewer(AbstractListViewer viewer, Object model, String modelPropertyList, String modelPropertySelection, String modelPropertyEnabled, ISelectionAction selectionAction) {
		getBindings().add(new ListViewerBinding(viewer, model, modelPropertyList, modelPropertySelection, modelPropertyEnabled, selectionAction));
	}

	protected void bindTableViewer(TableViewer viewer, String modelPropertyList, String modelPropertySelection,
			String[] modelPropertiesColumnHeaders, String[] modelPropertiesColumnValues, 
			int[] initialColumnWidths, String[][] modelPropertiesImage, 
			String[][] modelPropertiesBackground, String[][] modelPropertiesForeground) {
		bindTableViewer(viewer, modelPropertyList, modelPropertySelection, modelPropertiesColumnHeaders, modelPropertiesColumnValues, initialColumnWidths, modelPropertiesImage, modelPropertiesBackground, modelPropertiesForeground, null, null);
	}
	
	protected void bindTableViewer(TableViewer viewer, String modelPropertyList, String modelPropertySelection, 
			String[] modelPropertiesColumnHeaders, String[] modelPropertiesColumnValues, 
			int[] initialColumnWidths, String[][] modelPropertiesImage, 
			String[][] modelPropertiesBackground, String[][] modelPropertiesForeground, 
			ISelectionAction selectionAction, IDoubleClickAction doubleClickAction) {
		bindTableViewer(viewer, getFacade(), modelPropertyList, modelPropertySelection, modelPropertiesColumnHeaders, modelPropertiesColumnValues, initialColumnWidths, modelPropertiesImage, modelPropertiesBackground, modelPropertiesForeground, selectionAction, doubleClickAction);
	}
	
	protected void bindTableViewer(TableViewer viewer, Object model, String modelPropertyList, String modelPropertySelection, String[] modelPropertiesColumnHeaders,
			String[] modelPropertiesColumnValues, int[] initialColumnWidths, String[][] modelPropertiesImage, String[][] modelPropertiesBackground, String[][] modelPropertiesForeground, ISelectionAction selectionAction, IDoubleClickAction doubleClickAction) {
		getBindings().add(new TableViewerBinding(viewer, model, modelPropertyList, modelPropertySelection, modelPropertiesColumnHeaders, modelPropertiesColumnValues, initialColumnWidths, modelPropertiesImage, modelPropertiesBackground, modelPropertiesForeground, selectionAction, doubleClickAction));
	}

	protected void bindTreeViewer(TreeViewer viewer, Object model, String modelPropertyTree, String modelPropertySelection, String[] modelPropertiesColumnHeaders,
			String[] modelPropertiesColumnValues, int[] initialColumnWidths, String[][] modelPropertiesImage, String[][] modelPropertiesBackground, String[][] modelPropertiesForeground, ISelectionAction selectionAction, IDoubleClickAction doubleClickAction,
			Object modelKontextMenu, String modelPropertyKontextMenuActions) { 
		getBindings().add(new TreeViewerBinding(viewer, model, modelPropertyTree, model, modelPropertySelection, 
				modelPropertiesColumnHeaders, modelPropertiesColumnValues, initialColumnWidths, 
				modelPropertiesImage, modelPropertiesBackground, modelPropertiesForeground, 
				selectionAction, doubleClickAction, modelKontextMenu, modelPropertyKontextMenuActions));
	}
	
	protected void bindText(Text text, String property) {
		bindText(text, getFacade(), property);
	}

	protected void bindText(Text text, String property, String enabledProperty) {
		bindText(text, getFacade(), property, enabledProperty);
	}

	protected void bindText(Text text, String property, ISelectionAction onUpdateModelToTargetAction) {
		bindText(text, property, null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, onUpdateModelToTargetAction);
	}

	protected void bindText(Text text, String property, TextUpdateStrategy textUpdateStrategy, ISelectionAction onUpdateModelToTargetAction) {
		bindText(text, property, null, textUpdateStrategy, onUpdateModelToTargetAction);
	}

	protected void bindText(Text text, String property, String enabledProperty, TextUpdateStrategy textUpdateStrategy, ISelectionAction onUpdateModelToTargetAction) {
		bindText(text, getFacade(), property, enabledProperty, textUpdateStrategy, onUpdateModelToTargetAction);
	}

	protected void bindText(Text text, Object model, String property, String enabledProperty, TextUpdateStrategy textUpdateStrategy, ISelectionAction onUpdateModelToTargetAction) {
		getBindings().add(new TextBinding(text, model, property, enabledProperty, textUpdateStrategy, onUpdateModelToTargetAction));
	}

	protected void bindText(Text text, Object model, String property, String enabledProperty) {
		getBindings().add(new TextBinding(text, model, property, enabledProperty, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null));
	}

	protected void bindText(Text text, Object model, String property) {
		getBindings().add(new TextBinding(text, model, property, null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null));
	}
	
	protected void bindLabel(Label label, String property) {
		bindLabel(label, getFacade(), property);
	}

	protected void bindLabel(Label label, String property, String enabledProperty) {
		bindLabel(label, getFacade(), property, enabledProperty);
	}

	protected void bindLabel(Label label, Object model, String property) {
		bindLabel(label, model, property, null);
	}

	protected void bindLabel(Label label, Object model, String property, String enabledProperty) {
		getBindings().add(new LabelBinding(label, model, property, enabledProperty));
	}

	protected void bindButton(Button button, Object model, String enabledProperty) {
		bindButton(button, null, model, enabledProperty);
	}
	
	public void bindButton(Button button, String enabledProperty) {
		bindButton(button, null, enabledProperty);
	}
	
	protected void bindButton(Button button, SelectionAdapter action, Object model, String enabledProperty) {
		getBindings().add(new ActionBinding(button, action, model, enabledProperty));
	}

	protected void bindButton(Button button, SelectionAdapter action, String enabledProperty) {
		getBindings().add(new ActionBinding(button, action, getFacade(), enabledProperty));
	}

	protected void bindButtonGroup(List<Button> target, String modelProperty, List modelValues, String enabledProperty, ISelectionAction selectionAction) {
		bindButtonGroup(target, getFacade(), modelProperty, modelValues, enabledProperty, selectionAction);
	}
	
	protected void bindButtonGroup(List<Button> target, Object model, String modelProperty, List modelValues, String enabledProperty, ISelectionAction selectionAction) {
		getBindings().add(new ButtonGroupBinding(target, model, modelProperty, modelValues, enabledProperty, selectionAction));
	}

	protected void bindSektion(Group sektion, String modelPropertySektionTitel) {
		bindSektion(sektion, modelPropertySektionTitel, null);
	}
	
	protected void bindSektion(Group sektion, String modelPropertySektionTitel, String enabledProperty) {
		bindSektion(sektion, getFacade(), modelPropertySektionTitel, enabledProperty);
	}
	
	protected void bindSektion(Group sektion, Object model, String modelPropertySektionTitel, String enabledProperty) {
		getBindings().add(new SektionBinding(sektion, model, modelPropertySektionTitel, enabledProperty));
	}
	
	protected void bindCheckbox(Button target, String modelProperty, ISelectionAction selectionAction) {
		bindCheckbox(target, getFacade(), modelProperty, selectionAction);
	}

	protected void bindCheckbox(Button target, Object model, String modelProperty, ISelectionAction selectionAction) {
		getBindings().add(new CheckboxBinding(target, model, modelProperty, selectionAction));
	}
	
	private class ProgressResult {
		public ProgressResult() {
			this.result = true;
			this.errorMsg = "";
		}
		
		public boolean result;
		public String errorMsg;
	}

	private Shell getViewShell() {
		return getView()==null ? Display.getDefault().getActiveShell() : getView().getShell();
	}
	
	public void showProgress(final String messageText, final IProgressAction action) {
		final ProgressResult progressResult = new ProgressResult();

		try {
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(getViewShell());
			progressMonitorDialog.run(true, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.setTaskName("task1");
					monitor.beginTask(messageText, IProgressMonitor.UNKNOWN);
					
					try {
						action.execute();
						progressResult.result = true;
					} catch (RuntimeException ex) {
						log(ex.getMessage());
						progressResult.result = false;
						progressResult.errorMsg = ex.getMessage();
					} finally {
						monitor.done();						
					}
				}
			});			
		} catch (InvocationTargetException ex) {
			progressResult.result = false;
			progressResult.errorMsg = ex.getMessage();
		} catch (InterruptedException ex) {
			progressResult.result = true;
		}

		if (!progressResult.result) {
			MessageDialog.openError(getView().getShell(), "Error", "Error with action '" +  messageText + "': " + progressResult.errorMsg);
		}
	}
}
