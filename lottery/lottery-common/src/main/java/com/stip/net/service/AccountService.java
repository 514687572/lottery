package com.stip.net.service;

import java.math.BigDecimal;
import java.util.Map;

import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.api.vo.account.Account;

public interface AccountService {
    boolean plusBalance(String account, String quantity) throws InterruptedException;
    
    boolean plusBalanceLotterExc(String pirveKey,String bonusAccount,String account,String quantity) throws InterruptedException;

    /**
	 * 从龙虎斗奖池给玩家发EOS
	 */
    boolean plusBalanceFromTiger(String account, BigDecimal money);

    boolean minusBalance(String account,String quantity,String private_key,String remark) throws InterruptedException;

    /**扣除玩家EOS到骰子奖池
     */
    String minusBalanceToDice(String account,String quantity,String private_key,String remark) throws InterruptedException;
    
    /**
	 * 从骰子奖池给玩家发EOS
	 */
    boolean plusBalanceFromDice(String account, String quantity);

    /**
	 * 扣除玩家EOS到龙虎斗奖池
	 */
    String minusBalanceToTiger(String account, BigDecimal money, String private_key, String remark);

    /**
     * 根据用户名查询账户
     */
	Account getAccount(String account);

	/**
	 * 根据用户名查询余额
	 */
	String getBalance(String account);

    String getPublicKey(String privateKey);
    
    String getUserName(String publicKey);
    
    Map<String, Object> getUserNameByPk(String privateKey) throws Exception;

	/**
	 * 查询TOP账户余额
	 */
	String getTopBalance(String username);

	/**
	 * 从TOP账户给用户发代币
	 */
	boolean transFromTop(String to, BigDecimal money);

	/**
	 * 查询链上有没有这个transaction_id
	 */
	boolean checkTxId(String txId);
	
	/**
	 * 查询账号是否是合约账号
	 * @param account_name
	 * @return
	 */
	boolean get_code_hash(String account_name);
	/**
	 * 抵押CPU
	 * @param pk私钥
	 * @param UserName账户名
	 * @param stakeCpuQuantity 抵押数量
	 * @return
	 */
	public boolean delegatebw(String pk,String UserName, String stakeCpuQuantity);

	public ChainInfo getInfo();

	Block getBlockId(String blockId);
}