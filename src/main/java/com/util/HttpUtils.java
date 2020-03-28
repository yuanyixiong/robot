package com.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @ClassName: HttpUtils
 * @Author： yuanyixiong
 * @Date： 2020/3/10 上午12:54
 * @Description： TODO
 * @Version： 1.0
 */
public class HttpUtils {

    /**
     * <p>发送post请求</p>
     *
     * @Author: yuanyixiong
     * @Date: 2020/3/11 下午1:31
     * @Description: TODO
     * @Param: [url, jsonParam] = [(请求地址),(请求的参数 JSON)]
     * @Version: 1.0
     * @Return: void
     **/
    public static void post(String url, String jsonParam) throws IOException {

        System.out.println(jsonParam);
        System.out.println(url);

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonParam, "UTF-8");

        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        try {

            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            System.out.println(String.format("响应状态为:%s", response.getStatusLine()));
            if (responseEntity != null) {
                System.out.println(String.format("响应内容长度为:%s", responseEntity.getContentLength()));
                System.out.println(String.format("响应内容为:%s", EntityUtils.toString(responseEntity)));
            }
        } catch (IOException e) {
            System.out.println(String.format("IOException %s", e.getMessage()));
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                System.out.println(String.format("IOException %s", e.getMessage()));
            }
        }
    }
}
