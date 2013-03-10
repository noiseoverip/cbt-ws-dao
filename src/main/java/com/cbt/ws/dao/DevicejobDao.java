package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Devicejobs.DEVICEJOBS;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.DeviceJob;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.enums.DevicejobsStatus;
import com.cbt.ws.jooq.tables.records.DevicejobsRecord;
import com.cbt.ws.mysql.Db;

/**
 * Device job DAO
 * 
 * @author SauliusALisauskas 2013-03-03 Initial version
 * 
 */
public class DevicejobDao {

	private final Logger mLogger = Logger.getLogger(DevicejobDao.class);

	/**
	 * Add new test configuration
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(DeviceJob deviceJob) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Adding new device job");
		Long testConfigID = sqexec
				.insertInto(DEVICEJOBS, DEVICEJOBS.DEVICE_ID, DEVICEJOBS.TESTRUN_ID, DEVICEJOBS.CREATED)
				.values(deviceJob.getDeviceId(), deviceJob.getTestRunId(),
						new Timestamp(Calendar.getInstance().getTimeInMillis())).returning(DEVICEJOBS.DEVICEJOB_ID)
				.fetchOne().getDevicejobId();
		mLogger.trace("Added device job, new id:" + testConfigID);
		return testConfigID;
	}
	
	/**
	 * Get device jobs
	 * 
	 * @return
	 */
	public DeviceJob[] getAll() {
		List<DeviceJob> testExecutions = new ArrayList<DeviceJob>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(DEVICEJOBS).fetch();
		for (Record r : result) {
			DeviceJob tc = new DeviceJob();
			tc.setId(r.getValue(DEVICEJOBS.DEVICEJOB_ID));
			tc.setDeviceId(r.getValue(DEVICEJOBS.DEVICE_ID));
			tc.setTestRunId(r.getValue(DEVICEJOBS.TESTRUN_ID));
			tc.setCreated(r.getValue(DEVICEJOBS.CREATED));
			tc.setUpdated(r.getValue(DEVICEJOBS.UPDATED));
			tc.setStatus(r.getValue(DEVICEJOBS.STATUS));
			testExecutions.add(tc);
			mLogger.debug(tc);
		}
		return testExecutions.toArray(new DeviceJob[testExecutions.size()]);
	}

	/**
	 * Get oldest job with status WAITING
	 * 
	 * @param deviceId
	 * @return
	 */
	public DeviceJob getOldestWaiting(Long deviceId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		DevicejobsRecord record = (DevicejobsRecord) sqexec.select().from(DEVICEJOBS)
				.where(DEVICEJOBS.DEVICE_ID.eq(deviceId).and(DEVICEJOBS.STATUS.eq(DevicejobsStatus.WAITING)))
				.orderBy(DEVICEJOBS.CREATED.asc()).limit(0, 1).fetchOne();		
		return DeviceJob.fromJooqRecord(record);
	}
	
	/**
	 * Update deviceJob, we should only need to update: state
	 * 
	 * @param deviceJob
	 * @throws CbtDaoException 
	 */
	public void update(DeviceJob deviceJob) throws CbtDaoException {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Updating deviceJob");
		int count = sqexec
				.update(DEVICEJOBS)
				.set(DEVICEJOBS.STATUS, deviceJob.getStatus())
				.where(DEVICEJOBS.DEVICEJOB_ID.eq(deviceJob.getId())).execute();
		
		if (count != 1) {
			mLogger.error("Could not update deviceJob:" + deviceJob);
			throw new CbtDaoException("Could not update deviceJob");
		}
		mLogger.trace("Updated device job, result: " + count);		
	}
}
