package com.jdk.common.String;

import java.lang.reflect.Field;

public class TestStringFiledVariable {
    public static void main(String[] args) throws Exception {
        String s = new String("abcdefgh");
        System.out.println(s.length());
        Class clazz = s.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            System.out.println(fields[i].getName());
        }
        System.out.println("----------------------------------");
        Field field = clazz.getDeclaredField("value");
        field.setAccessible(true);
        char[] value = (char[])field.get(s);
        value[1] = '范';
        value[value.length-1] = '婕';
        System.out.println(s + "  length: " + s.length());
    }
}
