package com.machinelearning.anamolydetection.martingale;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoubleDataSetVectorImpl extends DataSet {
	private List<DoubleVectorDataPoint> dataPoints;
	private List<Double> martingaleValues;
		
	private int currentMaxIndex;
	private double currentMaxDataPointValue;
	
	private int referenceClusterSize;
	private double clusterMean;
	
	private int numberOfDataPoints;
	private int numberOfDimensions;
	
	// TODO: Make it a singleton?
	private MartingaleParams params;
	private double martingaleIndex;

	public DoubleDataSetVectorImpl(int nDimension) {
		numberOfDataPoints = 0;
		numberOfDimensions = nDimension;
		
		currentMaxIndex = -1;
		currentMaxDataPointValue = 0.0;
		
		referenceClusterSize = 20;
		clusterMean = 0.0;
		
		dataPoints = new ArrayList<DoubleVectorDataPoint>();
		martingaleValues = new ArrayList<Double>();
		martingaleValues.add(new Double(1.0));
		
		params = new MartingaleParams("martingale.properties");
		martingaleIndex = params.getMartingaleIndex();
	}

	@Override
	public boolean addDataPoint(DataPoint d) {
		if (d instanceof DoubleVectorDataPoint) {
			double pVal = pValue(d);
			double martingaleValue = martingaleIndex * Math.pow(pVal, martingaleIndex - 1.0);
			martingaleValue *= martingaleValues.get(martingaleValues.size() - 1).doubleValue();		

			martingaleValues.add(new Double(martingaleValue));
			System.out.println(numberOfDataPoints + ", " + martingaleValue + ", " + pVal + ", " + currentMaxDataPointValue);

			dataPoints.add((DoubleVectorDataPoint)d);
			_checkAndChangeCurrentMax((DoubleVectorDataPoint)d, dataPoints.size() - 1);
			++numberOfDataPoints;
			return true;
		}
		else return false;
	}

	@Override
	public void resetDataSet() {
		numberOfDataPoints = 0;		
		dataPoints.clear();
		martingaleValues.clear();
		martingaleValues.add(new Double(1.0));
	}

	@Override
	public double strangenessMeasure(DataPoint zn) {
		double currentDataPointNorm = zn.norm();
		double distance = 0.0;
		if (currentDataPointNorm < currentMaxDataPointValue) {
			distance = zn.distanceTo(dataPoints.get(currentMaxIndex));
		}
		return distance;
	}

	@Override
	public double pValue(DataPoint zn) {
		double newDataStrangenessMeasure = strangenessMeasure(zn);
		int countOfDeviatingDataPoints = 0;
		int countOfSameStrangeness = 0;
		double theta_n = new Random().nextDouble();
		for (int i = 0; i < numberOfDataPoints; ++i) {
			double sm_i = strangenessMeasure(dataPoints.get(i));
			if (sm_i > newDataStrangenessMeasure)
				++countOfDeviatingDataPoints;
			else if (sm_i == newDataStrangenessMeasure)
				++countOfSameStrangeness;
		}
		//System.out.println("#deviating: " + countOfDeviatingDataPoints + "# same: " + countOfSameStrangeness + " theta: " + theta_n);
		return ((double)countOfDeviatingDataPoints + (theta_n * countOfSameStrangeness))/(numberOfDataPoints);
	}

	@Override
	public double martingaleValue(DataPoint zn) {
		// TODO: We can surely optimize these calls for the better
		dataPoints.add((DoubleVectorDataPoint)zn);
		_checkAndChangeCurrentMax((DoubleVectorDataPoint)zn, dataPoints.size() - 1);
		++numberOfDataPoints;
		
		double pVal = pValue(zn);
		double martingaleValue = martingaleIndex * Math.pow(pVal, martingaleIndex - 1.0);
		martingaleValue *= martingaleValues.get(martingaleValues.size() - 1).doubleValue();
		//System.out.println("(in martingaleValue func) " + numberOfDataPoints + " - pVal = " + pVal 
		//		+ " martingaleValue = " + martingaleValue + " martingale value (last): "
		//		+ martingaleValues.get(martingaleValues.size() - 1).doubleValue());
		System.out.println(numberOfDataPoints + " - pVal: " + pVal + " p^(e-1): " + Math.pow(pVal, martingaleIndex - 1.0)
				+ " M(n-1): " + martingaleValues.get(martingaleValues.size() - 1).doubleValue()
				+ " M(n): " + martingaleValue);
		martingaleValues.add(new Double(martingaleValue));
		//System.out.println(numberOfDataPoints + ", " + martingaleValue + ", " + pVal + ", " + currentMaxDataPointValue);
		//System.out.println(pVal +",");
		//System.out.println(martingaleValue +",");
				
		return martingaleValue;
	}

	@Override
	public DataPoint getMaxDataPoint() {
		return dataPoints.get(currentMaxIndex);
	}

	@Override
	public DataPoint getMinDataPoint() {
		// Not implemented for now
		return null;
	}

	@Override
	public int numberOfDataPoints() {
		return numberOfDataPoints;
	}

	@Override
	public int numberOfDimensions() {
		return numberOfDimensions;
	}
	
	@Override
	public boolean addDataPoints(List<DataPoint> d) {
		// TODO: Implement adding a list of data points
		// Not implemented for now
		return false;
	}
	
	private void _checkAndChangeCurrentMax(DoubleVectorDataPoint d, int newIndex) {
		double newDataPointNorm = d.norm();
		//System.out.println("Norm: " + newDataPointNorm);
		if (newDataPointNorm > currentMaxDataPointValue) {
			currentMaxIndex = newIndex;
			currentMaxDataPointValue = newDataPointNorm;
			//System.out.println("New max:" + newDataPointNorm + ", New Index: " + newIndex);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
