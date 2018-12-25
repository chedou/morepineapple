package com.jnshu.resourceservice.dao;

import io.swagger.models.auth.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RolePermissionMapperTest {

	private final org.slf4j.Logger LOGGER =LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	RolePermissionMapper rolePermissionMapper;


	@Test
	public void insertRolePermission() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		System.out.println(list.toString());


		Map<String, Object> rolePermissionMap =new HashMap<>();
		rolePermissionMap.put("roleId",10);

		// List<Integer> integerList = new ArrayList<>();
		// integerList.add(12);
		// integerList.add(341);
		// rolePermissionMap.put("Permissions",integerList);
		rolePermissionMap.put("status",1);
		rolePermissionMap.put("gmtCreate",111);
		rolePermissionMap.put("gmtUpdate",222);
		rolePermissionMap.put("createBy",333);
		rolePermissionMap.put("updateBy",444);
		rolePermissionMap.put("Permissions",Arrays.asList("888","777"));
		rolePermissionMapper.insertRolePermission(rolePermissionMap);
		LOGGER.info("-------------------------");
		LOGGER.info("插入新增的用户");

	}
}