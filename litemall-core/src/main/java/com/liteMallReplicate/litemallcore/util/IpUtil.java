package com.liteMallReplicate.litemallcore.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {

    private static final Log logger = LogFactory.getLog(IpUtil.class);

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forward-for");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");

            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        logger.error(e.getMessage(), e);
                    }
                    ipAddress = inet.getHostAddress();
                }
            }

            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0){
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }

        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
