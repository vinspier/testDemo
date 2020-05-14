package jdk1;

/**
 * @ClassName: TestReffrence
 * @Description:
 * @Author:
 * @Date: 2020/5/14 11:51
 * @Version V1.0
 **/
public class TestReffrence {
    public static void main(String[] args) {
        A a = new A();
        a.setName("A");
        B b = new B();
        b.setName("B");
        a.setB(b);
        System.out.println(a.toString());
        B b1 = a.getB();
        a.setUuid("sd123452fg");
        b1.setUuid("sdsdfghfg");
        System.out.println(a.toString());
    }

}
