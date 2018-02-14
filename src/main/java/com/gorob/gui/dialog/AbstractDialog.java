package com.gorob.gui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.gorob.client.logging.AbstractLogging;
import com.gorob.gui.controller.AbstractController;
import com.gorob.gui.model.AbstractFacade;


@SuppressWarnings("rawtypes")
public abstract class AbstractDialog<C extends AbstractController, V extends Composite, M extends AbstractFacade, P extends IDialogParameter> extends Dialog {
	private static final int DEFAULT_DIALOG_BREITE = 800;
	private static final int DEFAULT_DIALOG_HOEHE = 600;
	
	private AbstractLogging log;
	private P dialogParameter;
	private C dialogController;
	private V dialogView;
	private M dialogModel;
	private String dialogTitel;
	private Button okButton;

	protected AbstractDialog(P dialogParameter, AbstractLogging log) {
		super(dialogParameter.isMainDialog() ? (Shell)null : new Shell());
		setShellStyle(getShellStyle() | SWT.MIN);
		this.dialogTitel = dialogParameter.getDialogTitel();
		this.dialogParameter = dialogParameter;
		this.log = log;
	}

	protected abstract C createDialogController(V dialogView, M dialogFacade, AbstractLogging log);
	protected abstract V createDialogView(Composite dialogAreaComposite);
	protected abstract M createDialogFacade(P dialogParameter, AbstractLogging log);
	
	public C getDialogController() {
		return dialogController;
	}
	
	public V getDialogView() {
		return dialogView;
	}
	
	public M getDialogFacade() {
		return dialogModel;
	}
	
	public P getDialogParameter() {
		return dialogParameter;
	}
	
	public String getDialogTitel() {
		return dialogTitel;
	}
	
	protected Button getOkButton() {
		return okButton;
	}
	
	protected String getOKLabelText() {
		return "Speichern";
	}

	protected String getAbbruchLabelText() {
		return "Abbruch";
	}
	
	public AbstractLogging getLog() {
		return log;
	}
	
	protected void log(String logText) {
		getLog().log(logText);
	}

	@Override
	public void okPressed() {
		saveDataBeforeClose();
		super.okPressed();
	}

	@Override
	public void cancelPressed() {
		super.cancelPressed();
	}
	
	protected void saveDataBeforeClose() {
		// in Subclass bei Bedarf überschreiben
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogAreaComposite = (Composite)super.createDialogArea(parent);
		((GridLayout)dialogAreaComposite.getLayout()).marginWidth = 0;
		((GridLayout)dialogAreaComposite.getLayout()).marginHeight = 0;
		this.dialogView = createDialogView(dialogAreaComposite);
		this.dialogModel = createDialogFacade(getDialogParameter(), getLog());
	    this.dialogController = createDialogController(this.dialogView, this.dialogModel, getLog());
		return dialogAreaComposite;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, getOKLabelText(), true);
		getDialogController().bindButton(getOkButton(), "speichernMoeglich");
		getOkButton().setData("OK_BUTTON", "true");
		getDialogController().updateModelToTargetOKButton();
		
		createButton(parent, IDialogConstants.CANCEL_ID, getAbbruchLabelText(), false);
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(getDialogTitel());
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	protected Point getInitialSize() {
		int breite = getDialogParameter().getInitialBreite()==-1 ? DEFAULT_DIALOG_BREITE : getDialogParameter().getInitialBreite();
		int hoehe = getDialogParameter().getInitialHoehe()==-1 ? DEFAULT_DIALOG_HOEHE : getDialogParameter().getInitialHoehe();
		return new Point(breite, hoehe);
	}
	
	@Override
	protected Point getInitialLocation(Point initialSize) {
		if (getDialogParameter().getInitialPositionX()==-1 && getDialogParameter().getInitialPositionY()==-1) {
			return super.getInitialLocation(initialSize);
		}
		
		return new Point(getDialogParameter().getInitialPositionX(), getDialogParameter().getInitialPositionY());
	}
}
