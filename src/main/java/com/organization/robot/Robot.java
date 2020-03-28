package com.organization.robot;

import com.organization.robot.enums.RobotTypeEnum;

/**
 * @InterfaceName: Robot
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午3:44
 * @Description： 机器人
 * @Version： 1.0
 */
public interface Robot {

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午4:16
     * @Description: 机器人
     * @Param: []
     * @Version: 1.0
     * @Return: com.organization.robot.enums.RobotTypeEnum
     **/
    RobotTypeEnum robotType();

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/12 下午12:59
     * @Description: 机器人接口地址
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    String getUrl();


}