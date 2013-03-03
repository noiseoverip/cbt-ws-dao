package com.cbt.ws.entity;

import com.cbt.ws.jooq.enums.TestrunStatus;

public class TestRun extends CbtEntity {
	private Long testConfigId;
	private TestrunStatus status;

	public Long getTestConfigId() {
		return testConfigId;
	}

	public void setTestConfigId(Long testConfigId) {
		this.testConfigId = testConfigId;
	}

	public TestrunStatus getStatus() {
		return status;
	}

	public void setStatus(TestrunStatus status) {
		this.status = status;
	}

}
