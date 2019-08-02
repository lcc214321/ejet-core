package com.ejet.core.boot.loader;

import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.enhance.ContextClassLoader;

/**
 * 自定义类加载器
 */
public class DecryptContextClassLoader extends ContextClassLoader {


    public DecryptContextClassLoader(ClassLoader parentClassLoader, OgnlContext context) {
        super(parentClassLoader, context);
    }
}
