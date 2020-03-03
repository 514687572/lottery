package com.stip.net.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页控制
 *
 * @author zl
 */
@Scope("request")
@RestController
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class IndexController {

	/**
	 * PC端首页
	 */
	@GetMapping("/")
	public ModelAndView toindex(HttpServletRequest request) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/welcome", resultMap);
	}

	/**
	 * PC端时时彩
	 */
	@GetMapping("/lottery")
	public ModelAndView toLottery(HttpServletRequest request) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/index", resultMap);
	}

	/**
	 * PC端骰子
	 */
	@GetMapping("/dice")
	public ModelAndView toDice(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/EOS_DICE/html/theDiceHome", resultMap);
	}

	/**
	 * PC端龙虎斗
	 */
	@GetMapping("/tiger")
	public ModelAndView toTiger(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/eosdtplay/tiger/home", resultMap);
	}

	/**
	 * PC端提现
	 */
	@GetMapping("/cashOut")
	public ModelAndView toCashOut(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/cashOut", resultMap);
	}

	/**
	 * PC端充值
	 */
	@GetMapping("/pay")
	public ModelAndView toPay(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/pay", resultMap);
	}

	/**
	 * PC端注册
	 */
	@GetMapping("/register")
	public ModelAndView toRegister(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/register", resultMap);
	}

	/**
	 * 移动端首页
	 */
	@GetMapping("/wap")
	public ModelAndView wap(HttpServletRequest request) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/wapWelcome", resultMap);
	}

	/**
	 * 移动端时时彩
	 */
	@GetMapping("/wap/lottery")
	public ModelAndView toLottery(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/wapIndex", resultMap);
	}

	/**
	 * 移动端骰子
	 */
	@GetMapping("/wap/dice")
	public ModelAndView toWapDice(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/EOS_DICE/html/wapDiceHome", resultMap);
	}

	/**
	 * 移动端龙虎斗
	 */
	@GetMapping("/wap/tiger")
	public ModelAndView toWapTiger(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/eosdtplay/tiger/wapHome", resultMap);
	}

	/**
	 * 移动端充值
	 */
	@GetMapping("/wap/payfor")
	public ModelAndView toPayFor(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/wapPayfor", resultMap);
	}

	/**
	 * 移动端注册
	 */
	@GetMapping("/wap/registerstep")
	public ModelAndView toRegisterStep(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/wapRegisterOneStep", resultMap);
	}

	/**
	 * 移动端提现
	 */
	@GetMapping("/wap/cashWithdrawal")
	public ModelAndView toCashWithdrawal(HttpServletRequest request, HttpServletResponse reponse) {
		String userCode = request.getParameter("userCode");
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put("userCode", userCode);
		return new ModelAndView("/html/CashWithdrawal", resultMap);
	}
}