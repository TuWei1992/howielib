package com.howie.framework.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;

/**
 * Uses URLConnection for stream retrieving
 * 
 * @author howieceo@163.com
 * @time Nov 9, 2012 2:55:54 PM
 */
public class URLConnectionDownloader extends BaseResource {
	private static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 5 * 1000;
	private static final int DEFAULT_HTTP_READ_TIMEOUT = 20 * 1000;

	private int mConnectTimeout;
	private int mReadTimeout;

	public URLConnectionDownloader() {
		this(DEFAULT_HTTP_CONNECT_TIMEOUT, DEFAULT_HTTP_READ_TIMEOUT);
	}

	public URLConnectionDownloader(int connectTimeout, int readTimeout) {
		this.mConnectTimeout = connectTimeout;
		this.mReadTimeout = readTimeout;
	}

	@Override
	public InputStream getStreamFromNetwork(URI uri) throws IOException {
		URLConnection conn = uri.toURL().openConnection();
		conn.setConnectTimeout(mConnectTimeout);
		conn.setReadTimeout(mReadTimeout);
		return new FlushedInputStream(new BufferedInputStream(
				conn.getInputStream()));
	}
}