package com.zx.dao;

import com.zx.domain.User;
import com.zx.util.MySessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class UserDaoTest  {
    private SqlSession session;
    private  UserDao userDao;
    @Before
    public void init() throws Exception {
        System.out.println("Before");
        //创建Session对象
        session = MySessionUtils.getSession();
        userDao = session.getMapper(UserDao.class);
    }

    @After
    public void destory() throws Exception {
        System.out.println("After");

        //提交与关闭session
        session.commit();
        session.close();
    }

    @Test
    public void test01() {
        //查找对象
        User user = userDao.findByName("jackma");
        //查看对象
        System.out.println(user);

    }

    @Test
    public void test02(){
        //存放用户信息
        User user= new User(33,"jackhello33","jackhello34","jack",new Date(),"男","3333","333@qq.com",'N',"ddd");
     userDao.save(user);
    }


}