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
 * <h>FeedCard消息</h>
 * <p>机器人名称、消息模板、[{title=单条信息文本,messageURL=点击单条信息到跳转链接,picURL=单条信息后面图片的URL}]、(@的人列表)<p/>
 *
 * @ClassName: FeedCard
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午7:09
 * @Description： FeedCard消息
 * @Version： 1.0
 */
@Data
@Builder
public class FeedCard extends AbstractMessage {

    /**
     * feedCard 内容
     */
    private Content feedCard;

    @Override
    public MessageTypeEnum messageType() {
        return MessageTypeEnum.FeedCard;
    }

    @Override
    public Message init(String[] params) {
        List<FeedCard.Link> links = Lists.newArrayList();
        JSONArray jsonArray = JSONArray.parseArray(params[NumberUtils.INTEGER_TWO]);
        for (int i = NumberUtils.INTEGER_ZERO; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            FeedCard.Link link = new FeedCard.Link(
                    json.getString("title"),
                    json.getString("messageURL"),
                    json.getString("picURL")
            );
            links.add(link);
        }
        links.forEach(link -> link.setTitle(formatterLn(link.getTitle())));

        this.feedCard = new FeedCard.Content(links);
        this.initAt(params.length == NumberUtils.INTEGER_FOUR ? params[NumberUtils.INTEGER_THREE] : StringUtils.EMPTY);
        return this;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Content {

        /**
         * 列表
         */
        private List<Link> links;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Link {

        /**
         * 单条信息文本
         */
        private String title;

        /**
         * 点击单条信息到跳转链接
         */
        private String messageURL;

        /**
         * 单条信息后面图片的URL
         */
        private String picURL;

    }
}
