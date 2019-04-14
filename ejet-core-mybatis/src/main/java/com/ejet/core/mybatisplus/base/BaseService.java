/**
 */
package com.ejet.core.mybatisplus.base;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 基础业务接口
 *
 * @param <T>
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * 逻辑删除(根据主键id)
	 *
	 * @param ids id集合(逗号分隔)
	 * @return
	 */
	boolean deleteLogic(@NotEmpty List<Integer> ids);

}
