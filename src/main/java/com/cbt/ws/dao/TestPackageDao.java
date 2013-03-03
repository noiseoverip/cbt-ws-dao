package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testpackage.TESTPACKAGE;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import com.cbt.ws.jooq.tables.records.TestpackageRecord;
import com.cbt.ws.mysql.Db;
import com.cbt.ws.utils.FileOperations;

public class TestPackageDao {

	private String mTestPackageStorePath;

	private final Logger mLogger = Logger.getLogger(TestPackageDao.class);

	@Inject
	public TestPackageDao(@TestFileStorePath String testPackageStorePath) {
		mTestPackageStorePath = testPackageStorePath;
	}

	/**
	 * Get all packages
	 * 
	 * @return
	 */
	public TestPackage[] getPackagesAll() {
		List<TestPackage> packages = new ArrayList<TestPackage>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTPACKAGE).fetch();
		for (Record r : result) {
			TestPackage tp = new TestPackage();
			tp.setId(r.getValue(TESTPACKAGE.TESTPACKAGE_ID));
			tp.setFilePath(r.getValue(TESTPACKAGE.PATH));
			tp.setMetadata(r.getValue(TESTPACKAGE.METADATA));
			packages.add(tp);
			mLogger.debug("ID: " + tp.getId() + " path: " + tp.getFilePath() + " metadata: " + tp.getMetadata());
		}
		return packages.toArray(new TestPackage[packages.size()]);
	}

	/**
	 * Save test package
	 * 
	 * @param testPackage
	 * @param uploadedInputStream
	 * @throws IOException
	 */
	public void storeTestPackage(TestPackage testPackage, InputStream uploadedInputStream) throws IOException {
		// Create new test package record in db -> get it's id
		Long newTestPackageId = createNewTestPackageRecord(testPackage.getOwner().getId());
		mLogger.debug("Generated new id for test package:" + newTestPackageId);

		// Create appropriate folder structure to store the file
		testPackage.setId(newTestPackageId);
		String testPackagePath = createTestPackageFolder(newTestPackageId, testPackage.getOwner().getId());

		// Store the file
		// TODO: manage file names better
		String filePath = testPackagePath + "//" + "uiautomator.jar";
		FileOperations.writeToFile(uploadedInputStream, filePath);

		// Update test package path and other info		
		testPackage.setFilePath(filePath);
		updateTestPackage(testPackage);
	}

	/**
	 * Create new TestPackage record mainly to generate new id
	 * 
	 * @param userid
	 * @return
	 */
	private Long createNewTestPackageRecord(Integer userid) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		TestpackageRecord result = sqexec.insertInto(TESTPACKAGE, TESTPACKAGE.USER_ID).values(userid)
				.returning(TESTPACKAGE.TESTPACKAGE_ID).fetchOne();
		return result.getTestpackageId();
	}

	/**
	 * Create appropriate folder structure for holding test package. e.g. /userid/testpackageid/
	 * 
	 * @param packagId
	 * @param userId
	 * @return
	 */
	private String createTestPackageFolder(Long packagId, Integer userId) {
		// create user folder if not existing
		String path = mTestPackageStorePath + userId + "//tp-" + packagId;
		if (new File(path).mkdirs()) {
			mLogger.info("New folder created:" + path);
			return path;
		}
		return null;
	}
	
	/**
	 * Update test package information
	 * 
	 * @param testPackage
	 */
	private void updateTestPackage(TestPackage testPackage) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		
		if (sqexec.update(TESTPACKAGE).set(TESTPACKAGE.PATH, testPackage.getFilePath())
				.where(TESTPACKAGE.TESTPACKAGE_ID.eq(testPackage.getId())).execute() != 1){
			mLogger.error("Failed to update package:" + testPackage);
		} else {
			mLogger.debug("Test package updated:" + testPackage);
		}		
	}
}
