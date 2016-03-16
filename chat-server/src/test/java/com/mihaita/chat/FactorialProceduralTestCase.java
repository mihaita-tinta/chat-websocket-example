package com.mihaita.chat;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FactorialProceduralTestCase {
	private static Logger log = Logger.getLogger(FactorialProceduralTestCase.class);
	
	@Test
	public void test() {
		int x = 5;
		log.debug("fact " + x + " = " + fact(x));
	}
	
	private float fact(int i) {
		if (i < 1)
			return 1;
		return i * fact(i-1);
	}
}
