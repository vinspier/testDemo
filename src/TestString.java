public class TestString {
    public static void main(String[] args) {

        /**new操作是直接在堆上创建*/
        String s1 = new String("123");
        /**常量复制是先查看常量池是否存在
         * 存在 则直接指向该常量的引用
         * 不存在 在常量池中创建 再返回指向该常量的引用*/
        String s2 = "123";
        System.out.println(s1 == s2);//false
        /**
        * s1.intern()执行时 先检查字符串常量池中是否存在 "123"
         * 若存在 则直接返回指向该常量的引用
         *
         * 若不存在
         * jdk 1.6 则拷贝s1的内容副本到常量池中 再返回指向该常量的引用
         * jdk 1.7及以后 直接存储的s1内容的引用等同于s1
        * */
        System.out.println(s2 == s1.intern());//true

        String s3 = new String("ab") + new String("cd");
        String intern = s3.intern();
        String s4 = "abcd";
        System.out.println(s3 == s4);// jdk 1.6:false  jdk 1.7: true
        System.out.println(s4 == intern);
    }
}
