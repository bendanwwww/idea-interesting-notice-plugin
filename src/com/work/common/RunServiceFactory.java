package com.work.common;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.work.result.ResultException;

/**
 * 执行线程实例工厂
 *
 * @author lsy
 */
public class RunServiceFactory {

    private static final Map<Class, Object> RUN_SERVICE_MAP = new HashMap<>();

    public static synchronized <T> T getByClass(Class<T> clazz) {
        if (RUN_SERVICE_MAP.containsKey(clazz)) {
            return (T) RUN_SERVICE_MAP.get(clazz);
        }
        return init(clazz);
    }

    private static <T> T init(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T t = constructor.newInstance();
            RUN_SERVICE_MAP.put(clazz, t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(e.getMessage());
        }
    }
}
