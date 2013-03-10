package com.cbt.ws.entity;

import com.cbt.ws.jooq.enums.DeviceJobStatus;
import com.cbt.ws.jooq.tables.records.DeviceJobRecord;

public class DeviceJob extends CbtEntity {
	private Long deviceId;
	private Long testRunId;
	private DeviceJobStatus status;

	/**
	 * Default constructor
	 */
	public DeviceJob() {

	}
	
	/**
	 * Constructor to construct entity from Jooq record
	 * 
	 * @param record
	 * @return
	 */
	public static DeviceJob fromJooqRecord(DeviceJobRecord record) {
		if (null == record) {
			return null;
		}
		DeviceJob job = new DeviceJob();
		job.setId(record.getDevicejobId());
		job.setDeviceId(record.getDeviceId());
		job.setTestRunId(record.getTestrunId());
		job.setStatus(record.getStatus());
		job.setCreated(record.getCreated());
		job.setUpdated(record.getUpdated());
		return job;
	}

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

	public DeviceJobStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceJobStatus status) {
		this.status = status;
	}

}
