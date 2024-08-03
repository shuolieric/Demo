package com.ls.demo.flink.study.chapter06.serialize;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SerializerTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.registerTypeWithKryoSerializer(Student.class, StudentSerializer.class);

        DataStreamSource<String> source = env.socketTextStream("localhost", 8888);

        source.map(line -> {
            String[] words = line.split(" ");
            return new Student(Integer.valueOf(words[0]), words[1], Integer.valueOf(words[2]));
        }).print();
        env.execute();
    }
}
