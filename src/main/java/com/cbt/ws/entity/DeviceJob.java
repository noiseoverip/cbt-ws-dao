package com.cbt.ws.entity;

import com.cbt.ws.jooq.enums.DevicejobsStatus;

public class DeviceJob extends CbtEntity {
	private Long deviceId;
	private Long testRunId;
	private DevicejobsStatus status;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getTestRunId() {
		return testRunId;
	}

	public void setTestRunId(Long testRunId) {
		this.testRunId = testRunId;
	}

	public DevicejobsStatus getStatus() {
		return status;
	}

	public void setStatus(DevicejobsStatus status) {
		this.status = status;
	}

}
