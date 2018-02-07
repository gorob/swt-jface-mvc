package com.gorob.gui.model.util;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DatumTest {

	@Test
	public void testGetDatumStrImFormatJJJJTTMM() throws Exception {
		assertEquals("20000101", new Datum(1,1,2000).getDatumStrImFormatJJJJTTMM());
		assertEquals("20001001", new Datum(1,10,2000).getDatumStrImFormatJJJJTTMM());
		assertEquals("20000110", new Datum(10,1,2000).getDatumStrImFormatJJJJTTMM());
		assertEquals("20001010", new Datum(10,10,2000).getDatumStrImFormatJJJJTTMM());
	}

	@Test
	public void testToStringString() throws Exception {
		Datum datum = new Datum("2014-10-01", "yyyy-MM-dd");
		assertEquals("2014-10-01", datum.toString("yyyy-MM-dd"));
	}

}
