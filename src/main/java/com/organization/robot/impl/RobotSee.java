package com.organization.robot.impl;

import com.organization.robot.AbstractRobot;
import com.organization.robot.enums.RobotTypeEnum;
import lombok.Builder;

/**
 * @ClassName: RobotSee
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午3:46
 * @Description： TODO
 * @Version： 1.0
 */
@Builder
public class RobotSee extends AbstractRobot {

    @Override
    public RobotTypeEnum robotType() {
        return RobotTypeEnum.SEE;
    }

    @Override
    public String url() {
        return RobotTypeEnum.SEE.getUrl();
    }

    @Override
    public String sign() {
        return RobotTypeEnum.SEE.getSign();
    }

    @Override
    protected String accessToken() {
        return RobotTypeEnum.SEE.getSign();
    }
}
