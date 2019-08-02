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

import com.ejet.core.kernel.web.utils.UrlUtil;
import com.ejet.core.kernel.web.utils.WebUtil;
import com.ejet.core.launch.props.CoreProperties;
import com.ejet.core.launch.server.ServerInfo;
import com.ejet.core.logger.constant.EventConstant;
import com.ejet.core.logger.feign.ILogClient;
import com.ejet.core.logger.model.LogApi;
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
public class ApiLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final CoreProperties coreProperties;


	@Async
	@Order
	@EventListener(ApiLogEvent.class)
	public void saveApiLog(ApiLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogApi logApi = (LogApi) source.get(EventConstant.EVENT_LOG);
		HttpServletRequest request = (HttpServletRequest) source.get(EventConstant.EVENT_REQUEST);
		logApi.setServiceId(coreProperties.getName());
		logApi.setServerHost(serverInfo.getHostName());
		logApi.setServerIp(serverInfo.getIpWithPort());
		logApi.setEnv(coreProperties.getEnv());
		logApi.setRemoteIp(WebUtil.getIP(request));
		logApi.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
		logApi.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
		logApi.setMethod(request.getMethod());
		logApi.setParams(WebUtil.getRequestParamString(request));
		logApi.setCreateBy(SecureUtil.getUserAccount(request));
		logApi.setCreateTime(LocalDateTime.now());
		logService.saveApiLog(logApi);
	}

}
