package com.xiaojukeji.chronos.config;


import com.xiaojukeji.carrera.config.ConfigurationValidator;

/**
 * zkConfig:
 *   zkAddrs : "127.0.0.1:2181"
 *   zkSessionTimeoutMs : 30000
 *   masterPathPrefix : "/chronos/master"
 *   metaPathPrefix : "/chronos/meta"
 *   offsetsProp : "offsets"
 *   seekTimestampProp : "seektimestamp"
 *   baseSleepTimeMs : 10   #baseSleepTimeMs initial amount of time to wait between retries
 *   maxSleepMs : 1000      #maxSleepMs max time in ms to sleep on each retry
 *   maxRetries : 5         #maxRetries max number of times to retry
 */
public class ZkConfig implements ConfigurationValidator {
    private String zkAddrs;
    private int zkSessionTimeoutMs;
    private String masterPathPrefix;
    private String metaPathPrefix;
    private String offsetsProp;
    private String seekTimestampProp;
    private int baseSleepTimeMs;
    private int maxSleepMs;
    private int maxRetries;

    public String getZkAddrs() {
        return zkAddrs;
    }

    public void setZkAddrs(String zkAddrs) {
        this.zkAddrs = zkAddrs;
    }

    public int getZkSessionTimeoutMs() {
        return zkSessionTimeoutMs;
    }

    public void setZkSessionTimeoutMs(int zkSessionTimeoutMs) {
        this.zkSessionTimeoutMs = zkSessionTimeoutMs;
    }

    public String getMasterPathPrefix() {
        return masterPathPrefix;
    }

    public void setMasterPathPrefix(String masterPathPrefix) {
        this.masterPathPrefix = masterPathPrefix;
    }

    public String getMetaPathPrefix() {
        return metaPathPrefix;
    }

    public void setMetaPathPrefix(String metaPathPrefix) {
        this.metaPathPrefix = metaPathPrefix;
    }

    public String getOffsetsProp() {
        return offsetsProp;
    }

    public void setOffsetsProp(String offsetsProp) {
        this.offsetsProp = offsetsProp;
    }

    public String getSeekTimestampProp() {
        return seekTimestampProp;
    }

    public void setSeekTimestampProp(String seekTimestampProp) {
        this.seekTimestampProp = seekTimestampProp;
    }

    public int getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(int baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public int getMaxSleepMs() {
        return maxSleepMs;
    }

    public void setMaxSleepMs(int maxSleepMs) {
        this.maxSleepMs = maxSleepMs;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public String toString() {
        return "ZkConfig{" +
                "zkAddrs='" + zkAddrs + '\'' +
                ", zkSessionTimeoutMs=" + zkSessionTimeoutMs +
                ", masterPathPrefix='" + masterPathPrefix + '\'' +
                ", metaPathPrefix='" + metaPathPrefix + '\'' +
                ", offsetsProp='" + offsetsProp + '\'' +
                ", seekTimestampProp='" + seekTimestampProp + '\'' +
                ", baseSleepTimeMs=" + baseSleepTimeMs +
                ", maxSleepMs=" + maxSleepMs +
                ", maxRetries=" + maxRetries +
                '}';
    }
}