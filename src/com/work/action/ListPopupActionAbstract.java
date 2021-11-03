package com.work.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;

public abstract class ListPopupActionAbstract extends ActionAbstract {

    public static final Map<String, Object> POPUP_MAP = new LinkedHashMap<>();

    public abstract String getTitle();

    public abstract LinkedHashMap<String, Object> getPopupList();

    public abstract void choseAction(String selectedValue);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 刷新列表
        refushPopupMap();
        Project project = event.getProject();
        begin(project);
        // 初始化菜单
        ListPopup listPopup = JBPopupFactory.getInstance()
                .createListPopup(new BaseListPopupStep<String>(getTitle(), new ArrayList<>(POPUP_MAP.keySet())) {
                    @Override
                    public PopupStep onChosen(String selectedValue, boolean finalChoice) {
                        choseAction(selectedValue);
                        return super.onChosen(selectedValue, finalChoice);
                    }
                });
        // 展示
        listPopup.showInFocusCenter();
        after(project);
    }

    private synchronized void refushPopupMap() {
        POPUP_MAP.clear();
        POPUP_MAP.putAll(getPopupList());
    }

}
