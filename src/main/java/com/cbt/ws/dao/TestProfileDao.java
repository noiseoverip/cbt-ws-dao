package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.DeviceType.DEVICE_TYPE;
import static com.cbt.ws.jooq.tables.Testprofile.TESTPROFILE;
import static com.cbt.ws.jooq.tables.TestprofileDevices.TESTPROFILE_DEVICES;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.DeviceType;
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
	public TestProfile add(TestProfile testProfile) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Adding new test configuration");
		Long id = sqexec
				.insertInto(TESTPROFILE, TESTPROFILE.USER_ID, TESTPROFILE.MODE, TESTPROFILE.NAME)
				.values(testProfile.getUserId(), testProfile.getMode(), testProfile.getName())
				.returning(TESTPROFILE.ID).fetchOne().getId();
		mLogger.trace("Added test configuration, enw id:" + id);
		mLogger.trace("Adding device list to test configuration:" + id);
		if (null != testProfile.getDeviceTypes() && testProfile.getDeviceTypes().size() > 0) {
			for (Long deviceTypeId : testProfile.getDeviceTypes()) {
				mLogger.trace("Addign device id");
				// TODO: improve performance here
				sqexec.insertInto(TESTPROFILE_DEVICES, TESTPROFILE_DEVICES.TESTPROFILE_ID,
						TESTPROFILE_DEVICES.DEVICETYPE_ID).values(id, deviceTypeId).execute();
			}
		} else {
			mLogger.error("Devices were not provided for testprofile:" + id);
		}
		return getById(id);
	}
	
	/**
	 * Get device types of test profile
	 * 
	 * @param testProfileId
	 * @return
	 */
	public List<DeviceType> getDeviceTypesByTestProfile(Long testProfileId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		List<DeviceType> result = sqexec.select().from(DEVICE_TYPE).join(TESTPROFILE_DEVICES)
				.on(TESTPROFILE_DEVICES.DEVICETYPE_ID.eq(DEVICE_TYPE.ID))
				.where(TESTPROFILE_DEVICES.TESTPROFILE_ID.eq(testProfileId))
				.fetch(new RecordMapper<Record, DeviceType>() {
					@Override
					public DeviceType map(Record record) {
						return record.into(DeviceType.class);
					}
				});
		return result;
	}
	
	/**
	 * Get all test profiles
	 * 
	 * @return
	 */
	public TestProfile[] getAll() {
		List<TestProfile> testProfiles = new ArrayList<TestProfile>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTPROFILE).orderBy(TESTPROFILE.UPDATED.desc()).fetch();
		for (Record r : result) {
			TestProfile tp = new TestProfile();
			tp.setId(r.getValue(TESTPROFILE.ID));
			tp.setName(r.getValue(TESTPROFILE.NAME));
			tp.setUserId(r.getValue(TESTPROFILE.USER_ID));
			tp.setMode(r.getValue(TESTPROFILE.MODE));
			// Get devices of this test profile
			List<DeviceType> devices = getDeviceTypesByTestProfile(tp.getId());
			if (null != devices) {
				tp.setDeviceTypesList(devices);
			}
			testProfiles.add(tp);
			
			mLogger.debug(tp);
		}
		return testProfiles.toArray(new TestProfile[testProfiles.size()]);
	}
	
	
	
	/**
	 * Get by user id
	 * 
	 * @param userId
	 * @return
	 */
	public List<TestProfile> getByUserId(Long userId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		List<TestProfile> result = sqexec.select().from(TESTPROFILE).where(TESTPROFILE.USER_ID.eq(userId))
				.orderBy(TESTPROFILE.UPDATED.desc()).fetch(new RecordMapper<Record, TestProfile>() {
					@Override
					public TestProfile map(Record record) {
						TestProfile tp = record.into(TestProfile.class);
						List<DeviceType> devices = getDeviceTypesByTestProfile(tp.getId());
						if (null != devices) {
							tp.setDeviceTypesList(devices);
						}
						return tp;
					}
				});
		return result;
	}
	
	/**
	 * Get by id
	 * 
	 * @param testProfileId
	 * @return
	 */
	public TestProfile getById(Long testProfileId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Record result = sqexec.select().from(TESTPROFILE).where(TESTPROFILE.ID.eq(testProfileId))
				.fetchOne();		
		return result.into(TestProfile.class);
	}
}
