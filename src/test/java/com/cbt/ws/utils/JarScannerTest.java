package com.cbt.ws.utils;

import org.jooq.tools.json.JSONArray;
import org.junit.Test;

public class JarScannerTest {
	
	@Test
	public void test() throws JarScannerException {
		JarScanner scanner = new JarScanner("/home/saulius/Documents/cbt/tests/OneButtonUiTest.jar");
		System.out.println(new JSONArray().toJSONString(scanner.getTestClasseNames()));		
	}
}
