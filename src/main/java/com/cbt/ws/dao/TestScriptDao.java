package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Testscript.TESTSCRIPT;

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
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;

import com.cbt.ws.annotations.TestFileStorePath;
import com.cbt.ws.entity.TestScript;
import com.cbt.ws.jooq.tables.records.TestscriptRecord;
import com.cbt.ws.mysql.Db;
import com.cbt.ws.utils.JarScanner;
import com.cbt.ws.utils.JarScannerException;
import com.cbt.ws.utils.Utils;

/**
 * Test package DAO
 * 
 * @author SauliusALisauskas 2013-03-03 Initial version
 * 
 */
public class TestScriptDao {

	private final Logger mLogger = Logger.getLogger(TestScriptDao.class);

	private String mTestScriptStorePath;

	@Inject
	public TestScriptDao(@TestFileStorePath String testPackageStorePath) {
		mTestScriptStorePath = testPackageStorePath;
	}

	/**
	 * Create new TestPackage record mainly to generate new id
	 * 
	 * @param userid
	 * @return
	 */
	private Long createNewTestScriptRecord(Long userid) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		TestscriptRecord result = sqexec.insertInto(TESTSCRIPT, TESTSCRIPT.USER_ID).values(userid)
				.returning(TESTSCRIPT.TESTSCRIPT_ID).fetchOne();
		return result.getTestscriptId();
	}

	/**
	 * Create appropriate folder structure for holding test package. e.g. /userid/testpackageid/
	 * 
	 * @param packagId
	 * @param userId
	 * @return
	 */
	private String createTestPackageFolder(Long packagId, Long userId) {
		// create user folder if not existing
		String path = mTestScriptStorePath + userId + "//ts-" + packagId;
		if (new File(path).mkdirs()) {
			mLogger.info("New folder created:" + path);
			return path;
		}
		return null;
	}

	/**
	 * Get all test packages
	 * 
	 * @return
	 */
	public TestScript[] getAll() {
		List<TestScript> packages = new ArrayList<TestScript>();
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		Result<Record> result = sqexec.select().from(TESTSCRIPT).fetch();
		for (Record r : result) {
			TestScript ts = r.into(TestScript.class);
			packages.add(ts);
			mLogger.debug("ID: " + ts.getId() + " path: " + ts.getFilePath() + " metadata: " + ts.getMetadata());
		}
		return packages.toArray(new TestScript[packages.size()]);
	}

	/**
	 * Save test package
	 * 
	 * @param testScript
	 * @param uploadedInputStream
	 * @throws IOException
	 */
	public void storeTestScript(TestScript testScript, InputStream uploadedInputStream) throws IOException {
		// Create new test package record in db -> get it's id
		Long newTestPackageId = createNewTestScriptRecord(testScript.getUserId());
		mLogger.debug("Generated new id for test package:" + newTestPackageId);

		// Create appropriate folder structure to store the file
		testScript.setId(newTestPackageId);
		String testPackagePath = createTestPackageFolder(newTestPackageId, testScript.getUserId());

		// Store the file
		// TODO: manage file names better
		String fileName = "uiautomator-" + testScript.getId() + ".jar";
		String filePath = testPackagePath + "//" + fileName;
		Utils.writeToFile(uploadedInputStream, filePath);

		// Parse test class names
		try {
			testScript.setTestClasses(new JarScanner(filePath).getTestClasseNames());
		} catch (JarScannerException e) {
			mLogger.error("Could not parse test class names from " + testScript.getFilePath());
		}

		// Update test package path and other info
		testScript.setFilePath(filePath);
		testScript.setFileName(fileName);
		updateTestScript(testScript);
	}

	/**
	 * Update test package information
	 * 
	 * @param testScript
	 */
	private void updateTestScript(TestScript testScript) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		if (sqexec.update(TESTSCRIPT).set(TESTSCRIPT.PATH, testScript.getFilePath())
				.set(TESTSCRIPT.FILENAME, testScript.getFileName()).set(TESTSCRIPT.NAME, testScript.getName())
				.set(TESTSCRIPT.CLASSES, JSONArray.toJSONString(testScript.getTestClasses()))
				.where(TESTSCRIPT.TESTSCRIPT_ID.eq(testScript.getId())).execute() != 1) {
			mLogger.error("Failed to update package:" + testScript);
		} else {
			mLogger.debug("Test package updated:" + testScript);
		}
	}
}
