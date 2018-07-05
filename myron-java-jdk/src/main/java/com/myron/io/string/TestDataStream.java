package com.myron.io.string;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/** 
 * DataInputStream类和DataOutputStream类以同积极平台无关的方式读写Java基本类型值和字符串(读写对象使用ObjectInputStream/ObjectOutStream)
 * BufferedInputStream类和BufferedOutputStream类通过设置缓存区减少读写次数提高输入和输出速度,一般用于包装FileInputStream/FileOutputStream
 * @author Administrator
 *
 */
public class TestDataStream {

	public static void main(String[] args) throws IOException {
		//create an output stream for file
		//数据流用于对已经存在的输入/输出流进行包装,以便在原始流中过滤数据,如：读取整数值、双精度值和字符串。(基本字节输出流提供的读取方法read只能用来读取字节)
		DataOutputStream output = new DataOutputStream(new FileOutputStream("temp.dat"));
		
		//没有指定缓存区大小,默认大小是512个字节
		//DataOutputStream output1 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("temp.dat")));
		
		//ObjectOutputStream 实现ObjectOutput接口(是DataOutput的子接口)
		//ObjectOutputStream output2 = new ObjectOutputStream(new FileOutputStream("temp.dat"));
		//提高性能添加缓冲区
		//ObjectOutputStream output2 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("temp.dat")));
		
		//write student test scores to the file
		output.writeUTF("John");
		output.writeDouble(85.5);
		output.writeUTF("Jim");
		output.writeDouble(185.5);
		output.writeUTF("George");
		output.writeDouble(105.25);
		
		//close output stream
		output.close();
		
		//crate an input stream for file
		DataInputStream input = new DataInputStream(new FileInputStream("temp.dat"));
		
		//TODO !!! 按存储的顺序和格式读取文件中的数据
		//read students test socre  
		System.out.println(input.readUTF() + " " + input.readDouble());
		System.out.println(input.readUTF() + " " + input.readDouble());
		System.out.println(input.readUTF() + " " + input.readDouble());
		
		//InputStream的末尾之后还继续读数据,就会发生EOFException异常,可以用来检查是否已经到达文件末尾
		try {
			System.out.println(input.readUTF());
		} catch (EOFException ex) {
			System.out.println("all data read");
		}
		input.close();
	}

}
