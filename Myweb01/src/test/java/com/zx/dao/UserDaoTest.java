package com.zx.dao;

import com.zx.domain.User;
import com.zx.util.MySessionUtils;
import com.zx.util.MySessionUtils2;
import com.zx.util.UuidUtil;
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
        session = MySessionUtils2.getSession();
        userDao = session.getMapper(UserDao.class);
    }

    @After
    public void destory() throws Exception {
        System.out.println("After");
        //提交与关闭session
        MySessionUtils2.commitAndClose();
    }

    @Test
    public void test01() {
        //查找对象
        User user = userDao.findByName("jackma");
        //查看对象
        System.out.println(user.getName());
        MySessionUtils2.commitAndClose();
    }

    @Test
    public void test02(){
        //存放用户信息
       String code= UuidUtil.getUuid();
        User user= new User(4,"zhangsan","123456","jackma","1998-01-01","男","12345","333@qq.com","N",code);
         userDao.save(user);
         MySessionUtils2.commitAndClose();

    }

    @Test
    public void test03(){
        //存放用户信息
      int code= userDao.updateStatus("162101dd5d4e41eb984b2c51cb38a3b9");
      System.out.println(code);
        MySessionUtils2.commitAndClose();


    }


}