package export_xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TextXml {
	
	//数据库连接
	 /**
	  * //oracle驱动
	 	public final static String driver = "oracle.jdbc.driver.OracleDriver";
	 	//选择目的数据库位置
	 	public final  static String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	 	//数据库用户名
	 	public final  static String username = "hzlyzb";
	 	//数据库密码
	 	public final  static String password = "hzlyzb12345";
 	*/
	 
	//mysql驱动
	public final static String driver = "com.mysql.jdbc.Driver";
 	//选择目的数据库位置
 	public final  static String url = "jdbc:mysql://127.0.0.1:3306/hz-price";
 	//数据库用户名
 	public final  static String username = "root";
 	//数据库密码
 	public final  static String password = "root";
	 
	 public static Connection sigletonConnection = null;
	 
	 public static int channelCount = 0;

	//时间格式工具
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//getMessagePublic();
		getMessageMultiple();
		//getChannels(640, 0);
	}
	
	//获取信息公开xml
	public static void getMessagePublic() throws Exception{
		Long start = System.currentTimeMillis();
		int[] channelIds = {174,186,188,760,761,190,198,199
							,214,215,763,764,196,768,769,771,
							772,773,774,847,848,850};
		
		String[] fileNames = {"机构职能","行政规范性文件","政策解读","部门文件","规划信息","计划总结","专项资金","财政预决算"
							,"人事任免","任前公示","应急预案","应急预警及应对","行政许可","监督检查结果","业务公告","重要会议"
							,"领导活动","重大项目","业务信息","行政执法公开","市场准入负面清单公开","政府和社会资本合作项目"};
		
		String[] code = {"A001","B001A001","B001B001","B001C001","C001A001","C001B001","D001A001","D001B001"
						,"E001A001","E001B001","F001A001","F001B001","G001A001","H001A001","H001B001","I001A001"
						,"I001B001","I001C001","I001D001","J001A001","J001B001","J001C001"};
		
		for(int i = 0; i < channelIds.length; i++){
			createXmlPulic(channelIds[i], fileNames[i], code[i]);
		}
		System.out.println("运行时间：" + (System.currentTimeMillis() - start));
	}
	
	//获取多信息xml
	public static void getMessageMultiple() throws Exception{
		Long start = System.currentTimeMillis();
		int[] channelIds = {237};
		
		String[] fileNames = {"课题招标"};
		
		
		// 循环遍历数组获取对应的数据导出xml文件
		for(int i = 0; i < channelIds.length; i++){
			createXml(channelIds[i], fileNames[i]);
		}
		
		
		//导出单个栏目id对应的数据xml
		//createXml(281, "网上办事工作动态");
		System.out.println("运行时间：" + (System.currentTimeMillis() - start));
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
	public static void createXml(int channelId,String fileName) throws Exception {
		 	Connection conn = getConnection();

		    String sql=" select * from wcmdocument where docstatus = 10 and docchannel = " + channelId + " order by docreltime desc";
		    
		    PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
		    ResultSet rs = pstmt.executeQuery();
		    
			//时间格式工具
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
			}
	
        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();
        ///设置输出文件的编码
        //format.setEncoding("GBK");
        try {
            // 创建XMLWriter对象
        	// D:/hzlyzb导出xml文件-2018-12-9        为本地存储路径 需要现在本地创建路径
        	//	并且需要修改下方方法中的位置与创建的路径一致
            XMLWriter writer = new XMLWriter(new FileOutputStream("D:/hzlyzb导出xml文件-2018-12-9/"+fileName+".xml"), format);
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
	
	//导出信息公开数据为xml的方法
	public static void createXmlPulic(int channelId,String fileName,String code) throws Exception {
		
	    Connection conn = getConnection();

	    String sql=" select * from wcmdocument where docstatus = 10 and docchannel = " + channelId + " order by docreltime desc";
	    
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
	        vc_fbjg.setText("<![CDATA[杭州市发改委]]>");
	        Element vc_service = article.addElement("vc_service");
	        vc_service.setText("<![CDATA[]]>");
	        Element vc_area = article.addElement("vc_area");
	        vc_area.setText("<![CDATA[002489444]]>");
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
	//获取数据库中所有栏目的树状结构  在控制台中输出
	public static void getChannels(Integer parentId,int tap)  throws Exception {
		
		//sql
	    //String sql=" select * from jc_content_ext where content_id in (172,173)";
		Connection conn = getConnection();
	    String sql=" select * from wcmchannel where parentid = " + parentId + " order by channelid asc";
	    
	    PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
	    ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			channelCount++;
			String channelId = rs.getString("CHANNELID");
			String chnlName = rs.getString("CHNLNAME");
			String chnlDesc = rs.getString("CHNlDESC");
			String tap1 = "";
			for(int i = 0; i < tap; i++){
				tap1 += "	";
			}
			System.out.println(tap1 + channelId +"  " + chnlDesc + "  ("+ chnlName +")");
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
		}
	    return sigletonConnection;
	}
	
	
}
