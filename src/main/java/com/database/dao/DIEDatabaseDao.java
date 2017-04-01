package com.database.dao;

import com.database.exceptions.DIEException;
import com.database.utils.CustomPropertyConfigurer;
import com.database.utils.LuceneUtil;
import com.database.vo.UserInfoVo;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邓昌路 on 17-3-16.
 */

public interface DIEDatabaseDao<T> {

    public boolean saveIndex(Object T);

    public boolean updateIndex(Object T, String updateByAttribute, String attributeValue);

    public boolean deleteIndex(String deleteByAttribute, String attributeValue);

    public List<T> getIndex(String queryByAttribute, String attributeValue, Integer resultNum);
}
