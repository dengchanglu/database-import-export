package com.database.main;

import com.database.service.impl.AuthenticationServiceImpl;
import com.database.utils.FileUtil;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
//import org.eclipse.jetty.servlets.gzip.GzipHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.BindException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created by perfection on 17-2-28.
 * 已经废弃,请使用JettyWebStart
 */
public class Start {

    private static final Logger log = LoggerFactory.getLogger(Start.class);

    public static final int DEFAULT_PORT = 8081;
    public static final String DEFAULT_CONTEXT_PATH = "/database-import-export";
    private static final String DEFAULT_APP_CONTEXT_PATH = "src/main/webapp";


    public static void main(String[] args) {
        try {
            List<Object> authenticationVos = FileUtil.analysisAuthenticationPhoneInfoFile("");
            AuthenticationServiceImpl io = new AuthenticationServiceImpl();
            io.saveAuthenticationInfo(authenticationVos);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        runJettyServer(DEFAULT_PORT, DEFAULT_CONTEXT_PATH);

    }

    public static void runJettyServer(int port, String contextPath) {

        Server server = createDevServer(port, contextPath);
        try {
            server.setStopAtShutdown(true);
            server.start();
            log.info("启动中");
            server.start();
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String pid = name.split("@")[0];
            log.info(pid);
            pid(true,pid);
            server.join();
        } catch (Exception e) {
        } finally {
            log.error("服务启动异常");
            try {
                Runtime.getRuntime().exec("kill -9 "+pid(false,null));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }

    private static ThreadPool createThreadPool() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(100);
        return threadPool;
    }


    /*private static NetworkConnector createConnector() {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        return connector;
    }*/

    public static Server createJettyServer(int port, String contextPath) {

        Server server = new Server(createThreadPool());
        server.setStopAtShutdown(true);

        ProtectionDomain protectionDomain = Start.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        String warFile = location.toExternalForm();

        WebAppContext context = new WebAppContext(warFile, contextPath);
        context.setServer(server);

        // 设置work dir,war包将解压到该目录，jsp编译后的文件也将放入其中。
        String currentDir = new File(location.getPath()).getParent();
        File workDir = new File(currentDir, "work");
        context.setTempDirectory(workDir);

        server.setHandler(context);
        return server;

    }

    public static Server createDevServer(int port, String contextPath) {

        Server server = new Server(createThreadPool());
        server.setStopAtShutdown(true);

        ServerConnector connector = new ServerConnector(server);
        // 设置服务端口
        connector.setPort(port);
        connector.setReuseAddress(false);
        server.setConnectors(new Connector[] {connector});

        // 设置web资源根路径以及访问web的根路径
        WebAppContext webAppCtx = new WebAppContext(DEFAULT_APP_CONTEXT_PATH, contextPath);
        webAppCtx.setDescriptor(DEFAULT_APP_CONTEXT_PATH + "/WEB-INF/web.xml");
        webAppCtx.setResourceBase(DEFAULT_APP_CONTEXT_PATH);
        webAppCtx.setClassLoader(Thread.currentThread().getContextClassLoader());
        RequestLogHandler logHandler = new RequestLogHandler();
        logHandler.setRequestLog(createRequestLog());
//        GzipHandler gzipHandler = new GzipHandler();
        HandlerCollection handlerCollection = new HandlerCollection();
//        handlerCollection.setHandlers(new Handler[]{webAppCtx, logHandler, gzipHandler});
        server.setHandler(handlerCollection);

        return server;
    }

    private static RequestLog createRequestLog() {
        //记录访问日志的处理
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename("logs/yyyy-mm-dd.log");
        requestLog.setRetainDays(90);
        requestLog.setExtended(false);
        requestLog.setAppend(true);
        //requestLog.setLogTimeZone("GMT");
        requestLog.setLogTimeZone("Asia/Shanghai");
        requestLog.setLogDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        requestLog.setLogLatency(true);
        return requestLog;
    }

    public static String pid(boolean flag, String pid){
        Properties prop = new Properties();
        FileOutputStream oFile;
        String pidBack=null;
        try {
            InputStream in = null;
            in = new BufferedInputStream(new FileInputStream("/home/perfection/database-import-export/src/main/resources/pid.properties"));
            prop.load(in);
            in.close();

            if(flag){
                Enumeration myEnumeration =prop.propertyNames();
                //使用对象循环显示myDataStruct类型的对象中的每一个元素
                while (myEnumeration.hasMoreElements()){
                    prop.setProperty((String)myEnumeration.nextElement(), pid);
                }
                oFile = new FileOutputStream("/home/perfection/database-import-export/src/main/resources/pid.properties", false);
                prop.store(oFile, "PID is changed,task is be worked!");
                oFile.close();
            }else{
                pidBack = prop.getProperty("pid");
            }
            return pidBack;
        } catch (IOException e) {
            log.error("pid.properties配置文件修改异常");
        }
        return pidBack;
    }
}
