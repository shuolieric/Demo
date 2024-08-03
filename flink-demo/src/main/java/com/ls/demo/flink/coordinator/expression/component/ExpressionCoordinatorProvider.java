package com.ls.demo.flink.coordinator.expression.component;

import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;

public class ExpressionCoordinatorProvider implements OperatorCoordinator.Provider{

    private final OperatorID operatorID;
    public ExpressionCoordinatorProvider(OperatorID operatorID) {
        this.operatorID = operatorID;
    }
    @Override
    public OperatorID getOperatorId() {
        return this.operatorID;
    }

    @Override
    public OperatorCoordinator create(OperatorCoordinator.Context context) throws Exception {
        return new ExpressionCoordinator();
    }
}
