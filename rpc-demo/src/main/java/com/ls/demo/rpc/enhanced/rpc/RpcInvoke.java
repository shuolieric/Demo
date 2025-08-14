package com.ls.demo.rpc.enhanced.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcInvoke implements Message, Serializable {

    private static final long serialVersionUID = 9166092048490693414L;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

}
