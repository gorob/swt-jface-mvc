package com.gorob.client.logging;

public class MemoryLogging extends AbstractLogging {
	private StringBuffer logTextBuffer;
	
	public MemoryLogging() {
		this.logTextBuffer = new StringBuffer();
	}
	
	private StringBuffer getLogTextBuffer() {
		return logTextBuffer;
	}
	
	public String getLogText() {
		return getLogTextBuffer().toString();
	}
	
	@Override
	public void doLog(String logtext) {
		super.doLog(logtext);
		getLogTextBuffer().append(logtext);
	}
}