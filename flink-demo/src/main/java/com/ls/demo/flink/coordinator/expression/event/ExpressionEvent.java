package com.ls.demo.flink.coordinator.expression.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressionEvent implements Event{
    private String expression;
}
