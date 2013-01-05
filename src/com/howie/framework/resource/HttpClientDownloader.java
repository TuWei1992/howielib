package com.howie.framework.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;

/**
 * 
 * @author howieceo@163.com
 * @time Nov 9, 2012 2:50:39 PM
 */
public class HttpClientDownloader extends BaseResource {
	private HttpClient mHttpClient;

	public HttpClientDownloader(HttpClient httpClient) {
		this.mHttpClient = httpClient;
	}

	@Override
	protected InputStream getStreamFromNetwork(URI uri) throws IOException {
		HttpGet httpGet = new HttpGet(uri.toString());
		HttpResponse resp = mHttpClient.execute(httpGet);
		HttpEntity entity = resp.getEntity();
		BufferedHttpEntity buf = new BufferedHttpEntity(entity);
		return buf.getContent();
	}
}
