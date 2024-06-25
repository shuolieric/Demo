package com.ls.demo.flink.cdc.sql;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

public class FlinkCDCSqlDemo {
    public static void main(String[] args) {
        EnvironmentSettings envSettings = EnvironmentSettings.newInstance().inStreamingMode().build();
        TableEnvironment tableEnv = TableEnvironment.create(envSettings);

        tableEnv.getConfig().getConfiguration().setLong("execution.checkpointing.interval",5000);

        tableEnv.executeSql("create table mysql_binlog (" +
                "id INT, " +
                "name STRING, " +
                "age INT, " +
                "PRIMARY KEY (id) NOT ENFORCED" +
                ") with ('connector' = 'mysql-cdc', " +
                " 'hostname' = 'localhost', " +
                " 'port' = '3306', " +
                " 'username' = 'root', " +
                " 'password' = '6GT5Os66NI', " +
                " 'database-name' = 'db1', " +
                " 'table-name' = 'tbl1'" +
                ")");

        tableEnv.executeSql("select * from mysql_binlog").print();
    }
}
