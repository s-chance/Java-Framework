package com.entropy.dao;

import com.entropy.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper {

    @Select("select * from teacher where id=#{teacher_id}")
    Teacher getTeacherById(@Param("teacher_id") Integer id);

    //按照结果嵌套处理
    Teacher getTeacherResultById(Integer id);

    //按照查询嵌套处理
    Teacher getTeacherQueryById(Integer id);
}
