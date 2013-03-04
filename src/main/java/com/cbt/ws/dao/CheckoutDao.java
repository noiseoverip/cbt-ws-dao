package com.cbt.ws.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.cbt.ws.annotations.TestFileStorePath;

public class CheckoutDao {

	private final Logger mLogger = Logger.getLogger(CheckoutDao.class);
	private String mFileStorePath;

	@Inject
	public CheckoutDao(@TestFileStorePath String testFileStorePath) {
		mFileStorePath = testFileStorePath;
	}

	public String getPathToTestPackage() {

		String zipFile = mFileStorePath + "zipdemo.zip";
		String[] sourceFiles = { mFileStorePath + "file1.png", mFileStorePath + "file2.png" };

		// create byte buffer
		byte[] buffer = new byte[1024];

		// create object of FileOutputStream
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(zipFile);
		} catch (FileNotFoundException e) {
			mLogger.error("Could not create file output stream to" + zipFile);			
		}

		// create object of ZipOutputStream from FileOutputStream
		ZipOutputStream zout = new ZipOutputStream(fout);

		for (int i = 0; i < sourceFiles.length; i++) {

			mLogger.info("Zipping file " + sourceFiles[i]);
			
			// create object of FileInputStream for source file
			FileInputStream fin = null;
			try {
				fin = new FileInputStream(sourceFiles[i]);
			} catch (FileNotFoundException e) {
				mLogger.error("Could not create file output stream to" + sourceFiles[i], e);
			}

			try {
				zout.putNextEntry(new ZipEntry("file"+i+".png"));
			} catch (IOException e) {
				mLogger.error("Could not create new ZIP entry for file:" + sourceFiles[i], e);
			}

			int length;
			try {
				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}
			} catch (IOException e) {
				mLogger.error("Error while copying file:" + sourceFiles[i], e);
			}			

			try {
				zout.closeEntry();
			} catch (IOException e) {
				mLogger.error("Could not close ZIP entry for file" + sourceFiles[i], e);
			}

			// close the InputStream
			try {
				fin.close();
			} catch (IOException e) {
				mLogger.error("Could not close file input stream", e);
			}
		}
		
		 try {
			zout.close();
		} catch (IOException e) {
			mLogger.error("Could not close zip file stream", e);
		}
		 return zipFile;
	}
}
