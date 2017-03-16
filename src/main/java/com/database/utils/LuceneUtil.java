package com.database.utils;

import com.database.exceptions.DIEException;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邓昌路 on 17-3-16.
 */
public class LuceneUtil {

    private static final Logger log = LoggerFactory.getLogger(LuceneUtil.class);

    private static Directory directory = null;

    static {
        try {
            File file = new File(CustomPropertyConfigurer.getProperty("database.path"));
            if(!file.isDirectory()){
                file.mkdirs();//创建多级目录
            }
            directory = FSDirectory.open(file.toPath());//配置索引数据位置

        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
    }

    /**
     * 获取搜索库的地址
     *
     * @return 返回索引writer
     */
    public static IndexWriter getWriter() {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new KeywordAnalyzer());//配置它的分析器
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(directory, indexWriterConfig);
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
        return indexWriter;
    }

    public static IndexReader getReader() {
        try {
            return DirectoryReader.open(directory);
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw DIEException.SYSTEM_ERROR;
        }
    }

}
