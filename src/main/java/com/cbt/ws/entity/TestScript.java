package com.cbt.ws.entity;

import java.util.List;

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
	private List<String> testClasses;
	
	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public List<String> getTestClasses() {
		return testClasses;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setTestClasses(List<String> testClasses) {
		this.testClasses = testClasses;
	}

	@Override
	public String toString() {
		return Utils.toString("TestScript", "id", getId(),"fileName", fileName, "filePath", filePath);
	}
}
