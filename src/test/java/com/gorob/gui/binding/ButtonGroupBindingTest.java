package com.gorob.gui.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ButtonGroupBindingTest {
	private List<Button> buttons;
	private Button button1;
	private Button button2;
	private Button button3;
	private Shell parentComp;
	private MyModel model;
	private ButtonGroupBinding binding;
	private List<String> modelValues;

	class MyModel {
		private String aktValue;
		
		public MyModel() {
			this.aktValue = null;
		}
		
		public String getAktValue() {
			return aktValue;
		}
		
		public void setAktValue(String aktValue) {
			this.aktValue = aktValue;
		}
	}

	@Before
	public void setUp() throws Exception {
		parentComp = new Shell();
		buttons = new ArrayList<Button>();
		button1 = new Button(parentComp, SWT.RADIO);
		button2 = new Button(parentComp, SWT.RADIO);
		button3 = new Button(parentComp, SWT.RADIO);
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		
		model = new MyModel();
		
		modelValues = new ArrayList<String>();
		modelValues.add("value1");
		modelValues.add("value2");
		modelValues.add("value3");
		
		binding = new ButtonGroupBinding(buttons, model, "aktValue", modelValues, null, null);
	}

	@After
	public void tearDown() throws Exception {
		button1.dispose();
		button1 = null;
		button2.dispose();
		button2 = null;
		button3.dispose();
		button3 = null;
		buttons = null;
		parentComp.dispose();
		parentComp = null;
		modelValues = null;
		model = null;
		binding = null;
	}

	@Test
	public void testButtonGroupBinding() throws Exception {
		assertEquals(model, binding.getModel());
		assertEquals("aktValue", binding.getModelProperty());
		assertEquals(modelValues, binding.getModelValues());
		assertEquals(buttons, binding.getTarget());
		
		assertNull(model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());
	}

	@Test
	public void testUpdateModelToTarget() throws Exception {
		assertNull(model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		binding.updateModelToTarget();
		
		assertNull(model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		model.setAktValue("value2");
		binding.updateModelToTarget();
		assertEquals("value2", model.getAktValue());
		assertFalse(button1.getSelection());
		assertTrue(button2.getSelection());
		assertFalse(button3.getSelection());

		model.setAktValue("value4");
		binding.updateModelToTarget();
		assertEquals("value4", model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		model.setAktValue("value3");
		binding.updateModelToTarget();
		assertEquals("value3", model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertTrue(button3.getSelection());

		model.setAktValue("value1");
		binding.updateModelToTarget();
		assertEquals("value1", model.getAktValue());
		assertTrue(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		model.setAktValue(null);
		binding.updateModelToTarget();
		assertNull(model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		model.setAktValue("value2");
		binding.updateModelToTarget();
		assertEquals("value2", model.getAktValue());
		assertFalse(button1.getSelection());
		assertTrue(button2.getSelection());
		assertFalse(button3.getSelection());

		model.setAktValue("");
		binding.updateModelToTarget();
		assertEquals("", model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());
	}

	@Test
	public void testUpdateTargetToModel() throws Exception {
		assertNull(model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		binding.updateTargetToModel();		
		assertNull(model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		button2.setSelection(true);
		binding.updateTargetToModel();		
		assertEquals("value2", model.getAktValue());
		assertFalse(button1.getSelection());
		assertTrue(button2.getSelection());
		assertFalse(button3.getSelection());

		button1.setSelection(false);
		button2.setSelection(false);
		button3.setSelection(true);
		binding.updateTargetToModel();		
		assertEquals("value3", model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertTrue(button3.getSelection());

		button1.setSelection(true);
		button2.setSelection(false);
		button3.setSelection(false);
		binding.updateTargetToModel();		
		assertEquals("value1", model.getAktValue());
		assertTrue(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		button1.setSelection(false);
		button2.setSelection(false);
		button3.setSelection(false);
		binding.updateTargetToModel();		
		assertNull(model.getAktValue());
		assertFalse(button1.getSelection());
		assertFalse(button2.getSelection());
		assertFalse(button3.getSelection());

		button1.setSelection(false);
		button2.setSelection(true);
		button3.setSelection(false);
		binding.updateTargetToModel();		
		assertEquals("value2", model.getAktValue());
		assertFalse(button1.getSelection());
		assertTrue(button2.getSelection());
		assertFalse(button3.getSelection());
	}
	
	

}
