package com.work.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.work.common.GlobalContext;
import com.work.common.RunServiceFactory;
import com.work.external.BasketBallService;
import com.work.service.GameRunService;
import com.work.vo.BallActionParam;
import com.work.vo.GamesVO;

/**
 * 篮球比赛action
 *
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-05-27
 */
public class BasketballAction extends ListPopupActionAbstract {

    private static final Logger log = Logger.getInstance(BasketballAction.class);

    private static final String POPUP_TITLE = "请选择一场比赛";
    private static final String NO_GAME_TITLE = "当前无比赛";
    private static final String CLOSE_TASK = "停止播放";

    private static GameRunService gameRunService = RunServiceFactory.getByClass(GameRunService.class);

    @Override
    public void begin(Project project) {
        gameRunService.run(project);
    }

    @Override
    public String getTitle() {
        return POPUP_TITLE;
    }

    @Override
    public LinkedHashMap<String, Object> getPopupList() {
        LinkedHashMap<String, Object> resMap = new LinkedHashMap<>();
        // 获取本日比赛
        List<GamesVO.Game> gameList = BasketBallService.getGameList();
        if (CollectionUtils.isNotEmpty(gameList)) {
            // 取出篮球且存在直播的比赛
            gameList = gameList.stream()
                    .filter(g -> (g.getFrom().equals("live.dc") || g.getFrom().equals("live.live") || g.getFrom().equals("dc.live"))
                            && g.getType().equals("basketball"))
                    .collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(gameList)) {
            // 生成列表
            for (GamesVO.Game g : gameList) {
                String title = g.getHome_team() + "vs" + g.getVisit_team();
                resMap.put(title, new BallActionParam(g.getId(), g.getHome_team(), g.getVisit_team()));
            }
        } else {
            resMap.put(NO_GAME_TITLE, new BallActionParam());
        }
        resMap.put(CLOSE_TASK, new BallActionParam());
        return resMap;
    }

    @Override
    public void choseAction(String selectedValue) {
        // 关闭线程
        if (selectedValue.equals(CLOSE_TASK)) {
            gameRunService.stop();
            return;
        }
        // 获取跳转参数
        BallActionParam params = (BallActionParam) POPUP_MAP.get(selectedValue);
        if (Objects.nonNull(params)) {
            // 插入全局上下文中
            GlobalContext.resetRunId(GameRunService.class, params.getId());
            GlobalContext.setRunParam(params.getId(), params);
        }
    }
}
