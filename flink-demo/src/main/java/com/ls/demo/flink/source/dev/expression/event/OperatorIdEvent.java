package com.ls.demo.flink.source.dev.expression.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.runtime.jobgraph.OperatorID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorIdEvent implements Event{
    private OperatorID operatorID;
}
