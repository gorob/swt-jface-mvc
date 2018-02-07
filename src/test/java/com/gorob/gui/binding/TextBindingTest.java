package com.gorob.gui.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gorob.gui.model.util.Datum;

public class TextBindingTest {
	private Text textString;
	private Text textDatum;
	private Text textInt;
	private Text textLong;
	private Text textInteger;
	private Text textLongGross;

	private Shell parentComp;
	private MyModel model;

	private TextBinding bindingString;
	private TextBinding bindingDatum;
	private TextBinding bindingInt;
	private TextBinding bindingLong;
	private TextBinding bindingInteger;
	private TextBinding bindingLongGross;

	class MyModel {
		private String stringValue;
		private Datum datumValue;
		private int intValue;
		private Integer integerValue;
		private long longValue;
		private Long longGrossValue;
		
		public MyModel() {
			this.stringValue = null;
			this.datumValue = null;
			this.intValue = 0;
			this.longValue = 0L;
			this.integerValue = null;
			this.longGrossValue = null;
		}

		public String getStringValue() {
			return stringValue;
		}
		
		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}
		
		public Datum getDatumValue() {
			return datumValue;
		}
		
		public void setDatumValue(Datum datumValue) {
			this.datumValue = datumValue;
		}
		
		public int getIntValue() {
			return intValue;
		}
		
		public void setIntValue(int intValue) {
			this.intValue = intValue;
		}
		
		public long getLongValue() {
			return longValue;
		}
		
		public void setLongValue(long longValue) {
			this.longValue = longValue;
		}
		
		public Integer getIntegerValue() {
			return integerValue;
		}
		
		public void setIntegerValue(Integer integerValue) {
			this.integerValue = integerValue;
		}
		
		public Long getLongGrossValue() {
			return longGrossValue;
		}
		
		public void setLongGrossValue(Long longGrossValue) {
			this.longGrossValue = longGrossValue;
		}
	}
	
	@Before
	public void setUp() throws Exception {
		parentComp = new Shell();
		textString = new Text(parentComp, SWT.NONE);
		textDatum = new Text(parentComp, SWT.NONE);
		textInt = new Text(parentComp, SWT.NONE);
		textLong = new Text(parentComp, SWT.NONE);
		textInteger = new Text(parentComp, SWT.NONE);
		textLongGross = new Text(parentComp, SWT.NONE);
		
		model = new MyModel();
		bindingString = new TextBinding(textString, model, "stringValue", null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null);
		bindingDatum = new TextBinding(textDatum, model, "datumValue", null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null);
		bindingInt = new TextBinding(textInt, model, "intValue", null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null);
		bindingLong = new TextBinding(textLong, model, "longValue", null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null);
		bindingInteger = new TextBinding(textInt, model, "integerValue", null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null);
		bindingLongGross = new TextBinding(textLong, model, "longGrossValue", null, TextUpdateStrategy.UPDATE_ON_FOCUS_LOST, null);
	}

	@After
	public void tearDown() throws Exception {
		textString.dispose();
		textString = null;
		textDatum.dispose();
		textDatum = null;
		textInt.dispose();
		textInt = null;
		textLong.dispose();
		textLong = null;
		textInteger.dispose();
		textInteger = null;
		textLongGross.dispose();
		textLongGross = null;

		parentComp.dispose();
		parentComp = null;
		model = null;
		bindingString = null;
		bindingDatum = null;
		bindingInt = null;
		bindingLong = null;
		bindingInteger = null;
		bindingLongGross = null;
	}

	@Test
	public void testTextBinding() throws Exception {
		assertNotNull(bindingString.getModel());
		assertNull(((MyModel)bindingString.getModel()).getStringValue());
		assertNotNull(bindingString.getTarget());
		assertEquals("", bindingString.getTarget().getText());

		assertNotNull(bindingDatum.getModel());
		assertNull(((MyModel)bindingDatum.getModel()).getDatumValue());
		assertNotNull(bindingDatum.getTarget());
		assertEquals("", bindingDatum.getTarget().getText());

		assertNotNull(bindingInt.getModel());
		assertEquals(0, ((MyModel)bindingInt.getModel()).getIntValue());
		assertNotNull(bindingInt.getTarget());
		assertEquals("", bindingInt.getTarget().getText());

		assertNotNull(bindingLong.getModel());
		assertEquals(0L, ((MyModel)bindingLong.getModel()).getLongValue());
		assertNotNull(bindingLong.getTarget());
		assertEquals("", bindingLong.getTarget().getText());

		assertNotNull(bindingInteger.getModel());
		assertNull(((MyModel)bindingInteger.getModel()).getIntegerValue());
		assertNotNull(bindingInteger.getTarget());
		assertEquals("", bindingInteger.getTarget().getText());

		assertNotNull(bindingLongGross.getModel());
		assertNull(((MyModel)bindingLongGross.getModel()).getLongGrossValue());
		assertNotNull(bindingLongGross.getTarget());
		assertEquals("", bindingLongGross.getTarget().getText());
	}

	@Test
	public void testUpdateTargetToModel_String() throws Exception {
		bindingString.updateTargetToModel();
		assertEquals("", ((MyModel)bindingString.getModel()).getStringValue());
		
		bindingString.getTarget().setText("text");
		bindingString.updateTargetToModel();
		assertEquals("text", ((MyModel)bindingString.getModel()).getStringValue());

		bindingString.getTarget().setText(" text ");
		bindingString.updateTargetToModel();
		assertEquals("text", ((MyModel)bindingString.getModel()).getStringValue());

		bindingString.getTarget().setText("");
		bindingString.updateTargetToModel();
		assertEquals("", ((MyModel)bindingString.getModel()).getStringValue());

		bindingString.getTarget().setText("   ");
		bindingString.updateTargetToModel();
		assertEquals("", ((MyModel)bindingString.getModel()).getStringValue());
	}

	
	@Test
	public void testUpdateTargetToModel_int() throws Exception {
		bindingInt.updateTargetToModel();
		assertEquals(0, ((MyModel)bindingInt.getModel()).getIntValue());
		
		bindingInt.getTarget().setText("text");
		bindingInt.updateTargetToModel();
		assertEquals(0, ((MyModel)bindingInt.getModel()).getIntValue());

		bindingInt.getTarget().setText("");
		bindingInt.updateTargetToModel();
		assertEquals(0, ((MyModel)bindingInt.getModel()).getIntValue());

		bindingInt.getTarget().setText(" text ");
		bindingInt.updateTargetToModel();
		assertEquals(0, ((MyModel)bindingInt.getModel()).getIntValue());

		bindingInt.getTarget().setText("a1");
		bindingInt.updateTargetToModel();
		assertEquals(0, ((MyModel)bindingInt.getModel()).getIntValue());

		bindingInt.getTarget().setText("1a");
		bindingInt.updateTargetToModel();
		assertEquals(0, ((MyModel)bindingInt.getModel()).getIntValue());

		bindingInt.getTarget().setText("1");
		bindingInt.updateTargetToModel();
		assertEquals(1, ((MyModel)bindingInt.getModel()).getIntValue());

		bindingInt.getTarget().setText("100");
		bindingInt.updateTargetToModel();
		assertEquals(100, ((MyModel)bindingInt.getModel()).getIntValue());

		bindingInt.getTarget().setText(" 100 ");
		bindingInt.updateTargetToModel();
		assertEquals(100, ((MyModel)bindingInt.getModel()).getIntValue());
	}

	@Test
	public void testUpdateTargetToModel_long() throws Exception {
		bindingLong.updateTargetToModel();
		assertEquals(0L, ((MyModel)bindingLong.getModel()).getLongValue());
		
		bindingLong.getTarget().setText("text");
		bindingLong.updateTargetToModel();
		assertEquals(0L, ((MyModel)bindingLong.getModel()).getLongValue());

		bindingLong.getTarget().setText("");
		bindingLong.updateTargetToModel();
		assertEquals(0L, ((MyModel)bindingLong.getModel()).getLongValue());

		bindingLong.getTarget().setText(" text ");
		bindingLong.updateTargetToModel();
		assertEquals(0L, ((MyModel)bindingLong.getModel()).getLongValue());

		bindingLong.getTarget().setText("a1");
		bindingLong.updateTargetToModel();
		assertEquals(0L, ((MyModel)bindingLong.getModel()).getLongValue());

		bindingLong.getTarget().setText("1a");
		bindingLong.updateTargetToModel();
		assertEquals(0L, ((MyModel)bindingLong.getModel()).getLongValue());

		bindingLong.getTarget().setText("1");
		bindingLong.updateTargetToModel();
		assertEquals(1L, ((MyModel)bindingLong.getModel()).getLongValue());

		bindingLong.getTarget().setText("100");
		bindingLong.updateTargetToModel();
		assertEquals(100L, ((MyModel)bindingLong.getModel()).getLongValue());

		bindingLong.getTarget().setText(" 100 ");
		bindingLong.updateTargetToModel();
		assertEquals(100L, ((MyModel)bindingLong.getModel()).getLongValue());
	}

	@Test
	public void testUpdateTargetToModel_Integer() throws Exception {
		bindingInteger.updateTargetToModel();
		assertNull(((MyModel)bindingInteger.getModel()).getIntegerValue());
		
		bindingInteger.getTarget().setText("text");
		bindingInteger.updateTargetToModel();
		assertNull(((MyModel)bindingInteger.getModel()).getIntegerValue());

		bindingInteger.getTarget().setText("");
		bindingInteger.updateTargetToModel();
		assertNull(((MyModel)bindingInteger.getModel()).getIntegerValue());

		bindingInteger.getTarget().setText(" text ");
		bindingInteger.updateTargetToModel();
		assertNull(((MyModel)bindingInteger.getModel()).getIntegerValue());

		bindingInteger.getTarget().setText("a1");
		bindingInteger.updateTargetToModel();
		assertNull(((MyModel)bindingInteger.getModel()).getIntegerValue());

		bindingInteger.getTarget().setText("1a");
		bindingInteger.updateTargetToModel();
		assertNull(((MyModel)bindingInteger.getModel()).getIntegerValue());

		bindingInteger.getTarget().setText("1");
		bindingInteger.updateTargetToModel();
		assertEquals(new Integer(1), ((MyModel)bindingInteger.getModel()).getIntegerValue());

		bindingInteger.getTarget().setText("100");
		bindingInteger.updateTargetToModel();
		assertEquals(new Integer(100), ((MyModel)bindingInteger.getModel()).getIntegerValue());

		bindingInteger.getTarget().setText(" 100 ");
		bindingInteger.updateTargetToModel();
		assertEquals(new Integer(100), ((MyModel)bindingInteger.getModel()).getIntegerValue());
	}

	@Test
	public void testUpdateTargetToModel_Long() throws Exception {
		bindingLongGross.updateTargetToModel();
		assertNull(((MyModel)bindingLongGross.getModel()).getLongGrossValue());
		
		bindingLongGross.getTarget().setText("text");
		bindingLongGross.updateTargetToModel();
		assertNull(((MyModel)bindingLongGross.getModel()).getLongGrossValue());

		bindingLongGross.getTarget().setText("");
		bindingLongGross.updateTargetToModel();
		assertNull(((MyModel)bindingLongGross.getModel()).getLongGrossValue());

		bindingLongGross.getTarget().setText(" text ");
		bindingLongGross.updateTargetToModel();
		assertNull(((MyModel)bindingLongGross.getModel()).getLongGrossValue());

		bindingLongGross.getTarget().setText("a1");
		bindingLongGross.updateTargetToModel();
		assertNull(((MyModel)bindingLongGross.getModel()).getLongGrossValue());

		bindingLongGross.getTarget().setText("1a");
		bindingLongGross.updateTargetToModel();
		assertNull(((MyModel)bindingLongGross.getModel()).getLongGrossValue());

		bindingLongGross.getTarget().setText("1");
		bindingLongGross.updateTargetToModel();
		assertEquals(new Long(1), ((MyModel)bindingLongGross.getModel()).getLongGrossValue());

		bindingLongGross.getTarget().setText("100");
		bindingLongGross.updateTargetToModel();
		assertEquals(new Long(100), ((MyModel)bindingLongGross.getModel()).getLongGrossValue());

		bindingLongGross.getTarget().setText(" 100 ");
		bindingLongGross.updateTargetToModel();
		assertEquals(new Long(100), ((MyModel)bindingLongGross.getModel()).getLongGrossValue());
	}

	@Test
	public void testUpdateTargetToModel_Datum() throws Exception {
		bindingDatum.updateTargetToModel();
		assertNull(((MyModel)bindingDatum.getModel()).getDatumValue());
		
		bindingDatum.getTarget().setText("text");
		bindingDatum.updateTargetToModel();
		assertNull(((MyModel)bindingDatum.getModel()).getDatumValue());

		bindingDatum.getTarget().setText(" text ");
		bindingDatum.updateTargetToModel();
		assertNull(((MyModel)bindingDatum.getModel()).getDatumValue());

		bindingDatum.getTarget().setText(" 01012001 ");
		bindingDatum.updateTargetToModel();
		assertEquals(new Datum(1,1,2001), ((MyModel)bindingDatum.getModel()).getDatumValue());

		bindingDatum.getTarget().setText(" 02.02.2002 ");
		bindingDatum.updateTargetToModel();
		assertEquals(new Datum(2,2,2002), ((MyModel)bindingDatum.getModel()).getDatumValue());
	}
	
	
	@Test
	public void testUpdateModelToTarget_String() throws Exception {
		bindingString.updateModelToTarget();
		assertEquals("", bindingString.getTarget().getText());
		
		((MyModel)bindingString.getModel()).setStringValue("text");
		bindingString.updateModelToTarget();
		assertEquals("text", bindingString.getTarget().getText());

		((MyModel)bindingString.getModel()).setStringValue(" text ");
		bindingString.updateModelToTarget();
		assertEquals("text", bindingString.getTarget().getText());

		((MyModel)bindingString.getModel()).setStringValue(" ");
		bindingString.updateModelToTarget();
		assertEquals("", bindingString.getTarget().getText());

		((MyModel)bindingString.getModel()).setStringValue("   ");
		bindingString.updateModelToTarget();
		assertEquals("", bindingString.getTarget().getText());
	}

	@Test
	public void testUpdateModelToTarget_int() throws Exception {
		bindingInt.updateModelToTarget();
		assertEquals("0", bindingInt.getTarget().getText());

		((MyModel)bindingInt.getModel()).setIntValue(10);
		bindingInt.updateModelToTarget();
		assertEquals("10", bindingInt.getTarget().getText());

		((MyModel)bindingInt.getModel()).setIntValue(0);
		bindingInt.updateModelToTarget();
		assertEquals("0", bindingInt.getTarget().getText());

		((MyModel)bindingInt.getModel()).setIntValue(100);
		bindingInt.updateModelToTarget();
		assertEquals("100", bindingInt.getTarget().getText());
	}

	@Test
	public void testUpdateModelToTarget_long() throws Exception {
		bindingLong.updateModelToTarget();
		assertEquals("0", bindingLong.getTarget().getText());

		((MyModel)bindingLong.getModel()).setLongValue(10);
		bindingLong.updateModelToTarget();
		assertEquals("10", bindingLong.getTarget().getText());

		((MyModel)bindingLong.getModel()).setLongValue(0);
		bindingLong.updateModelToTarget();
		assertEquals("0", bindingLong.getTarget().getText());

		((MyModel)bindingLong.getModel()).setLongValue(100);
		bindingLong.updateModelToTarget();
		assertEquals("100", bindingLong.getTarget().getText());
	}
	
	@Test
	public void testUpdateModelToTarget_Integer() throws Exception {
		bindingInteger.updateModelToTarget();
		assertEquals("", bindingInteger.getTarget().getText());

		((MyModel)bindingInteger.getModel()).setIntegerValue(10);
		bindingInteger.updateModelToTarget();
		assertEquals("10", bindingInteger.getTarget().getText());

		((MyModel)bindingInteger.getModel()).setIntegerValue(0);
		bindingInteger.updateModelToTarget();
		assertEquals("0", bindingInteger.getTarget().getText());

		((MyModel)bindingInteger.getModel()).setIntegerValue(100);
		bindingInteger.updateModelToTarget();
		assertEquals("100", bindingInteger.getTarget().getText());
	}
	
	@Test
	public void testUpdateModelToTarget_Long() throws Exception {
		bindingLongGross.updateModelToTarget();
		assertEquals("", bindingLongGross.getTarget().getText());

		((MyModel)bindingLongGross.getModel()).setLongGrossValue(10L);
		bindingLongGross.updateModelToTarget();
		assertEquals("10", bindingLongGross.getTarget().getText());

		((MyModel)bindingLongGross.getModel()).setLongGrossValue(0L);
		bindingLongGross.updateModelToTarget();
		assertEquals("0", bindingLongGross.getTarget().getText());

		((MyModel)bindingLongGross.getModel()).setLongGrossValue(100L);
		bindingLongGross.updateModelToTarget();
		assertEquals("100", bindingLongGross.getTarget().getText());
	}
	
	@Test
	public void testUpdateModelToTarget_Datum() throws Exception {
		bindingDatum.updateModelToTarget();
		assertEquals("", bindingDatum.getTarget().getText());

		((MyModel)bindingDatum.getModel()).setDatumValue(new Datum(1,1,2001));
		bindingDatum.updateModelToTarget();
		assertEquals("01.01.2001", bindingDatum.getTarget().getText());

		((MyModel)bindingDatum.getModel()).setDatumValue(null);
		bindingDatum.updateModelToTarget();
		assertEquals("", bindingDatum.getTarget().getText());

		((MyModel)bindingDatum.getModel()).setDatumValue(new Datum(2,2,2002));
		bindingDatum.updateModelToTarget();
		assertEquals("02.02.2002", bindingDatum.getTarget().getText());
	}

}
