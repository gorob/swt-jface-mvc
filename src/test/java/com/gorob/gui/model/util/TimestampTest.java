package com.gorob.gui.model.util;



import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimestampTest {

	@Test
	public void testCreateTimestampMitFormat() throws Exception {
		Timestamp timestamp = Timestamp.createTimestampMitFormat("2011-10-19T10:29:29.908+0200", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		assertEquals(new Timestamp(19,10,2011,10,29,29), timestamp);
	}

	@Test
	public void testToStringString() throws Exception {
		String format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		assertEquals("2011-10-19T10:29:29.908+0200", Timestamp.createTimestampMitFormat("2011-10-19T10:29:29.908+0200", format).toString(format));
	}

	@Test
	public void testToStringOhneUhrzeit() throws Exception {
		assertEquals("19.10.2011", new Timestamp(19,10,2011,10,29,29).toStringOhneUhrzeit());
	}

	@Test
	public void testToStringMitUhrzeitOhneSekundenOhneSekunden() throws Exception {
		assertEquals("19.10.2011, 10:29 Uhr", new Timestamp(19,10,2011,10,29,29).toStringMitUhrzeitOhneSekunden());
	}

}
