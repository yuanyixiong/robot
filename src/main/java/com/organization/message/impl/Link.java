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
 * <h1>连接消息</h1>
 * <p>机器人名称、消息模板、消息标题、消息内容。如果太长只会部分展示、点击消息跳转的URL、图片URL、(@的人列表)<p/>
 *
 * @ClassName: Link
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午7:08
 * @Description： 连接消息
 * @Version： 1.0
 */
@Data
@Builder
public class Link extends AbstractMessage {

    /**
     * 链接内容
     */
    private Content link;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.Link;
    }

    @Override
    public Message init(String[] params) {
        this.link = new Link.Content(
                formatterLn(params[NumberUtils.INTEGER_TWO]),
                formatterLn(params[NumberUtils.INTEGER_THREE]),
                params[NumberUtils.INTEGER_FOUR],
                params[NumberUtils.INTEGER_FIVE]
        );
        this.initAt(params.length == NumberUtils.INTEGER_SEVEN ? params[NumberUtils.INTEGER_SIX] : StringUtils.EMPTY);
        return this;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Content {

        /**
         * 消息标题
         */
        private String title;

        /**
         * 消息内容。如果太长只会部分展示
         */
        private String text;

        /**
         * 点击消息跳转的URL
         */
        private String messageUrl;

        /**
         * 图片URL
         */
        private String picUrl;
    }
}
