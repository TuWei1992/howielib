package com.howie.framework.provider;

/**
 * 
 * @author howieceo@163.com
 * @time Dec 6, 2012 3:21:01 PM
 */
public class ModCallLog {
	/** Call log type for incoming calls. */
	public static final int INCOMING_TYPE = 1;
	/** Call log type for outgoing calls. */
	public static final int OUTGOING_TYPE = 2;
	/** Call log type for missed calls. */
	public static final int MISSED_TYPE = 3;
	/** Call log type for voicemails. */
	public static final int VOICEMAIL_TYPE = 4;

	private String number;
	private long date;
	private long duration;
	private int type;

	public ModCallLog() {
	}

	public ModCallLog(String number, long date, long duration, int type) {
		this.number = number;
		this.date = date;
		this.duration = duration;
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public long getDate() {
		return date;
	}

	public long getDuration() {
		return duration;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ModCallLog [number=" + number + ", date=" + date
				+ ", duration=" + duration + ", type=" + type + "]";
	}
}
