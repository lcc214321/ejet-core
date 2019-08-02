package com.ejet.core.logger.event;

import com.ejet.core.kernel.web.utils.UrlUtil;
import com.ejet.core.kernel.web.utils.WebUtil;
import com.ejet.core.launch.props.CoreProperties;
import com.ejet.core.launch.server.ServerInfo;
import com.ejet.core.logger.constant.EventConstant;
import com.ejet.core.logger.feign.ILogClient;
import com.ejet.core.logger.model.LogUsual;
import com.ejet.core.secure.utils.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class UsualLogListener {

    private final ILogClient logService;
    private final ServerInfo serverInfo;
    private final CoreProperties coreProperties;

    @Async
    @Order
    @EventListener(UsualLogEvent.class)
    public void saveUsualLog(UsualLogEvent event) {
        Map<String, Object> source = (Map<String, Object>) event.getSource();
        LogUsual logUsual = (LogUsual) source.get(EventConstant.EVENT_LOG);
        HttpServletRequest request = (HttpServletRequest) source.get(EventConstant.EVENT_REQUEST);
        logUsual.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
        logUsual.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
        logUsual.setMethod(request.getMethod());
        logUsual.setParams(WebUtil.getRequestParamString(request));
        logUsual.setServerHost(serverInfo.getHostName());
        logUsual.setServiceId(coreProperties.getName());
        logUsual.setEnv(coreProperties.getEnv());
        logUsual.setServerIp(serverInfo.getIpWithPort());
        logUsual.setCreateBy(SecureUtil.getUserAccount(request));
        logUsual.setCreateTime(LocalDateTime.now());
        logService.saveUsualLog(logUsual);
    }


}
