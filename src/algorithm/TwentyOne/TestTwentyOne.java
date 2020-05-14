package algorithm.TwentyOne;

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
        twentyOne.bet(twentyOne.getAmount(),0);
        twentyOne.getLeftData().stream().forEach((i)-> System.out.println(i));
        System.out.println(twentyOne.getAvaliable());
    }


}
