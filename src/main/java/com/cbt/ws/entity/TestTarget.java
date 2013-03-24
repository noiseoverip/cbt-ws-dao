package com.cbt.ws.entity;

import com.cbt.ws.utils.Utils;

/**
 * Entity representing test target (application) data (file name, path)
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 * 
 */
public class TestTarget extends CbtEntity {
	private String fileName;
	private String filePath;

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return Utils.toString("TestTarget", "id", getId(), "fileName", fileName, "filePath", filePath);
	}

}
