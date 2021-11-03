package com.work.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private static volatile Map<Class, List<String>> runIdMap;

    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();

    private GlobalContext() { }

    public static List<String> getRunIds(Class clazz) {
        try {
            if (readLock.tryLock(10, TimeUnit.SECONDS)) {
                return runIdMap.get(clazz);
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public static String getRunId(Class clazz) {
        try {
            if (readLock.tryLock(10, TimeUnit.SECONDS)) {
                if (runIdMap.containsKey(clazz)) {
                    return runIdMap.get(clazz).get(0);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public static void resetRunId(Class clazz, String runId) {
        try {
            while (true) {
                if (writeLock.tryLock()) {
                    runIdMap.put(clazz, Arrays.asList(runId));
                    break;
                }
            }
        } catch (Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

    public static void setRunId(Class clazz, String runId) {
        try {
            while (true) {
                if (writeLock.tryLock()) {
                    if (runIdMap.containsKey(clazz)) {
                        runIdMap.get(clazz).add(runId);
                    } else {
                        runIdMap.put(clazz, Arrays.asList(runId));
                    }
                    break;
                }
            }
        } catch (Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

    public static void removeRunId(Class clazz) {
        try {
            while (true) {
                if (writeLock.tryLock()) {
                    runIdMap.remove(clazz);
                    break;
                }
            }
        } catch (Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

}
