package com.ejet.core.comm.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Copyright (C), 2016-2018, 武汉康华数海有限公司
 * @FileName: MybatisPlusGenerator
 * @Author:   ShenYijie(Ejet)
 * @CreateDate:     2019/4/3 11:38
 * @Description:    代码生成器
 * @History:
 * @Version: 1.0
 */
public class MybatisPlusGenerator extends MybatisPlusGeneratorConfig implements MybatisPlusConstant {

    /**
     *  全局配置
     *
     * @return
     */
    private GlobalConfig GlobalGenerate(){
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false)// 不需要ActiveRecord特性的请改为false
                .setIdType(table_idtype)
                .setEnableCache(false);// XML 二级缓存
        config.setAuthor(create_author)
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true);// XML columList
        config.setOutputDir(project_generate_disk +"/java")
                .setFileOverride(true)
                .setControllerName("%sController");//自定义文件命名，注意 %s 会自动填充表实体属性！

        if (!service_class_name_start_with) {
            config.setServiceName("I%sService");
        }
        return config;
    }


    /**
     * 数据源配置
     *
     * @return
     */
    private DataSourceConfig DaoSourceGenerate(){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        DbType type = null;
        if("oracle".equals(db_type)){
            type = DbType.ORACLE;
        }else if("sql_server".equals(db_type)){
            type = DbType.SQL_SERVER;
        }else if("mysql".equals(db_type)){
            type = DbType.MYSQL;
        }else if("postgre_sql".equals(db_type)){
            type = DbType.POSTGRE_SQL;
        }
        dataSourceConfig.setDbType(type)//数据库类型
                .setUrl(db_url)//数据库地址
                .setUsername(user)//数据库用户名
                .setPassword(password)//数据库密码
                .setDriverName(driver_class_name)//实例名
                .setSchemaName(schema);
        return dataSourceConfig;
    }

    /**
     * 策略配置
     */
    private StrategyConfig StrategyGenerate() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setVersionFieldName("version")
                    .setCapitalMode(true)// 全局大写命名 ORACLE 注意
                    .setEntityLombokModel(true);
        strategyConfig
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setEntityTableFieldAnnotationEnable(enable_table_field_annotation)
                //.entityTableFieldAnnotationEnable(enable_table_field_annotation)
                .setInclude(table_names)
        ;//修改替换成你需要的表名，多个表名传数组
        //strategyConfig.setExclude(new String[]{"test"}); // 排除生成的表
        strategyConfig.setTablePrefix(new String[]{"tlog_", "tsys_"});// 此处可以修改为您的表前缀
        strategyConfig.setSuperEntityClass(BASE_ENTITY_CLAZZ);// 自定义实体父类
        strategyConfig.setSuperEntityColumns(new String[]{"test_id", "age"});// 自定义实体，公共字段
        strategyConfig.setSuperMapperClass("com.lin.demo.TestMapper");// 自定义 mapper 父类
        strategyConfig.setSuperServiceClass("com.lin.demo.TestService");// 自定义 service 父类
        strategyConfig.setSuperServiceImplClass("com.lin.demo.TestServiceImpl");// 自定义 service 实现类父类
        strategyConfig.setSuperControllerClass("com.lin.demo.TestController");// 自定义 controller 父类
        strategyConfig.setEntityColumnConstant(true);// 【实体】是否生成字段常量（默认 false）public static final String ID = "test_id";
        strategyConfig.setEntityBuilderModel(true);// 【实体】是否为构建者模型（默认 false）public User setName(String name) {this.name = name; return this;}
        return strategyConfig;
    }

    /**
     * 自定义模板配置
     * @return
     */
    private TemplateConfig TemplateGenerate(){
        TemplateConfig templateConfig = new TemplateConfig()
                .setXml("/template/mapper2.xml")//注意：不要带上.vm
                .setController("/template/action.java")
                .setMapper("/template/mapper.java")
                .setXml("/template/mapper.xml")
                .setService("/template/service.java")
                .setServiceImpl("/template/serviceImpl.java")
                ;
        return templateConfig;
    }

    /**
     * 自定义文件及key
     */
    private InjectionConfig FileGenerate() throws IOException {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {//自定义参数
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        //创建jsp目录
        File file = new File(jsp_url);
        file.mkdirs();
        file.createNewFile();
//        Files files = new Files(jsp_url);
//        files.createlist();
        //生成列表页面
        focList.add(new FileOutConfig("/template/list.jsp.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return jsp_url +"/list.jsp";
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }

    /**
     * 包配置
     *
     * @return
     */
    public PackageConfig PackageGenerate(){
        PackageConfig pc = new PackageConfig()
                .setParent(package_name)
                //.setModuleName("test")
                .setController("controller")
                .setEntity("entity")
                .setMapper("mapper")
                .setXml("mapper");
        return pc;
    }

    public void generateByTablesWithInjectConfig() {
        try {
            //全局配置
            GlobalConfig config = GlobalGenerate();
            //配置数据源
            DataSourceConfig dataSourceConfig = DaoSourceGenerate();
            //配置策略
            StrategyConfig strategyConfig = StrategyGenerate();
            //配置模板
            TemplateConfig templateConfig = TemplateGenerate();
            //生成jsp文件
            InjectionConfig injectionConfig = FileGenerate();
            //配置包
            PackageConfig packageConfig = PackageGenerate();
            //生成代码
            AutoGenerator generator = new AutoGenerator()
                    .setGlobalConfig(config)
                    //.setTemplate(templateConfig)//自定义模板路径
                    .setDataSource(dataSourceConfig)
                    //.setCfg(injectionConfig)
                    .setStrategy(strategyConfig)
                    .setPackageInfo(packageConfig);

            generator.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
