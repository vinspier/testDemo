package export_xml;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TestXml1 {
	
	//数据库连接

	   //oracle驱动
	 	public final static String driver = "oracle.jdbc.driver.OracleDriver";
	 	//选择目的数据库位置
	 	public final  static String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	 	//数据库用户名
	 	public final  static String username = "HZSF";
	 	//数据库密码
	 	public final  static String password = "HZSF";

	/*//mysql驱动
	public final static String driver = "com.mysql.jdbc.Driver";
 	//选择目的数据库位置
 	public final  static String url = "jdbc:mysql://127.0.0.1:3306/hz-price";
 	//数据库用户名
 	public final  static String username = "root";
 	//数据库密码
 	public final  static String password = "root";*/
	 
	 public static Connection sigletonConnection = null;
	 
	 public static int channelCount = 0;

	 public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

	// public static String HTTP_URL = "http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/3226/site/picture/old";
	public static String HTTP_URL = "http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3226/site/picture/old";

	 public static String[] MSG_STATUS = {"草稿","审核","审核通过","回收","投稿"};

	 public static String regex = "(?<=/).*?(?=/)";

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//  getMessagePublic();
		//  getMessageMultiple();
		// getExportWithRank(127,"网上接待室");
		// testExport();
		 getChannels(null, 0);
		// testReplacePath("");
		// testReplaceTxt();
	}

	public static void testExport() throws Exception{
		int[] channelIds = {2601279,2617222,2617229};
		String[] fileNames = {"2020年度政府立法项目征集","习近平新时代中国特色社会主义思想","中央和省委重大决策部署"};
		// j为信息状态 0:草稿;1:审核;2:审核通过;3:回收站；4:投稿
		for(int j = 0; j < 5; j++) {
			// 循环遍历数组获取对应的数据导出xml文件
			for (int i = 0; i < channelIds.length; i++) {
				createXml(channelIds[i], fileNames[i], 0, 200, j);
			}
		}
	}
	
	//获取信息公开xml
	public static void getMessagePublic() throws Exception{
		Long start = System.currentTimeMillis();
		int[] channelIds = {230,232,233,234,2013410,
							66116,987883,237,238,207,
							208,1802519,37827,37830,11,
							12,13,314987,314988};
		
		String[] fileNames = {"机构概况","领导信息","内设机构","直属单位","市政府规章",
							"杭州市司法局","政策解读","计划总结","财政预决算","人事信息",
							"应急管理","重点领域信息公开","行政处罚结果信息网上公开","行政复议决定书","政府信息公开指南",
							"政府信息公开制度","政府信息公开年报","目标考核","意见整改"};
		
		String[] code = {"A001A001","A001B001","A001C001","A001D001","B001C001",
						"B001D001","B001E001","C001A001","D001A001","E001",
						"F001","G001","H001","I001","J001",
						"K001","L001","M001A001","M001B001"};

		for (int j = 0; j < 5; j++) {
			for(int i = 0; i < channelIds.length; i++){
				creatXmlPublicLimit(channelIds[i], fileNames[i], code[i],0,200,j);
            }
		}
		System.out.println("运行时间：" + (System.currentTimeMillis() - start));
	}
	
	//获取多信息xml
	public static void getMessageMultiple() throws Exception{
		Long start = System.currentTimeMillis();
		
		int[] channelIds = {158,159,160,161,162,1700334,24,987923,
							846,887,868,884,408,1263086,1280464,1604952,
							100,405416,988634,988637,988638,988639,988642,988643,
							988644,246,2027032,2027034,2027035,2178022,2178023,
							869,891,880,879,881,882,883,
							886,892,888,890,889};
		String[] fileNames = {"最新动态","最新公文","区县动态","图片新闻","城市链接","政府信息公开说明","通知公告","新闻发布会",
								"表格下载","律师进社区","开心学法（公证专题）","新修订《保密法》宣传月","视频点播","最佳营商环境法律服务专项行动","人民调解参与信访矛盾化解专项活动","平安宣传",
								"常见问题","公共法律服务","党建信息","专题活动","创先争优","党员风采", "文件资料","学习园地",
								"他山之石","干警风采","复议简介","复议问答","复议流程","复议应诉动态","典型案例",
								"我市全国人民调解能手风采","法律助力企业度难关","2011年杭州市司法行政工作亮点回眸","进村入企、服务基层大走访活动","2012年新春团拜会","庆祝建党90周年","《人民调解法》宣传",
								"五五普法回眸","2009工作亮点回眸","抗震救灾","与你同行 法在身边","学习十七大精神"};
		// j为信息状态 0:草稿;1:审核;2:审核通过;3:回收站；4:投稿
		for(int j = 0; j < 5; j++) {
			// 循环遍历数组获取对应的数据导出xml文件
			for (int i = 0; i < channelIds.length; i++) {
				createXml(channelIds[i], fileNames[i], 0, 200, j);
			}
		}
		
		//导出单个栏目id对应的数据xml
		//createXml(281, "网上办事工作动态");
		System.out.println("运行时间：" + (System.currentTimeMillis() - start) +" 栏目数 " + channelIds.length + " 栏目名称数 " + fileNames.length);
	}
	
	public static void getChannelTree() throws Exception{
		Long start = System.currentTimeMillis();
		getChannels(0, 0);
		System.out.println("运行时间：" + (System.currentTimeMillis() - start));
		System.out.println(channelCount);
	}

	/**
	 * 生成xml方法
	 */
	/**
	 * 生成xml方法
	 * @throws Exception 
	 */
	public static void createXml() throws Exception {
		
	    Connection conn = (Connection) DriverManager.getConnection(url, username, password);
	    Class.forName(driver); //classLoader,加载对应驱动
		//sql
	    //String sql=" select * from jc_content_ext where content_id in (172,173)";
	    String sql=" select * from wcmdocument where docstatus = 10 and docchannel = 237 order by docreltime asc";
	    
	    PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
	    ResultSet rs = pstmt.executeQuery();
	    
		
	    // 创建Document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element book = document.addElement("book");
        
        // 创建edition子节点
        Element edition = book.addElement("edition");
        edition.setText("1.0");
        
        Element copyright = book.addElement("copyright");
        copyright.setText("大汉网络");
		
		
		// 通过数据库查询循环导出     内容在此处添加
		while (rs.next()) {
			// 创建channel子节点
	        Element article = book.addElement("article");
	        // 创建title子节点
/*	        Element title = article.addElement("title");
	        title.setText("<![CDATA["+rs.getString("title")+"]]>");
	        // 创建caption子节点
	        Element caption = article.addElement("caption");
	        caption.setText("<![CDATA["+rs.getString("short_title")+"]]>");
*/
	        Element title = article.addElement("title");
	        title.setText("<![CDATA["+(rs.getString("docTitle") == null ? "" : rs.getString("docTitle")) +"]]>");
	        Element caption = article.addElement("caption");
	        caption.setText("<![CDATA["+( rs.getString("SUBDOCTITLE") == null ? "" : rs.getString("SUBDOCTITLE")) +"]]>");
	        Element leadtitle = article.addElement("leadtitle");
	        leadtitle.setText("<![CDATA[]]>");
	        Element linktitle = article.addElement("linktitle");
	        linktitle.setText("<![CDATA[]]>");
	        Element href = article.addElement("href");
	        href.setText("<![CDATA["+(rs.getString("DOCLINK") == null ? "" : rs.getString("DOCLINK"))+"]]>");
	        Element author = article.addElement("author");
	        author.setText("<![CDATA["+(rs.getString("CRUSER") == null ? "" : rs.getString("CRUSER"))+"]]>");
	        Element source = article.addElement("source");
	        source.setText("<![CDATA["+(rs.getString("DOCSOURCENAME") == null ? "" : rs.getString("DOCSOURCENAME"))+"]]>");
	        Element userid = article.addElement("userid");
	        userid.setText("<![CDATA[]]>");
	        Element editor = article.addElement("editor");
	        editor.setText("<![CDATA[]]>");
	        Element keyword = article.addElement("keyword");
	        keyword.setText("<![CDATA["+(rs.getString("DOCKEYWORDS") == null ? "" :rs.getString("DOCKEYWORDS"))+"]]>");
	        Element classname = article.addElement("classname");
	        classname.setText("<![CDATA[]]>");
	        Element describe = article.addElement("describe");
	        describe.setText("<![CDATA["+(rs.getString("DOCABSTRACT") == null ? "" : rs.getString("DOCABSTRACT"))+"]]>");
	        
	        //判断附件类型
	        String sql1=" select * from wcmappendix where appflag = 20 and appdocid = " + rs.getString("DOCID");
		    PreparedStatement pstmt1 = (PreparedStatement)conn.prepareStatement(sql1);
		    ResultSet rs1 = pstmt1.executeQuery();
		    String imagePath = "";//焦点图
		    String contentAppendix = "";//正文内容附件
		    while(rs1.next()){
		    	String appflag = rs1.getString("APPFLAG");
		    	if(appflag != null && appflag.equals("20")){
		    		String imageSrc = rs1.getString("APPFILE");
			    	if(imageSrc != null && !"".equals(imageSrc)){
			    		imagePath = "http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3158/site/picture/old/" + imageSrc;
			    	}
		    	}
		    	if(appflag != null && appflag.equals("10")){
		    		String path = "<p><a target='_blank' href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3158/site/picture/old/"+rs1.getString("APPFILE")+"\">"+rs1.getString("APPDESC")+"</a></p>";
		    		contentAppendix += path;
		    	}
		    	
		    }
	        
	        // 修改正文里面附件路劲--开始
	        Element text = article.addElement("text");
	        String contentTxt = rs.getString("DOCHTMLCON");
	        if(contentTxt != null){
	        	contentTxt = contentTxt.replaceAll("src=\"w", "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3158/site/picture/old/w");
	        }
	        text.setText("<![CDATA["+(contentTxt == null ? "" : contentTxt)+contentAppendix+"]]>");
	        // 修改正文里面附件路劲--结束
	        
	        Element datetime = article.addElement("datetime");
	        datetime.setText("<![CDATA["+(rs.getString("DOCRELTIME") == null ? "" : rs.getString("DOCRELTIME"))+"]]>");
	        Element deploytime = article.addElement("deploytime");
	        deploytime.setText("<![CDATA["+(rs.getString("DOCRELTIME") == null ? "": rs.getString("DOCRELTIME"))+"]]>");
	        Element validend = article.addElement("validend");
	        validend.setText("<![CDATA[0000-00-00]]>");
	        
	        // 获取焦点图---开始
	        Element image = article.addElement("image");
	        image.setText("<![CDATA["+imagePath+"]]>");
	        // 获取焦点图---结束
	        
	        Element flash = article.addElement("flash");
	        flash.setText("<![CDATA[]]>");
	        Element media = article.addElement("media");
	        media.setText("<![CDATA[]]>");
	        Element attach = article.addElement("attach");
	        attach.setText("<![CDATA[]]>");
	        
	    }
	
        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();
        ///设置输出文件的编码
        //format.setEncoding("GBK");
        try {
            // 创建XMLWriter对象
            XMLWriter writer = new XMLWriter(new FileOutputStream("D:/课题招标.xml"), format);
            //设置不自动进行转义
            writer.setEscapeText(false);
            // 生成XML文件
            writer.write(document);
            //关闭XMLWriter对象
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

	
	//导出多信息数据为xml的方法
	public static void createXml(int channelId,String fileName,int start,int end,int status) throws Exception {
		Class.forName(driver); //classLoader,加载对应驱动
		//Connection conn = getConnection();
		Connection conn = (Connection) DriverManager.getConnection(url, username, password);

		    String sql=	"SELECT * FROM JC_CONTENT c LEFT JOIN JC_CONTENT_EXT ce ON c.CONTENT_ID = ce.CONTENT_ID LEFT JOIN JC_CONTENT_TXT ct ON ce.CONTENT_ID = ct.CONTENT_ID " +
							" WHERE c.STATUS =" + status + " AND c.CHANNEL_ID IN (SELECT CHANNEL_ID FROM JC_CHANNEL START WITH CHANNEL_ID =" +channelId + " CONNECT BY PRIOR CHANNEL_ID = PARENT_ID)";
		    
		    PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
		    ResultSet rs = pstmt.executeQuery();

		    // 创建Document对象
	        Document document = DocumentHelper.createDocument();
	        // 创建根节点
	        Element book = document.addElement("book");
	        
	        // 创建edition子节点
	        Element edition = book.addElement("edition");
	        edition.setText("1.0");
	        
	        Element copyright = book.addElement("copyright");
	        copyright.setText("大汉网络");
			
			// 通过数据库查询循环导出     内容在此处添加
	        int count = 0;
	        Integer contentId;
			while (rs.next()) {
				contentId = rs.getInt("CONTENT_ID");
				++count;
				if(count < start){
					continue;
				}
				if(count > start && count <= end){
					
					// 创建channel子节点
			        Element article = book.addElement("article");
			        // 创建title子节点
			        Element title = article.addElement("title");
			        title.setText("<![CDATA["+(rs.getString("TITLE") == null ? "" : rs.getString("TITLE")) +"]]>");
			        Element caption = article.addElement("caption");
			        caption.setText("<![CDATA["+( rs.getString("SHORT_TITLE") == null ? "" : rs.getString("SHORT_TITLE")) +"]]>");
			        Element leadtitle = article.addElement("leadtitle");
			        leadtitle.setText("<![CDATA[]]>");
			        Element linktitle = article.addElement("linktitle");
			        linktitle.setText("<![CDATA[]]>");
			        Element href = article.addElement("href");
			        href.setText("<![CDATA["+ (rs.getString("LINK") == null ? "" : rs.getString("LINK")) +"]]>");
			        Element author = article.addElement("author");
			        String authorName = rs.getString("AUTHOR");
			        author.setText("<![CDATA["+(authorName == null ? "市司法局" : authorName)+"]]>");
			        Element source = article.addElement("source");
			        source.setText("<![CDATA["+ (rs.getString("ORIGIN") == null ? "" : rs.getString("ORIGIN")) +"]]>");
			        Element userid = article.addElement("userid");
			        userid.setText("<![CDATA[]]>");//添加了userid  0000137797不需要的话去掉
			        Element editor = article.addElement("editor");
			        editor.setText("<![CDATA["+(authorName == null ? "" : authorName)+"]]>");
			        Element keyword = article.addElement("keyword");
			        keyword.setText("<![CDATA[]]>");
			        Element classname = article.addElement("classname");
			        classname.setText("<![CDATA[]]>");
			        Element describe = article.addElement("describe");
			        describe.setText("<![CDATA["+ (rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION")) +"]]>");
			        
			         /*************修改正文里面链接路劲--开始***************/
			        Element text = article.addElement("text");
			        String contentTxt = rs.getString("TXT");
			        if(contentTxt != null){
			        	contentTxt = testReplaceTxt(contentTxt);
			        }
			        // 添加附件
					contentTxt += getAttachment(contentId);
			        text.setText("<![CDATA["+(contentTxt == null ? "" : contentTxt) + "]]>");
			        /*************修改正文里面链接路劲--结束***************/		        
			        
			        Element datetime = article.addElement("datetime");
			        String dTime = rs.getString("RELEASE_DATE");
			        if(dTime != null){
			        	dTime = dateFormat.format(dateFormat.parse(dTime));
			        }
			        datetime.setText("<![CDATA["+(dTime == null ? "" : dTime)+"]]>");
			        Element deploytime = article.addElement("deploytime");
					if(dTime != null){
						dTime = dateFormat1.format(dateFormat.parse(dTime));
					}
			        deploytime.setText("<![CDATA["+(dTime == null ? "": dTime + " 00:00:00")+"]]>");
			        Element validend = article.addElement("validend");
			        validend.setText("<![CDATA[0000-00-00]]>");
			        Element image = article.addElement("image");
			        String typeImage = transferTypeImage(rs.getString("TYPE_IMG"));
			        image.setText("<![CDATA[" + (typeImage == null ? "" :  typeImage) + "]]>");
			        Element flash = article.addElement("flash");
			        flash.setText("<![CDATA[]]>");
			        String mediaPath = getMedia(contentId);
			        Element media = article.addElement("media");
			        media.setText("<![CDATA["+ (mediaPath == null ? "": mediaPath) +"]]>");
			        Element attach = article.addElement("attach");
			        attach.setText("<![CDATA[]]>");
				}
				if (count > end) {
					break;
				}
				
			}
	
        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();
        ///设置输出文件的编码
        //format.setEncoding("GBK");
        try {
            // 创建XMLWriter对象
        	// D:/hzlyzb导出xml文件-2018-12-9        为本地存储路径 需要现在本地创建路径
        	//	并且需要修改下方方法中的位置与创建的路径一致
			String path = "E:/hzsf/多信息/"+fileName;
			// System.out.println(path);
			File file = new File(path);
			if(!file.exists()){
				file.mkdir();
			}
            XMLWriter writer = new XMLWriter(new FileOutputStream(path +"/" + ((end / 200) - 1) +"_"+ MSG_STATUS[status]+ "_" + (count > 1 ? ((count-1) % 200 == 0 ? 200 : count % 200) : count) +"条"+ ".xml"), format);
            //设置不自动进行转义
            writer.setEscapeText(false);
            // 生成XML文件
            writer.write(document);
            //关闭XMLWriter对象
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileName + ((end / 200) - 1) + "-----"+ (count > 1 ? ((count-1) % 200 == 0 ? 200 : count % 200) : count) +" 条数据" + "---" + MSG_STATUS[status]);
        if (count > end) {
			createXml(channelId, fileName, start + 200, end + 200,status);
		}else {
			close(conn,pstmt,rs);
		}
	}

	private static String getAttachment(Integer contentId) throws Exception{
		StringBuilder sb = new StringBuilder();
		String path;
		if (contentId != null){
			Connection conn = getConnection();
			String sql=" select * from JC_CONTENT_ATTACHMENT where CONTENT_ID =" + contentId;
			PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()){
                    path = rs.getString("ATTACHMENT_PATH");
                    if(!isVideo(path)){
						path = HTTP_URL + "/" + path.replaceAll(regex,"").replaceAll("/","");
						sb.append("<p><a target=\"_blank\" href=\"").append(path).append("\">").append(rs.getString("ATTACHMENT_NAME")).append("</a></p>");
					}
                }
				sql = " select * from JC_FILE where CONTENT_ID =" + contentId;
				pstmt = (PreparedStatement)conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()){
                    path = rs.getString("FILE_PATH");
                    if(!isVideo(path)){
						path = HTTP_URL + "/" + path.replaceAll(regex,"").replaceAll("/","");
						sb.append("<p><a target=\"_blank\" href=\"").append(path).append("\">").append(rs.getString("FILE_NAME")).append("</a></p>");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(conn,pstmt,rs);
			}
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	private static String transferTypeImage(String typeImage){
		if(typeImage != null){
			typeImage = HTTP_URL + "/" + typeImage.replaceAll(regex,"").replaceAll("/","");
			// System.out.println(typeImage);
			return  typeImage;
		}
		return null;
	}

	private static String getMedia(Integer contentId) throws Exception{
		StringBuilder sb = new StringBuilder();
		String path;
		if (contentId != null){
			Connection conn = getConnection();
			String sql=" select * from JC_CONTENT_ATTACHMENT where CONTENT_ID =" + contentId;
			PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()){
					path = rs.getString("ATTACHMENT_PATH");
					if(isVideo(path)){
						path = HTTP_URL + "/" + path.replaceAll(regex,"").replaceAll("/","");
						sb.append(path);
					}
				}
				sql = " select * from JC_FILE where CONTENT_ID =" + contentId;
				pstmt = (PreparedStatement)conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()){
					path = rs.getString("FILE_PATH");
					if(isVideo(path)){
						path = HTTP_URL + "/" + path.replaceAll(regex,"").replaceAll("/","");
						sb.append(path);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(conn,pstmt,rs);
			}
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}
	private static Boolean isVideo(String path){
		if(path.endsWith("mp4") || path.endsWith("wmv") || path.endsWith("swf") || path.endsWith("flv") || path.endsWith("avi") || path.endsWith("mpg")){
			return true;
		}
		return false;
	}
	private static String getFileNumber(Integer contentId) throws Exception{
		StringBuilder sb = new StringBuilder();
		String path;
		if (contentId != null){
			Connection conn = getConnection();
			String sql=" select * from JC_CONTENT_ATTR WHERE ATTR_NAME = 'tybh' AND CONTENT_ID =" + contentId;
			PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()){
                    path = rs.getString("ATTR_VALUE");
                    if(StringUtils.isNotBlank(path))
                        sb.append(path);
                }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(conn,pstmt,rs);
			}
		}
		return sb.toString().replaceAll(" ","");
	}
	//信息公开 分500条一文件导出
	public static void creatXmlPublicLimit(int channelId,String fileName,String code,int start,int end,int status) throws Exception{
		Class.forName(driver); //classLoader,加载对应驱动
		//Connection conn = getConnection();
		Connection conn = (Connection) DriverManager.getConnection(url, username, password);
		// ce.RELEASE_DATE > TO_DATE('2019-07-01 17:00:00', 'yyyy-mm-dd,hh24:mi:ss') AND
		String sql=	"SELECT * FROM JC_CONTENT c LEFT JOIN JC_CONTENT_EXT ce ON c.CONTENT_ID = ce.CONTENT_ID LEFT JOIN JC_CONTENT_TXT ct ON ce.CONTENT_ID = ct.CONTENT_ID " +
				" WHERE c.STATUS =" + status + " AND c.CHANNEL_ID IN (SELECT CHANNEL_ID FROM JC_CHANNEL START WITH CHANNEL_ID =" +channelId + " CONNECT BY PRIOR CHANNEL_ID = PARENT_ID)";

		PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		// 创建Document对象
		Document document = DocumentHelper.createDocument();
		// 创建根节点
		Element book = document.addElement("book");

		// 创建edition子节点
		Element edition = book.addElement("edition");
		edition.setText("1.0");

		Element copyright = book.addElement("copyright");
		copyright.setText("大汉网络");

		// 通过数据库查询循环导出     内容在此处添加
		int count = 0;
		Integer contentId;
		while (rs.next()) {
			contentId = rs.getInt("CONTENT_ID");
			++count;
			if(count < start){
				continue;
			}
			if(count > start && count <= end){

				// 创建channel子节点
				Element article = book.addElement("article");
				// 创建title子节点
				Element title = article.addElement("title");
				title.setText("<![CDATA["+(rs.getString("TITLE") == null ? "" : rs.getString("TITLE")) +"]]>");
				Element caption = article.addElement("caption");
				caption.setText("<![CDATA["+( rs.getString("SHORT_TITLE") == null ? "" : rs.getString("SHORT_TITLE")) +"]]>");
				Element leadtitle = article.addElement("leadtitle");
				leadtitle.setText("<![CDATA[]]>");
				Element linktitle = article.addElement("linktitle");
				linktitle.setText("<![CDATA[]]>");
				Element href = article.addElement("href");
				href.setText("<![CDATA["+ (rs.getString("LINK") == null ? "" : rs.getString("LINK")) +"]]>");

				Element author = article.addElement("author");

				String authorName = rs.getString("AUTHOR") == null ? "" : rs.getString("AUTHOR");

				author.setText("<![CDATA[]]>");

				Element source = article.addElement("source");
				source.setText("<![CDATA["+ (rs.getString("ORIGIN") == null ? "" : rs.getString("ORIGIN")) +"]]>");
				Element userid = article.addElement("userid");
				userid.setText("<![CDATA[]]>");//添加了userid  0000137797不需要的话去掉
				Element editor = article.addElement("editor");
				editor.setText("<![CDATA["+authorName+"]]>");
				Element keyword = article.addElement("keyword");
				keyword.setText("<![CDATA[]]>");
				Element classname = article.addElement("classname");
				classname.setText("<![CDATA[]]>");
				Element describe = article.addElement("describe");
				describe.setText("<![CDATA["+ (rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION")) +"]]>");

				/*************修改正文里面链接路劲--开始***************/
				Element text = article.addElement("text");
				String contentTxt = rs.getString("TXT");
				if(contentTxt != null){
					contentTxt = testReplaceTxt(contentTxt);
				}
				// 添加附件
				contentTxt += getAttachment(contentId);
				text.setText("<![CDATA["+(contentTxt == null ? "" : contentTxt) + "]]>");
				/*************修改正文里面链接路劲--结束***************/

				Element datetime = article.addElement("datetime");
				String dTime = rs.getString("RELEASE_DATE");
				if(dTime != null){
					dTime = dateFormat.format(dateFormat.parse(dTime));
				}
				datetime.setText("<![CDATA["+(dTime == null ? "" : dTime)+"]]>");
				Element deploytime = article.addElement("deploytime");
				if(dTime != null){
					dTime = dateFormat1.format(dateFormat.parse(dTime));
				}
				deploytime.setText("<![CDATA["+(dTime == null ? "": dTime + " 00:00:00")+"]]>");
				Element validend = article.addElement("validend");
				validend.setText("<![CDATA[0000-00-00]]>");
				Element image = article.addElement("image");
				image.setText("<![CDATA[" + (rs.getString("TYPE_IMG") == null ? "" :  HTTP_URL + rs.getString("TYPE_IMG")) + "]]>");
				Element flash = article.addElement("flash");
				flash.setText("<![CDATA[]]>");
				String mediaPath = getMedia(contentId);
				Element media = article.addElement("media");
				media.setText("<![CDATA["+ (mediaPath == null ? "": mediaPath) +"]]>");
				Element attach = article.addElement("attach");
				attach.setText("<![CDATA[]]>");
				//信息公开附加数据
				Element b_xxgk = article.addElement("b_xxgk");
				b_xxgk.setText("<![CDATA[1]]>");
				Element vc_number = article.addElement("vc_number");
				vc_number.setText("<![CDATA["+code+"]]>");
				Element vc_openmodel = article.addElement("vc_openmodel");
				vc_openmodel.setText("<![CDATA[主动公开]]>");
				Element vc_filenumber = article.addElement("vc_filenumber");
				String filenumber = getFileNumber(contentId);
				vc_filenumber.setText("<![CDATA["+ (filenumber == null ? "" : filenumber) +"]]>");
				Element c_complatedate = article.addElement("c_complatedate");
				c_complatedate.setText("<![CDATA[" + (dTime == null ? "" : dTime) + "]]>");
				Element vc_opentimelimit = article.addElement("vc_opentimelimit");
				vc_opentimelimit.setText("<![CDATA[]]>");
				Element vc_auditprogram = article.addElement("vc_auditprogram");
				vc_auditprogram.setText("<![CDATA[单位审核公开]]>");
				Element vc_ztflid = article.addElement("vc_ztflid");
				vc_ztflid.setText("<![CDATA[]]>");
				Element vc_ztflname = article.addElement("vc_ztflname");
				vc_ztflname.setText("<![CDATA[]]>");
				Element vc_serviceid = article.addElement("vc_serviceid");
				vc_serviceid.setText("<![CDATA[]]>");
				Element vc_servicename = article.addElement("vc_servicename");
				vc_servicename.setText("<![CDATA[]]>");
				Element vc_fbjg = article.addElement("vc_fbjg");
				vc_fbjg.setText("<![CDATA[市司法局]]>");
				Element vc_service = article.addElement("vc_service");
				vc_service.setText("<![CDATA[]]>");
				Element vc_area = article.addElement("vc_area");
				vc_area.setText("<![CDATA[002491034]]>");
				Element vc_validate = article.addElement("vc_validate");
				vc_validate.setText("<![CDATA[]]>");
				Element vc_wgkfl = article.addElement("vc_wgkfl");
				vc_wgkfl.setText("<![CDATA[]]>");
				Element vc_xmgkfl = article.addElement("vc_xmgkfl");
				vc_xmgkfl.setText("<![CDATA[]]>");
				Element vc_standardfile = article.addElement("vc_standardfile");
				vc_standardfile.setText("<![CDATA[]]>");
			}
			if (count > end) {
				break;
			}

		}

		// 创建输出格式(OutputFormat对象)
		OutputFormat format = OutputFormat.createPrettyPrint();
		///设置输出文件的编码
		//format.setEncoding("GBK");
		try {
			// 创建XMLWriter对象
			// D:/hzlyzb导出xml文件-2018-12-9        为本地存储路径 需要现在本地创建路径
			//	并且需要修改下方方法中的位置与创建的路径一致
			File file = new File("E:/hzsf/信息公开/"+fileName);
			if(!file.exists()){
				file.mkdir();
			}
			XMLWriter writer = new XMLWriter(new FileOutputStream("E:/hzsf/信息公开/"+fileName + "/" + "cloumn_" + channelId + "_" +((end / 500)-1) +"_" + MSG_STATUS[status]+ "_" + (count > 1 ? ((count-1) % 500 == 0 ? 500 : count % 500) : count) +"条" +".xml"), format);
			//设置不自动进行转义
			writer.setEscapeText(false);
			// 生成XML文件
			writer.write(document);
			//关闭XMLWriter对象
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(fileName + ((end / 500)-1) + "-----"+ ((end / 500)-1) +"_" + (count > 1 ? ((count-1) % 500 == 0 ? 500 : count % 500) : count) +" 条数据" + "-----" + MSG_STATUS[status] );
		if (count > end) {
			createXml(channelId, fileName, start + 500, end + 500,status);
		}else {
			close(conn,pstmt,rs);
		}
	}


	//导出信息公开数据为xml的方法
	public static void createXmlPulic(int channelId,String fileName,String code) throws Exception {
		
	    Connection conn = getConnection();

	    String sql=" select * from wcmdocument where docstatus = 10 and docchannel = " + channelId + " order by docreltime desc";
	    
	    PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
	    ResultSet rs = pstmt.executeQuery();
	    
		//时间格式工具
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	    // 创建Document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element book = document.addElement("book");
        
        // 创建edition子节点
        Element edition = book.addElement("edition");
        edition.setText("1.0");
        
        Element copyright = book.addElement("copyright");
        copyright.setText("大汉网络");
		
		// 通过数据库查询循环导出     内容在此处添加
        int count = 0;
		while (rs.next()) {
			count++;
			
			// 创建channel子节点
	        Element article = book.addElement("article");
	        // 创建title子节点
/*	        Element title = article.addElement("title");
	        title.setText("<![CDATA["+rs.getString("title")+"]]>");
	        // 创建caption子节点
	        Element caption = article.addElement("caption");
	        caption.setText("<![CDATA["+rs.getString("short_title")+"]]>");
*/
	        Element title = article.addElement("title");
	        title.setText("<![CDATA["+(rs.getString("docTitle") == null ? "" : rs.getString("docTitle")) +"]]>");
	        Element caption = article.addElement("caption");
	        caption.setText("<![CDATA["+( rs.getString("SUBDOCTITLE") == null ? "" : rs.getString("SUBDOCTITLE")) +"]]>");
	        Element leadtitle = article.addElement("leadtitle");
	        leadtitle.setText("<![CDATA[]]>");
	        Element linktitle = article.addElement("linktitle");
	        linktitle.setText("<![CDATA[]]>");
	        Element href = article.addElement("href");
	        href.setText("<![CDATA["+(rs.getString("DOCLINK") == null ? "" : rs.getString("DOCLINK"))+"]]>");
	        Element author = article.addElement("author");
	        author.setText("<![CDATA["+(rs.getString("CRUSER") == null ? "" : rs.getString("CRUSER"))+"]]>");
	        Element source = article.addElement("source");
	        source.setText("<![CDATA["+(rs.getString("DOCSOURCENAME") == null ? "" : rs.getString("DOCSOURCENAME"))+"]]>");
	        Element userid = article.addElement("userid");
	        userid.setText("<![CDATA[]]>");//添加了userid  0000137797不需要的话去掉
	        Element editor = article.addElement("editor");
	        editor.setText("<![CDATA[]]>");
	        Element keyword = article.addElement("keyword");
	        keyword.setText("<![CDATA["+(rs.getString("DOCKEYWORDS") == null ? "" :rs.getString("DOCKEYWORDS"))+"]]>");
	        Element classname = article.addElement("classname");
	        classname.setText("<![CDATA[]]>");
	        Element describe = article.addElement("describe");
	        describe.setText("<![CDATA["+(rs.getString("DOCABSTRACT") == null ? "" : rs.getString("DOCABSTRACT"))+"]]>");
	        
	        //判断附件类型
	        String sql1=" select * from wcmappendix where appdocid = " + rs.getString("DOCID");
		    PreparedStatement pstmt1 = (PreparedStatement)conn.prepareStatement(sql1);
		    ResultSet rs1 = pstmt1.executeQuery();
		    String imagePath = "";//焦点图
		    List<String> imagePaths  = new ArrayList<String>();
		    String contentAppendix = "";//正文内容附件
		    while(rs1.next()){
		    	String appflag = rs1.getString("APPFLAG");
		    	if(appflag != null && appflag.equals("20")){
		    		String imageSrc = rs1.getString("APPFILE");
			    	if(imageSrc != null && !"".equals(imageSrc)){
			    		imageSrc = "http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3158/site/picture/old/" + imageSrc;
			    		imagePaths.add(imageSrc);
			    	}
		    	}
		    	if(appflag != null && appflag.equals("10")){
		    		String path = "<p><a target='_blank' href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3158/site/picture/old/"+rs1.getString("APPFILE")+"\">"+rs1.getString("APPDESC")+"</a></p>";
		    		contentAppendix += path;
		    	}
		    	
		    }
	        
	        // 修改正文里面附件路劲--开始
	        Element text = article.addElement("text");
	        String contentTxt = rs.getString("DOCHTMLCON");
	        if("".equals(contentTxt) || contentTxt == null){
	        	String pdf = rs.getString("DOCFILENAME");
	        	if(pdf != null && !"".equals(pdf)){
	        		contentTxt = "<p><a target='_blank' href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3158/site/picture/old/"+pdf+"\">"+rs.getString("docTitle")+"</a></p>";
	        	}
	        }
	        if(contentTxt != null){
	        	contentTxt = contentTxt.replaceAll("src=\"W", "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web3158/site/picture/old/W");
	        }
	        text.setText("<![CDATA["+(contentTxt == null ? "" : contentTxt)+contentAppendix+"]]>");
	        // 修改正文里面附件路劲--结束
	        
	        
	        
	        
	        Element datetime = article.addElement("datetime");
	        String dTime = rs.getString("DOCRELTIME");
	        if(dTime != null){
	        	dTime = dateFormat.format(dateFormat.parse(dTime));
	        }
	        datetime.setText("<![CDATA["+(dTime == null ? "" : dTime)+"]]>");
	        Element deploytime = article.addElement("deploytime");
	        String deTime = rs.getString("DOCRELTIME");
	        if(deTime != null){
	        	deTime = dateFormat.format(dateFormat.parse(deTime));
	        }
	        deploytime.setText("<![CDATA["+(deTime == null ? "": deTime)+"]]>");
	        Element validend = article.addElement("validend");
	        validend.setText("<![CDATA[0000-00-00]]>");
	        
	        // 获取焦点图---开始
	        if(imagePaths.size() > 0){
	        	imagePath = imagePaths.get(0);
	        }
	        Element image = article.addElement("image");
	        image.setText("<![CDATA["+imagePath+"]]>");
	        // 获取焦点图---结束
	        
	        Element flash = article.addElement("flash");
	        flash.setText("<![CDATA[]]>");
	        Element media = article.addElement("media");
	        media.setText("<![CDATA[]]>");
	        Element attach = article.addElement("attach");
	        attach.setText("<![CDATA[]]>");
	        
	        //信息公开附加数据
	        Element b_xxgk = article.addElement("b_xxgk");
	        b_xxgk.setText("<![CDATA[1]]>");
	        Element vc_number = article.addElement("vc_number");
	        vc_number.setText("<![CDATA["+code+"]]>");
	        Element vc_openmodel = article.addElement("vc_openmodel");
	        vc_openmodel.setText("<![CDATA[主动公开]]>");
	        Element vc_filenumber = article.addElement("vc_filenumber");
	        vc_filenumber.setText("<![CDATA["+(rs.getString("SUBDOCTITLE") == null ? "" : rs.getString("SUBDOCTITLE"))+"]]>");
	        Element c_complatedate = article.addElement("c_complatedate");
	        String comDate = rs.getString("DOCRELTIME");
	        if(comDate != null){
	        	comDate = dateFormat1.format(dateFormat1.parse(comDate));
	        }
	        c_complatedate.setText("<![CDATA["+(comDate == null ? "" : comDate)+"]]>");
	        Element vc_opentimelimit = article.addElement("vc_opentimelimit");
	        vc_opentimelimit.setText("<![CDATA[]]>");
	        Element vc_auditprogram = article.addElement("vc_auditprogram");
	        vc_auditprogram.setText("<![CDATA[单位审核公开]]>");
	        Element vc_ztflid = article.addElement("vc_ztflid");
	        vc_ztflid.setText("<![CDATA[]]>");
	        Element vc_ztflname = article.addElement("vc_ztflname");
	        vc_ztflname.setText("<![CDATA[]]>");
	        Element vc_serviceid = article.addElement("vc_serviceid");
	        vc_serviceid.setText("<![CDATA[]]>");
	        Element vc_servicename = article.addElement("vc_servicename");
	        vc_servicename.setText("<![CDATA[]]>");
	        Element vc_fbjg = article.addElement("vc_fbjg");
	        vc_fbjg.setText("<![CDATA[市司法局]]>");
	        Element vc_service = article.addElement("vc_service");
	        vc_service.setText("<![CDATA[]]>");
	        Element vc_area = article.addElement("vc_area");
	        vc_area.setText("<![CDATA[002491034]]>");
	        Element vc_validate = article.addElement("vc_validate");
	        vc_validate.setText("<![CDATA[]]>");
	        Element vc_wgkfl = article.addElement("vc_wgkfl");
	        vc_wgkfl.setText("<![CDATA[]]>");
	        Element vc_xmgkfl = article.addElement("vc_xmgkfl");
	        vc_xmgkfl.setText("<![CDATA[]]>");
	        Element vc_standardfile = article.addElement("vc_standardfile");
	        vc_standardfile.setText("<![CDATA[]]>");
	    }
	
        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();
        ///设置输出文件的编码
        //format.setEncoding("GBK");
        try {
            // 创建XMLWriter对象
            XMLWriter writer = new XMLWriter(new FileOutputStream("D:/hzlyzb/messagePublic/"+fileName+".xml"), format);
            //设置不自动进行转义
            writer.setEscapeText(false);
            // 生成XML文件
            writer.write(document);
            //关闭XMLWriter对象
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileName +"-----"+ count +" 条数据");
	}
	
	public static void querySrcXml() throws Exception {
		
	    Connection conn = (Connection) DriverManager.getConnection(url, username, password);
	    Class.forName(driver); //classLoader,加载对应驱动
		//sql
	    //String sql=" select * from jc_content_ext where content_id in (172,173)";
	    String sql=" select * from wcmdocument where dochtmlcon like '%src%'";
	    
	    PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
	    ResultSet rs = pstmt.executeQuery();
	    
		
	    // 创建Document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element book = document.addElement("book");
        
        // 创建edition子节点
        Element edition = book.addElement("edition");
        edition.setText("1.0");
        
        Element copyright = book.addElement("copyright");
        copyright.setText("大汉网络");
		
		
		// 通过数据库查询循环导出     内容在此处添加
		while (rs.next()) {
			// 创建channel子节点
	        Element article = book.addElement("src");
	        String contentTxt = rs.getString("dochtmlcon");
	        int startIndex = contentTxt.indexOf("src=\"");
	        if(startIndex > 0){
	        	article.setText(contentTxt.substring(startIndex,startIndex+80));
	        }
	    }
	
        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();
        ///设置输出文件的编码
        //format.setEncoding("GBK");
        try {
            // 创建XMLWriter对象
            XMLWriter writer = new XMLWriter(new FileOutputStream("D:/SRC.xml"), format);
            //设置不自动进行转义
            writer.setEscapeText(false);
            // 生成XML文件
            writer.write(document);
            //关闭XMLWriter对象
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/** 分层级导出文件 */
	public static void getExportWithRank(Integer channelId,String filename) throws Exception{
		Class.forName(driver); //classLoader,加载对应驱动
		Connection conn = (Connection) DriverManager.getConnection(url, username, password);
		StringBuilder sb = new StringBuilder();
		int count = 0;
		sb.append("select * from JC_CHANNEL c LEFT JOIN JC_CHANNEL_EXT ce ON c.CHANNEL_ID = ce.CHANNEL_ID WHERE c.PARENT_ID ");
		if(channelId != null){
			sb.append("= ").append(channelId);
		}else {
			sb.append("is null");
		}
		PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		File file = new File("E:/hzsf/多信息/" + filename);
		if(!file.exists()){
			file.mkdir();
		}
		while(rs.next()){
			++count;
			String subChannelId = rs.getString("CHANNEL_ID");
			String chnlName = rs.getString("CHANNEL_NAME");
			String newFilename = filename + "/" + chnlName;
			getExportWithRank(Integer.valueOf(subChannelId),newFilename);
		}
		/** 末节点执行导出 */
		if(count == 0){
			for (int i = 0; i < 5; i++){
				createXml(channelId,filename,0,200,i);
			}
		}else {
			close(conn,pstmt,rs);
		}
	}

	//获取数据库中所有栏目的树状结构  在控制台中输出
	public static void getChannels(Integer parentId,int tap)  throws Exception {
		
		//sql
	    //String sql=" select * from jc_content_ext where content_id in (172,173)";
		Connection conn = getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from JC_CHANNEL c LEFT JOIN JC_CHANNEL_EXT ce ON c.CHANNEL_ID = ce.CHANNEL_ID WHERE c.PARENT_ID ");
	    if(parentId != null){
	    	sb.append("= ").append(parentId);
		}else {
	    	sb.append("is null");
		}
		sb.append(" order by c.PRIORITY asc");
	    PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sb.toString());
	    ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			channelCount++;
			String channelId = rs.getString("CHANNEL_ID");
			String chnlName = rs.getString("CHANNEL_NAME");
			//String chnlDesc = rs.getString("CHNlDESC");
			String tap1 = "";
			for(int i = 0; i < tap; i++){
				tap1 += "	";
			}
			System.out.println(tap1 + chnlName + "("+ channelId +")");
			//System.out.print("\"" + chnlDesc + "\",");
			if(channelId != null){
				getChannels(Integer.valueOf(channelId), tap+1); 
			}
		}
	}
	
	public static Connection getConnection() throws Exception{
		if(sigletonConnection == null){
			Class.forName(driver); //classLoader,加载对应驱动
			Connection conn = (Connection) DriverManager.getConnection(url, username, password);
		    sigletonConnection = conn;
		}else {
			if(sigletonConnection.isClosed()){
				Class.forName(driver); //classLoader,加载对应驱动
				Connection conn = (Connection) DriverManager.getConnection(url, username, password);
				sigletonConnection = conn;
			}
		}
	    return sigletonConnection;
	}

	public static void close(Connection conn,PreparedStatement pstmt,ResultSet rs) throws Exception{
		if (rs != null){
			rs.close();
		}
		if (pstmt != null){
			pstmt.close();
		}
		if (conn != null){
			conn.close();
		}
	}

	public static void testReplacePath(String path){
		// String regex = "(?<=/).*?(?=/)";
		//String url = "/u/cms/hzpf/201611/07162203nnje.pdf";
		//String url = "/jpm//FileService/13_11_8_28_1_2014AdditionImg_Small.jpg";
		String regex = "(\\?filename=).*?(/pjpeg)";
		String url = "wagfhjk2345678.jpg?filename=2008_9_17_10_28_20Addition.JPG&amp;contentType=image/pjpeg";
		url = url.replaceAll(regex,"");
		System.out.println(url);

	}

	public static String testReplaceTxt(String txt){
		//String regex1 = "(src='/).*?([a-zA-Z0-9]/)";
		String regex1 = "http://www.hzsf.gov.cn";
		String regex2 = "(/jpm/).*?(Service/)";
		String regex3 = "(/u/cms).*?([0-9]/)";
		String regex4 = "(/INFOADMIN).*?(file/)";
		String regex5 = "(/INFOADMIN).*?(uploadfile/)";
		String regex6 = "(/thirdparty).*?(fileTypeImages/)";
		String regex7 = "(file:///C:\\\\).*?(01\\\\)";
		String regex8 = "(\\?filename=).*?(/pjpeg)";
		txt = txt.replaceAll(regex1,"").replaceAll(regex2,"/").replaceAll(regex3,"/").replaceAll(regex4,"/")
				.replaceAll(regex5,"/").replaceAll(regex6,"/").replaceAll(regex7,"/").replaceAll(regex8,"");
		txt = txt.replaceAll("src='/","src='" + HTTP_URL +"/").replaceAll("src=\"/","src=\"" + HTTP_URL +"/")
					.replaceAll("href='/","href='" + HTTP_URL +"/").replaceAll("href=\"/","href=\"" + HTTP_URL +"/");
		return txt;
	}
}
