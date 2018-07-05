package com.myron.io.string;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 计算机不区分二进制我呢间与文本文件。所有文本都是以二进制存储的。文本IO尽力在二进制IO基础上，提供字符层次编码和 解码
 * @author Administrator
 *
 */
public class StringIO {
	
	public static void main(String[] args) throws FileNotFoundException {

		PrintWriter output = new PrintWriter("temp.txt");
		output.write("helloworld");
		output.close();
		
		Scanner input = new Scanner(new File("temp.txt"));
		System.out.println(input.nextLine());
		input.close();
	}
}
