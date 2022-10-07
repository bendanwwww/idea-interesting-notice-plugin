package com.work.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 热搜数据源枚举
 *
 * @author lsy
 */
public enum HotSearchEnum {

    ZHIHU("知乎", "<p>{0}</p><br />\r\n<a href=\"{1}\">{1}</a>"),
    WEIBO("微博", "{0}&nbsp;{1}\uD83D\uDD25<br />\r\n<a href=\"{2}\">{2}</a>"),
    ;

    private String name;
    private String showFormat;

    private static final Map<String, HotSearchEnum> NAME_ENUM_MAP = new HashMap<>();

    static {
        for (HotSearchEnum hotSearchEnum : HotSearchEnum.values()) {
            NAME_ENUM_MAP.put(hotSearchEnum.getName(), hotSearchEnum);
        }
    }

    HotSearchEnum(String name, String showFormat) {
        this.name = name;
        this.showFormat = showFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowFormat() {
        return showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public static HotSearchEnum getByName(String name) {
        return NAME_ENUM_MAP.get(name);
    }
}
