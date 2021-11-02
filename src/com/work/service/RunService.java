package com.work.service;

import com.intellij.openapi.project.Project;

/**
 * 执行线程接口
 *
 * @author lsy
 */
public interface RunService {

    void run(Project project);

    void stop();

    void runAction(Project project);
}
