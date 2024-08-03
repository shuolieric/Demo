package com.ls.demo.flink.study.chapter02;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class StreamBatchWordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setRuntimeMode(RuntimeExecutionMode.BATCH);
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        DataStreamSource<String> lineDS = env.readTextFile("data/words.txt");
        SingleOutputStreamOperator<String> wordsDS = lineDS.flatMap((String line, Collector<String> collector) -> {
            String[] words = line.split(" ");
            for (String word : words) {
                collector.collect(word);
            }
        }).returns(Types.STRING);

        SingleOutputStreamOperator<Tuple2<String, Long>> mapDS = wordsDS.map(w -> Tuple2.of(w, 1L)).returns(Types.TUPLE(Types.STRING, Types.LONG));
        KeyedStream<Tuple2<String, Long>, String> keyByDS = mapDS.keyBy(tp -> tp.f0);

        keyByDS.sum(1).print();

        env.execute();
    }
}
