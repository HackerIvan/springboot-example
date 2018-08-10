package com.kingboy.generator.constants;

/**
 * 配置定义.
 * <p></p>
 *
 * @author kingboy - KingBoyWorld@163.com
 * @since 2018/8/7 00:38
 */
public class GeneralConstants {

    /**
     * 数据库连接配置
     */
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "123456";
    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/provider?characterEncoding=utf8";

    /**
     * JsonFormat注解中的日期格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 要逆向生成的表,支持多个表的生成.多个表以英文逗号间隔
     */
    public static final String TABLES = "user";

    /**
     * 作者信息
     */
    public static final String AUTHOR = "KingBoy - KingBoyWorld@163.com";

    /**
     * 输出位置
     */
    public static final String OUTPUT_DIR = System.getProperty("user.home") + "/Desktop";

}
