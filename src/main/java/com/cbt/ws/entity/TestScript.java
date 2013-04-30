package com.cbt.ws.entity;

import com.cbt.ws.utils.Utils;

/**
 * Enitity class representing test script data (file name, path...)
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 *
 */
public class TestScript extends CbtEntity {
	private String filePath;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return Utils.toString("TestScript", "id", getId(),"fileName", fileName, "filePath", filePath);
	}
}
