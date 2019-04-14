package com.ejet.core.logger.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ejet.core.kernel.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志实体类
 */
@Data
@TableName("sys_log_usual")
public class LogUsual implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 服务器名
     */
    private String serverHost;
    /**
     * 服务器IP地址
     */
    private String serverIp;
    /**
     * 系统环境
     */
    private String env;
    /**
     * 日志级别
     */
    private String logLevel;
    /**
     * 日志业务id
     */
    private String logId;
    /**
     * 日志数据
     */
    private String logData;
    /**
     * 操作方式
     */
    private String method;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 操作提交的数据
     */
    private String params;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private LocalDateTime createTime;
}
