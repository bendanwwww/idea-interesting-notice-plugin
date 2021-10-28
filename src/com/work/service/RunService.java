package com.work.service;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;
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

    private static NotificationGroup notificationGroup = new NotificationGroup(
            "testid", NotificationDisplayType.BALLOON, false);

    private volatile static boolean isRun = false;

    public synchronized static void run() {
        if (!isRun) {
            new Thread(RunService :: runAction).run();
            isRun = true;
        }
    }

    private static void runAction() {
        while (true) {
            String runId = GlobalContext.getRunId();
            if (StringUtils.isEmpty(runId)) {
                try {
                    Thread.sleep(1000);
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String text = BasketBallService.getGameLiveText(GlobalContext.getUrlByRunId(runId));
            Notification notification = notificationGroup.createNotification(text, MessageType.INFO);
            Notifications.Bus.notify(notification);
        }
    }
}
