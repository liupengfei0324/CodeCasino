package com.thundersoft.codecasino;

public class PacManIndex {
	RandomUtil randomUtil;
	//处理后的地图返回数据
	public static String formatString;
	//当前吃豆人位置
		int index = 0;
	public int getNumber() {
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
				return randomUtil.getRandomOf123();
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
				return randomUtil.getRandomOf123();
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
				return randomUtil.getRandomOf14();
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
					return randomUtil.getRandomOf14();
				}
				return randomUtil.getRandomOf34();
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
}
