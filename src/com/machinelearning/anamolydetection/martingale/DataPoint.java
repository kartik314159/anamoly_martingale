package com.machinelearning.anamolydetection.martingale;

public abstract class DataPoint {
	public abstract int numberOfDimensions();
	
	public abstract void createDataPoint(String[] data);
	
	public abstract double norm();
	public abstract double distanceTo(DataPoint z);
}
