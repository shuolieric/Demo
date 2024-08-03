package com.ls.demo.flink.study.chapter02;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class BatchWordCount {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<String> lineDS = env.readTextFile("data/words.txt");
        FlatMapOperator<String, String> wordsDS = lineDS.flatMap( (String line, Collector<String> collector) -> {
            String[] words = line.split(" ");
            for (String word : words) {
                collector.collect(word);
            }
       }).returns(Types.STRING);

        MapOperator<String, Tuple2<String, Long>> words = wordsDS.map(w -> Tuple2.of(w, 1L)).returns(Types.TUPLE(Types.STRING, Types.LONG));

        words.groupBy(0).sum(1).print();
    }
}
