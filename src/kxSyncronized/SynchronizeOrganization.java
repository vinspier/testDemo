package kxSyncronized;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 * @ClassName: Test
 * @Description: 同步组织结构
 * @Author:
 * @Date: 2019/11/20 15:33
 * @Version V1.0
 **/
public class SynchronizeOrganization {

   private static Connection conn = null;

    //mysql驱动
    public final static String DRIVER = "com.mysql.jdbc.Driver";
    //选择目的数据库位置
    // public final  static String URL = "jdbc:mysql://127.0.0.1:3306/risen_gxkx";
    public final  static String URL = "jdbc:mysql://192.168.5.54:9421/risen_gxkx";
    //数据库用户名
    public final  static String USER_NAME = "root";
    //数据库密码
    public final  static String PASSWORD = "root";

    public static final SimpleDateFormat DATE_FORMAT_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception{
       // String fetchSql = "SELECT * FROM risen_colleges_univers WHERE risencs_parent_uuid = '8ECAC8B88F75424181804A1CD6798AA6'";
        String fetchSql = "SELECT * FROM risen_colleges_univers WHERE RISENCS_LEVEL_CODE LIKE '00%' AND RISENCS_UUID <> '8ECAC8B88F75424181804A1CD6798AA6' ORDER BY LENGTH(RISENCS_LEVEL_CODE) ASC";
        String modifySql = "INSERT INTO `core_organization` VALUES (NULL, ?, ?, NULL, ?, ?, ?, ?, ?, '2', '1', ?, ?, ?, ?, NULL, NULL, NULL, NULL)";
        PreparedStatement fetchStatement = createPrepareStatement(fetchSql);
        PreparedStatement modifyStatement = createPrepareStatement(modifySql);
        ResultSet fetchResult = fetchStatement.executeQuery();
        int index = 0;
        while (fetchResult.next()){
            System.out.println(index ++);
            modifyStatement.setString(1,fetchResult.getString("RISENCS_UUID"));
            modifyStatement.setString(2,fetchResult.getString("RISENCS_LEVEL_CODE"));

            modifyStatement.setString(3,fetchResult.getString("RISENCS_PARENT_UUID"));
            modifyStatement.setString(4,fetchResult.getString("RISENCS_NAME"));
            modifyStatement.setString(5,fetchResult.getString("RISENCS_NAME"));
            modifyStatement.setString(6,fetchResult.getString("RISENCS_ADDR"));
            modifyStatement.setString(7,fetchResult.getString("RISENCS_PHONE"));

            modifyStatement.setString(8,fetchResult.getString("RISENCS_ORDER"));
            modifyStatement.setTimestamp(9,fetchResult.getTimestamp("RISENCS_CDATE"));
            modifyStatement.setTimestamp(10,fetchResult.getTimestamp("RISENCS_UDATE"));

            modifyStatement.setString(11,fetchResult.getString("RISENCS_CREATOR"));
            modifyStatement.addBatch();
        }
        modifyStatement.executeBatch();
        close(conn,fetchStatement,fetchResult);
        close(null,modifyStatement,null);
    }
    public static PreparedStatement createPrepareStatement(String sql) throws Exception{
      return  getConnection().prepareStatement(sql);
    }

    /**
     * 创建连接
     * */
    public static Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        if (conn == null || conn.isClosed()){
            conn =  DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        }
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
