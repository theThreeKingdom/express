package io.express;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @ClassName TestUtils
 * @Description TODO
 * @Author nixin
 * @Date 2020/2/25
 * @Version 1.0.0
 **/
public class TestUtils {

    @Test
    public void testGetRandom(){
        assertEquals(888,Utils.getRandom(3));
    }

    @Test
    public void testGetMd5Sum(){
        assertEquals("e10adc3949ba59abbe56e057f20f883e",Utils.getMd5Sum("123456"));
    }
}
