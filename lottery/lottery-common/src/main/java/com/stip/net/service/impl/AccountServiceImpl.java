package com.stip.net.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.net.utils.CoinUtil;
import com.lottery.net.utils.Constants;
import com.stip.net.dao.LotteryExcDao;
import com.stip.net.entity.LotteryExc;
import com.stip.net.service.AccountService;

import io.eblock.eos4j.Ecc;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.vo.account.Account;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import io.eblock.eos4j.utils.Constant;
import io.eblock.eos4j.utils.HttpUtils;
import net.sf.json.JSONObject;
/**
 * EOS账户
 * @author cja
 *
 */
@Service
public class AccountServiceImpl implements AccountService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LotteryExcDao lotteryExcDao;

	/**
	 * 向用户eos钱包加EOS
	 */
	public boolean plusBalance(String account,String quantity) throws InterruptedException {  
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			Transaction t = rpc.transfer(Constants.eosjs_prive_key,Constants.contractAccount, Constants.account,account, quantity+" EOS", "");
			t.getTransactionId();
			return true;
		}catch(Exception ex) {
			logger.error("plusBalance失败，区块链服务异常！");
			this.addLotteryExc(new LotteryExc(account,
					Constants.ADD,new BigDecimal(quantity),Constants.GAME_TYPE_LOTTERY));
			return false;
		}
	}

	/**
	 * 添加转账失败记录
	 */
	private void addLotteryExc(LotteryExc lotteryExc) {
		try {
			lotteryExc.setExcCount(1);
			lotteryExc.setExcStatus(Constants.GENERAL_ZERO);
			lotteryExcDao.insertSelective(lotteryExc);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean plusBalanceFromTiger(String account, BigDecimal money) {
		String formatMoney = CoinUtil.formatMoney(money);
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			rpc.transfer(Constants.eosjs_prive_key_tiger, Constants.contractAccount, Constants.account_tiger, account,
					formatMoney + " EOS", "");
			return true;
		} catch (Exception e) {
			logger.error("plusBalanceFromTiger失败，区块链服务异常！");
			addLotteryExc(new LotteryExc(account, Constants.ADD, money, Constants.GAME_TYPE_TIGER));
		}
		return false;
	}

	/**
	 * 用户EOS加钱 用于转账失败 记录补发
	 * 此方法不能做任何修改
	 * @return
	 * @throws InterruptedException
	 */
	public boolean plusBalanceLotterExc(String pirveKey,String bonusAccount,String account,String quantity) throws InterruptedException {
		Rpc rpc = new Rpc(Constants.EOS_server);
		
		try {
			Transaction t = rpc.transfer(pirveKey,Constants.contractAccount, bonusAccount,account, quantity+" EOS", "");
			t.getTransactionId();
			
			return true;
		}catch(Exception ex) {
			logger.error(ex.toString());
			return false;
		}
		
	}
	
	/**
	 * 向用户eos钱包减EOS
	 */
	public  boolean minusBalance(String account,String quantity,String private_key,String remark) throws InterruptedException {  
		Rpc rpc = new Rpc(Constants.EOS_server);

		try {
			Transaction t = rpc.transfer(private_key,Constants.contractAccount,account ,Constants.account, quantity+" EOS",remark);
			t.getTransactionId();
			return true;
		}catch(Exception ex) {
			logger.error("minusBalance失败，区块链服务异常！");
			return false;
		}
		
	}
	
	/**
	 * 用户EOS钱包减EOS
	 * @param bonusAccount 奖金池账户
	 * @param account 转出账户名
	 * @param quantity 转出数量小数点后4位
	 * @param private_key 转出账户私钥
	 * @param remark 转账备注
	 */
	public  boolean minusBalance(String bonusAccount,String account,String quantity,String private_key,String remark) throws InterruptedException {  
		Rpc rpc = new Rpc(Constants.EOS_server);
		
		try {
			Transaction t = rpc.transfer(private_key,Constants.contractAccount,account ,bonusAccount, quantity+" EOS",remark);
			t.getTransactionId();
			return true;
		}catch(Exception ex) {
			logger.error("minusBalance失败，区块链服务异常！");
			return false;
		}
		
	}
	
	/**
	 * 骰子向用户eos钱包减EOS
	 */
	public String minusBalanceToDice(String account, String quantity, String private_key, String remark)
			throws InterruptedException {
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			Transaction t1 = rpc.transfer(private_key,Constants.contractAccount,account ,Constants.account_dice, quantity+" EOS",remark);
			return t1.getTransactionId();
		}catch(Exception ex) {
			logger.error("minusBalanceToDice失败，区块链服务异常！");
			return null;
		}
	}
	
	/**
	 * 骰子向用户EOS钱包加EOS
	 */
	@Override
	public boolean plusBalanceFromDice(String account, String quantity) {
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			rpc.transfer(Constants.eosjs_prive_key_dice,Constants.contractAccount, Constants.account_dice,account, quantity+" EOS", "");
			return true;
		}catch(Exception e) {
			logger.error("plusBalanceFromDice失败，区块链服务异常！");
			addLotteryExc(new LotteryExc(account,
					Constants.ADD,new BigDecimal(quantity),Constants.GAME_TYPE_DICE));
			return false;
		}
	}

	@Override
	public String minusBalanceToTiger(String account, BigDecimal money, String private_key, String remark) {
		String formatMoney = CoinUtil.formatMoney(money);
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			Transaction t1 = rpc.transfer(private_key, Constants.contractAccount, account, Constants.account_tiger,
					formatMoney + " EOS", remark);
			return t1.getTransactionId();
		} catch (Exception e) {
			logger.error("minusBalanceToTiger失败，区块链服务异常！");
		}
		return null;
	}

	/**
	 * 查询指定账户
	 * 
	 * @param account
	 *            账户名
	 */
	public Account getAccount(String account) {
		if (StringUtils.isBlank(account)) {
			logger.error("getAccount失败，account:" + account);
			return null;
		}
		try {
			Rpc rpc = new Rpc(Constants.EOS_server);
			Account acc = rpc.getAccount(account);
			return acc;
		} catch (Exception e) {
			logger.error("getAccount失败，区块链服务异常！account:" + account);
		}
		return null;
	}

	/**
	 * 查询指定账户余额
	 */
	public String getBalance(String account) {
		Account acc = getAccount(account);
		if (acc != null) {
			return acc.getCoreLiquidBalance();
		}
		return null;
	}

	/**
	 * 通过用户私钥查询公钥
	 */
	public String getPublicKey(String privateKey) {
		try {
			String pk = Ecc.privateToPublic(privateKey);
			return pk;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 通过公钥查询用户账户名
	 */
	public String getUserName(String publicKey) {
		Map<String, String> params = new HashMap<String, String>(1);
		JSONObject obj = null;

		if(StringUtils.isBlank(publicKey)) {
			return null;
		}else {
			params.put("public_key", publicKey);
		}

		try {
			obj = HttpUtils.BodyPost(Constants.EOS_GET_USERNAME, params);
		} catch (IOException e) {
			logger.error("getUserName失败，区块链服务异常！");
		}
		if (obj != null && obj.containsKey("account_names")) {
			if (!obj.getJSONArray("account_names").isEmpty()) {
				return obj.getJSONArray("account_names").getString(0);
			}
			return null;
		}
		return null;
	}

	/**
	 * 通过用户私钥获取当前用户名
	 * @param request
	 * @return
	 * @throws Exception
	 */
    public Map<String, Object> getUserNameByPk(String privateKey) throws Exception {
    	Map<String, Object> jsonResult = new HashMap<String, Object>(1);
    	
    	jsonResult.put("status","0");
    	
    	if (StringUtils.isBlank(privateKey)&&!(privateKey.length()==51)) {
    		jsonResult.put("error", "私钥错误");
    		
    		return jsonResult;
    	}
    	
    	String pubKey=this.getPublicKey(privateKey);

    	if (StringUtils.isBlank(pubKey)&&!(pubKey.length()==53)) {
    		jsonResult.put("error", "信息错误01");
    		
    		return jsonResult;
    	}else {
    		jsonResult.put("pubKey",pubKey);
    	}
    	
    	String userName=this.getUserName(pubKey);
    	
    	if(userName==null) {
    		jsonResult.put("error", "信息错误02");
    		
    		return jsonResult;
    	}else {
    		jsonResult.put("userName",userName);
    		jsonResult.put("status","1");
    	}
    	
    	return jsonResult;
    }

	@Override
	public String getTopBalance(String username) {
		try {
			Rpc rpc = new Rpc(Constants.EOS_server);
			String balance = rpc.getTopBalance(username);
			return balance;
		} catch (Exception e) {
			logger.error("getTopBalance失败，区块链服务异常！");
		}
		return null;
	}

	@Override
	public boolean transFromTop(String to, BigDecimal money) {
		String formatMoney = CoinUtil.formatMoney(money);
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			rpc.transfer(Constant.topjs_prive_key, Constant.contractAccount_top, Constant.account_top, to,
					formatMoney + " GOS", "");
			return true;
		} catch (Exception e) {
			logger.error("发送TOP币失败，区块链服务异常！");
		}
		return false;
	}

	@Override
	public boolean checkTxId(String txId) {
		String actionUrl = Constants.EOS_server + "/v1/history/get_transaction";
		Map<String, String> params = new HashMap<>();
		params.put("id", txId);
		try {
			HttpUtils.BodyPost(actionUrl, params);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean get_code_hash(String account_name) {
		String actionUrl = Constants.EOS_server + "/v1/chain/get_code_hash";
		Map<String, String> params = new HashMap<>();
		params.put("account_name", account_name);
		try {
			JSONObject object=HttpUtils.BodyPost(actionUrl, params);
			String hash=(String) object.get("code_hash");
			if(StringUtils.remove(hash, "0").length()==0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 用户CPU抵押
	 * pk 私钥
	 * userName 账户名
	 * stakeCpuQuantity 抵押数量必须保留4位小数如0.0100 EOS
	 */
	@Override
	public boolean delegatebw(String pk,String UserName, String stakeCpuQuantity) {
		Rpc rpc = new Rpc(Constants.EOS_server);
		try {
			rpc.delegatebw(pk, UserName, UserName, stakeCpuQuantity, 0l);
			return true;
		} catch (Exception e) {
			logger.error("抵押操作失败，区块链服务异常！");
		}
		return false;
	}

	public ChainInfo getInfo() {
		try {
			Rpc rpc = new Rpc(Constants.EOS_server);
			ChainInfo chainInfo = rpc.getChainInfo();
			return chainInfo;
		} catch (Exception e) {
			logger.error("getTopBalance失败，区块链服务异常！");
		}
		return null;
	}

	public Block getBlockId(String blockId) {
		try {
			Rpc rpc = new Rpc(Constants.EOS_server);
			Block block = rpc.getBlock(blockId);
			return block;
		} catch (Exception e) {
			logger.error("getTopBalance失败，区块链服务异常！");
		}
		return null;
	}
}