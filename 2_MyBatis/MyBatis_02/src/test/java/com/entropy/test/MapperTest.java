package com.entropy.test;

import com.entropy.dao.StudentMapper;
import com.entropy.dao.TeacherMapper;
import com.entropy.dao.UserMapper;
import com.entropy.pojo.Student;
import com.entropy.pojo.Teacher;
import com.entropy.pojo.User;
import com.entropy.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MapperTest {

    //注解实现查询
    @Test
    public void test01() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> all = userMapper.getAll();

        for (User user : all) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    //根据id查询
    @Test
    public void test02() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.getById(1);

        System.out.println(user);

        sqlSession.close();
    }

    //新增
    @Test
    public void test03() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int add = userMapper.add(new User(6, "abc", "333"));
        System.out.println("add = " + add);

        sqlSession.close();
    }

    //修改
    @Test
    public void test04() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int update = userMapper.update(new User(6, "xyz", "999"));
        System.out.println("update = " + update);

        sqlSession.close();
    }

    //删除
    @Test
    public void test05() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int delete = userMapper.delete(6);
        System.out.println("delete = " + delete);

        sqlSession.close();
    }


    //查询教师
    @Test
    public void test06() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher = teacherMapper.getTeacherById(1);
        System.out.println(teacher);

        sqlSession.close();
    }

    //多对一关系处理测试
    //查询学生, 按照查询嵌套处理
    @Test
    public void test07() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        List<Student> students = studentMapper.getStudents();

        for (Student student : students) {
            System.out.println(student);
        }

        sqlSession.close();
    }
    //按照结果嵌套处理
    @Test
    public void test08() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        List<Student> result = studentMapper.getResult();

        for (Student student : result) {
            System.out.println(student);
        }

        sqlSession.close();
    }

    //一对多关系处理
    //查询教师, 按照结果嵌套处理
    @Test
    public void test09() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher = teacherMapper.getTeacherResultById(1);
        System.out.println(teacher);

        sqlSession.close();
    }
    //按照查询嵌套处理
    @Test
    public void test10() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher = teacherMapper.getTeacherQueryById(1);
        System.out.println(teacher);

        sqlSession.close();
    }
}
