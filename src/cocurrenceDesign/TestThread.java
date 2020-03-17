package cocurrenceDesign;

import java.util.concurrent.ExecutionException;

/**
 * @ClassName: TestThread
 * @Description:
 * @Author:
 * @Date: 2019/10/21 9:04
 * @Version V1.0
 **/
public class TestThread {
    public static void main(String[] args) throws InterruptedException,ExecutionException{
        new Thread(() -> System.out.println(123)).start();
    }
}
