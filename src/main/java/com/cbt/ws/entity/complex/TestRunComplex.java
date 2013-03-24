package com.cbt.ws.entity.complex;

import java.util.List;

import com.cbt.ws.utils.Utils;

/**
 * Entity representing detailed test run information
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 *
 */
public class TestRunComplex {
	private Long testConfigId;
	private Long testProfileId;
	private List<Long> deviceTypes;

	public Long getTestConfigId() {
		return testConfigId;
	}

	public void setTestConfigId(Long testConfigId) {
		this.testConfigId = testConfigId;
	}

	public Long getTestProfileId() {
		return testProfileId;
	}

	public void setTestProfileId(Long testProfileId) {
		this.testProfileId = testProfileId;
	}

	public List<Long> getDeviceTypes() {
		return deviceTypes;
	}

	public void setDeviceTypes(List<Long> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}
	
	@Override
	public String toString() {		
		return Utils.toString("TestRunComplex", "testConfigId", testConfigId, "testProfileId", testProfileId, "deviceTypes", deviceTypes);
	}
	
}
