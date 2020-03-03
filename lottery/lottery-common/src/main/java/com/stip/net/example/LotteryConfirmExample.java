package com.stip.net.example;

import java.util.ArrayList;
import java.util.List;

import com.stip.mybatis.generator.plugin.BaseCriteria;
import com.stip.mybatis.generator.plugin.BaseModelExample;

public class LotteryConfirmExample extends BaseModelExample {
    protected List<Criteria> oredCriteria;

    public LotteryConfirmExample() {
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

        public Criteria andConfirmIdIsNull() {
            addCriterion("lotteryConfirm.confirm_id is null");
            return (Criteria) this;
        }

        public Criteria andConfirmIdIsNotNull() {
            addCriterion("lotteryConfirm.confirm_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmIdEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_id =", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdNotEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_id <>", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdGreaterThan(String value) {
            addCriterion("lotteryConfirm.confirm_id >", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdGreaterThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_id >=", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdLessThan(String value) {
            addCriterion("lotteryConfirm.confirm_id <", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdLessThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_id <=", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdLike(String value) {
            addCriterion("lotteryConfirm.confirm_id like", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdNotLike(String value) {
            addCriterion("lotteryConfirm.confirm_id not like", value, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdIn(List<String> values) {
            addCriterion("lotteryConfirm.confirm_id in", values, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdNotIn(List<String> values) {
            addCriterion("lotteryConfirm.confirm_id not in", values, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.confirm_id between", value1, value2, "confirmId");
            return (Criteria) this;
        }

        public Criteria andConfirmIdNotBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.confirm_id not between", value1, value2, "confirmId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIsNull() {
            addCriterion("lotteryConfirm.transaction_id is null");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIsNotNull() {
            addCriterion("lotteryConfirm.transaction_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionIdEqualTo(String value) {
            addCriterion("lotteryConfirm.transaction_id =", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotEqualTo(String value) {
            addCriterion("lotteryConfirm.transaction_id <>", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdGreaterThan(String value) {
            addCriterion("lotteryConfirm.transaction_id >", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdGreaterThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.transaction_id >=", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLessThan(String value) {
            addCriterion("lotteryConfirm.transaction_id <", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLessThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.transaction_id <=", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLike(String value) {
            addCriterion("lotteryConfirm.transaction_id like", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotLike(String value) {
            addCriterion("lotteryConfirm.transaction_id not like", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIn(List<String> values) {
            addCriterion("lotteryConfirm.transaction_id in", values, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotIn(List<String> values) {
            addCriterion("lotteryConfirm.transaction_id not in", values, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.transaction_id between", value1, value2, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.transaction_id not between", value1, value2, "transactionId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("lotteryConfirm.user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("lotteryConfirm.user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("lotteryConfirm.user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("lotteryConfirm.user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("lotteryConfirm.user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("lotteryConfirm.user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("lotteryConfirm.user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("lotteryConfirm.user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("lotteryConfirm.user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("lotteryConfirm.user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andGameTypeIsNull() {
            addCriterion("lotteryConfirm.game_type is null");
            return (Criteria) this;
        }

        public Criteria andGameTypeIsNotNull() {
            addCriterion("lotteryConfirm.game_type is not null");
            return (Criteria) this;
        }

        public Criteria andGameTypeEqualTo(String value) {
            addCriterion("lotteryConfirm.game_type =", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotEqualTo(String value) {
            addCriterion("lotteryConfirm.game_type <>", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeGreaterThan(String value) {
            addCriterion("lotteryConfirm.game_type >", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeGreaterThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.game_type >=", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeLessThan(String value) {
            addCriterion("lotteryConfirm.game_type <", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeLessThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.game_type <=", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeLike(String value) {
            addCriterion("lotteryConfirm.game_type like", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotLike(String value) {
            addCriterion("lotteryConfirm.game_type not like", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeIn(List<String> values) {
            addCriterion("lotteryConfirm.game_type in", values, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotIn(List<String> values) {
            addCriterion("lotteryConfirm.game_type not in", values, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.game_type between", value1, value2, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.game_type not between", value1, value2, "gameType");
            return (Criteria) this;
        }

        public Criteria andConfirmExtIsNull() {
            addCriterion("lotteryConfirm.confirm_ext is null");
            return (Criteria) this;
        }

        public Criteria andConfirmExtIsNotNull() {
            addCriterion("lotteryConfirm.confirm_ext is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmExtEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_ext =", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtNotEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_ext <>", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtGreaterThan(String value) {
            addCriterion("lotteryConfirm.confirm_ext >", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtGreaterThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_ext >=", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtLessThan(String value) {
            addCriterion("lotteryConfirm.confirm_ext <", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtLessThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.confirm_ext <=", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtLike(String value) {
            addCriterion("lotteryConfirm.confirm_ext like", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtNotLike(String value) {
            addCriterion("lotteryConfirm.confirm_ext not like", value, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtIn(List<String> values) {
            addCriterion("lotteryConfirm.confirm_ext in", values, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtNotIn(List<String> values) {
            addCriterion("lotteryConfirm.confirm_ext not in", values, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.confirm_ext between", value1, value2, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andConfirmExtNotBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.confirm_ext not between", value1, value2, "confirmExt");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("lotteryConfirm.remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("lotteryConfirm.remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("lotteryConfirm.remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("lotteryConfirm.remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("lotteryConfirm.remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("lotteryConfirm.remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("lotteryConfirm.remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("lotteryConfirm.remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("lotteryConfirm.remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("lotteryConfirm.remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("lotteryConfirm.remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("lotteryConfirm.remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}