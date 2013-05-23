package com.cbt.ws.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;


import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.jooq.exception.DataAccessException;
import org.junit.Test;

import com.cbt.ws.entity.Device;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.enums.DeviceState;
import com.cbt.ws.utils.Utils;

/**
 * Unit test for {@link DeviceDao}
 * 
 * @author SauliusAlisauskas 2013-03-23 Initial version
 *
 */
public class DeviceDaoTest {
	
	private final Logger mLogger = Logger.getLogger(DeviceDaoTest.class);
	
	/**
	 * Create new device and verify
	 * @param dao
	 * @param device
	 * @return
	 */
	private Device createNewDevice(DeviceDao dao, Device device) {		
		if (null == device) {
			device = new Device();
			device.setUserId(1L);
			device.setDeviceTypeId(1L);
			device.setDeviceOsId(1L);
			device.setSerialNumber("someserialnumbver");
			String uniqueId = Utils.Md5(Utils.buildContentForDeviceUniqueId(device));	
			device.setDeviceUniqueId(uniqueId);
		}		
		Long deviceId = dao.add(device);
		assertTrue("Failed to add new device:" + device, deviceId > 0);
		device.setId(deviceId);
		assertTrue("Failed to add new device:" + device, verify(dao, deviceId, device));
		return device;
	}
	
	private void deleteDevice(DeviceDao dao, Device device) {
		try {
			dao.deleteDevice(device.getId());
		} catch (CbtDaoException e) {
			mLogger.error("Could not delete device", e);
			fail("Could not delete " + device);
		}
	}
	
	/**
	 * Tests: add(), get(), getDeviceByUid()
	 */
	@Test
	public void testAddNewDevice() {
		DeviceDao dao = new DeviceDao();
		Device device1 = createNewDevice(dao, null);		
		Device device2 = new Device();		
		device2.setUserId(device1.getUserId());
		device2.setDeviceOsId(device1.getDeviceOsId());
		device2.setDeviceTypeId(device1.getDeviceTypeId());
		device2.setSerialNumber(device1.getSerialNumber());
		device2.setDeviceUniqueId(device1.getDeviceUniqueId());
		
		// Try to add with the same id		
		try {
			createNewDevice(dao, device2);
			fail("Expected exception not thrown");
		} catch(DataAccessException e) {
			mLogger.error(e);
		}
		
		Device deviceByUid = dao.getDeviceByUid(device1.getDeviceUniqueId());
		Assert.assertEquals(device1, deviceByUid);
		
		List<Device> allOfThatType = dao.getDevicesOfType(device1.getDeviceTypeId(), null);
		mLogger.info(allOfThatType);
		
		//Clean up		
		deleteDevice(dao, device1);		
	}
	
	@Test
	public void testUpdateDevice() {
		DeviceDao dao = new DeviceDao();
		// Add new device		
		Device device = createNewDevice(dao, null);		
		device.setState(DeviceState.ONLINE);		
		try {
			dao.updateDevice(device);
		} catch (CbtDaoException e) {
			fail("Could not update device");
		}		
		Device updateDevice = dao.getDevice(device.getId());		
		assertEquals(device, updateDevice);		
		deleteDevice(dao, device);		
	}
	
	/**
	 * Fetch device object and compare to one specified
	 * 
	 * @param dao
	 * @param deviceId
	 * @param device2
	 * @return
	 */
	private boolean verify(DeviceDao dao, Long deviceId, Device device2) {
		Device fetchedDevice = dao.getDevice(deviceId);
		return fetchedDevice.equals(device2);		
	}
			
}
