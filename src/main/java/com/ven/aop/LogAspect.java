package com.ven.aop;

import com.alibaba.fastjson.JSONObject;
import com.ven.mapper.OperateLogMapper;
import com.ven.pojo.OperateLog;
import com.ven.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component //当前类交给IOC容器管理
@Aspect  //当前类是AOP类
@Slf4j
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OperateLogMapper operateLogMapper;

    //环绕方法
    @Around("@annotation(com.ven.anno.Log)")
    public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {
        //操作人ID
        String jwt = request.getHeader("token"); //获取请求头中的jwt令牌
        Claims clamis = JwtUtils.parseJWT(jwt);
        Integer operateUser = (Integer) clamis.get("id");
        //当前时间
        LocalDateTime operateTime = LocalDateTime.now();
        //操作类名
        String className = pjp.getTarget().getClass().getName();
        //操作方法名
        String methodName = pjp.getSignature().getName();
        //操作方法参数
        Object[] args = pjp.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        Object result = pjp.proceed();  //调用目标方法执行
        long end = System.currentTimeMillis();
        //方法返回值
        String returnValue = JSONObject.toJSONString(result);
        //操作耗时
        Long costTime = end-begin;

        //记录操作日志
        OperateLog operateLog = new OperateLog(null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);

        log.info("AOP操作记录：",operateLog);
        return result;
    }
}
