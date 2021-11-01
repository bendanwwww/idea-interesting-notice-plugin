package com.work.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.work.result.ResultException;

/**
 * HttpClient工具类
 *
 * @author liushuoyang
 * @Description 2019年1月30日
 */
public class ApacheHttpTool {

    private static final Logger log = LoggerFactory.getLogger(ApacheHttpTool.class);

    // client 对象
    private static CloseableHttpClient httpClient;
    // 连接池
    private static PoolingHttpClientConnectionManager pool;
    // 请求默认配置
    private static RequestConfig requestConfig;

    /** 默认超时参数 */
    // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 500;
    // 请求等待返回超时时间
    private static final int SOCKET_TIMEOUT = 1500;
    // 连接池连接占满时等待超时时间
    private static final int CONN_MANAGER_TIMEOUT = 500;

    /** 调用失败重试次数 */
    private static final int COUNT = 1;

    /** 默认连接池参数 */
    // 连接池最大连接数
    private static final int MAX_TOTAL = 300;
    // 同一主机的最大连接数
    private static final int MAX_PER_ROUTE = 200;


    static {
        pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(MAX_TOTAL);
        pool.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT)
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(pool)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new MyHttpRequestRetryHandler()).build();
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static Result httpGet(String url, Map<String, String> header, Map<String, String> params) {
        return httpGet(url, header, params, null);
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static Result httpGet(String url, Map<String, String> header, Map<String, String> params,
                                 int connectionTimeout, int socketTimeout) {
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT)
                .build();
        return httpGet(url, header, params, requestConfig);
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static Result httpGet(String url, Map<String, String> headers, Map<String, String> params, RequestConfig requestConfig) {
        // 拼装url
        url = url + appendParams(params);

        HttpGet get = new HttpGet(url);
        if(requestConfig != null) {
            get.setConfig(requestConfig);
        }

        // 构造消息头
        List<String> headerKey = new ArrayList<String>(headers.keySet());
        for(String key : headerKey) {
            get.setHeader(key, headers.get(key));
        }

        try {
            long startTime = System.currentTimeMillis();
            log.info("httpClient.get url: {}, [request.headers]: {}, [request.body]: {}", url, JSON.toJSONString(headers), JSON.toJSONString(params));
            HttpResponse response = httpClient.execute(get);
            String responseBody = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
            log.info("httpClient.get[response]: {} ,请求耗时:[{}]ms", responseBody, (System.currentTimeMillis() - startTime));

            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK) {
                throw new ResultException("http返回状态异常:" + statusCode + ":" + responseBody);
            }
            Result result = new Result(statusCode, responseBody);
            return result;
        } catch (Exception e) {
            throw new ResultException(e.getMessage());
        }finally {
            if(get != null) {
                get.releaseConnection();
            }
        }
    }

    /**
     * 发送post form请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static Result httpPostWithForm(String url, Map<String, String> header, Map<String, String> params) {
        return httpPostWithForm(url, header, params, null);
    }

    /**
     * 发送post form请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static Result httpPostWithForm(String url, Map<String, String> header, Map<String, String> params,
                                          int connectionTimeout, int socketTimeout) {
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT)
                .build();
        return httpPostWithForm(url, header, params, requestConfig);
    }

    /**
     * 发送post form请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static Result httpPostWithForm(String url, Map<String, String> headers, Map<String, String> params, RequestConfig requestConfig) {
        HttpPost post = new HttpPost(url);
        if(requestConfig != null) {
            post.setConfig(requestConfig);
        }
        // 构造消息头
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        List<String> headerKey = new ArrayList<String>(headers.keySet());
        for(String key : headerKey) {
            post.setHeader(key, headers.get(key));
        }
        // 构建请求参数
        List<NameValuePair> paramList = new ArrayList <NameValuePair>();
        if(!params.isEmpty()) {
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        StringEntity entity = new UrlEncodedFormEntity(paramList, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送form格式的数据请求
        entity.setContentType("application/x-www-form-urlencoded");
        post.setEntity(entity);

        try {
            long startTime = System.currentTimeMillis();
            log.info("httpClient.post url: {}, [request.headers]: {}, [request.body]: {}", url, JSON.toJSONString(headers), JSON.toJSONString(params));
            HttpResponse response = httpClient.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());
            log.info("httpClient.post[response]: {} ,请求耗时:[{}]ms", responseBody, (System.currentTimeMillis() - startTime));
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK) {
                throw new ResultException("http返回状态异常:" + statusCode + ":" + responseBody);
            }
            Result result = new Result(statusCode, responseBody);
            return result;
        } catch (Exception e) {
            throw new ResultException(e.getMessage());
        }finally{
            if(post != null) {
                post.releaseConnection();
            }
        }
    }

    /**
     * 发送post json请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static Result httpPostWithJson(String url, Map<String, String> header, Object params) {
        return httpPostWithJson(url, header, params, null);
    }

    /**
     * 发送post json请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static Result httpPostWithJsonWithTimeOut(String url, Map<String, String> header, Object params,
                                          int connectionTimeout, int socketTimeout) {
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT)
                .build();
        return httpPostWithJson(url, header, params, requestConfig);
    }

    /**
     * 发送post json请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static Result httpPostWithJson(String url, Map<String, String> headers, Object params, RequestConfig requestConfig) {
        HttpPost post = new HttpPost(url);
        if(requestConfig != null) {
            post.setConfig(requestConfig);
        }
        // 构造消息头
        post.setHeader("Content-type", "application/json; charset=utf-8");
        List<String> headerKey = new ArrayList<String>(headers.keySet());
        for(String key : headerKey) {
            post.setHeader(key, headers.get(key));
        }
        String paramsStr = null;
        if(params instanceof String){
            paramsStr = (String)params;
        }else{
            paramsStr = JSON.toJSONString(params);
        }
        // 构建消息实体
        StringEntity entity  = new StringEntity(paramsStr, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        post.setEntity(entity);

        try {
            long startTime = System.currentTimeMillis();
            log.info("httpClient.post url: {}, [request.headers]: {}, [request.body]: {}", url, JSON.toJSONString(headers), JSON.toJSONString(paramsStr));
            HttpResponse response = httpClient.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());
            log.info("httpClient.post[response]: {} ,请求耗时:[{}]ms", responseBody, (System.currentTimeMillis() - startTime));
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK) {
                throw new ResultException("http返回状态异常:" + statusCode + ":" + responseBody);
            }
            Result result = new Result(statusCode, responseBody);
            return result;
        } catch (Exception e) {
            throw new ResultException(e.getMessage());
        }finally{
            if(post != null) {
                post.releaseConnection();
            }
        }
    }

    // 构建唯一会话Id
    private static String getSessionId() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }

    // 拼接请求参数
    public static String appendParams(Map<String, String> params) {
        if(params != null && params.size() > 0){
            Set<String> paramKey = params.keySet();
            Iterator<String> it = paramKey.iterator();
            List<String> paramList = new ArrayList<String>();
            while(it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                paramList.add(key + "=" + value);
            }
            return "?" + paramList.stream().collect(Collectors.joining("&"));
        }else{
            return "";
        }
    }


    /**
     * 设置重试
     */
    static class MyHttpRequestRetryHandler implements HttpRequestRetryHandler {

        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount > COUNT) { // 超过重试次数，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 没有响应, 重试
                log.info("没有响应，重试1次");
                return true;
            } else if (exception instanceof ConnectTimeoutException) {// 连接超时, 重试
                log.info("连接超时，重试1次");
                return true;
            } else if (exception instanceof SocketTimeoutException) {// 连接或读取超时, 重试
                log.info("连接或读取超时，重试1次");
                return true;
            } else if (exception instanceof UnknownHostException) {// 找不到服务器
                log.info("找不到服务器，重试1次");
                return true;
            } else if (exception instanceof SSLHandshakeException) {// 本地证书异常
                return false;
            } else if (exception instanceof InterruptedIOException) {// 被中断
                return false;
            } else if (exception instanceof SSLException) {// SSL异常
                return false;
            } else {
                log.error("未记录的请求异常：" + exception.getClass());
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，则重试
            if (!(request instanceof HttpEntityEnclosingRequest)) return true;
            return false;
        }
    }

    /**
     * http返回结果
     */
    public static class Result implements Serializable {

        private static final long serialVersionUID = -5122451672072932559L;

        /**
         * response code
         */
        private int code;
        /**
         * body信息文本
         */
        private String body;
        /**
         * response code 200 标志
         */
        private boolean ok;

        public Result() {
        }

        Result(int code, String body) {
            this.code = code;
            this.body = body;
            this.ok = code == HttpStatus.SC_OK;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public boolean isOk() {
            return ok;
        }

        public void setOk(boolean ok) {
            this.ok = ok;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "code=" + code +
                    ", body='" + body + '\'' +
                    ", ok=" + ok +
                    '}';
        }
    }
}
