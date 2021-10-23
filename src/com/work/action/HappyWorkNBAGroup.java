package com.work.action;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.popup.*;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.work.service.BasketBallService;
import com.work.vo.GamesVO;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.util.ArrayList;
import java.util.List;
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
        // 获取本日比赛
        List<GamesVO.Game> gameList = BasketBallService.getGameList();
        List<String> gameTitle = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(gameList)) {
            gameTitle = gameList.stream()
                    .map(g -> g.getHome_team() + "vs" + g.getVisit_team())
                    .collect(Collectors.toList());
            gameTitle.add(CLOSE_TASK);
        } else {
            gameTitle.add(NO_GAME_TITLE);
        }
        // 初始化菜单
        ListPopup listPopup = JBPopupFactory.getInstance()
                .createListPopup(new BaseListPopupStep<String>(POPUP_TITLE, gameTitle) {
            @Override
            public PopupStep onChosen(String selectedValue, boolean finalChoice) {
                log.info(" ===== "+ selectedValue);
                return super.onChosen(selectedValue, finalChoice);
            }
        });
        // 展示
        listPopup.showInFocusCenter();
    }
}
