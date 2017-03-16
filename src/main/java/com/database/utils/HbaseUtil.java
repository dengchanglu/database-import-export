package com.database.utils;

import org.apache.hadoop.hbase.client.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邓昌路 on 17-3-2.
 */
public class HbaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(HbaseUtil.class);
    private static final String Charset = "utf-8" ;

    public static void saveDatas(String tableName,Object obj,String rowKey,String timpstamp) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException{

        Field[] fields = obj.getClass().getDeclaredFields() ;
        // 校验是否每一个属性都有HbaseAnnotation的family属性
        for (Field field : fields) {
            System.out.println(obj.getClass()+"##"+field.getName());
            HbaseAnnotation anno = field.getAnnotation(HbaseAnnotation.class) ;
            if(anno == null || anno.familyName() == null){
                logger.error("未设置HbaseAnnotation的family属性，请确认");
                throw new RuntimeException("未设置HbaseAnnotation的family属性，请确认") ;
            }
        }

        List<Put> list = new ArrayList<Put>() ;
        Put put = new Put(rowKey.getBytes(Charset),Long.parseLong(timpstamp)) ;
        for (Field field : fields) {
            field.setAccessible(true);

            if(field.get(obj) != null ){
                HbaseAnnotation anno = field.getAnnotation(HbaseAnnotation.class) ;
                put.add(anno.familyName().getBytes(Charset), field.getName().getBytes(Charset), ((field.get(obj))+"").getBytes(Charset)) ;
            }
            list.add(put) ;
        }

        HbaseConnection.saveData(tableName, list);
    }

    public static List<Put> dealData(Object obj, String rowKey, String timpstamp) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException{
        Field[] fields = obj.getClass().getDeclaredFields() ;
        // 校验是否每一个属性都有HbaseAnnotation的family属性
        for (Field field : fields) {
            System.out.println(obj.getClass()+"##"+field.getName());
            HbaseAnnotation anno = field.getAnnotation(HbaseAnnotation.class) ;
            if(anno == null || anno.familyName() == null){
                logger.error("未设置HbaseAnnotation的family属性，请确认");
                throw new RuntimeException("未设置HbaseAnnotation的family属性，请确认") ;
            }
        }

        List<Put> list = new ArrayList<Put>() ;
        Put put = new Put(rowKey.getBytes(Charset),Long.parseLong(timpstamp)) ;
        for (Field field : fields) {
            field.setAccessible(true);

            if(field.get(obj) != null ){
                HbaseAnnotation anno = field.getAnnotation(HbaseAnnotation.class) ;
                put.add(anno.familyName().getBytes(Charset), field.getName().getBytes(Charset), ((field.get(obj))+"").getBytes(Charset)) ;
            }
            list.add(put) ;
        }
        return list;
    }


    /**
     * 获取put对象
     * @param rowKey
     * @param timpstamp
     * @throws UnsupportedEncodingException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Put getPut(Object obj,String rowKey,String timpstamp) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException{
        Put put = new Put(rowKey.getBytes(Charset),Long.parseLong(timpstamp)) ;

        Field[] fields = obj.getClass().getDeclaredFields() ;
        // 校验是否每一个属性都有HbaseAnnotation的family属性
        for (Field field : fields) {
            System.out.println(obj.getClass()+"##"+field.getName());
            HbaseAnnotation anno = field.getAnnotation(HbaseAnnotation.class) ;
            if(anno == null || anno.familyName() == null){
                logger.error("未设置HbaseAnnotation的family属性，请确认");
                throw new RuntimeException("未设置HbaseAnnotation的family属性，请确认") ;
            }
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.get(obj) != null ){
                HbaseAnnotation anno = field.getAnnotation(HbaseAnnotation.class) ;
                put.add(anno.familyName().getBytes(Charset), field.getName().getBytes(Charset), ((field.get(obj))+"").getBytes(Charset)) ;

            }
        }

        return put ;
    }


    public static void saveMulDatas(String tableName,List<Object> objs,Class T,String methodName) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException{
        try {
            List<Put> puts = new ArrayList<Put>() ;
            for (Object obj:objs) {
                puts.add(getPut(obj, (String) obj.getClass().getMethod(methodName).invoke(obj),DateUtil.getCurrentDateTime()));
            }
            System.out.println(puts.size());
            HbaseConnection.saveDatas(tableName, puts);
        } catch (IOException e) {
            logger.info("连接hbase数据库异常");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
