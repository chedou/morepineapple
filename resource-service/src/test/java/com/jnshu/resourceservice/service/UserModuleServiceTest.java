package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.entity.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import org.springframework.test.context.junit4.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserModuleServiceTest {

	private final org.slf4j.Logger LOGGER =LoggerFactory.getLogger(this.getClass());


	@Autowired
	UserModuleService userModuleService;

	@Test
	public void addUser() {

		LOGGER.info("-------------------------");
		LOGGER.info("插入新增的用户");

		User userOne =new User();
		userOne.setName("海澜之家3");
		userOne.setPassword("12adq");

		JWT jwt = new JWT();
		jwt.setUserID(1L);

		userModuleService.addUser(userOne,jwt.getUserID() );


	}
}