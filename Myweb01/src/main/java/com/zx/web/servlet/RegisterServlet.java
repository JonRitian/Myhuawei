package com.zx.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.domain.ResponseInfo;
import com.zx.domain.User;
import com.zx.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    static {
        //静态代码块，在加载的时候自动执行，而且只执行一次
        //将string转date的转换器注册给beanUtils
        ConvertUtils.register(new Converter() {

            public Object convert(Class type, Object value) {


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return simpleDateFormat.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, Date.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //从请求中获取check1码
        String check1 = request.getParameter("check");
        //从session中获取check2码
        String check2 = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        System.out.println(check1);
        System.out.println(check2);
        //从session中删除check2码
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //比较check1 与 check2
        //相同表示验证码不正确，将提示信息写到页面的错误提示
        if(check1 == null || !check1.equalsIgnoreCase(check2)){
            //验证码不看大小写
            ResponseInfo responseInfo = new ResponseInfo();
            responseInfo.setCode(-4);
            responseInfo.setData("注册失败，验证码出错");

            //json
            String json = new ObjectMapper().writeValueAsString(responseInfo);
            response.getWriter().println(json);
            return;
        }


        //接收请求，获取参数
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //将请求中的参数赋值给变量
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("注册数据"+user);
        //对参数处理
        UserService userService = new UserService();
        int code = userService.register(user);

        //响应给浏览器
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setCode(code);
        if(code==1){
            responseInfo.setData("注册成功");
        }else{//-1,-2,-3
            responseInfo.setData("注册失败");
        }
        String json = new ObjectMapper().writeValueAsString(responseInfo);
        response.getWriter().println(json);
    }
}
