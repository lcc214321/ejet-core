package com.ejet.core.secure.interceptor;

import com.ejet.core.kernel.api.R;
import com.ejet.core.kernel.api.ResultCode;
import com.ejet.core.kernel.constant.CoConstant;
import com.ejet.core.kernel.jackson.JsonUtil;
import com.ejet.core.kernel.utils.StringPool;
import com.ejet.core.kernel.utils.WebUtil;
import com.ejet.core.secure.utils.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt拦截器校验
 */
@Slf4j
public class SecureInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (null != SecureUtil.getUser()) {
            return true;
        } else {
            log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
            R result = R.fail(ResultCode.UN_AUTHORIZED);
            response.setCharacterEncoding(StringPool.UTF_8);
            response.setHeader(CoConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            try {
                response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
            return false;
        }
    }
}
