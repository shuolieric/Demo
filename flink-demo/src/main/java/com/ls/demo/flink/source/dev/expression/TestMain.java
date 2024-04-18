package com.ls.demo.flink.source.dev.expression;

import com.ls.demo.flink.source.dev.expression.component.CalculateOperator;
import com.ls.demo.flink.source.dev.expression.component.ExpressionOperator;
import com.ls.demo.flink.source.dev.expression.component.ExpressionOperatorFactory;
import com.ls.demo.flink.source.dev.expression.event.DataEvent;
import com.ls.demo.flink.source.dev.expression.event.Event;
import com.ls.demo.flink.source.dev.expression.event.ExpressionEvent;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TestMain {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataSource = env.socketTextStream("localhost", 9999);

        SingleOutputStreamOperator<Event> mapDataStream = dataSource.map(line -> {
            String[] splits = line.split("\\|");
            if (splits[0].equals("e")) {
                return new ExpressionEvent(splits[1]);
            } else if (splits[0].equals("d")) {
                String[] dataSplits = splits[1].split(",");
                return new DataEvent(
                        Integer.parseInt(dataSplits[0]),
                        Integer.parseInt(dataSplits[1]),
                        Integer.parseInt(dataSplits[2]));
            } else {
                throw new RuntimeException("Wrong data type");
            }
        });

        int downstreamParallelism = 2;
        SingleOutputStreamOperator<Event> expressionDataStream = mapDataStream.transform("expression operator",
                TypeInformation.of(Event.class),
                new ExpressionOperatorFactory(new ExpressionOperator(2))).setParallelism(1);

        SingleOutputStreamOperator<Double> calculateDataStream = expressionDataStream.transform("calculate operator",
                TypeInformation.of(Double.class),
                new CalculateOperator()).setParallelism(downstreamParallelism);

        calculateDataStream.print();

        env.execute();
    }
}
