package com.jnshu.zuulserver.filter;

import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import com.netflix.zuul.exception.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;

/**
 * @program: morepineapple
 * @description: 过滤器
 * @author: Mr.huang
 * @create: 2019-01-07 10:57
 **/
@Component
public class MyFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(MyFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
		Object accessToken = request.getParameter("token");
		if (accessToken == null) {
			log.warn("token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			try {
				ctx.getResponse().getWriter().write("token is empty");
			} catch (Exception e) {
			}

			return null;
		}
		log.info("ok");
		return null;
	}
}
