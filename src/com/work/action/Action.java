package com.work.action;

import com.intellij.openapi.project.Project;

public interface Action {

    void begin(Project project);

    void after(Project project);
}
