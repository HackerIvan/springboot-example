package com.kingboy.generator.constants;

/**
 * 包的配置.
 * <p></p>
 *
 * @author kingboy - KingBoyWorld@163.com
 * @since 2018/8/7 01:37
 */
public class PackageConstants {

    /**
     * 项目基础包
     */
    public static final String PACKAGE = "com.kingboy.provider";

    /**
     * Controller包名
     */
    public static final String CONTROLLER_PACKAGE = "web";

    /**
     * Service包名
     */
    public static final String SERVICE_PACKAGE = "service";

    /**
     * ServiceImpl实现类包名
     */
    public static final String SERVICEIMPL_PACKAGE = "service.impl";

    /**
     * Mapper包名
     */
    public static final String MAPPER_PACKAGE = "mapper";

    /**
     * MapperXML包名
     */
    public static final String MAPPERXML_PACKAGE = "xml";

    /**
     * DTO包名
     */
    public static final String DTO_PACKAGE = "domain.dto";

    /**
     * PO包名
     */
    public static final String PO_PACKAGE = "domain.po";

    /**
     * VO包名
     */
    public static final String VO_PACKAGE = "domain.vo";

    /**
     * 是否根据模块进行分包，eg: com.company.service.User.java ==> com.company.service.user.User.java
     * 默认模块名称为表名去除下划线，转小写
     */
    public static final Boolean DIVIDE_MODULE = true;


}
