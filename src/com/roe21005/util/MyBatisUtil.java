package com.roe21005.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            System.out.println("Loading MyBatis configuration...");
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            if (inputStream == null) {
                System.err.println("Could not find MyBatis configuration file: " + resource);
            } else {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                System.out.println("MyBatis configuration loaded successfully.");
            }
        } catch (IOException e) {
            System.err.println("IOException while initializing MyBatis: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error during MyBatis initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static SqlSession getSession() {
    	if (sqlSessionFactory == null) {
            System.err.println("SqlSessionFactory is null. MyBatis configuration might have failed.");
            return null;
        }
    	return sqlSessionFactory.openSession(true);
    }
}
