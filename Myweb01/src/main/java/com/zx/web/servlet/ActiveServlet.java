package com.zx.web.servlet;

import com.zx.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                     doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //接受请求,获得参数
            String activeCode=request.getParameter("activeCode");
            System.out.println(activeCode);
        //处理参数
        UserService userService=new UserService();
       int code=userService.active(activeCode);
      String msg="";
        //作出响应给浏览器
        if(code==1){
            msg="激活成功可以使用";
        }else{
            msg="激活失败";
        }
        request.setAttribute("msg",msg);
        request.getRequestDispatcher("messege.jsp").forward(request,response);
    }
}
