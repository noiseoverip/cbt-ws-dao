package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testconfig.TESTCONFIG;
import static com.cbt.ws.jooq.tables.TestconfigDevices.TESTCONFIG_DEVICES;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.TestConfiguration;
import com.cbt.ws.mysql.Db;

public class TestConfigDao {

	private final Logger mLogger = Logger.getLogger(TestConfigDao.class);

	/**
	 * Get test configurations
	 * 
	 * @return
	 */
	public TestConfiguration[] getAll() {
		List<TestConfiguration> testConfigurations = new ArrayList<TestConfiguration>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTCONFIG).fetch();
		for (Record r : result) {
			TestConfiguration tc = new TestConfiguration();
			tc.setId(r.getValue(TESTCONFIG.TESTCONFIG_ID));
			tc.setMode(r.getValue(TESTCONFIG.MODE));			
			tc.setMetadata(r.getValue(TESTCONFIG.METADATA));
			testConfigurations.add(tc);
			mLogger.debug(tc);
		}
		return testConfigurations.toArray(new TestConfiguration[testConfigurations.size()]);
	}

	/**
	 * Create new TestPackage record mainly to generate new id
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(TestConfiguration tConfiguration) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		
		mLogger.trace("Adding new test configuration");
		Long testConfigID = sqexec.insertInto(TESTCONFIG, TESTCONFIG.MODE, TESTCONFIG.DEVICETYPECOUNT)
				.values(tConfiguration.getMode(), tConfiguration.getDeviceTypes().size())
				.returning(TESTCONFIG.TESTCONFIG_ID).fetchOne().getTestconfigId();
		mLogger.trace("Added test configuration, enw id:" + testConfigID);
		mLogger.trace("Adding device list to test configuration:" + testConfigID);
		for (Integer deviceTypeId : tConfiguration.getDeviceTypes()) { 
			mLogger.trace("Addign device id");
			//TODO: improve performance here
			sqexec.insertInto(TESTCONFIG_DEVICES, TESTCONFIG_DEVICES.TESTCONFIG_ID, TESTCONFIG_DEVICES.DEVICE_TYPE_ID)
					.values(testConfigID, deviceTypeId).execute();
		}
		return testConfigID;
	}
}
