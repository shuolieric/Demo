package com.ls.demo.flink.coordinator.hotpin.componet;

import org.apache.flink.metrics.groups.OperatorMetricGroup;
import org.apache.flink.runtime.checkpoint.CheckpointOptions;
import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.state.CheckpointStreamFactory;
import org.apache.flink.streaming.api.operators.OperatorSnapshotFutures;
import org.apache.flink.streaming.api.operators.StreamOperator;
import org.apache.flink.streaming.api.operators.StreamTaskStateInitializer;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;

public class PinProcessOperator implements StreamOperator<String> {
    @Override
    public void open() throws Exception {

    }

    @Override
    public void finish() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void prepareSnapshotPreBarrier(long checkpointId) throws Exception {

    }

    @Override
    public OperatorSnapshotFutures snapshotState(long checkpointId, long timestamp, CheckpointOptions checkpointOptions, CheckpointStreamFactory storageLocation) throws Exception {
        return null;
    }

    @Override
    public void initializeState(StreamTaskStateInitializer streamTaskStateManager) throws Exception {

    }

    @Override
    public void setKeyContextElement1(StreamRecord<?> record) throws Exception {

    }

    @Override
    public void setKeyContextElement2(StreamRecord<?> record) throws Exception {

    }

    @Override
    public OperatorMetricGroup getMetricGroup() {
        return null;
    }

    @Override
    public OperatorID getOperatorID() {
        return null;
    }

    @Override
    public void notifyCheckpointComplete(long checkpointId) throws Exception {

    }

    @Override
    public void setCurrentKey(Object key) {

    }

    @Override
    public Object getCurrentKey() {
        return null;
    }
}
