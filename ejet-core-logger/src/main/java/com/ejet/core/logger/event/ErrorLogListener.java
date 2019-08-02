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
package com.ejet.core.logger.event;


import com.ejet.core.kernel.web.utils.WebUtil;
import com.ejet.core.launch.props.CoreProperties;
import com.ejet.core.launch.server.ServerInfo;
import com.ejet.core.logger.constant.EventConstant;
import com.ejet.core.logger.feign.ILogClient;
import com.ejet.core.logger.model.LogError;
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
 * 异步监听错误日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final CoreProperties coreProperties;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	public void saveErrorLog(ErrorLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
		HttpServletRequest request = (HttpServletRequest) source.get(EventConstant.EVENT_REQUEST);
		logError.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
		logError.setMethod(request.getMethod());
		logError.setParams(WebUtil.getRequestParamString(request));
		logError.setServiceId(coreProperties.getName());
		logError.setServerHost(serverInfo.getHostName());
		logError.setServerIp(serverInfo.getIpWithPort());
		logError.setEnv(coreProperties.getEnv());
		logError.setCreateBy(SecureUtil.getUserAccount(request));
		logError.setCreateTime(LocalDateTime.now());
		logService.saveErrorLog(logError);
	}

}
