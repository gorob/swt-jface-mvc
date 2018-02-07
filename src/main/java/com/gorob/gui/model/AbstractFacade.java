package com.gorob.gui.model;

import com.gorob.client.logging.AbstractLogging;
import com.gorob.gui.dialog.IDialogParameter;

public abstract class AbstractFacade<P extends IDialogParameter> {
	private P dialogParameter;
	private AbstractLogging log;
	
	public AbstractFacade(P dialogParameter, AbstractLogging log) {
		this.dialogParameter = dialogParameter;
		this.log = log;
	}
	
	public P getDialogParameter() {
		return dialogParameter;
	}
	
	public AbstractLogging getLog() {
		return log;
	}
	
	protected void log(String logText) {
		getLog().log(logText);
	}

	public final void initFacadeWithParameter() {
		this.initFacade(getDialogParameter());
	}

	public void initFacade(P dialogParameter) {
		// overwrite in subclass if necessary
	}
}
