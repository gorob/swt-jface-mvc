package com.gorob.client.logging;

import com.gorob.gui.model.util.StringUtil;

public abstract class AbstractLogging {	
	public final void log(String logtext) {
		log(logtext, 0);
	}
	
	public void log(String logtext, int ebene) {
		doLog(getEinrueckung(ebene));
		doLog(getTextOhneUmbruecheUndTabs(logtext));
		doLog("\r\n");
	}

	protected void doLog(String logtext) {
		System.out.print(logtext);
	}
	
	private String getEinrueckung(int ebene) {
		return StringUtil.replicate(" ", (ebene-1)*4);
	}
	
	protected String getTextOhneUmbruecheUndTabs(String text) {
		return StringUtil.padr(text.replace("\r\n", "").trim().replace("\r", "").trim().replace("\n", "").trim().replace("\t", "").trim(), 120, ' ').trim();
	}
}
