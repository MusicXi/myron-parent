package com.myron.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by linrx1 on 2018/9/28.
 */
public class RequestUtils {
    /**
     * 构建请求参数
     * <ul>
     * <li>优先获取request中的请求参数,使用于get等方法</li>
     * <li>request中无参数时,获取连接点参数,间接获取post类型请求体参数</li>
     * </ul>
     * @param paramMap request.getParameterMap(); //请求提交的参数
     * @param args joinPoint.getArgs(); //连接点获取请求参数
     * @return
     */
    private String buildRequestParams(Map<String, String[]> paramMap, Object[] args) {
        StringBuilder params = new StringBuilder();
        // post 请求体json参数
        if (CollectionUtils.isEmpty(paramMap)) {
            for (Object obj : args) {
                if(! (obj instanceof Serializable)) {
                    continue;
                }
                params.append(JacksonUtils.obj2jsonIgnoreNull(obj));
            }
            // get 请求参数
        } else {
            for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
                params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
                String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
                params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
            }
        }
        return params.toString();
    }

    /**
     * 是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String xhr = req.getHeader("X-Requested-With");
        //ajax请求
        if("XMLHttpRequest".equalsIgnoreCase(xhr)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 为response设置header，实现跨域
     */
    public static void setHeader(HttpServletRequest request,HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }

    /*		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String origin = httpRequest.getHeader(ORIGIN);
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");/*//* or origin as u prefer
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "content-type, authorization");


		if (httpRequest.getMethod().equals("OPTIONS")) {
			httpResponse.setStatus(HttpServletResponse.SC_OK);
		}*/
}
