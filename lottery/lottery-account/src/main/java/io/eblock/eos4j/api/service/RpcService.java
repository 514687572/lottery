package io.eblock.eos4j.api.service;

import java.util.List;
import java.util.Map;

import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.api.vo.TableRows;
import io.eblock.eos4j.api.vo.TableRowsReq;
import io.eblock.eos4j.api.vo.account.Account;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import io.eblock.eos4j.api.vo.transaction.push.TxRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public interface RpcService {

	@GET("/v1/chain/get_info")
	Call<ChainInfo> getChainInfo();

	@POST("/v1/chain/get_block")
	Call<Block> getBlock(@Body Map<String, String> requestFields);

	@POST("/v1/chain/get_account")
	Call<Account> getAccount(@Body Map<String, String> requestFields);
	
	@POST("/v1/history/get_key_accounts")
	Call<Account> getAccounts(@Body Map<String, String> requestFields);

	@POST("/v1/chain/push_transaction")
	Call<Transaction> pushTransaction(@Body TxRequest request);

	@POST("/v1/chain/get_table_rows")
	Call<TableRows> getTableRows(@Body TableRowsReq request);

	/**
	 * 查询TOP账户余额，返回的是个集合
	 */
	@POST("/v1/chain/get_currency_balance")
	Call<List<String>> getTopBalance(@Body Map<String, String> params);
}