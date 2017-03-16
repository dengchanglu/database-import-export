package com.database.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by 邓昌路 on 17-3-1.
 */
public class HbaseConnection {

    // 声明静态配置
    private static Configuration hbaseConfiguration = HBaseConfiguration.create();
    public static HConnection connection;
    public static HBaseAdmin admin;

    static {
        // 获取配置
        List<String> pathList = new ArrayList<String>() ;
        //开发
        pathList.add("classpath:zk_conf.properties");
        //测试
        pathList.add("file:/home/modelFactoryModule/config/zk_conf.properties");
        //生产
        pathList.add("file:/home/dataCollect/config/zk_conf.properties");

        Properties bundle = null;
//        try {
//            bundle = PropertyFileReaderHelper.initalPropertyContext(pathList);

//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        String quorum = bundle.getProperty("hbase.zookeeper.quorum");
//        String clientPort = bundle.getProperty("hbase.zookeeper.property.clientPort");
//        String parent = bundle.getProperty("zookeeper.znode.parent");
        hbaseConfiguration.set("hbase.zookeeper.quorum", "192.168.3.210");
        hbaseConfiguration.set("hbase.zookeeper.property.clientPort", "2181");
        hbaseConfiguration.set("zookeeper.znode.parent", "/hbase");

    }

    /**
     * 初始化链接 init:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author Hesy
     * @since JDK 1.6
     */
    private static void init() {
        try {
            connection = HConnectionManager.createConnection(hbaseConfiguration);
            admin = new HBaseAdmin(hbaseConfiguration);
            System.out.println("Hbase初始化链接成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接 close:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author Hesy
     * @since JDK 1.6
     */
    private static void close() {
        try {
            if (null != admin)
                admin.close();
            if (null != connection)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static  void deleteRow(String tableName,String rowKey){
        init();
        HTableInterface table = null ;
        try {
            table = connection.getTable(tableName);
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
            table.setAutoFlushTo(true);
            table.flushCommits();
            System.out.println(table.checkAndDelete(null, null, null,
                    null, delete));
            System.out.println("------------数据插入成功--------------");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            close();
        }
    }

    /**
     * 创建表
     *
     * @author Hesy
     * @param tableName
     * @param cols
     * @since JDK 1.6
     */
    @SuppressWarnings({ "resource" })
    public static void crateTable(String tableName, List<String> cols) {
        try {
            HBaseAdmin admin = new HBaseAdmin(hbaseConfiguration);
            if (admin.tableExists(tableName))
                throw new IOException("table exists");
            else {

                HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
                for (String col : cols) {
                    HColumnDescriptor colDesc = new HColumnDescriptor(col);
                    colDesc.setCompressionType(Algorithm.GZ);
                    colDesc.setDataBlockEncoding(DataBlockEncoding.DIFF);
                    tableDesc.addFamily(colDesc);
                }
                admin.createTable(tableDesc);
            }
            System.out.println("表创建成功");

        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // close();
    }

    /**
     * 插入数据
     *
     * @author Hesy
     * @param tableName
     * @param puts
     * @since JDK 1.6
     */
    public static void saveData(String tableName, List<Put> puts) {
        init();
        HTableInterface table = null ;
        try {
            table = connection.getTable(tableName);
            table.put(puts);
            table.setAutoFlushTo(false);
            table.flushCommits();
            System.out.println("------------数据插入成功--------------");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            close();
        }

    }

    /**
     * 查询数据
     *
     * @author Hesy
     * @param tableName
     * @param rowkey
     * @return
     * @since JDK 1.6
     */
    public static Result getData(String tableName, String rowkey) {
        init();
        HTableInterface table = null ;
        try {
            table = connection.getTable(tableName);
            Get get = new Get(rowkey.getBytes());
            System.out.println(table.get(get));
            System.out.println("------------数据查询成功：--------------");
            return table.get(get);
        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            close();

        }
        return null;
    }

    /**
     * 根据rowkey查找数据 getData:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author Hesy
     * @param tableName
     *            表名
     * @param rowkey
     * @param colFamily
     *            列族
     * @param col
     *            列
     * @return
     * @throws IOException
     * @since JDK 1.6
     */
    public static Result getData(String tableName, String rowkey, String colFamily, String col) throws IOException {
        init();
        HTable table = null ;
        try{
            table = new HTable(hbaseConfiguration, tableName);
            Get get = new Get(Bytes.toBytes(rowkey));
            // 获取指定列族数据
            get.addFamily(Bytes.toBytes(colFamily));
            // 获取指定列数据
            get.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col));
            Result result = table.get(get);

            format(result);
            return result;
        }finally {
            table.close();
            close();
        }
    }

    /**
     * 批量查找数据 scanData:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author Hesy
     * @param tableName
     * @param startRow
     * @param stopRow
     * @throws IOException
     * @since JDK 1.6
     */
    public static void scanData(String tableName, String startRow, String stopRow) throws IOException {
        HTable table = null ;

        try{
            table = new HTable(hbaseConfiguration, tableName);
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(stopRow));
            ResultScanner resultScanner = table.getScanner(scan);
            for (Result result : resultScanner) {
                format(result);
            }
        }finally {
            table.close();
        }


    }



    /**
     * 插入数据
     *
     * @author Hesy
     * @param tableName
     * @param puts
     * @since JDK 1.6
     */
    public static void saveDatas(String tableName, List<Put> puts) throws IOException {
        init();
        HTableInterface table = null;
        try {
            table = connection.getTable(tableName);
            table.setAutoFlushTo(false);

            List<Put> storePut = new LinkedList<Put>() ;
            for(Put put : puts){
                storePut.add(put) ;
                if(storePut.size() % 100 == 0){//如果达到100条，自动flush进hbase
                    table.put(storePut);
                    table.flushCommits();
                    System.out.println("*******************************插入数据数  "+storePut.size());
                    storePut = new LinkedList<Put>() ;

                }
            }
            table.put(storePut);
            table.flushCommits();
            System.out.println("*******************************插入数据数  "+storePut.size());
            System.out.println("------------数据插入成功--------------");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            table.close();
            close();
        }

    }

    /**
     * 输出result结果 format:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author Hesy
     * @param result
     * @since JDK 1.6
     */
    @SuppressWarnings({ "deprecation" })
    private static void format(Result result) {

        String rowkey = Bytes.toString(result.getRow());
        KeyValue[] kvs = result.raw();
        for (KeyValue kv : kvs) {
            String family = Bytes.toString(kv.getFamily());
            String qualifier = Bytes.toString(kv.getQualifier());
            String value = Bytes.toString(kv.getValue());
            System.out.println("rowkey->" + rowkey + " | family->" + family +
                    " | qualifier->" + qualifier
                    + " | value->" + value);
        }
    }

    public static void main(String[] args){
        HbaseConnection.deleteRow("authentication_phone_info","phone");
    }
}
