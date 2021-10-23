package com.work.vo;

/**
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-05-31
 */
public class BallActionVO {

    private String id;
    private String text;

    public BallActionVO(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
