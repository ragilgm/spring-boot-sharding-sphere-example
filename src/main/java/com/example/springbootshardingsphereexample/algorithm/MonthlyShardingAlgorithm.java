package com.example.springbootshardingsphereexample.algorithm;

import com.example.springbootshardingsphereexample.constants.ShardingConstant;
import com.google.common.collect.Range;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @Author: Gilang Whisperer
 * Created on 05/03/2024
 */

public class MonthlyShardingAlgorithm implements ComplexKeysShardingAlgorithm<Date> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyShardingAlgorithm.class);

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Date> shardingValue) {
        LOGGER.debug("The potential target table names are: {}", String.join(",", availableTargetNames));
        if (shardingValue.getColumnNameAndShardingValuesMap() != null && shardingValue.getColumnNameAndShardingValuesMap().size() > 0) {
            Map<String, Collection<Date>> shardingColumns = shardingValue.getColumnNameAndShardingValuesMap();
            //根据sharding列以及散列值获得目标表名
            Collection<String> targetNames = availableTargetNames;
            if (shardingColumns.containsKey(ShardingConstant.SHARDING_COLUMN_CREATE_TIME) && !CollectionUtils.isEmpty(shardingColumns.get(ShardingConstant.SHARDING_COLUMN_CREATE_TIME))) {
                Collection<Date> dates = shardingColumns.get(ShardingConstant.SHARDING_COLUMN_CREATE_TIME);
                Set<String> dateStringValues = dates.stream().map(d -> DateFormatUtils.format(d, ShardingConstant.MONTH_DASH_FORMAT)).collect(Collectors.toSet());
                targetNames = targetNames.stream().filter(t -> dateStringValues.contains(t.replace(shardingValue.getLogicTableName() + "_", ""))).collect(Collectors.toSet());
            }
            //返回目标值
            LOGGER.debug("The target table names are: {}", String.join(",", targetNames));
            return targetNames;
        } else if (shardingValue.getColumnNameAndRangeValuesMap() != null && shardingValue.getColumnNameAndRangeValuesMap().size() > 0) {
            Map<String, Range<Date>> shardingColumns = shardingValue.getColumnNameAndRangeValuesMap();
            //根据sharding列以及散列值获得目标表名
            Collection<String> targetNames = availableTargetNames;
            if (shardingColumns.containsKey(ShardingConstant.SHARDING_COLUMN_CREATE_TIME)) {
                Range<Date> dateRange = shardingColumns.get(ShardingConstant.SHARDING_COLUMN_CREATE_TIME);
                Integer lowerEndpoint = dateRange.hasLowerBound() ? Integer.valueOf(DateFormatUtils.format(dateRange.lowerEndpoint(), ShardingConstant.MONTH_FORMAT)) : null;
                Integer upperEndpoint = dateRange.hasUpperBound() ? Integer.valueOf(DateFormatUtils.format(dateRange.upperEndpoint(), ShardingConstant.MONTH_FORMAT)) : null;
                LOGGER.debug("Range lower endpoint is {}", lowerEndpoint);
                LOGGER.debug("Range upper endpoint is {}", upperEndpoint);

                targetNames = targetNames.stream().filter(t -> {
                    Integer val = Integer.valueOf(t.replace(shardingValue.getLogicTableName(), "").replace("_", ""));
                    LOGGER.debug("table {} caculated value is {}", t, val);
                    return (lowerEndpoint == null || val >= lowerEndpoint) && (upperEndpoint == null || val <= upperEndpoint);
                }).collect(Collectors.toSet());

                //返回目标值
                LOGGER.debug("The target table names are: {}", String.join(",", targetNames));
                return targetNames;
            }
        }
        return availableTargetNames;
    }
}
