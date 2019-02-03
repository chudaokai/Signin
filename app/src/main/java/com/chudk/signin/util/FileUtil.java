package com.chudk.signin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by chudaokai on 2019/2/1.
 */
public class FileUtil {
    /**
     * 创建新文件夹
     * @param path
     * @return
     */
    public static boolean createNewDirectory(String path){
        boolean f = true;
        File fi = new File(path);
        try {
            if (!fi.exists())
                fi.mkdirs();
        }catch (Exception ex){
            f = false;
        }
        return f;
    }

    /**
     * 创建新文件
     * @param path
     * @param fileName
     * @return
     */
    public static boolean createNewFile(String path,String fileName){
        boolean f = createNewDirectory(path);
        if(f){
            try{
                File fi = new File(path+"/"+fileName);
                if(!fi.exists())
                    fi.createNewFile();
            }
            catch (Exception ex){
                f=false;
            }
        }
        return f;
    }
    public static boolean createNewFile(String fileName){
        boolean f  =true;
        File fi = new File(fileName);
        String path = fi.getParent();
        String name = fi.getName();
        f = createNewFile(path,name);
        return f;
    }

    /**
     * 写入文件
     * @param fileName
     * @param context
     * @return
     */
    public static boolean writeToFile(String fileName,String context){
        boolean f = true;
        f = createNewFile(fileName);
        if(f) {
            FileWriter writer = null;
            try{
                writer = new FileWriter(fileName);
                writer.write(context);
                writer.flush();
            }
            catch (Exception ex){
                f=false;
            }finally{
                if(writer!=null)
                    try {
                        writer.close();
                    }catch(Exception ex){

                    }
            }
        }
        return f;
    }

    /**
     * 向文件追加内容
     * @param fileName
     * @param context
     * @return
     */
    public static boolean appendTofile(String fileName,String context){
        boolean f = true;
        f = createNewFile(fileName);
        if(f) {
            FileWriter writer = null;
            try{
                writer = new FileWriter(fileName);
                writer.append(context);
                writer.flush();
            }
            catch (Exception ex){
                f=false;
            }finally{
                if(writer!=null)
                    try {
                        writer.close();
                    }catch(Exception ex){
                    }
            }
        }
        return f;
    }
    /**
     * 读取文件内容
     * @param fileName
     * @return
     */
    public static String readFromFile(String fileName){
        File fi = new File(fileName);
        if(!fi.exists())
            return null;
        StringBuilder result = new StringBuilder();
        FileReader reader = null;
        BufferedReader bfReader = null;
        try{
            reader = new FileReader(fileName);
            bfReader = new BufferedReader(reader);
            String line = null;
            while((line = bfReader.readLine())!=null){
                result.append(line);
            }
        }
        catch (Exception ex){
            result=new StringBuilder();
        }finally {
            if(reader!=null){
                try{
                    bfReader.close();
                }catch (Exception ex){
                }
                try{
                    reader.close();
                }catch (Exception ex){
                }
            }
        }
        return result.toString();
    }


}
