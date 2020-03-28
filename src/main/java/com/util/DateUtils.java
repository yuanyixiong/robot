package com.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: DateUtils
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午12:51
 * @Description： TODO
 * @Version： 1.0
 */
public class DateUtils {

    /**
     * <p>时间格式:yyyy-MM-dd HH:mm:ss<p/>
     */
    public final static String FORMATTER_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * <p>时间格式:yyyy-MM-dd HH:mm:ss<p/>
     */
    public final static String FORMATTER_HH_mm_ss = "HH:mm:ss";

    /**
     * <p>时间格式:yyyy-MM-dd</p>
     */
    public final static String FORMATTER_yyyy_MM_dd = "yyyy-MM-dd";

    /**
     * <p>返回时间yyyy-MM-dd hh:mm:ss格式<p/>
     *
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午1:33
     * @Description: TODO
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    public static String currentDateTime_yyyy_MM_dd_HH_mm_ss() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateUtils.FORMATTER_yyyy_MM_dd_HH_mm_ss));
    }

    /**
     * <p>返回时间yyyy-MM-dd格式<p/>
     *
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午1:33
     * @Description: TODO
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    public static String currentDateTime_yyyy_MM_dd() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.FORMATTER_yyyy_MM_dd));
    }

    /**
     * <p>返回时间yyyy-MM-dd格式<p/>
     *
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午1:33
     * @Description: TODO
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    public static String currentDateTime_HH_mm_ss() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateUtils.FORMATTER_HH_mm_ss));
    }
}
