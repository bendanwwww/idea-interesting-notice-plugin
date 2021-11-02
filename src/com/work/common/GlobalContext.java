package com.work.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 插件全局变量
 *
 * @author lsy
 */
public class GlobalContext {

    /** 执行id */
    private static volatile String runId;

    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();

    private GlobalContext() { }

    public static String getRunId() {
        try {
            if (readLock.tryLock(10, TimeUnit.SECONDS)) {
                return GlobalContext.runId;
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public static void setRunId(String runId) {
        try {
            while (true) {
                if (writeLock.tryLock()) {
                    GlobalContext.runId = runId;
                    break;
                }
            }
        } catch (Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

}
