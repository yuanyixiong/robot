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
 * <h1>MarkDown消息</h1>
 * <p>机器人名称、消息模板、首屏会话透出的展示内容、markdown格式的消息、(@的人列表)<p/>
 *
 * @ClassName: MarkDown
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午7:09
 * @Description： MarkDown消息
 * @Version： 1.0
 */
@Data
@Builder
public class MarkDown extends AbstractMessage {

    /**
     * MarkDown内容
     */
    private Content markdown;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.MarkDown;
    }

    @Override
    public Message init(String[] params) {
        this.markdown = new MarkDown.Content(
                formatterLn(params[NumberUtils.INTEGER_TWO]),
                params[NumberUtils.INTEGER_THREE]
        );
        this.initAt(params.length == NumberUtils.INTEGER_FIVE ? params[NumberUtils.INTEGER_FOUR] : StringUtils.EMPTY);
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
    }
}
