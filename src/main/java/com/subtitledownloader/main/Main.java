package com.subtitledownloader.main;

import com.subtitledownloader.utils.HTTPClient;

public class Main {

	/* This URL is used for Dev. and Test purposes only */
	private static final String apiUrl = "http://api.thesubdb.com/";

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		/* We need to get the MovieFile Path from the user. */
		String filePath = "/home/MAC/Movies/movies/Unbroken.2014/Unbroken.2014.Screener.XviD-SaM[ETRG].avi";
		SubDBUtil subUtil = new SubDBUtil();
		String hash = subUtil.getHash(filePath);

		HTTPClient client = new HTTPClient(apiUrl)
				.setRequestParam("hash", hash)
				.setRequestParam("language", "en")
				.setRequestParam("action", "download").init();

		client.setUserAgent("Java Client", "1.0",
				"https://github.com/Chetan496");

		/*
		 * You need to write the subtitle to a file in the same directory as the
		 * movie. The name of the file should be same, extension should be .srt
		 */
		String response = client.request();
		SubTitleWriter subTitleWriter = new SubTitleWriter();
		subTitleWriter.saveSubTitleToFile(filePath, response);
		System.out.println("Saved the SubTitles to the movie folder");

	}

}
