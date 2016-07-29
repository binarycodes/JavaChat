package org.ju.mtech.chat.api.encryption.ecc;

import java.math.BigDecimal;

public class ECC {
	private BigDecimal a;
	private BigDecimal b;
	private BigDecimal p;

	public boolean checkParameters() {
		boolean status = checkBigPrime(p);
		if (status) {
			double remainder = (4 * Math.pow(a.doubleValue(), 3) + 27 * Math.pow(b.doubleValue(), 2)) % p.doubleValue();
			status = remainder != 0;
		}
		return status;
	}

	private boolean checkBigPrime(BigDecimal number) {
		return true;
	}

	public static String encrypt(String message) {
		return message;
	}

	public static String decrypt(String message) {
		return message;
	}
}
