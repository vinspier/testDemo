import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class FetchDataUtil {
	public static void main(String[] args) throws Exception{
		//String url = "https://fang.mooyui.com/estate/out/lists?page=1";
		String url ="http://218.108.106.229/estate/page.jspx?pageNo=1";
		URL webUrl = null;
		URLConnection conn = null;
		BufferedReader bfReader = null;
		try {
			webUrl = new URL(url);
			conn = webUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			bfReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String lineData = null;
			StringBuilder sb = new StringBuilder();
			while( (lineData = bfReader.readLine()) != null){
				sb.append(lineData);
			}
			String allData = sb.toString();
			JSONArray array = JSONArray.fromObject(allData);
			List<?> list = JSONArray.toList(array,new Object(),new JsonConfig());
			System.out.println(allData);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bfReader != null){
				bfReader.close();
			}
		}
	}
}
