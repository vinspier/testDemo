package algorithm;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: TestByte
 * @Description:
 * @Author:
 * @Date: 2020/3/18 11:28
 * @Version V1.0
 **/
public class TestByte {
    public static void main(String[] args) {
        Integer a = 20;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toOctalString(a));
        System.out.println(Integer.toHexString(a));
        System.out.println(BigDecimal.valueOf(0).compareTo(BigDecimal.valueOf(0)));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        System.out.println(dateFormat.format(new Date()));
    }
}
