package com.work.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.intellij.openapi.components.Service;
import com.work.utils.ApacheHttpTool;
import com.work.vo.GamesVO;

/**
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-06-01
 */
public class BasketBallService {

    private static final String GAME_LIST_URL = "https://bifen4pc.qiumibao.com/json/list.htm";

    public static List<GamesVO.Game> getGameList() {
        Map<String, String> header = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        ApacheHttpTool.Result result = ApacheHttpTool.httpGet(GAME_LIST_URL, header, params);
        GamesVO resultObj = JSON.parseObject(result.getBody(), new TypeReference<GamesVO>() {});
        return resultObj.getList();
    }

    public static String getGameLiveText(String url) {
        return url;
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSON(getGameList()));
    }
}
