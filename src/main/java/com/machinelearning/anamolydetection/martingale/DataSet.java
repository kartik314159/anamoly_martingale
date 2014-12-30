package com.machinelearning.anamolydetection.martingale;

import java.util.List;

// Models the collection Z
// Z belongs to Real Number space of N-dimension
// Z = { z1, z2, z3, ..., zn }, where zi is a DataPoint
// Each data point z1 = [a1 a2 a3 ... aN]
public abstract class DataSet {
	public abstract int numberOfDataPoints();
	public abstract int numberOfDimensions();
	
	// Calculate strangeness measure and pValue per feature
	public abstract double strangenessMeasure(DataPoint d);
	public abstract double pValue(DataPoint d);
	public abstract double martingaleValue(DataPoint d);
	
	// Data Set manipulation
	public abstract boolean addDataPoint(DataPoint d);
	public abstract boolean addDataPoints(List<DataPoint> d);
	
	public abstract DataPoint getMaxDataPoint() ;
	public abstract DataPoint getMinDataPoint();

	public abstract void resetDataSet();
}
