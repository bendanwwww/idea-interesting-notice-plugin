package com.work.vo;

import java.util.List;

/**
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-06-01
 */
@SuppressWarnings({"MethodName", "MemberName", "ParameterName"})
public class GamesVO {

    private String code;
    private String second;
    private List<Game> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public List<Game> getList() {
        return list;
    }

    public void setList(List<Game> list) {
        this.list = list;
    }

    public static class Game {
        private String id;
        private String sdate;
        private String time;
        private String url;
        private String type;
        private String start;
        private String home_team;
        private String visit_team;
        private String home_score;
        private String visit_score;
        private String period_cn;
        private String from;
        private String code;
        private String update;
        private String big_score_1;
        private String big_score_2;
        private String[] header;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSdate() {
            return sdate;
        }

        public void setSdate(String sdate) {
            this.sdate = sdate;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getHome_team() {
            return home_team;
        }

        public void setHome_team(String home_team) {
            this.home_team = home_team;
        }

        public String getVisit_team() {
            return visit_team;
        }

        public void setVisit_team(String visit_team) {
            this.visit_team = visit_team;
        }

        public String getHome_score() {
            return home_score;
        }

        public void setHome_score(String home_score) {
            this.home_score = home_score;
        }

        public String getVisit_score() {
            return visit_score;
        }

        public void setVisit_score(String visit_score) {
            this.visit_score = visit_score;
        }

        public String getPeriod_cn() {
            return period_cn;
        }

        public void setPeriod_cn(String period_cn) {
            this.period_cn = period_cn;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getBig_score_1() {
            return big_score_1;
        }

        public void setBig_score_1(String big_score_1) {
            this.big_score_1 = big_score_1;
        }

        public String getBig_score_2() {
            return big_score_2;
        }

        public void setBig_score_2(String big_score_2) {
            this.big_score_2 = big_score_2;
        }

        public String[] getHeader() {
            return header;
        }

        public void setHeader(String[] header) {
            this.header = header;
        }

//        @Override
//        public boolean equals(Object o) {
//            if (!(o instanceof Game)) {
//                return false;
//            }
//            Game equalsGame = (Game) o;
//            if (this.id.equals(equalsGame.getId())
//                    || (this.home_team.equals(equalsGame.getHome_team()) && this.visit_team.equals(equalsGame.getVisit_team()))) {
//                return true;
//            }
//            return super.equals(o);
//        }
//
//        @Override
//        public int hashCode() {
//            Game hashGame = new Game();
//            hashGame.setId(this.id);
//            hashGame.setHome_team(this.home_team);
//            hashGame.setVisit_team(this.visit_team);
//            return hashGame.hashCode();
//        }
    }
}
