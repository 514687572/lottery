package com.lottery.net.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主键生成
 * 
 * @author cja
 * 
 */
public class GrnerateUUID {

	public static synchronized String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private static AtomicInteger counter = new AtomicInteger(0);

	/**
	 * 生成十位纯数字ID
	 * 
	 * @return
	 */
	public static long getAtomicCounter() {
		if (counter.get() > 999999) {
			counter.set(1);
		}
		long time = Long.parseLong((new Date().getTime() + "").subSequence(5, 13).toString());
		long returnValue = time * 100 + counter.incrementAndGet();
		return returnValue;
	}

	/**
	 * 生成8位纯数字ID
	 * 
	 * @return
	 */
	public static long getVlidateCode() {
		if (counter.get() > 999999) {
			counter.set(1);
		}
		long time = Long.parseLong((new Date().getTime() + "").subSequence(5, 13).toString());
		long returnValue = time + counter.incrementAndGet();
		return returnValue;
	}

	public static int getSingerNum() {
		Random rad = new Random();
		return rad.nextInt(10000);
	}

	/**
	 * 生成6位纯数字ID
	 * 
	 * @return
	 */
	public static long getKaraokeAppoiCode() {
		if (counter.get() > 999999) {
			counter.set(1);
		}
		long time = Long.parseLong((new Date().getTime() + "").subSequence(7, 13).toString());
		long returnValue = time + counter.incrementAndGet();
		return returnValue;
	}

	private static long incrementAndGet() {
		return counter.incrementAndGet();
	}

	public static void main(String[] args) {
		System.out.println(TimeUtils.dateToString(new Date(),"yyyyMMddHHmmss")+GrnerateUUID.getAtomicCounter());
	}
}
