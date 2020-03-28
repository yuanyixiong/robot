package com.organization.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: RobotEnum
 * @Author: yuanyixiong
 * @Date: 2020/3/9 下午11:47
 * @Description: 机器人枚举
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum RobotTypeEnum {

    SVN("https://oapi.dingtalk.com/robot/send", "aa2d7343176194c33c067484293f2838ae9546d276002b89e0125db9ccf406ab", StringUtils.EMPTY, "SVN代码提交监控机器人"),

    SEE("https://oapi.dingtalk.com/robot/send", "e916b88e54316a84819e94a0407914405de16f19a51582c959225038cf0774d7", StringUtils.EMPTY, "SEE 平台发包监控机器人"),

    Local("https://oapi.dingtalk.com/robot/send", "7eae909b0d315600114830d308f052db19533609860f09e02186cf3a16ca36d5", "SECdc53711da34bdda8533dcb3097fb3724132801e8ef3deaddc57ccc5e00254a94", "本地测试");

    private String url;

    private String accessToken;

    private String sign;

    private String describe;
}
