package value_Or_reference;

/**
 *
 * java 按官方的说法 是只有值值传递的
 * 并不存在引用传递
 * 所谓的引用传递 其实 归根结底是把 指向对象内存地址的引用值传递给其他者
 *
 * 需要联系到 java运行时内存布局（堆 和 栈）
 * */
public class Test {

    public static void main(String[] args) {
        A a = new A("FXB",23);
        System.out.println(a.toString());
        A a0 = a;
        a0 = new A("fxb23",234);
        // method1(a1);
        // method2(a2);
        System.out.println(a.toString());
        System.out.println(a0.toString());
    }

    public static void method1(A a1){
        a1 = new A("sdf",23);
        System.out.println(a1.toString());
    }

    public static void method2(A a2){
        System.out.println(a2.toString());
    }
}
