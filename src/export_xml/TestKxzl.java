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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: TestKxzl
 * @Description:
 * @Author:
 * @Date: 2019/8/13 11:42
 * @Version V1.0
 **/
public class TestKxzl {

    //mysql驱动
    public final static String DRIVER = "com.mysql.jdbc.Driver";
    //选择目的数据库位置
    public final  static String URL = "jdbc:mysql://127.0.0.1:3306/kxzl";
    //sqlserver驱动
    //public final static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //选择目的数据库位置
    // public final  static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=dzkjw";
    //  public final  static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=kx_cms";
    //public final  static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=kx_new";
    //数据库用户名
    public final  static String USER_NAME = "root";
    //数据库密码
    public final  static String PASSWORD = "root";

    public static final SimpleDateFormat DATE_FORMAT_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String PATH_PREFIX = "/picture/old/cxqdzl/";

    public static final String REGULAR_1 = "<img(.*?)src=\"(.*?)\"";
    public static final String REGULAR_2 = "<a(.*?)href=\"(.*?)\"";
    public static final String REGULAR_3 = "<A(.*?)href=\"(.*?)\"";
    public static final String REGULAR_4 = "<IMG(.*?)src=\"(.*?)\"";

    public static final String[] ORIGINAL_PATH = {"src=\"/kxzl/upload_file/","href=\"/kxzl/upload_file/",
                                                "src=\"http://www.zast.org.cn/","href=\"http://www.zast.org.cn/"
                                        };
    public static final String[] REPLACE_PATH = {"src=\"/picture/old/cxqdzl/kxzl/upload_file/","href=\"/picture/old/cxqdzl/kxzl/upload_file/",
                                                "src=\"http://www.zast.org.cn/picture/old/","href=\"http://www.zast.org.cn/picture/old/"
                                        };

    public static void main(String[] args) throws  Exception{
         getChannelsTree(null,0,"");
       // getArticle(null,"");
       // getAllContentPath();
       // getAllContentPath1();
    }

    /**
     * 获取栏目树状
     * */
    public static void getChannelsTree(Long parentId,int tap,String path)  throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from b_articletype where usable = 1");
        if (parentId == null)
            sb.append(" and parent_id is null");
        else
            sb.append(" and parent_id = ").append(parentId);
        Connection conn = connection();
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Long pid = rs.getLong("id");
            String name = rs.getString("name");

            String filename = path +  "/" + name;
            createFile(filename);

            int count = getCount(pid);
            if (count > 0) {
                getArticle(pid,0,200,filename);
            }

            //  打印栏目树状结构

                String tap1 = "";
                for(int i = 0; i < tap; i++){
                    tap1 += "	";
                }
                System.out.println(tap1 +  name  + "(channel_id: " + pid + ")" + "article_count: "+ count);

            /** 次级栏目 */
            getChannelsTree(pid,tap + 1,filename);
        }
    }

    public static void getArticle(Long typeId,int start,int end,String path) throws Exception{
        StringBuilder sb = new StringBuilder("SELECT b.*,f1.webPath as imagePath,f2.webPath as mediaPath FROM b_article b left join sys_fileinfo f1 on b.image_id = f1.id left join sys_fileinfo f2 on b.video_id = f2.id");
        if (typeId != null)
            sb.append(" where b.type_id = ").append(typeId);
        Connection conn = connection();
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
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

        int count = 0;
        while(rs.next()){
            count++;
            if(count < start){
                continue;
            }
            if(count > start && count <= end){
                String contentText = rs.getString("content");
                if (StringUtils.isNotBlank(contentText))
                    contentText = testReplaceTxt(contentText);

                if (StringUtils.isNotBlank(contentText) && contentText.contains("/picture/old/cxqdzl/kxzl/")){
                    // 创建channel子节点
                    Element article = book.addElement("article");
                    // 创建title子节点
                    Element title = article.addElement("title");
                    title.setText("<![CDATA["+(rs.getString("title") == null ? "" : rs.getString("title")) +"]]>");

                    Element caption = article.addElement("caption");
                    caption.setText("<![CDATA[]]>");
                    Element leadtitle = article.addElement("leadtitle");
                    leadtitle.setText("<![CDATA[]]>");
                    Element linktitle = article.addElement("linktitle");
                    linktitle.setText("<![CDATA[]]>");
                    Element href = article.addElement("href");
                    href.setText("<![CDATA[]]>");

                    Element author = article.addElement("author");
                    String authorName = rs.getString("writer");
                    author.setText("<![CDATA["+(authorName == null ? "" : authorName)+"]]>");

                    Element source = article.addElement("source");
                    source.setText("<![CDATA["+ (rs.getString("source") == null ? "" : rs.getString("source")) +"]]>");

                    Element userid = article.addElement("userid");
                    userid.setText("<![CDATA[]]>");//添加了userid  0000137797不需要的话去掉
                    Element editor = article.addElement("editor");
                    editor.setText("<![CDATA["+(authorName == null ? "" : authorName)+"]]>");

                    Element keyword = article.addElement("keyword");
                    keyword.setText("<![CDATA[]]>");
                    Element classname = article.addElement("classname");
                    classname.setText("<![CDATA[]]>");
                    Element describe = article.addElement("describe");
                    describe.setText("<![CDATA[]]>");

                    /*************修改正文里面链接路劲--开始***************/
                    Element text = article.addElement("text");
                    text.setText("<![CDATA["+(StringUtils.isNotBlank(contentText) ? contentText : "") + "]]>");
                    /*************修改正文里面链接路劲--结束***************/

                    Element datetime = article.addElement("datetime");
                    String dTime = rs.getString("release_date");
                    if(dTime != null){
                        dTime = DATE_FORMAT_ALL.format(DATE_FORMAT_ALL.parse(dTime));
                    }
                    datetime.setText("<![CDATA["+(dTime == null ? "" : dTime)+"]]>");
                    Element deploytime = article.addElement("deploytime");
                    deploytime.setText("<![CDATA["+(dTime == null ? "": dTime)+"]]>");
                    Element validend = article.addElement("validend");
                    validend.setText("<![CDATA[0000-00-00]]>");
                    Element image = article.addElement("image");
                    String imagePath = transferTypeImage(rs.getString("imagePath"));
                    image.setText("<![CDATA[" + (imagePath == null ? "" :  imagePath) + "]]>");
                    Element flash = article.addElement("flash");
                    flash.setText("<![CDATA[]]>");

                    Element media = article.addElement("media");
                    String mediaPath = transferMediaPath(rs.getString("mediaPath"));
                    media.setText("<![CDATA["+ (mediaPath == null ? "": mediaPath) +"]]>");
                    Element attach = article.addElement("attach");
                    attach.setText("<![CDATA[]]>");
                }

            }
            if (count > end) {
                break;
            }
        }
        createXml(document,path,end,count);
        if (count > end) {
            getArticle(typeId,start + 200, end + 200,path);
        }else {
            close(conn,pstmt,rs);
        }
    }

    /**
     * 产生xml
     * */
    public static void createXml(Document document, String fileName, int end, int count) throws Exception{
        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();
        try {
            String path = "E:/cxqdzl/多信息"+fileName;
            // System.out.println(path);
            File file = new File(path);
            if(!file.exists()){
                file.mkdir();
            }
            System.out.println(path +"/" + ((end / 200) - 1) +"_"+ (count > 1 ? ((count-1) % 200 == 0 ? 200 : count % 200) : count) +"条");
            XMLWriter writer = new XMLWriter(new FileOutputStream(path +"/" + ((end / 200) - 1) +"_"+ (count > 1 ? ((count-1) % 200 == 0 ? 200 : count % 200) : count) +"条"+ ".xml"), format);
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

    /**
     * 转换标题图路径
     * */
    public static String transferTypeImage(String image){
        if (StringUtils.isNotBlank(image)){
            if (image.endsWith(".jpg") || image.endsWith(".png") || image.endsWith(".bmp")|| image.endsWith(".gif"))
                return PATH_PREFIX + image;
            else
                return null;
        }
        return null;
    }

    /**
     * 转换视屏路径
     * */
    public static String transferMediaPath(String media) {
        if (StringUtils.isNotBlank(media)) {
            if (media.endsWith(".mp4") || media.endsWith(".avi") || media.endsWith(".wmv"))
                return PATH_PREFIX + media;
            else
                return null;
        }
        return null;
    }

        /**
         * 转换正文内容资源路径
         * */
    public static String testReplaceTxt(String contentText){
        for (int i = 0; i < ORIGINAL_PATH.length; i++){
            contentText = contentText.replaceAll(ORIGINAL_PATH[i],REPLACE_PATH[i]);
        }
        return contentText;
    }

    /**
     * 创建站点文件
     * */
    public static void createFile(String path){
        path = "E:/cxqdzl/多信息" + path;
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
    }

    /**
     * 统计某一栏目下的文章数目
     */
    public static int getCount(Long typeId) throws Exception{
        StringBuilder sb = new StringBuilder("SELECT count(*) as count FROM b_article b where b.type_id = ").append(typeId);
        Connection conn = connection();
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        int count = 0;
        while(rs.next()){
            count = rs.getInt("count");
        }
        return count;
    }

    public static void getAllContentPath() throws Exception{
        StringBuilder sb = new StringBuilder("SELECT content  FROM b_article ");
        Connection conn = connection();
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            String contentText = rs.getString("content");
            if(StringUtils.isNotBlank(contentText))
                getPath(contentText);
        }
    }
    public static void getAllContentPath1() throws Exception{
        StringBuilder sb = new StringBuilder("SELECT ba.id,ba.title,ba.content,bat.name,bat.id as tid FROM b_article ba left join b_articletype bat on ba.type_id = bat.id");
        Connection conn = connection();
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        Map<String,String> map = new HashMap<String, String>();
        Map<String,String> articleMap = new HashMap<String, String>();
        String pattern1 = "<img(.*?)src=\"/kxzl/upload_file/(.*?)\"";
        String pattern2 = "<a(.*?)href=\"/kxzl/upload_file/(.*?)\"";
        while(rs.next()){
            String contentText = rs.getString("content");
            String channelId = rs.getString("tid");
            String channelName = rs.getString("name");
            if(StringUtils.isNotBlank(contentText)){
                Pattern p=Pattern.compile(pattern1,Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(contentText);
                String match;
                while (m.find()) {
                    map.put(channelId,channelName);
                    articleMap.put(channelId,"");
                    match = m.group();
                    System.out.println(match);
                }
            }
        }
        if (!map.isEmpty()){
            for (Map.Entry<String,String> e : map.entrySet()){
                System.out.println("栏目id：" + e.getKey() + " ----------栏目名称：" + e.getValue());
            }
        }
    }

    /**
     * 统计正文中的附件路径
     * */
    public static void getPath(String contentText){
        getPattern(contentText,REGULAR_1);
        getPattern(contentText,REGULAR_2);
        getPattern(contentText,REGULAR_3);
        getPattern(contentText,REGULAR_4);
    }
    /**
     * 打印出匹配规则的路径
     * */
    public static void getPattern(String contentText,String regular){
        Pattern p=Pattern.compile(regular,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(contentText);
        String match;
        while (m.find()) {
            match = m.group();
            System.out.println(match);
        }
    }

    /**
     * 创建连接
     * */
    public static Connection connection() throws Exception {
        Class.forName(DRIVER);
        Connection conn =  DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        return conn;
    }

    /**
     * 关闭资源
     * */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) throws Exception{
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

}
