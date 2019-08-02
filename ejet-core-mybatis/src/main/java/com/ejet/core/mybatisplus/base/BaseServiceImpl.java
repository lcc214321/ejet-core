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
package com.ejet.core.mybatisplus.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ejet.core.kernel.web.constant.CoConstant;
import com.ejet.core.kernel.web.utils.BeanUtil;
import com.ejet.core.secure.SysUser;
import com.ejet.core.secure.utils.SecureUtil;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

	private Class<T> modelClass;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type type = this.getClass().getGenericSuperclass();
		this.modelClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
	}

	@Override
	public boolean save(T entity) {
		SysUser user = SecureUtil.getUser();
		LocalDateTime now = LocalDateTime.now();
		entity.setCreateUser(user.getUserId());
		entity.setCreateTime(now);
		entity.setUpdateUser(user.getUserId());
		entity.setUpdateTime(now);
		entity.setStatus(CoConstant.DB_STATUS_NORMAL);
		entity.setIsDeleted(CoConstant.DB_NOT_DELETED);
		return super.save(entity);
	}

	@Override
	public boolean updateById(T entity) {
		SysUser user = SecureUtil.getUser();
		entity.setUpdateUser(user.getUserId());
		entity.setUpdateTime(LocalDateTime.now());
		return super.updateById(entity);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		SysUser user = SecureUtil.getUser();
		T entity = BeanUtil.newInstance(modelClass);
		entity.setUpdateUser(user.getUserId());
		entity.setUpdateTime(LocalDateTime.now());
		return super.update(entity, Wrappers.<T>update().lambda().in(T::getId, ids)) && super.removeByIds(ids);
	}

}
