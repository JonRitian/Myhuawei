package com.zx.util;

import org.junit.Test;

public class UuidUtiltest {
    @Test
    public void test01(){
        for (int i = 0; i < 10; i++) {
            String code=UuidUtil.getUuid();
            System.out.println(code);
        }

    }
}
