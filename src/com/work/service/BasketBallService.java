package com.work.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.intellij.openapi.components.Service;
import com.work.utils.ApacheHttpTool;
import com.work.vo.GameLiveVO;
import com.work.vo.GamesVO;

/**
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-06-01
 */
public class BasketBallService {

    private static final String GAME_WEB_URL = "https://www.zhibo8.cc/";

    private static final String GAME_LIST_URL = "https://bifen4pc.qiumibao.com/json/list.htm";

    private static final String GAME_LIVE_NUMBER_URL = "https://dingshi4pc.qiumibao.com/livetext/data/cache/max_sid/{0}/0.htm";

    private static final String GAME_LIVE_TEXT_URL = "https://dingshi4pc.qiumibao.com/livetext/data/cache/livetext/{0}/0/lit_page_2/{1}.htm";


    public static List<GamesVO.Game> getGameList() {
        Map<String, String> header = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        ApacheHttpTool.Result result = ApacheHttpTool.httpGet(GAME_LIST_URL, header, params);
        GamesVO resultObj = JSON.parseObject(result.getBody(), new TypeReference<GamesVO>() {});
        return resultObj.getList();
    }

    public static String getGameLiveNumber(String id) {
        // 获取当前比赛直播id
        String liveUrl = MessageFormat.format(GAME_LIVE_NUMBER_URL, id);
        ApacheHttpTool.Result liveResult = ApacheHttpTool.httpGet(liveUrl, new HashMap<>(), new HashMap<>());
        if (!liveResult.isOk()) {
            return null;
        }
        return liveResult.getBody();
    }

    public static List<GameLiveVO> getGameLiveText(String id, String gameLiveId) {
        // 获取比赛直播文案
        String liveTextUrl = MessageFormat.format(GAME_LIVE_TEXT_URL, id, gameLiveId);
        Map<String, String> header = new HashMap<>();
        header.put("authority", "dingshi4pc.qiumibao.com");
        header.put("sec-ch-ua", "\"Google Chrome\";v=\"95\", \"Chromium\";v=\"95\", \";Not A Brand\";v=\"99\"");
        header.put("accept", "application/json, text/javascript, */*; q=0.01");
        header.put("sec-ch-ua-mobile", "?0");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36");
        header.put("sec-ch-ua-platform", "\"macOS\"");
        header.put("origin", "https://www.zhibo8.cc");
        header.put("sec-fetch-site", "cross-site");
        header.put("sec-fetch-mode", "cors");
        header.put("sec-fetch-dest", "empty");
        header.put("referer", "https://www.zhibo8.cc/");
        header.put("accept-language", "zh-CN,zh;q=0.9");
        ApacheHttpTool.Result liveTextResult = ApacheHttpTool.httpGet(liveTextUrl, header, new HashMap<>());
        if (!liveTextResult.isOk()) {
            return null;
        }
        return JSON.parseObject(liveTextResult.getBody(), new TypeReference<List<GameLiveVO>>() {});
    }

    public static void main(String[] args) {

        System.out.println(JSONObject.toJSONString(getGameList()));

        String testUrl = "https://dingshi4pc.qiumibao.com/livetext/data/cache/max_sid/776192/0.htm?time=0.5625657025442139";
        Map<String, String> header = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        ApacheHttpTool.Result result = ApacheHttpTool.httpGet(testUrl, header, params);
        System.out.println(JSONObject.toJSON(result.getBody()));

        String testUrl2 = "https://dingshi4pc.qiumibao.com/livetext/data/cache/livetext/776192/0/lit_page_2/%s.htm?get=0.6459999979004349";
        header.put("authority", "dingshi4pc.qiumibao.com");
        header.put("sec-ch-ua", "\"Google Chrome\";v=\"95\", \"Chromium\";v=\"95\", \";Not A Brand\";v=\"99\"");
        header.put("accept", "application/json, text/javascript, */*; q=0.01");
        header.put("sec-ch-ua-mobile", "?0");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36");
        header.put("sec-ch-ua-platform", "\"macOS\"");
        header.put("origin", "https://www.zhibo8.cc");
        header.put("sec-fetch-site", "cross-site");
        header.put("sec-fetch-mode", "cors");
        header.put("sec-fetch-dest", "empty");
        header.put("referer", "https://www.zhibo8.cc/");
        header.put("accept-language", "zh-CN,zh;q=0.9");
        System.out.println(String.format(testUrl2, result.getBody()));
        ApacheHttpTool.Result result2 = ApacheHttpTool.httpGet(String.format(testUrl2, result.getBody()), header, params);
        System.out.println(JSONObject.toJSON(result2));
    }
}
