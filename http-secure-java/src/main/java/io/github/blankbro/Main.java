package io.github.blankbro;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
        // 获取默认配置的 HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 请求路径及参数
        String url = "https://www.baidu.com";

        // 创建 GET 请求对象
        HttpGet httpGet = new HttpGet(url);
        // 调用 HttpClient 的 execute 方法执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 获取请求状态
        int code = response.getCode();
        // 如果请求成功
        if (code == HttpStatus.SC_OK) {
            log.info("响应结果为：{}", EntityUtils.toString(response.getEntity()));
        }

    }
}