package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testprofile.TESTPROFILE;
import static com.cbt.ws.jooq.tables.TestprofileDevices.TESTPROFILE_DEVICES;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.TestProfile;
import com.cbt.ws.mysql.Db;

/**
 * Test profile DAO
 * 
 * @author SauliusALisauskas 2013-03-03 Initial version
 * 
 */
public class TestProfileDao {

	private final Logger mLogger = Logger.getLogger(TestProfileDao.class);

	/**
	 * Add new test profile
	 * 
	 * @param testProfile
	 * @return
	 */
	public Long add(TestProfile testProfile) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Adding new test configuration");
		Long TESTPROFILEID = sqexec
				.insertInto(TESTPROFILE, TESTPROFILE.USER_ID, TESTPROFILE.MODE, TESTPROFILE.DEVICETYPECOUNT)
				.values(testProfile.getUserId(), testProfile.getMode(), testProfile.getDeviceTypes().size())
				.returning(TESTPROFILE.TESTPROFILE_ID).fetchOne().getTestprofileId();
		mLogger.trace("Added test configuration, enw id:" + TESTPROFILEID);
		mLogger.trace("Adding device list to test configuration:" + TESTPROFILEID);
		for (Long deviceTypeId : testProfile.getDeviceTypes()) {
			mLogger.trace("Addign device id");
			// TODO: improve performance here
			sqexec.insertInto(TESTPROFILE_DEVICES, TESTPROFILE_DEVICES.TESTPROFILE_ID,
					TESTPROFILE_DEVICES.DEVICE_TYPE_ID).values(TESTPROFILEID, deviceTypeId).execute();
		}
		return TESTPROFILEID;
	}

	/**
	 * Get all test profiles
	 * 
	 * @return
	 */
	public TestProfile[] getAll() {
		List<TestProfile> testProfiles = new ArrayList<TestProfile>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTPROFILE).fetch();
		for (Record r : result) {
			TestProfile tc = new TestProfile();
			tc.setId(r.getValue(TESTPROFILE.TESTPROFILE_ID));
			tc.setUserId(r.getValue(TESTPROFILE.USER_ID));
			tc.setMode(r.getValue(TESTPROFILE.MODE));
			tc.setMetadata(r.getValue(TESTPROFILE.METADATA));
			testProfiles.add(tc);
			mLogger.debug(tc);
		}
		return testProfiles.toArray(new TestProfile[testProfiles.size()]);
	}
}
