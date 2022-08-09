package com.xiaojukeji.chronos.utils;

import com.google.common.base.Joiner;
import com.xiaojukeji.chronos.config.ChronosConfig;
import com.xiaojukeji.chronos.config.ConfigManager;
import com.xiaojukeji.chronos.config.ZkConfig;


public class Constants {
    private static final ChronosConfig chronosConfig = ConfigManager.getConfig();
    private static final ZkConfig zkConfig = chronosConfig.getZkConfig();
    // zkConfig.getMetaPathPrefix() = /chronos/meta
    // chronosConfig.getClusterName() = test
    // chronosConfig.getGroupName() = group_0
    // META_BASE_ZK_PATH = /chronos/meta/test/group_0
    public static final String META_BASE_ZK_PATH = Joiner.on("/").join(zkConfig.getMetaPathPrefix(), chronosConfig.getClusterName(), chronosConfig.getGroupName());
    // SEEK_TIMESTAMP_ZK_PATH = /chronos/meta/test/group_0/
    public static final String SEEK_TIMESTAMP_ZK_PATH = Joiner.on("/").join(META_BASE_ZK_PATH, zkConfig.getSeekTimestampProp());
    // OFFSET_ZK_PATH = /chronos/meta/test/group_0/offsets/seektimestamp
    public static final String OFFSET_ZK_PATH = Joiner.on("/").join(META_BASE_ZK_PATH, zkConfig.getOffsetsProp());
    // MASTER_PATH = /chronos/master/test/group_0
    public static final String MASTER_PATH = Joiner.on("/").join(zkConfig.getMasterPathPrefix(), chronosConfig.getClusterName(), chronosConfig.getGroupName());

    // 为了迎合rocksdb的默认排序
    public static final int SEGMENT_INDEX_BASE = 10000;
}