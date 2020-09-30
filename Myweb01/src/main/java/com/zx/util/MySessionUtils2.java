package com.zx.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

//Session工具类
public class MySessionUtils2 {
    private static SqlSessionFactory sessionFactory;
    public static  ThreadLocal<SqlSession>  map=new ThreadLocal<>();
    //static 静态代码，在类加载的时候执行一次，且只执行一次
    static{
//  》1 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
// 》2 创建SqlSessionFactory对象
        InputStream inputStream = MySessionUtils2.class.getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        sessionFactory = sqlSessionFactoryBuilder.build(inputStream);//加载核心配置文件 参1 输入流
//        》3 加载SqlMapConfig.xml配置文件
    }

    // 用ThreadLocal来降低耦合  定义一个集合来存储线程，Map<Thread,Object>
    public static SqlSession getSession() {
//        //查找local中已经存在的session
             SqlSession sqlSession=map.get();
             if (sqlSession!=null){
                 return  sqlSession;
             }
             //session不存在时，创建session，并保存在Local中
             else {
                sqlSession = sessionFactory.openSession();
                map.set(sqlSession);
                return  sqlSession;
             }
//        》4 创建SqlSession对象
    }

    //提交关闭session，用ThreadLocal集合
    public static void commitAndCloseSession(){
        SqlSession sqlSession=map.get();
        if(sqlSession!=null){
            sqlSession.commit();
            sqlSession.close();
            //已经关闭了的session不能留在local
            //要删除
            map.remove();
        }
    }

}
