package com.work.service;

import com.intellij.openapi.project.Project;
import com.work.common.GlobalContext;

/**
 * 执行线程抽象类
 *
 * @author lsy
 */
public abstract class RunServiceAbstract implements RunService {

    boolean isRun = false;

    public void run(Project project) {
        if (!isRun) {
            isRun = true;
            new Thread(() -> runAction(project)).start();
        }
    }

    public void stop() {
        isRun = false;
        GlobalContext.removeRunId();
    }

}
