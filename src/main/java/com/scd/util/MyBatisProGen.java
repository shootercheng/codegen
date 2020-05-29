package com.scd.util;

import com.scd.api.ShellRunner;
import com.scd.reader.MyBufferFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

/**
 * @author James
 */
public class MyBatisProGen {
    private static final Logger LOG = LoggerFactory.getLogger(MyBatisGen.class);

    // 驱动路径，放在resources
    private static final String driverPath = "jar/mysql-connector-java-5.1.47.jar";

    private static final String LINE_SEP = System.lineSeparator();

    /**
     * 配置文件输出路径
     * 默认使用temp
     */
    private static final String configPath = "temp";

    private static final String TABLES_TEMPLATE = "@@@###TABLES###@@@";

    private static final String TABLE_NAME_TEMPLATE = "@@@###TABLE###@@@";

    private static final String MODEL_NAME_TEMPLATE = "@@@###MODEL###@@@";

    private static final String TABLE_TEMPLATE_STR = "<table tableName=\"@@@###TABLE###@@@\" " +
            "domainObjectName=\"@@@###MODEL###@@@\" enableCountByExample=\"false\" " +
            "enableUpdateByExample=\"false\" enableDeleteByExample=\"false\" " +
            "enableSelectByExample=\"false\" selectByExampleQueryId=\"false\"></table>";

    public static void main(String[] args) throws Exception {
        String properPath = "templates/gen.properties";
        Properties properties = FileUtil.getResourceAsProperties(properPath);
        buildProperties(properties);
        String driverPath = properties.getProperty("driverPath");
        if(!copyJar(driverPath)) {
            LOG.error("copy jar file error");
            return;
        }
        String tempaltePath = "templates/generatorProConfigTemplate.xml";
        String filepath = createConfigFile(tempaltePath, properties);
        String[] command = {ShellRunner.CONFIG_FILE, filepath, ShellRunner.OVERWRITE};
        ShellRunner.genCode(command);
    }

    /**
     * 驱动路径，没有就默认取 resources/jar
     * @param properties
     */
    private static void buildProperties(Properties properties) {
        String jarPath = properties.getProperty("driverPath");
        if (StrParseUtil.isEmpty(jarPath)) {
            jarPath = driverPath;
            properties.put("driverPath", jarPath);
        }
        String path = properties.getProperty("configPath");
        if (StrParseUtil.isEmpty(path)) {
            path = configPath;
            properties.put("configPath", path);
        }
    }

    private static String createConfigFile(String template, Properties properties) throws Exception {
        Reader reader = FileUtil.getReader(template);
        MyBufferFileReader fileReader = new MyBufferFileReader(reader, properties);
        StringBuilder stringBuilder = new StringBuilder();
        String readLine;
        while((readLine = fileReader.readLine()) != null) {
            if (TABLES_TEMPLATE.equals(readLine.trim())) {
                // 生成xml table 标签
                String result = createTableTag(readLine, properties);
                stringBuilder.append(result).append(LINE_SEP);
            } else {
                stringBuilder.append(readLine).append(LINE_SEP);
            }
        }
        // 替换 Table 标签
        String ouputConfig = properties.getProperty("configPath");
        FileUtil.makedir(ouputConfig);
        String filepath = ouputConfig + File.separator + "mbgeneratorConfig.xml";
        FileUtil.writeStrtoFile(filepath, stringBuilder.toString(), false);
        return filepath;
    }

    private static String createTableTag(String readLine, Properties properties) {
        StringBuilder tableBuilder = new StringBuilder("");
        List<String> tables = StrParseUtil.parseStrBySeparator(properties.getProperty("tables"), "|");
        List<String> models = StrParseUtil.parseStrBySeparator(properties.getProperty("models"), "|");
        for (int i = 0; i < tables.size(); i++){
            String tablestr = TABLE_TEMPLATE_STR;
            tablestr = tablestr.replace(TABLE_NAME_TEMPLATE, tables.get(i));
            tablestr = tablestr.replace(MODEL_NAME_TEMPLATE, models.get(i));
            tableBuilder.append(tablestr).append("\n\t\t");
        }
        readLine = readLine.replace(TABLES_TEMPLATE, tableBuilder);
        return readLine;
    }

    private static boolean copyJar(String jarPath) {
        File file = new File(jarPath);
        if (!file.exists()) {
            InputStream inputStream = FileUtil.getResourceInputStream(MyBatisGen.class.getClassLoader(), jarPath);
            FileUtil.writeInputStreamToLocal(inputStream, jarPath);
        }
        return file.isFile();
    }
}
