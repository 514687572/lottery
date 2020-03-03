package com.bootdo.lottery.controller;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.lottery.domain.UserBerecordsDO;
import com.bootdo.lottery.pojo.UserBerecordsPojo;
import com.bootdo.lottery.service.UserBerecordsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户下注记录
 *
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-11-16 17:02:19
 */

@Controller
@RequestMapping("/lottery/userBerecords")
public class UserBerecordsController {
    @Autowired
    private UserBerecordsService userBerecordsService;

    @GetMapping()
    @RequiresPermissions("lottery:userBerecords:userBerecords")
    String UserBerecords() {
        return "lottery/userBerecords/userBerecords";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("lottery:userBerecords:userBerecords")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UserBerecordsDO> userBerecordsList = userBerecordsService.list(query);
        int total = userBerecordsService.count(query);
        PageUtils pageUtils = new PageUtils(userBerecordsList, total);
        return pageUtils;
    }

    /**
     * 统计交易数据
     */
    @ResponseBody
    @PostMapping("/getData")
    @RequiresPermissions("lottery:userBerecords:getData")
    public R getData(String status) {
        List<UserBerecordsPojo> list = userBerecordsService.listByStatistics(status);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("state",status);
        return R.ok(map);
    }

    @GetMapping("/userActivity")
    @RequiresPermissions("lottery:userBerecords:userActivity")
    String userActivity() {
        return "lottery/userBerecords/userActivity";
    }

    /**
     * 统计用户活跃数据
     */
    @ResponseBody
    @PostMapping("/getUserActivityData")
    @RequiresPermissions("lottery:userBerecords:getUserActivityData")
    public R getUserActivityData(String status) {
        List<UserBerecordsPojo> list = userBerecordsService.listByUserActivity(status);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("state",status);
        return R.ok(map);
    }

    @GetMapping("/profit")
    @RequiresPermissions("lottery:userBerecords:profit")
    String profit() {
        return "lottery/userBerecords/profitStatistics";
    }

    /**
     * 利润统计
     */
    @ResponseBody
    @PostMapping("/getProfit")
    @RequiresPermissions("lottery:userBerecords:getProfit")
    public R getProfit(String status) {
        List<UserBerecordsPojo> list = userBerecordsService.listByProfit(status);
        BigDecimal accumulateBalance = userBerecordsService.accumulateBalance();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("state",status);
        map.put("accumulateBalance",accumulateBalance);
        return R.ok(map);
    }

    /**
     * 用户管理页面
     * @return
     */
    @GetMapping("/userMange")
    @RequiresPermissions("lottery:userBerecords:userMange")
    String userMange() {
        return "lottery/userBerecords/userMange";
    }

    /**
     * 用户管理统计
     */
    @ResponseBody
    @GetMapping("/userList")
    @RequiresPermissions("lottery:userBerecords:userMange")
    public PageUtils userList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UserBerecordsDO> userBerecordsList = userBerecordsService.listForUsers(query);
        int total = userBerecordsService.countForUsers(query);
        PageUtils pageUtils = new PageUtils(userBerecordsList, total);
        return pageUtils;
    }

    /**
     * 用户投注记录明细页面
     * @return
     */
    @GetMapping("/userDetail")
    @RequiresPermissions("lottery:userBerecords:userMange")
    String userDetail(Model model, HttpServletRequest request) {
        String userName = request.getParameter("userName");
        model.addAttribute("userName",userName);
        return "lottery/userBerecords/userDetail";
    }

    /**
     * 用户投注记录明细列表
     */
    @ResponseBody
    @GetMapping("/userDetailList")
    @RequiresPermissions("lottery:userBerecords:userMange")
    public PageUtils userDetailList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UserBerecordsDO> userBerecordsList = userBerecordsService.listForUsers(query);
        int total = userBerecordsService.countForUsers(query);
        PageUtils pageUtils = new PageUtils(userBerecordsList, total);
        return pageUtils;
    }

    /**
     * 用户下级列表
     * @return
     */
    @GetMapping("/userChilds")
    @RequiresPermissions("lottery:userBerecords:getChilds")
    public String userChilds(Model model, HttpServletRequest request) {
        String userName = request.getParameter("userName");
        model.addAttribute("userName",userName);
        return "lottery/userBerecords/userChilds";
    }

    @ResponseBody
    @GetMapping("/getChilds")
    @RequiresPermissions("lottery:userBerecords:getChilds")
    public PageUtils getChilds(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        String userName = query.get("userName")+"";
        if(StringUtils.isBlank(userName)){
            return null;
        }
        Long userId = userBerecordsService.getUserInfoByName(userName);
        query.put("userId",userId);
        List<UserBerecordsDO> myChilds = userBerecordsService.getMyChilds(query);
        int total = userBerecordsService.getMyChildsDataCount(query);
        PageUtils pageUtils = new PageUtils(myChilds, total);
        return pageUtils;
    }

    /**
     * 修改用户冻结状态
     */
    @ResponseBody
    @PostMapping("/userEdit")
    @RequiresPermissions("lottery:userBerecords:userEdit")
    public R userEdit(Integer userId) {
        userBerecordsService.updateUserStatus(userId);
        return R.ok();
    }



    @GetMapping("/add")
    @RequiresPermissions("lottery:userBerecords:add")
    String add() {
        return "lottery/userBerecords/add";
    }

    @GetMapping("/edit/{betId}")
    @RequiresPermissions("lottery:userBerecords:edit")
    String edit(@PathVariable("betId") Integer betId, Model model) {
        UserBerecordsDO userBerecords = userBerecordsService.get(betId);
        model.addAttribute("userBerecords", userBerecords);
        return "lottery/userBerecords/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("lottery:userBerecords:add")
    public R save(UserBerecordsDO userBerecords) {
        if (userBerecordsService.save(userBerecords) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("lottery:userBerecords:edit")
    public R update(UserBerecordsDO userBerecords) {
        userBerecordsService.update(userBerecords);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("lottery:userBerecords:remove")
    public R remove(Integer betId) {
        if (userBerecordsService.remove(betId) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("lottery:userBerecords:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] betIds) {
        userBerecordsService.batchRemove(betIds);
        return R.ok();
    }

}
