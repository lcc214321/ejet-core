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
package com.ejet.core.boot.controller;

import com.ejet.core.boot.file.CoreFile;
import com.ejet.core.boot.file.CoreFileUtil;
import com.ejet.core.kernel.web.api.R;
import com.ejet.core.secure.SysUser;
import com.ejet.core.secure.utils.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 控制器基类封装类
 *
 */
public class CoreController {

	/**
	 * ============================     REQUEST    =================================================
	 */

	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取request
	 *
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 获取当前用户
	 *
	 * @return BladeUser
	 */
	public SysUser getUser() {
		return SecureUtil.getUser();
	}

	/** ============================     API_RESULT    =================================================  */

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data) {
		return R.data(data);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data, String msg) {
		return R.data(data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param code 状态码
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data, String msg, int code) {
		return R.data(code, data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public R success(String msg) {
		return R.success(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public R fail(String msg) {
		return R.fail(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param flag 是否成功
	 * @return R
	 */
	public R status(boolean flag) {
		return R.status(flag);
	}


	/**============================     FILE    =================================================  */

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file 文件
	 * @return BladeFile
	 */
	public CoreFile getFile(MultipartFile file) {
		return CoreFileUtil.getFile(file);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file 文件
	 * @param dir  目录
	 * @return BladeFile
	 */
	public CoreFile getFile(MultipartFile file, String dir) {
		return CoreFileUtil.getFile(file, dir);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file        文件
	 * @param dir         目录
	 * @param path        路径
	 * @param virtualPath 虚拟路径
	 * @return BladeFile
	 */
	public CoreFile getFile(MultipartFile file, String dir, String path, String virtualPath) {
		return CoreFileUtil.getFile(file, dir, path, virtualPath);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files 文件集合
	 * @return BladeFile
	 */
	public List<CoreFile> getFiles(List<MultipartFile> files) {
		return CoreFileUtil.getFiles(files);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files 文件集合
	 * @param dir   目录
	 * @return BladeFile
	 */
	public List<CoreFile> getFiles(List<MultipartFile> files, String dir) {
		return CoreFileUtil.getFiles(files, dir);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files       文件集合
	 * @param dir         目录
	 * @param path        目录
	 * @param virtualPath 虚拟路径
	 * @return BladeFile
	 */
	public List<CoreFile> getFiles(List<MultipartFile> files, String dir, String path, String virtualPath) {
		return CoreFileUtil.getFiles(files, dir, path, virtualPath);
	}


}
