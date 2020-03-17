package com.jdk.common.String;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TestStringTwo
 * @Description:
 * @Author:
 * @Date: 2019/12/31 11:24
 * @Version V1.0
 **/
public class TestStringTwo {
    public static void main(String[] args) {
        String s = "士大夫，阿斯蒂芬，阿斯蒂芬";
        String[] sArray = s.split("，");
        System.out.println(sArray.length);


        List<String> list = new ArrayList<>();
        System.out.println(CollectionUtils.isEmpty(list)  + "");
    }
}
