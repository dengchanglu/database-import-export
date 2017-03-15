package com.database.main;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by perfection on 17-3-3.
 */
public class JettyWebStart {

    public static void main(String[] args){
        try {
            createServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start(){

    }

    public static void createServer() throws Exception {
        //该服务的线程池
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(1000);

        //建立jetty server
        Server server = new Server(threadPool);
        server.setStopAtShutdown(true);

        //该服务的HTTP连接设置
        ServerConnector connector = new ServerConnector(server);
        connector.setReuseAddress(true);
        //设置该服务端口
        connector.setPort(8080);
        //设置请求IP
        connector.setHost("192.168.2.38");
        //关闭连接后,该连接之前的空闲时间时间(-1为禁止该功能)
        connector.setSoLingerTime(-1);
        //可接收连接数量队列大小
        connector.setAcceptQueueSize(10);
        server.setConnectors(new Connector[] { connector });

        //设置jetty的web.xml路径
        WebAppContext wac = new WebAppContext("src/main/webapp","/database-import-export");
        wac.setOverrideDescriptor("src/main/webapp/WEB-INF/web.xml");
        wac.setClassLoader(Thread.currentThread().getContextClassLoader());
        wac.setResourceBase("src/main/webapp");
        System.out.println(wac.getResourceAliases());


        //HTTP请求处理树(现在使用的默认)
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { wac, new DefaultHandler() });
        server.setHandler(handlers);

        server.start();
        server.join();

    }
}
