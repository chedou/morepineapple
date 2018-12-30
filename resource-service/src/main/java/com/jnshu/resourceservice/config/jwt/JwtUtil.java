package com.jnshu.resourceservice.config.jwt;

import com.google.gson.*;
import io.jsonwebtoken.*;

import java.util.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2018-12-29 20:55
 **/
public class JwtUtil {
	/**
	 * 解密
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(base64Security.getBytes())
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 前三个参数为自己用户token的一些信息比如id，权限，名称等。不要将隐私信息放入（大家都可以获取到）
	 * @param map
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(Map<String, Object> map, String base64Security) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//添加构成JWT的参数
		//估计是第三段密钥
		//生成JWT
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
				.setPayload(new Gson().toJson(map))
				.signWith(signatureAlgorithm,base64Security.getBytes());
		return builder.compact();
	}

	public static void main(String[] args) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("province", "898765");
		// map.put("city", "898765");
		// map.put("appkey", "HMu1H/cmyKDOiHv41Y9lLROuOlOo+PPG8F4/RotRmNc=");
		// map.put("timestamp", new Date().getTime());

		// //密钥
		// String keyt = "79e7c69681b8270162386e6daa53d1dc";
		// String token=JwtUtil.createJWT(map, keyt);
		// System.out.println("JWT加密的结果："+ token);
		// System.out.println("JWT解密的结果："+ parseJWT(token, keyt));
		System.out.println(parseJWT("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDYzNTY1NjEsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQWNjb3VudE1hbmFnZURlbGV0ZSIsIlJvbGVNYW5hZ2VVcGRhdGUiLCJDb21wYW55TWFuYWdlRGVsZXRlIiwiQWNjb3VudE1hbmFnZUFsbCIsIkFjY291bnRNYW5hZ2VBZGQiLCJBcnRpY2xlTWFuYWdlRGVsZXRlIiwiTW9kdWxlTWFuYWdlVXBkYXRlIiwiQ29tcGFueU1hbmFnZVVwZGF0ZSIsIkFjY291bnRNYW5hZ2VVcGRhdGUiLCJDb21wYW55TWFuYWdlQWxsIiwiQ29tcGFueU1hbmFnZUFkZCIsIkFydGljbGVNYW5hZ2VVcGRhdGUiLCJSb2xlTWFuYWdlRGVsZXRlIiwiUG9zaXRpb25NYW5hZ2VEZWxldGUiLCJBcnRpY2xlTWFuYWdlQWxsIiwiQXJ0aWNsZU1hbmFnZUFkZCIsIk1vZHVsZU1hbmFnZUFsbCIsIkluZm9ybWF0aW9uTWFuYWdlQWxsIiwiUG9zaXRpb25NYW5hZ2VVcGRhdGUiLCJSb2xlTWFuYWdlQWxsIiwiUm9sZU1hbmFnZUFkZCIsIk1vZHVsZU1hbmFnQWRkIiwiUG9zaXRpb25NYW5hZ2VBbGwiLCJQb3NpdGlvbk1hbmFnZUFkZCIsIkJhY2tzdGFnZU1hbmFnZUFsbCJdLCJqdGkiOiJlZTFkZGYwYS0wY2Q3LTRiMGItYjA2Ni1kM2JiYzZiOTE4YWUiLCJjbGllbnRfaWQiOiJ1c2VyLXNlcnZpY2UiLCJzY29wZSI6WyJzZXJ2aWNlIl19.TDBk6TsSc3TuiMXut2QF9Pme-Ck-Y5-3lxXUpGl-UCjzvGopgNGVtFX8O4RJxaHpAVwVciThcLWfosqGY4T8HNBOAW3CTGA2oa2ISSIrY8k3i9yWSHatrx2GmScg9DcGEomVyYL2X7uPfQG7Z4ndUfzQEBCY4RCVvEha5OXQGTimEZfeI9RRn4jMtYK3ErBakiJ_pQJhPKUpURpCoyASLYsSXx7rldffzhHp0dNxIO0Hocv5T1YRtyS00HgW4C5bsFuIWqsxIjspO7P9_m6dZjFwaTwvAstQqwe6eWIZB9ZghYYlGjH2mVEKtMyskZfF4TtnhBybcyrfmiBKChc7vQ",
				"hwb123"));


	}

}
