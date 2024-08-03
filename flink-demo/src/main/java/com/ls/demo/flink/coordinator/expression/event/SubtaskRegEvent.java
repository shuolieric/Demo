package com.ls.demo.flink.coordinator.expression.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.runtime.operators.coordination.OperatorEvent;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubtaskRegEvent implements OperatorEvent {
    private Integer subtaskId;
}
