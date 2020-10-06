package com.zx.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.domain.ResponseInfo;
import com.zx.domain.User;
import com.zx.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受请求获取参数
        Map<String,String[]> map=request.getParameterMap();
        User user=new User();
        System.out.println(map);
        try {
            //参数一JavaBean  参数二map
            BeanUtils.populate(user,map);    //将map中的所有的参数赋值给JavaBean
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //处理参数
        UserService userService=new UserService();
        int code=userService.login(user);
        //响应给浏览器   因为是ajax提交 所以响应json给浏览器
        ResponseInfo responseInfo=new ResponseInfo();
        responseInfo.setCode(code);
        if(code==-1){
            responseInfo.setData("用户不存在");
        }else if (code==1){
            responseInfo.setData("登录成功");
         //查出用户数据
         User user1=userService.findUserByName(user.getUsername());
         //保存在session中
            request.getSession().setAttribute("user",user1);
        }
        else if (code==-2){
            responseInfo.setData("密码或者用户名错误");
        }
        else if (code==-3){
            responseInfo.setData("没有激活");
        }
        //转化为json
        String json=new ObjectMapper().writeValueAsString(responseInfo);
        response.getWriter().println(json);
    }


}
