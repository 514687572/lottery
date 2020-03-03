package com.bootdo.lottery.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户下注记录
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-11-16 17:02:19
 */
public class UserBerecordsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Integer betId;
	//期号
	private Long betNum;
	//用户ID
	private Long userId;
	//用户名
	private String userName;
	//个
	private String lotteryOne;
	//十
	private String lotteryTwo;
	//百
	private String lotteryThree;
	//千
	private String lotteryFour;
	//万
	private String lotteryFive;
	//是否高级选项 1是 0否
	private String highClass;
	//1大 0小
	private String largeNum;
	//1单 0双
	private String lotterySingle;
	//注数
	private Integer noteNum;
	//下注金额
	private Double noteMoney;
	//奖金
	private Double lotteryBonus;
	//下注状态
	private String lotteryStatus;
	//下注时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;
	// 胜率
	private String winning;
	// 冻结状态
	private String status;

	public String getLotteryStatus() {
		return lotteryStatus;
	}

	public void setLotteryStatus(String lotteryStatus) {
		this.lotteryStatus = lotteryStatus;
	}

	public String getWinning() {
		return winning;
	}

	public void setWinning(String winning) {
		this.winning = winning;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 设置：主键
	 */
	public void setBetId(Integer betId) {
		this.betId = betId;
	}
	/**
	 * 获取：主键
	 */
	public Integer getBetId() {
		return betId;
	}
	/**
	 * 设置：期号
	 */
	public void setBetNum(Long betNum) {
		this.betNum = betNum;
	}
	/**
	 * 获取：期号
	 */
	public Long getBetNum() {
		return betNum;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：个
	 */
	public void setLotteryOne(String lotteryOne) {
		this.lotteryOne = lotteryOne;
	}
	/**
	 * 获取：个
	 */
	public String getLotteryOne() {
		return lotteryOne;
	}
	/**
	 * 设置：十
	 */
	public void setLotteryTwo(String lotteryTwo) {
		this.lotteryTwo = lotteryTwo;
	}
	/**
	 * 获取：十
	 */
	public String getLotteryTwo() {
		return lotteryTwo;
	}
	/**
	 * 设置：百
	 */
	public void setLotteryThree(String lotteryThree) {
		this.lotteryThree = lotteryThree;
	}
	/**
	 * 获取：百
	 */
	public String getLotteryThree() {
		return lotteryThree;
	}
	/**
	 * 设置：千
	 */
	public void setLotteryFour(String lotteryFour) {
		this.lotteryFour = lotteryFour;
	}
	/**
	 * 获取：千
	 */
	public String getLotteryFour() {
		return lotteryFour;
	}
	/**
	 * 设置：万
	 */
	public void setLotteryFive(String lotteryFive) {
		this.lotteryFive = lotteryFive;
	}
	/**
	 * 获取：万
	 */
	public String getLotteryFive() {
		return lotteryFive;
	}
	/**
	 * 设置：是否高级选项 1是 0否
	 */
	public void setHighClass(String highClass) {
		this.highClass = highClass;
	}
	/**
	 * 获取：是否高级选项 1是 0否
	 */
	public String getHighClass() {
		return highClass;
	}
	/**
	 * 设置：1大 0小
	 */
	public void setLargeNum(String largeNum) {
		this.largeNum = largeNum;
	}
	/**
	 * 获取：1大 0小
	 */
	public String getLargeNum() {
		return largeNum;
	}
	/**
	 * 设置：1单 0双
	 */
	public void setLotterySingle(String lotterySingle) {
		this.lotterySingle = lotterySingle;
	}
	/**
	 * 获取：1单 0双
	 */
	public String getLotterySingle() {
		return lotterySingle;
	}
	/**
	 * 设置：注数
	 */
	public void setNoteNum(Integer noteNum) {
		this.noteNum = noteNum;
	}
	/**
	 * 获取：注数
	 */
	public Integer getNoteNum() {
		return noteNum;
	}
	/**
	 * 设置：下注金额
	 */
	public void setNoteMoney(Double noteMoney) {
		this.noteMoney = noteMoney;
	}
	/**
	 * 获取：下注金额
	 */
	public Double getNoteMoney() {
		return noteMoney;
	}
	/**
	 * 设置：奖金
	 */
	public void setLotteryBonus(Double lotteryBonus) {
		this.lotteryBonus = lotteryBonus;
	}
	/**
	 * 获取：奖金
	 */
	public Double getLotteryBonus() {
		return lotteryBonus;
	}
	/**
	 * 设置：下注时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：下注时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
