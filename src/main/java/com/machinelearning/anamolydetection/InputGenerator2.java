package com.machinelearning.anamolydetection;

import java.util.Random;

public class InputGenerator2 {

	public static void main(String[] args) {
		//double p = 3.14159;
		Random r = new Random();
		for (int i = 0; i < 100; ++i)
			System.out.println(r.nextDouble()*10);
		for (int i = 0; i < 100; ++i)
			System.out.println(10 + r.nextDouble()*10);
		for (int i = 0; i < 100; ++i)
			System.out.println(20 + r.nextDouble()*10);
	}
}
