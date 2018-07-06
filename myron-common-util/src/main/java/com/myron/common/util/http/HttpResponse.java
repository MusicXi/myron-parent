package com.myron.common.util.http;

import org.apache.http.Header;

import java.util.Arrays;

/**
 * http响应结果封装
 * Created by linrx1 on 2018/6/19.
 */
public class HttpResponse {

    private String body;
    private Header[] headers;
    private String reasonPhrase;
    private Integer statusCode;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "body='" + body + '\'' +
                ", headers=" + Arrays.toString(headers) +
                ", reasonPhrase='" + reasonPhrase + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
