package com.zx.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MailUtilsTest {

    @Test
    public void sendMail() {
            // 参一收件人 ,参二内容 参三标题
        MailUtils.sendMail("jon_email@126.com","<a href='http://localhost:8080/Myweb01_war_exploded//activeServlet?activeCode=263db83e2a3c4a35957297fb6d4ed3e5'>点击激活账户</a>","激活账户");
    }
}