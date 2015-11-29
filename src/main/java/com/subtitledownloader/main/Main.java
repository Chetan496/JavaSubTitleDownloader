package com.subtitledownloader.main;

import com.subtitledownloader.utils.HTTPClient;

public class Main {

	/* This URL is used for Dev. and Test purposes only */
	private static final String apiUrl = "http://api.thesubdb.com/";

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String filePath = "/home/MAC/Movies/movies/Unbroken.2014/Unbroken.2014.Screener.XviD-SaM[ETRG].avi";
		SubDBUtil subUtil = new SubDBUtil();
		String hash = subUtil.getHash(filePath);

		HTTPClient client = new HTTPClient(apiUrl)
				.setRequestParam("hash", hash)
				.setRequestParam("language", "en")
				.setRequestParam("action", "download").init();

		client.setUserAgent("Java Client", "1.0",
				"https://github.com/Chetan496");

		System.out.println(client.request());

	}

}
