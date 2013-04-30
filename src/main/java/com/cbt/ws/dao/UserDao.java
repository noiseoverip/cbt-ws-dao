package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Device.DEVICE;
import static com.cbt.ws.jooq.tables.DeviceJob.DEVICE_JOB;
import static com.cbt.ws.jooq.tables.Testrun.TESTRUN;
import static com.cbt.ws.jooq.tables.User.USER;

import java.util.List;
import java.util.Map;

import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;
import org.jooq.impl.Factory;

import com.cbt.ws.entity.User;
import com.cbt.ws.jooq.tables.records.UserRecord;
import com.cbt.ws.mysql.Db;

/**
 * User Dao
 * 
 * @author SauliusAlisauskas 2013-04-08 Initial version
 * 
 */
public class UserDao {

	/**
	 * Authenticate user
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User authenticate(String username, String password) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		UserRecord record = (UserRecord) sqexec.select().from(USER)
				.where(USER.NAME.eq(username).and(USER.PASSWORD.eq(password))).fetchOne();
		return record.into(User.class);
	}
	
	/**
	 * Get user by name
	 * 
	 * @param name
	 * @return
	 */
	public User getUserByName(String name) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		UserRecord record = (UserRecord) sqexec.select().from(USER)
				.where(USER.NAME.eq(name)).fetchOne();
		return record.into(User.class);
	}
	
	/**
	 * Create new user
	 */
	public User createNew(String username, String password ) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		UserRecord user = sqexec.newRecord(USER);
		user.setName(username);
		user.setPassword(password);
		if (user.insert()== 1) {
			return user.into(User.class);
		} else {
			return null;
		}
	}

	/**
	 * Get user object
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getUserById(Long userId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		UserRecord record = (UserRecord) sqexec.select().from(USER).where(USER.ID.eq(userId)).fetchOne();
		return record.intoMap();
	}

	/**
	 * Get test runs per device hosted by user
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getUserHostedTestStats(Long userId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record2<Long, Integer>> result = sqexec.select(DEVICE_JOB.DEVICE_ID, Factory.count().as("runs"))
				.from(DEVICE_JOB).join(DEVICE).on(DEVICE_JOB.DEVICE_ID.eq(DEVICE.ID))
				.where(DEVICE.USER_ID.eq(userId)).groupBy(DEVICE_JOB.DEVICE_ID).fetch();
		return result.intoMaps();
	}
	
	/**
	 * Get statistics of services consumed by user
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getUserRunTestStats(Long userId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record1<Integer>> result = sqexec.select(Factory.count().as("runs")).from(DEVICE_JOB).join(TESTRUN)
				.on(DEVICE_JOB.TESTRUN_ID.eq(TESTRUN.ID)).where(TESTRUN.USER_ID.eq(userId)).fetch();
		return result.intoMaps();
	}
	
	/**
	 * Get list of users
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAllUsers() {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record2<Long, String>> result = sqexec.select(USER.ID, USER.NAME).from(USER).fetch();
		return result.intoMaps();
	}	
}
