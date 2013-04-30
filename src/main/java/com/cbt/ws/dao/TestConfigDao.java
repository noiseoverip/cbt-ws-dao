package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testconfig.TESTCONFIG;
import static com.cbt.ws.jooq.tables.Testrun.TESTRUN;
import static com.cbt.ws.jooq.tables.Testscript.TESTSCRIPT;
import static com.cbt.ws.jooq.tables.Testtarget.TESTTARGET;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.TestConfig;
import com.cbt.ws.entity.TestRun;
import com.cbt.ws.entity.TestScript;
import com.cbt.ws.entity.TestTarget;
import com.cbt.ws.entity.complex.TestConfigComplex;
import com.cbt.ws.mysql.Db;

/**
 * Test configuration DAO
 * 
 * @author SauliusALisauskas 2013-03-03 Initial version
 * 
 */
public class TestConfigDao {

	private final Logger mLogger = Logger.getLogger(TestConfigDao.class);

	// TODO: remove, use only complex
	/**
	 * Get test configurations
	 * 
	 * @return
	 */
	public TestConfig[] getAll() {
		List<TestConfig> testExecutions = new ArrayList<TestConfig>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTCONFIG).orderBy(TESTCONFIG.UPDATED.desc()).fetch();
		for (Record r : result) {
			TestConfig tc = new TestConfig();
			tc.setId(r.getValue(TESTCONFIG.ID));
			tc.setTestTargetId(r.getValue(TESTCONFIG.TEST_TARGET_ID));
			tc.setTestScriptId(r.getValue(TESTCONFIG.TEST_SCRIPT_ID));
			tc.setTestProfileId(r.getValue(TESTCONFIG.TEST_PROFILE_ID));
			tc.setName(r.getValue(TESTCONFIG.NAME));
			tc.setMetadata(r.getValue(TESTCONFIG.METADATA));
			tc.setUserId(r.getValue(TESTCONFIG.USER_ID));
			tc.setUpdated(r.getValue(TESTCONFIG.UPDATED));
			testExecutions.add(tc);
			mLogger.debug(tc);
		}
		return testExecutions.toArray(new TestConfig[testExecutions.size()]);
	}

	/**
	 * Get test configuration full objects
	 * 
	 * @return
	 */
	public TestConfigComplex[] getAllComplex() {
		List<TestConfigComplex> testExecutions = new ArrayList<TestConfigComplex>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTCONFIG).join(TESTSCRIPT)
				.on(TESTSCRIPT.TESTSCRIPT_ID.eq(TESTCONFIG.TEST_SCRIPT_ID)).join(TESTTARGET)
				.on(TESTTARGET.TESTTARGET_ID.eq(TESTCONFIG.TEST_TARGET_ID)).orderBy(TESTCONFIG.UPDATED.desc()).fetch();

		for (Record r : result) {
			TestConfigComplex tc = new TestConfigComplex();
			tc.setId(r.getValue(TESTCONFIG.ID));
			tc.setTestTarget(r.into(TestTarget.class));
			tc.setTestScript(r.into(TestScript.class));
			tc.setTestProfileId(r.getValue(TESTCONFIG.TEST_PROFILE_ID));
			tc.setName(r.getValue(TESTCONFIG.NAME));
			tc.setMetadata(r.getValue(TESTCONFIG.METADATA));
			tc.setUserId(r.getValue(TESTCONFIG.USER_ID));
			tc.setUpdated(r.getValue(TESTCONFIG.UPDATED));
			testExecutions.add(tc);
			mLogger.debug(tc);
		}
		return testExecutions.toArray(new TestConfigComplex[testExecutions.size()]);
	}

	/**
	 * Get test runs of specific user
	 * 
	 * @param userId
	 * @return
	 */
	public TestConfigComplex[] getByUserId(Long userId) {

		List<TestConfigComplex> testExecutions = new ArrayList<TestConfigComplex>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTCONFIG).join(TESTSCRIPT)
				.on(TESTSCRIPT.TESTSCRIPT_ID.eq(TESTCONFIG.TEST_SCRIPT_ID)).join(TESTTARGET)
				.on(TESTTARGET.TESTTARGET_ID.eq(TESTCONFIG.TEST_TARGET_ID)).where(TESTCONFIG.USER_ID.eq(userId))
				.orderBy(TESTCONFIG.UPDATED.desc()).fetch();

		for (Record r : result) {
			TestConfigComplex tc = new TestConfigComplex();
			tc.setId(r.getValue(TESTCONFIG.ID));
			tc.setTestTarget(r.into(TestTarget.class));
			tc.setTestScript(r.into(TestScript.class));
			tc.setTestProfileId(r.getValue(TESTCONFIG.TEST_PROFILE_ID));
			tc.setName(r.getValue(TESTCONFIG.NAME));
			tc.setMetadata(r.getValue(TESTCONFIG.METADATA));
			tc.setUserId(r.getValue(TESTCONFIG.USER_ID));
			tc.setUpdated(r.getValue(TESTCONFIG.UPDATED));
			testExecutions.add(tc);
			mLogger.debug(tc);
		}
		return testExecutions.toArray(new TestConfigComplex[testExecutions.size()]);
	}

	/**
	 * Add new test configuration
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(TestConfig testConfig) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Adding new test configuration");
		Long testConfigID = sqexec
				.insertInto(TESTCONFIG, TESTCONFIG.USER_ID, TESTCONFIG.NAME, TESTCONFIG.TEST_SCRIPT_ID,
						TESTCONFIG.TEST_TARGET_ID, TESTCONFIG.TEST_PROFILE_ID, TESTCONFIG.METADATA)
				.values(testConfig.getUserId(), testConfig.getName(), testConfig.getTestScriptId(),
						testConfig.getTestTargetId(), testConfig.getTestProfileId(), testConfig.getMetadata())
				.returning(TESTCONFIG.ID).fetchOne().getId();
		mLogger.trace("Added test configuration, new id:" + testConfigID);
		return testConfigID;
	}
}
