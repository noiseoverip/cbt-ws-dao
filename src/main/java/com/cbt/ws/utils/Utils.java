package com.cbt.ws.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.cbt.ws.entity.Device;

public final class Utils {
	
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
	
	/**
	 * Generate MD5 from provided string
	 * 
	 * @param string
	 * @return
	 */
	public static String Md5(String string) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(string.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < array.length; ++i) {				
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
			
		}
		return null;
	}
	
	/**
	 * Return string to be used for hashing with MD5
	 * 
	 * @param device
	 * @return
	 */
	public static String buildContentForDeviceUniqueId(Device device) {
		return device.getUserId() + device.getSerialNumber() + device.getDeviceTypeId() + device.getDeviceOsId();
	}
}
