package generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器 工具类
 */
public class GenMain {

    public static final String queryTable = "SELECT `table_name`, `engine`, `table_comment`, `create_time` FROM information_schema.tables WHERE `table_schema`=(SELECT DATABASE()) AND `table_name`=#{tableName}";
    public static final String queryColumns = "select `column_name`, `data_type`, `column_comment`, `column_key`, `extra` from information_schema.columns where `table_name`= #{tableName} and `table_schema`= (select database()) order by `ordinal_position`";

    public static void main(String[] args) throws Exception {
        String[] tableNames = {"lms_top"};
        File zipFile = new File("d:" + File.separator + "FitWeb.zip");
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFile));

        for (String tableName : tableNames) {
            // 查询表信息
            Map<String, String> table = queryTable(tableName);
            // 查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            // 生成代码
            generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
    }

    /**
     * 根据表名获取表字段类型等信息
     */
    private static List<Map<String, String>> queryColumns(String tableName) {
        try {
            Connection con = DBUtil.openConnection();
            List<Map<String, String>> list = DBUtil.queryMapList(con, queryColumns.replace("#{tableName}", "'" + tableName + "'"));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据表名获取字段名称
     */
    private static Map<String, String> queryTable(String tableName) {
        try {
            Connection con = DBUtil.openConnection();
            List<Map<String, String>> list = DBUtil.queryMapList(con, queryTable.replace("#{tableName}", "'" + tableName + "'"));
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取模版
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.ftl");
        templates.add("template/Dao.java.ftl");
        templates.add("template/Dao.xml.ftl");
        templates.add("template/Service.java.ftl");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns, ZipOutputStream zip) throws Exception {
        // 配置信息
        PropertiesConfiguration config = getConfig();
        // 表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("TABLE_NAME"));
        tableEntity.setComments(table.get("TABLE_COMMENT"));
        // 表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));
        // 判断是否包含 Date 或 BigDecimal 类型
        boolean hasDate = false;
        boolean hasBigDecimal = false;
        // 列信息
        List<ColumnEntity> columsList = new ArrayList<ColumnEntity>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("COLUMN_NAME"));
            columnEntity.setDataType(column.get("DATA_TYPE"));
            columnEntity.setComments(column.get("COLUMN_COMMENT"));
            columnEntity.setExtra(column.get("EXTRA"));
            // 列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
            // 列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "String");
            columnEntity.setAttrType(attrType);
            // 判断是否包含 Date 或 BigDecimal 类型
            if ("Date".equals(attrType)) {
                hasDate = true;
            } else if ("BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            // 是否主键
            if ("PRI".equalsIgnoreCase(column.get("column_key")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        // 没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        // 配置FreeMarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        cfg.setClassForTemplateLoading(GenMain.class, "/");
        cfg.setDefaultEncoding("UTF-8");

        // 封装模板数据
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("tableName", tableEntity.getTableName());
        data.put("comments", tableEntity.getComments());
        data.put("pk", tableEntity.getPk());
        data.put("className", tableEntity.getClassName());
        data.put("classname", tableEntity.getClassname());
        data.put("pathName", tableEntity.getClassname().toLowerCase());
        data.put("columns", tableEntity.getColumns());
        data.put("package", config.getString("package"));
        data.put("author", config.getString("author"));
        data.put("email", config.getString("email"));
        data.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        data.put("hasDate", hasDate);
        data.put("hasBigDecimal", hasBigDecimal);
        // 获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = cfg.getTemplate(template);
            tpl.process(data, sw);
            try {
                // 生成文件路径
                String filePath = getFileName(template, tableEntity.getClassName(), config.getString("package"));
                File file = new File(filePath);
                // 确保目录存在
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                // 写入文件
                FileOutputStream outputStream = new FileOutputStream(file, false);
                outputStream.write(sw.toString().getBytes(Charsets.toCharset("UTF-8")));
                System.out.println("文件生成成功：" + filePath);
                sw.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static PropertiesConfiguration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName) {
        String packagePath = "src" + File.separator + "test" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Entity.java.ftl")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Dao.java.ftl")) {
            return packagePath + "dao" + File.separator + className + "DAO.java";
        }

        if (template.contains("Service.java.ftl")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("Dao.xml.ftl")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.xml";
        }
        return null;
    }
}