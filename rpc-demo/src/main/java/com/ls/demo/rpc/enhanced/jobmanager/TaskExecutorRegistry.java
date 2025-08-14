package com.ls.demo.rpc.enhanced.jobmanager;

import com.ls.demo.rpc.enhanced.taskmanager.TaskExecutorGateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskExecutorRegistry {
    private String resourceId;
    private String taskExecutorAddress;
    private TaskExecutorGateway taskExecutor;
}
