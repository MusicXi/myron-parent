package com.myron.thread.runnable.thread;

public class TraditionalThreadSynchronized {  
    public static void main(String[] args) {  
        final Outputter output = new Outputter();  
        new Thread() {  
            public void run() {  
                output.output("zhangsan");  
            }  
        }.start();        
        new Thread() {  
            public void run() {  
                output.output("lisi");  
            }  
        }.start();  
    }  
}  
class Outputter {  
    public void output(String name) {  
        // TODO 为了保证对name的输出不是一个原子操作，这里逐个输出name的每个字符  
        for(int i = 0; i < name.length(); i++) {  
            System.out.print(name.charAt(i));  
             try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  
    }  
} 