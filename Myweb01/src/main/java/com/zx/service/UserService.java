package com.zx.service;

import com.zx.dao.UserDao;
import com.zx.domain.User;
import com.zx.util.MySessionUtils2;
import org.apache.ibatis.session.SqlSession;

public class UserService {

    public User findUserByName(String UserName) {
        //getMapper是mybatis,给接口生成实现类，将实现类对象返回
        UserDao userDao = MySessionUtils2.getSession().getMapper(UserDao.class);
        //根据用户名查找用户
        User user = userDao.findByName(UserName);
        MySessionUtils2.getSession().commit();
        return user;
    }

      //获取账号密码，对比数据库
    public int login(User user) {
        UserDao userDao =MySessionUtils2.getSession().getMapper(UserDao.class);//内部就使用你编写接口来生成代理对象
        User user1=userDao.findByName(user.getUsername());
        if(user1==null){
            return -1;     //未注册
        }
         else {
             if('Y'==user1.getStatus()){
                 if(user1.getUsername().equals(user.getUsername())&&user1.getPassword().equals(user.getPassword())){
                     return  1;   //登录成功
                 }else{
                     return  -2;  //密码或者用户名错误
                 }
             }else {
                 return  -3;   //没有激活
             }
        }

    }

    public int register(User user){

        UserDao userDao=MySessionUtils2.getSession().getMapper(UserDao.class);
        User user2=userDao.findByName(user.getUsername());
        if(user2 ==null){
           userDao.save(user);
            return 1;     //不存在,保存用户数据
        }else {
            return -1;  //用户已经存在
        }
    }


}
