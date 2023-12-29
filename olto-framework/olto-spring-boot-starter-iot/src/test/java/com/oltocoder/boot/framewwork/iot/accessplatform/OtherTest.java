package com.oltocoder.boot.framewwork.iot.accessplatform;

import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.IitaPlatformAccessComponent;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class OtherTest {

    @Test
    public void testGetClassName() {
        System.out.println(IitaPlatformAccessComponent.class.getSimpleName().toLowerCase());
    }


    @Test
    public void testTimestampMillis() {
        System.out.println(System.currentTimeMillis());

        Date date = new Date();
        long timestamp = date.getTime();
    }

    @Test
    public void testMd5()
    {
      String result=  DigestUtils.md5Hex("111111111");
    }
}
