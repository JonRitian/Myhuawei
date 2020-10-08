package com.zx.service;

import com.zx.dao.UserDao;
import com.zx.domain.User;
import com.zx.util.MailUtils;
import com.zx.util.MySessionUtils2;
import com.zx.util.UuidUtil;
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
             if("Y"==user1.getStatus()){
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
        User user2=userDao.findByUserName(user.getUsername());
        if(user2 ==null){
            user.setStatus("N");  //默认未激活
          String activeCode=UuidUtil.getUuid();    //激活码
           user.setCode(activeCode);
            MailUtils.sendMail(user.getEmail(),"<a href='http://localhost:8080/Myweb01_war_exploded//activeServlet?activeCode="+activeCode+"'>点击激活账户</a>","激活账户");
            System.out.println(user);
           userDao.save(user);
            return 1;     //不存在,保存用户数据
        }else {
            return -1;  //用户已经存在
        }
    }


    public int active(String activeCode) {
       UserDao userDao=MySessionUtils2.getSession().getMapper(UserDao.class);
       int code=userDao.updateStatus(activeCode);
        return code;
    }
}
