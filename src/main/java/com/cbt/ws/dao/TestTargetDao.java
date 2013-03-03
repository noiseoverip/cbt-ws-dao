package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testtarget.TESTTARGET;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.annotations.TestFileStorePath;
import com.cbt.ws.entity.TestPackage;
import com.cbt.ws.entity.TestTarget;
import com.cbt.ws.jooq.tables.records.TestpackageRecord;
import com.cbt.ws.jooq.tables.records.TesttargetRecord;
import com.cbt.ws.mysql.Db;
import com.cbt.ws.utils.FileOperations;

public class TestTargetDao {

	private final Logger mLogger = Logger.getLogger(TestTargetDao.class);

	private String mFileStorePath;

	@Inject
	public TestTargetDao(@TestFileStorePath String testFileStorePath) {
		mFileStorePath = testFileStorePath;
	}

	/**
	 * Get all packages
	 * 
	 * @return
	 */
	public TestTarget[] getTestTargetAll() {
		List<TestTarget> applications = new ArrayList<TestTarget>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTTARGET).fetch();
		for (Record r : result) {
			TestTarget tp = new TestTarget();			
			tp.setId(r.getValue(TESTTARGET.TESTTARGET_ID));
			tp.setFilePath(r.getValue(TESTTARGET.PATH));
			tp.setMetadata(r.getValue(TESTTARGET.METADATA));
			applications.add(tp);
			mLogger.debug("ID: " + tp.getId() + " path: " + tp.getFilePath() + " metadata: " + tp.getMetadata());
		}
		return applications.toArray(new TestTarget[applications.size()]);
	}

	/**
	 * Save test package
	 * 
	 * @param testTarget
	 * @param uploadedInputStream
	 * @throws IOException
	 */
	public void storeTestTarget(TestTarget testTarget, InputStream uploadedInputStream) throws IOException {
		// Create new test package record in db -> get it's id
		Long newTestPackageId = createNewTestPackageRecord(testTarget.getUserId());
		mLogger.debug("Generated new id for test package:" + newTestPackageId);

		// Create appropriate folder structure to store the file
		testTarget.setId(newTestPackageId);
		String testPackagePath = createTestTargetFolder(newTestPackageId, testTarget.getUserId());

		// Store the file
		// TODO: manage file names better
		String filePath = testPackagePath + "//app-" + testTarget.getId() + ".apk";
		FileOperations.writeToFile(uploadedInputStream, filePath);

		// Update path and other info		
		testTarget.setFilePath(filePath);
		updateTestTarget(testTarget);
	}

	/**
	 * Create new TestPackage record mainly to generate new id
	 * 
	 * @param userid
	 * @return
	 */
	private Long createNewTestPackageRecord(Long userid) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		TesttargetRecord result = sqexec.insertInto(TESTTARGET, TESTTARGET.USER_ID).values(userid)
				.returning(TESTTARGET.TESTTARGET_ID).fetchOne();
		return result.getTesttargetId();
	}

	/**
	 * Create appropriate folder structure for holding test package. e.g. /userid/testpackageid/
	 * 
	 * @param targetId
	 * @param userId
	 * @return
	 */
	private String createTestTargetFolder(Long targetId, Long userId) {
		// create user folder if not existing
		String path = mFileStorePath + userId + "//tt-" + targetId;
		if (new File(path).mkdirs()) {
			mLogger.info("New folder created:" + path);
			return path;
		}
		return null;
	}

	private void updateTestTarget(TestTarget testTarget) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		
		if (sqexec.update(TESTTARGET).set(TESTTARGET.PATH, testTarget.getFilePath())
				.where(TESTTARGET.TESTTARGET_ID.eq(testTarget.getId())).execute() != 1){
			mLogger.error("Failed to update package:" + testTarget);
		} else {
			mLogger.debug("Test package updated:" + testTarget);
		}		
	}
}
