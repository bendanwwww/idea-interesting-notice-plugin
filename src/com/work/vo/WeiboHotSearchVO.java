package com.work.vo;

import java.util.List;

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
        private long hot;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getHot() {
            return hot;
        }

        public void setHot(long hot) {
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
