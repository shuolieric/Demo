package com.ls.demo.flink.source.dev.expression.event;

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
