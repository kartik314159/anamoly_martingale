package com.machinelearning.anamolydetection.martingale;

public class DoubleVectorDataPoint extends DataPoint {
	private int nDimension;
	
	@Override
	public int numberOfDimensions() {
		return nDimension;
	}

	private double[] doubleVector;
	
	public DoubleVectorDataPoint(int N) {
		nDimension = N;
		doubleVector = new double[N];
	}

	@Override
	public void createDataPoint(String[] data) {
		if (data.length == nDimension) {
			for (int i = 0; i < data.length; ++i) {
				doubleVector[i] = Double.parseDouble(data[i]);
			}
		}
	}

	@Override
	public double norm() {
		double sumOfSquares = 0;
		for (int i = 0; i < nDimension; ++i) {
			sumOfSquares += doubleVector[i] * doubleVector[i];
			//System.out.println("Sum = " + sumOfSquares + ", " + doubleVector[i]);
		}
		return Math.sqrt(sumOfSquares);
	}

	@Override
	public double distanceTo(DataPoint z) {
		double distance = 0.0; 
		if (z instanceof DoubleVectorDataPoint) {				
			double sumOfSquares = 0.0;
			for (int i = 0; i < nDimension; ++i) {
				double diff = this.doubleVector[i] - ((DoubleVectorDataPoint)z).doubleVector[i];
				sumOfSquares += (diff * diff);
			}
			distance = Math.sqrt(sumOfSquares);
		}		
		return distance;
	}
}
