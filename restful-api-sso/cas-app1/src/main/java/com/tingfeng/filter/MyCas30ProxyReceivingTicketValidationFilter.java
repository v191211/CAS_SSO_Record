package com.tingfeng.filter;

import com.tingfeng.cas.config.CasConfig;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MyCas30ProxyReceivingTicketValidationFilter extends Cas30ProxyReceivingTicketValidationFilter {
    // 验证成功，生成COOKIE或JWT
    @Override
    protected void onSuccessfulValidation(HttpServletRequest request, HttpServletResponse response, Assertion assertion) {
        super.onSuccessfulValidation(request, response, assertion);

        Map<String, Object> attributes = assertion.getPrincipal().getAttributes();

        attributes.forEach((k,v) -> System.out.println("============key: "+k+" Value: "+v));

        String name = assertion.getPrincipal().getName();

        System.out.println("--------name: "+name);

        // 3、设置cookie（1小时）
        Cookie cookie = new Cookie("UName", (String)attributes.get("username"));
        cookie.setMaxAge(60*60*1);             // Cookie有效时间
        cookie.setPath("/");                       // Cookie有效路径
        cookie.setHttpOnly(true);                  // 只允许服务器获取cookie
        response.addCookie(cookie);

    }
}
