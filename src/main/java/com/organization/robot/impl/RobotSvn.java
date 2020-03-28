package com.organization.robot.impl;

import com.organization.robot.AbstractRobot;
import com.organization.robot.enums.RobotTypeEnum;
import lombok.Builder;

/**
 * @ClassName: RobotSvn
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午3:46
 * @Description： TODO
 * @Version： 1.0
 */
@Builder
public class RobotSvn extends AbstractRobot {

    @Override
    public RobotTypeEnum robotType() {
        return RobotTypeEnum.SVN;
    }

    @Override
    public String url() {
        return RobotTypeEnum.SVN.getUrl();
    }

    @Override
    public String sign() {
        return RobotTypeEnum.SVN.getSign();
    }

    @Override
    protected String accessToken() {
        return RobotTypeEnum.SVN.getSign();
    }
}
