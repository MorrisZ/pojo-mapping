package com.morrisz.tools;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zhangyoumao
 */
public class ReflectionHelper {

    public Map<String, Object> reflect(Class<?> cls) {
        Map<String, Object> map = new HashMap<>();
        List<Field> fields = getAllFields(cls);
        List<FieldItem> fieldItems = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>();
        String idField = null;
        for (Field field : fields) {
            String name = field.getName();
            if (fieldNames.contains(name) || name.equals("serialVersionUID")) {
                continue;
            }
            fieldItems.add(new FieldItem(name, null));
            fieldNames.add(name);
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                idField = name;
            }
        }
        map.put("fieldItems", fieldItems);
        map.put("modelClsName", cls.getSimpleName());
        if (idField != null) {
            map.put("idField", idField);
        }
        return map;
    }

    private List<Field> getAllFields(Class<?> cls) {
        List<Field> list = new ArrayList<>();
        do {
            list.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        } while (cls != null && cls != Object.class);
        Collections.sort(list, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }
}
