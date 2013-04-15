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
	private List<DeviceType> deviceTypesList; // TODO: fix this, shoud only be one list

	private TestprofileMode mode;

	public List<Long> getDeviceTypes() {
		return deviceTypes;
	}

	public List<DeviceType> getDeviceTypesList() {
		return deviceTypesList;
	}

	public TestprofileMode getMode() {
		return mode;
	}

	public void setDeviceTypes(List<Long> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

	public void setDeviceTypesList(List<DeviceType> deviceTypesList) {
		this.deviceTypesList = deviceTypesList;
	}

	public void setMode(TestprofileMode mode) {
		this.mode = mode;
	}
}
