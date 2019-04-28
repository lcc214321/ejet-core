package com.ejet.core.kernel.tree;


import com.ejet.core.kernel.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 树节点信息
 *
 * @author ejet
 *
 */
public class TreeVO<T> {

	private T node;

	private boolean isRoot;

	private List<TreeVO<T>> child = new ArrayList<TreeVO<T>>();

	public TreeVO(T t) {
		this.node = t;
	}

	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}


	public void setChild(List<TreeVO<T>> child) {
		this.child = child;
	}

	public void addChild(TreeVO<T> t) {
		child.add(t);
	}

	public List<TreeVO<T>> getChild() {
		return child;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("id:").append(ReflectUtil.getFieldValue(this, "id"));
		buff.append(", pid:").append(ReflectUtil.getFieldValue(this, "pid"));
		buff.append(", child:").append(child.size());
		return buff.toString();
	}

}
