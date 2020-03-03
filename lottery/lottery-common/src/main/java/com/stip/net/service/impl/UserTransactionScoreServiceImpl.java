package com.stip.net.service.impl;

import com.stip.mybatis.generator.plugin.BaseService;
import com.stip.net.entity.UserTransactionScore;
import com.stip.net.example.UserTransactionScoreExample;
import com.stip.net.service.UserTransactionScoreService;
import org.springframework.stereotype.Service;

@Service
public class UserTransactionScoreServiceImpl extends BaseService<UserTransactionScore, UserTransactionScoreExample, Integer> implements UserTransactionScoreService {
}