package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.entity.group.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.*;
import com.jnshu.resourceservice.utils.pageutil.*;
import io.swagger.annotations.*;
import org.apache.commons.lang.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

/**
 * @program: morepineapple
 * @description: 角色管理模块
 * @author: Mr.huang
 * @create: 2018-12-22 20:57
 **/
@RestController
@RequestMapping("/a")
@Api(tags = {"角色管理模块"}, description = "测试角色管理模块")
public class RoleModuleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleModuleService roleModuleService;


	//TODO:角色管理-查询角色列表
	/**
	 * @Description 角色管理-获取角色列表，分页默返回参数单页8组数据
	 * @param [pageUtil]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/26
	 * @throws Exception
	 */
	@ApiOperation(value = "selectRoleList",  notes = "查询角色列表")
	@GetMapping(value = "/role/list", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll')")
	public RetResult<?> selectRoleList(@Validated ({PageUtilGroup.class})PageUtil pageUtil
			) throws Exception {

		if (logger.isDebugEnabled()){
			logger.debug("----UserModuleController----selectRoleList-----");
			logger.debug("分页参数为:{}", pageUtil.toString());

		}
		System.out.println("-----------------------------------------");
		System.out.println(AuthorizationUtils.getUserId());
		System.out.println(AuthorizationUtils.getDetails().toString());


		return RetResponse.result(RetCode.SUCCESS_ROLE_LIST_GET)
				.setData(roleModuleService.selectRoleList(pageUtil));
	}



	//TODO:角色管理-新增角色
	/**
	 * @Description 角色管理-增加角色
	 * @param [role, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/24
	 * @throws
	 */
	@ApiOperation(value = "addRole",  notes = "新增角色")
	@PostMapping(value = "/role", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageAdd') ")
	public RetResult<?> addRole(@Validated({addAndUpdateRoleGroup.class})Role role,
								@Validated({JWTOperatingGroup.class})JWT jwt)throws Exception{


		if (logger.isDebugEnabled()){
			logger.debug("----RoleModuleController----addRole------");
			logger.debug("插入的角色名:{}", role.getRoleName());
		}
		roleModuleService.addRole(role, jwt);
		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_ADD);
	}



	//TODO:橘色管理-更改角色信息
	/**
	 * @Description 角色管理-更改角色信息
	 * @param [role, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	@ApiOperation(value = "updateRole",  notes = "更改角色")
	@PutMapping(value = "/role", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageUpdate') ")
	public RetResult<?> updateRole(@Validated({addAndUpdateRoleGroup.class})Role role,
								   @Validated({JWTOperatingGroup.class})JWT jwt)throws Exception{


		// defaultAccessTokenConverter
		roleModuleService.updateRole(role, jwt);
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_UPDATE);

	}



	//TODO:角色管理-删除角色
	/**
	 * @Description 角色管理-删除角色
	 * @param [targetRoleId, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	@ApiOperation(value = "deleteRole",  notes = "更改角色")
	@DeleteMapping(value = "/role/{targetRoleId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageDelete') ")
	public RetResult<?> deleteRole(@PathVariable Integer targetRoleId,
								   @Validated({JWTOperatingGroup.class})JWT jwt)throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----RoleModuleController----deleteRole-----");
			logger.debug("需要删除的角色ID:{}", targetRoleId);
		}
		roleModuleService.deleteRole(targetRoleId, jwt);
		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_DELETE);

	}



	//TODO:橘色管理-查询单个角色
	/**
	 * @Description 角色管理-新增单个角色信息
	 * @param [targetRoleId]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	@ApiOperation(value = "selectRole",  notes = "查询角色")
	@PostMapping(value = "/role/{targetUserId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll')")
	public RetResult<?> selectRole(@PathVariable Integer targetRoleId) throws Exception{
		if (logger.isDebugEnabled()){
			logger.debug("----RoleModuleController----selectRole-----");
			logger.debug("需要查询的用户ID:{}", targetRoleId);
		}

		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_GET).
				setData(roleModuleService.selectRole(targetRoleId));
	}

	@ApiOperation(value = "selectRoleList",  notes = "查询角色列表")
	@GetMapping(value = "/role/me", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll')")
	public RetResult<?> getCurrentUser( HttpServletRequest request,PageUtil pageUtil
									   ) throws Exception {

		// String s = user.getPrincipal().toString();
		// String name = user.getName();
		String header = request.getHeader("Authorization");
		System.out.println(header);
		String token = StringUtils.substringAfter(header,"Bearer ");
		System.out.println("token:"  + token);

		//
		// Claims body = Jwts.parser().setSigningKey("hwb123")
		// 		.parseClaimsJws(token).getBody();
		//
		// String username = (String) body.get("username");
		// logger.info("解析token获取到的username为{}",username);
		// logger.info("从Authentication里获取到的username为{}",s);
		// logger.info("从Authentication里获取到的username为{}",name);
		// return ServerResponse.createBySuccess(user);


		// ---------------------------------
		// Key KEY = new SecretKeySpec("javastack".getBytes(),
		// 		SignatureAlgorithm.HS512.getJcaName());
		// Resource resource = new ClassPathResource("public.cert");
		// String publicKey ;
		// // 通过IO流读取公钥文件
		// try {
		// 	publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }
		// // 将公钥写入JWT转换器
		// System.out.println("================================");
		// System.out.println("publicKey:" + publicKey);


		// Jws<Claims> claimsJws = Jwts.parser()
		// 		.setSigningKey(publicKey).parseClaimsJws(token);
		// JwsHeader header1 = claimsJws.getHeader();
		// Claims body1 = claimsJws.getBody();

		// SecretKey secretKey = generalKey();
		// Claims body1 = Jwts.parser().setSigningKey("hwb123").setSigningKey(publicKey).parseClaimsJws(token).getBody();


		// System.out.println("header1:" + header1);
		// System.out.println("body1:" + body1);


		// if (logger.isDebugEnabled()){
		// 	logger.debug("----UserModuleController----selectRoleList-----");
		// 	logger.debug("分页参数为:{}", pageUtil.toString());
		//
		// }

		return RetResponse.result(RetCode.SUCCESS_ROLE_LIST_GET)
				.setData(roleModuleService.selectRoleList(pageUtil));
	}


}
