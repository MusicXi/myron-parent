package com.myron.thread.runnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TxtUtils {
    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
        	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");   
        	BufferedReader reader = new BufferedReader(isr);   
            //BufferedReader reader = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = reader.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            reader.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
    public static void main(String[] args){
        File file = new File("F:/Desktop/svn.txt");//F:\Desktop
        System.out.println(txt2String(file));
    }
}