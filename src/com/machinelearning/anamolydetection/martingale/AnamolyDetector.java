package com.machinelearning.anamolydetection.martingale;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AnamolyDetector {
	private int numberOfFeatures;
	private int numberOfDimensions;
	
	private double[] martingale;
	private DataSet[] dataSet;
	
	private MartingaleParams params;
	private int dataSetIndex;
	
	public AnamolyDetector() {
		numberOfFeatures = 1;
		numberOfDimensions = 1;
		_initializeDataStructures();
	}
	
	public AnamolyDetector(int nFeatures, int nDimension) {
		numberOfFeatures = nFeatures;
		numberOfDimensions = nDimension;
		_initializeDataStructures();
	}
	
	private void _initializeDataStructures() {
		martingale = new double[numberOfFeatures];
		for (int i = 0; i < numberOfFeatures; ++i)
			martingale[i] = 0.0;
		dataSet = new DataSet[numberOfFeatures];
		for (int i = 0; i < numberOfFeatures; ++i) {
			dataSet[i] = new DoubleDataSetVectorImpl(numberOfDimensions);
		}
		params = new MartingaleParams("martingale.properties");
		dataSetIndex = 0;
	}
	

	protected boolean applyMultipleMartingaleTest(DataPoint[] x) {
		boolean anamolyFound = false;
		for (int f = 0; f < numberOfFeatures && !anamolyFound; ++f) {
			martingale[f] = dataSet[f].martingaleValue(x[f]);
			//System.out.print(martingale[f] + ",");
			
			if (martingale[f] > params.getMartingaleThreshold()) {
				// TODO: Have a better reporting in place here
				System.out.print("ANAMOLY DETECTED @:");
				System.out.println("Data Set Index: " + dataSetIndex);
				System.out.println("Martingale[f]: " + martingale[f]);
				System.out.println("Resetting Data set for next iteration");

				anamolyFound = true;
			}
		}
		//System.out.println();
		if (anamolyFound) {
			// Reset data
			dataSetIndex = 0;
			for (int f = 0; f < numberOfFeatures; ++f)
				dataSet[f].resetDataSet();
		}
		return anamolyFound;
	}
	
	// This method assumes all the multiple features are already prepared
	// and passed into the input stream.
	// TODO: May be we need to separate out this logic and handle it
	// ASSUMPTION: '|' separates every feature data set. ',' separates every
	// element in a given data set vector
	public void readDataAndDetectAnamoly(InputStream in) {
		if (in != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			try {
				// First line
/*
				if ((line = reader.readLine()) != null) {
					String[] fields = line.split("\\|");
					//System.out.println("Line: " + line);
					//System.out.println("Splitted line: " + line.split("\\|")[0]);
					
					// For every feature add the data point
					DataPoint[] dPoints = new DataPoint[numberOfFeatures];
					for (int i = 0; i < numberOfFeatures; ++i) {
						dPoints[i] = new DoubleVectorDataPoint(numberOfDimensions);
						dPoints[i].createDataPoint(fields[i].split(","));
						//System.out.println("fields: " + fields[i]);
						dataSet[i].addDataPoint(dPoints[i]);
					}
				}
*/				
				while ((line = reader.readLine()) != null) {
					String[] fields = line.split("\\|");
					//System.out.println("Line: " + line);
					//System.out.println("Splitted line: " + line.split("\\|")[0]);
					
					// For every feature add the data point
					DataPoint[] dPoints = new DataPoint[numberOfFeatures];
					for (int i = 0; i < numberOfFeatures; ++i) {
						dPoints[i] = new DoubleVectorDataPoint(numberOfDimensions);
						dPoints[i].createDataPoint(fields[i].split(","));
						//System.out.println("fields: " + fields[i]);
					}

					applyMultipleMartingaleTest(dPoints);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("Couldn't load file");
	}
	
	public static void main(String[] args) {
		System.out.println("Anamoly detection by Multiple Martingale Test");
		AnamolyDetector detector = new AnamolyDetector(1, 1);
		InputStream in = null;
		try {
			in = new FileInputStream("/Users/karthik/inputDataSet2.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detector.readDataAndDetectAnamoly(in);
		System.out.println("Anamoly Detection by MM test - COMPLETE");
	}
}
