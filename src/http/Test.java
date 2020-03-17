package http;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName: Test
 * @Description:
 * @Author:
 * @Date: 2019/11/7 10:09
 * @Version V1.0
 **/
public class Test {

    private static final String LOGIN_URL = "http://localhost:8080/public/app/loginApp.action";

    private static final String params1 = "user=13732204908&registercode=250626";

    private static final String params2 = "user=中国共产党杭州市富阳区春江街道山建村委员会第四支部&password=2015xfzs";

    public static void main(String[] args) {
        testLogin(2,LOGIN_URL);
    }

    public static void testLogin(int count,final String loginUrl){
        AtomicLong atomicLong = new AtomicLong(0);
        Random random = new Random(10);
        for (int i = 0; i < count;i++){
            if (random.nextInt() / 2 == 0){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Long start = System.currentTimeMillis();
                        String result = HttpRequestUtil.sendGet(loginUrl,params1);
                        Long end = System.currentTimeMillis();
                        System.out.println("result: " + result);
                        System.out.println("time took: " + (end - start));
                        atomicLong.addAndGet(end-start);
                    }
                }).start();
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Long start = System.currentTimeMillis();
                        String result = HttpRequestUtil.sendPost(loginUrl,params2);
                        Long end = System.currentTimeMillis();
                        System.out.println("result: " + result);
                        System.out.println("time took: " + (end - start));
                        atomicLong.addAndGet(end-start);
                    }
                }).start();
            }

        }
    }



}
