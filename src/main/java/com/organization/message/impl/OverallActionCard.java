package com.organization.message.impl;

import com.organization.message.AbstractMessage;
import com.organization.message.Message;
import com.organization.message.enums.MessageTypeEnum;
import com.util.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


/**
 * <h1>整体跳转消息</h1>
 * <p>机器人名称、消息模板、首屏会话透出的展示内容、markdown格式的消息、0-正常发消息者头像，1-隐藏发消息者头像、0-按钮竖直排列，1-按钮横向排列、单个按钮的方案。(设置此项和singleURL后btns无效)、点击singleTitle按钮触发的URL、(@的人列表)<p/>
 *
 * @ClassName: OverallActionCard
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午7:09
 * @Description： 整体跳转消息
 * @Version： 1.0
 */
@Data
@Builder
public class OverallActionCard extends AbstractMessage {

    /**
     * 整体跳转的内容
     */
    private Content actionCard;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.OverallActionCard;
    }

    @Override
    public Message init(String[] params) {
        this.actionCard = new OverallActionCard.Content(
                formatterLn(params[NumberUtils.INTEGER_TWO]),
                params[NumberUtils.INTEGER_THREE],
                params[NumberUtils.INTEGER_FOUR],
                params[NumberUtils.INTEGER_FIVE],
                params[NumberUtils.INTEGER_SIX],
                params[NumberUtils.INTEGER_SEVEN]
        );
        this.initAt(params.length == NumberUtils.INTEGER_NINE ? params[NumberUtils.INTEGER_EIGHT] : StringUtils.EMPTY);
        return this;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Content {

        /**
         * 首屏会话透出的展示内容
         */
        private String title;

        /**
         * markdown格式的消息
         */
        private String text;

        /**
         * 0-正常发消息者头像，1-隐藏发消息者头像
         */
        private String hideAvatar;

        /**
         * 0-按钮竖直排列，1-按钮横向排列
         */
        private String btnOrientation;

        /**
         * 单个按钮的方案。(设置此项和singleURL后btns无效)
         */
        private String singleTitle;

        /**
         * 点击singleTitle按钮触发的URL
         */
        private String singleURL;

    }
}
