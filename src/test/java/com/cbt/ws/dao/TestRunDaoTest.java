package com.cbt.ws.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.cbt.ws.entity.TestRun;
import com.cbt.ws.entity.complex.TestRunComplex;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.enums.TestrunStatus;

/**
 * Unit test for {@link TestRun}
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 *
 */
public class TestRunDaoTest {
	
	private final Logger mLogger = Logger.getLogger(TestRunDaoTest.class);
	
	@Test
	public void testAddTestRun() {
		TestRunDao dao = new TestRunDao();
		TestRun testRun = createNew(dao, null);		
		
		assertNotNull(testRun);
		assertTrue(testRun.getId() > 0);
		
		TestRunComplex complex = dao.getTestRunComplex(testRun.getId());
		mLogger.info(complex);
		
		//Clean up		
		delete(dao, testRun);		
	}
	
	@Test
	public void testUpdateTestRun() {
		TestRunDao dao = new TestRunDao();
		TestRun testRun = createNew(dao, null);		
		testRun.setStatus(TestrunStatus.RUNNING);
		
		try {
			dao.update(testRun);
		} catch (CbtDaoException e) {
			fail("Could not update");
		}		
		
		TestRun fetchedTestRun = dao.getTestRun(testRun.getId());		
		assertEquals(testRun, fetchedTestRun);		
		delete(dao, testRun);		
	}
	
	/**
	 * Create new device and verify
	 * @param dao
	 * @param device
	 * @return
	 */
	private TestRun createNew(TestRunDao dao, TestRun testRun) {		
		if (null == testRun) {
			testRun = new TestRun();
			testRun.setUserId(1L);
			testRun.setTestConfigId(32L);			
		}		
		Long testRunId = dao.add(testRun);
		assertTrue("Failed to add new device:" + testRun, testRunId > 0);
		testRun.setId(testRunId);
		assertTrue("Failed to add new device:" + testRun, verify(dao, testRunId, testRun));
		return testRun;
	}
	
	/**
	 * Delete test run
	 * 
	 * @param dao
	 * @param testRun
	 */
	private void delete(TestRunDao dao, TestRun testRun) {
		try {
			dao.delete(testRun);
		} catch (CbtDaoException e) {
			mLogger.error("Could not delete", e);
			fail("Could not delete " + testRun);
		}
		// TODO: add verify that it was deleted
	}
	
	/**
	 * Fetch testRun and verify that it is equal to one specified
	 * 
	 * @param dao
	 * @param deviceId
	 * @param device2
	 * @return
	 */
	private boolean verify(TestRunDao dao, Long testRunId, TestRun testRun) {
		TestRun fetchedTestRun = dao.getTestRun(testRunId);
		// synchronize Status since we don't really care now
		testRun.setStatus(fetchedTestRun.getStatus());
		return fetchedTestRun.equals(testRun);		
	}
			
}
