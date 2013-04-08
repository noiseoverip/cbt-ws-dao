package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.User.USER;

import java.util.Map;

import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

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
	public boolean authenticate(String username, String password) {		
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		UserRecord record = (UserRecord) sqexec.select().from(USER)
				.where(USER.NAME.eq(username).and(USER.PASSWORD.eq(password))).fetchOne();
		if (null != record) {
			return true;
		} else {
			return false;
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
		UserRecord record = (UserRecord) sqexec.select().from(USER).where(USER.USER_ID.eq(userId)).fetchOne();
		return record.intoMap();
	}
}
