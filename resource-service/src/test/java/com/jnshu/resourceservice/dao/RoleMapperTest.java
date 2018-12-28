package com.jnshu.resourceservice.dao;

import com.jnshu.resourceservice.entity.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {

	private final org.slf4j.Logger LOGGER =LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	RoleMapper roleMapper;

	@Test
	public void selectByRoleName() {
		LOGGER.info("-------------------------");
		LOGGER.info("插入新增的角色");

		Role role = roleMapper.selectByRoleName("admin");
		System.out.println(role.toString());


	}


	@Test
	public void selectById() {
		LOGGER.info("-------------------------");
		LOGGER.info("插入新增的角色");

		Role role = roleMapper.selectRolePermissionById(1);
		System.out.println(role.toString());
		System.out.println(role.getPermissionsList().get(1).getId());

	}

	@Test
	public void selectAll() {
		LOGGER.info("-------------------------");
		LOGGER.info("插入新增的角色");
		List<Role> roleList = roleMapper.selectAll();
		System.out.println(roleList.toString());
		System.out.println(roleList.get(0).getPermissionsList().get(1).getId());

	}




}