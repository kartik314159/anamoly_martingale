package com.machinelearning.anamolydetection;

import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import jsat.classifiers.CategoricalData;
import jsat.classifiers.ClassificationDataSet;
import jsat.classifiers.DataPoint;
import jsat.distributions.multivariate.NormalM;
import jsat.graphing.CategoryPlot;
import jsat.linear.DenseMatrix;
import jsat.linear.DenseVector;
import jsat.linear.Matrix;
import jsat.linear.Vec;

public class InputGenerator {

	public static void main(String[] args) {
        //We create a new data set. This data set will have 2 dimensions so we can visualize it, and 4 target class values
        ClassificationDataSet dataSet = new ClassificationDataSet(2, new CategoricalData[0], new CategoricalData(4));

        //We can generate data from a multivarete normal distribution. The 'M' at the end stands for Multivariate 
        NormalM normal;

        //The normal is specifed by a mean and covariance matrix. The covariance matrix must be symmetric. 
        //We use a simple covariance matrix for each data point for simplicity
        Matrix covariance = new DenseMatrix(new double[][]
        {
            {1.0, 0.0}, //Try altering these values to see the change!
            {0.0, 1.0} //Just make sure its still symmetric! 
        });

        //And we create 4 different means
        Vec mean0 = DenseVector.toDenseVec(0.0, 0.0);
        Vec mean1 = DenseVector.toDenseVec(0.0, 4.0);
        Vec mean2 = DenseVector.toDenseVec(4.0, 0.0);
        Vec mean3 = DenseVector.toDenseVec(4.0, 4.0);

        Vec[] means = new Vec[] {mean0, mean1, mean2, mean3};

        //We now generate out data
        for(int i = 0; i < means.length; i++)
        {
        	//System.out.println("DataSet -- " + i);
            normal = new NormalM(means[i], covariance);
            int count = 0;
            for(Vec sample : normal.sample(300, new Random())) {
            	++count;
                dataSet.addDataPoint(sample, new int[0], i);
                //System.out.println(count + "-" + sample.toString());
            }
        }
        List<Vec> vList = dataSet.getDataVectors();
        int count = 0;
        for (Vec v : vList) {
        	//System.out.println(v.toString());
        	count++;
        	System.out.println(v.get(0) + "," + v.get(1));
        }
        
        /*
        CategoryPlot plot = new CategoryPlot(dataSet);
        
        JFrame jFrame = new JFrame("2D Visualization");
        jFrame.add(plot);
        jFrame.setSize(400, 400);
        jFrame.setVisible(true); 
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */
	}
}
