package com.gorob.gui.dialog;

public class AbstractDialogParameter implements IDialogParameter {
	private int initialBreite;
	private int initialHoehe;
	
	private int initialPositionX;
	private int initialPositionY;
	
	private boolean mainDialog;
	private String dialogTitel;

	public AbstractDialogParameter(String dialogTitel, int initialBreite, int initialHoehe) {
		this(dialogTitel, initialBreite, initialHoehe, -1, -1, false);
	}
	
	public AbstractDialogParameter(String dialogTitel, int initialBreite, int initialHoehe, int initialPositionX, int initialPositionY) {
		this(dialogTitel, initialBreite, initialHoehe, initialPositionX, initialPositionY, false);
	}
	
	public AbstractDialogParameter(String dialogTitel, int initialBreite, int initialHoehe, int initialPositionX, int initialPositionY, boolean mainDialog) {
		this.initialBreite = initialBreite;
		this.initialHoehe = initialHoehe;
		this.initialPositionX = initialPositionX;
		this.initialPositionY = initialPositionY;
		this.mainDialog = mainDialog;
		this.dialogTitel = dialogTitel;
	}

	@Override
	public int getInitialBreite() {
		return this.initialBreite;
	}
	
	public void setInitialBreite(int initialBreite) {
		this.initialBreite = initialBreite;
	}

	@Override
	public int getInitialHoehe() {
		return this.initialHoehe;
	}
	
	public void setInitialHoehe(int initialHoehe) {
		this.initialHoehe = initialHoehe;
	}
	
	@Override
	public int getInitialPositionX() {
		return initialPositionX;
	}
	
	public void setInitialPositionX(int initialPositionX) {
		this.initialPositionX = initialPositionX;
	}
	
	@Override
	public int getInitialPositionY() {
		return initialPositionY;
	}
	
	public void setInitialPositionY(int initialPositionY) {
		this.initialPositionY = initialPositionY;
	}
	
	@Override
	public String getDialogTitel() {
		return dialogTitel;
	}
	
	public void setDialogTitel(String dialogTitel) {
		this.dialogTitel = dialogTitel;
	}
	
	@Override
	public boolean isMainDialog() {
		return mainDialog;
	}
	
	public void setMainDialog(boolean mainDialog) {
		this.mainDialog = mainDialog;
	}
}
