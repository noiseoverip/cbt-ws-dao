package com.cbt.ws.entity;

import com.cbt.ws.utils.Utils;

/**
 * Entity representing test configuration data.
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 *
 */
public class TestConfig extends CbtEntity {
	private Long testScriptId;
	private Long testTargetId;
	private Long testProfileId;
	
	public Long getTestProfileId() {
		return testProfileId;
	}

	public void setTestProfileId(Long testProfileId) {
		this.testProfileId = testProfileId;
	}

	public Long getTestScriptId() {
		return testScriptId;
	}

	public void setTestScriptId(Long testScriptId) {
		this.testScriptId = testScriptId;
	}

	public Long getTestTargetId() {
		return testTargetId;
	}

	public void setTestTargetId(Long testTargetId) {
		this.testTargetId = testTargetId;
	}
	
	@Override
	public String toString() {		
		return Utils.toString("TestConfig", "id", getId(), "testScriptId", testScriptId, "testTargetId", testTargetId, "testProfileId", testProfileId);
	}

}
