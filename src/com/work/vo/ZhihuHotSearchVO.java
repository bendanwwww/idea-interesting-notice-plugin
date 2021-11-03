package com.work.vo;

import java.util.List;

/**
 * 知乎热搜实体
 *
 * @author lsy
 */
public class ZhihuHotSearchVO {

    private int data;
    private List<ZhihuInfo> list;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public List<ZhihuInfo> getList() {
        return list;
    }

    public void setList(List<ZhihuInfo> list) {
        this.list = list;
    }

    public static class ZhihuInfo {
        private String name;
        private String query;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
