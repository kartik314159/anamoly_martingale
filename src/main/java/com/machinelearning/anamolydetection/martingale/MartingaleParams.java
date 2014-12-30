package com.machinelearning.anamolydetection.martingale;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MartingaleParams {
	private double martingaleIndex;
	private double martingaleThreshold;
	
	public MartingaleParams() {
		martingaleIndex = 0.92;
		martingaleThreshold = 10;
	}
	
	public MartingaleParams(String propertiesFile) {
		this();
		
		Properties p = new Properties();
		InputStream in = getClass().getClassLoader().getResourceAsStream(propertiesFile);
		
		if (in != null) {
			try {
				p.load(in);
				in.close();
				
				martingaleIndex = Double.parseDouble(p.getProperty("index"));
				martingaleThreshold = Double.parseDouble(p.getProperty("lambda"));
			} catch (IOException e) { e.printStackTrace(); }
		}
		else
			System.out.println("Properties file not found");
	}
	
	public double getMartingaleIndex() {
		return martingaleIndex;
	}

	public double getMartingaleThreshold() {
		return martingaleThreshold;
	}

	public static void main(String[] args) {
		MartingaleParams params = new MartingaleParams("martingale.properties");
		
		System.out.println(params.getMartingaleIndex());
		System.out.println(params.getMartingaleThreshold());
	}
}
