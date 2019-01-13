package com.jnshu.zuulserver.filter;

import com.jnshu.zuulserver.service.*;
import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import com.netflix.zuul.exception.*;
import org.apache.commons.lang.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;

/**
 * @program: morepineapple
 * @description: 过滤器
 * @author: Mr.huang
 * @create: 2019-01-10 10:57
 **/
@Component
public class MyFilter extends ZuulFilter {

	@Autowired
	public RedisService redisService;

	private static Logger log = LoggerFactory.getLogger(MyFilter.class);

	@Override
	public String filterType() {
		// 前置过滤器
		return "pre";
	}

	@Override
	public int filterOrder() {
		// 优先级为0，数字越大，优先级越低
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		// 是否执行该过滤器，此处为true，说明需要过滤
		return true;
	}

	/**
	 * @Description 拦截器详细
	 * @param []
	 * @return java.lang.Object
	 * @author Mr.HUANG
	 * @date 2019/1/10
	 * @throws ZuulException
	 */
	@Override
	public Object run() throws ZuulException {
		// 获取共享上下文信息
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));

		String url = request.getRequestURI();
		log.info("当前请求url:{}",url);

		String loginOutPartUrl = "/a/out";
		String backstageUrlHead = "/a";
		String loginPartUrl = "/a/login";

		// 登录及访问模块
		if (url.contains(backstageUrlHead) && !url.equals(loginPartUrl)){
			String accessToken = request.getHeader("Authorization");
			log.info("登录、访问模块authorization 获取：{}",accessToken);

			// accessToken为null
			if (StringUtils.isBlank(accessToken)){
				log.warn("accessToken is empty");
				ctx.setResponseBody("{\"code\": 2006, \"message\": \"token is empty\"}");
				return null;
			}
			// accessToken不为null
			else if (StringUtils.isNotBlank(accessToken)){
				Long loginTime = Long.valueOf(redisService.get(accessToken));
				Long nowTime = System.currentTimeMillis();
				Long timeOut = 3*24*60*60L;

				// 超过有效期
				if (0 ==loginTime || nowTime - loginTime > timeOut ){
					log.warn("token已过期");
					ctx.setResponseBody("{\"code\": 2007, \"message\": \"token is expired\"}");
					return null;
				}
			}
			ctx.addZuulRequestHeader("Authorization", "Bearer " + accessToken);
		}

		// 登出模块,直接补上 authorization,在服务类中再对以登录信息做处理
		if(url.contains(loginOutPartUrl)){
			String accessToken = request.getHeader("Authorization");
			ctx.addZuulRequestHeader("Authorization", "Bearer " + accessToken);
			return null;
		}

		if(url.contains(loginPartUrl)){
			String accessToken = request.getHeader("Authorization");
			ctx.addZuulRequestHeader("Authorization", accessToken);




			System.out.println("accessToken:" + accessToken );
			System.out.println(ctx.getRequest());
			System.out.println("------------2--------");
			System.out.println(ctx.getResponse());
			System.out.println("--------------3----------");
			System.out.println(ctx.toString());
			return null;
		}
		return null;
	}

}
