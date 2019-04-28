package com.ejet.core.kernel.utils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A reflection utility class.
 * @author ejet
 */

public class ReflectUtil {
    /**
     * 获取对象所有属性
     * @param bean
     * @return
     */
    public static List<Field> getDeclaredFields(Object bean) {
        List<Field> list = new ArrayList<Field>();
        if(bean==null)		return list;
        return getDeclaredFieldsBy(bean.getClass(), bean);
    }

    /**
     * 获取类所有属性
     *
     * @return
     */
    public static List<Field> getDeclaredFieldsByClazz(Class clazz) {
        List<Field> list = new ArrayList<Field>();
        return getDeclaredFieldsBy(clazz, null);
    }

    /**
     * 获取类所有 属性
     * @param clazz
     * @return
     */
    private static List<Field> getDeclaredFieldsBy(Class clazz, Object bean) {
        List<Field> list = new ArrayList<Field>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {//向上循环  遍历父类
            Field[] field = clazz.getDeclaredFields();
            for (Field f : field) {
                try {
                    f.setAccessible(true);
                    list.add(f);
                    //System.out.println("属性："+f.getName()+" 值：" + bean!=null ? "null" : f.get(bean).toString());
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 循环向上转型, 获取对象属性
     * @param object
     * @param fieldName
     * @return
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null ;
        Class<?> clazz = object.getClass() ;
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field ;
            } catch (Exception e) {
                //这里甚么都不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        return null;
    }


    /**
     * 获取声明的方法
     * @param object
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){
        Method method = null;
        for(Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里甚么都不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     * @param object : 子类对象
     * @param methodName : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters : 父类中的方法参数
     * @return 父类中方法的执行结果
     */
    public static Object invokeMethod(Object object, String methodName, Class<?> [] parameterTypes,
                                      Object [] parameters) {
        //根据 对象、方法名和对应的方法参数 通过取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        //抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);
        try {
            if(null != method) {
                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也
     * @param object : 子类对象
     * @param fieldName : 父类中
     * @param value : 将要设置的值
     */

    public static void setFieldValue(Object object, String fieldName, Object value){
        //根据 对象和属性名通过取 Field对象
        Field field = getDeclaredField(object, fieldName);
        if(field==null)     return;
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * 直接读的属性值, 忽略 private/protected 修饰符, 也
     * @param object : 子类对象
     * @param fieldName : 父类中
     * @return : 父类中
     * */
    public static Object getFieldValue(Object object, String fieldName){
        //根据 对象和属性名通过取 Field对象
        Field field = getDeclaredField(object, fieldName);
        if(field==null)     return null;
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //获的属性值
            return field.get(object);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否为父类
     */
    public static boolean classIsOrExtends(Class<?> clazz, Class<?> superClass) {
        if (clazz.equals(Object.class)) {
            return false;
        } else {
            return clazz.equals(superClass) || classIsOrExtends(clazz.getSuperclass(), superClass);
        }
    }

    /**
     * 是否为实现接口
     */
    public static boolean classIsOrImplements(Class<?> clazz, Class<?> superClass) {
        return superClass.isAssignableFrom(clazz);
    }


    /**
     * 获取set方法名
     * @return
     */
    public static String getSetMethodName(String fieldName) {
        StringBuilder builder = new StringBuilder();
        builder.append("set");
        builder.append(StringUtil.upperFirst(fieldName));
        return builder.toString();
    }

    /**
     * 获取get方法名
     * @return
     */
    public static String getGetMethodName(String fieldName) {
        StringBuilder builder = new StringBuilder();
        builder.append("get");
        builder.append(StringUtil.upperFirst(fieldName));
        return builder.toString();
    }


    /**
     * 执行对象属性set方法,
     * @param obj 执行对象
     * @param fieldName fieldName属性
     * @param value value值
     */
    public static void invokeSetMethodValue(Object obj, String fieldName, Object value) {
        setFieldValue(obj, getSetMethodName(fieldName), value);
    }

    /**
     * 执行对象的get方法
     * @param object
     * @param fieldName
     * @return
     */
    public static Object invokeGetMethodValue(Object object, String fieldName) {
        return getFieldValue(object, getGetMethodName(fieldName));
    }



}
