package com.work.action;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public abstract class ActionAbstract extends AnAction implements Action {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

    }
}
