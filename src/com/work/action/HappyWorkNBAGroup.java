package com.work.action;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.popup.*;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.work.service.BasketBallService;
import com.work.service.GlobalContext;
import com.work.service.RunService;
import com.work.vo.GamesVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自动生成子菜单
 *
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-05-27
 */
public class HappyWorkNBAGroup extends AnAction {

    private static final String POPUP_TITLE = "请选择一场比赛";
    private static final String NO_GAME_TITLE = "当前无比赛";
    private static final String CLOSE_TASK = "停止播放";

    private static final Logger log = Logger.getInstance(HappyWorkNBAGroup.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 启动线程
        RunService.run(event.getProject());
        // 获取本日比赛
        List<GamesVO.Game> gameList = BasketBallService.getGameList();
        if (CollectionUtils.isNotEmpty(gameList)) {
            // 取出篮球且存在直播的比赛
            gameList = gameList.stream()
                    .filter(g -> (g.getFrom().equals("live.dc") || g.getFrom().equals("live.live") || g.getFrom().equals("dc.live"))
                            && g.getType().equals("basketball"))
                    .collect(Collectors.toList());
        }
        Map<String, String> gameTitleMap = new HashMap<>();
        List<String> gameTitle = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(gameList)) {
            // 插入全局上下文中
            Map<String, String> runUrlMap = gameList.stream()
                    .collect(Collectors.toMap(GamesVO.Game :: getId, GamesVO.Game :: getUrl));
            GlobalContext.setAllUrl(runUrlMap);
            // 生成列表
            Map<String, String> gameTitleMapTmp = new HashMap<>();
            for (GamesVO.Game g : gameList) {
                String title = g.getHome_team() + "vs" + g.getVisit_team();
                gameTitleMapTmp.put(title, g.getId());
            }
            gameTitleMap.putAll(gameTitleMapTmp);
            gameTitle.addAll(gameTitleMap.keySet());
        } else {
            gameTitle.add(NO_GAME_TITLE);
        }
        gameTitle.add(CLOSE_TASK);
        // 初始化菜单
        ListPopup listPopup = JBPopupFactory.getInstance()
                .createListPopup(new BaseListPopupStep<String>(POPUP_TITLE, gameTitle) {
            @Override
            public PopupStep onChosen(String selectedValue, boolean finalChoice) {
                // 关闭线程
                if (selectedValue.equals(CLOSE_TASK)) {
                    RunService.stop();
                }
                // 获取内容id
                String id = gameTitleMap.get(selectedValue);
                if (StringUtils.isNotEmpty(id)) {
                    // 插入全局上下文中
                    GlobalContext.setRunId(id);
                }
                return super.onChosen(selectedValue, finalChoice);
            }
        });
        // 展示
        listPopup.showInFocusCenter();
    }
}
