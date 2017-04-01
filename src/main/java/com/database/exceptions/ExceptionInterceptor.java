package com.database.exceptions;

import com.alibaba.fastjson.JSON;
import com.database.vo.ResponseVo;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by perfection on 17-3-27.
 */
public class ExceptionInterceptor extends SimpleMappingExceptionResolver {
    private static final Logger log = Logger.getLogger(ExceptionInterceptor.class) ;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        setResponseHeader(response) ;
        ResponseVo vo = setResponseContent(ex) ;
        sendMessage(response,vo) ;
        return null;
    }


    private void sendMessage(HttpServletResponse response, ResponseVo vo) {
        PrintWriter writer = null ;
        log.debug("异常信息响应："+ JSON.toJSONString(vo));
        try {
            writer = response.getWriter() ;
            writer.println(JSON.toJSONString(vo));
        } catch (IOException e) {
            log.error("异常拦截器中的异常信息：",e);
        }finally{
            if(writer != null){
                writer.close();
            }
        }
    }

    private ResponseVo setResponseContent(Exception ex) {
        ResponseVo vo = new ResponseVo() ;
        if(ex instanceof DIEException ){
            DIEException dieException = (DIEException)ex ;
            vo.setCode(dieException.getCode());
            vo.setMsg(dieException.getMsg());
        }else{
            log.info("不是业务类异常");
            vo.setCode(DIEException.SYSTEM_ERROR.getCode());
            vo.setMsg(DIEException.SYSTEM_ERROR.getMsg());
        }
        log.error("异常拦截器中的异常信息：",ex);
        return vo ;
    }


    private void setResponseHeader(HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());//设置相应状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);//设置返回的内容类型
        response.setCharacterEncoding("UTF-8");//设置返回的编码
        response.setHeader("Cache-Control", "no-cache,must-revalidate");
    }
}
