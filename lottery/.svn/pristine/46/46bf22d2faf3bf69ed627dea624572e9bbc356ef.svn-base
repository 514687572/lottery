package com.stip.net.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.net.utils.CoinUtil;
import com.lottery.net.utils.Constants;
import com.stip.net.dao.UserBetScoreRecordsDao;
import com.stip.net.entity.UserBetScoreRecords;
import com.stip.net.example.UserBetScoreRecordsExample;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.UserBetScoreRecordsService;
import com.stip.net.service.UserBetService;

import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.utils.Constant;
import net.sf.json.JSONObject;

@Scope("request")
@RequestMapping("/tt")
@RestController
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class TigerController2 {

//	@Autowired
//	private TigerOpenConsumer consumer;

	@Autowired
	private AccountService accountService;

	@Autowired
	private GameService gameService;

	@Autowired
	LotteryUserService lotteryUserService;

	@Autowired
	private UserBetService userBetService;

	@Autowired
    public UserBetScoreRecordsDao userBetScoreRecords;

	@Autowired
    private UserBetScoreRecordsService userBetScoreRecordsService;

	@GetMapping("/song.do")
	public String song() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int block_number = 90810;
				while (true) {
					block_number++;
					String hash = UUID.randomUUID().toString().replaceAll("-", "");
					hash = hash + hash;
					long block_time = System.currentTimeMillis();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
					df.setTimeZone(TimeZone.getTimeZone("UTC"));
					String timestamp = df.format(new Date(block_time));
					String value = "{\"id\":\"" + hash + "\",\"block_num\":" + block_number + ",\"header\":{\"timestamp\":\"" + timestamp + "\",\"producer\":\"eosio\",\"confirmed\":0,\"previous\":\"000162b90b0ae8b6ef3479a9d0e2344eb0d357c0a5711523ba3a32f1aed7860f\",\"transaction_mroot\":\"0000000000000000000000000000000000000000000000000000000000000000\",\"action_mroot\":\"1395b3b838f1a9249bfcde76b9b6379a5c7fb764fada90d7fb6f1593c0e113f1\",\"schedule_version\":0,\"header_extensions\":[],\"producer_signature\":\"SIG_K1_K3yPwihm7iTdn4DgE8Q9NJSTR4AgTxhsBf7ys6sAY5sDivzDbzB1WnEBPH3u7iGcdXjQBMUhSfdMkdoaMiZEt8yu8cpQ8p\"},\"dpos_proposed_irreversible_blocknum\":90810,\"dpos_irreversible_blocknum\":90809,\"bft_irreversible_blocknum\":0,\"pending_schedule_lib_num\":0,\"pending_schedule_hash\":\"828135c21a947b15cdbf4941ba09e1c9e0a80e88a157b0989e9b476b71a21c6b\",\"pending_schedule\":{\"version\":0,\"producers\":[]},\"active_schedule\":{\"version\":0,\"producers\":[{\"producer_name\":\"eosio\",\"block_signing_key\":\"EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV\"}]},\"blockroot_merkle\":{\"_active_nodes\":[\"000162b90b0ae8b6ef3479a9d0e2344eb0d357c0a5711523ba3a32f1aed7860f\",\"1a8105676f6458e9929e8238b44849e9596e526cd630c03c3b9bfcfd992f2fc3\",\"7966bd8c279136a7dc35366ee19c598dbbfc386baa8fcf3cd890e8855baac7af\",\"d085dd89871a3e7d3c707acc5bdd778930fb1ee1bcdbc4e306fae9fa8647fdd0\",\"28142a4d76123450a7d89023aeea3fd6d5617045b503e73c9973b0d3475efc74\",\"fdfe66d14da768d3dc1c1e84832ade78c5ff543b3f96a47da92b6dda82d9b6d3\",\"dc98f96278b6deaaeb477d55bd6bb00db06366d043f4025b76fcaf48f9bbec7b\",\"7c86b57c18f2f35a0ad5458aabae54d2db65a14fd4dbbde5639154fb9e051e2a\",\"2dbd8598fb1109289cf27b4a9188e2c7494daadc027d3f2e3dd156745151c7d5\",\"4c4fdb6e73b5760c4e4fa87a661f0a415d1d6548ca8aaa4974ad2d22a70345e8\"],\"_node_count\":90809},\"producer_to_last_produced\":[[\"eosio\",90810]],\"producer_to_last_implied_irb\":[[\"eosio\",90809]],\"block_signing_key\":\"EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV\",\"confirm_count\":[],\"confirmations\":[],\"block\":{\"timestamp\":\"2018-11-28T06:16:26.000\",\"producer\":\"eosio\",\"confirmed\":0,\"previous\":\"000162b90b0ae8b6ef3479a9d0e2344eb0d357c0a5711523ba3a32f1aed7860f\",\"transaction_mroot\":\"0000000000000000000000000000000000000000000000000000000000000000\",\"action_mroot\":\"1395b3b838f1a9249bfcde76b9b6379a5c7fb764fada90d7fb6f1593c0e113f1\",\"schedule_version\":0,\"header_extensions\":[],\"producer_signature\":\"SIG_K1_K3yPwihm7iTdn4DgE8Q9NJSTR4AgTxhsBf7ys6sAY5sDivzDbzB1WnEBPH3u7iGcdXjQBMUhSfdMkdoaMiZEt8yu8cpQ8p\",\"transactions\":[],\"block_extensions\":[]},\"validated\":true,\"in_current_chain\":true}";
					JSONObject jsonObject = JSONObject.fromObject(value);
//					consumer.test(jsonObject);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		return "<h1>success</h1>";
	}

	@GetMapping("/get")
	public String get() {
		String username = "neversaylove";
		String balance = accountService.getTopBalance(username);
		return balance;
	}

	@GetMapping("/trans")
	public String trans() {
		String account = "mylovefamily";
		BigDecimal money = new BigDecimal("5");
		boolean success = accountService.plusBalanceFromTiger(account, money);
		return "" + success;
	}

	@GetMapping("/fix")
	public String fix() {
		String formatMoney = CoinUtil.formatMoney(new BigDecimal(10000));
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			rpc.transfer("5KWSK1cYpLX6ob7uKhgUKkX3beF7EN5twBwnUUf86Cw1t8oZDSr", Constant.contractAccount_top, "neversaylove", Constant.account_top, formatMoney + " GOS", "");
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		return "true";
	}

	@GetMapping("/sql")
	public String sql() {
		int pageNum = 2;
		UserBetScoreRecordsExample example = new UserBetScoreRecordsExample();
        example.setPager(pageNum, 4);
        List<UserBetScoreRecords> list = userBetScoreRecords.selectByExample(example);
        StringBuffer sb = new StringBuffer();
        for (UserBetScoreRecords o : list) {
			sb.append(o.getBetId());
			sb.append("<br />");
		}
		return sb.toString();
	}
}