package com.organization.message.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.organization.message.AbstractMessage;
import com.organization.message.Message;
import com.organization.message.enums.MessageTypeEnum;
import com.util.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <h>独立跳转消息</h>
 * <p>机器人名称、消息模板、首屏会话透出的展示内容、markdown格式的消息、0-正常发消息者头像，1-隐藏发消息者头像、0-按钮竖直排列，1-按钮横向排列、按钮的信息：title-按钮方案，actionURL-点击按钮触发的URL、[{title=单条信息标题,actionURL=单条跳转连接}]、(@的人列表)<p/>
 *
 * @ClassName: IndependentActionCard
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午7:09
 * @Description： 独立跳转消息
 * @Version： 1.0
 */
@Data
@Builder
public class IndependentActionCard extends AbstractMessage {

    /**
     * 独立跳转的内容
     */
    private Content actionCard;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.IndependentActionCard;
    }

    @Override
    public Message init(String[] params) {
        List<IndependentActionCard.Btn> btns = Lists.newLinkedList();
        JSONArray jsonArray = JSONArray.parseArray(params[NumberUtils.INTEGER_SIX]);
        for (int i = NumberUtils.INTEGER_ZERO; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            IndependentActionCard.Btn btn = new IndependentActionCard.Btn(
                    json.getString("title"),
                    json.getString("actionURL")
            );
            btns.add(btn);
        }
        btns.forEach(btn -> btn.setTitle(formatterLn(btn.getTitle())));

        this.actionCard = new IndependentActionCard.Content(
                formatterLn(params[NumberUtils.INTEGER_TWO]),
                params[NumberUtils.INTEGER_THREE],
                params[NumberUtils.INTEGER_FOUR],
                params[NumberUtils.INTEGER_FIVE],
                btns
        );
        this.initAt(params.length == NumberUtils.INTEGER_EIGHT ? params[NumberUtils.INTEGER_SEVEN] : StringUtils.EMPTY);
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
         * 按钮的信息：title-按钮方案，actionURL-点击按钮触发的URL
         */
        private List<Btn> btns;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Btn {

        /**
         * 单条信息标题
         */
        private String title;

        /**
         * 单条跳转连接
         */
        private String actionURL;

    }
}
