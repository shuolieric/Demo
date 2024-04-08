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
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = -8658969603077305177L;
    private Object data;
    private String msg;

}
