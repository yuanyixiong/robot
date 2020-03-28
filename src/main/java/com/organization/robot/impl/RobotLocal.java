package com.organization.robot.impl;

import com.organization.robot.AbstractRobot;
import com.organization.robot.enums.RobotTypeEnum;
import lombok.Builder;

/**
 * @ClassName: RobotSVN
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午3:46
 * @Description： TODO
 * @Version： 1.0
 */
@Builder
public class RobotLocal extends AbstractRobot {

    @Override
    public RobotTypeEnum robotType() {
        return RobotTypeEnum.Local;
    }

    @Override
    public String url() {
        return RobotTypeEnum.Local.getUrl();
    }

    @Override
    public String sign() {
        return RobotTypeEnum.Local.getSign();
    }

    @Override
    protected String accessToken() {
        return RobotTypeEnum.Local.getSign();
    }
}
