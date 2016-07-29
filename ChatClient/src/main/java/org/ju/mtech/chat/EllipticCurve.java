package org.ju.mtech.chat;

import java.math.BigDecimal;

public class EllipticCurve {
	private BigDecimal a;
	private BigDecimal b;
	private BigDecimal x;
	private BigDecimal y;

	public EllipticCurve(BigDecimal a, BigDecimal b, BigDecimal x) {
		this.a = a;
		this.b = b;
		this.x = x;
		this.y = computeY();
	}

	public EllipticCurve(long a, long b, long x) {
		this(BigDecimal.valueOf(a), BigDecimal.valueOf(b), BigDecimal.valueOf(x));
	}

	public BigDecimal computeY() {
		BigDecimal y2 = x.pow(3).add(a.multiply(x)).add(b);
		return BigDecimal.valueOf(Math.sqrt(y2.doubleValue()));
	}

	public String toString() {
		return String.format("x:%s, a:%s, b:%s, y:%s", this.x, this.a, this.b, this.y);
	}

	public BigDecimal getA() {
		return a;
	}

	public BigDecimal getB() {
		return b;
	}

	public BigDecimal getX() {
		return x;
	}

	public BigDecimal getY() {
		return y;
	}

}
