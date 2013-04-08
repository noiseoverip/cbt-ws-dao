package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.DeviceJob.DEVICE_JOB;
import static com.cbt.ws.jooq.tables.DeviceJobResult.DEVICE_JOB_RESULT;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.entity.DeviceJob;
import com.cbt.ws.entity.DeviceJobResult;
import com.cbt.ws.exceptions.CbtDaoException;
import com.cbt.ws.jooq.enums.DeviceJobResultState;
import com.cbt.ws.jooq.tables.records.DeviceJobRecord;
import com.cbt.ws.jooq.tables.records.DeviceJobResultRecord;
import com.cbt.ws.mysql.Db;

/**
 * Device job result DAO
 * 
 * @author SauliusAlisauskas 2013-04-07 Initial version
 * 
 */
public class DevicejobResultDao {
	private final Logger mLogger = Logger.getLogger(DevicejobResultDao.class);

	/**
	 * Add device job result
	 * 
	 * @param userid
	 * @return
	 */
	public Long add(DeviceJobResult deviceJobResult) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Adding new device job result");
		Long deviceJobResultId = sqexec
				.insertInto(DEVICE_JOB_RESULT, DEVICE_JOB_RESULT.DEVICEJOBID, DEVICE_JOB_RESULT.OUTPUT,
						DEVICE_JOB_RESULT.STATE, DEVICE_JOB_RESULT.TESTSRUN, DEVICE_JOB_RESULT.TESTSFAILED,
						DEVICE_JOB_RESULT.TESTSERRORS)
				.values(deviceJobResult.getDevicejobId(), deviceJobResult.getOutput(),
						DeviceJobResultState.valueOf(deviceJobResult.getState().toString()),
						deviceJobResult.getTestsRun(), deviceJobResult.getTestsFailed(),
						deviceJobResult.getTestsErrors()).returning(DEVICE_JOB_RESULT.DEVICEJOBRESULT_ID).fetchOne()
				.getDevicejobresultId();
		mLogger.trace("Added device job result, new id:" + deviceJobResultId);
		return deviceJobResultId;
	}

	/**
	 * Get all device job result records
	 * 
	 * @return
	 */
	public DeviceJobResult[] getAll() {
		List<DeviceJobResult> deviceJobResults = new ArrayList<DeviceJobResult>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> results = sqexec.select().from(DEVICE_JOB_RESULT).fetch();
		for (Record r : results) {
			DeviceJobResult tc = DeviceJobResult.fromJooqRecord((DeviceJobResultRecord) r);
			deviceJobResults.add(tc);
			mLogger.debug(tc);
		}
		return deviceJobResults.toArray(new DeviceJobResult[deviceJobResults.size()]);
	}
	
	/**
	 * Get device job result by device job id
	 * 
	 * @param deviceJobId
	 * @return
	 */
	public DeviceJobResult getByDeviceJobId(Long deviceJobId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Record record = sqexec.select().from(DEVICE_JOB_RESULT).where(DEVICE_JOB_RESULT.DEVICEJOBID.eq(deviceJobId))
				.fetchOne();
		DeviceJobResult deviceJobRersult = DeviceJobResult.fromJooqRecord((DeviceJobResultRecord) record);
		return deviceJobRersult;
	}

	/**
	 * Delete device job result record
	 * 
	 * @param deviceJobResult
	 * @throws CbtDaoException
	 */
	public void delete(DeviceJobResult deviceJobResult) throws CbtDaoException {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		mLogger.trace("Updating deviceJob");
		int count = sqexec.delete(DEVICE_JOB_RESULT)
				.where(DEVICE_JOB_RESULT.DEVICEJOBRESULT_ID.eq(deviceJobResult.getId())).execute();

		if (count != 1) {
			mLogger.error("Could delete: " + deviceJobResult);
			throw new CbtDaoException("Could not delete:" + deviceJobResult);
		}
		mLogger.trace("Deleted: " + deviceJobResult);
	}

}
