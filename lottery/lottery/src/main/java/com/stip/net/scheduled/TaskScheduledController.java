package com.stip.net.scheduled;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.lottery.net.utils.Constants;
import com.stip.net.entity.LotteryConfirm;
import com.stip.net.entity.LotteryExc;
import com.stip.net.entity.LotteryRecords;
import com.stip.net.entity.UserBetRecords;
import com.stip.net.example.LotteryExcExample;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.service.AccountService;
import com.stip.net.service.ConfirmService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryExcService;
import com.stip.net.service.LotteryService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.UserBetScoreRecordsService;
import com.stip.net.service.UserBetService;
import com.stip.net.service.UserTransactionScoreService;

@Component
@Lazy(false)
public class TaskScheduledController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConfirmService confirmService;
    @Autowired
    private IMWebSocketHandler handler;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private UserBetService userBetService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private LotteryUserService lotteryUserService;
    @Autowired
    private GameService gameService;
    @Autowired
    private LotteryExcService lotteryExcService;
    @Autowired
	private UserTransactionScoreService userTransactionScoreRecordsService;
    @Autowired
    private UserBetScoreRecordsService userBetScoreService;

    private boolean isExc=false;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateUserBetRecords() throws Exception {
        logger.info("执行补发奖操作");
        List<LotteryConfirm> list = confirmService.selectByExample();

        if (list == null) {
            return;
        }

        for (LotteryConfirm confirm : list) {
            List<UserBetRecords> records = userBetService.getUserBetByRecordId(confirm.getConfirmId());

            if (records != null) {
                UserBetRecords currentRecord = records.get(0);
                LotteryRecords lotteryRecord = lotteryService.getLotteryById(currentRecord.getBetNum());

                threadPoolTaskExecutor.execute(new Runnable() {
                    public void run() {
                        PublicMethod.senPrizeMsg(lotteryRecord, new Date(), userBetService, userBetScoreService , accountService, logger,lotteryUserService, handler, gameService,userTransactionScoreRecordsService);
                        // 新用户奖励
                        PublicMethod.newUser(currentRecord.getUserName(), lotteryUserService, accountService, logger, null,
                                currentRecord.getBetNum(), Constants.GAME_TYPE_LOTTERY, handler, gameService);
                        //  邀请奖励发放
                        BigDecimal b = new BigDecimal(currentRecord.getNoteMoney());
                        // bug，投注金额应该算总金额而不是拆分金额
                        BigDecimal bb = b.multiply(new BigDecimal(records.size()));
                        PublicMethod.invitationAward(bb, currentRecord.getUserName(), lotteryUserService, accountService, logger, null, currentRecord.getBetNum(),
                                Constants.GAME_TYPE_LOTTERY, gameService, handler);
                        confirmService.deleteByPrimaryKey(confirm.getConfirmId());
                    }
                });
            } else {
                continue;
            }
        }
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void deleteUnPrize() throws Exception {
        logger.info("执行删除未中奖的投注记录拆分信息");

        int count = userBetService.getUserUnPrizeRecords();

        if (count > 0) {
            logger.info("清理数据结束");
        }
    }

    /**
     * 执行转账失败补发操作
     *
     * @throws Exception
     */
    @Scheduled(cron = "30 0/1 * * * ?")
    public void updateLotteryExc() throws Exception {
        logger.info("执行转账失败补发操作");
        LotteryExcExample excExample = new LotteryExcExample();
        excExample.createCriteria().andIsplusEqualTo(1)
                .andExcStatusEqualTo(Constants.GENERAL_ZERO);
        List<LotteryExc> list = lotteryExcService.selectLotteryExcByExample(excExample);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (LotteryExc lotteryExc : list) {
            threadPoolTaskExecutor.execute(new Runnable() {
                public void run() {
                    StringBuffer key = new StringBuffer("");
                    StringBuffer account = new StringBuffer("");
                    if (Constants.GAME_TYPE_LOTTERY.equals(lotteryExc.getGameType())) {
                        key.append(Constants.eosjs_prive_key);
                        account.append(Constants.account);
                    } else if (Constants.GAME_TYPE_TIGER.equals(lotteryExc.getGameType())) {
                        key.append(Constants.eosjs_prive_key_tiger);
                        account.append(Constants.account_tiger);
                    } else if (Constants.GAME_TYPE_DICE.equals(lotteryExc.getGameType())) {
                        key.append(Constants.eosjs_prive_key_dice);
                        account.append(Constants.account_dice);
                    }
                    String result = new DecimalFormat("0.0000")
                            .format(lotteryExc.getAmount().setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue()).toString();

                    boolean b = true;
                    try {
                        b = accountService.plusBalanceLotterExc(key.toString(), account.toString(), lotteryExc.getUserName(), result);
                    } catch (InterruptedException e) {
                        b = false;
                        logger.error("执行转账失败补发操作失败  excId:" + lotteryExc.getExcId());
                    } finally {
                        if (!b) {
                            lotteryExc.setExcCount(lotteryExc.getExcCount() + 1);
                        } else {
                            lotteryExc.setExcStatus(Constants.GENERAL_ONE);
                        }
                        try {
                            lotteryExcService.updateLotteryExcByPrimaryKeySelective(lotteryExc);
                        } catch (Exception e) {
                            logger.error("更新失败支付记录失败  excId:" + lotteryExc.getExcId());
                            logger.error(e.toString());
                        }
                    }
                }
            });
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getInfor() throws Exception {
        if(isExc){
            return ;
        }

        ChainInfo chainInfo=accountService.getInfo();
        Long num=Long.parseLong(chainInfo.getHeadBlockNum())+1;

        while(true){
            Block block=accountService.getBlockId(String.valueOf(num));
            System.out.println(JSONObject.toJSON(block).toString());
            num++;
        }
    }

}