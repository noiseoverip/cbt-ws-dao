package com.cbt.ws.entity;

import static com.cbt.ws.jooq.tables.Testscript.TESTSCRIPT;

import org.jooq.Record;

import com.cbt.ws.utils.Utils;

/**
 * Enitity class representing test script data (file name, path...)
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 *
 */
public class TestScript extends CbtEntity {
	private String filePath;
	private String fileName;
	
	public static TestScript fromJooq(Record r) {
		TestScript tp = new TestScript();
		tp.setId(r.getValue(TESTSCRIPT.TESTSCRIPT_ID));
		tp.setName(r.getValue(TESTSCRIPT.NAME));
		tp.setFilePath(r.getValue(TESTSCRIPT.PATH));
		tp.setFileName(r.getValue(TESTSCRIPT.FILENAME));
		tp.setMetadata(r.getValue(TESTSCRIPT.METADATA));
		return tp;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return Utils.toString("TestScript", "id", getId(),"fileName", fileName, "filePath", filePath);
	}
}
