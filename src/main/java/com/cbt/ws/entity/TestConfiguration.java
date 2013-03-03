package com.cbt.ws.entity;

import java.util.List;

import com.cbt.ws.jooq.enums.TestconfigMode;

public class TestConfiguration extends CbtEntity {
	private TestconfigMode mode;
	private List<Integer> deviceTypes;

	public TestconfigMode getMode() {
		return mode;
	}

	public void setMode(TestconfigMode mode) {
		this.mode = mode;
	}

	public List<Integer> getDeviceTypes() {
		return deviceTypes;
	}

	public void setDeviceTypes(List<Integer> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

}
