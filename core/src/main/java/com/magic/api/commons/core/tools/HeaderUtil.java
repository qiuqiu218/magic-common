package com.magic.api.commons.core.tools;

import com.magic.api.commons.ApiLogger;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Header工具
 * @author zz
 */
public class HeaderUtil {

    /**
     * APPID 区分实惠与其他平台 5实惠 100-10000SaaS渠道
     */
    public static final String APP_ID = "X-APP-ID";

    /**
     * header中mauth 现统一使用
     */
    public static final String AUTH = "Authorization";

    /**
     * H5之前未统一
     */
    public static final String AUTH_HEADER_OTHER = "mauth";

    /**
     * H5之前未统一
     */
    public static final String AUTH_PARAM = "mauth";

    /**
     * 客户端版本
     * SDK版本-客户端版本-设备ID(标记设备)-设备型号(手机型号)-渠道
     * 示例1.0.5-2.2.1-74a8da5c0e192a27-Android_MediaDemo-textChannel
     */
    public static final String CLIENT_VERSION = "X-WVersion";


    /**
     * 客户端类型 ios android
     * 字符串约定版本-APPID-验证APPID的token-设备平台
     * 示例1-1-8eda6533c616a1ffdf57154550e87c83-android
     */
    public static final String CLIENT_TYPE = "X-Client-ID";

    /**
     * 客户端设备ID
     */
    public static final String NDEVICE_ID = "ndeviceid";

    /**
     * User-Agent
     */
    public static final String USER_AGENT = "User-Agent";

    /**
     * 实惠用户ID
     */
    public static final String MATRIX_UID = "X-Matrix-UID";
    
    /**
     * RequestId
     */
    public static final String REQUEST_ID = "X-RequestID";

    /**
     * x-forwarded-for
     */
    public static final String X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * X-Real-IP
     */
    public static final String X_REAL_IP = "X-Real-IP";

    /**
     * REFERER
     */
    public static final String REFERER = "Referer";

    /**
     * REFERER
     */
    public static final String ORIGIN = "Origin";


    /**
     * header中用户名
     */
    public static final String HEADER_USER = "H-U";

    /**
     * 用户在Session中Key
     */
    public static final String HEADER_PASSWORD = "H-P";

    /**
     * header中的资源ID
     */
    public static final String HEADER_SOURCE_ID = "H_SOURCE_ID";

    /**
     * header中的资源url
     */
    public static final String HEADER_SOURCE_URL = "H_SOURCE_URL";

    /**
     * header connection
     */
    public static final String HEADER_CONNECTION = "Connection";

    /**
     * 分隔符
     */
    public static final String SPLIT = "-";

    /**
     * 获取APPID 区分实惠与其他平台 5实惠 100-10000SaaS渠道
     * @param request   HttpServletRequest
     * @return  APPID
     */
    public static int getAppId(HttpServletRequest request) {
        String appid = request.getHeader(APP_ID);
        return StringUtils.isNoneBlank(appid) ? Integer.valueOf(appid) : -1;
    }

    /**
     * 获取Mauth
     * @param request   HttpServletRequest
     * @return Mauth
     */
    public static String getMauth(HttpServletRequest request) {
        String mauth = request.getHeader(AUTH);
        if (StringUtils.isNoneBlank(mauth)) {
            return mauth;
        }
        mauth = request.getHeader(AUTH_HEADER_OTHER);
        if (StringUtils.isNoneBlank(mauth)) {
            return mauth;
        }
        mauth = request.getParameter(AUTH_PARAM);
        if (StringUtils.isNoneBlank(mauth)) {
            return mauth;
        }
        return null;
    }

    /**
     * 获取客户端版本
     * @param request   HttpServletRequest
     * @return 客户端版本
     */
    public static String getClientVersion(HttpServletRequest request) {
        String version = request.getHeader(CLIENT_VERSION);
        if (StringUtils.isEmpty(version)) {
            return null;
        }
        try {
            String[] split = version.split(SPLIT);
            if (2 < split.length) {
                return split[1];
            }
        } catch (Exception e) {
            ApiLogger.error("获取客户端版本发生异常", e);
        }
        return null;
    }

    /**
     * 获取客户端类型
     * @param request   HttpServletRequest
     * @return 客户端版本
     */
    public static String getClientType(HttpServletRequest request) {
        String clientType = request.getHeader(CLIENT_TYPE);
        if (StringUtils.isEmpty(clientType)) {
            return null;
        }
        try {
            String[] split = clientType.split(SPLIT);
            if (0 < split.length) {
                return split[split.length - 1];
            }
        } catch (Exception e) {
            ApiLogger.error("解析客户端类型发生异常", e);
        }
        return null;
    }

    /**
     * 获取客户端版本
     * @param request   HttpServletRequest
     * @return 客户端版本
     */
    public static String getClientNdeviceId(HttpServletRequest request) {
        String clientType = request.getHeader(NDEVICE_ID);
        return StringUtils.isNoneBlank(clientType) ? clientType : null;
    }

    /**
     * 获取User-Agent
     * @param request   HttpServletRequest
     * @return User-Agent
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(USER_AGENT);
        return StringUtils.isNoneBlank(userAgent) ? userAgent : null;
    }

    /**
     * 获取User-Agent
     * @param request   HttpServletRequest
     * @return User-Agent
     */
    public static String getzbUid(HttpServletRequest request) {
        String uid = request.getHeader(MATRIX_UID);
        return StringUtils.isNoneBlank(uid) ? uid : null;
    }

    /**
     * 获取requestId
     * @param request   HttpServletRequest
     * @return requestId
     */
    public static String getRequestId(HttpServletRequest request) {
        String requestId = request.getHeader(REQUEST_ID);
        return StringUtils.isNoneBlank(requestId) ? requestId : null;
    }

    /**
     * 获取IP
     * @param request   HttpServletRequest
     * @return IP
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (StringUtils.isNoneBlank(ip)) {
            return ip;
        }
        ip = request.getHeader(X_REAL_IP);
        if (StringUtils.isNoneBlank(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取Referer
     * @param request   HttpServletRequest
     * @return  Referer
     */
    public static String getReferer(HttpServletRequest request) {
        String referer = request.getHeader(REFERER);
        return StringUtils.isNoneBlank(referer) ? referer : null;
    }

    /**
     * 获取Header中用户账号
     * @param request   HttpServletRequest
     * @return 用户账号
     */
    public static String getHeaderUser(HttpServletRequest request) {
        String user = request.getHeader(HEADER_USER);
        return StringUtils.isNoneBlank(user) ? user : null;
    }

    /**
     * 获取Header中用户密码
     * @param request   HttpServletRequest
     * @return 用密码
     */
    public static String getHeaderPassword(HttpServletRequest request) {
        String password = request.getHeader(HEADER_PASSWORD);
        return StringUtils.isNoneBlank(password) ? password : null;
    }

    /**
     * 获取header中的资源ID
     * @param request   HttpServletRequest
     * @return 资源ID
     */
    public static String getHeaderResourceId(HttpServletRequest request){
        String resourceId = request.getHeader(HEADER_SOURCE_ID);
        return StringUtils.isNoneBlank(resourceId) ? resourceId : null;
    }

    /**
     * 获取header中的资源URL
     * @param request   HttpServletRequest
     * @return 资源URL
     */
    public static String getHeaderResourceUrl(HttpServletRequest request){
        String resourceUrl = request.getHeader(HEADER_SOURCE_URL);
        return StringUtils.isNoneBlank(resourceUrl) ? resourceUrl : null;
    }


    /**
     * 获取header中的Connection
     * @param request
     * @return  connection值
     */
    public static String getHeaderConnection(HttpServletRequest request){
        String connection = request.getHeader(HEADER_CONNECTION);
        return StringUtils.isNoneBlank(connection) ? connection : null;
    }

    /**
     * 获取设备号 app-userAgent-protocol-headerConnection
     * @param request
     * @return
     */
    public static String getDeviceId(HttpServletRequest request) {
        int appId = getAppId(request);
        String userAgent = getUserAgent(request);
        String protocol = request.getProtocol();
        String headerConnection = getHeaderConnection(request);
        String deviceId = appId + SPLIT + userAgent + SPLIT + protocol + SPLIT + headerConnection;
        return MD5Util.saltPassword(appId, deviceId);
    }

    /**
     * 获取请求来源
     * @param request
     * @return
     */
    public static String getOrigin(HttpServletRequest request) {
        String origin = request.getHeader(ORIGIN);
        if (StringUtils.isEmpty(origin)){
            origin = request.getHeader(REFERER);
        }
        return origin;
    }
}
