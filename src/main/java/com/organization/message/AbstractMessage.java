package com.organization.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: AbstractMessage
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午4:10
 * @Description： 消息
 * @Version： 1.0
 */
@Data
public abstract class AbstractMessage implements Message {

    /**
     * 消息类型
     */
    private String msgtype;

    /**
     * @的人
     */
    private At at;

    @Override
    public String toJSON() {
        return JSON.toJSONString(this);
    }

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午8:41
     * @Description: 初始化@的用户
     * @Param: [iphone]
     * @Version: 1.0
     * @Return: void
     **/
    public void initAt(String iphone) {
        List<String> mobileList = Lists.newLinkedList();
        if (StringUtils.isNoneBlank(iphone)) {
            mobileList.addAll(Arrays.stream(iphone.split(",")).collect(Collectors.toList()));
        }
        this.msgtype = messageType().getKey();
        this.at = new At(CollectionUtils.isEmpty(mobileList), mobileList);
    }

    /**
     * @ClassName: com.organization.app.Robot.Text.At
     * @Author: yuanyixiong
     * @Date: 2020/3/10 上午12:21
     * @Description: 通知的人
     * @Version: 1.0
     */
    @Data
    @AllArgsConstructor
    class At {

        /**
         * @所有人时：true，否则为：false
         */
        @JSONField(name = "isAtAll")
        private boolean isAtAll;

        /**
         * 被@人的手机号(在text内容里要有@手机号)
         */
        private List<String> atMobiles;

    }

    /**
     * <p>处理换行问题~n为换行符</p>
     *
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午4:12
     * @Description: 处理Content格式
     * @Param: [content] = [传输的内容]
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    protected String formatterLn(String content) {
        return StringUtils.isNoneBlank(content) ? content.trim().replace("~n", "\n") : StringUtils.EMPTY;
    }
}
