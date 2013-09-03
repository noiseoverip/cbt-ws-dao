//package com.cbt.ws.dao;
//
//import static org.junit.Assert.fail;
//
//import java.util.Random;
//
//import junit.framework.Assert;
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.cbt.ws.entity.DeviceJob;
//import com.cbt.ws.exceptions.CbtDaoException;
//import com.cbt.ws.jooq.enums.DeviceJobStatus;
//
///**
// * Unit test for {@link DevicejobDao}
// * 
// * @author SauliusAlisauskas 2013-03-23 Initial version
// *
// */
//public class DevicejobDaoTest {
//	private DevicejobDao mUnit;
//	private final Logger logger = Logger.getLogger(DevicejobDaoTest.class);
//	
//	/**
//	 * Create and store new job, verify creation
//	 * 
//	 * @return
//	 */
//	private DeviceJob createAndAddJob() {
//		Long randomTestRunId = new Random().nextLong();
//		Long randomDeviceId = new Random().nextLong();
//		DeviceJob job = new DeviceJob();
//		job.setDeviceId(randomDeviceId);
//		job.setTestRunId(randomTestRunId);
//		job.getMetadata().setTestClasses(new String[]{"com.test.1", "com.test.2"});
//		Long newDeviceJobId = mUnit.add(job);
//		Assert.assertNotNull(newDeviceJobId);
//		job.setId(newDeviceJobId);		
//		return job;
//	}
//	
//	/**
//	 * Delete job
//	 * 
//	 * @param job
//	 */
//	private void deleteJob(DeviceJob job) {
//		try {
//			mUnit.delete(job);
//		} catch (CbtDaoException e) {
//			Assert.fail("Could not delete job:" + e.getMessage());
//		}
//	}
//	
//	@Before
//	public void test() {
//		mUnit = new DevicejobDao();
//	}
//	
//	@Test
//	public void updateDeviceJob() {
//		logger.info("Starting updateDeviceJobTest");
//		// Add job
//		DeviceJob job = createAndAddJob();
//		logger.info("Created job:" + job);
//		
//		// Get waiting
//		DeviceJob waitingJob = mUnit.getOldestWaiting(job.getDeviceId());
//		logger.info("Received oldest waiting job:" + waitingJob);
//		Assert.assertNotNull(waitingJob);
//		Assert.assertEquals(job.getDeviceId(), waitingJob.getDeviceId());
//		Assert.assertEquals(job.getTestRunId(), waitingJob.getTestRunId());
//		waitingJob.setStatus(DeviceJobStatus.CHECKEDOUT);
//		
//		// Update to CHECKEDOUT
//		try {
//			mUnit.update(waitingJob);
//		} catch (CbtDaoException e) {
//			fail("Unexpected exception" + e.getMessage());
//		}
//		
//		// Make sure we don't have any more waiting jobs for our device
//		DeviceJob waitingJob2 = mUnit.getOldestWaiting(job.getDeviceId());
//		Assert.assertNull(waitingJob2);
//		
//		// Delete
//		deleteJob(job);
//	}
//	
//	@Test
//	public void testGetById2() {
//		logger.info(mUnit.getByIdWithResult(129L));
//	}
//}
