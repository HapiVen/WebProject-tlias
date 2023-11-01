package com.ven.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.ven.pojo.Result;
import com.ven.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
//自定义拦截器
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    //ctrl+o 重写
    //目标资源方法执行前执行。 返回true：放行    返回false：不放行
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        //1. 获取请求url
        String url = req.getRequestURL().toString();
        log.info("请求的url:{}", url);
        //2. 判断请求url中是否包含login，如果包含，说明是登录操作，放行
        if(url.contains("login")) {
            log.info("登陆操作，放行。");
            return true;
        }
        //3. 获取请求头中的令牌（token）
        String jwt = req.getHeader("token");
        //4. 判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if(!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登陆的信息。");
            Result error = Result.error("NOT_LOGIN");
            //手动转换，将对象转为josn格式—————>fastJSON(fastjson是阿里巴巴提供的用于实现对象和json的转换工具类)
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }
        //5. 解析token，如果解析失败，返回错误结果（未登录）
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败，返回未登陆错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换，将对象转为josn格式—————>fastJSON(fastjson是阿里巴巴提供的用于实现对象和json的转换工具类)
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }
        //6. 放行
        log.info("令牌合法，放行。");
        return true;
    }

    //目标资源方法执行后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle ... ");
    }

    //视图渲染完毕后执行，最后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion .... ");
    }
}
