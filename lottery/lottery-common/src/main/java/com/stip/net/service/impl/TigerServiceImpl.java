package com.stip.net.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.stip.net.dao.TigerDao;
import com.stip.net.entity.TopFlow;
import com.stip.net.entity.tiger.TigerConfirm;
import com.stip.net.entity.tiger.TigerHistory;
import com.stip.net.entity.tiger.TigerLatePutRecord;
import com.stip.net.entity.tiger.TigerOption;
import com.stip.net.entity.tiger.TigerPutRecord;
import com.stip.net.entity.tiger.TigerPutRecord2;
import com.stip.net.entity.tiger.TigerPutRecordUpdater;
import com.stip.net.entity.tiger.TigerRoom;
import com.stip.net.service.TigerService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class TigerServiceImpl implements TigerService {

	@Autowired
	private TigerDao dao;

	@Override
	public BigDecimal getMyPutMoney(String userId, int roomId, int qid, int opt) {
		return dao.getMyPutMoney(userId, roomId, qid, opt);
	}

	@Override
	public BigDecimal getMyPutMoney2(String userId, int roomId, int qid, int opt) {
		return dao.getMyPutMoney2(userId, roomId, qid, opt);
	}

	@Override
	public void saveRecord(TigerPutRecord record) {
		dao.saveRecord(record);
	}

	@Override
	public void saveRecord2(TigerPutRecord2 record) {
		dao.saveRecord2(record);
	}

	@Override
	public void saveLateRecord(TigerLatePutRecord record) {
		dao.saveLateRecord(record);
	}

	@Override
	public TigerPutRecord getRecordByTxId(String txId) {
		return dao.getRecordByTxId(txId);
	}

	@Override
	public TigerLatePutRecord getLateRecordByTxId(String txId) {
		return dao.getLateRecordByTxId(txId);
	}

	@Override
	public void updateRecord(TigerPutRecordUpdater updater) {
		dao.updateRecordByTxId(updater);
	}

	@Override
	public void updateRecord2(TigerPutRecord2 record) {
		dao.updateRecord2ById(record);
	}

	@Override
	public void updateLateRecord(TigerLatePutRecord record) {
		dao.updateLateRecordByTxId(record);
	}

	@Override
	public List<TigerPutRecord> getRecordsWhenOpen(int roomId, int qid) {
		return dao.getRecordsWhenOpen(roomId, qid);
	}

	@Override
	public List<TigerPutRecord2> getRecordsWhenOpen2(int roomId, int qid) {
		return dao.getRecordsWhenOpen2(roomId, qid);
	}

	@Override
	public List<TigerPutRecord> getRecordsWhenSend(int roomId, int qid) {
		return dao.getRecordsWhenSend(roomId, qid);
	}

	@Override
	public List<TigerPutRecord2> getRecordsWhenSend2(int roomId, int qid) {
		return dao.getRecordsWhenSend2(roomId, qid);
	}

	@Override
	public List<TigerPutRecord> getExpiredRecordsToOpen(long cur, long expire) {
		return dao.getExpiredRecordsToOpen(cur, expire);
	}

	@Override
	public List<TigerPutRecord> getExpiredRecordsToSend(long cur, long expire) {
		return dao.getExpiredRecordsToSend(cur, expire);
	}

	@Override
	public List<TigerLatePutRecord> getLateRecordsToReturn() {
		return dao.getLateRecordsToReturn();
	}

	@Override
	public List<TigerPutRecord> getBlockErrorRecord() {
		return dao.getRecordsByStatus(-2);
	}

	@Override
	public List<TigerPutRecord> getMyRecords(String userId, int pageSize, long time) {
		return dao.getMyRecords(userId, pageSize, time);
	}

	@Override
	public List<TigerPutRecord2> getMyRecord2(long uid, int pageSize, long time) {
		return dao.getMyRecord2(uid, pageSize, time);
	}

	@Override
	public List<TigerRoom> getAllTigerRooms() {
		return dao.getAllTigerRooms();
	}

	@Override
	public void saveAllRooms(List<TigerRoom> list) {
		if (list != null & list.size() > 0) {
			dao.deleteAllRooms();
			dao.batchInsertRooms(list);
		}
	}

	@Override
	public List<TigerPutRecord> getRecordsToSendTop() {
		return dao.getRecordsToSendTop();
	}

	@Override
	public List<TigerConfirm> getAllConfirms() {
		return dao.getAllConfirms();
	}

	@Override
	public void saveAllConfirms(List<TigerConfirm> list) {
		dao.deleteAllConfirms();
		if (list != null && list.size() > 0) {
			dao.batchInsertConfirms(list);
		}
	}

	@Override
	public void insertTigerHistory(TigerHistory th) {
		dao.insertTigerHistory(th);
	}

	@Override
	public TigerHistory getTigerHistory(int qid, int roomId) {
		return dao.getTigerHistory(qid, roomId);
	}

	@Override
	public void insertTopFlow(TopFlow tf) {
		dao.insertTopFlow(tf);
	}

	@Override
	public BigDecimal getTotalTop() {
		BigDecimal sum = dao.getTotalTop();
		if (sum == null) {
			return new BigDecimal(0);
		}
		return sum;
	}

	@Override
	public void addMyInfoInRoom(Map<String, Object> map, String userId, int roomId, int type, int qid) {
		BigDecimal long0_my = new BigDecimal(0);
		BigDecimal long1_my = new BigDecimal(0);
		BigDecimal long2_my = new BigDecimal(0);
		BigDecimal hu0_my = new BigDecimal(0);
		BigDecimal hu1_my = new BigDecimal(0);
		BigDecimal hu2_my = new BigDecimal(0);
		BigDecimal he_my = new BigDecimal(0);

		if (type == 0) {
			long0_my = getMyPutMoney(userId, roomId, qid, TigerOption.long0.getId());
			long1_my = getMyPutMoney(userId, roomId, qid, TigerOption.long1.getId());
			long2_my = getMyPutMoney(userId, roomId, qid, TigerOption.long2.getId());
			hu0_my = getMyPutMoney(userId, roomId, qid, TigerOption.hu0.getId());
			hu1_my = getMyPutMoney(userId, roomId, qid, TigerOption.hu1.getId());
			hu2_my = getMyPutMoney(userId, roomId, qid, TigerOption.hu2.getId());
			he_my = getMyPutMoney(userId, roomId, qid, TigerOption.he.getId());
		} else if (type == 1) {
			long0_my = getMyPutMoney2(userId, roomId, qid, TigerOption.long0.getId());
			long1_my = getMyPutMoney2(userId, roomId, qid, TigerOption.long1.getId());
			long2_my = getMyPutMoney2(userId, roomId, qid, TigerOption.long2.getId());
			hu0_my = getMyPutMoney2(userId, roomId, qid, TigerOption.hu0.getId());
			hu1_my = getMyPutMoney2(userId, roomId, qid, TigerOption.hu1.getId());
			hu2_my = getMyPutMoney2(userId, roomId, qid, TigerOption.hu2.getId());
			he_my = getMyPutMoney2(userId, roomId, qid, TigerOption.he.getId());
		}

		List<Map<String, Object>> myBet = new ArrayList<>();
		map.put("myBet", myBet);

		Map<String, Object> put_long0 = new HashMap<>();
		put_long0.put("op", TigerOption.long0.getId());
		put_long0.put("m", long0_my);
		myBet.add(put_long0);

		Map<String, Object> put_long1 = new HashMap<>();
		put_long1.put("op", TigerOption.long1.getId());
		put_long1.put("m", long1_my);
		myBet.add(put_long1);

		Map<String, Object> put_long2 = new HashMap<>();
		put_long2.put("op", TigerOption.long2.getId());
		put_long2.put("m", long2_my);
		myBet.add(put_long2);

		Map<String, Object> put_hu0 = new HashMap<>();
		put_hu0.put("op", TigerOption.hu0.getId());
		put_hu0.put("m", hu0_my);
		myBet.add(put_hu0);

		Map<String, Object> put_hu1 = new HashMap<>();
		put_hu1.put("op", TigerOption.hu1.getId());
		put_hu1.put("m", hu1_my);
		myBet.add(put_hu1);

		Map<String, Object> put_hu2 = new HashMap<>();
		put_hu2.put("op", TigerOption.hu2.getId());
		put_hu2.put("m", hu2_my);
		myBet.add(put_hu2);

		Map<String, Object> put_he = new HashMap<>();
		put_he.put("op", TigerOption.he.getId());
		put_he.put("m", he_my);
		myBet.add(put_he);
	}
}