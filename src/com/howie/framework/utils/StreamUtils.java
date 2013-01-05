package com.howie.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides operations with Stream
 * 
 * @author howieceo@163.com
 * @time Nov 9, 2012 2:30:02 PM
 */
public final class StreamUtils {
	private static final int BUFFER_SIZE = 8 * 1024; // 8 KB

	public static void copyStream(InputStream is, OutputStream os)
			throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];

		while (true) {
			int count = is.read(bytes, 0, BUFFER_SIZE);
			if (count == -1) {
				break;
			}
			os.write(bytes, 0, count);
		}
	}
}
