package com.ultra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {
    private static final String packageName = "com.ultra";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/demo";
    private static final String tablePrefix = "sw_";
    private static final String[] tables = { "sw_sa" };
    private static final String dbUserName = "demo";
    private static final String dbPassWord = "demo";
    private static final String codeAuthor = "author";
    private static final String codeOutputDir = "C:\\codes";

    @Test
    public void generateCode() {
        // user -> UserService, 设置成true: user -> IUserService
        boolean serviceNameStartWithI = false;
        // 多个表 "sys_user","sys_role"
        generateByTables(serviceNameStartWithI, packageName, tables);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername(dbUserName).setPassword(dbPassWord)
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true).setEntityLombokModel(false).setDbColumnUnderline(true)
                .setTablePrefix(new String[] { tablePrefix }).setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);

        config.setActiveRecord(false).setAuthor(codeAuthor).setOutputDir(codeOutputDir).setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig).setPackageInfo(new PackageConfig().setParent(packageName)
                        .setController("controller").setMapper("dao").setXml("mapper").setEntity("dao.entity"))
                .execute();
    }

}
