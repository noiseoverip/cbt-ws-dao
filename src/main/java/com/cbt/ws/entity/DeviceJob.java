package com.cbt.ws.entity;

import com.cbt.ws.jooq.enums.DeviceJobStatus;
import com.cbt.ws.jooq.tables.records.DeviceJobRecord;
import com.cbt.ws.utils.Utils;

/**
 * Device job entity. Represents information describing a job(test) for specific device.
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 *
 */
public class DeviceJob extends CbtEntity {

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

	private Long deviceId;
	private DeviceJobStatus status;

	private Long testRunId;

	/**
	 * Default constructor
	 */
	public DeviceJob() {

	}

	public Long getDeviceId() {
		return deviceId;
	}

	public DeviceJobStatus getStatus() {
		return status;
	}

	public Long getTestRunId() {
		return testRunId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public void setStatus(DeviceJobStatus status) {
		this.status = status;
	}

	public void setTestRunId(Long testRunId) {
		this.testRunId = testRunId;
	}

	@Override
	public String toString() {
		return Utils.toString("Device", "id", getId(), "deviceId", deviceId, "testRun", testRunId, "status", status, "meta", getMetadata());
	}

}
