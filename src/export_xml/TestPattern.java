package export_xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 统计正文匹配路径规则
 * */
public class TestPattern {

    public static final String REGULAR_1 = "<img(.*?)src=\"(.*?)\"";
    public static final String REGULAR_2 = "<a(.*?)href=\"(.*?)\"";
    public static final String REGULAR_3 = "<A(.*?)href=\"(.*?)\"";
    public static final String REGULAR_4 = "<IMG(.*?)src=\"(.*?)\"";

    public static void main(String[] args) {
        String contentText = "<P><BR>　　为深入实施科学素质行动计划，大力提升公民科学素质，根据国务院《全民科学素质行动计划纲要（2006-2010—2020年）》（以下简称《科学素质纲要》）、《浙江省全民科学素质行动计划实施方案(2011-2015年）》和《浙江省科普事业发展“十二五”规划》精神，特制定本实施方案。</P>\n" +
                "<P><IMG border=0 src=\"/editor/sysimage/icon16/doc.gif\"><A href=\"/editor/uploadfile/20140225095423582.doc\" target=_blank>舟山市全民科学素质行动计划实施方案.doc</A></P>";

        getPattern(contentText,REGULAR_1);
        getPattern(contentText,REGULAR_2);
        getPattern(contentText,REGULAR_3);
        getPattern(contentText,REGULAR_4);
    }



    public static void getPattern(String contentText,String regular){
        Pattern p=Pattern.compile(regular,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(contentText);
        String match;
        while (m.find()) {
            match = m.group();
            System.out.println(match);
        }
    }
}
