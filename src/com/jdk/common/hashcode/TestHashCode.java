package com.jdk.common.hashcode;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

public class TestHashCode {
    public static void main(String[] args) {
        String s = new String("fbxsadf");
       // System.out.println(s.hashCode());
        System.out.println("fxb".hashCode());
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        list1.add(123);
        System.out.println(list1 == list2);
        System.out.println(list1.hashCode() + "===" + list2.hashCode());

        System.out.println(new Integer(123).hashCode());
    }
}
