package com.work.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.work.common.GlobalContext;
import com.work.external.BasketBallService;
import com.work.vo.GameLiveVO;

/**
 * 比赛执行线程
 *
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-05-31
 */
public class GameRunService extends RunServiceAbstract {

    private static final Logger log = LoggerFactory.getLogger(GameRunService.class);

    private static final int ACTION_SLEEP_TIME = 5000;

    private static NotificationGroup notify =
            new NotificationGroup("game.notify", NotificationDisplayType.BALLOON, true);

    private GameRunService() {}

    @Override
    public void runAction(Project project) {
        String lastGameLiveId = null;
        while (isRun) {
            String runId = GlobalContext.getRunId();
            if (StringUtils.isEmpty(runId)) {
                continue;
            }
            StringBuffer textBuffer = new StringBuffer();
            try {
                String gameLiveId = BasketBallService.getGameLiveNumber(runId);
                if (StringUtils.isNotEmpty(gameLiveId) && !gameLiveId.equals(lastGameLiveId)) {
                    List<GameLiveVO> liveList = BasketBallService.getGameLiveText(runId, gameLiveId);
                    if (CollectionUtils.isNotEmpty(liveList)) {
                        textBuffer.append("[");
                        textBuffer.append(liveList.get(0).getPid_text());
                        textBuffer.append("]");
                        textBuffer.append(" ");
                        for (GameLiveVO gameLive : liveList) {
                            textBuffer.append(gameLive.getLive_text());
                            textBuffer.append(" ");
                        }
                        textBuffer.append(liveList.get(0).getHome_score());
                        textBuffer.append(" - ");
                        textBuffer.append(liveList.get(0).getVisit_score());
                    }
                    lastGameLiveId = gameLiveId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String text = textBuffer.toString();
            if (StringUtils.isNotEmpty(text)) {
                notify.createNotification(text, NotificationType.INFORMATION).notify(project);
            }
            try {
                Thread.sleep(ACTION_SLEEP_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
