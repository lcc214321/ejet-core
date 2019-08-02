package com.ejet.core.kernel.web.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 获取UUID
 */
public abstract class UuidUtil {

    private static boolean IS_THREADLOCALRANDOM_AVAILABLE = false;
    private static Random random;
    private static final long leastSigBits;
    private static final ReentrantLock lock = new ReentrantLock();
    private static AtomicLong seq = new AtomicLong(100000);
    private static long lastTime;
    static {
        try {
            IS_THREADLOCALRANDOM_AVAILABLE = null != UuidUtil.class.getClassLoader().loadClass(
                    "java.util.concurrent.ThreadLocalRandom"
            );
        } catch(ClassNotFoundException e) {
        }
        byte[] seed = new SecureRandom().generateSeed(8);
        leastSigBits = new BigInteger(seed).longValue();
        if(!IS_THREADLOCALRANDOM_AVAILABLE) {
            random = new Random(leastSigBits);
        }
    }
	/**
	 * 获取32位UUID(永不重复)
	 * @return String
	 */
	 public static String getUUID32() {
		 String ss= UUID.randomUUID().toString();
		 ss= ss.replaceAll("-", "");
		 return ss;
	 }
	 /**
	  * 获取8位UUID(可能重复)
	  * @return String
	  */
	 public static String getUUID8() {
		 String ss= getUUID32();
		 return ss.substring(ss.length()-8, ss.length());
	 }

    /**
     * 获取64位UUID,包含日期(永不重复)
     *
     * @return String
     */
    public static String getUUID() {
        lock.lock();
        String uuid = null;
        try {
            String num = seq.incrementAndGet()+"";
            int length = num.length();
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            uuid = uuid + num.substring(length<=6 ? 0 : length-6, length);
        } finally {
            lock.unlock();
        }
        return uuid;
    }


    public static String getUUIDNumer() {
        lock.lock();
        String uuid = null;
        try {
            String num = seq.incrementAndGet()+"";
            int length = num.length();
            uuid = create().toString().replaceAll("-", "");
            uuid = uuid + num.substring(length<=6 ? 0 : length-6, length);
        } finally {
            lock.unlock();
        }
        return uuid;
    }


    public static UUID create() {
        long timeMillis = (System.currentTimeMillis() * 10000) + 0x01B21DD213814000L;
        lock.lock();
        try {
            if(timeMillis > lastTime) {
                lastTime = timeMillis;
            } else {
                timeMillis = ++lastTime;
            }
        } finally {
            lock.unlock();
        }
        // time low
        long mostSigBits = timeMillis << 32;
        // time mid
        mostSigBits |= (timeMillis & 0xFFFF00000000L) >> 16;
        // time hi and version
        mostSigBits |= 0x1000 | ((timeMillis >> 48) & 0x0FFF); // version 1
        return new UUID(mostSigBits, leastSigBits);
    }

    public static UUID random() {
        byte[] randomBytes = new byte[16];
        if(IS_THREADLOCALRANDOM_AVAILABLE) {
            java.util.concurrent.ThreadLocalRandom.current().nextBytes(randomBytes);
        } else {
            random.nextBytes(randomBytes);
        }

        long mostSigBits = 0;
        for(int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
        }
        long leastSigBits = 0;
        for(int i = 8; i < 16; i++) {
            leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
        }
        return new UUID(mostSigBits, leastSigBits);
    }

}
