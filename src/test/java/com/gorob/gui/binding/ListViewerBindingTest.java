package com.gorob.gui.binding;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gorob.gui.model.util.Datum;
import com.gorob.gui.model.util.Timestamp;

public class ListViewerBindingTest {
	private AbstractListViewer viewer;
	private Shell parentComp;
	private MyModel_String model;

	private ListViewerBinding binding;

	class MyModel_String {
		private List<String> values;
		private String aktValue;
		
		public MyModel_String() {
			this.values = new ArrayList<String>();
			this.aktValue = null;
		}
		
		public List<String> getValues() {
			return values;
		}
		
		public String getAktValue() {
			return aktValue;
		}
		
		public void setAktValue(String aktValue) {
			this.aktValue = aktValue;
		}
	}
	
	class MyModel_Integer {
		private List<Integer> values;
		private Integer aktValue;
		
		public MyModel_Integer() {
			this.values = new ArrayList<Integer>();
			this.aktValue = null;
		}
		
		public List<Integer> getValues() {
			return values;
		}
		
		public Integer getAktValue() {
			return aktValue;
		}
		
		public void setAktValue(Integer aktValue) {
			this.aktValue = aktValue;
		}
	}

	class MyModel_Long {
		private List<Long> values;
		private Long aktValue;
		
		public MyModel_Long() {
			this.values = new ArrayList<Long>();
			this.aktValue = null;
		}
		
		public List<Long> getValues() {
			return values;
		}
		
		public Long getAktValue() {
			return aktValue;
		}
		
		public void setAktValue(Long aktValue) {
			this.aktValue = aktValue;
		}
	}

	class MyModel_Datum {
		private List<Datum> values;
		private Datum aktValue;
		
		public MyModel_Datum() {
			this.values = new ArrayList<Datum>();
			this.aktValue = null;
		}
		
		public List<Datum> getValues() {
			return values;
		}
		
		public Datum getAktValue() {
			return aktValue;
		}
		
		public void setAktValue(Datum aktValue) {
			this.aktValue = aktValue;
		}
	}

	class MyModel_Timestamp {
		private List<Timestamp> values;
		private Timestamp aktValue;
		
		public MyModel_Timestamp() {
			this.values = new ArrayList<Timestamp>();
			this.aktValue = null;
		}
		
		public List<Timestamp> getValues() {
			return values;
		}
		
		public Timestamp getAktValue() {
			return aktValue;
		}
		
		public void setAktValue(Timestamp aktValue) {
			this.aktValue = aktValue;
		}
	}

	
	@Before
	public void setUp() throws Exception {
		parentComp = new Shell();
		viewer = new ComboViewer(parentComp, SWT.READ_ONLY);
		model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		binding = new ListViewerBinding(viewer, model, "values", "aktValue");
	}

	@After
	public void tearDown() throws Exception {
		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;
	}

	@Test
	public void testListViewerBinding() throws Exception {
		assertSame(model, binding.getModel());
		assertEquals("values", binding.getModelPropertyList());
		assertEquals("aktValue", binding.getModelPropertySelection());
		assertSame(viewer, binding.getTarget());
		assertNull(binding.getTargetSelection());
		
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
	}

	@Test
	public void testUpdateModelToTarget() throws Exception {
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(binding.getTargetSelection());
		binding.updateModelToTarget();
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(binding.getTargetSelection());
		
		model.setAktValue("bbb");
		binding.updateModelToTarget();
		assertEquals(expectedList, binding.getTarget().getInput());
		assertEquals("bbb", model.getAktValue());
		assertEquals("bbb", binding.getTargetSelection());
		
		model.getValues().clear();
		binding.updateModelToTarget();
		expectedList = new ArrayList<String>();
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(model.getAktValue());
		assertNull(binding.getTargetSelection());
		
		model.getValues().add("bbb");
		binding.updateModelToTarget();
		expectedList = new ArrayList<String>();
		expectedList.add("bbb");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(model.getAktValue());
		assertNull(binding.getTargetSelection());

		model.setAktValue("bbb");
		binding.updateModelToTarget();
		assertEquals(expectedList, binding.getTarget().getInput());
		assertEquals("bbb", model.getAktValue());
		assertEquals("bbb", binding.getTargetSelection());
				
		model.getValues().add("ccc");
		binding.updateModelToTarget();
		expectedList = new ArrayList<String>();
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertEquals("bbb", model.getAktValue());
		assertEquals("bbb", binding.getTargetSelection());

		model.setAktValue("ccc");
		binding.updateModelToTarget();
		expectedList = new ArrayList<String>();
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertEquals("ccc", model.getAktValue());
		assertEquals("ccc", binding.getTargetSelection());
		
		model.setAktValue(null);
		binding.updateModelToTarget();
		expectedList = new ArrayList<String>();
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(model.getAktValue());
		assertNull(binding.getTargetSelection());
	}

	@Test
	public void testUpdateTargetToModel() throws Exception {
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(model.getAktValue());
		assertNull(binding.getTargetSelection());

		binding.updateTargetToModel();
		
		expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(model.getAktValue());
		assertNull(binding.getTargetSelection());
		
		binding.getTarget().setSelection(new StructuredSelection("bbb"));
		binding.updateTargetToModel();
		expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertEquals("bbb", model.getAktValue());
		assertEquals("bbb", binding.getTargetSelection());

		binding.getTarget().setSelection(new StructuredSelection("aaa"));
		binding.updateTargetToModel();
		expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertEquals("aaa", model.getAktValue());
		assertEquals("aaa", binding.getTargetSelection());

		binding.getTarget().setSelection(new StructuredSelection("ccc"));
		binding.updateTargetToModel();
		expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertEquals("ccc", model.getAktValue());
		assertEquals("ccc", binding.getTargetSelection());

		binding.getTarget().setSelection(null);
		binding.updateTargetToModel();		
		expectedList = new ArrayList<String>();
		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");
		assertEquals(expectedList, binding.getTarget().getInput());
		assertNull(model.getAktValue());
		assertNull(binding.getTargetSelection());
	}

	@Test
	public void testIsEditierbarerComboViewer_Comboviewer_readOnly() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.READ_ONLY);
		MyModel_String model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertFalse(binding.isEditierbarerComboViewer());
		
		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;		
	}
	
	@Test
	public void testIsEditierbarerComboViewer_Comboviewer_editierbar() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_String model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertTrue(binding.isEditierbarerComboViewer());
		
		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;		
	}

	@Test
	public void testIsEditierbarerComboViewer_ListViewer_readOnly() throws Exception {
		Shell parentComp = new Shell();
		ListViewer viewer = new ListViewer(parentComp, SWT.READ_ONLY);
		MyModel_String model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertFalse(binding.isEditierbarerComboViewer());
		
		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;		
	}

	@Test
	public void testIsEditierbarerComboViewer_ListViewer_editierbar() throws Exception {
		Shell parentComp = new Shell();
		ListViewer viewer = new ListViewer(parentComp, SWT.BORDER);
		MyModel_String model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertFalse(binding.isEditierbarerComboViewer());
		
		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;		
	}

	@Test
	public void testGetTargetValue_String() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_String model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertEquals("", binding.getTargetValue());

		viewer.getCombo().setText("aaa");
		assertEquals("aaa", binding.getTargetValue());

		viewer.getCombo().setText("bbb");
		assertEquals("bbb", binding.getTargetValue());

		viewer.getCombo().setText("ccc");
		assertEquals("ccc", binding.getTargetValue());

		viewer.getCombo().setText("ddd");
		assertEquals("ddd", binding.getTargetValue());

		viewer.setSelection(new StructuredSelection("aaa"));		
		assertEquals("aaa", binding.getTargetValue());

		viewer.setSelection(new StructuredSelection("bbb"));		
		assertEquals("bbb", binding.getTargetValue());
		
		viewer.setSelection(new StructuredSelection("ccc"));		
		assertEquals("ccc", binding.getTargetValue());

		viewer.setSelection(new StructuredSelection("ddd"));		
		assertEquals("ccc", binding.getTargetValue());

		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;				
	}

	@Test
	public void testGetTargetValue_Integer() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_Integer model = new MyModel_Integer();
		model.getValues().add(new Integer("1"));
		model.getValues().add(new Integer("2"));
		model.getValues().add(new Integer("3"));
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertNull(binding.getTargetValue());

		viewer.getCombo().setText("1");
		assertEquals(new Integer("1"), binding.getTargetValue());

		viewer.getCombo().setText("2");
		assertEquals(new Integer("2"), binding.getTargetValue());

		viewer.getCombo().setText("3");
		assertEquals(new Integer("3"), binding.getTargetValue());

		viewer.getCombo().setText("4");
		assertEquals(new Integer("4"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Integer("1")));		
		assertEquals(new Integer("1"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Integer("2")));		
		assertEquals(new Integer("2"), binding.getTargetValue());
		
		viewer.setSelection(new StructuredSelection(new Integer("3")));		
		assertEquals(new Integer("3"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Integer("4")));		
		assertEquals(new Integer("3"), binding.getTargetValue());

		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;				
	}

	@Test
	public void testGetTargetValue_Long() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_Long model = new MyModel_Long();
		model.getValues().add(new Long("1"));
		model.getValues().add(new Long("2"));
		model.getValues().add(new Long("3"));
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertNull(binding.getTargetValue());

		viewer.getCombo().setText("1");
		assertEquals(new Long("1"), binding.getTargetValue());

		viewer.getCombo().setText("2");
		assertEquals(new Long("2"), binding.getTargetValue());

		viewer.getCombo().setText("3");
		assertEquals(new Long("3"), binding.getTargetValue());

		viewer.getCombo().setText("4");
		assertEquals(new Long("4"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Long("1")));		
		assertEquals(new Long("1"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Long("2")));		
		assertEquals(new Long("2"), binding.getTargetValue());
		
		viewer.setSelection(new StructuredSelection(new Long("3")));		
		assertEquals(new Long("3"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Long("4")));		
		assertEquals(new Long("3"), binding.getTargetValue());

		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;				
	}

	@Test
	public void testGetTargetValue_Datum() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_Datum model = new MyModel_Datum();
		model.getValues().add(new Datum("01.01.2001"));
		model.getValues().add(new Datum("02.02.2002"));
		model.getValues().add(new Datum("03.03.2003"));
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertNull(binding.getTargetValue());

		viewer.getCombo().setText("01.01.2001");
		assertEquals(new Datum("01.01.2001"), binding.getTargetValue());

		viewer.getCombo().setText("02.02.2002");
		assertEquals(new Datum("02.02.2002"), binding.getTargetValue());

		viewer.getCombo().setText("03.03.2003");
		assertEquals(new Datum("03.03.2003"), binding.getTargetValue());

		viewer.getCombo().setText("04.04.2004");
		assertEquals(new Datum("04.04.2004"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Datum("01.01.2001")));		
		assertEquals(new Datum("01.01.2001"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Datum("02.02.2002")));		
		assertEquals(new Datum("02.02.2002"), binding.getTargetValue());
		
		viewer.setSelection(new StructuredSelection(new Datum("03.03.2003")));		
		assertEquals(new Datum("03.03.2003"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Datum("04.04.2004")));		
		assertEquals(new Datum("03.03.2003"), binding.getTargetValue());

		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;				
	}

	@Test
	public void testGetTargetValue_Timestamp() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_Timestamp model = new MyModel_Timestamp();
		model.getValues().add(new Timestamp("01.01.2001 01:01:01"));
		model.getValues().add(new Timestamp("02.02.2002 02:02:02"));
		model.getValues().add(new Timestamp("03.03.2003 03:03:03"));
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertNull(binding.getTargetValue());

		viewer.getCombo().setText("01.01.2001 01:01:01");
		assertEquals(new Timestamp("01.01.2001 01:01:01"), binding.getTargetValue());

		viewer.getCombo().setText("02.02.2002 02:02:02");
		assertEquals(new Timestamp("02.02.2002 02:02:02"), binding.getTargetValue());

		viewer.getCombo().setText("03.03.2003 03:03:03");
		assertEquals(new Timestamp("03.03.2003 03:03:03"), binding.getTargetValue());

		viewer.getCombo().setText("04.04.2004 04:04:04");
		assertEquals(new Timestamp("04.04.2004 04:04:04"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Timestamp("01.01.2001 01:01:01")));		
		assertEquals(new Timestamp("01.01.2001 01:01:01"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Timestamp("02.02.2002 02:02:02")));		
		assertEquals(new Timestamp("02.02.2002 02:02:02"), binding.getTargetValue());
		
		viewer.setSelection(new StructuredSelection(new Timestamp("03.03.2003 03:03:03")));		
		assertEquals(new Timestamp("03.03.2003 03:03:03"), binding.getTargetValue());

		viewer.setSelection(new StructuredSelection(new Timestamp("04.04.2004 04:04:04")));		
		assertEquals(new Timestamp("03.03.2003 03:03:03"), binding.getTargetValue());

		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;				
	}

	@Test
	public void testIsTargetValueEmpty() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_String model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertTrue(binding.isTargetValueEmpty());
		viewer.getCombo().setText("bbb");
		assertFalse(binding.isTargetValueEmpty());
		viewer.setSelection(new StructuredSelection("aaa"));		
		assertFalse(binding.isTargetValueEmpty());
		viewer.setSelection(new StructuredSelection("ddd"));		
		assertFalse(binding.isTargetValueEmpty());
		
		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;				
	}

	@Test
	public void testOnFocusLostComboViewer() throws Exception {
		Shell parentComp = new Shell();
		ComboViewer viewer = new ComboViewer(parentComp, SWT.BORDER);
		MyModel_String model = new MyModel_String();
		model.getValues().add("aaa");
		model.getValues().add("bbb");
		model.getValues().add("ccc");
		ListViewerBinding binding = new ListViewerBinding(viewer, model, "values", "aktValue");
		
		assertTrue(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("", model.getAktValue());
		assertEquals(3, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));
		
		viewer.getCombo().setText("bbb");
		assertFalse(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("bbb", model.getAktValue());
		assertEquals(3, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));

		viewer.getCombo().setText("ccc");
		assertFalse(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("ccc", model.getAktValue());
		assertEquals(3, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));

		viewer.getCombo().setText("aaa");
		assertFalse(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("aaa", model.getAktValue());
		assertEquals(3, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));
		
		viewer.getCombo().setText("ddd");
		assertFalse(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("ddd", model.getAktValue());
		assertEquals(4, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));
		assertEquals("ddd", model.getValues().get(3));

		viewer.getCombo().setText("");
		assertTrue(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("", model.getAktValue());
		assertEquals(4, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));
		assertEquals("ddd", model.getValues().get(3));

		viewer.getCombo().setText("bbb");
		assertFalse(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("bbb", model.getAktValue());
		assertEquals(4, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));
		assertEquals("ddd", model.getValues().get(3));

		viewer.getCombo().setText("hallo");
		assertFalse(binding.isTargetValueEmpty());
		binding.onFocusLostComboViewer();
		assertEquals("hallo", model.getAktValue());
		assertEquals(5, model.getValues().size());
		assertEquals("aaa", model.getValues().get(0));
		assertEquals("bbb", model.getValues().get(1));
		assertEquals("ccc", model.getValues().get(2));
		assertEquals("ddd", model.getValues().get(3));
		assertEquals("hallo", model.getValues().get(4));

		viewer.getControl().dispose();
		viewer = null;
		parentComp.dispose();
		parentComp = null;
		model = null;
		binding = null;				
	}

}
