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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSqlServer {

//数据库连接

    //sqlserver驱动
    public final static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //选择目的数据库位置
    /**  衢州科协*/
    public final  static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=dzkjw";
   //  public final  static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=kx_cms";
   //  public final  static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=kx_new";
    //数据库用户名
    public final  static String USER_NAME = "sa";
    //数据库密码
    public final  static String PASSWORD = "root";

    public static final SimpleDateFormat DATE_FORMAT_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_PART = new SimpleDateFormat("yyyy-MM-dd");

    public static final String PATH_PREFIX = "http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old";

    public static final String ORIGIN_SRC_PATH1 = "src=\"/editor/sysimage/";
    public static final String ORIGIN_HREF_PATH1 = "href=\"/editor/sysimage/";
    public static final String ORIGIN_SRC_REPLACE1 = "src=\"/picture/old/editor/sysimage/";
    public static final String ORIGIN_HREF_REPLACE1 = "href=\"/picture/old/editor/sysimage/";

    public static final String ORIGIN_SRC_PATH2 = "src=\"/editor/uploadfile/";
    public static final String ORIGIN_HREF_PATH2 = "href=\"/editor/uploadfile/";
    public static final String ORIGIN_SRC_REPLACE2 = "src=\"/picture/old/editor/uploadfile/";
    public static final String ORIGIN_HREF_REPLACE2 = "href=\"/picture/old/editor/uploadfile/";

    public static final String[] ORIGINAL_PATH = {"src=\"/editor/sysimage/","href=\"/editor/sysimage/",
                                                    "src=\"/editor/uploadfile/","href=\"/editor/uploadfile/",
                                                    "src=\"/editor_new/sysimage/","href=\"/editor_new/sysimage/",
                                                    "src=\"/uploadfiles/","href=\"/uploadfiles/",
                                                    "src=\"/uploadFiles/","href=\"/uploadFiles/",
                                                    "src=\"/images/","href=\"/images/",
                                                    "src=\"/html/main/","href=\"/html/main/"
                                                };
    public static final String[] REPLACE_PATH = {"src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/editor/sysimage/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/editor/sysimage/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/editor/uploadfile/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/editor/uploadfile/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/editor_new/sysimage/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/editor_new/sysimage/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/uploadfiles/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/uploadfiles/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/uploadFiles/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/uploadFiles/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/images/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/images/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/html/main/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/html/main/"
                                                };

    public static final String[] ORIGINAL_PATH2 = {"src=\"http://qzkx.qzedu.net/","href=\"http://qzkx.qzedu.net/",
                                                    "src=\"/uploadFiles/","href=\"/uploadFiles/",
                                                    "src=\"/UploadFiles/","href=\"/UploadFiles/",
                                                    "src=\"/UpLoad/image/","href=\"/UpLoad/image/",
                                                    "src=\"/manage/KindEditor/","href=\"/manage/KindEditor/",
                                                    "src=\"/UpLoad/file/","href=\"/UpLoad/file/"
                                            };
    public static final String[] REPLACE_PATH2 = {   "src=\"","href=\"",
            "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/uploadFiles/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/uploadFiles/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/UploadFiles/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/UploadFiles/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/UpLoad/image/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/UpLoad/image/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/manage/KindEditor/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/manage/KindEditor/",
                                                    "src=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/UpLoad/file/","href=\"http://zjjcmspublic.oss-cn-hangzhou.aliyuncs.com/jcms_files/jcms1/web2807/site/picture/old/UpLoad/file/"

                                            };
    /** 部门栏目需要按照特定规则 */
    public static final List<String> specialChannel = Arrays.asList(new String[]{"1000110004","1000110008","1000210002","1000210003","1000410002","1000410003"
                                                ,"1000510003","1000510004","1000510005","1000610003","1000610001","1000610002"
            ,"1001010001","1001010002","1001010003","1001010004","1001010005","1000710001"});

    public static final String MEDIA_CONTENT_PREFIX = "<script type=\"text/javascript\" src=\"/picture/old/ckplayer/ckplayer.js\" charset=\"utf-8\"></script>\n" +
            "<SCRIPT type=text/javascript> \n" +
            "   var flashvars={\n" ;

           String media = "    f:'/sp/20180920.mp4',\n";

    public static final String MEDIA_CONTENT_SUFERIX ="    c:0,\n" +
            "    p:1,\n" +
            "    b:1\n" +
            "    };\n" +
            "  var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};\n" +
            "  CKobject.embedSWF('/picture/old/ckplayer/ckplayer.swf','a1','ckplayer_a1','680','400',flashvars,params);  \n" +
            "  function closelights(){//关灯\n" +
            "    alert(' 本演示不支持开关灯');\n" +
            "  }\n" +
            "  function openlights(){//开灯\n" +
            "    alert(' 本演示不支持开关灯');\n" +
            "  }\n" +
            "</script>";

    public static final String REGULAR_1 = "<img(.*?)src=\"(.*?)\"";
    public static final String REGULAR_2 = "<a(.*?)href=\"(.*?)\"";
    public static final String REGULAR_3 = "<A(.*?)href=\"(.*?)\"";
    public static final String REGULAR_4 = "<IMG(.*?)src=\"(.*?)\"";

    /**
     * 程序入口
     * */
    public static void main(String[] args) throws Exception{
       // getChannels("1000110004",0,"","");
        /** 设置父栏目 */
       // getChannels("",0,"","");
        getChannels("10012",0,"0","");
       // Connection connection = connection();
       // System.out.println(connection);
//        String context = "<p style=\"text-align: center;\"><img src=\"/uploadFiles/201909/20190902153708958.png\" border=\"0\" align=\"center\"><br> </p>";
//        System.out.println(testReplaceTxt(context));
    }


    /**
     * 设置父栏目值
     * */
    public static void setPatent(String lmbh) throws Exception {
        Connection conn = connection();
        StringBuilder sb = new StringBuilder();
        sb.append("update [dbo].[lmgl]  set parent = 0 where lmbh = '" + lmbh + "'");
        Statement statement = conn.createStatement();
       //  PreparedStatement pstmt = conn.prepareStatement(sb.toString());
       // pstmt.executeUpdate(sb.toString());
        statement.execute(sb.toString());
       // close(conn,pstmt,null);
        statement.close();
        conn.close();
    }

    /**
     * 获取栏目树状
     * */
    public static void getChannels(String superLmbh,int tap,String parentCode,String fileName)  throws Exception {
        Connection conn = connection();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from [dbo].[lmgl] [a] left join [dbo].[zdgl] [b] on [a].[sszd] = [b].[zdbh]");
        if (StringUtils.isNotBlank(superLmbh)){
            sb.append( " where [a].[lmbh] like '" ).append(superLmbh).append("%'");
            if(StringUtils.isNotBlank(parentCode)){
                sb.append(" and [a].[parent] = '").append(parentCode).append("'");
            }
        }else{
            if(StringUtils.isNotBlank(parentCode)){
                sb.append(" where [a].[parent] = '").append(parentCode).append("'");
            }
        }
        sb.append(" order by [a].[lmbh]");
        // System.out.println(sb.toString());
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        int count = 0;
        while(rs.next()){
            count++;
            String lmbh = rs.getString("lmbh");
            // 设置栏目父级
           /* System.out.println(lmbh);
            if (lmbh.length() < 7)
                setPatent(lmbh);*/

            String lmmc = rs.getString("lmmc");
            String zdmc = rs.getString("zdmc");
            createFile(zdmc);
            String tap1 = "";
            for(int i = 0; i < tap; i++){
                tap1 += "	";
            }
            String path = fileName + "/" + lmmc;

            /** 次级栏目 */
            if (StringUtils.isNotBlank(parentCode)){
              //  System.out.println(tap1 + "(" + lmmc + ")" + lmbh);
                /** 导出该栏目文章 */
                getArticles(lmbh,0,200,"/" + zdmc + path);
                /** 打印匹配规则的路径 */
            //    getContentText(lmbh);
                getChannels(lmbh,tap + 1,null,path);
            }else {
                if (count > 1){
                //    System.out.println(tap1 + "(" + lmmc + ")" + lmbh);
                    /** 导出该栏目文章 */
                    getArticles(lmbh,0,200,"/" + zdmc + path);
                    /** 打印匹配规则的路径 */
                 //   getContentText(lmbh);
                }
            }
        }
    }

    /**
     * 创建站点文件
     * */
    public static void createFile(String path){
         path = "E:/qzkx/多信息/" + path;
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
    }

    /**
     * 获取栏目下的文章
     * */
    public static void getArticles(String lmbh,int start,int end,String fileName) throws Exception {
        Connection conn = connection();
        StringBuilder sb = new StringBuilder();
       // sb.append("SELECT * FROM [dbo].[xxgl_lm] [a] LEFT JOIN [dbo].[xxgl] [b] ON [a].[xxid] = [b].id LEFT JOIN [dbo].[xxgl_kz] [c] ON [b].[id] = [c].[xxid] WHERE [a].[shbj] = '1' and [a].[tjsj] > '2019-09-19 17:30:00' and [a].[lmbh] = '").append(lmbh).append("'");
        sb.append("SELECT * FROM [dbo].[xxgl_lm] [a] LEFT JOIN [dbo].[xxgl] [b] ON [a].[xxid] = [b].id LEFT JOIN [dbo].[xxgl_kz] [c] ON [b].[id] = [c].[xxid] WHERE [a].[shbj] = '1' and [a].[lmbh] = '").append(lmbh).append("'");
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
        while(rs.next()) {
            count++;
            if(count < start){
                continue;
            }
            if(count > start && count <= end){

                // 创建channel子节点
                Element article = book.addElement("article");
                // 创建title子节点
                Element title = article.addElement("title");
                title.setText("<![CDATA["+(rs.getString("bt") == null ? "" : rs.getString("bt")) +"]]>");

                Element caption = article.addElement("caption");
                String subTitle = getSubTitle(rs.getString("varchar1"),rs.getString("varchar2"),rs.getString("varchar3"));
                caption.setText("<![CDATA["+ (subTitle == null ? "" : subTitle) +"]]>");

                Element leadtitle = article.addElement("leadtitle");
                leadtitle.setText("<![CDATA[]]>");
                Element linktitle = article.addElement("linktitle");
                linktitle.setText("<![CDATA[]]>");
                Element href = article.addElement("href");
                String link = getLink(rs.getString("varchar1"),rs.getString("varchar2"),rs.getString("varchar3"));
                href.setText("<![CDATA["+ (link == null ? "" : link) +"]]>");
                Element author = article.addElement("author");
                String authorName = rs.getString("zz");
                author.setText("<![CDATA["+(authorName == null ? "" : authorName)+"]]>");
                Element source = article.addElement("source");
//                if (StringUtils.isNotBlank(rs.getString("ly")) && "浙江省科协".equals(rs.getString("ly"))){
//                    source.setText("<![CDATA["+ (rs.getString("cc") == null ? "" : rs.getString("cc")) +"]]>");
//                }else {
//                    source.setText("<![CDATA["+ (rs.getString("ly") == null ? "" : rs.getString("ly")) +"]]>");
//                }
                if (StringUtils.isNotBlank(rs.getString("ly"))){
                    source.setText("<![CDATA["+ rs.getString("ly") +"]]>");
                }else {
                    source.setText("<![CDATA[衢州市科协]]>");
                }
                Element userid = article.addElement("userid");
                userid.setText("<![CDATA[]]>");//添加了userid  0000137797不需要的话去掉
                Element editor = article.addElement("editor");
                editor.setText("<![CDATA["+(authorName == null ? "" : authorName)+"]]>");
                Element keyword = article.addElement("keyword");
                keyword.setText("<![CDATA[" + (rs.getString("gjz") == null ? "" : rs.getString("gjz")) + "]]>");
                Element classname = article.addElement("classname");
                classname.setText("<![CDATA[]]>");
                Element describe = article.addElement("describe");
                describe.setText("<![CDATA["+ (rs.getString("text1") == null ? "" : rs.getString("text1")) +"]]>");

                /*************修改正文里面链接路劲--开始***************/
                Element text = article.addElement("text");
                String contentTxt = rs.getString("nr");
                if(contentTxt != null){
                    contentTxt = testReplaceTxt(contentTxt);
                }
                /** 添加附件*/
                contentTxt += getAttachment(rs.getString("varchar1"),rs.getString("varchar2"),rs.getString("varchar3"));
                String mediaPath = getMedia(rs.getString("varchar2"));
                if (StringUtils.isNotBlank(mediaPath)){
                    contentTxt += MEDIA_CONTENT_PREFIX + "    f:'" + mediaPath + "',\n" + MEDIA_CONTENT_SUFERIX;
                }
                text.setText("<![CDATA["+(StringUtils.isNotBlank(contentTxt) ? contentTxt : "") + "]]>");
                /*************修改正文里面链接路劲--结束***************/

                Element datetime = article.addElement("datetime");
                String dTime = null;
//                if (specialChannel.contains(lmbh)){
//                    dTime = rs.getString("tjsj");
//                }else{
//                    dTime = rs.getString("scjtymsj");
//                }
                if (lmbh.startsWith("10012")){
                    dTime = rs.getString("tjsj");
                }else {
                    dTime = rs.getString("scjtymsj");
                }
                if(dTime != null){
                    dTime = DATE_FORMAT_ALL.format(DATE_FORMAT_ALL.parse(dTime));
                }
                datetime.setText("<![CDATA["+(dTime == null ? "" : dTime)+"]]>");
                Element deploytime = article.addElement("deploytime");
                /*dTime = rs.getString("tjsj");
                if(dTime != null){
                    dTime = DATE_FORMAT_ALL.format(DATE_FORMAT_ALL.parse(dTime));
                }*/
                deploytime.setText("<![CDATA["+(dTime == null ? "": dTime)+"]]>");
                Element validend = article.addElement("validend");
                validend.setText("<![CDATA[0000-00-00]]>");
                Element image = article.addElement("image");
                String typeImage = transferTypeImage(rs.getString("varchar1"));
                if (typeImage == null)
                    typeImage = transferTypeImage(rs.getString("varchar2"));
                image.setText("<![CDATA[" + (typeImage == null ? "" :  typeImage) + "]]>");
                Element flash = article.addElement("flash");
                flash.setText("<![CDATA[]]>");
                Element media = article.addElement("media");
                media.setText("<![CDATA["+ (mediaPath == null ? "": mediaPath) +"]]>");
                Element attach = article.addElement("attach");
                attach.setText("<![CDATA[]]>");
            }
            if (count > end) {
                break;
            }
        }
        createXml(document,fileName,end,count);
        if (count > end) {
            getArticles(lmbh,start + 200, end + 200,fileName);
        }else {
            close(conn,pstmt,rs);
        }
    }

    /**
     * 产生xml
     * */
    public static void createXml(Document document,String fileName,int end,int count) throws Exception{
        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();
        try {
            String path = "E:/qzkx/多信息"+fileName;
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
     * 转换视频路径
     * */
    public static String getMedia(String media){
        if (StringUtils.isNotBlank(media)){
            if (media.endsWith(".mp4") || media.endsWith(".wmv") || media.endsWith(".avi"))
                return PATH_PREFIX + media;
            else
                return "";
        }
        return "";
    }

    /**
     * 转换正文内容资源路径
     * */
    public static String testReplaceTxt(String contentText){
        for (int i = 0; i < ORIGINAL_PATH2.length; i++){
            contentText = contentText.replaceAll(ORIGINAL_PATH2[i],REPLACE_PATH2[i]);
        }
        return contentText;
    }

    public static String testReplaceTxt1(String txt){
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
        txt = txt.replaceAll("src='/","src='" + PATH_PREFIX +"/").replaceAll("src=\"/","src=\"" + PATH_PREFIX +"/")
                .replaceAll("href='/","href='" + PATH_PREFIX +"/").replaceAll("href=\"/","href=\"" + PATH_PREFIX +"/");
        return txt;
    }

    /**
     * 获取附件路径 拼接到正文
     * */
    public static String getAttachment(String varchar1,String varchar2,String varchar3){
        StringBuilder sb = new StringBuilder();
        if (judgeAttatchment(varchar1))
            sb.append("<p><a target=\"_blank\" href=\"").append(PATH_PREFIX).append(varchar1).append("\">").append(varchar1).append("</a></p>");
        if (judgeAttatchment(varchar2))
            sb.append("<p><a target=\"_blank\" href=\"").append(PATH_PREFIX).append(varchar2).append("\">").append(varchar2).append("</a></p>");
        if (judgeAttatchment(varchar3))
            sb.append("<p><a target=\"_blank\" href=\"").append(PATH_PREFIX).append(varchar3).append("\">").append(varchar3).append("</a></p>");
        return sb.toString();
    }

    /**
     * 获取副标题
     * */
    public static String getSubTitle(String varchar1,String varchar2,String varchar3){
        if (varchar1 != null &&!(varchar1.contains(".") || varchar1.contains("/")))
            return varchar1;
        else
            if (varchar2 != null && !(varchar2.contains(".") || varchar2.contains("/")))
                return varchar2;
            else
                if (varchar3 != null && !(varchar3.contains(".") || varchar3.contains("/")))
                    return varchar3;
                else
                    return null;
    }

    /**
     * 获取连接link
     * */
    public static String getLink(String varchar1,String varchar2,String varchar3){
        if (varchar1 != null && varchar1.startsWith("http") && !judgeMedia(varchar1))
            return varchar1;
        else
        if (varchar2 != null && varchar2.startsWith("http") && !judgeMedia(varchar2))
            return varchar2;
        else
        if (varchar3 != null && varchar3.startsWith("http") && !judgeMedia(varchar3))
            return varchar3;
        else
            return null;
    }

    /**
     * 判断是否是视屏类型
     * */
    public static boolean judgeMedia(String path){
        if (StringUtils.isNotBlank(path)){
            if (path.endsWith(".wmv") || path.endsWith(".mp4") || path.endsWith(".avi"))
                return true;
            else
                return false;
        }
        return false;
    }


    /**
     * 判断是否是图片类型
     * */
    public static boolean judgeImage(String path){
        if (StringUtils.isNotBlank(path)){
            if (path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".bmp") || path.endsWith(".gif"))
                return true;
            else
                return false;
        }
        return false;
    }

    /**
     * 判断是否是附件类型
     * */
    public static boolean judgeAttatchment(String path){
        if (StringUtils.isNotBlank(path)){
            if (path.endsWith(".pdf") || path.endsWith(".doc") || path.endsWith(".xls") || path.endsWith(".ppt"))
                return true;
            else
                return false;
        }
        return false;
    }

    /**
     * 获取栏目下的文章内容
     * */
    public static void getContentText(String lmbh) throws Exception{
        Connection conn = connection();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM [dbo].[xxgl_lm] [a] LEFT JOIN [dbo].[xxgl] [b] ON [a].[xxid] = [b].id WHERE [a].[lmbh] = '").append(lmbh).append("'");
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            getPath(rs.getString("nr"));
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
