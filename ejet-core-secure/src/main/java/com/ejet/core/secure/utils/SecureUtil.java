/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ejet.core.secure.utils;

import com.ejet.core.kernel.utils.Charsets;
import com.ejet.core.kernel.utils.Func;
import com.ejet.core.kernel.utils.StringPool;
import com.ejet.core.kernel.utils.WebUtil;
import com.ejet.core.secure.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Secure工具类
 */
public class SecureUtil {
	public static final String BLADE_USER_REQUEST_ATTR = "_BLADE_USER_REQUEST_ATTR_";

	public final static String HEADER = "blade-auth";
	public final static String BEARER = "bearer";
	public final static String ACCOUNT = "account";
	public final static String USER_ID = "userId";
	public final static String ROLE_ID = "roleId";
	public final static String USER_NAME = "userName";
	public final static String ROLE_NAME = "roleName";
	public final static String TENANT_CODE = "tenantCode";
	public final static Integer AUTH_LENGTH = 7;
	public static String BASE64_SECURITY = Base64.getEncoder().encodeToString("BladeX".getBytes(Charsets.UTF_8));

	/**
	 * 获取用户信息
	 *
	 * @return BladeUser
	 */
	public static SysUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		// 优先从 request 中获取
		SysUser bladeUser = (SysUser) request.getAttribute(BLADE_USER_REQUEST_ATTR);
		if (bladeUser == null) {
			bladeUser = getUser(request);
			if (bladeUser != null) {
				// 设置到 request 中
				request.setAttribute(BLADE_USER_REQUEST_ATTR, bladeUser);
			}
		}
		return bladeUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return BladeUser
	 */
	public static SysUser getUser(HttpServletRequest request) {
		Claims claims = getClaims(request);
		if (claims == null) {
			return null;
		}
		Integer userId = Func.toInt(claims.get(SecureUtil.USER_ID));
		String tenantCode = Func.toStr(claims.get(SecureUtil.TENANT_CODE));
		String roleId = Func.toStr(claims.get(SecureUtil.ROLE_ID));
		String account = Func.toStr(claims.get(SecureUtil.ACCOUNT));
		String roleName = Func.toStr(claims.get(SecureUtil.ROLE_NAME));
		String userName = Func.toStr(claims.get(SecureUtil.USER_NAME));
		SysUser bladeUser = new SysUser();
		bladeUser.setUserId(userId);
		bladeUser.setTenantCode(tenantCode);
		bladeUser.setAccount(account);
		bladeUser.setRoleId(roleId);
		bladeUser.setRoleName(roleName);
		bladeUser.setUserName(userName);
		return bladeUser;
	}


	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Integer getUserId() {
		SysUser user = getUser();
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户id
	 *
	 * @param request request
	 * @return userId
	 */
	public static Integer getUserId(HttpServletRequest request) {
		SysUser user = getUser(request);
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户账号
	 *
	 * @return userAccount
	 */
	public static String getUserAccount() {
		SysUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户账号
	 *
	 * @param request request
	 * @return userAccount
	 */
	public static String getUserAccount(HttpServletRequest request) {
		SysUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		SysUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户名
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserName(HttpServletRequest request) {
		SysUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		SysUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取用角色
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserRole(HttpServletRequest request) {
		SysUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}


	/**
	 * 获取租户编号
	 *
	 * @return tenantCode
	 */
	public static String getTenantCode() {
		SysUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getTenantCode();
	}

	/**
	 * 获取租户编号
	 *
	 * @param request request
	 * @return tenantCode
	 */
	public static String getTenantCode(HttpServletRequest request) {
		SysUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getTenantCode();
	}

	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Claims getClaims(HttpServletRequest request) {
		String auth = request.getHeader(SecureUtil.HEADER);
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			if (headStr.compareTo(SecureUtil.BEARER) == 0) {
				auth = auth.substring(7);
				return SecureUtil.parseJWT(auth);
			}
		}
		return null;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(WebUtil.getRequest());
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken jsonWebToken
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			Claims claims = Jwts.parser()
				.setSigningKey(Base64.getDecoder().decode(BASE64_SECURITY))
				.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 创建jwt
	 *
	 * @param user     user
	 * @param audience audience
	 * @param issuer   issuer
	 * @param isExpire isExpire
	 * @param expireTime expireTime
	 * @return jwt
	 */
	public static String createJWT(Map<String, String> user, String audience, String issuer, boolean isExpire, Long expireTime) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//生成签名密钥
		byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//添加构成JWT的类
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JsonWebToken")
			.setIssuer(issuer)
			.setAudience(audience)
			.signWith(signatureAlgorithm, signingKey);

		//设置JWT参数
		user.forEach(builder::claim);

		//添加Token过期时间
		if (isExpire) {
			long expMillis = nowMillis + getExpire(expireTime);
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}

		//生成JWT
		return builder.compact();
	}

	/**
	 * 获取过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static long getExpire(Long expireTime) {
		if(expireTime!=null && expireTime>=1000*5) {//至少5秒钟
			return expireTime;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis() - System.currentTimeMillis();
	}

}
