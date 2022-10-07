package com.work.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intellij.openapi.project.Project;
import com.work.common.GlobalContext;

/**
 * 执行线程抽象类
 *
 * @author lsy
 */
public abstract class RunServiceAbstract implements RunService {

    private static final Logger log = LoggerFactory.getLogger(RunServiceAbstract.class);

    protected boolean isRun = false;

    public void run(Project project) {
        if (!isRun) {
            isRun = true;
            new Thread(() -> {
                log.info("======= {} is run =======", this.getClass().getSimpleName());
                runAction(project);
            }).start();
        }
    }

    public void stop() {
        isRun = false;
        GlobalContext.removeRunInfo(this.getClass());
        log.info("======= {} is stop =======", this.getClass().getSimpleName());
    }
}
