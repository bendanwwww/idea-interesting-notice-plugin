package com.work.vo;

/**
 * 篮球比赛执行参数
 *
 * @author lsy
 * @date 2022/10/07
 */
public class BallActionParam {

    /** 比赛 id */
    private String id;
    /** 主队名称 */
    private String homeTeam;
    /** 客队名称 */
    private String visitTeam;

    public BallActionParam() { }

    public BallActionParam(String id, String homeTeam, String visitTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.visitTeam = visitTeam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getVisitTeam() {
        return visitTeam;
    }

    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }
}