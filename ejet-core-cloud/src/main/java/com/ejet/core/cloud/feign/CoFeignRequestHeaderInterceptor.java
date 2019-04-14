package com.ejet.core.cloud.feign;

import com.ejet.core.cloud.constant.Constant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Copyright (C), 2016-2018
 * @FileName: CoFeignRequestHeaderInterceptor
 * @Author:   ShenYijie(Ejet)
 * @CreateDate:     2019/4/13 16:44
 * @Description:    feign 传递Request header
 * @History:
 * @Version: 1.0
 */
@Slf4j
public class CoFeignRequestHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String value = request.getHeader(name);
                    if (Constant.AUTH_KEY.equals(name)) {
                        requestTemplate.header(name, value);
                    }
                }
            }
        }
    }

}
