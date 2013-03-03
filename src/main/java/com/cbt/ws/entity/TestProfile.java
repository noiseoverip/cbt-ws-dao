package com.cbt.ws.entity;

import java.util.List;

import com.cbt.ws.jooq.enums.TestprofileMode;

public class TestProfile extends CbtEntity {
	private TestprofileMode mode;
	private List<Integer> deviceTypes;

	public TestprofileMode getMode() {
		return mode;
	}

	public void setMode(TestprofileMode mode) {
		this.mode = mode;
	}

	public List<Integer> getDeviceTypes() {
		return deviceTypes;
	}

	public void setDeviceTypes(List<Integer> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

}
