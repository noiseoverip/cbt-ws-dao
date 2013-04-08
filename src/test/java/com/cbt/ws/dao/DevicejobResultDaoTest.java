package com.cbt.ws.dao;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.cbt.ws.entity.DeviceJobResult;
import com.cbt.ws.exceptions.CbtDaoException;

/**
 * Unit test for {@link DevicejobResultDao}
 * 
 * @author SauliusAlisauskas 2013-04-07 Initial version
 * 
 */
public class DevicejobResultDaoTest {
	DevicejobResultDao mUnit;

	/**
	 * Create and store new job, verify creation
	 * 
	 * @return
	 */
	private DeviceJobResult createAndAddJob() {
		Long randomDeviceJobId = new Random().nextLong();
		DeviceJobResult job = new DeviceJobResult();
		job.setDevicejobId(randomDeviceJobId);
		job.setOutput("");
		job.setTestsRun(1);
		job.setTestsFailed(1);
		job.setTestsErrors(1);
		job.setState(DeviceJobResult.State.FAILED);
		Long newDeviceJobResultId = mUnit.add(job);
		Assert.assertNotNull(newDeviceJobResultId);
		job.setId(newDeviceJobResultId);
		return job;
	}

	/**
	 * Delete job
	 * 
	 * @param job
	 * @throws CbtDaoException 
	 */
	private void deleteJob(DeviceJobResult job) throws CbtDaoException {
		mUnit.delete(job);
	}

	@Before
	public void test() {
		mUnit = new DevicejobResultDao();
	}

	@Test
	public void updateDeviceJob() throws CbtDaoException {
		// Add job
		DeviceJobResult job = createAndAddJob();

		DeviceJobResult[] jobs = mUnit.getAll();
		boolean jobFound = false;
		for (DeviceJobResult result : jobs) {
			if (result.equals(job)) {
				jobFound = true;
			}
		}
		Assert.assertTrue(jobFound);
		// Delete
		deleteJob(job);
	}
}
