package com.example.lotterysystem;

import com.example.lotterysystem.controller.param.DrawPrizeParam;
import com.example.lotterysystem.service.DrawPrizeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class DrawPrizeTest {
    @Autowired
    private DrawPrizeService drawPrizeService;
    @Test
    void drawPrize(){
        DrawPrizeParam param = new DrawPrizeParam();
        param.setActivityId(1L);
        param.setPrizeId(1L);
        param.setPrizeTiers("FIRST_PRIZE");
        param.setWinningTime(new Date());
        List<DrawPrizeParam.winner> winnerList =new ArrayList<>();
        DrawPrizeParam.winner winner = new DrawPrizeParam.winner();
        winner.setUserId(1L);
        winner.setUserName("XXX");
        winnerList.add(winner);

        param.setWinnerList(winnerList);


        drawPrizeService.drawPrize(param);
    }
}
