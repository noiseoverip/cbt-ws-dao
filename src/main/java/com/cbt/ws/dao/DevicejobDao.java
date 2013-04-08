package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.DeviceJob.DEVICE_JOB;

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
import com.cbt.ws.jooq.enums.DeviceJobStatus;
import com.cbt.ws.jooq.tables.records.DeviceJobRecord;
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
		Long deviceJobId = sqexec
				.insertInto(DEVICE_JOB, DEVICE_JOB.DEVICE_ID, DEVICE_JOB.TESTRUN_ID, DEVICE_JOB.CREATED)
				.values(deviceJob.getDeviceId(), deviceJob.getTestRunId(),
						new Timestamp(Calendar.getInstance().getTimeInMillis())).returning(DEVICE_JOB.DEVICEJOB_ID)
				.fetchOne().getDevicejobId();
		mLogger.trace("Added device job, new id:" + deviceJobId);
		return deviceJobId;
	}

	/**
	 * Delete deviceJob
	 * 
	 * @param deviceJob
	 * @throws CbtDaoException
	 */
	public void delete(DeviceJob deviceJob) throws CbtDaoException {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Updating deviceJob");
		int count = sqexec.delete(DEVICE_JOB).where(DEVICE_JOB.DEVICEJOB_ID.eq(deviceJob.getId())).execute();

		if (count != 1) {
			mLogger.error("Could delete deviceJob:" + deviceJob);
			throw new CbtDaoException("Could not update deviceJob");
		}
		mLogger.trace("Deleted job, result: " + count);
	}

	/**
	 * Get device jobs
	 * 
	 * @return
	 */
	public DeviceJob[] getAll() {
		List<DeviceJob> testExecutions = new ArrayList<DeviceJob>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(DEVICE_JOB).fetch();
		for (Record r : result) {
			DeviceJob tc = DeviceJob.fromJooqRecord((DeviceJobRecord) r);
			testExecutions.add(tc);
			mLogger.debug(tc);
		}
		return testExecutions.toArray(new DeviceJob[testExecutions.size()]);
	}

	/**
	 * Get devicesjobs of specified test run
	 * 
	 * @param testRunId
	 * @return
	 */
	public DeviceJob[] getByTestRunId(Long testRunId) {
		List<DeviceJob> testExecutions = new ArrayList<DeviceJob>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(DEVICE_JOB).where(DEVICE_JOB.TESTRUN_ID.eq(testRunId)).fetch();
		for (Record r : result) {
			DeviceJob tc = DeviceJob.fromJooqRecord((DeviceJobRecord) r);
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
		DeviceJobRecord record = (DeviceJobRecord) sqexec.select().from(DEVICE_JOB)
				.where(DEVICE_JOB.DEVICE_ID.eq(deviceId).and(DEVICE_JOB.STATUS.eq(DeviceJobStatus.WAITING)))
				.orderBy(DEVICE_JOB.CREATED.asc()).limit(0, 1).fetchOne();
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
		int count = sqexec.update(DEVICE_JOB).set(DEVICE_JOB.STATUS, deviceJob.getStatus())
				.where(DEVICE_JOB.DEVICEJOB_ID.eq(deviceJob.getId())).execute();

		if (count != 1) {
			mLogger.error("Could not update deviceJob:" + deviceJob);
			throw new CbtDaoException("Could not update deviceJob");
		}
		mLogger.trace("Updated device job, result: " + count);
	}
	
	/**
	 * Get device job by id
	 * 
	 * @param id
	 * @return
	 */
	public DeviceJob getById(Long id) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		DeviceJobRecord record = (DeviceJobRecord) sqexec.select().from(DEVICE_JOB)
				.where(DEVICE_JOB.DEVICEJOB_ID.eq(id)).fetchOne();
		return DeviceJob.fromJooqRecord(record);
	}
}
