package com.database.webhandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.database.exceptions.DIEException;
import com.database.vo.ResponseVo;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by perfection on 17-2-28.
 */
public class WebHandler extends AbstractHandler {

    private static final String taskClassPath = "com.database.controllers.";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        baseRequest.setHandled(true);

        log.info("请求路径:"+target);

        if(null!=target||target.trim().length()!=0){
            if(target.split(".").length==2){
                try {
                    Class classType = Class.forName(taskClassPath+target.split(".")[0]);
                    Method method = classType.getMethod(target.split(".")[1],HttpServletRequest.class,HttpServletResponse.class);
                    method.invoke(classType.newInstance(),request,response);
                } catch (Exception e) {
                    log.error(DIEException.SYSTEM_ERROR.getMsg(),e.getCause());
                }
            }
        }
    }

    private void send(HttpServletResponse response,ResponseVo vo){
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println(JSON.toJSONString(vo, false));
        } catch (IOException e) {
            log.error(DIEException.SYSTEM_ERROR.getMsg(),e.getCause());
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
