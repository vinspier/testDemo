package jdbc;

import java.sql.*;

/**
 * 测试调用mysql存储过程
 *
 * 在调用之前，需在数据库中先创建好存储过程
 * */
public class TestProduce {
    public static void main(String[] args) {
        Connection conn = JdbcUtil.getMysqlConnection("127.0.0.1","3306","risen_gs_hzcy","root","root");

        testCount(conn);
        testCollection(conn);
        testInOut(conn);
    }

    /**
     * 测试输出为单个结果
     *
     * 对应mysql存储过程
     DELIMITER $$

     USE `risen_gs_hzcy`$$

     DROP PROCEDURE IF EXISTS `countUser`$$

     CREATE DEFINER=`root`@`localhost` PROCEDURE `countUser`(OUT countNum LONG)
     BEGIN
     SELECT COUNT(*) INTO countNum FROM jc_user;
     END$$

     DELIMITER ;
     * */
    public static void testCount(Connection conn){
        String sql = "{call countUser(?)}";
        try {
            CallableStatement cs = conn.prepareCall(sql);
            cs.registerOutParameter(1, Types.BIGINT);
            cs.execute();
            System.out.println(cs.getLong(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试返回为结果集
     * 对应mysql存储过程
     *
     DELIMITER $$

     USE `risen_gs_hzcy`$$

     DROP PROCEDURE IF EXISTS `selectUser`$$

     CREATE DEFINER=`root`@`localhost` PROCEDURE `selectUser`(IN admin_is INT)
     BEGIN
     SELECT * FROM jc_user WHERE is_admin = admin_is;
     END$$

     DELIMITER ;
     * */
    public static void testCollection(Connection conn){
        String sql = "{call selectUser(?)}";
        try {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1,0);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next()){
                System.out.println("ID:   " + rs.getString("user_id") + " username: " +  rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试有输入输出的存储过程
     * */
    public static void testInOut(Connection conn){
        String sql = "{call inOutUser(?,?)}";
        try {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1,5);
            cs.registerOutParameter(2,Types.BIGINT);
            cs.execute();
            System.out.println("User Count:   " + cs.getString("countNum"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
