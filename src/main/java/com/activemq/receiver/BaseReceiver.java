package com.activemq.receiver;

import org.apache.log4j.Logger;

public class BaseReceiver {

	public Logger logger = Logger.getLogger(this.getClass());
	
	private String prefix;

	public void setPrefix(String prefix) {
		if (prefix == null || prefix.trim().equals(""))
			this.prefix = "";
		else
			this.prefix = prefix.trim() + ".";
	}

	public String getPrefix() {
		return prefix;
	}

}
