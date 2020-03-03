package io.eblock.eos4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.eblock.eos4j.api.vo.transaction.push.TxSign;
import io.eblock.eos4j.ecc.EccTool;
import io.eblock.eos4j.ese.Ese;
import io.eblock.eos4j.utils.HttpUtils;
import net.sf.json.JSONObject;

/**
 * Ecc,用户生成公私钥，签名，数据序列化
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Ecc {

	/**
	 * 通过种子生成私钥
	 * 
	 * @param seed
	 *            种子
	 * @return
	 */
	public static String seedPrivate(String seed) {
		return EccTool.seedPrivate(seed);
	}

	/**
	 * 通过私钥生成公钥
	 * 
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String privateToPublic(String privateKey) {
		return EccTool.privateToPublic(privateKey);
	}

	/**
	 * 普通数据签名
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            需要签名的数据
	 * @return
	 */
	public static String sign(String privateKey, String data) {
		return EccTool.sign(privateKey, data);
	}
	
	/**
	 * 交易签名
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            需要签名的对象
	 * @return
	 */
	public static String signTransaction(String privateKey, TxSign sign) {
		return EccTool.signTransaction(privateKey, sign);
	}

	/**
	 * 转账数据序列化
	 * 
	 * @param from
	 *            从
	 * @param to
	 *            到
	 * @param quantity
	 *            转账金额和币种
	 * @param memo
	 *            备注留言
	 * @return
	 */
	public static String parseTransferData(String from, String to, String quantity, String memo) {
		return Ese.parseTransferData(from, to, quantity, memo);
	}
	
	/**
	 * 
	 * @param voter
	 * @param proxy
	 * @param producers
	 * @return
	 */
	public static String parseVoteProducerData(String voter, String proxy, List<String> producers) {
		return Ese.parseVoteProducerData(voter, proxy, producers);
	}

	/**
	 * 创建账户数据序列化
	 * 
	 * @param creator
	 *            创建者
	 * @param name
	 *            账户名
	 * @param onwe
	 *            onwer公钥
	 * @param active
	 *            active公钥
	 * @return
	 */
	public static String parseAccountData(String creator, String name, String onwer, String active) {
		return Ese.parseAccountData(creator, name, onwer, active);
	}

	/**
	 * 购买ram数据序列化
	 * 
	 * @param payer
	 *            付款账户
	 * @param receiver
	 *            接收账户
	 * @param bytes
	 *            购买字节数量
	 * @return
	 */
	public static String parseBuyRamData(String payer, String receiver, Long bytes) {
		return Ese.parseBuyRamData(payer, receiver, bytes);
	}

	/**
	 * 抵押数据序列化
	 * 
	 * @param from
	 *            抵押账户
	 * @param receiver
	 *            接受账户
	 * @param stakeNetQuantity
	 *            网络抵押数量和币种
	 * @param stakeCpuQuantity
	 *            CPU抵押数量和币种
	 * @param transfer
	 *            是否讲抵押资产转送给对方，0自己所有，1对方所有
	 * @return
	 */
	public static String parseBuyRamData(String from, String receiver, String stakeNetQuantity, String stakeCpuQuantity,
			int transfer) {
		return Ese.parseDelegateData(from, receiver, stakeNetQuantity, stakeCpuQuantity, transfer);
	}
	
	/**
	 * 关闭token
	 * @param owner
	 * @param symble
	 * @return
	 */
	public static String parseCloseData(String owner, String symbol) {
		return Ese.parseCloseData(owner, symbol);
	}
	static final String eosjs_transfer_seriz = "00f2d4142123e95d0000c85353840ccdb486010000000000045359530000000019e6b58be8af95313233616263646f2e2f2c2e2f214023232425";
	
	static final String eosjs_account_seriz = "0000000000ea30550002a2f164772b5601000000010003ee4221c9c3f4f62646e3c758dbb8abaae506a559f67148a76968fa6b0f0868140100000001000000010003ba8de2f029cae85e7ca5c9f591bb17b86d750c5116cec30d94100e16e446d41501000000";
	
	public static void main(String[] args) throws Exception {

		System.out.println("============= 通过私钥生成公钥 ===============");
		String pu = Ecc.privateToPublic("5JeRf7CegmRw1Rs2bdX9zoc4QLKQDHLUT3DkS6W3yemStEdjEZE");
		System.out.println("public key :" + pu + " \n ");
		Map<String, String> params=new HashMap<String, String>();
		params.put("public_key", pu);
		JSONObject t1 = HttpUtils.BodyPost("http://47.91.208.237:8888/v1/history/get_key_accounts",params);
		System.out.println("转账成功 = " + t1.getJSONArray("account_names").getString(0)+" \n ");
	}
}
