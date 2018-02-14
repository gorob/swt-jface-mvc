package com.gorob.gui.binding;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.gorob.gui.model.util.Datum;
import com.gorob.gui.model.util.StringUtil;
import com.gorob.gui.model.util.Timestamp;

public class TextBinding extends AbstractBinding {
	private Text target;
	private Object model; 
	private String modelProperty;
	private String enabledProperty;
	private TextUpdateStrategy textUpdateStrategy;
	private ISelectionAction onUpdateModelToTargetAction;

	public TextBinding(Text text, Object model, String modelProperty, String enabledProperty, TextUpdateStrategy textUpdateStrategy, ISelectionAction onUpdateModelToTargetAction) {
		this.target = text;
		this.model = model;
		this.modelProperty = modelProperty;
		this.enabledProperty = enabledProperty;
		this.textUpdateStrategy = textUpdateStrategy;
		this.onUpdateModelToTargetAction = onUpdateModelToTargetAction;
		checkSetterExists();
		checkGetterExists();
		bind();
	}
	
	private void bind() {
		if (getTextUpdateStrategy().isDirectWriting()) {
			getTarget().addKeyListener(new KeyAdapter() {
			    @Override
			    public void keyReleased(KeyEvent e) {
			    	if (e.character=='h') {
			    		if (isTimestampFeld()) {
			    			getTarget().setText(new Timestamp(new Datum().getDate()).toString());
			    			getTarget().setSelection(11, 19);
			    		}			    		
			    		if (isDatumFeld()) {
			    			getTarget().setText(new Datum().toString());
			    			getTarget().selectAll();
			    		}			    		
			    	}
			    	if (e.keyCode==SWT.ARROW_UP) {
			    		if (isTimestampFeld()) {
			    			passeTimestampAnHoch();
			    		}
			    		if (isDatumFeld()) {
			    			passeDatumAnHoch();
			    		}		    					    		
			    	}
			    	if (e.keyCode==SWT.ARROW_DOWN) {
			    		if (isTimestampFeld()) {
			    			passeTimestampAnRunter();
			    		}
			    		if (isDatumFeld()) {
			    			passeDatumAnRunter();
			    		}		    					    		
			    	}
			    	
					updateTargetToModel();  // Eingaben in Model schreiben lassen
					
					if (getOnUpdateModelToTargetAction()!=null) {
						Display.getCurrent().asyncExec(new Runnable() {
							@Override
							public void run() {
								getOnUpdateModelToTargetAction().execute();								
							}
						});
					}
			    }
			});

			getTarget().addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (getTarget().getSelectionCount() > 0) {
						getTarget().clearSelection();
					}
				}
				
				public void focusGained(FocusEvent e) {
					getTarget().selectAll();					
				}
			});
		} else {
			getTarget().addKeyListener(new KeyAdapter() {
			    @Override
			    public void keyReleased(KeyEvent e) {
			    	if (e.character=='h') {
			    		if (isTimestampFeld()) {
			    			getTarget().setText(new Timestamp(new Datum().getDate()).toString());
			    			getTarget().setSelection(11, 19);
							updateTargetToModel();  // Eingaben in Model schreiben lassen
			    		}			    		
			    		if (isDatumFeld()) {
			    			getTarget().setText(new Datum().toString());
			    			getTarget().selectAll();
							updateTargetToModel();  // Eingaben in Model schreiben lassen
			    		}			    		
			    	}
			    	
			    	if (e.keyCode==SWT.ARROW_UP) {
			    		if (isTimestampFeld()) {
			    			passeTimestampAnHoch();
			    		}
			    		if (isDatumFeld()) {
			    			passeDatumAnHoch();
			    		}		    					    		
			    	}
			    	if (e.keyCode==SWT.ARROW_DOWN) {
			    		if (isTimestampFeld()) {
			    			passeTimestampAnRunter();
			    		}
			    		if (isDatumFeld()) {
			    			passeDatumAnRunter();
			    		}		    					    		
			    	}
			    	if (getTextUpdateStrategy().isUpdateOnFocusLostOrEnter()) {
				    	if (e.keyCode==SWT.CR) {
							updateTargetToModel();  // Eingaben in Model schreiben lassen
							updateModelToTarget();  // Änderungen Model-Wert (z.B. Leerzeichen per Trim raus) sichtbar machen
							
			    			getTarget().setSelection(getTarget().getText().length());
			    			
							if (getOnUpdateModelToTargetAction()!=null) {
								getOnUpdateModelToTargetAction().execute();								
							}													
				    	}
			    	}
			    }
			});
			
			getTarget().addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (getTarget().getSelectionCount() > 0) {
						getTarget().clearSelection();
					}
					
					updateTargetToModel();  // Eingaben in Model schreiben lassen
					updateModelToTarget();  // Änderungen Model-Wert (z.B. Leerzeichen per Trim raus) sichtbar machen
					
					if (getOnUpdateModelToTargetAction()!=null) {
						getOnUpdateModelToTargetAction().execute();								
					}
				}
				
				
				@Override
				public void focusGained(FocusEvent e) {
					getTarget().selectAll();
				}
			});			
		}
		
		if (SWT.MULTI==(getTarget().getStyle() & SWT.MULTI)){
			getTarget().addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
						e.doit = true;
					}
				}
			});
		}		
	}

	protected void passeTimestampAnHoch() {
		if (getTargetValue()==null) { 
			getTarget().setText("");
			return; 
		}
		Timestamp timestamp = (Timestamp)getTargetValue();

		int caretPosition = getTarget().getCaretPosition();
		
		if (caretPosition>=0 && caretPosition<=1) {
			getTarget().setText(timestamp.addTage(1).toString());			    							    				
		} else if (caretPosition>=2 && caretPosition<=4) {
			getTarget().setText(timestamp.addMonate(1).toString());			    				
		} else if (caretPosition>=5 && caretPosition<=9) {
			getTarget().setText(timestamp.addJahre(1).toString());			    				
		} else if (caretPosition>=10 && caretPosition<=12) {
			getTarget().setText(timestamp.addStunden(1).toString());			    				
		} else if (caretPosition>=13 && caretPosition<=15) {
			getTarget().setText(timestamp.addMinuten(1).toString());			    							    				
		} else if (caretPosition>=16 && caretPosition<=18) {
			getTarget().setText(timestamp.addSekunden(1).toString());
		} else {
			getTarget().setText(timestamp.addTage(1).toString());			    				
		}
		getTarget().setSelection(caretPosition+1);
	}

	protected void passeTimestampAnRunter() {
		if (getTargetValue()==null) { 
			getTarget().setText("");
			return; 
		}
		Timestamp timestamp = (Timestamp)getTargetValue();

		int caretPosition = getTarget().getCaretPosition();
		
		if (caretPosition>=1 && caretPosition<=3) {
			getTarget().setText(timestamp.addTage(-1).toString());			    							    				
		} else if (caretPosition>=4 && caretPosition<=6) {
			getTarget().setText(timestamp.addMonate(-1).toString());			    				
		} else if (caretPosition>=7 && caretPosition<=11) {
			getTarget().setText(timestamp.addJahre(-1).toString());			    				
		} else if (caretPosition>=12 && caretPosition<=14) {
			getTarget().setText(timestamp.addStunden(-1).toString());			    				
		} else if (caretPosition>=15 && caretPosition<=17) {
			getTarget().setText(timestamp.addMinuten(-1).toString());			    							    				
		} else if (caretPosition>=18 && caretPosition<=19) {
			getTarget().setText(timestamp.addSekunden(-1).toString());
		} else {
			getTarget().setText(timestamp.addTage(-1).toString());			    				
		}
		getTarget().setSelection(caretPosition-1);
	}

	protected void passeDatumAnHoch() {
		if (getTargetValue()==null) { 
			getTarget().setText("");
			return; 
		}
		Datum datum = (Datum)getTargetValue();

		int caretPosition = getTarget().getCaretPosition();
		if (caretPosition>=0 && caretPosition<=1) {
			getTarget().setText(datum.addTage(1).toString());			    							    				
		} else if (caretPosition>=2 && caretPosition<=4) {
			getTarget().setText(datum.addMonate(1).toString());			    				
		} else if (caretPosition>=5 && caretPosition<=9) {
			getTarget().setText(datum.addJahre(1).toString());			    				
		} else {
			getTarget().setText(datum.addTage(1).toString());			    				
		}
		getTarget().setSelection(caretPosition+1);
	}

	protected void passeDatumAnRunter() {
		if (getTargetValue()==null) { 
			getTarget().setText("");
			return; 
		}
		Datum datum = (Datum)getTargetValue();

		int caretPosition = getTarget().getCaretPosition();
		if (caretPosition>=1 && caretPosition<=3) {
			getTarget().setText(datum.addTage(-1).toString());			    							    				
		} else if (caretPosition>=4 && caretPosition<=6) {
			getTarget().setText(datum.addMonate(-1).toString());			    				
		} else if (caretPosition>=7 && caretPosition<=11) {
			getTarget().setText(datum.addJahre(-1).toString());			    				
		} else {
			getTarget().setText(datum.addTage(-1).toString());			    				
		}
		getTarget().setSelection(caretPosition-1);
	}

	protected String getModelProperty() {
		return modelProperty;
	}
	
	protected String getEnabledProperty() {
		return enabledProperty;
	}
	
	public Object getModel() {
		return model;
	}
	
	public Text getTarget() {
		return target;
	}
	
	public TextUpdateStrategy getTextUpdateStrategy() {
		return textUpdateStrategy;
	}
		
	public ISelectionAction getOnUpdateModelToTargetAction() {
		return onUpdateModelToTargetAction;
	}
	
	protected void checkSetterExists() {
		getSetterMethod(getModel(), getModelProperty());		
	}

	protected void checkGetterExists() {
		getGetterMethod(getModel(), getModelProperty());
		if (getEnabledProperty()!=null) {
			getGetterMethod(getModel(), getEnabledProperty());			
		}
	}

	public void updateModelToTarget() {
		getTarget().setText(convertToString(getModelValue(getModel(), getModelProperty())));
		
		if (getEnabledProperty()!=null) {
			getTarget().setEnabled((Boolean)getModelValue(getModel(), getEnabledProperty()));			
		}
	}
	
	public void updateTargetToModel() {
		setModelValue(getModel(), getModelProperty(), getTargetValue());
	}
	
	protected String convertToString(Object value) {
		return value==null ? "" : value.toString().trim();
	}
	
	protected boolean isDatumFeld() {
		return getModelGetterValueType(getModel(), getModelProperty()).equals("Datum");
	}

	protected boolean isTimestampFeld() {
		return getModelGetterValueType(getModel(), getModelProperty()).equals("Timestamp");
	}

	protected Object getTargetValue() {
		String parameterTypName = getModelGetterValueType(getModel(), getModelProperty());
		String eingabeText = getTarget().getText();
		
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
		} else if (isDatumFeld()) {
			value = Datum.convertToDatum(eingabeText);
		} else if (isTimestampFeld()) {
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
}
