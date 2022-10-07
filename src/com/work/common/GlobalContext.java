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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插件全局变量
 *
 * @author lsy
 */
public class GlobalContext {

    private static final Logger log = LoggerFactory.getLogger(GlobalContext.class);

    /** 正在执行id 集合 */
    private static volatile Map<Class, Set<String>> runIdMap = new HashMap<>();
    /** 正在执行id 执行参数 */
    private static volatile Map<String, Object> runIdParamMap = new HashMap<>();

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
            e.printStackTrace();
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
            e.printStackTrace();
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

    public static Object getRunIdParam(String runId) {
        try {
            if (readLock.tryLock(10, TimeUnit.SECONDS)) {
                if (runIdParamMap.containsKey(runId)) {
                    return runIdParamMap.get(runId);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            readLock.unlock();
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

    public static void setRunParam(String runId, Object runParam) {
        try {
            while (true) {
                if (writeLock.tryLock()) {
                    runIdParamMap.put(runId, runParam);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void removeRunInfo(Class clazz) {
        try {
            while (true) {
                if (writeLock.tryLock()) {
                    runIdMap.remove(clazz);
                    runIdParamMap.remove(clazz);
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
