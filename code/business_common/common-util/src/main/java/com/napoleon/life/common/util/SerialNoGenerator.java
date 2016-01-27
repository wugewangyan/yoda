package com.napoleon.life.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列号生成器
 * @author wuge
 *
 */
public class SerialNoGenerator {

    protected static final Logger logger = LoggerFactory.getLogger(SerialNoGenerator.class);

    private long workerId;
    private long sequence = 0L;

    private static long twepoch = 1435725920677l;

    private static long workerIdBits = 10L;
    private static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static long sequenceBits = 12L;

    private static long workerIdShift = sequenceBits;
    private static long timestampLeftShift = sequenceBits + workerIdBits;
    private static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public SerialNoGenerator(long workId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workId;
        logger.info(String.format("worker starting. timestamp left shift %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, workerIdBits, sequenceBits, workerId));
    }

    public synchronized long nextNo() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            logger.warn(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        else {
            sequence = 0L;
        }
 
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}