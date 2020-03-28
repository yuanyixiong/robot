package com.organization.message.impl;

import com.organization.message.AbstractMessage;
import com.organization.message.Message;
import com.organization.message.enums.MessageTypeEnum;
import com.util.NumberUtils;
import lombok.*;
import org.apache.commons.lang3.StringUtils;


/**
 * <h1>文本消息</h1>
 * <p>机器人名称、消息模板、文本消息、(@的人列表)<p/>
 *
 * @ClassName: Text
 * @Author： yuanyixiong
 * @Date： 2020/3/10 上午12:55
 * @Description： 文本消息
 * @Version： 1.0
 */
@Data
@Builder
public class Text extends AbstractMessage {

    /**
     * 消息内容
     */
    private Content text;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.Text;
    }

    @Override
    public Message init(String[] params) {
        this.text = new Text.Content(formatterLn(params[NumberUtils.INTEGER_TWO]));
        super.initAt(params.length == NumberUtils.INTEGER_FOUR ? params[NumberUtils.INTEGER_THREE] : StringUtils.EMPTY);
        return this;
    }

    /**
     * @ClassName: com.organization.app.Robot.Text.Content
     * @Author: yuanyixiong
     * @Date: 2020/3/10 上午12:21
     * @Description: 消息内容
     * @Version: 1.0
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Content {

        /**
         * 消息文本
         */
        private String content;
    }
}
