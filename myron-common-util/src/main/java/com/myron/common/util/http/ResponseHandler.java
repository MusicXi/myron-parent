package com.myron.common.util.http;

/**
 * 自定义处理httpclient的response响应
 * @author linrx1
 * @date 2018/6/22.
 */
public interface ResponseHandler {

    /**
     * 处理响应结果
     * @param response
     * @return
     */
    String handle(HttpResponse response) throws Exception;
}
