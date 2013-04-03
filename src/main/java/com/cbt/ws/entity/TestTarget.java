package com.cbt.ws.entity;

import static com.cbt.ws.jooq.tables.Testscript.TESTSCRIPT;
import static com.cbt.ws.jooq.tables.Testtarget.TESTTARGET;

import org.jooq.Record;

import com.cbt.ws.utils.Utils;

/**
 * Entity representing test target (application) data (file name, path)
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 * 
 */
public class TestTarget extends CbtEntity {
	private String fileName;
	private String filePath;
	
	public static TestTarget fromJooq(Record r) {
		TestTarget tp = new TestTarget();
		tp.setId(r.getValue(TESTTARGET.TESTTARGET_ID));
		tp.setFilePath(r.getValue(TESTTARGET.PATH));
		tp.setName(r.getValue(TESTSCRIPT.NAME));
		tp.setFileName(r.getValue(TESTSCRIPT.FILENAME));
		tp.setMetadata(r.getValue(TESTTARGET.METADATA));
		return tp;
	}
	
	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return Utils.toString("TestTarget", "id", getId(), "fileName", fileName, "filePath", filePath);
	}

}
