package com.howie.framework.provider;

/**
 * 
 * @author howieceo@163.com
 * @time Dec 6, 2012 5:04:33 PM
 */
public class ModSmsLog {
	/** Receiver */
	public static final int RECEIVE_TYPE = 1;
	/** Sender */
	public static final int SEND_TYPE = 2;

	private String address;
	private String body;
	private long date;
	private int type;

	public ModSmsLog() {
	}

	public ModSmsLog(String address, String body, long date, int type) {
		this.address = address;
		this.body = body;
		this.date = date;
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public String getBody() {
		return body;
	}

	public long getDate() {
		return date;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ModSmsLog [address=" + address + ", body=" + body + ", date="
				+ date + ", type=" + type + "]";
	}
}
