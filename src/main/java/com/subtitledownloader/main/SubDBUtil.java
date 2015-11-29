package com.subtitledownloader.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*This class will contain methods which will read first and 
 * last 64bytes from the given filePath.
 * They will then concatenate the data and compute the hash.
 * and then return it */
public class SubDBUtil {

	public SubDBUtil() {
		// TODO Auto-generated constructor stub
	}

	public String getHash(String filePath) {

		byte[] bytesRead = readBytesFromMovie(filePath);
		return getMD5HashInHex(bytesRead);
	}

	/*
	 * A method which takes in path to Movie/Video FileReads the video file in a
	 * readOnly/binary format.Then returns the first 64KB and last 64KB
	 * concatenated.
	 */
	private byte[] readBytesFromMovie(String filePath) {

		File movieFile = new File(filePath);

		if (movieFile.isDirectory()) {
			throw new IllegalArgumentException(
					"The given filename is a directory");
		}

		if (null == filePath) {
			throw new IllegalArgumentException("The given filename is empty");
		}

		int bufferSize = 64 * 1024;
		byte[] tempBuffer = new byte[2 * bufferSize];

		try {
			RandomAccessFile accessFile = new RandomAccessFile(movieFile, "r");
			long fileSizeInBytes = accessFile.getChannel().size();
			accessFile.read(tempBuffer, 0, bufferSize);
			accessFile.seek(fileSizeInBytes - bufferSize);
			accessFile.read(tempBuffer, bufferSize, bufferSize);
			accessFile.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error. File Not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while reading from the file");
			e.printStackTrace();
		}

		return tempBuffer;

	}

	private String getMD5HashInHex(byte[] bytesRead) {

		StringBuffer hexString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] theDigest = md.digest(bytesRead);

			hexString = new StringBuffer();
			for (int i = 0; i < theDigest.length; i++) {
				/*
				 * The leading zeroes were getting dropped when converting to
				 * hex. so this hack..
				 */
				String hex = Integer.toHexString(0xFF & theDigest[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No such algorithm exists");
			e.printStackTrace();
		}
		return hexString.toString();

	}
}
