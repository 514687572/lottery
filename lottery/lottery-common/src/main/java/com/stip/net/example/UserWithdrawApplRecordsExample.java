package com.stip.net.example;

import com.stip.mybatis.generator.plugin.BaseCriteria;
import com.stip.mybatis.generator.plugin.BaseModelExample;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserWithdrawApplRecordsExample extends BaseModelExample {
    protected List<Criteria> oredCriteria;

    public UserWithdrawApplRecordsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        super.clear();
        oredCriteria.clear();
    }

    protected abstract static class GeneratedCriteria extends BaseCriteria {

        public Criteria andInorderidIsNull() {
            addCriterion("UserWithdrawApplRecords.inOrderId is null");
            return (Criteria) this;
        }

        public Criteria andInorderidIsNotNull() {
            addCriterion("UserWithdrawApplRecords.inOrderId is not null");
            return (Criteria) this;
        }

        public Criteria andInorderidEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId =", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidNotEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId <>", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidGreaterThan(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId >", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidGreaterThanOrEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId >=", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidLessThan(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId <", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidLessThanOrEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId <=", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidLike(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId like", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidNotLike(String value) {
            addCriterion("UserWithdrawApplRecords.inOrderId not like", value, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidIn(List<String> values) {
            addCriterion("UserWithdrawApplRecords.inOrderId in", values, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidNotIn(List<String> values) {
            addCriterion("UserWithdrawApplRecords.inOrderId not in", values, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidBetween(String value1, String value2) {
            addCriterion("UserWithdrawApplRecords.inOrderId between", value1, value2, "inorderid");
            return (Criteria) this;
        }

        public Criteria andInorderidNotBetween(String value1, String value2) {
            addCriterion("UserWithdrawApplRecords.inOrderId not between", value1, value2, "inorderid");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("UserWithdrawApplRecords.uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("UserWithdrawApplRecords.uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("UserWithdrawApplRecords.uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("UserWithdrawApplRecords.uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("UserWithdrawApplRecords.uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("UserWithdrawApplRecords.uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("UserWithdrawApplRecords.uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("UserWithdrawApplRecords.uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("UserWithdrawApplRecords.uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("UserWithdrawApplRecords.uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("UserWithdrawApplRecords.uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("UserWithdrawApplRecords.uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andEosPriceIsNull() {
            addCriterion("UserWithdrawApplRecords.eos_price is null");
            return (Criteria) this;
        }

        public Criteria andEosPriceIsNotNull() {
            addCriterion("UserWithdrawApplRecords.eos_price is not null");
            return (Criteria) this;
        }

        public Criteria andEosPriceEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.eos_price =", value, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceNotEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.eos_price <>", value, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceGreaterThan(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.eos_price >", value, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.eos_price >=", value, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceLessThan(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.eos_price <", value, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.eos_price <=", value, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceIn(List<BigDecimal> values) {
            addCriterion("UserWithdrawApplRecords.eos_price in", values, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceNotIn(List<BigDecimal> values) {
            addCriterion("UserWithdrawApplRecords.eos_price not in", values, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UserWithdrawApplRecords.eos_price between", value1, value2, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andEosPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UserWithdrawApplRecords.eos_price not between", value1, value2, "eosPrice");
            return (Criteria) this;
        }

        public Criteria andRechargeNumIsNull() {
            addCriterion("UserWithdrawApplRecords.recharge_num is null");
            return (Criteria) this;
        }

        public Criteria andRechargeNumIsNotNull() {
            addCriterion("UserWithdrawApplRecords.recharge_num is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeNumEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_num =", value, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumNotEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_num <>", value, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumGreaterThan(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_num >", value, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_num >=", value, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumLessThan(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_num <", value, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumLessThanOrEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_num <=", value, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumIn(List<Integer> values) {
            addCriterion("UserWithdrawApplRecords.recharge_num in", values, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumNotIn(List<Integer> values) {
            addCriterion("UserWithdrawApplRecords.recharge_num not in", values, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumBetween(Integer value1, Integer value2) {
            addCriterion("UserWithdrawApplRecords.recharge_num between", value1, value2, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("UserWithdrawApplRecords.recharge_num not between", value1, value2, "rechargeNum");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeIsNull() {
            addCriterion("UserWithdrawApplRecords.recharge_type is null");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeIsNotNull() {
            addCriterion("UserWithdrawApplRecords.recharge_type is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_type =", value, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeNotEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_type <>", value, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeGreaterThan(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_type >", value, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_type >=", value, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeLessThan(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_type <", value, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_type <=", value, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeIn(List<Integer> values) {
            addCriterion("UserWithdrawApplRecords.recharge_type in", values, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeNotIn(List<Integer> values) {
            addCriterion("UserWithdrawApplRecords.recharge_type not in", values, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeBetween(Integer value1, Integer value2) {
            addCriterion("UserWithdrawApplRecords.recharge_type between", value1, value2, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("UserWithdrawApplRecords.recharge_type not between", value1, value2, "rechargeType");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusIsNull() {
            addCriterion("UserWithdrawApplRecords.recharge_status is null");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusIsNotNull() {
            addCriterion("UserWithdrawApplRecords.recharge_status is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_status =", value, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusNotEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_status <>", value, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusGreaterThan(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_status >", value, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_status >=", value, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusLessThan(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_status <", value, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("UserWithdrawApplRecords.recharge_status <=", value, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusIn(List<Integer> values) {
            addCriterion("UserWithdrawApplRecords.recharge_status in", values, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusNotIn(List<Integer> values) {
            addCriterion("UserWithdrawApplRecords.recharge_status not in", values, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusBetween(Integer value1, Integer value2) {
            addCriterion("UserWithdrawApplRecords.recharge_status between", value1, value2, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andRechargeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("UserWithdrawApplRecords.recharge_status not between", value1, value2, "rechargeStatus");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("UserWithdrawApplRecords.num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("UserWithdrawApplRecords.num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("UserWithdrawApplRecords.num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<BigDecimal> values) {
            addCriterion("UserWithdrawApplRecords.num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<BigDecimal> values) {
            addCriterion("UserWithdrawApplRecords.num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UserWithdrawApplRecords.num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UserWithdrawApplRecords.num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("UserWithdrawApplRecords.time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("UserWithdrawApplRecords.time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("UserWithdrawApplRecords.time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("UserWithdrawApplRecords.time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("UserWithdrawApplRecords.time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("UserWithdrawApplRecords.time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("UserWithdrawApplRecords.time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("UserWithdrawApplRecords.time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UserWithdrawApplRecords.update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UserWithdrawApplRecords.update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UserWithdrawApplRecords.update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UserWithdrawApplRecords.update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UserWithdrawApplRecords.update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UserWithdrawApplRecords.update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UserWithdrawApplRecords.update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UserWithdrawApplRecords.update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UserWithdrawApplRecords.update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("UserWithdrawApplRecords.remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("UserWithdrawApplRecords.remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("UserWithdrawApplRecords.remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("UserWithdrawApplRecords.remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("UserWithdrawApplRecords.remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("UserWithdrawApplRecords.remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("UserWithdrawApplRecords.remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("UserWithdrawApplRecords.remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("UserWithdrawApplRecords.remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("UserWithdrawApplRecords.remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("UserWithdrawApplRecords.remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}