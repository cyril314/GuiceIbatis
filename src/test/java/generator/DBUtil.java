package generator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @DBUtil:数据库访问工具类<br/>
 */
public class DBUtil {

    private static Connection con = null;

    /**
     * 打开连接
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Connection openConnection() throws SQLException, ClassNotFoundException, IOException {
        if (null == con || con.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "root");
        }
        return con;
    }

    /**
     * 关闭连接
     *
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        try {
            if (null != con) con.close();
        } finally {
            con = null;
            System.gc();
        }
    }

    /**
     * 查询List<Map<String, Object>>结果
     *
     * @param con
     * @param sql
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static List<Map<String, String>> queryMapList(Connection con, String sql) throws SQLException, InstantiationException, IllegalAccessException {
        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        Statement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.createStatement();
            rs = preStmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (null != rs && rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < columnCount; i++) {
                    String name = rsmd.getColumnName(i + 1);
                    String value = rs.getObject(name).toString().trim();
                    map.put(name, value);
                }
                lists.add(map);
            }
        } finally {
            if (null != rs) rs.close();
            if (null != preStmt) preStmt.close();
        }
        return lists;
    }

    /**
     * 根据条件查询List<Map<String, Object>>结果集
     *
     * @param con
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static List<Map<String, Object>> queryMapList(Connection con, String sql, Object... params) throws SQLException, InstantiationException, IllegalAccessException {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            rs = preStmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (null != rs && rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnCount; i++) {
                    String name = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(name);
                    map.put(name, value);
                }
                lists.add(map);
            }
        } finally {
            if (null != rs) rs.close();
            if (null != preStmt) preStmt.close();
        }
        return lists;
    }

    /**
     * 查询List<T>结果集
     *
     * @param con
     * @param sql
     * @param beanClass
     */
    public static <T> List<T> queryBeanList(Connection con, String sql, Class<T> beanClass) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> lists = new ArrayList<T>();
        Statement stmt = null;
        ResultSet rs = null;
        Field[] fields = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            fields = beanClass.getDeclaredFields();
            for (Field f : fields)
                f.setAccessible(true);
            while (null != rs && rs.next()) {
                T t = beanClass.newInstance();
                for (Field f : fields) {
                    String name = f.getName();
                    try {
                        Object value = rs.getObject(name);
                        setValue(t, f, value);
                    } catch (Exception e) {
                    }
                }
                lists.add(t);
            }
        } finally {
            if (null != rs) rs.close();
            if (null != stmt) stmt.close();
        }
        return lists;
    }

    /**
     * 根据条件查询List<T>结果集
     *
     * @param con
     * @param sql
     * @param beanClass
     * @param params
     */
    public static <T> List<T> queryBeanList(Connection con, String sql, Class<T> beanClass, Object... params) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> lists = new ArrayList<T>();
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        Field[] fields = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            rs = preStmt.executeQuery();
            fields = beanClass.getDeclaredFields();
            for (Field f : fields)
                f.setAccessible(true);
            while (null != rs && rs.next()) {
                T t = beanClass.newInstance();
                for (Field f : fields) {
                    String name = f.getName();
                    try {
                        Object value = rs.getObject(name);
                        setValue(t, f, value);
                    } catch (Exception e) {
                    }
                }
                lists.add(t);
            }
        } finally {
            if (null != rs) rs.close();
            if (null != preStmt) preStmt.close();
        }
        return lists;
    }

    /**
     * 查询Bean类结果集
     *
     * @param con
     * @param sql
     * @param beanClass
     * @return
     */
    public static <T> T queryBean(Connection con, String sql, Class<T> beanClass) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> lists = queryBeanList(con, sql, beanClass);
        if (lists.size() != 1) throw new SQLException("SqlError：期待一行返回值，却返回了太多行！");
        return lists.get(0);
    }

    /**
     * 根据参数查询Bean类结果集
     *
     * @param con
     * @param sql
     * @param beanClass
     * @param params
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T queryBean(Connection con, String sql, Class<T> beanClass, Object... params) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> lists = queryBeanList(con, sql, beanClass, params);
        if (lists.size() != 1) throw new SQLException("SqlError：期待一行返回值，却返回了太多行！");
        return lists.get(0);
    }

    /**
     * 执行SQL更新
     */
    public static int execute(Connection con, String sql) throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            return stmt.executeUpdate(sql);
        } finally {
            if (null != stmt) stmt.close();
        }
    }

    /**
     * 根据条件执行SQL更新
     */
    public static int execute(Connection con, String sql, Object... params) throws SQLException {
        PreparedStatement preStmt = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            return preStmt.executeUpdate();
        } finally {
            if (null != preStmt) preStmt.close();
        }
    }

    /**
     * @param con
     * @param sqlList
     */
    public static int[] executeAsBatch(Connection con, List<String> sqlList) throws SQLException {
        return executeAsBatch(con, sqlList.toArray(new String[]{}));
    }

    /**
     * @param con
     * @param sqlArray
     */
    public static int[] executeAsBatch(Connection con, String[] sqlArray) throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            for (String sql : sqlArray) {
                stmt.addBatch(sql);
            }
            return stmt.executeBatch();
        } finally {
            if (null != stmt) {
                stmt.close();
            }
        }
    }

    /**
     * @param con
     * @param sql
     * @param params
     */
    public static int[] executeAsBatch(Connection con, String sql, Object[][] params) throws SQLException {
        PreparedStatement preStmt = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                Object[] rowParams = params[i];
                for (int k = 0; k < rowParams.length; k++) {
                    Object obj = rowParams[k];
                    preStmt.setObject(k + 1, obj);
                }
                preStmt.addBatch();
            }
            return preStmt.executeBatch();
        } finally {
            if (null != preStmt) {
                preStmt.close();
            }
        }
    }

    /**
     * @throws IllegalAccessException
     * @t
     * @f
     * @value
     */
    private static <T> void setValue(T t, Field f, Object value) throws IllegalAccessException {
        // TODO 以数据库类型为准绳，还是以java数据类型为准绳？还是混合两种方式？
        if (null == value) return;
        String v = value.toString();// 值
        String n = f.getType().getName();
        if ("java.lang.Byte".equals(n) || "byte".equals(n)) {
            f.set(t, Byte.parseByte(v));
        } else if ("java.lang.Short".equals(n) || "short".equals(n)) {
            f.set(t, Short.parseShort(v));
        } else if ("java.lang.Integer".equals(n) || "int".equals(n)) {
            f.set(t, Integer.parseInt(v));
        } else if ("java.lang.Long".equals(n) || "long".equals(n)) {
            f.set(t, Long.parseLong(v));
        } else if ("java.lang.Float".equals(n) || "float".equals(n)) {
            f.set(t, Float.parseFloat(v));
        } else if ("java.lang.Double".equals(n) || "double".equals(n)) {
            f.set(t, Double.parseDouble(v));
        } else if ("java.lang.String".equals(n)) {
            f.set(t, value.toString());
        } else if ("java.lang.Character".equals(n) || "char".equals(n)) {
            f.set(t, (Character) value);
        } else if ("java.lang.Date".equals(n)) {
            f.set(t, new Date(((java.sql.Date) value).getTime()));
        } else if ("java.lang.Timer".equals(n)) {
            f.set(t, new Time(((Time) value).getTime()));
        } else if ("java.sql.Timestamp".equals(n)) {
            f.set(t, (java.sql.Timestamp) value);
        } else {
            System.out.println("SqlError：暂时不支持此数据类型，请使用其他类型代替此类型！");
        }
    }

    /**
     * 根据参数执行存过
     *
     * @param con
     * @throws SQLException
     * @procedureName 存储过程名字
     * @params 参数
     */
    public static void executeProcedure(Connection con, String procedureName, Object... params) throws SQLException {
        CallableStatement proc = null;
        try {
            proc = con.prepareCall(procedureName);
            for (int i = 0; i < params.length; i++) {
                proc.setObject(i + 1, params[i]);
            }
            proc.execute();
        } finally {
            if (null != proc) proc.close();
        }
    }

    /**
     * 查询分页List<List<T>>结果集
     *
     * @param lists
     * @param pageSize
     */
    public static <T> List<List<T>> listLimit(List<T> lists, int pageSize) {
        List<List<T>> llists = new ArrayList<List<T>>();
        for (int i = 0; i < lists.size(); i = i + pageSize) {
            try {
                List<T> list = lists.subList(i, i + pageSize);
                llists.add(list);
            } catch (IndexOutOfBoundsException e) {
                List<T> list = lists.subList(i, i + (lists.size() % pageSize));
                llists.add(list);
            }
        }
        return llists;
    }
}