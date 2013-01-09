package com.howie.framework.net;

/**
 * 
 * @author howieceo@163.com
 * @time 2012-7-19 下午3:33:45
 */
public class HttpResponse {
	public static final int OK = 200;
	private int statusCode;
	private String body;

	public HttpResponse(int statusCode, String responseStr) {
		this.statusCode = statusCode;
		this.body = responseStr;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", body=" + body + "]";
	}

}
