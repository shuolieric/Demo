package com.ls.demo.flink.time;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.TimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class WatermarkTest {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStateBackend(new FsStateBackend("file:///tmp/flink-state-backend"));

        DataStreamSource<String> sourceDS = env.socketTextStream("localhost", 9999);
        SingleOutputStreamOperator<StationLog> mapDS = sourceDS.map(new MapFunction<String, StationLog>() {
            @Override
            public StationLog map(String value) throws Exception {
                String[] split = value.split(",");
                StationLog stationLog = new StationLog(
                        split[0],
                        split[1],
                        split[2],
                        split[3],
                        Long.parseLong(split[4]),
                        Long.parseLong(split[5]));
                return stationLog;
            }
        });
        SingleOutputStreamOperator<StationLog> withWatermarkDS = mapDS.assignTimestampsAndWatermarks(
                WatermarkStrategy.<StationLog>forBoundedOutOfOrderness(Duration.ofSeconds(2))
                        .withTimestampAssigner(new SerializableTimestampAssigner<StationLog>() {
                            @Override
                            public long extractTimestamp(StationLog element, long recordTimestamp) {
                                return element.getCallTime();
                            }
                        })
                        .withIdleness(Duration.ofSeconds(5))
        );

//        withWatermarkDS.process(new ProcessFunction<StationLog, String>() {
//            @Override
//            public void processElement(StationLog value, ProcessFunction<StationLog, String>.Context ctx, Collector<String> out) throws Exception {
//                out.collect("element: " + value + " watermark: " + ctx.timerService().currentWatermark());
//            }
//        }).print();

        withWatermarkDS.keyBy(StationLog::getSid)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .sum("duration")
                .print();

        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE);
        env.execute();

    }
}
