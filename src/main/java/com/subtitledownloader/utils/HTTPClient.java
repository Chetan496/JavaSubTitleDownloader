package com.subtitledownloader.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HTTPClient implements AutoCloseable {
	private String baseUrl;
	private HttpURLConnection conn = null;
	private URL url = null;
	private final String UTF_8 = java.nio.charset.StandardCharsets.UTF_8
			.toString();
	private ArrayList<String> queryParams;
	private Proxy proxy = null; /* If we are going to use a Proxy */
	private String fullUrl;

	public HTTPClient(String baseUrl) {
		this.baseUrl = baseUrl;
		this.fullUrl = baseUrl;
	}

	/*
	 * Takes in request parameter to pass in the URL. Then constructs the full
	 * URL by appending queryString to baseUrl
	 */
	public HTTPClient setRequestParam(String param, String value) {
		try {

			String encodedVal = URLEncoder.encode(value, UTF_8);
			String queryParam = param.concat("=").concat(encodedVal);
			if (null == this.queryParams) {
				queryParams = new ArrayList<String>();
			}

			queryParams.add(queryParam);
			StringBuffer queryString = new StringBuffer();
			for (String tmp : queryParams) {
				queryString.append(tmp).append("&");
			}
			this.fullUrl = baseUrl.concat("?").concat(queryString.toString());
			System.out.println(this.fullUrl);

		} catch (UnsupportedEncodingException e) {
			System.out.println("Error while encoding the request property");
			e.printStackTrace();
		}
		return this;
	}

	/* This effectively initializes the HttpUrlConnection */
	public HTTPClient init() {
		try {
			this.url = new URL(fullUrl);
			this.proxy = ProxyUtils.getProxy();
			conn = (HttpURLConnection) url.openConnection(proxy);
			conn.setDefaultUseCaches(false);
			conn.setRequestProperty("Accept-CharSet", UTF_8);

		} catch (MalformedURLException e) {
			System.out.println("Error! Malformed URL exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not open Http URL connection");
			e.printStackTrace();
		}

		return this; // you can chain the new and init method on HTTPCleint
	}

	public String request() {
		StringBuffer inputLine = null;
		try {
			// first check if we are getting a valid response back
			conn.connect();
			System.out.println(conn.getURL());
			int respCode = conn.getResponseCode();

			if (respCode == HttpURLConnection.HTTP_OK) {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				inputLine = new StringBuffer();
				String temp;
				while ((temp = bufferedReader.readLine()) != null)
					inputLine.append(temp);

				bufferedReader.close();
			} else {
				System.out
						.println("Response was not successful. Response code is "
								+ respCode);
			}

		} catch (IOException e) {
			System.out.println("Error while reading the data from the stream");
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}

		return inputLine.toString();
	}

	public void post(String requestBody) {
		/* This will post the given requestBody to the HTTP end-point */
		DataOutputStream wr;

		try {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			wr = new DataOutputStream(this.conn.getOutputStream());
			wr.writeBytes(requestBody);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while POSting the request");
			e.printStackTrace();
		}

		// also read back the response after the POST request
		System.out.println(this.request());
	}

	public void setUserAgent(final String clientName,
			final String clientVersion, final String clientUrl) {
		final String protocolName = "SubDB";
		final String protocolVersion = "1.0";
		/* This can probably be improved.. maybe a StringBuffer can be used */
		String userAgent = protocolName.concat("/").concat(protocolVersion)
				.concat(" ").concat("(").concat(clientName).concat("/")
				.concat(clientVersion).concat(";").concat(" ")
				.concat(clientUrl).concat(")");
		this.conn.setRequestProperty("User-Agent", userAgent);

	}

	public void close() throws Exception {
		this.conn.disconnect();
		System.out.println("Connection Closed");
	}

}
