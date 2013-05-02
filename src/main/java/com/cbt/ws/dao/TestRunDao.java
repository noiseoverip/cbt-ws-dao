package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testconfig.TESTCONFIG;
import static com.cbt.ws.jooq.tables.Testprofile.TESTPROFILE;
import static com.cbt.ws.jooq.tables.TestprofileDevices.TESTPROFILE_DEVICES;
import static com.cbt.ws.jooq.tables.Testrun.TESTRUN;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.TestConfig;
import com.cbt.ws.entity.TestProfile;
import com.cbt.ws.entity.TestRun;
import com.cbt.ws.entity.complex.TestRunComplex;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.tables.records.TestrunRecord;
import com.cbt.ws.mysql.Db;

/**
 * Test run DAO
 * 
 * @author SauliusALisauskas 2013-03-03 Initial version
 * 
 */
public class TestRunDao {

	private final Logger mLogger = Logger.getLogger(TestRunDao.class);

	/**
	 * Add new test run
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(TestRun testRun) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Starting new test run");
		Long testRunId = sqexec
				.insertInto(TESTRUN, TESTRUN.USER_ID, TESTRUN.TEST_CONFIG_ID, TESTRUN.CREATED)
				.values(testRun.getUserId(), testRun.getTestConfigId(),
						new Timestamp(Calendar.getInstance().getTimeInMillis())).returning(TESTRUN.ID)
				.fetchOne().getId();
		mLogger.trace("Added test run, new id:" + testRunId);
		return testRunId;
	}

	/**
	 * Delete testRun
	 * 
	 * @param testRun
	 * @throws CbtDaoException
	 */
	public void delete(TestRun testRun) throws CbtDaoException {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		int result = sqexec.delete(TESTRUN).where(TESTRUN.ID.eq(testRun.getId())).execute();
		if (result != 1) {
			throw new CbtDaoException("Error while deleting device, result:" + result);
		}
	}

	/**
	 * Get test runs
	 * 
	 * @return
	 */
	public TestRun[] getAll() {
		List<TestRun> testRuns = new ArrayList<TestRun>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTRUN).orderBy(TESTRUN.CREATED.desc()).fetch();
		for (Record r : result) {
			TestRun tr = new TestRun();
			tr.setId(r.getValue(TESTRUN.ID));
			tr.setTestConfigId(r.getValue(TESTRUN.TEST_CONFIG_ID));
			tr.setCreated(r.getValue(TESTRUN.CREATED));
			tr.setUpdated(r.getValue(TESTRUN.UPDATED));
			tr.setStatus(r.getValue(TESTRUN.STATUS));
			tr.setUserId(r.getValue(TESTRUN.USER_ID));
			testRuns.add(tr);
			mLogger.debug(tr);
		}
		return testRuns.toArray(new TestRun[testRuns.size()]);
	}

	/**
	 * Get test runs of specific user
	 * 
	 * @param userId
	 * @return
	 */
	public List<TestRun> getByUserId(Long userId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		List<TestRun> result = sqexec.select().from(TESTRUN).where(TESTRUN.USER_ID.eq(userId))
				.orderBy(TESTRUN.UPDATED.desc()).fetch(new RecordMapper<Record, TestRun>() {
					@Override
					public TestRun map(Record record) {
						TestRun tp = record.into(TestRun.class);
						return tp;
					}
				});
		return result;
	}

	/**
	 * Get TestRun
	 * 
	 * @param testRunId
	 * @return
	 */
	public TestRun getTestRun(Long testRunId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		TestrunRecord record = (TestrunRecord) sqexec.select().from(TESTRUN).where(TESTRUN.ID.eq(testRunId))
				.fetchOne();
		return record.into(TestRun.class);
	}

	/**
	 * Get Test more complex test run information
	 * 
	 * @param testRunId
	 * @return
	 */
	public TestRunComplex getTestRunComplex(Long testRunId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Record result = sqexec.select().from(TESTRUN).join(TESTCONFIG)
				.on(TESTCONFIG.ID.eq(TESTRUN.TEST_CONFIG_ID)).join(TESTPROFILE)
				.on(TESTPROFILE.ID.eq(TESTCONFIG.TEST_PROFILE_ID)).where(TESTRUN.ID.eq(testRunId)).fetchOne();

		TestRunComplex testRun = new TestRunComplex();
		testRun.setId(testRunId);
		testRun.setTestConfigId(result.getValue(TESTCONFIG.ID));
		testRun.setTestProfileId(result.getValue(TESTPROFILE.ID));
		testRun.setTestProfile(result.into(TestProfile.class));
		testRun.setTestConfig(result.into(TestConfig.class));

		// Construct device type list
		Result<Record> resultDeviceTypes = sqexec.select().from(TESTPROFILE_DEVICES)
				.where(TESTPROFILE_DEVICES.TESTPROFILE_ID.eq(testRun.getTestProfileId())).fetch();
		List<Long> deviceTypes = new ArrayList<Long>(resultDeviceTypes.size());
		for (Record r : resultDeviceTypes) {
			deviceTypes.add(r.getValue(TESTPROFILE_DEVICES.DEVICETYPE_ID));
		}
		testRun.setDeviceTypes(deviceTypes);
		return testRun;
	}

	/**
	 * Update TestRun record
	 * 
	 * @param testRun
	 * @throws CbtDaoException
	 */
	public void update(TestRun testRun) throws CbtDaoException {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		int count = sqexec.update(TESTRUN).set(TESTRUN.STATUS, testRun.getStatus())
				.where(TESTRUN.ID.eq(testRun.getId())).execute();

		if (count != 1) {
			throw new CbtDaoException("Could not update device");
		}
	}
}
