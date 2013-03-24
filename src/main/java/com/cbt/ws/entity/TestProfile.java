package com.cbt.ws.entity;

import java.util.List;

import com.cbt.ws.jooq.enums.TestprofileMode;

/**
 * Entity representing test profile data (mode, device types...)
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 * 
 */
public class TestProfile extends CbtEntity {
	private List<Long> deviceTypes;
	private TestprofileMode mode;

	public List<Long> getDeviceTypes() {
		return deviceTypes;
	}

	public TestprofileMode getMode() {
		return mode;
	}

	public void setDeviceTypes(List<Long> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

	public void setMode(TestprofileMode mode) {
		this.mode = mode;
	}
}
