package com.work.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;

/**
 * 列表插件抽象类
 *
 * @author lsy
 */
public abstract class ListPopupActionAbstract extends ActionAbstract {

    /** 列表集合 key: 列表名称 value: 跳转参数 */
    public static final Map<String, Object> POPUP_MAP = new LinkedHashMap<>();

    /** 列表标题 */
    public abstract String getTitle();
    /** 获取列表数据 */
    public abstract LinkedHashMap<String, Object> getPopupList();
    /** 点击事件 */
    public abstract void choseAction(String selectedValue);

    /**
     * 点击动作执行函数
     * @param event
     */
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
