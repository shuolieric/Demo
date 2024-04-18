package com.ls.demo.flink.source.dev.expression.component;

import com.ls.demo.flink.source.dev.expression.event.Event;
import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;
import org.apache.flink.streaming.api.operators.*;

public class ExpressionOperatorFactory extends SimpleOperatorFactory<Event> implements OneInputStreamOperatorFactory<Event, Event>, CoordinatedOperatorFactory<Event> {
    public ExpressionOperatorFactory(StreamOperator<Event> operator) {
        super(operator);
    }

    @Override
    public OperatorCoordinator.Provider getCoordinatorProvider(String operatorName, OperatorID operatorID) {
        return new ExpressionCoordinatorProvider(operatorID);
    }
}
