package com.jdk.common.String;

import net.sf.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: test
 * @Description:
 * @Author:
 * @Date: 2019/10/22 11:43
 * @Version V1.0
 **/
public class Test {
    public static void main(String[] args) throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

        System.out.println(dateFormat.format(new Date()));
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date().getTime());
        System.out.println(1576664414568L - 1576663484321L);
        System.out.println(dateFormat.parse("1998年1月"));
       // testString();
       // testDate();
       // testFile();
    }

    public static void testString(){
        String value = "1604demo11";
        System.out.println(value.substring(value.indexOf("demo")));
        System.out.println(System.currentTimeMillis());
    }

    public static void testJson(){
        Man man = new Man("head","body","foot");
        Human wife = new Human("head","body","foot");
        man.setDiff("diff");
        man.setWife(wife);
        System.out.println(JSONObject.fromObject(man));
    }

    public static void testDate(){
        System.out.println(new Date());
    }

    public static void testFile(){
        String path = "G:/ossTest/attachment/test/";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        String yearMonth = dateFormat.format(new Date());
        path += yearMonth + "/";
        File file = new File(path);
        File parent = file.getParentFile();
        if (!parent.exists()){
            parent.mkdirs();
        }
    }

}
