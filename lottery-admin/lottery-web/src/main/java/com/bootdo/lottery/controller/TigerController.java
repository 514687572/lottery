package com.bootdo.lottery.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.lottery.domain.TigerConfirmDO;
import com.bootdo.lottery.domain.TigerHistoryDO;
import com.bootdo.lottery.domain.TigerPutRecordDO;
import com.bootdo.lottery.domain.TigerRoomDO;
import com.bootdo.lottery.domain.TigerUserDO;
import com.bootdo.lottery.service.TigerService;

@Controller
@RequestMapping("/tiger")
public class TigerController {

	@Autowired
	private TigerService tigerService;

	@GetMapping("/confirm")
	String confirmList() {
		return "tiger/confirm/list";
	}

	@ResponseBody
	@GetMapping("/confirm/query")
	public PageUtils confirmQuery(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<TigerConfirmDO> list = tigerService.queryConfirm(query);
		int total = tigerService.countConfirm(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}

	@GetMapping("/room")
	String roomList() {
		return "tiger/room/list";
	}

	@ResponseBody
	@GetMapping("/room/query")
	public PageUtils roomQuery(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<TigerRoomDO> list = tigerService.queryRoom(query);
		int total = tigerService.countRoom(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}

	@GetMapping("/history")
	String historyList() {
		return "tiger/history/list";
	}

	@ResponseBody
	@GetMapping("/history/query")
	public PageUtils historyQuery(@RequestParam Map<String, Object> params) {
		Object qid = params.get("qid");
		Object startTime = params.get("startTime");
		Object endTime = params.get("endTime");
		try {
			Integer.parseInt(qid.toString());
		} catch (Exception e) {
			params.put("qid", null);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(startTime.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			params.put("startTime", cal.getTimeInMillis());
		} catch (Exception e) {
			params.put("startTime", null);
		}
		try {
			Date date = sdf.parse(endTime.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			params.put("endTime", cal.getTimeInMillis());
		} catch (Exception e) {
			params.put("endTime", null);
		}
		Query query = new Query(params);
		List<TigerHistoryDO> list = tigerService.queryHistory(query);
		int total = tigerService.countHistory(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}

	@GetMapping("/record")
	String recordList() {
		return "tiger/record/list";
	}

	@ResponseBody
	@GetMapping("/record/query")
	public PageUtils recordQuery(@RequestParam Map<String, Object> params) {
		Object putMoney = params.get("putMoney");
		Object gainMoney = params.get("gainMoney");
		Object startTime = params.get("startTime");
		Object endTime = params.get("endTime");
		try {
			Double.parseDouble(putMoney.toString());
		} catch (Exception e) {
			params.put("putMoney", null);
		}
		try {
			Double.parseDouble(gainMoney.toString());
		} catch (Exception e) {
			params.put("gainMoney", null);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(startTime.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			params.put("startTime", cal.getTimeInMillis());
		} catch (Exception e) {
			params.put("startTime", null);
		}
		try {
			Date date = sdf.parse(endTime.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			params.put("endTime", cal.getTimeInMillis());
		} catch (Exception e) {
			params.put("endTime", null);
		}
		Query query = new Query(params);
		List<TigerPutRecordDO> list = tigerService.queryRecord(query);
		int total = tigerService.countRecord(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}

	@GetMapping("/statsPut")
	String statsPut() {
		return "tiger/stats/put";
	}

	@ResponseBody
	@PostMapping("/stats/put")
	public R statsPutData(String state) {
		List<Map<String, Object>> list = tigerService.statsPut(state);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("state", state);
		return R.ok(map);
	}

	@GetMapping("/statsActive")
	String statsActive() {
		return "tiger/stats/active";
	}

	@ResponseBody
	@PostMapping("/stats/active")
	public R statsActiveData(String state) {
		List<Map<String, Object>> list = tigerService.statsActive(state);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("state", state);
		return R.ok(map);
	}

	@GetMapping("/statsProfit")
	String statsProfit() {
		return "tiger/stats/profit";
	}

	@ResponseBody
	@PostMapping("/stats/profit")
	public R statsProfitData(String state) {
		BigDecimal totalPutMoney = tigerService.getTotalPutMoney();
		List<Map<String, Object>> list = tigerService.statsProfit(state);
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalPutMoney);
		map.put("list", list);
		map.put("state", state);
		return R.ok(map);
	}

	/**
	 * 用户管理页面
	 */
	@GetMapping("/user")
	String useList() {
		return "tiger/user/list";
	}

	/**
	 * 用户管理统计
	 */
	@ResponseBody
	@GetMapping("/user/query")
	public PageUtils userQuery(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<TigerUserDO> list = tigerService.queryUser(query);
		int total = tigerService.countUser(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}

	/**
	 * 更改用户冻结状态
	 */
	@ResponseBody
	@PostMapping("/user/edit")
	public R editUserStatus(@RequestParam String userId) {
		tigerService.updateUserStatus(userId);
        return R.ok();
	}

    /**
     * 用户投注记录明细页面
     */
    @GetMapping("/user/puts")
    String userPut(Model model, HttpServletRequest request) {
        String userId = request.getParameter("userId");
        model.addAttribute("userId",userId);
        return "tiger/user/puts";
    }

    /**
     * 用户下级列表
     */
    @GetMapping("/user/child")
    public String userChild(Model model, HttpServletRequest request) {
    	String userId = request.getParameter("userId");
    	model.addAttribute("userId",userId);
    	return "tiger/user/child";
    }

    @ResponseBody
    @GetMapping("/child/query")
    public PageUtils childQuery(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<Map<String, Object>> list = tigerService.queryChild(query);
		int total = tigerService.countChild(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
    }
}