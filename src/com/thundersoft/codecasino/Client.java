package com.thundersoft.codecasino;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client extends Thread{
	PacManIndex pacManIndex = new PacManIndex();
	boolean change = true;
	boolean isSendKey = true;
	Scanner scanner = null;
	OutputStream outputStream = null;
	Socket socket = null;
	public Client(String host, int port) {
		try {
			socket = new Socket(host,port);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new sendMessage().start();
		super.run();
			try {
				// 读Sock里面的数据
				InputStream inputStream = socket.getInputStream();
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(b)) != -1) {
					String str = new String(b, 0, len);
					System.out.println(str);
					if("OK".equals(str)) {
						continue;
					}else if (str.length() == 227) {//取出返回数据的地图数据
						pacManIndex.formatString = str.substring(1, str.length()-1);//数据去头尾[]
						if (isSendKey) {//发送key值
							outputStream.write("(feb9945c4df3434aa9b8b202e7541b1e)".getBytes());
							outputStream.flush();
							isSendKey = false;
						}
						System.out.println(pacManIndex.formatString);
						switch(pacManIndex.getNumber()) {
						case 1:
							outputStream.write("[w]".getBytes());
							outputStream.flush();
							break;
	                    case 2:
	        				outputStream.write("[a]".getBytes());
	        				outputStream.flush();
							break;
							
	                    case 3:
	        				outputStream.write("[s]".getBytes());
	        				outputStream.flush();
		                    break;
	                    case 4:
	     				    outputStream.write("[d]".getBytes());
	     				   outputStream.flush();
		                    break;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
	}
	
	class sendMessage extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				scanner =new Scanner(System.in);
				outputStream = socket.getOutputStream();
				String messageString = "(feb9945c4df3434aa9b8b202e7541b1e)";
				messageString = scanner.next();
				outputStream.write(messageString.getBytes());
				outputStream.flush();
				System.out.print(messageString);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		Client clinetTest = new Client("127.0.0.1", 9527);
		clinetTest.start();
	}
}