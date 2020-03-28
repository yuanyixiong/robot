package com.organization.message;

import com.organization.message.enums.MessageTypeEnum;

/**
 * @InterfaceName: Message
 * @Author: yuanyixiong
 * @Date: 2020/3/10 上午12:59
 * @Description: 消息
 * @Version: 1.0
 */
public interface Message {

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午4:14
     * @Description: 消息
     * @Param: []
     * @Version: 1.0
     * @Return: com.organization.message.enums.MessageTypeEnum
     **/
    MessageTypeEnum messageType();
    
    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/12 下午12:45
     * @Description: 初始化数据
     * @Param: [params]
     * @Version: 1.0
     * @Return: com.organization.message.Message
     **/
    Message init(String[] params);

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/12 下午12:45
     * @Description: 消息转换成JSON
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    String toJSON();
}
