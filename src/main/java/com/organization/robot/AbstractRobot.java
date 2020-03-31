package com.organization.robot;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * @ClassName: AbstractRobot
 * @Author： yuanyixiong
 * @Date： 2020/3/11 下午3:52
 * @Description： 机器人
 * @Version： 1.0
 */
public abstract class AbstractRobot implements Robot {

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/12 下午12:59
     * @Description: 接口地址
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    protected abstract String url();

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午10:39
     * @Description: 原始签名
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    protected abstract String sign();

    /**
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午11:07
     * @Description: access_token
     * @Param: []
     * @Version: 1.0
     * @Return: java.lang.String
     **/
    protected abstract String accessToken();

    @Override
    public String getUrl() {
        String url;
        if (StringUtils.isNoneBlank(sign())) {
            // 使用签名
            String sign = StringUtils.EMPTY;
            Long timestamp = System.currentTimeMillis();
            try {
                String stringToSign = timestamp + "\n" + sign();
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(sign().getBytes("UTF-8"), "HmacSHA256"));
                byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
                sign = null;//URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("签名加密失败");
            }
            url = String.format("%s?access_token=%s&timestamp=%s&sign=%s", url(), accessToken(), sign, timestamp);
        } else {
            // 使用关键字
            url = String.format("%s?access_token=%s", url(), accessToken());
        }
        return url;
    }

}
