package com.bootdo.lottery.controller;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.lottery.domain.UserRechargeRecordsDO;
import com.bootdo.lottery.service.UserRechargeRecordsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2019-01-09 15:37:15
 */
 
@Controller
@RequestMapping("/lottery/userRechargeRecords")
public class UserRechargeRecordsController {
	@Autowired
	private UserRechargeRecordsService userRechargeRecordsService;
	
	@GetMapping()
	@RequiresPermissions("lottery:userRechargeRecords:userRechargeRecords")
	String UserRechargeRecords(){
	    return "lottery/userRechargeRecords/userRechargeRecords";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("lottery:userRechargeRecords:userRechargeRecords")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserRechargeRecordsDO> userRechargeRecordsList = userRechargeRecordsService.list(query);
		int total = userRechargeRecordsService.count(query);
		PageUtils pageUtils = new PageUtils(userRechargeRecordsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("lottery:userRechargeRecords:add")
	String add(){
	    return "lottery/userRechargeRecords/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("lottery:userRechargeRecords:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		UserRechargeRecordsDO userRechargeRecords = userRechargeRecordsService.get(id);
		model.addAttribute("userRechargeRecords", userRechargeRecords);
	    return "lottery/userRechargeRecords/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("lottery:userRechargeRecords:add")
	public R save( UserRechargeRecordsDO userRechargeRecords){
		if(userRechargeRecordsService.save(userRechargeRecords)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("lottery:userRechargeRecords:edit")
	public R update( UserRechargeRecordsDO userRechargeRecords){
		userRechargeRecordsService.update(userRechargeRecords);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("lottery:userRechargeRecords:remove")
	public R remove( Integer id){
		if(userRechargeRecordsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("lottery:userRechargeRecords:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		userRechargeRecordsService.batchRemove(ids);
		return R.ok();
	}
	
}
