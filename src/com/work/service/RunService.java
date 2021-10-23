package com.work.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.entity.BasketBallEntity;
import com.work.vo.BallActionVO;
import com.work.vo.GamesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行线程
 *
 * @author lsy <liushuoyang03@kuaishou.com>
 * Created on 2021-05-31
 */
public class RunService {

    private static final Logger log = LoggerFactory.getLogger(RunService.class);

    private static Map<String, BasketBallEntity> ballServiceMap;

    private static String runId;

    public static List<BallActionVO> init() {
        List<BallActionVO> res = new ArrayList<>();
        // 查询当前赛程链接
        List<GamesVO.Game> gameList = BasketBallService.getGameList();
        // 构造BasketBallService
        for (GamesVO.Game game : gameList) {
            if ("basketball".equals(game.getType())) {
                String title = game.getVisit_team() + " vs " + game.getHome_team();
                ballServiceMap.put(game.getId(), new BasketBallEntity(game.getId(), title, game.getUrl()));
                res.add(new BallActionVO(game.getId(), title));
            }
        }
        return res;
    }

    public static void run(String id) {
//        if (runId == id) {
//            return;
//        }
//        runId = id;
        // 查询ballServiceMap
        // 终止正在运行的线程
        // 执行新线程
    }
}
