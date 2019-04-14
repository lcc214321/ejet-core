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

package com.ejet.core.logger.config;

import com.ejet.core.launch.props.CoreProperties;
import com.ejet.core.launch.server.ServerInfo;
import com.ejet.core.logger.aspect.ApiLogAspect;
import com.ejet.core.logger.event.ApiLogListener;
import com.ejet.core.logger.event.ErrorLogListener;
import com.ejet.core.logger.event.UsualLogListener;
import com.ejet.core.logger.feign.ILogClient;
import com.ejet.core.logger.logger.CoreLogger;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class CoLoggerAutoConfiguration {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final CoreProperties bladeProperties;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public CoreLogger coreLogger() {
		return new CoreLogger();
	}

	@Bean
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean
	public UsualLogListener coreEventListener() {
		return new UsualLogListener(logService, serverInfo, bladeProperties);
	}

}
