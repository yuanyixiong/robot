package com.organization.robot;

import com.google.common.collect.Lists;
import com.organization.robot.enums.RobotTypeEnum;
import com.organization.robot.impl.RobotLocal;
import com.organization.robot.impl.RobotSee;
import com.organization.robot.impl.RobotSvn;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: RobotFactory
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午3:49
 * @Description： 机器人工厂
 * @Version： 1.0
 */
public class RobotFactory {

    private static List<AbstractRobot> robotList = Lists.newArrayList(
            RobotLocal.builder().build(),
            RobotSvn.builder().build(),
            RobotSee.builder().build()
    );

    private static Map<RobotTypeEnum, Robot> ROBOT_MAP;

    static {
        ROBOT_MAP = Optional.ofNullable(robotList.stream().collect(Collectors.toMap(AbstractRobot::robotType, e -> (Robot) e))).orElse(null);
    }

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午4:19
     * @Description: 获取机器人
     * @Param: [typeEnum]
     * @Version: 1.0
     * @Return: com.organization.robot.Robot
     **/
    public static Robot getRobotService(RobotTypeEnum typeEnum) {
        return ROBOT_MAP.get(typeEnum);
    }
}
