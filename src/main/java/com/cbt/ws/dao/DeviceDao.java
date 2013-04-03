package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Device.DEVICE;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.Device;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.tables.records.DeviceRecord;
import com.cbt.ws.mysql.Db;

/**
 * DAO for device related data operations
 * 
 * @author SauliusAlisauskas 2013-03-05 Initial version
 * 
 */
public class DeviceDao {

	// private final Logger mLogger = Logger.getLogger(DeviceDao.class);

	/**
	 * Add new device
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(Device device) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Long newDeviceId = sqexec
				.insertInto(DEVICE, DEVICE.USER_ID, DEVICE.SERIALNUMBER, DEVICE.DEVICEUNIQUE_ID, DEVICE.DEVICETYPE_ID,
						DEVICE.DEVICEOS_ID)
				.values(device.getUserId(), device.getSerialNumber(), device.getDeviceUniqueId(),
						device.getDeviceTypeId(), device.getDeviceOsId()).returning(DEVICE.DEVICE_ID).fetchOne()
				.getDeviceId();
		return newDeviceId;
	}

	/**
	 * Delete specified device
	 * 
	 * @param device
	 * @throws CbtDaoException
	 */
	public void deleteDevice(Long deviceId) throws CbtDaoException {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		int result = sqexec.delete(DEVICE).where(DEVICE.DEVICE_ID.eq(deviceId)).execute();
		if (result != 1) {
			throw new CbtDaoException("Error while deleting device, result:" + result);
		}
	}

	/**
	 * Get one device record
	 * 
	 * @param deviceId
	 * @return
	 */
	public Device getDevice(Long deviceId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		DeviceRecord record = (DeviceRecord) sqexec.select().from(DEVICE).where(DEVICE.DEVICE_ID.eq(deviceId))
				.fetchOne();
		return Device.fromJooqRecord(record);
	}

	/**
	 * Get device by device unique id
	 * 
	 * @param device
	 * @return
	 */
	public Device getDeviceByUid(String uniqueId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		DeviceRecord record = (DeviceRecord) sqexec.select().from(DEVICE).where(DEVICE.DEVICEUNIQUE_ID.eq(uniqueId))
				.fetchOne();
		return Device.fromJooqRecord(record);
	}

	/**
	 * Get devices of specified type
	 * 
	 * @param deviceType
	 * @return
	 */
	public List<Device> getDevicesOfType(Long deviceType) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		List<Device> devices = sqexec.select().from(DEVICE).where(DEVICE.DEVICETYPE_ID.eq(deviceType)).fetch()
				.map(new RecordMapper<Record, Device>() {
					@Override
					public Device map(Record record) {
						return Device.fromJooqRecord((DeviceRecord) record);
					}
				});
		return devices;
	}

	/**
	 * Get devices by user id
	 * 
	 * @param userId
	 * @return
	 */
	public List<Device> getDevicesByUser(Long userId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		List<Device> devices = sqexec.select().from(DEVICE).where(DEVICE.USER_ID.eq(userId)).fetch()
				.map(new RecordMapper<Record, Device>() {
					@Override
					public Device map(Record record) {
						return Device.fromJooqRecord((DeviceRecord) record);
					}
				});
		return devices;
	}

	/**
	 * Update device (status, type)
	 * 
	 * @param device
	 * @throws CbtDaoException
	 */
	public void updateDevice(Device device) throws CbtDaoException {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		int count = sqexec.update(DEVICE).set(DEVICE.DEVICETYPE_ID, device.getDeviceTypeId())
				.set(DEVICE.DEVICEOS_ID, device.getDeviceOsId()).set(DEVICE.STATE, device.getState())
				.set(DEVICE.UPDATED, new Timestamp(Calendar.getInstance().getTimeInMillis()))
				.where(DEVICE.DEVICE_ID.eq(device.getId())).execute();
		if (count != 1) {
			throw new CbtDaoException("Could not update device");
		}
	}
}
