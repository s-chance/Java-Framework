package com.entropy.springboot01initializr;

import com.entropy.springboot01initializr.pojo.Cat;
import com.entropy.springboot01initializr.pojo.Person;
import com.entropy.springboot01initializr.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot01InitializrApplicationTests {

	@Autowired
	private Cat cat;
	@Autowired
	private Person person;
	@Autowired
	private User user;
	@Test
	void contextLoads() {
		System.out.println(cat);
		System.out.println(person);
		System.out.println(user);
	}

}
