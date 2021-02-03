package com.example.spring_mybatis.connection;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * ES Cleint class
 */
public class ESClient {
	private static RestHighLevelClient client;
	
	 /** 协议 */
     private final static String schema="http";
 
    /** 集群地址，如果有多个用“,”隔开 */
    private final static String address="127.0.0.1:9200";
 
    /** 连接超时时间 */
    private final static int connectTimeout=5000;
 
    /** Socket 连接超时时间 */
    private final static int socketTimeout=5000;
 
    /** 获取连接的超时时间 */
    private final static int connectionRequestTimeout=5000;
 
    /** 最大连接数 */
    private final static int maxConnectNum=100;
 
    /** 最大路由连接数 */
    private final static int maxConnectPerRoute=100;

	public static RestHighLevelClient getClient() throws UnknownHostException {
		if(null==client) {
			client=restHighLevelClient();
		}
		return client;
	}
	
	private static RestHighLevelClient restHighLevelClient() {
        // 拆分地址
        List<HttpHost> hostLists = new ArrayList<>();
        String[] hostList = address.split(",");
        for (String addr : hostList) {
            String host = addr.split(":")[0];
            String port = addr.split(":")[1];
            hostLists.add(new HttpHost(host, Integer.parseInt(port), schema));
        }
        // 转换成 HttpHost 数组
        HttpHost[] httpHost = hostLists.toArray(new HttpHost[]{});
        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);
        // 异步连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
            requestConfigBuilder.setSocketTimeout(socketTimeout);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
            return requestConfigBuilder;
        });
        // 异步连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }
}
