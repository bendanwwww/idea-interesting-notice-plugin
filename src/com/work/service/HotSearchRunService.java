package com.work.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.work.common.GlobalContext;
import com.work.enums.HotSearchEnum;
import com.work.external.HotSearchService;
import com.work.vo.WeiboHotSearchVO;
import com.work.vo.ZhihuHotSearchVO;

/**
 * 热搜执行线程
 *
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-05-31
 */
public class HotSearchRunService extends RunServiceAbstract {

    private static final Logger log = LoggerFactory.getLogger(HotSearchRunService.class);

    private static final int ACTION_SLEEP_TIME = 3 * 60 * 1000;

    private static final int TEXT_SLEEP_TIME = 5 * 1000;

    private static NotificationGroup notify =
            new NotificationGroup("hotSearch.notify", NotificationDisplayType.BALLOON, true);

    private Map<String, WeiboHotSearchVO.WeiboInfo> weiboHotSearchMap = new HashMap<>();
    private Map<String, ZhihuHotSearchVO.ZhihuInfo> zhihuHotSearchMap = new HashMap<>();

    private HotSearchRunService() {}

    @Override
    public void runAction(Project project) {
        while (isRun) {
            Set<String> runIds = GlobalContext.getRunIds(this.getClass());
            List<String> pushList = new ArrayList<>();
            for (String runId : runIds) {
                HotSearchEnum hotSearchEnum = HotSearchEnum.getByName(runId);
                switch (hotSearchEnum) {
                    case WEIBO:
                        List<WeiboHotSearchVO.WeiboInfo> weiboHotList = HotSearchService.getWeiboHotSearchList();
                        for (WeiboHotSearchVO.WeiboInfo info : weiboHotList) {
                            if (!weiboHotSearchMap.containsKey(info.getUrl())) {
                                String pushText = "标题: " + info.getName() + " 热度: " + info.getHot() + " 地址: " + info.getUrl();
                                pushList.add(pushText);
                                weiboHotSearchMap.put(info.getUrl(), info);

                            }
                        }
                        break;
                    case ZHIHU:
                        List<ZhihuHotSearchVO.ZhihuInfo> zhihuHotList = HotSearchService.getZhihuHotSearchList();
                        for (ZhihuHotSearchVO.ZhihuInfo info : zhihuHotList) {
                            if (!zhihuHotSearchMap.containsKey(info.getUrl())) {
                                String pushText = "标题: " + info.getQuery() + " 地址: " + info.getUrl();
                                pushList.add(pushText);
                                zhihuHotSearchMap.put(info.getUrl(), info);
                            }
                        }
                        break;
                }
            }
            for (String text : pushList) {
                notify.createNotification(text, NotificationType.INFORMATION).notify(project);
                try {
                    Thread.sleep(TEXT_SLEEP_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(ACTION_SLEEP_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
