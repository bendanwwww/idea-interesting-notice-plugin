package com.work.service;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
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
        while (needRun) {
            String runId = GlobalContext.getRunId();
            if (StringUtils.isEmpty(runId)) {
                continue;
            }
            String text = "";
            try {
                text = BasketBallService.getGameLiveText(runId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isNotEmpty(text)) {
                notify.createNotification(text, NotificationType.INFORMATION).notify(project);
            }
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
