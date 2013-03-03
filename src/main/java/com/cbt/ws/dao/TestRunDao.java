package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testrun.TESTRUN;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.TestRun;
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
	 * Get test runs
	 * 
	 * @return
	 */
	public TestRun[] getAll() {
		List<TestRun> testRuns = new ArrayList<TestRun>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTRUN).fetch();
		for (Record r : result) {
			TestRun tr = new TestRun();
			tr.setId(r.getValue(TESTRUN.TESTRUN_ID));
			tr.setTestConfigId(r.getValue(TESTRUN.TESTCONFIG_ID));
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
	 * Add new test run
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(TestRun testConfig) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Starting new test run");
		Long testRunId = sqexec
				.insertInto(TESTRUN, 
						TESTRUN.USER_ID, 
						TESTRUN.TESTCONFIG_ID, 
						TESTRUN.CREATED)
				.values(testConfig.getUserId(), 
						testConfig.getTestConfigId(), 
						new Timestamp(Calendar.getInstance().getTimeInMillis()))
				.returning(TESTRUN.TESTRUN_ID).fetchOne().getTestrunId();
		mLogger.trace("Added test run, new id:" + testRunId);		
		return testRunId;
	}
}
