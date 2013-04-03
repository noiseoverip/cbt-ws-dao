package com.cbt.ws.entity;

import java.util.Date;

import com.cbt.ws.jooq.enums.DeviceState;
import com.cbt.ws.jooq.tables.records.DeviceRecord;
import com.cbt.ws.utils.Utils;

/**
 * Device object entity
 * 
 * @author saulius
 * 
 */
public class Device extends CbtEntity {
	/**
	 * Constructor to create device object from Jooq record
	 * 
	 * @param record
	 * @return
	 */
	public static Device fromJooqRecord(DeviceRecord record) {
		if (null == record) {
			return null;
		}
		Device device = new Device();
		device.setId(record.getDeviceId());
		device.setDeviceOsId(record.getDeviceosId());
		device.setDeviceTypeId(record.getDevicetypeId());
		device.setDeviceUniqueId(record.getDeviceuniqueId());
		device.setSerialNumber(record.getSerialnumber());
		device.setState(record.getState());
		device.setUpdated(record.getUpdated());
		device.setUserId(record.getUserId());
		return device;
	}

	private Long deviceOsId;
	private Long deviceTypeId;
	private String deviceUniqueId;
	private String serialNumber;
	private DeviceState state;

	private Date updated;

	@Override
	public boolean equals(Object object) {
		if (null != object && object instanceof Device) {
			Device other = (Device) object;
			if (getId().equals(other.getId()) && getDeviceOsId().equals(other.getDeviceOsId())
					&& getDeviceTypeId().equals(other.getDeviceTypeId())) {
				return true;
			}
		}
		return false;
	}

	public Long getDeviceOsId() {
		return deviceOsId;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public DeviceState getState() {
		return state;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setDeviceOsId(Long deviceOsId) {
		this.deviceOsId = deviceOsId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setState(DeviceState state) {
		this.state = state;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return Utils.toString("Device", "id", getId(), "serial", serialNumber, "state", state);
	}
}
