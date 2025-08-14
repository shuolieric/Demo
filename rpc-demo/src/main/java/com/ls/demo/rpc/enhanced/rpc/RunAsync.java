package com.ls.demo.rpc.enhanced.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RunAsync implements Message{
    private Runnable runnable;
    private long delayTime;
}
