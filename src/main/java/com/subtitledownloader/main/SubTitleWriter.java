package com.subtitledownloader.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*This class will take care of writing the SubTitles to the 
 * appropriate directory */
public class SubTitleWriter {
	private final String subTitleExt = ".srt";

	public SubTitleWriter() {
		// TODO Auto-generated constructor stub
	}

	public void saveSubTitleToFile(String movieFilePath, String subTitleText) {
		Path fullPathForSubTitle = getFullPathForSubTitle(movieFilePath);
		/* Now create a new FIle at the path.. */
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(fullPathForSubTitle,
					StandardOpenOption.CREATE_NEW);

			writer.write(subTitleText, 0, subTitleText.length());
			writer.close();
		} catch (IOException e) {
			System.out
					.println("Error while trying to create a new File. A file already Exists");
			e.printStackTrace();
		}
	}

	private Path getFullPathForSubTitle(String movieFilePath) {
		File file = new File(movieFilePath);

		String parentDirPath = null;
		if (file.isDirectory()) {
			throw new IllegalArgumentException(
					"Error! Given Path of movie file is a directory");
		} else {
			parentDirPath = file.getParent();
		}

		String subTitleFileName = file.getName().replaceFirst(".[a-z]+[0-9]+$",
				subTitleExt);
		Path p1 = Paths.get(parentDirPath);
		Path p2 = p1.resolve(subTitleFileName);

		return p2;
	}

}
