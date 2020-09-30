package com.zx.service;

import com.zx.dao.UserDao;
import com.zx.domain.User;
import com.zx.util.MySessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class TestUserService {
     @Test
    public  void test01(){
         //将用户信息发到后台
        UserService userService=new UserService();
        //查找用户信息
        User user=userService.findUserByName("jackma");
        //根据查找到的数据，进行判断  正确 错误 不存在
         if(user == null){
             System.out.println("不存在");
         }
         else {
             System.out.println("存在");
         }
    }


    @Test
    public  void test02(){
        //将用户信息发到后台
        UserService userService=new UserService();
        //查找用户信息
        User user=new User();
        user.setUsername("jackma");
        user.setPassword("123456");
        user.setStatus('Y');
      int code=userService.login(user);
        //根据查找到的数据，进行判断  正确 错误 不存在
        if(code ==-1){
            System.out.println("未注册");
        }
        else if(code==1){
            System.out.println("注册，已激活，密码正确");
        }
        else if(code==-2){
            System.out.println("注册，未激活");
        }
        else if(code==-3){
            System.out.println("注册，已激活，密码不正确");
        }
    }


}

