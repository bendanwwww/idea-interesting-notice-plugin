package com.work.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    /** 正在执行id 集合 */
    private static volatile Map<Class, Set<String>> runIdMap = new HashMap<>();

    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();

    private GlobalContext() { }

    public static Set<String> getRunIds(Class clazz) {
        try {
            if (readLock.tryLock(10, TimeUnit.SECONDS)) {
                if (runIdMap.containsKey(clazz)) {
                    return runIdMap.get(clazz);
                }
            }
            return new HashSet<>();
        } catch (Exception e) {
            return new HashSet<>();
        } finally {
            readLock.unlock();
        }
    }

    public static String getRunId(Class clazz) {
        try {
            if (readLock.tryLock(10, TimeUnit.SECONDS)) {
                if (runIdMap.containsKey(clazz)) {
                    return String.valueOf(runIdMap.get(clazz).toArray()[0]);
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
                    runIdMap.put(clazz, new HashSet(Arrays.asList(runId)));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void setRunId(Class clazz, String runId) {
        try {
            while (true) {
                if (writeLock.tryLock()) {
                    if (runIdMap.containsKey(clazz)) {
                        Set<String> mapSet = new HashSet<>();
                        mapSet.addAll(runIdMap.get(clazz));
                        mapSet.add(runId);
                        runIdMap.put(clazz, mapSet);
                    } else {
                        runIdMap.put(clazz, new HashSet<>(Arrays.asList(runId)));
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

}
