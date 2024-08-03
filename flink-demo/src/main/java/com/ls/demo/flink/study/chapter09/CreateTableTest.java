package com.ls.demo.flink.study.chapter09;

import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
import org.apache.flink.table.api.*;

public class CreateTableTest {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .build();

        TableEnvironment tableEnv = TableEnvironment.create(settings);
        tableEnv.createTable("SourceTable", TableDescriptor.forConnector("datagen")
                .schema(Schema.newBuilder()
                        .column("f0", DataTypes.STRING())
                        .column("f1", DataTypes.INT())
                        .build())
                .option(DataGenConnectorOptions.ROWS_PER_SECOND, 1L)
                .build()
        );

        Table table = tableEnv.sqlQuery("select * from SourceTable");
        table.execute().print();
    }
}
