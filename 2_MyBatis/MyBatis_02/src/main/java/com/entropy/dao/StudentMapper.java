package com.entropy.dao;

import com.entropy.pojo.Student;

import java.util.List;

public interface StudentMapper {

    //查询全部信息, 按照查询嵌套处理
    List<Student> getStudents();

    //查询全部信息, 按照结果嵌套处理
    List<Student> getResult();
}
