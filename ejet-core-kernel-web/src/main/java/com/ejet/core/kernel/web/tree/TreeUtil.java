package com.ejet.core.kernel.web.tree;


import com.ejet.core.kernel.web.utils.ReflectUtil;

import java.util.List;

/**
 * 树转换接口
 *
 * @author ShenYijie
 */
public class TreeUtil {

	/**
	 * 转换成树节点信息
	 *
	 * @param all
	 * @param rootNode
	 * @return
	 */
	public static <T> TreeVO<T> toTree(List<T> all, T rootNode, String fieldNameOfId, String fieldNameOfPid) {
		return toTree(all, rootNode, fieldNameOfId, fieldNameOfPid, null);
	}

	/**
	 * 转换成树节点信息
	 *
	 * @param all
	 * @param rootNode
	 * @return
	 */
	public static <T> TreeVO<T> toTree(List<T> all, T rootNode,
				String fieldNameOfId, String fieldNameOfPid, String[] ignoreFileds) {
		TreeVO<T> treeNode = new TreeVO<T>(rootNode);
		boolean allAppend = false;
		for(T item : all) {
			TreeVO<T> node = new TreeVO<T>(item);
			if(ignoreFileds!=null) {
				for(String fieldName : ignoreFileds) { //设置为空
					ReflectUtil.invokeSetMethodValue(item, fieldName, null);
				}
			}
			boolean temp = appendChild(treeNode, node, fieldNameOfId, fieldNameOfPid);
			allAppend = temp && allAppend;
		}
		return treeNode;
	}

	/**
	 * 递归增加子节点数据
	 *
	 * @param treeNode
	 * @param currentNode
	 * @param fieldNameOfId
	 * @param fieldNameOfPid
	 * @return
	 */
	public static <T> boolean appendChild(TreeVO<T> treeNode, TreeVO<T> currentNode, String fieldNameOfId, String fieldNameOfPid) {
		boolean appended = false;
		Object value = ReflectUtil.getFieldValue(treeNode.getNode(), fieldNameOfId);
		Object pidValue = ReflectUtil.getFieldValue(currentNode.getNode(), fieldNameOfPid);
		if( Integer.valueOf(String.valueOf(value)).intValue() ==
				Integer.valueOf(String.valueOf(pidValue)).intValue() ) { //
			treeNode.addChild(currentNode);
			return true;
		} else if( treeNode.getChild()!=null ) {
			for(TreeVO<T> item : treeNode.getChild()) {
				if( appendChild(item, currentNode, fieldNameOfId, fieldNameOfPid) ) {
					appended = true;
					break;
				}
			}
		}
		return appended;
	}
}
