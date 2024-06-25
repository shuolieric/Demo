package com.ls.demo.flink.cdc.stream;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkCDCDataStreamDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000L);

        DataStreamSource<String> source = env.fromSource(
                        MySqlSource.<String>builder()
                                .hostname("localhost")
                                .port(3306)
                                .username("root")
                                .password("6GT5Os66NI")
                                .databaseList("db1")
                                .tableList("db1.tbl1,db1.tbl2")
                                .deserializer(new JsonDebeziumDeserializationSchema())
                                .startupOptions(StartupOptions.initial())
                                .build(), WatermarkStrategy.noWatermarks(), "MysqlCDC source")
                .setParallelism(4);
        source.print();

        env.execute();

    }
}
