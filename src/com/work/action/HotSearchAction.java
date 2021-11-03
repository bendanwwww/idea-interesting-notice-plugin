package com.work.action;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.intellij.openapi.project.Project;
import com.work.common.GlobalContext;
import com.work.common.RunServiceFactory;
import com.work.enums.HotSearchEnum;
import com.work.service.GameRunService;
import com.work.service.HotSearchRunService;

/**
 * 热搜榜单action
 *
 * @author lsy
 */
public class HotSearchAction extends ListPopupActionAbstract {

    private static final String POPUP_TITLE = "请选择数据源";
    private static final String CLOSE_TASK = "停止播放";

    private static HotSearchRunService hotSearchRunService = RunServiceFactory.getByClass(HotSearchRunService.class);

    @Override
    public void begin(Project project) {
        hotSearchRunService.run(project);
    }

    @Override
    public String getTitle() {
        return POPUP_TITLE;
    }

    @Override
    public LinkedHashMap<String, Object> getPopupList() {
        LinkedHashMap<String, Object> resMap = new LinkedHashMap<>();
        for (HotSearchEnum hotSearchEnum : HotSearchEnum.values()) {
            resMap.put(hotSearchEnum.getName(), "");
        }
        resMap.put(CLOSE_TASK, "");
        return resMap;
    }

    @Override
    public void choseAction(String selectedValue) {
        // 关闭线程
        if (selectedValue.equals(CLOSE_TASK)) {
            hotSearchRunService.stop();
            return;
        }
        // 插入全局上下文中
        GlobalContext.setRunId(HotSearchRunService.class, selectedValue);
    }
}
