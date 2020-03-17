package algorithm;

/**
 * @ClassName: TestTwentyOne
 * @Description: 21点游戏
 * @Author:
 * @Date: 2020/3/17 10:22
 * @Version V1.0
 **/

public class TestTwentyOne {
    public static void main(String[] args) {
        TwentyOne twentyOne = new TwentyOne(10,24);
        System.out.println(twentyOne.getTime());
        twentyOne.bet(twentyOne.getAmount(),0);
        System.out.println(twentyOne.getAvaliable());
    }


}
