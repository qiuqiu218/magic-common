package com.magic.api.commons.core.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.magic.api.commons.core.context.Client;
import com.magic.api.commons.core.context.ClientVersion;
import com.magic.api.commons.core.context.RequestContext;
import com.magic.api.commons.ApiLogger;
import com.magic.api.commons.tools.HeaderUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求日志处理
 * @author zz
 */
public class HttpRequestTraceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContext requestContext = RequestContext.getRequestContext();
        Client client = requestContext.getClient();
        requestContext.setRequest(request);
        requestContext.setResponse(response);
        RequestLogRecord requestLogRecord = requestContext.getRequestLogRecord();
        requestLogRecord.setStartTime(System.currentTimeMillis());
        requestLogRecord.setRequestId(HeaderUtil.getRequestId(request));
        requestLogRecord.setApi(request.getRequestURI());
        requestLogRecord.setMethod(request.getMethod());
        requestLogRecord.setReferer(HeaderUtil.getReferer(request));
        int appId = HeaderUtil.getAppId(request);
        requestLogRecord.setAppid(appId);
        client.setAppId(appId);
        String clientType = HeaderUtil.getClientType(request);
        requestLogRecord.setPlatform(clientType);
        client.setClientType(Client.ClientType.get(clientType));
        requestLogRecord.setParameters(request.getParameterMap());
        String ip = HeaderUtil.getIp(request);
        requestLogRecord.setOriginalIp(ip);
        requestContext.setIp(ip);
        requestLogRecord.setRequestIp(request.getRemoteAddr());
        ClientVersion clientVersion = new ClientVersion(HeaderUtil.getClientVersion(request));
        requestLogRecord.setClientVersion(clientVersion);
        client.setClientVersion(clientVersion);
        String clientNdeviceId = HeaderUtil.getClientNdeviceId(request);
        requestLogRecord.setNdeviceid(clientNdeviceId);
        client.setDeviceId(clientNdeviceId);
        requestLogRecord.setUserAgent(HeaderUtil.getUserAgent(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestLogRecord requestLogRecord = RequestContext.getRequestContext().getRequestLogRecord();
        requestLogRecord.setPage(null != modelAndView);
        if (null != modelAndView) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("view", modelAndView.getViewName());
            jsonObject.put("model", modelAndView.getModel());
            requestLogRecord.setResponse(JSON.toJSONString(jsonObject));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestLogRecord requestLogRecord = RequestContext.getRequestContext().getRequestLogRecord();
        if (null == ex) {
            requestLogRecord.setResponseStatus(200);
        } else {
            requestLogRecord.setResponseStatus(500);
        }
        ApiLogger.requset(requestLogRecord.toString());
        super.afterCompletion(request, response, handler, ex);
        RequestContext.clearRequestContext();
    }

}