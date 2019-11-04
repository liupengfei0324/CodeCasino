package com.thundersoft.codecasino;

import java.util.Random;

public class RandomUtil {
	public int getRandomOf34() {
		Random rd=new Random();
		int j=(int)(rd.nextDouble()*10);
		if (j<6) {
			return 3;
		}else {
			return 4;
		}
	}
	
	public int getRandomOf14() {
		Random rd=new Random();
		int j=(int)(rd.nextDouble()*10);
		if (j<6) {
			return 1;
		}else {
			return 4;
		}
	}
	
	public int getRandomOf123() {
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
}
