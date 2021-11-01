package com.work.service;

import java.util.List;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.work.vo.GameLiveVO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行线程
 *
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-05-31
 */
public class RunService {

    private static final Logger log = LoggerFactory.getLogger(RunService.class);

    private volatile static boolean isRun = false;

    private volatile static boolean needRun = false;

    private static NotificationGroup notify =
            new NotificationGroup("game.notify", NotificationDisplayType.BALLOON, true);

    public synchronized static void run(Project project) {
        if (!isRun) {
            needRun = true;
            isRun = true;
            new Thread(() -> runAction(project)).start();
        }
    }

    public static void stop() {
        if (isRun) {
            needRun = false;
            isRun = false;
        }
    }

    public static void runAction(Project project) {
        String lastGameLiveId = null;
        while (needRun) {
            String runId = GlobalContext.getRunId();
            if (StringUtils.isEmpty(runId)) {
                continue;
            }
            StringBuffer textBuffer = new StringBuffer();
            try {
                String gameLiveId = BasketBallService.getGameLiveNumber(runId);
                if (StringUtils.isNotEmpty(gameLiveId) && !gameLiveId.equals(lastGameLiveId)) {
                    List<GameLiveVO> liveList = BasketBallService.getGameLiveText(runId, gameLiveId);
                    for (GameLiveVO gameLive : liveList) {
                        textBuffer.append("[");
                        textBuffer.append(gameLive.getPid_text());
                        textBuffer.append("]");
                        textBuffer.append(" ");
                        textBuffer.append(gameLive.getLive_text());
                        textBuffer.append(" ");
                        textBuffer.append(gameLive.getHome_score());
                        textBuffer.append(" - ");
                        textBuffer.append(gameLive.getVisit_score());
                        textBuffer.append("   ");
                        textBuffer.append("\r\n");
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
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
