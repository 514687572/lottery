package com.bootdo.common.algorithm;


import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public final class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    
    @Override
    public String doSharding(Collection<String> availableTargetNames,PreciseShardingValue<Integer> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
