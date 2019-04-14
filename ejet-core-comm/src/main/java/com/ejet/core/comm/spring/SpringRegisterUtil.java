package com.ejet.core.comm.spring;

import com.ejet.core.kernel.utils.StringUtil;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;
import java.util.Map;

import static com.ejet.core.kernel.utils.SpringUtil.getBean;
import static com.ejet.core.kernel.utils.SpringUtil.getContext;

/**
 * 动态注册spring DataSourceBean
 */
public class SpringRegisterUtil {

    public static final String SCOPE_SINGLETON = "singleton";

    public static <T> T registerBean(String beanName, Class<T> clazz) {
        return registerBean(beanName, clazz, null);
    }


    public static <T> T registerBean(String beanName, Class<T> clazz, Map<String, Object> propertyValue) {
        return registerBean(beanName, clazz, propertyValue, null, false,true);
    }

    public static <T> T registerBean(String beanName, Class<T> clazz, Map<String, Object> propertyValue, String scope, boolean lazyInit, boolean autowireCandidate ) {
        T bean = null;
        if(clazz==null) {
            return null;
        }

        bean = StringUtil.isBlank(beanName) ?  getBean(clazz) : getBean(beanName, clazz);
        if(bean!=null) {
            return bean;
        }
        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) getContext();
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
        if(propertyValue!=null && propertyValue.size()>0) {
            Iterator iterator = propertyValue.entrySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                beanDefinitionBuilder.addPropertyValue(key, propertyValue.get(key));
            }
        }
        beanDefinitionBuilder.getRawBeanDefinition().setScope(StringUtil.isBlank(scope) ? SCOPE_SINGLETON : scope);
        beanDefinitionBuilder.getRawBeanDefinition().setLazyInit(lazyInit);
        beanDefinitionBuilder.getRawBeanDefinition().setAutowireCandidate(autowireCandidate);

        String name = StringUtil.isBlank(beanName) ? clazz.getSimpleName() : beanName;
        beanDefinitionBuilder.addPropertyReference(name, name);
        return getBean(name, clazz);
    }


}
