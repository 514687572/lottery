package com.bootdo.lottery.controller;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.lottery.domain.LotteryRecordsDO;
import com.bootdo.lottery.service.LotteryRecordsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开奖记录
 *
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-11-16 17:02:19
 */

@Controller
@RequestMapping("/lottery/lotteryRecords")
public class LotteryRecordsController {
	@Autowired
	private LotteryRecordsService lotteryRecordsService;

	@GetMapping()
	@RequiresPermissions("lottery:lotteryRecords:lotteryRecords")
	String LotteryRecords(){
	    return "lottery/lotteryRecords/lotteryRecords";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("lottery:lotteryRecords:lotteryRecords")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LotteryRecordsDO> lotteryRecordsList = lotteryRecordsService.list(query);
		int total = lotteryRecordsService.count(query);
		PageUtils pageUtils = new PageUtils(lotteryRecordsList, total);
		return pageUtils;
	}

	/**
	 * 统计用户数据
	 */
	@ResponseBody
	@PostMapping("/getData")
	@RequiresPermissions("lottery:lotteryRecords:lotteryRecords")
	public R getData(String status) {
//		List<UserBerecordsPojo> list = lotteryRecordsService.listByStatistics(status);
		Map<String, Object> map = new HashMap<>();
//		map.put("list", list);
		return R.ok(map);
	}

	@GetMapping("/add")
	@RequiresPermissions("lottery:lotteryRecords:add")
	String add(){
	    return "lottery/lotteryRecords/add";
	}

	@GetMapping("/edit/{recordsId}")
	@RequiresPermissions("lottery:lotteryRecords:edit")
	String edit(@PathVariable("recordsId") Integer recordsId,Model model){
		LotteryRecordsDO lotteryRecords = lotteryRecordsService.get(recordsId);
		model.addAttribute("lotteryRecords", lotteryRecords);
	    return "lottery/lotteryRecords/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("lottery:lotteryRecords:add")
	public R save( LotteryRecordsDO lotteryRecords){
		if(lotteryRecordsService.save(lotteryRecords)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("lottery:lotteryRecords:edit")
	public R update( LotteryRecordsDO lotteryRecords){
		lotteryRecordsService.update(lotteryRecords);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("lottery:lotteryRecords:remove")
	public R remove( Integer recordsId){
		if(lotteryRecordsService.remove(recordsId)>0){
		return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("lottery:lotteryRecords:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] recordsIds){
		lotteryRecordsService.batchRemove(recordsIds);
		return R.ok();
	}

}
