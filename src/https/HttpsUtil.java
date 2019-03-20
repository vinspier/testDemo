package https;

import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * https 请求工具类
 *
 * */
public class HttpsUtil {

	public static final String CONTENT_TYPE_JSON = "application/json";
	
	public static JSONObject postResJson(String url,String param,String contentType){
		String result = postResponse(url, param, contentType);
		try {
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String postResponse(String url,String param,String contentType){
		CloseableHttpClient client = getClient();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(param, "UTF-8");
		if (StringUtils.isBlank(contentType)) {
			contentType = CONTENT_TYPE_JSON;
		}
		entity.setContentType(contentType);
		post.setEntity(entity);
		try {
			HttpResponse response = client.execute(post);
			HttpEntity result = response.getEntity();
			return EntityUtils.toString(result,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JSONObject getResJson(String url,String param){
		String result = getResponse(url, param);
		try {
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getResponse(String url,String param){
		CharsetHandler handler = new CharsetHandler("UTF-8");
		try {
			CloseableHttpClient client = getClient();
			HttpGet get = new HttpGet(new URI(url + param));
			return client.execute(get,handler);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static CloseableHttpClient getClient(){
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient client = builder.build();
		client = (CloseableHttpClient) wrapClient(client);
		return client;
	}
	
	//私有内部类 处理返回数据的编码
	private static class CharsetHandler implements ResponseHandler<String> {

		private String charset;
		
		public CharsetHandler(String charset) {
			super();
			this.charset = charset;
		}

		@Override
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if (StringUtils.isNotBlank(charset)) {
					return EntityUtils.toString(entity,charset);
				}else {
					return EntityUtils.toString(entity);
				}
			}else {
				return null;
			}
		}
		
	}
	
	// 转换client
	private static HttpClient wrapClient(HttpClient client){
		try {
			SSLContext context = SSLContext.getInstance("TLSv1");
			X509TrustManager manager = new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					
				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					
				}
			};
			context.init(null, new TrustManager[]{manager}, null);
			//SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			CloseableHttpClient closeableClient = HttpClients.custom().setSSLSocketFactory(factory).build();
			return closeableClient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
