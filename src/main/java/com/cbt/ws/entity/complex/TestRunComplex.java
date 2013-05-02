package com.cbt.ws.entity.complex;

import java.util.List;

import com.cbt.ws.entity.CbtEntity;
import com.cbt.ws.entity.TestConfig;
import com.cbt.ws.entity.TestProfile;
import com.cbt.ws.utils.Utils;

/**
 * Entity representing detailed test run information
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 *
 */
public class TestRunComplex extends CbtEntity {
	private List<Long> deviceTypes;
	private TestConfig testConfig;
	//TODO: remove, get id from object
	private Long testConfigId;
	private TestProfile testProfile;
	//TODO: remove, get id from object
	private Long testProfileId;

	public List<Long> getDeviceTypes() {
		return deviceTypes;
	}

	public TestConfig getTestConfig() {
		return testConfig;
	}

	public Long getTestConfigId() {
		return testConfigId;
	}

	public TestProfile getTestProfile() {
		return testProfile;
	}

	public Long getTestProfileId() {
		return testProfileId;
	}

	public void setDeviceTypes(List<Long> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

	public void setTestConfig(TestConfig testConfig) {
		this.testConfig = testConfig;
	}

	public void setTestConfigId(Long testConfigId) {
		this.testConfigId = testConfigId;
	}

	public void setTestProfile(TestProfile testProfile) {
		this.testProfile = testProfile;
	}

	public void setTestProfileId(Long testProfileId) {
		this.testProfileId = testProfileId;
	}
	
	@Override
	public String toString() {		
		return Utils.toString("TestRunComplex", "testConfig", testConfig, "testProfile", testProfile, "deviceTypes", deviceTypes);
	}
	
}
