package com.gorob.gui.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CheckboxBindingTest {
	private Shell parentComp;
	private Button target;
	private MyModel model;
	private CheckboxBinding binding;
	
	class MyModel {
		private boolean aktValue;
		
		public MyModel() {
			this.aktValue = false;
		}

		public boolean isAktValue() {
			return aktValue;
		}
		
		public void setAktValue(boolean aktValue) {
			this.aktValue = aktValue;
		}
	}
	
	@Before
	public void setUp() throws Exception {
		parentComp = new Shell();
		target = new Button(parentComp, SWT.CHECK);
		model = new MyModel();
		binding = new CheckboxBinding(target, model, "aktValue", null);
	}

	@After
	public void tearDown() throws Exception {
		target.dispose();
		target = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;
	}

	public Button getTarget() {
		return target;
	}
	
	public Object getModel() {
		return model;
	}
	
	public CheckboxBinding getBinding() {
		return binding;
	}

	@Test
	public void testCheckboxBinding() throws Exception {
		assertEquals(model, binding.getModel());
		assertEquals("aktValue", binding.getModelProperty());
		assertEquals(target, binding.getTarget());
	}

	@Test
	public void testUpdateModelToTarget() throws Exception {
		assertFalse(model.isAktValue());
		assertFalse(target.getSelection());
		
		binding.updateModelToTarget();
		assertFalse(model.isAktValue());
		assertFalse(target.getSelection());
		
		model.setAktValue(true);
		binding.updateModelToTarget();
		assertTrue(model.isAktValue());
		assertTrue(target.getSelection());

		model.setAktValue(false);
		binding.updateModelToTarget();
		assertFalse(model.isAktValue());
		assertFalse(target.getSelection());
	}

	@Test
	public void testUpdateTargetToModel() throws Exception {
		assertFalse(model.isAktValue());
		assertFalse(target.getSelection());

		binding.updateTargetToModel();
		assertFalse(model.isAktValue());
		assertFalse(target.getSelection());

		target.setSelection(true);
		binding.updateTargetToModel();
		assertTrue(model.isAktValue());
		assertTrue(target.getSelection());

		target.setSelection(false);
		binding.updateTargetToModel();
		assertFalse(model.isAktValue());
		assertFalse(target.getSelection());
	}
}
