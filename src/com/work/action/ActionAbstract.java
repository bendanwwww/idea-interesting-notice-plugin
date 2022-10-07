package com.work.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;

public abstract class ActionAbstract extends AnAction implements Action {

    /** 点击函数执行开始前执行 */
    public void begin(Project project) { }

    /** 点击函数执行结束后执行 */
    public void after(Project project) { }

}
