package com.ven.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//配置请求
//@WebFilter(urlPatterns = "/login") //拦截所有

public class DemoFilter implements Filter {
    @Override //初始化方法，只调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init初始化方法执行了");
    }

    @Override //拦截到请求之后调用，调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Demo拦截到了请求");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Demo放行后的逻辑");
    }

    @Override //销毁方法，只调用一次
    public void destroy() {
        System.out.println("destory销毁方法执行了");
    }
}
