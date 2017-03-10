package com.database.webhandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.database.exceptions.DIEException;
import com.database.vo.ResponseVo;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
//import org.eclipse.jetty.servlets.gzip.GzipHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by perfection on 17-2-28.
 */
public class WebHandler {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(WebHandler.class);

    private Server server;
    private int port;
    private String host;
    private String tempDir;
    private String logDir;
    private String webDir;
    private String contextPath;


    public WebHandler(int port, String host, String tempDir, String webDir, String logDir, String contextPath) {

        logger.info("port:{},host:{},tempDir:{},webDir:{},logDir:{},contextPath:{}", port, host, tempDir, webDir, logDir, contextPath);

        this.port = port;
        this.host = host;
        this.tempDir = tempDir;
        this.webDir = webDir;
        this.contextPath = contextPath;
        this.logDir = logDir;
    }

    public void start() throws Exception {
        server = new Server(createThreadPool());
        server.addConnector(createConnector());
        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);
        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }


    private ThreadPool createThreadPool() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(100);
        return threadPool;
    }


    private NetworkConnector createConnector() {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        return connector;
    }

    private HandlerCollection createHandlers() {
        WebAppContext context = new WebAppContext();
        context.setContextPath(contextPath);
        context.setWar(webDir);
        context.setTempDirectory(new File(tempDir));


        RequestLogHandler logHandler = new RequestLogHandler();
        logHandler.setRequestLog(createRequestLog());
//        GzipHandler gzipHandler = new GzipHandler();
        HandlerCollection handlerCollection = new HandlerCollection();
//        handlerCollection.setHandlers(new Handler[]{context, logHandler, gzipHandler});
        return handlerCollection;
    }

    private RequestLog createRequestLog() {
        //记录访问日志的处理
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename(logDir + "/yyyy-mm-dd.log");
        requestLog.setRetainDays(90);
        requestLog.setExtended(false);
        requestLog.setAppend(true);
        //requestLog.setLogTimeZone("GMT");
        requestLog.setLogTimeZone("Asia/Shanghai");
        requestLog.setLogDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        requestLog.setLogLatency(true);
        return requestLog;
    }
}
