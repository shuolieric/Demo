package com.ls.demo.rpc.enhanced.rpc;

import java.util.Properties;

public class Configuration {

    private Properties properties;

    public Configuration() {
        this.properties = new Properties();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

}
