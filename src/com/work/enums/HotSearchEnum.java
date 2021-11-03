package com.work.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 热搜数据源枚举
 *
 * @author lsy
 */
public enum HotSearchEnum {

    ZHIHU("知乎"),
    WEIBO("微博"),
    ;

    private String name;

    private static final Map<String, HotSearchEnum> NAME_ENUM_MAP = new HashMap<>();

    static {
        for (HotSearchEnum hotSearchEnum : HotSearchEnum.values()) {
            NAME_ENUM_MAP.put(hotSearchEnum.getName(), hotSearchEnum);
        }
    }

    HotSearchEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HotSearchEnum getByName(String name) {
        return NAME_ENUM_MAP.get(name);
    }
}
