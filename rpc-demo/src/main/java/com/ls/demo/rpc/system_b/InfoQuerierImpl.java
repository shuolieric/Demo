package com.ls.demo.rpc.system_b;

import com.ls.demo.rpc.common.InfoQuerier;

public class InfoQuerierImpl implements InfoQuerier {
    @Override
    public Double getBalance(Integer id) {
        return 3000.00;
    }
}
