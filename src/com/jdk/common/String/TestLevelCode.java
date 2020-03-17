package com.jdk.common.String;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: TestLevelCode
 * @Description:
 * @Author:
 * @Date: 2019/11/28 17:50
 * @Version V1.0
 **/
public class TestLevelCode {
    public static void main(String[] args) {
        List<Integer> userList = new ArrayList<>();
        userList.add(1);
        userList.add(2);
        userList.add(3);
        int index0 = 0;
        int index1 = 0;
        int index2 = 0;
        int i = 0;
        Random random = new Random();
        while ( i < 4){
            int index = random.nextInt(3);
            System.out.println(userList.get(index));
            if (index == 0){
                index0++;
            }
            if (index == 1){
                index1++;
            }
            if (index == 2){
                index2++;
            }
            i++;
        }
        System.out.println(index0 + " " + index1 + " " + index2);
    }
}
