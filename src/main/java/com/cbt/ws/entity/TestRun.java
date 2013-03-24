package com.cbt.ws.entity;

import com.cbt.ws.jooq.enums.TestrunStatus;
import com.cbt.ws.jooq.tables.records.TestrunRecord;
import com.cbt.ws.utils.Utils;

/**
 * Entity representing single test run information (configuration id, status...)
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 * 
 */
public class TestRun extends CbtEntity {
	private Long testConfigId;
	private TestrunStatus status;

	public static TestRun fromJooqRecord(TestrunRecord record) {
		if (null == record) {
			return null;
		}
		TestRun testRun = new TestRun();
		testRun.setId(record.getTestrunId());
		testRun.setStatus(record.getStatus());
		testRun.setTestConfigId(record.getTestconfigId());
		testRun.setUpdated(record.getUpdated());

		return testRun;
	}

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

	@Override
	public String toString() {
		return Utils.toString("TestRun", "id", getId(), "testConfigId", testConfigId, "status", status);
	}

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
}
