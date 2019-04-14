/**
 * Copyright (c) 2018-2028, DreamLu 卢春梦 (qq596392912@gmail.com).
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
package com.ejet.core.kernel.utils;


import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 字符集工具类
 */
public class Charsets {

	/**
	 * 字符集ISO-8859-1
	 */
	public static final java.nio.charset.Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
	/**
	 * 字符集GBK
	 */
	public static final java.nio.charset.Charset GBK = java.nio.charset.Charset.forName(StringPool.GBK);
	/**
	 * 字符集utf-8
	 */
	public static final java.nio.charset.Charset UTF_8 = StandardCharsets.UTF_8;

	/**
	 * 转换为Charset对象
	 *
	 * @param charsetName 字符集，为空则返回默认字符集
	 * @return Charsets
	 * @throws UnsupportedCharsetException 编码不支持
	 */
	public static java.nio.charset.Charset charset(String charsetName) throws UnsupportedCharsetException {
		return StringUtil.isBlank(charsetName) ? java.nio.charset.Charset.defaultCharset() : java.nio.charset.Charset.forName(charsetName);
	}

}
