import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 获取第三方接口数据帮助类
 * @class WebFetchDataUtil.java
 * @author fxb
 * @date 2019-1-9 下午1:45:26
 */
public class WebFetchDataUtil {


	public static StringBuilder interfaceUrl;
	public static URLConnection connection;

	public static Object fetchEntity(Class<?> clazz) throws Exception{
		JSONObject jsonObj = fetchJsonObject();
		return JSONObject.toBean(jsonObj, clazz);
	}

	public static Object fetchEntity(String url,Class<?> clazz) throws Exception{
		JSONObject jsonObj = fetchJsonObject(url);
		return JSONObject.toBean(jsonObj, clazz);
	}

	public static List<?> fetchList(Class<?> clazz) throws Exception{
		JSONArray array = fetchJsonArray();
		return JSONArray.toList(array, clazz.newInstance(), new JsonConfig());
	}

	public static List<?> fetchList(String url,Class<?> clazz) throws Exception{
		JSONArray array = fetchJsonArray(url);
		return JSONArray.toList(array, clazz.newInstance(), new JsonConfig());
	}

	public static JSONObject fetchJsonObject() throws Exception{
		String origin = read();
		return JSONObject.fromObject(origin);
	}

	public static JSONObject fetchJsonObject(String url) throws Exception{
		String origin = read(url);
		return JSONObject.fromObject(origin);
	}

	public static JSONArray fetchJsonArray() throws Exception{
		String origin = read();
		return JSONArray.fromObject(origin);
	}

	public static JSONArray fetchJsonArray(String url) throws Exception{
		String origin = read(url);
		return JSONArray.fromObject(origin);
	}

	/**
	 * @return String
	 * @throws Exception
	 * @date 2019-1-9 下午1:46:20
	 *
	 * 连接成功后 读取数据拼接成string
	 */
	public static String read() throws Exception{
		getConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String lineData = null;
		while( (lineData = br.readLine()) != null){
			sb.append(lineData);
		}
		br.close();
		return sb.toString();
	}

	public static String read(String url) throws Exception{
		setUrl(url);
		return read();
	}

	public static String setParam(String url,String param,Object value){
		setUrl(url);
		return setParam(param, value);
	}

	public static String setUrl(String url){
		interfaceUrl = new StringBuilder(url);
		return interfaceUrl.toString();
	}

	public static String setParam(String param,Object value){
		if(!interfaceUrl.toString().contains("?")){
			interfaceUrl.append("?");
		}
		if(isFirst(interfaceUrl.toString(), "?")){
			interfaceUrl.append(param).append("=").append(value.toString());
		}else{
			interfaceUrl.append("&").append(param).append("=").append(value.toString());
		}
		return interfaceUrl.toString();
	}

	public static Boolean isFirst(String url,String target){
		int index = url.indexOf(target);
		if(index == (url.length() - 1))
			return true;
		return false;
	}

	/**
	 * @return URLConnection
	 * @throws Exception
	 * @date 2019-1-9 下午1:55:22
	 * 根据url获取目标连接
	 */
	public static URLConnection getConnection() throws Exception{
		if(connection == null){
			URL url = new URL(interfaceUrl.toString());
			connection = url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
		}
		return connection;
	}

	public static URLConnection getConnection(String url) throws Exception{
		setUrl(url);
		return getConnection();
	}

	public static void main(String[] args) throws Exception{
		String url ="http://218.108.106.229/estate/page.jspx?pageNo=1";
		WebFetchDataUtil.setUrl(url);
		WebFetchDataUtil.setParam("pageSize", 1);
		WebFetchDataUtil.getConnection();
		String orign = WebFetchDataUtil.read();
		System.out.println(orign);
	}
}