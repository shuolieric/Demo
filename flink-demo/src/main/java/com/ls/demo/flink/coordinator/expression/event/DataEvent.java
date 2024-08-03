package com.ls.demo.flink.coordinator.expression.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataEvent implements Event{
    private int f1;
    private int f2;
    private int f3;
}
