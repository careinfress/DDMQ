package com.xiaojukeji.chronos.ha;

import com.xiaojukeji.chronos.enums.ServerState;
import com.xiaojukeji.chronos.services.MetaService;
import com.xiaojukeji.chronos.utils.LogUtils;
import com.xiaojukeji.chronos.utils.ZkUtils;
import com.xiaojukeji.chronos.utils.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class MasterElection {
    private static final Logger SWITCH_LOGGER = LogUtils.SWITCH_LOGGER;

    private static volatile ServerState state = ServerState.BACKUPING;

    public static void election(final CountDownLatch cdl) {
        final CuratorFramework client = ZkUtils.getCuratorClient();
        /**
         * MASTER_PATH = /chronos/master/test/group_0
         * 创建LeaderSelector实例（用于Leader选举）
         * MASTER_PATH 是 leaderPath，Leader 节点会成功创建该节点（其他节点则会失败）
         * listener是该实例的监听器
         */
        final LeaderSelector selector = new LeaderSelector(client, Constants.MASTER_PATH, new LeaderSelectorListenerAdapter() {
            /**
             * 被选举为Leader时调用
             * 该方法结束后会释放领导权，即重新进行Leader选举（还有节点的情况下）
             */
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                SWITCH_LOGGER.info("take master leadership");

                long seekTimestamp = MetaService.getSeekTimestamp();
                long zkSeekTimestamp = MetaService.getZkSeekTimestamp();
                final long sleepMs = 200;
                long sleepCount = 0;
                // 如果zk上的数据丢失了, 则zkSeekTimestamp为0, 此时chronos则被block住
                while (seekTimestamp < zkSeekTimestamp && zkSeekTimestamp > 0) {
                    SWITCH_LOGGER.info("sleep {}ms to wait seekTimestamp:{} to catch up with zkSeekTimestamp:{}",
                            sleepMs, seekTimestamp, zkSeekTimestamp);
                    TimeUnit.MILLISECONDS.sleep(sleepMs);
                    seekTimestamp = MetaService.getSeekTimestamp();
                    zkSeekTimestamp = MetaService.getZkSeekTimestamp();
                    sleepCount++;
                }

                state = ServerState.MASTERING;
                SWITCH_LOGGER.info("change server state to {}, totalSleepMs:{}ms", state, sleepCount * sleepMs);
                // 因为这个方法结束的时候就会重新进入选举，所以需要在这里阻塞
                cdl.await();
                state = ServerState.BACKUPING;
                SWITCH_LOGGER.info("release master leadership");
            }

            // 当连接状态发生变化时调用
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                super.stateChanged(client, newState);
            }
        });
        // 默认情况下，当LeaderSelectorListener.takeLeadership(CuratorFramework)返回时，此实例不会重新排队
        // 调用此方法会将实例置于一种自动重新排队的模式
        selector.autoRequeue();
        // 开始Leader选举
        selector.start();
    }

    public static boolean isMaster() {
        return state == ServerState.MASTERING;
    }

    public static boolean isBackup() {
        return state == ServerState.BACKUPING;
    }

    public static void standAlone() {
        state = ServerState.MASTERING;
    }

    public static ServerState getState() {
        return state;
    }
}