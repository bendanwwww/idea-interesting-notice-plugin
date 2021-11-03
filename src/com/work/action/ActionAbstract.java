package com.work.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;

public abstract class ActionAbstract extends AnAction implements Action {

    public void begin(Project project) {}

    public void after(Project project) {}

}
