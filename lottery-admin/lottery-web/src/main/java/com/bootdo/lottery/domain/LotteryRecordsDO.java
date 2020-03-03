package com.bootdo.lottery.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 开奖记录
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-11-16 17:02:19
 */
public class LotteryRecordsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Integer recordsId;
	//期号
	private Long betNum;
	//个
	private Integer lotteryOne;
	//十
	private Integer lotteryTwo;
	//百
	private Integer lotteryThree;
	//千
	private Integer lotteryFour;
	//万
	private Integer lotteryFive;
	//1大 0小
	private String largeNum;
	//1单 0双
	private String lotterySingle;
	//第一个hash值
	private String hashOne;
	//第2个hash值
	private String hashTwo;
	//第3个hash值
	private String hashThree;
	//第4个hash值
	private String hashFour;
	//第5个hash值
	private String hashFive;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;

	/**
	 * 设置：主键
	 */
	public void setRecordsId(Integer recordsId) {
		this.recordsId = recordsId;
	}
	/**
	 * 获取：主键
	 */
	public Integer getRecordsId() {
		return recordsId;
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
	 * 设置：个
	 */
	public void setLotteryOne(Integer lotteryOne) {
		this.lotteryOne = lotteryOne;
	}
	/**
	 * 获取：个
	 */
	public Integer getLotteryOne() {
		return lotteryOne;
	}
	/**
	 * 设置：十
	 */
	public void setLotteryTwo(Integer lotteryTwo) {
		this.lotteryTwo = lotteryTwo;
	}
	/**
	 * 获取：十
	 */
	public Integer getLotteryTwo() {
		return lotteryTwo;
	}
	/**
	 * 设置：百
	 */
	public void setLotteryThree(Integer lotteryThree) {
		this.lotteryThree = lotteryThree;
	}
	/**
	 * 获取：百
	 */
	public Integer getLotteryThree() {
		return lotteryThree;
	}
	/**
	 * 设置：千
	 */
	public void setLotteryFour(Integer lotteryFour) {
		this.lotteryFour = lotteryFour;
	}
	/**
	 * 获取：千
	 */
	public Integer getLotteryFour() {
		return lotteryFour;
	}
	/**
	 * 设置：万
	 */
	public void setLotteryFive(Integer lotteryFive) {
		this.lotteryFive = lotteryFive;
	}
	/**
	 * 获取：万
	 */
	public Integer getLotteryFive() {
		return lotteryFive;
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
	 * 设置：第一个hash值
	 */
	public void setHashOne(String hashOne) {
		this.hashOne = hashOne;
	}
	/**
	 * 获取：第一个hash值
	 */
	public String getHashOne() {
		return hashOne;
	}
	/**
	 * 设置：第2个hash值
	 */
	public void setHashTwo(String hashTwo) {
		this.hashTwo = hashTwo;
	}
	/**
	 * 获取：第2个hash值
	 */
	public String getHashTwo() {
		return hashTwo;
	}
	/**
	 * 设置：第3个hash值
	 */
	public void setHashThree(String hashThree) {
		this.hashThree = hashThree;
	}
	/**
	 * 获取：第3个hash值
	 */
	public String getHashThree() {
		return hashThree;
	}
	/**
	 * 设置：第4个hash值
	 */
	public void setHashFour(String hashFour) {
		this.hashFour = hashFour;
	}
	/**
	 * 获取：第4个hash值
	 */
	public String getHashFour() {
		return hashFour;
	}
	/**
	 * 设置：第5个hash值
	 */
	public void setHashFive(String hashFive) {
		this.hashFive = hashFive;
	}
	/**
	 * 获取：第5个hash值
	 */
	public String getHashFive() {
		return hashFive;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
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
