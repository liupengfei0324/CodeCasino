package com.thundersoft.codecasino;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class Client extends Thread{
	boolean change = true;
	//下一步的动作
	private int step = 3;
	//处理后的地图返回数据
	private String formatString;
	//当前吃豆人位置
	int index = 0;
	int one = 1;
	int two = 2;
	int three = 3;
	int four = 4;
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
						formatString = str.substring(1, str.length()-1);//数据去头尾[]
						if (isSendKey) {
							outputStream.write("(feb9945c4df3434aa9b8b202e7541b1e)".getBytes());
							outputStream.flush();
							isSendKey = false;
						}
						System.out.println(formatString);
						switch(getNumber()) {
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
	
	private int getNumber() {
		int index = getIndexOfMine();
		//判断是否位于四角
		if (index == 0) {
			if (String.valueOf(formatString.charAt(1)).equals("9")) {
				return 3;
			}else if (String.valueOf(formatString.charAt(15)).equals("9")) {
				return 4;
			}else {
				return 3;
			}
		}else if (index == 14) {
			if (String.valueOf(formatString.charAt(index-1)).equals("9")) {
				return 3;
			}else if (String.valueOf(formatString.charAt(index+15)).equals("9")) {
				return 4;
			}else {
				return 2;
			}
		}else if (index == 210) {
			if (String.valueOf(formatString.charAt(index+1)).equals("9")) {
				return 1;
			}else if (String.valueOf(formatString.charAt(index-15)).equals("9")) {
				return 4;
			}else {
				return 4;
			}
		}else if (index == 224) {
			if (String.valueOf(formatString.charAt(index-1)).equals("9")) {
				return 1;
			}else if (String.valueOf(formatString.charAt(index-15)).equals("9")) {
				return 2;
			}else {
				return getRandomOf123();
			}
		}else if (index%15 == 0) {
			//判断是否位于左边
			if (String.valueOf(formatString.charAt(index+15)).equals("9")) {
				return 4;
			}else if (String.valueOf(formatString.charAt(index+1)).equals("9")) {
				return 3;
			}else {
				return 3;
			}
		}else if ((index+1)%15 == 0) {
			//判断是否位于右边
			if (String.valueOf(formatString.charAt(index+15)).equals("9")) {
				if (String.valueOf(formatString.charAt(index-1)).equals("9")) {
					return 1;
				} else {
					return 2;
				}
			}else if (String.valueOf(formatString.charAt(index-1)).equals("9")) {
				return 3;
			}else {
				return getRandomOf123();
			}
		}else if (index > 0 && index < 14) {
			//判断是否位于上边
			if (String.valueOf(formatString.charAt(index+1)).equals("9")) {
				return 3;
			}else if (String.valueOf(formatString.charAt(index+15)).equals("9")) {
				return 4;
			}else {
				return 3;
			}
		}
		else if (index > 210 && index < 224) {
			//判断是否位于下边
			if (String.valueOf(formatString.charAt(index+1)).equals("9")) {
				if (String.valueOf(formatString.charAt(index-15)).equals("9")) {
					return 4;
				} else {
					return 1;
				}
			}else if (String.valueOf(formatString.charAt(index-15)).equals("9")) {
				return 4;
			}else {
				if (String.valueOf(formatString.charAt(index+1)).equals("9")) {
					return 1;
				}
				return getRandomOf14();
			}
		}else {
			//位于非边上位置
			if (String.valueOf(formatString.charAt(index-15)).equals("9")) {
				if(String.valueOf(formatString.charAt(index+1)).equals("9")) {
					if(String.valueOf(formatString.charAt(index-1)).equals("9")) {
						return 3;
					} else {
						return 2;
					}
				} else {
					if (String.valueOf(formatString.charAt(index-1)).equals("9")) {
						return 3;
					} else {
						return 4;
					}
				}
			}else if (String.valueOf(formatString.charAt(index+15)).equals("9")) {
				return 4;
			}else if (String.valueOf(formatString.charAt(index+1)).equals("9")) {
				return 1;
			}else{
				if (String.valueOf(formatString.charAt(index+16)).equals("9")) {
					return getRandomOf14();
				}
				return getRandomOf34();
			}
		}
	}
	
	private int getIndexOfMine() {
		if (formatString.indexOf("w") != -1) {
			index = formatString.indexOf("w");
		} else if (formatString.indexOf("a") != -1) {
			index = formatString.indexOf("a");
		}else if (formatString.indexOf("s") != -1) {
			index = formatString.indexOf("s");
		}else if (formatString.indexOf("d") != -1) {
			index = formatString.indexOf("d");
		}
		return index;
	}
	
	private int getRandomOf34() {
		Random rd=new Random();
		int j=(int)(rd.nextDouble()*10);
		if (j<6) {
			return 3;
		}else {
			return 4;
		}
	}
	
	private int getRandomOf14() {
		Random rd=new Random();
		int j=(int)(rd.nextDouble()*10);
		if (j<6) {
			return 1;
		}else {
			return 4;
		}
	}
	
	private int getRandomOf123() {
		Random rd=new Random();
		int j=(int)(rd.nextDouble()*9);
		if (j<4) {
			return 1;
		}else if (j>=4 && j<8) {
			return 2;
		}else {
			return 3;
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