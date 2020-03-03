package com.stip.net.example;

import com.stip.mybatis.generator.plugin.BaseCriteria;
import com.stip.mybatis.generator.plugin.BaseModelExample;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LotteryExcExample extends BaseModelExample {
    protected List<Criteria> oredCriteria;

    public LotteryExcExample() {
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

        public Criteria andExcIdIsNull() {
            addCriterion("LotteryExc.exc_id is null");
            return (Criteria) this;
        }

        public Criteria andExcIdIsNotNull() {
            addCriterion("LotteryExc.exc_id is not null");
            return (Criteria) this;
        }

        public Criteria andExcIdEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_id =", value, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdNotEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_id <>", value, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdGreaterThan(Integer value) {
            addCriterion("LotteryExc.exc_id >", value, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_id >=", value, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdLessThan(Integer value) {
            addCriterion("LotteryExc.exc_id <", value, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdLessThanOrEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_id <=", value, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdIn(List<Integer> values) {
            addCriterion("LotteryExc.exc_id in", values, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdNotIn(List<Integer> values) {
            addCriterion("LotteryExc.exc_id not in", values, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdBetween(Integer value1, Integer value2) {
            addCriterion("LotteryExc.exc_id between", value1, value2, "excId");
            return (Criteria) this;
        }

        public Criteria andExcIdNotBetween(Integer value1, Integer value2) {
            addCriterion("LotteryExc.exc_id not between", value1, value2, "excId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("LotteryExc.user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("LotteryExc.user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("LotteryExc.user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("LotteryExc.user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("LotteryExc.user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("LotteryExc.user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("LotteryExc.user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("LotteryExc.user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("LotteryExc.user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("LotteryExc.user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("LotteryExc.user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("LotteryExc.user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("LotteryExc.user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("LotteryExc.user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andIsplusIsNull() {
            addCriterion("LotteryExc.isPlus is null");
            return (Criteria) this;
        }

        public Criteria andIsplusIsNotNull() {
            addCriterion("LotteryExc.isPlus is not null");
            return (Criteria) this;
        }

        public Criteria andIsplusEqualTo(Integer value) {
            addCriterion("LotteryExc.isPlus =", value, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusNotEqualTo(Integer value) {
            addCriterion("LotteryExc.isPlus <>", value, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusGreaterThan(Integer value) {
            addCriterion("LotteryExc.isPlus >", value, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusGreaterThanOrEqualTo(Integer value) {
            addCriterion("LotteryExc.isPlus >=", value, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusLessThan(Integer value) {
            addCriterion("LotteryExc.isPlus <", value, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusLessThanOrEqualTo(Integer value) {
            addCriterion("LotteryExc.isPlus <=", value, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusIn(List<Integer> values) {
            addCriterion("LotteryExc.isPlus in", values, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusNotIn(List<Integer> values) {
            addCriterion("LotteryExc.isPlus not in", values, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusBetween(Integer value1, Integer value2) {
            addCriterion("LotteryExc.isPlus between", value1, value2, "isplus");
            return (Criteria) this;
        }

        public Criteria andIsplusNotBetween(Integer value1, Integer value2) {
            addCriterion("LotteryExc.isPlus not between", value1, value2, "isplus");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("LotteryExc.amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("LotteryExc.amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("LotteryExc.amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("LotteryExc.amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("LotteryExc.amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LotteryExc.amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("LotteryExc.amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LotteryExc.amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("LotteryExc.amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("LotteryExc.amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LotteryExc.amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LotteryExc.amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andExcStatusIsNull() {
            addCriterion("LotteryExc.exc_status is null");
            return (Criteria) this;
        }

        public Criteria andExcStatusIsNotNull() {
            addCriterion("LotteryExc.exc_status is not null");
            return (Criteria) this;
        }

        public Criteria andExcStatusEqualTo(String value) {
            addCriterion("LotteryExc.exc_status =", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusNotEqualTo(String value) {
            addCriterion("LotteryExc.exc_status <>", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusGreaterThan(String value) {
            addCriterion("LotteryExc.exc_status >", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusGreaterThanOrEqualTo(String value) {
            addCriterion("LotteryExc.exc_status >=", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusLessThan(String value) {
            addCriterion("LotteryExc.exc_status <", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusLessThanOrEqualTo(String value) {
            addCriterion("LotteryExc.exc_status <=", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusLike(String value) {
            addCriterion("LotteryExc.exc_status like", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusNotLike(String value) {
            addCriterion("LotteryExc.exc_status not like", value, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusIn(List<String> values) {
            addCriterion("LotteryExc.exc_status in", values, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusNotIn(List<String> values) {
            addCriterion("LotteryExc.exc_status not in", values, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusBetween(String value1, String value2) {
            addCriterion("LotteryExc.exc_status between", value1, value2, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcStatusNotBetween(String value1, String value2) {
            addCriterion("LotteryExc.exc_status not between", value1, value2, "excStatus");
            return (Criteria) this;
        }

        public Criteria andExcCountIsNull() {
            addCriterion("LotteryExc.exc_count is null");
            return (Criteria) this;
        }

        public Criteria andExcCountIsNotNull() {
            addCriterion("LotteryExc.exc_count is not null");
            return (Criteria) this;
        }

        public Criteria andExcCountEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_count =", value, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountNotEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_count <>", value, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountGreaterThan(Integer value) {
            addCriterion("LotteryExc.exc_count >", value, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_count >=", value, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountLessThan(Integer value) {
            addCriterion("LotteryExc.exc_count <", value, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountLessThanOrEqualTo(Integer value) {
            addCriterion("LotteryExc.exc_count <=", value, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountIn(List<Integer> values) {
            addCriterion("LotteryExc.exc_count in", values, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountNotIn(List<Integer> values) {
            addCriterion("LotteryExc.exc_count not in", values, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountBetween(Integer value1, Integer value2) {
            addCriterion("LotteryExc.exc_count between", value1, value2, "excCount");
            return (Criteria) this;
        }

        public Criteria andExcCountNotBetween(Integer value1, Integer value2) {
            addCriterion("LotteryExc.exc_count not between", value1, value2, "excCount");
            return (Criteria) this;
        }

        public Criteria andGameTypeIsNull() {
            addCriterion("LotteryExc.game_type is null");
            return (Criteria) this;
        }

        public Criteria andGameTypeIsNotNull() {
            addCriterion("LotteryExc.game_type is not null");
            return (Criteria) this;
        }

        public Criteria andGameTypeEqualTo(String value) {
            addCriterion("LotteryExc.game_type =", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotEqualTo(String value) {
            addCriterion("LotteryExc.game_type <>", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeGreaterThan(String value) {
            addCriterion("LotteryExc.game_type >", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeGreaterThanOrEqualTo(String value) {
            addCriterion("LotteryExc.game_type >=", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeLessThan(String value) {
            addCriterion("LotteryExc.game_type <", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeLessThanOrEqualTo(String value) {
            addCriterion("LotteryExc.game_type <=", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeLike(String value) {
            addCriterion("LotteryExc.game_type like", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotLike(String value) {
            addCriterion("LotteryExc.game_type not like", value, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeIn(List<String> values) {
            addCriterion("LotteryExc.game_type in", values, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotIn(List<String> values) {
            addCriterion("LotteryExc.game_type not in", values, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeBetween(String value1, String value2) {
            addCriterion("LotteryExc.game_type between", value1, value2, "gameType");
            return (Criteria) this;
        }

        public Criteria andGameTypeNotBetween(String value1, String value2) {
            addCriterion("LotteryExc.game_type not between", value1, value2, "gameType");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("LotteryExc.remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("LotteryExc.remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("LotteryExc.remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("LotteryExc.remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("LotteryExc.remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("LotteryExc.remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("LotteryExc.remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("LotteryExc.remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("LotteryExc.remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("LotteryExc.remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("LotteryExc.remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("LotteryExc.remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("LotteryExc.remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("LotteryExc.remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}