package com.ls.demo.flink.coordinator.hotpin.componet;

import com.ls.demo.flink.coordinator.hotpin.event.Event;
import org.apache.flink.runtime.operators.coordination.OperatorEvent;
import org.apache.flink.runtime.operators.coordination.OperatorEventHandler;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;

public class HotpinDispatcherOperator extends AbstractStreamOperator<Event>
        implements OneInputStreamOperator<Event, Event>, OperatorEventHandler {
    @Override
    public void handleOperatorEvent(OperatorEvent evt) {

    }

    @Override
    public void processElement(StreamRecord<Event> element) throws Exception {

    }
}
