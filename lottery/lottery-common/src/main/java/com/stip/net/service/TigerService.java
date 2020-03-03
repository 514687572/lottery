package com.stip.net.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.stip.net.entity.TopFlow;
import com.stip.net.entity.tiger.TigerConfirm;
import com.stip.net.entity.tiger.TigerHistory;
import com.stip.net.entity.tiger.TigerLatePutRecord;
import com.stip.net.entity.tiger.TigerPutRecord;
import com.stip.net.entity.tiger.TigerPutRecord2;
import com.stip.net.entity.tiger.TigerPutRecordUpdater;
import com.stip.net.entity.tiger.TigerRoom;

/**
 * 龙虎斗
 */
public interface TigerService {

	/**
	 * 查询用户某房间某期某个投注项的投注总金额（EOS房间）
	 */
	BigDecimal getMyPutMoney(String userId, int roomId, int qid, int opt);

	/**
	 * 查询用户某房间某期某个投注项的投注总金额（积分房间）
	 */
	BigDecimal getMyPutMoney2(String userId, int roomId, int qid, int opt);

	void saveRecord(TigerPutRecord record);

	void saveRecord2(TigerPutRecord2 record);

	void saveLateRecord(TigerLatePutRecord record);

	TigerPutRecord getRecordByTxId(String txId);

	TigerLatePutRecord getLateRecordByTxId(String txId);

	void updateRecord(TigerPutRecordUpdater updater);

	void updateRecord2(TigerPutRecord2 record);

	void updateLateRecord(TigerLatePutRecord record);

	/**
	 * 龙虎斗开奖时，查询某房间某期的投注记录（包含区块未确认的）
	 * 
	 * @param roomId
	 *            房间号
	 * @param num
	 *            期号
	 */
	List<TigerPutRecord> getRecordsWhenOpen(int roomId, int qid);

	/**
	 * 龙虎斗开奖时，查询某房间某期的投注记录（积分投注）
	 * 
	 * @param roomId
	 *            房间号
	 * @param num
	 *            期号
	 */
	List<TigerPutRecord2> getRecordsWhenOpen2(int roomId, int qid);

	/**
	 * 龙虎斗发奖时，查询某房间某期应该发奖的投注记录
	 * 
	 * @param roomId
	 *            房间号
	 * @param num
	 *            期号
	 */
	List<TigerPutRecord> getRecordsWhenSend(int roomId, int qid);

	/**
	 * 龙虎斗发奖时，查询某房间某期应该发奖的投注记录（积分投注）
	 * 
	 * @param roomId
	 *            房间号
	 * @param num
	 *            期号
	 */
	List<TigerPutRecord2> getRecordsWhenSend2(int roomId, int qid);

	/**
	 * 查询投注时间悠久，却未开奖的投注记录（包含区块未确认的）
	 * 
	 * @param cur
	 *            当前时间戳
	 * @param expire
	 *            过期时长
	 */
	List<TigerPutRecord> getExpiredRecordsToOpen(long cur, long expire);

	/**
	 * 查询投注时间悠久，应该发奖却未发奖的记录
	 * 
	 * @param cur
	 *            当前时间戳
	 * @param expire
	 *            过期时长
	 */
	List<TigerPutRecord> getExpiredRecordsToSend(long cur, long expire);

	/**
	 * 查询应该退钱的迟到投注
	 */
	List<TigerLatePutRecord> getLateRecordsToReturn();

	/**
	 * 查询发奖时因为区块查不到txId而未发奖的记录
	 */
	List<TigerPutRecord> getBlockErrorRecord();

	/**
	 * 查询我的EOS投注记录
	 * 
	 * @param userId
	 *            用户名
	 * @param pageSize
	 *            每页条数
	 * @param time
	 *            开奖时间小于这个时间戳，0表示最新的
	 */
	List<TigerPutRecord> getMyRecords(String userId, int pageSize, long time);

	/**
	 * 查询我的积分投注记录
	 * 
	 * @param uid
	 *            用户表主键id，唯一标识
	 * @param pageSize
	 *            每页条数
	 * @param time
	 *            开奖时间小于这个时间戳，0表示最新的
	 */
	List<TigerPutRecord2> getMyRecord2(long uid, int pageSize, long time);

	List<TigerRoom> getAllTigerRooms();

	/**
	 * 存储房间数据（先删后插）
	 */
	void saveAllRooms(List<TigerRoom> list);

	/**
	 * 查询所有龙虎斗投注确认数据
	 */
	List<TigerConfirm> getAllConfirms();

	/**
	 * 存储房间数据（先删后插）
	 */
	void saveAllConfirms(List<TigerConfirm> list);

	/**
	 * 插入开奖记录
	 */
	void insertTigerHistory(TigerHistory th);

	/**
	 * 查询某条开奖记录
	 * 
	 * @param qid
	 *            期号
	 * @param roomId
	 *            房间号
	 */
	TigerHistory getTigerHistory(int qid, int roomId);

	/**
	 * 查询需要发代币的投注记录
	 */
	List<TigerPutRecord> getRecordsToSendTop();

	/**
	 * 插入TOP币流水
	 */
	void insertTopFlow(TopFlow tf);

	/**
	 * 查询返TOP币总量
	 */
	BigDecimal getTotalTop();

	/**
	 * 添加房间内个人投注信息
	 */
	void addMyInfoInRoom(Map<String, Object> map, String userId, int roomId, int type, int qid);
}