package info.angrynerds.primes;

import java.math.BigInteger;
import java.math.BigInteger.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Generator {
	
	BigInteger num;
	HashMap<BigInteger, BigInteger> remainders;
	
	UI ui;
	
	public void go() {
		while(true) {
			
			for(BigInteger remainder : remainders.keySet()) {
				if( remainder.add(BigInteger.ONE) == BigInteger.ZERO ) {
					
				}
			}
			
			num = num.add( BigInteger.ONE );
		}
	}
	
	public Generator() {
		
		remainders = new HashMap<BigInteger, BigInteger>();
	}
	
	public static void main(String[] args) {
		new Generator().go();
	}
	
}
