package com.database.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
/**
 * Created by perfection on 17-3-2.
 */
public class PropertyFileReaderHelper {

    private static final String PREFIX_CLASSPATH = "classpath" ;
    private static final String PREFIX_FILE = "file" ;

    public static Properties initalPropertyContext(List<String> filePaths) throws IOException {

        Properties prop = new Properties() ;
        for (String path : filePaths) {

            String filePath = null ;
            //判断文件是本地绝对路径还是classpath下路径
            if(path.startsWith(PREFIX_CLASSPATH)){
                String classpath = Thread.currentThread().getContextClassLoader ().getResource("").getPath()  ;
                filePath = classpath + File.separator+path.substring(PREFIX_CLASSPATH.length()+1) ;
            }else if(path.startsWith(PREFIX_FILE)){
                filePath = path.substring(PREFIX_FILE.length()+1) ;
            }

            File propertyFile = new File(filePath) ;
            if (!propertyFile.exists()){
                continue;
            }
            prop.load(new FileInputStream(propertyFile));
        }

        return prop ;
    }
}
