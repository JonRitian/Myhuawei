<%--
  Created by IntelliJ IDEA.
  User: 25863
  Date: 2020/10/6
  Time: 9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>途牛旅游网-登录</title>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--导入angularJS文件-->
    <script src="js/angular.min.js"></script>
    <!--导入jquery-->
    <script src="js/jquery-3.3.1.js"></script>
    <script type="text/javascript">

        //用户名格式校验
        function checkUserName() {
            var username=$("#username").val();  //获得输入框的值
            //判断获得的值是否是符合正则表达式的规则，执行test方法进行判断
            var reg=/^\w{6,20}$/;  //正则表达式
            var   flag=reg.test(username); //判断
            //如果是错误的则边框变红
            if (flag){
                $("#username").css("border","");
            }else {
                $("#username").css("border","1px solid red");
            }
            return flag;
        }

        //密码格式校验
        function checkPassWord() {
            var password=$("#password").val();  //获得输入框的值
            //判断获得的值是否是符合正则表达式的规则，执行test方法进行判断
            var reg=/^\w{6,20}$/;  //正则表达式
            var   flag=reg.test(password); //判断
            //如果是错误的则边框变红
            if (flag){
                $("#password").css("border","");
            }else {
                $("#password").css("border","1px solid red");
            }
            return flag;
        }

        $(function () {
            //格式是否正确
            $("#username").blur(checkUserName);  //用户输入框失去焦点
            $("#password").blur(checkPassWord);  //密码输入框失去焦点
            //如果正确就ajax发送请求到servlet

            $("#errorMsg").html("");
            //ajax提交
            $("#btn_login").click(function () {
                if(checkUserName()&&checkPassWord()){
                    var  un=$("#username").val();
                    var  pw=$("#password").val();
                    var  ck=$("#check").val();
                    //写提交
                    $.ajax({
                        url:"loginServlet",
                        async:true,
                        data:"username="+un+"&password="+pw+"&check="+ck,
                        type:"post",
                        dataType:"json", //服务端返回的数据类型
                        success:function (data) {
                             //   alert(data)  ("code":1,  "data":"登录成功")
                           if(1==data.code){
                                //跳转到主页 index.jsp
                                $("#errorMsg").html("");
                                window.location="index.jsp";
                            }else {
                                $("#errorMsg").html(data.data);
                            }
                        },
                        error:function () {
                            alert("服务器发生错误");
                        }
                    })
                }
            });

        });
    </script>
</head>

<body>
<!--引入头部-->
<div id="header">
    <%@include file="header.jsp"%>
</div>
<!-- 头部 end -->
<section id="login_wrap">
    <div class="fullscreen-bg" style="background: url(images/login_bg.png);height: 532px;">

    </div>
    <div class="login-box">
        <div class="title">
            <img src="images/login_logo.png" alt="">
            <span>欢迎登录途牛旅游账户</span>
        </div>
        <div class="login_inner">

            <!--登录错误提示消息-->
            <div id="errorMsg" class="alert alert-danger" ></div>
            <form id="loginForm" action="" method="post" accept-charset="utf-8">
                <input type="hidden" name="action" value="login"/>
                <input id="username" name="username" type="text" placeholder="请输入账号" autocomplete="off">
                <input id="password" name="password" type="text" placeholder="请输入密码" autocomplete="off">
                <div class="verify">
                    <input id="check" name="check" type="text" placeholder="请输入验证码" autocomplete="off">
                    <span><img src="${pageContext.request.contextPath}/checkCode" alt="" onclick="changeCheckCode(this)"></span>
                    <script type="text/javascript">
                        //图片点击事件
                        function changeCheckCode(img) {
                            img.src="${pageContext.request.contextPath}/checkCode?"+new Date().getTime();
                        }
                    </script>
                </div>
                <div class="submit_btn">
                    <button id="btn_login" type="button">登录</button>
                    <div class="auto_login">
                        <input type="checkbox" name="" class="checkbox">
                        <span>自动登录</span>
                    </div>
                </div>
            </form>
            <div class="reg">没有账户？<a href="javascript:;">立即注册</a></div>
        </div>
    </div>
</section>
<!--引入尾部-->
<div id="footer">
    <%@include file="footer.jsp"%>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery-1.11.0.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
<!--导入布局js，共享header和footer-->
<%--<script type="text/javascript" src="js/include.js"></script>--%>
</body>

</html>
