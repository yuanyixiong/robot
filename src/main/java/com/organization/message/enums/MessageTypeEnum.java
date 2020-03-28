package com.organization.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: MessageTypeEnum
 * @Author： yuanyixiong
 * @Date： 2020/3/10 上午1:12
 * @Description： 消息枚举
 * @Version： 1.0
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    Text("text", "文本消息"),

    Link("link", "链接消息"),

    MarkDown("markdown", "支持MarkDown消息"),

    OverallActionCard("actionCard", "整体跳转消息"),

    IndependentActionCard("actionCard", "独立跳转消息"),

    FeedCard("feedCard", "FeedCard消息");

    private String key;

    private String describe;
}
