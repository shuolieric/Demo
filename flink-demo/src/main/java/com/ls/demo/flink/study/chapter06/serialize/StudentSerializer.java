package com.ls.demo.flink.study.chapter06.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class StudentSerializer extends Serializer<Student> {
    @Override
    public void write(Kryo kryo, Output output, Student student) {
        output.writeInt(student.getId());
        output.writeString(student.getName());
        output.writeInt(student.getAge());
    }

    @Override
    public Student read(Kryo kryo, Input input, Class<Student> type) {
        Student student = new Student();
        student.setId(input.readInt());
        student.setName(input.readString());
        student.setAge(input.readInt());
        return student;
    }
}
