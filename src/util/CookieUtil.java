package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import com.google.gson.Gson;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class CookieUtil{
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }
    /**
     * ��cookie��װ��Map����
     * 
     * @param request
     * @author ye xiuyun
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * ����Cookies
     * 
     * @param response
     *            servlet����
     * @param value
     *            ����ֵ
     * @author ye xiuyun
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value,int time) {
        // newһ��Cookie����,��ֵ��Ϊ����
        Cookie cookie = new Cookie(name, value);
        // tomcat�¶�Ӧ�ù���
        cookie.setPath("/");
        // ���cookie��ֵ�к�������ʱ����Ҫ��cookie���б��룬��Ȼ���������
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setMaxAge(time);
        // ��Cookie��ӵ�Response��,ʹ֮��Ч
        response.addCookie(cookie); // addCookie������Ѿ�������ͬ���ֵ�cookie�������µĸ��Ǿɵ�cookie
        return response;
    }
    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response, String cookieName) {
    	Cookie[] cookies = request.getCookies();  
        if (null==cookies) {  
            System.out.println("û��cookie==============");  
        } else {  
            for(Cookie cookie : cookies){  
                if(cookie.getName().equals(cookieName)){  
                    cookie.setValue(null);  
                    cookie.setMaxAge(0);// ��������cookie  
                    cookie.setPath("/");  
                    System.out.println("��ɾ����cookie����Ϊ:"+cookie.getName());  
                    response.addCookie(cookie);   
                }  
            }  
        }  
    }

}
