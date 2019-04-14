package com.ejet.core.comm.mybatisplus;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

@Data
public class MybatisPlusGeneratorConfig {
    //项目存储位置
    public String project_generate_disk = "";
    //包名
    public String package_name = "";
    //数据库地址
    public String db_url = "jdbc:mysql://192.168.0.244:3306/empi?useUnicode=true&characterEncoding=utf-8";
    //数据库实例名
    public String driver_class_name = "";
    //数据库类型
    public String db_type = "mysql";
    //数据库用户
    public String user = "root";
    //数据库密码
    public String password = "123456";
    //数据库schema
    public String schema = "";
    //要查询的表名
    public String[] table_names = {};
    //创建人
    public String create_author = "Ejet";
    //是否强制带上注解
    public boolean enable_table_field_annotation = true;
    //生成的注解带上IdType类型
    public IdType table_idtype = null;
    //是否去掉生成实体的属性名前缀
    public String field_prefix = null;
    //生成的Service 接口类名是否以I开头 默认是以I开头  user表 -> IUserService, UserServiceImpl
    public boolean service_class_name_start_with = false;
    //jsp生成地址
    public String jsp_url ="";

}
