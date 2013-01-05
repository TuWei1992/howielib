package com.howie.framework.utils;


/**
 * @author howieceo@163.com
 * @time Sep 5, 2012 12:22:09 PM
 */

public class MIMEUtil {
	public static String getMIMEType(String filename) {
		String type = "*/*";

		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}

		String end = filename.substring(dotIndex, filename.length())
				.toLowerCase();

		for (int i = 0; i < MIME_MAP_TABLE.length; i++) {
			if (end.equals(MIME_MAP_TABLE[i][0])) {
				type = MIME_MAP_TABLE[i][1];
				break;
			}
		}
		return type;
	}

	private static final String[][] MIME_MAP_TABLE = { { "", "*/*" },
			{ ".jpeg", "image/jpeg" }, 
			{ ".jpg", "image/jpeg" },
			{ ".png", "image/png" }, 
			{ ".bmp", "image/bmp" },
			{ ".gif", "image/gif" }, 
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" }, 
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".exe", "application/octet-stream" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" }, 
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" }, 
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" }, 
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" }, 
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" }, 
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" }, 
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" }, 
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" }, 
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" }, 
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, 
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".prop", "text/plain" },
			{ ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, 
			{ ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, 
			{ ".sh", "text/plain" },
			{ ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, 
			{ ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, 
			{ ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" },
			{ ".xml", "text/plain" },
			{ ".z", "application/x-compress" }, 
			{ ".zip", "application/zip" } };
}
