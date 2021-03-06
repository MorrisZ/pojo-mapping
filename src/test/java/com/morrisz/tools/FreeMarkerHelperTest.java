package com.morrisz.tools;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyoumao
 */
public class FreeMarkerHelperTest {

    @Test
    public void processTemplate() throws Exception {
        FreeMarkerHelper helper = new FreeMarkerHelper();
        File f = new File("./target/t1.js");
        Map<String, Object> map = new HashMap<>();
        map.put("modelClsName", "Resume");
        List<FieldItem> items = new ArrayList<>();
        items.add(new FieldItem("hello1", null));
        items.add(new FieldItem("hello2", null));
        map.put("fieldItems", items);
        map.put("idField", "what");
        helper.processToFile("extjs.ftl", map, f);
    }
}
