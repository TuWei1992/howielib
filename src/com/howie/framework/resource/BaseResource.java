package com.howie.framework.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Provides retrieving of InputStream by URI.
 * 
 * @author howieceo@163.com
 * @time Nov 9, 2012 2:42:51 PM
 */
public abstract class BaseResource {
	private static final String PROTOCOL_FILE = "file";
	private static final String PROTOCOL_HTTP = "http";
	private static final String PROTOCOL_HTTPS = "https";
	private static final String PROTOCOL_FTP = "ftp";

	/**
	 * Retrieves InputStream by URI. Resource can be located as in the network
	 * and on local file system.
	 */
	public InputStream getStream(URI uri) throws IOException {
		String scheme = uri.getScheme();
		if (PROTOCOL_HTTP.equals(scheme) || PROTOCOL_HTTPS.equals(scheme)
				|| PROTOCOL_FTP.equals(scheme)) {
			return getStreamFromNetwork(uri);
		} else if (PROTOCOL_FILE.equals(scheme)) {
			return getStreamFromFile(uri);
		} else {
			return getStreamFromOtherSource(uri);
		}
	}

	/**
	 * Retrieves InputStream by URI from other source. Should be overriden by
	 * successors to implement Resource downloading from special sources (not
	 * local file and not web URL).
	 */
	protected InputStream getStreamFromOtherSource(URI imageUri)
			throws IOException {
		return null;
	}

	/**
	 * Retrieves InputStream by URI (located in the network)
	 */
	protected abstract InputStream getStreamFromNetwork(URI uri)
			throws IOException;

	/**
	 * Retrieves InputStream by URI (located on the local file system or SD
	 * card)
	 */
	protected InputStream getStreamFromFile(URI uri) throws IOException {
		return new BufferedInputStream(uri.toURL().openStream());
	}
}