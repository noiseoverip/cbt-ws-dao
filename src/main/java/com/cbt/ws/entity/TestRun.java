package com.cbt.ws.entity;

import java.util.List;

import com.cbt.ws.jooq.enums.TestrunStatus;
import com.cbt.ws.utils.Utils;

/**
 * Entity representing single test run information (configuration id, status...)
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 * 
 */
public class TestRun extends CbtEntity {
	private List<Device> devices;
	private TestrunStatus status;
	private Long testConfigId;

	@Override
	public boolean equals(Object object) {
		if (null != object && object instanceof TestRun) {
			TestRun other = (TestRun) object;
			if (getId().equals(other.getId()) && getTestConfigId().equals(other.getTestConfigId())
					&& getStatus().equals(other.getStatus())) {
				return true;
			}
		}
		return false;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public TestrunStatus getStatus() {
		return status;
	}

	public Long getTestConfigId() {
		return testConfigId;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public void setStatus(TestrunStatus status) {
		this.status = status;
	}

	public void setTestConfigId(Long testConfigId) {
		this.testConfigId = testConfigId;
	}

	@Override
	public String toString() {
		return Utils.toString("TestRun", "id", getId(), "testConfigId", testConfigId, "status", status);
	}
}
