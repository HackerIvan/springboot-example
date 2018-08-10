package com.kingboy.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.kingboy.generator.constants.GeneralConstants.*;
import static com.kingboy.generator.constants.PackageConstants.*;

/**
 * 代码生成类.
 * <p></p>
 *
 * @author KingBoy - KingBoyWorld@163.com
 * @since 2018-8-6 12:12
 */
public class GeneratorApplication {

    private static Logger log = LoggerFactory.getLogger(GeneratorApplication.class);


    /**
     * 修改constants/PackageConstants与GeneralConstants配置类，生成自定义代码.
     * <p></p>
     * @param args
     * @return void
     * @author kingboy
     * @since 2018/8/7 00:45
     */
    public static void main(String[] args) {
        Arrays.stream(TABLES.split(","))
                .filter(table -> table != null)
                .forEach(table -> GeneratorApplication.createByOneTable(table));
    }

    /**
     * 代码生成.
     * <p></p>
     * @param tableName  表名
     * @return void
     * @author KingBoy
     * @since 2018-08-07 16:49:47
     *
     */
    public static void createByOneTable(String tableName) {
        //默认将表名去除下划线，转为全小写，作为包名
        String moduleName = "." + tableName.replaceAll("_", "").toLowerCase();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(getGlobalConfig());
        // 数据源配置
        mpg.setDataSource(getDataSourceCOnfig());
        // 策略配置
        mpg.setStrategy(getStrategyConfig(tableName));
        // 包配置
        mpg.setPackageInfo(getPackageConfig(moduleName));
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        mpg.setCfg(getInjectionConfig());
        mpg.setTemplate(getTemplateConfig());
        // 执行生成Controller Service Mapper XML PO
        mpg.execute();

        /*以下为个人配置*/

        //为了只生成下面的vo和dto,将其它模块置为空,同时复用其他配置
        mpg.getTemplate()
                .setController(null)
                .setService(null)
                .setServiceImpl(null)
                .setMapper(null)
                .setXml(null);

        //生成DTO
        AutoGenerator generatorDTO = new AutoGenerator()
                .setGlobalConfig(mpg.getGlobalConfig().setEntityName("%sDTO"))
                .setDataSource(mpg.getDataSource())
                .setStrategy(mpg.getStrategy())
                .setPackageInfo(mpg.getPackageInfo().setEntity(DTO_PACKAGE + (DIVIDE_MODULE ? moduleName : "")))
                .setCfg(mpg.getCfg())
                .setTemplate(mpg.getTemplate().setEntity("templates/dto.java.vm"));
        generatorDTO.execute();

        //生成VO
        AutoGenerator generatorVO = new AutoGenerator()
                .setGlobalConfig(mpg.getGlobalConfig().setEntityName("%sVO"))
                .setDataSource(mpg.getDataSource())
                .setStrategy(mpg.getStrategy())
                .setPackageInfo(mpg.getPackageInfo().setEntity(VO_PACKAGE + (DIVIDE_MODULE ? moduleName : "")))
                .setCfg(mpg.getCfg())
                .setTemplate(mpg.getTemplate().setEntity("templates/vo.java.vm"));
        generatorVO.execute();
    }

    private static InjectionConfig getInjectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("time", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                map.put("dateFormat", DATE_FORMAT);
                this.setMap(map);
            }
        };
    }

    private static PackageConfig getPackageConfig(String moduleName) {
        return new PackageConfig()
                // .setModuleName("test")//设置模块名, king: 没有才用这个配置，直接写zaiparent中
                .setParent(PACKAGE)// 自定义包路径
                .setController(CONTROLLER_PACKAGE + (DIVIDE_MODULE ? moduleName : ""))
                .setService(SERVICE_PACKAGE + (DIVIDE_MODULE ? moduleName : ""))
                .setServiceImpl(
                        SERVICEIMPL_PACKAGE.substring(0, SERVICEIMPL_PACKAGE.lastIndexOf("."))
                        + (DIVIDE_MODULE ? moduleName : "")
                        + SERVICEIMPL_PACKAGE.substring(SERVICEIMPL_PACKAGE.lastIndexOf("."), SERVICEIMPL_PACKAGE.length())
                )
                .setEntity(PO_PACKAGE + (DIVIDE_MODULE ? moduleName : ""))
                .setMapper(MAPPER_PACKAGE + (DIVIDE_MODULE ? moduleName : ""))
                .setXml(MAPPERXML_PACKAGE + (DIVIDE_MODULE ? moduleName : ""));
    }

    private static DataSourceConfig getDataSourceCOnfig() {
        return new DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        log.info("自定义转换类型：" + fieldType);
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            return DbColumnType.BOOLEAN;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .setUrl(JDBC_URL);
    }

    private static GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                .setOutputDir(OUTPUT_DIR)//输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setSwagger2(true)//生成Swagger注解
                .setAuthor(AUTHOR)
                .setIdType(IdType.ID_WORKER)//使用分布式ID
                .setActiveRecord(false)// 不需要ActiveRecord特性的请改为false
                .setOpen(true)//生成完成后是否打开文件夹
                //自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setEntityName("%s");
    }

    private static StrategyConfig getStrategyConfig(String tableName) {
        //自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_by", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));
        tableFillList.add(new TableFill("update_by", FieldFill.UPDATE));

        return new StrategyConfig()
                .setRestControllerStyle(true)//设置为RestController
                .setEntityLombokModel(true)//开启Lombok注解
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setTablePrefix(new String[]{"bmd_", "mp_"})// 此处可以修改为您的表前缀
                .setInclude(new String[] {tableName}) // 需要生成的表
                .setVersionFieldName("version")//设置乐观锁属性名
                .setLogicDeleteFieldName("delete_flag")//设置逻辑删除属性名
                .setTableFillList(tableFillList); //自定义需要填充的字段
        // .setSuperEntityColumns(new String[]{"test_id"})//自定义实体，公共字段
        // .setCapitalMode(true)// 全局大写命名
        // .setDbColumnUnderline(true)//全局下划线命名
        // .setExclude(new String[]{"test"}) // 排除生成的表
        // .setSuperEntityClass("com.baomidou.demo.TestEntity")//自定义实体父类
        // 自定义 mapper 父类
        // .setSuperMapperClass("com.baomidou.demo.TestMapper")
        // 自定义 service 父类
        // .setSuperServiceClass("com.baomidou.demo.TestService")
        // 自定义 service 实现类父类
        // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
        // 自定义 controller 父类
        // .setSuperControllerClass("com.baomidou.demo.TestController")
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // .setEntityColumnConstant(true)
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // .setEntityBuilderModel(true)
        // Boolean类型字段是否移除is前缀处理
        // .setEntityBooleanColumnRemoveIsPrefix(true)
        // .setControllerMappingHyphenStyle(true)
    }

    private static TemplateConfig getTemplateConfig() {
        //自定义模板配置，将generator项目中的/resources/template使用copy至您项目对应目录下，
        // 模板名称可自定义如下配置：
        // 设置模板文件的位置，
        return new TemplateConfig()
                .setEntity("templates/po.java.vm"); //模板名称可自定义,如果不需要输出，则设置为null
        // .setController("...");
        // .setEntity("...");
        // .setMapper("...");
        // .setXml("...");
        // .setService("...");
        // .setServiceImpl("...");
    }

}
