package com.cbt.ws.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public final class FileOperations {
	
	/**
	 * Save file to specified location
	 * 
	 * @param uploadedInputStream
	 * @param filePath
	 * @throws IOException
	 */
	public static void writeToFile(final InputStream uploadedInputStream, final String filePath) throws IOException {
		OutputStream out = new FileOutputStream(new File(filePath));
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(filePath));
		while ((read = uploadedInputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	
	public Date getCurrentDate() {
		return new Date();
	}
}
