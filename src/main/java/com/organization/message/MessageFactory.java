package com.organization.message;

import com.google.common.collect.Lists;
import com.organization.message.enums.MessageTypeEnum;
import com.organization.message.impl.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: MessageFactory
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午4:09
 * @Description： 消息工厂
 * @Version： 1.0
 */
public class MessageFactory {

    private static List<AbstractMessage> messageList = Lists.newArrayList(
            Text.builder().build(),
            Link.builder().build(),
            MarkDown.builder().build(),
            IndependentActionCard.builder().build(),
            OverallActionCard.builder().build(),
            FeedCard.builder().build()
    );

    private static Map<MessageTypeEnum, Message> MESSAGE_MAP;

    static {
        MESSAGE_MAP = Optional.ofNullable(messageList.stream().collect(Collectors.toMap(AbstractMessage::messageType, e -> (Message) e))).orElse(null);
    }

    /***
     * @Author: yuanyixiong
     * @Date: 2020/3/12 下午12:45
     * @Description: 获取消息
     * @Param: [typeEnum]
     * @Version: 1.0
     * @Return: com.organization.message.Message
     **/
    public static Message getMessageService(MessageTypeEnum typeEnum) {
        return MESSAGE_MAP.get(typeEnum);
    }
}
