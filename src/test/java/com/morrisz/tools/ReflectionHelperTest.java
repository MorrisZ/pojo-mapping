package com.morrisz.tools;

import org.junit.Test;

import java.util.Map;

/**
 * @author zhangyoumao
 */
public class ReflectionHelperTest {

    @Test
    public void reflectTest() {
        ReflectionHelper helper = new ReflectionHelper();
        Map<String, Object> map = helper.reflect(ModelGenerator.class);
        System.out.println(map);
    }
}
