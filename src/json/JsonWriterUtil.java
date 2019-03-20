package json;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;

public class JsonWriterUtil{
	
	public static final Logger logger = LoggerFactory.getLogger(JsonWriterUtil.class);
	
	public static final String CHRACTOR_ENCODING = "UTF-8";
	public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
	public static final String CONTENT_TYPE_TEXT = "text/plain; charset=utf-8";
	public static final String CONTENT_TYPE_XML = "text/xml; charset=utf-8";
	
	public static void writerJson(HttpServletResponse response,String dataContent){
		writer(response, CONTENT_TYPE_JSON, dataContent);
	}
	
	public static void wirterText(HttpServletResponse response,String dataContent){
		writer(response, CONTENT_TYPE_TEXT, dataContent);
	}
	
	public static void wirterXml(HttpServletResponse response,String dataContent){
		writer(response, CONTENT_TYPE_XML, dataContent);
	}
	public static void writerJson(HttpServletResponse response,Object dataContent){
		writer(response, CONTENT_TYPE_JSON, dataContent);
	}
	
	public static void wirterText(HttpServletResponse response,Object dataContent){
		writer(response, CONTENT_TYPE_TEXT, dataContent);
	}
	
	public static void wirterXml(HttpServletResponse response,Object dataContent){
		writer(response, CONTENT_TYPE_XML, dataContent);
	}
	
	public static void writer(HttpServletResponse response,String contentType,String dataContent){
		response.setCharacterEncoding(CHRACTOR_ENCODING);
		// 禁用浏览器请求访问缓存
		response.addHeader( "Cache-Control", "no-cache" );
		response.addHeader( "Cache-Control", "no-store" );
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(dataContent);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	public static void writer(HttpServletResponse response,String contentType,Object dataObject){
		response.setCharacterEncoding(CHRACTOR_ENCODING);
		response.setContentType(contentType);
		try {
			response.getWriter().write(JSONObject.fromObject(dataObject).toString());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
