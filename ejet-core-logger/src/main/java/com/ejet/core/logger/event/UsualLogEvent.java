package com.ejet.core.logger.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;


/**
 * 系统日志事件
 */
public class UsualLogEvent extends ApplicationEvent {

    public UsualLogEvent(Map<String, Object> source) {
        super(source);
    }

}
