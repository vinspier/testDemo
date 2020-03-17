package https;

import http.HttpRequestUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpRequest;

/**
 * @ClassName: TestHttps
 * @Description:
 * @Author:
 * @Date: 2019/10/12 13:47
 * @Version V1.0
 **/
public class TestHttps {

    public static void main(String[] args) {
        String url = "http://116.62.244.37/yqx/v1/sms/single_send";
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("account","7552");
        paramsJson.put("mobile","17767160232");
        paramsJson.put("text", "您的验证码是 12345678");
        paramsJson.put("sign","M2NlOGQ0MzBhYjFlOTBlZjcwOWJkN2Y1NzhhNWUwMTE=");
        paramsJson.put("extend","");

      //  String result = HttpsUtil.postResponse(url,paramsJson.toString(),"application/json");
        String result = HttpRequestUtil.sendPost(url,paramsJson.toString());
        System.out.println(result);
    }


}
