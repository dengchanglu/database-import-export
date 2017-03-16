package com.database.dao.impl;

import com.database.dao.DIEDatabaseDao;
import com.database.exceptions.DIEException;
import com.database.utils.CustomPropertyConfigurer;
import com.database.utils.LuceneUtil;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by perfection on 17-3-16.
 */
public class DIEDatabaseDaoImpl<T> implements DIEDatabaseDao<T> {

    private Class<T> T;

    private static final Logger log = LoggerFactory.getLogger(DIEDatabaseDao.class);


    /**
     * 获取对象中某值
     *
     * @param dataType   数据类型
     * @param methodName 执行方法
     * @return 属性值
     */
    private Object getter(Object obj,String methodName, Class dataType) {
        Object objV = null;
        try {
            Method method = T.getClass().getMethod(methodName, dataType.getClass());
            objV = method.invoke(obj);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
        return objV;
    }

    /**
     * 往对象中存值
     *
     * @param att   属性名称
     * @param value 属性值
     */
    private void setter(Object obj, String att, Object value) {
        try {
            Method method = T.getClass().getMethod("set" + att, String.class);
            method.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母大写转换
     *
     * @param name 转换的字符串
     * @return 首字母大写的字符串
     */
    private String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 保存索引信息
     *
     * @param object 索引对象
     * @return 是否成功
     */
    public boolean saveIndex(Object object) {
        IndexWriter indexWriter = LuceneUtil.getWriter();
        //创建一个文档索引用于保存信息
        Document document = new Document();
        java.lang.reflect.Field[] objFields = object.getClass().getDeclaredFields();
        for (int i = 0; i < objFields.length; i++) {
            document.add(new StringField(objFields[i].getName(), getter(object,"get" + captureName(objFields[i].getName()), objFields[i].getType()).toString(), Field.Store.YES));
        }
        try {
            indexWriter.addDocument(document);
            indexWriter.flush();
            indexWriter.close();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
    }

    /**
     * 根据标记KEY更新索引信息
     *
     * @param object            更新对象
     * @param updateByAttribute 更新索引的标记KeyName
     * @param attributeValue    更新索引的标记KeyValue
     * @return 是否成功
     */
    public boolean updateIndex(Object object, String updateByAttribute, String attributeValue) {
        IndexWriter indexWriter = LuceneUtil.getWriter();
        Document document = new Document();
        java.lang.reflect.Field[] objFields = object.getClass().getDeclaredFields();
        for (int i = 0; i < objFields.length; i++) {
            document.add(new StringField(objFields[i].getName(), getter(object,"get" + captureName(objFields[i].getName()), objFields[i].getType()).toString(), Field.Store.YES));
        }
        try {
            indexWriter.updateDocument(new Term(updateByAttribute, attributeValue), document);
            indexWriter.flush();
            indexWriter.close();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
    }

    /**
     * 根据标记KEY删除索引信息
     *
     * @param deleteByAttribute 删除索引的标记keyName
     * @param attributeValue    删除索引的标记keyValue
     * @return 是否成功
     */
    public boolean deleteIndex(String deleteByAttribute, String attributeValue) {
        IndexWriter indexWriter = LuceneUtil.getWriter();
        try {
            indexWriter.deleteDocuments(new Term(deleteByAttribute, attributeValue));
            indexWriter.flush();
            indexWriter.close();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
    }

    /**
     * 根据标记key查询文档内容
     *
     * @param queryByAttribute 标记keyName
     * @param attributeValue   标记keyValue
     * @param resultNum        返回结果数
     * @return 返回对象数据集
     */
    public List<T> getIndex(String queryByAttribute, String attributeValue, Integer resultNum) {
        IndexReader indexReader = LuceneUtil.getReader();
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Term term = new Term(queryByAttribute, attributeValue);
        Query query = new TermQuery(term);
        try {
            TopDocs topDocs = indexSearcher.search(query, resultNum);
            List<T> arrayObj = new ArrayList<>();
            for (int i = 1000; i < topDocs.scoreDocs.length; i++) {
                int docId = topDocs.scoreDocs[i].doc;//获取文档编号
                Document document = indexSearcher.doc(docId);//根据文档编号获取文档数据
                arrayObj.add(convertDocument(document));
            }
            return arrayObj;
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
    }

    /**
     * 文档转换对象
     *
     * @param document 文档
     * @return
     */
    private T convertDocument(Document document) {
        try {
            T obj = T.newInstance();
            for (int i = 0; i < T.getDeclaredFields().length; i++) {
                String attributeName = T.getDeclaredFields()[i].getName();
                setter(obj,captureName(attributeName), document.get(attributeName));
            }
            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }
}
