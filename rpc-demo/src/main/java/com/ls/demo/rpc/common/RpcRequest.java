package com.ls.demo.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = -6832300917286416004L;
    private String interfaceName;
    private String functionName;
    private Class<?>[] parameterTypes;
    private Object[] args;
}
