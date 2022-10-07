package com.work.vo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 微博热搜实体
 *
 * @author lsy
 */
public class WeiboHotSearchVO {

    private int data;
    private List<WeiboInfo> list;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public List<WeiboInfo> getList() {
        return list;
    }

    public void setList(List<WeiboInfo> list) {
        this.list = list;
    }

    public static class WeiboInfo {
        private String name;
        private String hotType;
        private String hot;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHotType() {
            if (StringUtils.isBlank(hot)) {
                return null;
            }
            String[] hots = hot.split(" ");
            if (hots.length == 1) {
                return null;
            }
            return hots[0];
        }

        public long getHot() {
            if (StringUtils.isBlank(hot)) {
                return 0L;
            }
            String[] hots = hot.split(" ");
            if (hots.length == 1) {
                return Long.valueOf(hot);
            }
            return Long.valueOf(hots[1]);
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
