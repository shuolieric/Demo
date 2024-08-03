package com.ls.demo.flink.coordinator.expression.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartitionedEvent implements Event{
    private int partition;
    private Event event;
}
