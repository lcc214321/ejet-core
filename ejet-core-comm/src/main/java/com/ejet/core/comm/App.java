package com.ejet.core.comm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ejet.core.comm.mybatisplus.MybatisPlusGenerator;
import com.ejet.core.kernel.web.utils.BeanUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class App {

    public static void main(String[] args) throws IOException {

        ResourceBundle rb =  ResourceBundle.getBundle("MybatisPlus-Generator");
        Map<String, Object> maps = new HashMap<>();
        for(String key : rb.keySet()) {
            if(key.equals("table_names")) {
                maps.put(key, rb.getString(key).split(","));
                continue;
            }
            if(key.equals("table_idtype")) {
                maps.put(key, IdType.values()[Integer.valueOf(rb.getString(key))]);
                continue;
            }
            if(key.equals("enable_table_field_annotation") || key.equals("service_class_name_start_with")) {
                maps.put(key, rb.getString(key).equals("true"));
                continue;
            }
            maps.put(key, rb.getObject(key));
        }
        MybatisPlusGenerator generator = new MybatisPlusGenerator();
        generator = BeanUtil.toBean(maps, MybatisPlusGenerator.class);
        generator.generateByTablesWithInjectConfig();
    }

}
