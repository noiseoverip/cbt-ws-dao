package com.cbt.ws.dao;

import static com.cbt.ws.jooq.tables.Devicejobs.DEVICEJOBS;
import static com.cbt.ws.jooq.tables.Testconfig.TESTCONFIG;
import static com.cbt.ws.jooq.tables.Testpackage.TESTPACKAGE;
import static com.cbt.ws.jooq.tables.Testrun.TESTRUN;
import static com.cbt.ws.jooq.tables.Testtarget.TESTTARGET;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import com.cbt.ws.annotations.TestFileStorePath;
import com.cbt.ws.entity.TestPackage;
import com.cbt.ws.mysql.Db;

/**
 * DAO for checkout(downloading files) operations
 * 
 * @author SauliusAlisauskas 2013-03-05 Initial version
 *
 */
public class CheckoutDao {

	private final Logger mLogger = Logger.getLogger(CheckoutDao.class);
	private String mFileStorePath;

	@Inject
	public CheckoutDao(@TestFileStorePath String testFileStorePath) {
		mFileStorePath = testFileStorePath;
	}
	
	/**
	 * 
	 * Query: SELECT t4.path, t5.path FROM `devicejobs` t1 
	 *			LEFT JOIN `testrun` t2 ON t1.testrun_id=t2.testrun_id 
	 *			LEFT JOIN `testconfig` t3 ON t2.testconfig_id = t3.testconfig_id
	 *			LEFT JOIN `testpackage` t4 ON t3.testpackage_id = t4.testpackage_id
	 *			LEFT JOIN `testtarget` t5 ON t3.testpackage_id = t5.testtarget_id
	 *			WHERE t1.devicejob_id=1	
	 * @param devicejobId
	 * @return
	 */
	public TestPackage getTestPackage(Long devicejobId) {
		Executor sqexec = new Executor(Db.getConnection(), SQLDialect.MYSQL);
		// TODO: improve size of returned data, we only need a couple of fields
		Record result = sqexec.select().from(DEVICEJOBS)
				.join(TESTRUN).on(DEVICEJOBS.TESTRUN_ID.eq(TESTRUN.TESTRUN_ID))
				.join(TESTCONFIG).on(TESTCONFIG.TESTCONFIG_ID.eq(TESTRUN.TESTCONFIG_ID))
				.join(TESTPACKAGE).on(TESTPACKAGE.TESTPACKAGE_ID.eq(TESTCONFIG.TESTPACKAGE_ID))
				.join(TESTTARGET).on(TESTTARGET.TESTTARGET_ID.eq(TESTCONFIG.TESTTARGET_ID))
				.where(DEVICEJOBS.DEVICEJOB_ID.eq(devicejobId))
				.fetchOne();
		
		TestPackage tp = new TestPackage();
		tp.setDevicejobId(devicejobId);
		tp.setTestScriptPath(result.getValue(TESTPACKAGE.PATH));
		tp.setTestTargetPath(result.getValue(TESTTARGET.PATH));
		return tp;
	}	
	
	/**
	 * Build a ZIP file containing file pointing to specified file paths 
	 * 
	 * @param paths
	 * @return - path to built zip file
	 */
	public String buildZipPackage(String[] paths) {

		String zipFile = mFileStorePath + UUID.randomUUID().toString();		

		// create byte buffer
		byte[] buffer = new byte[1024];

		// create object of FileOutputStream
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(zipFile);
		} catch (FileNotFoundException e) {
			mLogger.error("Could not create file output stream to" + zipFile);
		}

		// create object of ZipOutputStream from FileOutputStream
		ZipOutputStream zout = new ZipOutputStream(fout);

		for (int i = 0; i < paths.length; i++) {

			mLogger.info("Zipping file " + paths[i]);
			File file = new File(paths[i]);			

			// create object of FileInputStream for source file
			FileInputStream fin = null;
			try {
				fin = new FileInputStream(paths[i]);
			} catch (FileNotFoundException e) {
				mLogger.error("Could not create file output stream to" + paths[i], e);
			}			
			
			try {
				zout.putNextEntry(new ZipEntry(file.getName()));
			} catch (IOException e) {
				mLogger.error("Could not create new ZIP entry for file:" + paths[i], e);
			}

			int length;
			try {
				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}
			} catch (IOException e) {
				mLogger.error("Error while copying file:" + paths[i], e);
			}

			try {
				zout.closeEntry();
			} catch (IOException e) {
				mLogger.error("Could not close ZIP entry for file" + paths[i], e);
			}

			// close the InputStream
			try {
				fin.close();
			} catch (IOException e) {
				mLogger.error("Could not close file input stream", e);
			}			
		}

		try {
			zout.close();
		} catch (IOException e) {
			mLogger.error("Could not close zip file stream", e);
		}
		return zipFile;
	}
}
