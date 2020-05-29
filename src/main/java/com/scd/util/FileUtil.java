package com.scd.util;

import com.scd.exception.ResourceLoadException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author chengdu
 */
public class FileUtil {
    public static final int BUFFSIZE = 1024;

    private static final Logger log = Logger.getLogger("lavasoft");

    /**
     * 一次读取整个文件
     * @param filepath
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFileAll(String filepath, String encoding) throws IOException{
        File file = new File(filepath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream inputStream = null;
        String readResult = "";
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(filecontent);
            readResult = new String(filecontent, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
        return readResult;
    }

    /**
     * 文件流读取文件内容
     * @param inputStream
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFileAll(InputStream inputStream, String encoding) throws IOException{
        byte[] filecontent = new byte[inputStream.available()];
        String readResult = "";
        try {
            inputStream.read(filecontent);
            readResult = new String(filecontent, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readResult;
    }

    public static void writeBytearrtoFile(String filepath, byte[] byteArray, boolean append) throws Exception{
        FileOutputStream fos = new FileOutputStream(filepath,append);
        fos.write(byteArray);
        fos.close();
    }

    public static void writeStrtoFile(String filepath, String content, boolean append) throws Exception {
        FileWriter writer = new FileWriter(filepath, append);
        writer.write(content);
        writer.flush();
        writer.close();
    }
    
    /**
     * 写入文件时指定编码
     * @param filepath
     * @param content
     * @param append
     * @param encode
     * @throws Exception
     */
    public static void writeBuffToFile(String filepath, String content, boolean append, String encode) throws Exception{
        BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(
                new FileOutputStream(filepath, append), encode));
        bufferedWriter.write(content);
        bufferedWriter.close();
        bufferedWriter.newLine();
    }    

    /**
     * byte[] 数组写入文件
     * @param path
     * @param content
     * @param append
     * @throws IOException
     */
    public static void writeByteArraytoFile(String path, byte[] content, boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(path, append);
        fos.write(content);
        fos.close();
    }

    public static List getListPathByfile(String fileName) {
        File file = new File(fileName);
        List<String> list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                if(new File(tempString).exists() && new File(tempString).isFile()) {
                    list.add(tempString);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
    }

    /**
     * 获取所有文件
     * @param filepath
     * @param pathlist
     */
    public static void getFilePath(String filepath, List<String> pathlist){
        File file = new File(filepath);
        if(!file.exists()){
            throw new RuntimeException("file not exist"+ filepath);
        }
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File efile : files){
                if(efile.isDirectory()){
                    getFilePath(efile.getAbsolutePath(), pathlist);
                }else{
                    pathlist.add(efile.getAbsolutePath());
                }
            }
        }else{
            //传入文件时添加一条
            pathlist.add(filepath);
        }
    }

    public static void createNewDirFile(String filepath) throws IOException{
        filepath = filepath.replace("\\","/");
        int endIndex = filepath.lastIndexOf("/");
        String dirpath = filepath.substring(0,endIndex);
        //创建文件目录
        File dirFile = new File(dirpath);
        if(!dirFile.exists()) {
            if (dirFile.mkdirs()) {
                log.info("create file dir successfully----" + dirpath);
            }
        }else{
            log.info("dir path exists----"+dirpath);
        }
        //创建文件
        File file = new File(filepath);
        if(!file.exists()) {
            if (file.createNewFile()) {
                log.info("create file successfully----" + filepath);
            }
        }else {
            log.info("file path exists----"+filepath);
        }
    }
    
        /**
     * 删除文件
     * @param filepath
     */
    public static void deleteFile(String filepath){
        File file = new File(filepath);
        if(file.exists()){
            if(file.isDirectory()){
                System.out.println("enter dir " + file.getPath());
                File[] files = file.listFiles();
                for(File f : files){
                    deleteFile(f.getAbsolutePath());
                }
            }else if(file.isFile()){
                System.out.println("delete file :"+ file.getAbsolutePath());
                file.delete();
            }
        }
    }
    
    /**
     * 删除文件以及文件夹
     * @param filepath
     */
    public static void deleteDirAndFile(String filepath){
        File file = new File(filepath);
        if(file.exists()){
            if(file.isDirectory()){
                System.out.println("enter dir " + file.getPath());
                File[] files = file.listFiles();
                for(File f : files){
                    deleteDirAndFile(f.getAbsolutePath());
                }
                if(file.delete()){
                    System.out.println("delete dir " + file.getAbsolutePath());
                }
            }else if(file.isFile()){
                System.out.println("delete file :"+ file.getAbsolutePath());
                file.delete();
            }
        }
    }

    public static void makedir(String dir){
        File file = new File(dir);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static InputStream getResourceInputStream(ClassLoader classLoader, String resource) {
        InputStream returnValue = classLoader.getResourceAsStream(resource);
        if (returnValue == null) {
            returnValue = classLoader.getResourceAsStream("/" + resource);
            // 外挂文件加载
            if (returnValue == null) {
                try {
                    returnValue = new FileInputStream(resource);
                } catch (FileNotFoundException e) {
                }
            }
        }
        if (returnValue == null) {
            throw new ResourceLoadException("load resource " + resource + "fail");
        }
        return returnValue;
    }

    public static Reader getReader(String reource) {
        InputStream inputStream = getResourceInputStream(Thread.currentThread().getContextClassLoader(), reource);
        return new InputStreamReader(inputStream);
    }

    public static Properties getResourceAsProperties(ClassLoader classLoader, String resource) {
        Properties properties = new Properties();
        try (InputStream inputStream = getResourceInputStream(classLoader, resource)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ResourceLoadException("load properties error " + resource, e);
        }
        return properties;
    }

    public static Properties getResourceAsProperties(String resource) {
        return getResourceAsProperties(Thread.currentThread().getContextClassLoader(), resource);
    }

    /**
     * 将文件输入流写入指定路径
     * @param inputStream
     * @param targetpath
     * @throws IOException
     */
    public static void writeInputStreamToLocal(InputStream inputStream, String targetpath) {
        FileOutputStream fileOutputStream = null;
        // 创建文件目录
        String filedir = getFileDir(targetpath);
        mkdirs(filedir);
        try {
            fileOutputStream = new FileOutputStream(targetpath);
            int index;
            byte[] filebytes = new byte[1024];
            while ((index = inputStream.read(filebytes)) != -1){
                fileOutputStream.write(filebytes, 0, index);
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                }catch (IOException e){
                    log.info("close stream error");
                }
            }
        }
    }

    /**
     * 根据文件路径获取文件目录
     * @param filepath
     * @return
     */
    public static String getFileDir(String filepath){
        filepath = filepath.replace("\\","/");
        int lastSeparator = filepath.lastIndexOf("/");
        if(lastSeparator != -1){
            return filepath.substring(0, lastSeparator);
        }else{
            throw new RuntimeException("can not find filepath " + filepath);
        }
    }

    /**
     * 创建文件夹
     * @param filepath
     */
    public static void mkdirs(String filepath){
        File file = new File(filepath);
        if(!file.exists()){
            if(file.mkdirs()){
                log.info("makedir success, dir path "+ filepath);
            }else{
                log.info("makedir error, dir path " + filepath);
            }
        }
    }


}
