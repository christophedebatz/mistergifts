package com.debatz.gifts.service;

import javax.servlet.http.HttpServletRequest;

public class ClientService 
{
    public static String getClientAddr(HttpServletRequest request)
    {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");  
        
        if (ipAddress == null) {  
            ipAddress = request.getRemoteAddr();  
        }
        
        return ipAddress;
    }
}
