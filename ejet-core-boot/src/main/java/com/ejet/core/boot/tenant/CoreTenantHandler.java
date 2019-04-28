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
package com.ejet.core.boot.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.ejet.core.kernel.utils.StringUtil;
import com.ejet.core.secure.utils.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * 租户信息处理器
 */
@Slf4j
@AllArgsConstructor
public class CoreTenantHandler implements TenantHandler {

	private final CoreTenantProperties properties;

	/**
	 * 获取租户编号
	 *
	 * @return 租户编号
	 */
	@Override
	public Expression getTenantId() {
		return new StringValue(SecureUtil.getTenantCode());
	}

	/**
	 * 获取租户字段名称
	 *
	 * @return 租户字段名称
	 */
	@Override
	public String getTenantIdColumn() {
		return properties.getColumn();
	}

	/**
	 * 过滤租户表
	 *
	 * @param tableName 表名
	 * @return 是否进行过滤
	 */
	@Override
	public boolean doTableFilter(String tableName) {
		return (properties.getTables().size() > 0 && !properties.getTables().contains(tableName)) || !properties.getBladeTables().contains(tableName) || StringUtil.isBlank(SecureUtil.getTenantCode());
	}
}
