package com.gorob.gui.model.util;

import java.util.List;

public final class StringUtil {
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	private StringUtil() {		
	}
	
	public static boolean isNullOrEmpty(String str) {
		return (str==null || str.trim().equals(""));
	}
	
	public static String padl(Object value, int anzahlZeichenErg, char charToAdd) {
		if (value.toString().length()>=anzahlZeichenErg) {
    		return value.toString();
    	}
    	
		String strErg = new String();
    	for (int i=0; i<anzahlZeichenErg; i++) {
    		strErg += charToAdd;
    	}
    	strErg += value.toString();
    	
    	return strErg.substring(strErg.length()-anzahlZeichenErg);
    }

	public static String padr(Object value, int anzahlZeichenErg, char charToAdd) {
    	if (value.toString().length()>=anzahlZeichenErg) {
    		return value.toString().substring(0, anzahlZeichenErg);
    	}
    	
		String strErg = new String();
		strErg += value.toString();
    	for (int i=0; i<anzahlZeichenErg; i++) {
    		strErg += charToAdd;
    	}
    	
    	return strErg.substring(0, anzahlZeichenErg);
    }

	public static boolean istNummerString(String str) {
		StringBuffer buffer = new StringBuffer(str);
		
		if (StringUtil.isNullOrEmpty(str)) { return false; }
		
		for (int i=0; i<buffer.length(); i++) {
			int asciiNr = (int)buffer.charAt(i);
			if (asciiNr<48 || asciiNr>57) { return false; }
		}

		return true;
	}

	public static String replicate(String str, int anzahl) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i<anzahl; i++) {
			buffer.append(str);
		}
		return buffer.toString();
	}

	public static String concat(List<String> zeilen) {
		return concat(zeilen, false);
	}

	public static String concatMitZeilenumbruch(List<String> zeilen) {
		return concat(zeilen, true);
	}

	public static String concatMitZeilenumbruchOhneAmEnde(List<String> zeilen) {
		return entferneZeilenumbruchFallsVorhanden(concat(zeilen, true));
	}
	
	public static String concat(List<String> zeilen, boolean mitZeilenumbruch) {
		StringBuffer buffer = new StringBuffer();
		for (String zeile : zeilen) {
			if (zeile==null) { continue; }
			buffer.append(zeile + (mitZeilenumbruch ? NEW_LINE : ""));
		}
		return buffer.toString();
	}

	public static String entferneZeilenumbruchFallsVorhanden(String text) {
		if (text==null) { return null; }
		if (StringUtil.isNullOrEmpty(text)) { return ""; }
		if (text.endsWith("\r\n")) { return text.substring(0, text.length()-2); }
		if (text.endsWith("\n")) { return text.substring(0, text.length()-1); }
		return text;
	}

	public static String entferneZeichenAmEndeFallsVorhanden(String text, String zeichen) {
		if (text==null) { return null; }
		if (!text.endsWith(zeichen)) { return text; }
		return text.substring(0, text.length()-zeichen.length());
	}
	
	public static String getStringBetween(String str, String beginStr, String endStr) {
		if (str==null) { return ""; }
		
		int beginIndex = -1;
		if (!StringUtil.isNullOrEmpty(beginStr)) {
			beginIndex = str.indexOf(beginStr);
			if (beginIndex==-1) {
				return "";
			}
			beginIndex = beginIndex + beginStr.length();
		} else {
			beginIndex = 0;
		}
				
		int endIndex = StringUtil.isNullOrEmpty(endStr) ? str.length() : str.indexOf(endStr);
		if (endIndex==-1) {
			return "";
		}
		
		return str.substring(beginIndex, endIndex);		
	}

}
