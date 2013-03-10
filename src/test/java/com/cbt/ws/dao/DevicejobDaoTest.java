package com.cbt.ws.dao;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.cbt.ws.entity.DeviceJob;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.enums.DevicejobsStatus;

public class DevicejobDaoTest {

	@Test
	public void updateDeviceJob() {
		Long deviceId = 1L;
		DevicejobDao dao = new DevicejobDao();
		DeviceJob job = dao.getOldestWaiting(deviceId);
		job.setStatus(DevicejobsStatus.CHECKEDOUT);
		
		try {
			dao.update(job);
		} catch (CbtDaoException e) {
			fail("Unexpected expetion" + e.getMessage());
		}
	}
}
