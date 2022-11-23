package com.entropy.test;

import com.entropy.pojo.Owner;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutoTest {

    ApplicationContext auto = new ClassPathXmlApplicationContext("auto.xml");

    @Test
    public void test1() {
        Owner owner = auto.getBean("owner", Owner.class);
        owner.getCat().sound();
        owner.getDog().sound();
        System.out.println(owner.getName());
    }

    @Test
    public void test2() {
        Owner ownerByName = auto.getBean("ownerByName", Owner.class);
        ownerByName.getCat().sound();
        ownerByName.getDog().sound();
        System.out.println(ownerByName.getName());
    }

    @Test
    public void test3() {
        Owner ownerByType = auto.getBean("ownerByType", Owner.class);
        ownerByType.getCat().sound();
        ownerByType.getDog().sound();
        System.out.println(ownerByType.getName());
    }

    @Test
    public void test4() {
        Owner ownerByAnnotation = auto.getBean("ownerByAnnotation", Owner.class);
        ownerByAnnotation.getCat().sound();
        ownerByAnnotation.getDog().sound();
    }
}
