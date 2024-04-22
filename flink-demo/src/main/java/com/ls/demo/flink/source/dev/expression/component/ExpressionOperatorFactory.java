package com.ls.demo.flink.source.dev.expression.component;

import com.ls.demo.flink.source.dev.expression.event.Event;
import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;
import org.apache.flink.runtime.operators.coordination.OperatorEventDispatcher;
import org.apache.flink.runtime.operators.coordination.OperatorEventGateway;
import org.apache.flink.streaming.api.operators.*;

public class ExpressionOperatorFactory extends SimpleOperatorFactory<Event>
        implements OneInputStreamOperatorFactory<Event, Event>,
        CoordinatedOperatorFactory<Event> {

    private ExpressionOperator operator;
    public ExpressionOperatorFactory(StreamOperator<Event> operator) {
        super(operator);
        this.operator = (ExpressionOperator) getOperator();
    }

    @Override
    public <T extends StreamOperator<Event>> T createStreamOperator(StreamOperatorParameters<Event> parameters) {
        final OperatorID operatorId = parameters.getStreamConfig().getOperatorID();
        final OperatorEventDispatcher eventDispatcher = parameters.getOperatorEventDispatcher();

        operator.setup(
                parameters.getContainingTask(),
                parameters.getStreamConfig(),
                parameters.getOutput());
        eventDispatcher.registerEventHandler(operatorId, operator);

        return (T) operator;
    }

    @Override
    public OperatorCoordinator.Provider getCoordinatorProvider(String operatorName, OperatorID operatorID) {
        return new ExpressionCoordinatorProvider(operatorID);
    }
}
