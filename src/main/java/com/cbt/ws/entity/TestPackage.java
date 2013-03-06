package com.cbt.ws.entity;

/**
 * Entity class representing a Test package (test scripts, test targets, other)
 * 
 * @author SauliusAlisauskas 2013-03-05 Initial version
 * 
 */
public class TestPackage {

	private Long devicejobId;
	private String testScriptPath;
	private String testTargetPath;

	public Long getDevicejobId() {
		return devicejobId;
	}

	public void setDevicejobId(Long devicejobId) {
		this.devicejobId = devicejobId;
	}

	public String getTestScriptPath() {
		return testScriptPath;
	}

	public void setTestScriptPath(String testScriptPath) {
		this.testScriptPath = testScriptPath;
	}

	public String getTestTargetPath() {
		return testTargetPath;
	}

	public void setTestTargetPath(String testTargetPath) {
		this.testTargetPath = testTargetPath;
	}

}
