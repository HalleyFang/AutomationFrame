package core.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 数据库连接类
 *
 * @author Halley.Fang
 */
public class DbTestDru {
    private static Logger logger = Logger.getLogger(DbTestDru.class);
    public static DruidDataSource dataSource;

    public static void setDruidDataSource(String type) {
        ResourceBundle resourceBundle = ReadConfig.readConfig("jdbc.properties");
        String url = null;
        String user = null;
        String password = null;
        String dbDriver = null;
        switch (type) {
            case "mysql":
                url = resourceBundle.getString("mysql.jdbc.url");
                user = resourceBundle.getString("mysql.jdbc.username");
                password = resourceBundle.getString("mysql.jdbc.password");
                dbDriver = resourceBundle.getString("mysql.jdbc.Driver");
                break;
            case "oracle":
                url = resourceBundle.getString("oracle.jdbc.url");
                user = resourceBundle.getString("oracle.jdbc.username");
                password = resourceBundle.getString("oracle.jdbc.password");
                dbDriver = resourceBundle.getString("oracle.jdbc.Driver");
                break;
            case "postgresql":
                url = resourceBundle.getString("postgresql.jdbc.url");
                user = resourceBundle.getString("postgresql.jdbc.username");
                password = resourceBundle.getString("postgresql.jdbc.password");
                dbDriver = resourceBundle.getString("postgresql.jdbc.Driver");
                break;
            case "sqlserver":
                url = resourceBundle.getString("sqlserver.jdbc.url");
                user = resourceBundle.getString("sqlserver.jdbc.username");
                password = resourceBundle.getString("sqlserver.jdbc.password");
                dbDriver = resourceBundle.getString("sqlserver.jdbc.Driver");
                break;
        }
        if (url != null && user != null && password != null && dbDriver != null) {
            dataSource = new DruidDataSource();
            //获取驱动
            dataSource.setDriverClassName(dbDriver);
            //建立连接
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            dataSource.setMaxWait(10000);
            dataSource.setBreakAfterAcquireFailure(true);
        }
    }

    public synchronized static String selectCount(String sql) {
        String count = "-1";
        try {
            DruidPooledConnection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<String> resultlist = new ArrayList<>();
            while (resultSet.next()) {
                count = resultSet.getString(1);
                resultlist.add(count);
            }
            if (resultlist.size() > 1) {
                for (int a = 0; a <= resultlist.size() - 2; a++) {
                    count = resultlist.get(resultlist.size() - 2 - a) + "," + count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public synchronized static void dbTestDu(String sql) {
        try {
            DruidPooledConnection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            int sql_status = statement.executeUpdate();
            System.out.println("sql_status:" + sql_status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static String selectRows(String sql) {
        String rows = "-1";
        try {
            DruidPooledConnection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<String> resultlist = new ArrayList<>();
            while (resultSet.next()) {
                rows = resultSet.getString(1);
                resultlist.add(rows);
            }
            if (resultlist.size() > 1) {
                for (int a = 0; a <= resultlist.size() - 2; a++) {
                    rows = resultlist.get(resultlist.size() - 2 - a) + "," + rows;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }


}
