package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.DeviceJob.DEVICE_JOB;

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

import com.cbt.ws.entity.DeviceJob;
import com.cbt.ws.entity.DeviceJobMetadata;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.enums.DeviceJobStatus;
import com.cbt.ws.jooq.tables.records.DeviceJobRecord;
import com.cbt.ws.mysql.Db;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Device job DAO
 * 
 * @author SauliusALisauskas 2013-03-03 Initial version
 * 
 */
public class DevicejobDao {

	private final Logger mLogger = Logger.getLogger(DevicejobDao.class);
	// TODO: use one static instance for all project
	private final ObjectMapper mMapper = new ObjectMapper();

	/**
	 * Add new test configuration
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(DeviceJob deviceJob) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Adding new device job");
		String metadata = null;
		try {
			metadata = mMapper.writeValueAsString(deviceJob.getMetadata());
		} catch (JsonProcessingException e) {
			mLogger.error("Could not convert to JSON:" + deviceJob.getMetadata());
		}		
		Long deviceJobId = sqexec
				.insertInto(DEVICE_JOB, DEVICE_JOB.DEVICE_ID, DEVICE_JOB.TEST_RUN_ID, DEVICE_JOB.CREATED,
						DEVICE_JOB.META)
				.values(deviceJob.getDeviceId(), deviceJob.getTestRunId(),
						new Timestamp(Calendar.getInstance().getTimeInMillis()), metadata)
				.returning(DEVICE_JOB.ID).fetchOne().getId();
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
		int count = sqexec.delete(DEVICE_JOB).where(DEVICE_JOB.ID.eq(deviceJob.getId())).execute();

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
		Result<Record> result = sqexec.select().from(DEVICE_JOB).where(DEVICE_JOB.TEST_RUN_ID.eq(testRunId)).fetch();
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
		return (null != record) ? record.map(deviceJobMapper) : null;
	}
	
	private final RecordMapper<Record, DeviceJob> deviceJobMapper = new RecordMapper<Record, DeviceJob>() {

		@Override
		public DeviceJob map(Record record) {
			DeviceJob deviceJob = record.into(DeviceJob.class);
			DeviceJobMetadata medatada = null;
			try {
				medatada = mMapper.readValue(record.getValue(DEVICE_JOB.META), DeviceJobMetadata.class);
			} catch (Exception e) {
				mLogger.error("Could not mapp " + record.getValue(DEVICE_JOB.META) + " to " + DeviceJobMetadata.class.getSimpleName());
			}
			deviceJob.setMetadata(medatada);
			return deviceJob;
		}
	};
	
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
				.where(DEVICE_JOB.ID.eq(deviceJob.getId())).execute();

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
				.where(DEVICE_JOB.ID.eq(id)).fetchOne();
		return DeviceJob.fromJooqRecord(record);
	}
}
