package com.zx.service;

import com.zx.dao.UserDao;
import com.zx.domain.User;
import com.zx.util.MySessionUtils;
import org.apache.ibatis.session.SqlSession;

public class UserService {

    public User findUserByName(String s) {
        return null;
    }

      //获取账号密码，对比数据库
    public int login(User user) {
        SqlSession sqlSession = MySessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);//内部就使用你编写接口来生成代理对象
        User user1=userDao.findByName(user.getUsername());
        if(user1==null){
            return -1;
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
}
