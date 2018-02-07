package com.gorob.gui.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class StringUtilTest {
	@Test
	public void testPadl() throws Exception {
		assertEquals("", StringUtil.padl("", 0, '0'));
		assertEquals("000", StringUtil.padl("", 3, '0'));
		assertEquals("00a", StringUtil.padl("a", 3, '0'));
		assertEquals("aaa", StringUtil.padl("aaa", 3, '0'));
		assertEquals("aa", StringUtil.padl("aa", 0, '0'));
		assertEquals("aa", StringUtil.padl("aa", 1, '0'));
		assertEquals("aa", StringUtil.padl("aa", 2, '0'));
		assertEquals("0aa", StringUtil.padl("aa", 3, '0'));
		
		assertEquals("01", StringUtil.padl(1, 2, '0'));
		assertEquals("11", StringUtil.padl(11, 2, '0'));
		assertEquals("111", StringUtil.padl(111, 2, '0'));
	}

	@Test
	public void testIsNullOrEmpty() throws Exception {
		assertTrue(StringUtil.isNullOrEmpty(""));
		assertTrue(StringUtil.isNullOrEmpty(" "));
		assertTrue(StringUtil.isNullOrEmpty("  "));
		assertFalse(StringUtil.isNullOrEmpty(" a "));
		assertFalse(StringUtil.isNullOrEmpty("a"));
		assertFalse(StringUtil.isNullOrEmpty("123"));
	}

	@Test
	public void testIstNummerString() throws Exception {
		assertFalse(StringUtil.istNummerString(""));
		assertFalse(StringUtil.istNummerString(" "));
		assertFalse(StringUtil.istNummerString("a"));
		assertFalse(StringUtil.istNummerString("A"));
		assertFalse(StringUtil.istNummerString("a1"));
		assertFalse(StringUtil.istNummerString("1a1"));
		assertFalse(StringUtil.istNummerString(" 1a1 "));
		assertFalse(StringUtil.istNummerString("-123"));
		assertFalse(StringUtil.istNummerString(" 123 "));
		assertTrue(StringUtil.istNummerString("1"));
		assertTrue(StringUtil.istNummerString("11"));
		assertTrue(StringUtil.istNummerString("123"));
	}

	@Test
	public void testPadr() throws Exception {
		assertEquals("", StringUtil.padr("", 0, ' '));
		assertEquals(" ", StringUtil.padr("", 1, ' '));
		assertEquals("  ", StringUtil.padr("", 2, ' '));
		assertEquals("a", StringUtil.padr("aaa", 1, ' '));
		assertEquals("aa", StringUtil.padr("aaa", 2, ' '));
		assertEquals("aaa", StringUtil.padr("aaa", 3, ' '));
		assertEquals("aaa ", StringUtil.padr("aaa", 4, ' '));
		assertEquals("aaa  ", StringUtil.padr("aaa", 5, ' '));
	}

	@Test
	public void testReplicate() throws Exception {
		assertEquals("     ", StringUtil.replicate(" ", 5));
		assertEquals("#########", StringUtil.replicate("###", 3));
	}

	@Test
	public void testConcat_List() throws Exception {
		assertEquals("aaabbbccc", StringUtil.concat(Arrays.asList(new String[] {"aaa", "bbb", "ccc"})));
		assertEquals("", StringUtil.concat(Arrays.asList(new String[] {})));
		assertEquals("", StringUtil.concat(Arrays.asList(new String[] {"", null, ""})));
		assertEquals("", StringUtil.concat(Arrays.asList(new String[] {null, null, null})));
		assertEquals("aaabbb", StringUtil.concat(Arrays.asList(new String[] {null, "aaa", "", "bbb"})));
	}

	@Test
	public void testConcatMitZeilenumbruch() throws Exception {
	    assertEquals("aaa\r\nbbb\r\nccc\r\n", StringUtil.concatMitZeilenumbruch(Arrays.asList(new String[] {"aaa", "bbb", "ccc"})));
		assertEquals("", StringUtil.concatMitZeilenumbruch(Arrays.asList(new String[] {})));
		assertEquals("\r\n\r\n", StringUtil.concatMitZeilenumbruch(Arrays.asList(new String[] {"", null, ""})));
		assertEquals("", StringUtil.concatMitZeilenumbruch(Arrays.asList(new String[] {null, null, null})));
		assertEquals("aaa\r\n\r\nbbb\r\n", StringUtil.concatMitZeilenumbruch(Arrays.asList(new String[] {null, "aaa", "", "bbb"})));
	}

	@Test
	public void testEntferneZeilenumbruchFallsVorhanden() throws Exception {
		assertNull(StringUtil.entferneZeilenumbruchFallsVorhanden(null));
		assertEquals("", StringUtil.entferneZeilenumbruchFallsVorhanden(""));
		assertEquals("", StringUtil.entferneZeilenumbruchFallsVorhanden("   "));
		assertEquals(" a ", StringUtil.entferneZeilenumbruchFallsVorhanden(" a "));
		assertEquals("a", StringUtil.entferneZeilenumbruchFallsVorhanden("a\n"));
		assertEquals(" a ", StringUtil.entferneZeilenumbruchFallsVorhanden(" a \n"));
		assertEquals("a", StringUtil.entferneZeilenumbruchFallsVorhanden("a\r\n"));
		assertEquals(" a ", StringUtil.entferneZeilenumbruchFallsVorhanden(" a \r\n"));
		assertEquals("a\r\n", StringUtil.entferneZeilenumbruchFallsVorhanden("a\r\n\n"));
		assertEquals("a\n", StringUtil.entferneZeilenumbruchFallsVorhanden("a\n\r\n"));
		assertEquals("a\r\n", StringUtil.entferneZeilenumbruchFallsVorhanden("a\r\n\r\n"));
		assertEquals("a\r\nb", StringUtil.entferneZeilenumbruchFallsVorhanden("a\r\nb\r\n"));
	}

	@Test
	public void testConcatMitZeilenumbruchOhneAmEnde() throws Exception {
	    assertEquals("aaa\r\nbbb\r\nccc", StringUtil.concatMitZeilenumbruchOhneAmEnde(Arrays.asList(new String[] {"aaa", "bbb", "ccc"})));
		assertEquals("", StringUtil.concatMitZeilenumbruchOhneAmEnde(Arrays.asList(new String[] {})));
		assertEquals("", StringUtil.concatMitZeilenumbruchOhneAmEnde(Arrays.asList(new String[] {"", null, ""})));
		assertEquals("", StringUtil.concatMitZeilenumbruchOhneAmEnde(Arrays.asList(new String[] {null, null, null})));
		assertEquals("aaa\r\n\r\nbbb", StringUtil.concatMitZeilenumbruchOhneAmEnde(Arrays.asList(new String[] {null, "aaa", "", "bbb"})));
	}
	
	@Test
	public void testEntferneZeichenAmEndeFallsVorhanden() throws Exception {
		assertNull(StringUtil.entferneZeichenAmEndeFallsVorhanden(null, "aaa"));
		assertEquals("text", StringUtil.entferneZeichenAmEndeFallsVorhanden("text", "aaa"));
		assertEquals("texta", StringUtil.entferneZeichenAmEndeFallsVorhanden("texta", "aaa"));
		assertEquals("textaa", StringUtil.entferneZeichenAmEndeFallsVorhanden("textaa", "aaa"));
		assertEquals("text", StringUtil.entferneZeichenAmEndeFallsVorhanden("textaaa", "aaa"));
	}

	@Test
	public void testGetStringBetween() throws Exception {
		assertEquals("", StringUtil.getStringBetween(null, "<", ">"));
		assertEquals("", StringUtil.getStringBetween("", "<", ">"));
		assertEquals("", StringUtil.getStringBetween(" ", "<", ">"));
		assertEquals("b", StringUtil.getStringBetween("abc", "a", "c"));
		assertEquals("", StringUtil.getStringBetween("ac", "a", "c"));
		assertEquals("a", StringUtil.getStringBetween("ac", "", "c"));
		assertEquals("c", StringUtil.getStringBetween("ac", "a", ""));
		assertEquals("abc", StringUtil.getStringBetween("<tag>abc</tag>", "<tag>", "</tag>"));
		assertEquals("abc", StringUtil.getStringBetween("<tag>abc", "<tag>", ""));
		assertEquals("abc", StringUtil.getStringBetween("abc</tag>", "", "</tag>"));
		assertEquals("abc</tag>", StringUtil.getStringBetween("<tag>abc</tag>", "<tag>", ""));
		assertEquals("<tag>abc", StringUtil.getStringBetween("<tag>abc</tag>", "", "</tag>"));
		assertEquals("", StringUtil.getStringBetween("abc", "<tag>", "</tag>"));
		assertEquals("", StringUtil.getStringBetween("", "<tag>", "</tag>"));
		assertEquals("", StringUtil.getStringBetween(null, "<tag>", "</tag>"));
	}
}
