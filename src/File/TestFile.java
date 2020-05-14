package File;

import java.io.File;
import java.math.BigDecimal;

/**
 * @ClassName: TestFile
 * @Description:
 * @Author:
 * @Date: 2020/4/9 16:38
 * @Version V1.0
 **/
public class TestFile {
    public static String filePath = "/E:/risen_idea/risen-app-kxxmsbgl/target/risen-app-kxxmsbgl-1.0.0-SNAPSHOT/WEB-INF/classes//com/risen/template/测试项目名臣7f979a58d5e24133b993df2d4fa4903e.doc";

    public static void main(String[] args) {
//        File file = new File(filePath);
//        if (file.exists()){
//            System.out.println(file.getName());
//            file.deleteOnExit();
//        }
        test();
    }

    public static void test(){
        BigDecimal bigDecimal = BigDecimal.valueOf(1);
        System.out.println(bigDecimal.multiply(BigDecimal.valueOf(0.01)).setScale(2).doubleValue());
    }
}
