package com.organization.app;

import com.google.common.collect.Lists;
import com.organization.message.Message;
import com.organization.message.MessageFactory;
import com.organization.message.enums.MessageTypeEnum;
import com.organization.robot.Robot;
import com.organization.robot.RobotFactory;
import com.organization.robot.enums.RobotTypeEnum;
import com.util.HttpUtils;
import com.util.NumberUtils;

import java.util.List;

/**
 * @ClassName: RobotApp
 * @Author: yuanyixiong
 * @Date: 2020/3/11 下午1:12
 * @Description: TODO
 * @Version: 1.0
 */
public class RobotApp {

    /**
     * @param args 机器人名称、消息类型 ......(@的人列表)
     * @describe 消息类型的描述:Text、Link、MarkDown、OverallActionCard、IndependentActionCard、FeedCard
     * <h1>文本消息</h1>
     * <p>机器人名称、消息模板、文本消息、(@的人列表)<p/>
     * <h1>连接消息</h1>
     * <p>机器人名称、消息模板、消息标题、消息内容。如果太长只会部分展示、点击消息跳转的URL、图片URL、(@的人列表)<p/>
     * <h1>MarkDown消息</h1>
     * <p>机器人名称、消息模板、首屏会话透出的展示内容、markdown格式的消息、(@的人列表)<p/>
     * <h1>整体跳转消息</h1>
     * <p>机器人名称、消息模板、首屏会话透出的展示内容、markdown格式的消息、0-正常发消息者头像，1-隐藏发消息者头像、0-按钮竖直排列，1-按钮横向排列、单个按钮的方案。(设置此项和singleURL后btns无效)、点击singleTitle按钮触发的URL、(@的人列表)<p/>
     * <h1>独立跳转消息:参数存在部分JSON Array</h1>
     * <p>机器人名称、消息模板、首屏会话透出的展示内容、markdown格式的消息、0-正常发消息者头像，1-隐藏发消息者头像、0-按钮竖直排列，1-按钮横向排列、按钮的信息：title-按钮方案，actionURL-点击按钮触发的URL、[{title=单条信息标题,actionURL=单条跳转连接}]、(@的人列表)<p/>
     * <h1>FeedCard消息:参数存在部分JSON Array</h1>
     * <p>机器人名称、消息模板、[{title=单条信息文本,messageURL=点击单条信息到跳转链接,picURL=单条信息后面图片的URL}]、(@的人列表)<p/>
     */
    public static void main(String[] args) {

        // 如果只传递一个参数,则使用案例模拟
        if (NumberUtils.INTEGER_ONE.equals(args.length)) {
            args = simulateData(Integer.valueOf(args[NumberUtils.INTEGER_ZERO]));
        } else if (args.length < NumberUtils.INTEGER_THREE) {
            System.out.println("参数错误:机器人名称、消息类型 ......(@的人列表)");
            return;
        }

        // 获取机器人
        Robot robot = null;
        try {
            robot = RobotFactory.getRobotService(RobotTypeEnum.valueOf(args[NumberUtils.INTEGER_ZERO]));
        } catch (Exception e) {
            System.out.println("机器人不存在");
            return;
        }

        // 获取消息
        Message message = null;
        try {
            message = MessageFactory.getMessageService(MessageTypeEnum.valueOf(args[NumberUtils.INTEGER_ONE])).init(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("机器人消息类型错误");
            return;
        }

        // 发送消息
        try {
            String url = robot.getUrl();
            String json = message.toJSON();
            System.out.println(url);
            System.out.println(json);
            System.out.println("==============================================================================================================");
            HttpUtils.post(url, json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(NumberUtils.INTEGER_ZERO);
        }
    }

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/12 下午1:11
     * @Description: 模拟数据
     * @Param: [index]
     * @Version: 1.0
     * @Return: java.lang.String[]
     **/
    private static String[] simulateData(Integer index) {
        List<String[]> messages = Lists.newArrayList(

                /**Text**/
                new String[]{
                        "Local",
                        "Text",
                        "a~nb"
                },
                new String[]{
                        "Local",
                        "Text",
                        "1~n2",
                        "15926499574,15926499574"
                },

                /**Link**/
                new String[]{
                        "Local",
                        "Link",
                        "时代的火车向前开",
                        "这个即将发布的新版本，创始人xx称它为“红树林”。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林”？",
                        "http://www.baidu.com",
                        "https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI"
                },
                new String[]{
                        "Local",
                        "Link",
                        "时代的火车向前开",
                        "这个即将发布的新版本，创始人xx称它为“红树林”。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林”？",
                        "http://www.baidu.com",
                        "https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI",
                        "15926499574,15926499574"
                },

                /**MarkDown**/
                new String[]{
                        "Local",
                        "MarkDown",
                        "杭州天气",
                        "#### 杭州天气 @156xxxx8827> 9度，西北风1级，空气良89，相对温度73%> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)> ###### 10点20分发布 [天气](http://www.thinkpage.cn/)"
                },
                new String[]{
                        "Local",
                        "MarkDown",
                        "杭州天气",
                        "#### 杭州天气 @156xxxx8827> 9度，西北风1级，空气良89，相对温度73%> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)> ###### 10点20分发布 [天气](http://www.thinkpage.cn/)",
                        "15926499574,15926499574"
                },

                /**OverallActionCard**/
                new String[]{
                        "Local",
                        "OverallActionCard",
                        "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身",
                        "![screenshot](@lADOpwk3K80C0M0FoA)### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划",
                        "0",
                        "0",
                        "阅读全文",
                        "https://www.dingtalk.com/"
                },
                new String[]{
                        "Local",
                        "OverallActionCard",
                        "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身",
                        "![screenshot](@lADOpwk3K80C0M0FoA)### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划",
                        "0",
                        "0",
                        "阅读全文",
                        "https://www.dingtalk.com/",
                        "15926499574,15926499574"
                },

                /**IndependentActionCard**/
                new String[]{
                        "Local",
                        "IndependentActionCard",
                        "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身",
                        "![screenshot](@lADOpwk3K80C0M0FoA)### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划",
                        "0",
                        "0",
                        "[{\"title\":\"内容不错\",\"actionURL\":\"https://www.dingtalk.com/\"},{\"title\":\"不感兴趣\",\"actionURL\":\"https://www.dingtalk.com/\"}]"
                },
                new String[]{
                        "Local",
                        "IndependentActionCard",
                        "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身",
                        "![screenshot](@lADOpwk3K80C0M0FoA)### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划",
                        "0",
                        "0",
                        "[{\"title\":\"内容不错\",\"actionURL\":\"https://www.dingtalk.com/\"},{\"title\":\"不感兴趣\",\"actionURL\":\"https://www.dingtalk.com/\"}]",
                        "15926499574,15926499574"
                },

                /**FeedCard**/
                new String[]{
                        "Local",
                        "FeedCard",
                        "[{'title':'时代的火车向前开','messageURL':'https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI','picURL':'https://www.dingtalk.com/'},{'title':'时代的火车向前开2','messageURL':'https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI','picURL':'https://www.dingtalk.com/'}]"
                },
                new String[]{
                        "Local",
                        "FeedCard",
                        "[{'title':'时代的火车向前开','messageURL':'https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI','picURL':'https://www.dingtalk.com/'},{'title':'时代的火车向前开2','messageURL':'https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI','picURL':'https://www.dingtalk.com/'}]",
                        "15926499574,15926499574"
                }
        );
        return messages.get(index);
    }
}
