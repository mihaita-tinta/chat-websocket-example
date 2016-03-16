package com.mihaita.chat;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FactorialObjectOrientedTestCase {
	private static Logger log = Logger.getLogger(FactorialObjectOrientedTestCase.class);
	
	@Test
	public void test() {
		int x = 5;
		Calculator calculator = new Calculator("Calculatorul meu de buzunar");
		log.debug("fact " + x + " = " + calculator.fact(5));
	}
	
	class Calculator {
		private final String marca;
		
		public Calculator(String marca) {
			this.marca = marca;
		}
		
		public String getMarca(){
			return marca;
		}
		
		float fact(int i) {
			if (i < 1)
				return 1;
			return i * fact(i-1);
		}
		
	}
}
