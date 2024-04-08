package com.ls.demo.rpc.system_b;

import com.ls.demo.rpc.common.UserQuerier;

public class UserQuerierImpl implements UserQuerier {
    @Override
    public Integer getId() {
        return 1;
    }

    @Override
    public String getUserName() {
        return "user1";
    }
}
