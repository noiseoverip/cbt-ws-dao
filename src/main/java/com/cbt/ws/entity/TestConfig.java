package com.cbt.ws.entity;

public class TestConfig extends CbtEntity {
	private Long testPackageId;
	private Long testTargetId;
	private Long testProfileId;

	public Long getTestProfileId() {
		return testProfileId;
	}

	public void setTestProfileId(Long testProfileId) {
		this.testProfileId = testProfileId;
	}

	public Long getTestPackageId() {
		return testPackageId;
	}

	public void setTestPackageId(Long testPackageId) {
		this.testPackageId = testPackageId;
	}

	public Long getTestTargetId() {
		return testTargetId;
	}

	public void setTestTargetId(Long testTargetId) {
		this.testTargetId = testTargetId;
	}

}
