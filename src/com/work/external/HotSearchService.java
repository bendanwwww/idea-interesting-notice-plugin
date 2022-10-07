package com.work.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.work.utils.ApacheHttpTool;
import com.work.vo.WeiboHotSearchVO;
import com.work.vo.ZhihuHotSearchVO;

/**
 * 热搜相关 service
 *
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-06-01
 */
public class HotSearchService {

    private static final String WEIBO_URL = "https://tenapi.cn/resou/";

    private static final String ZHIHU_URL = "https://tenapi.cn/zhihuresou/";

    public static List<WeiboHotSearchVO.WeiboInfo> getWeiboHotSearchList() {
        Map<String, String> header = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36");
        ApacheHttpTool.Result result = ApacheHttpTool.httpGet(WEIBO_URL, header, params, 3000, 10000);
        WeiboHotSearchVO data = JSON.parseObject(result.getBody(), new TypeReference<WeiboHotSearchVO>() { });
        if (Objects.isNull(data)) {
            return new ArrayList<>();
        }
        return data.getList();
    }

    public static List<ZhihuHotSearchVO.ZhihuInfo> getZhihuHotSearchList() {
        Map<String, String> header = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36");
        ApacheHttpTool.Result result = ApacheHttpTool.httpGet(ZHIHU_URL, header, params, 3000, 10000);
        ZhihuHotSearchVO data = JSON.parseObject(result.getBody(), new TypeReference<ZhihuHotSearchVO>() { });
        if (Objects.isNull(data)) {
            return new ArrayList<>();
        }
        return data.getList();
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(getWeiboHotSearchList()));
        System.out.println(JSONObject.toJSONString(getZhihuHotSearchList()));
    }
}
