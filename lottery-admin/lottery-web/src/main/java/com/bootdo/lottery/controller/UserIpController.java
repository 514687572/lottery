package com.bootdo.lottery.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.lottery.domain.UserIpDO;
import com.bootdo.lottery.service.UserIpService;
import com.bootdo.system.domain.UserDO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-12-18 14:44:15
 */

@Controller
@RequestMapping("/lottery/userIp")
public class UserIpController {
    @Autowired
    private UserIpService userIpService;

    @GetMapping()
    @RequiresPermissions("lottery:userIp:userIp")
    String UserIp() {
        return "lottery/userIp/userIp";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("lottery:userIp:userIp")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UserIpDO> userIpList = userIpService.list(query);
        int total = userIpService.count(query);
        PageUtils pageUtils = new PageUtils(userIpList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("lottery:userIp:add")
    String add() {
        return "lottery/userIp/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("lottery:userIp:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        UserIpDO userIp = userIpService.get(id);
        model.addAttribute("userIp", userIp);
        return "lottery/userIp/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("lottery:userIp:add")
    public R save(UserIpDO userIp) {
        if (userIpService.save(userIp) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 禁止/解除
     */
    @ResponseBody
    @RequestMapping("/banUserIp")
    @RequiresPermissions("lottery:userIp:banUserIp")
    public R banUserIp(UserIpDO userIp) {
        UserDO user = ShiroUtils.getUser();
        if (userIp != null && user != null) {
            userIp.setOptUser(user.getUsername());
            userIp.setOptUserId(user.getUserId());
        }
        userIpService.updateIpStatus(userIp);
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("lottery:userIp:edit")
    public R update(UserIpDO userIp) {
        userIpService.update(userIp);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("lottery:userIp:remove")
    public R remove(Long id) {
        if (userIpService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("lottery:userIp:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        userIpService.batchRemove(ids);
        return R.ok();
    }

}
