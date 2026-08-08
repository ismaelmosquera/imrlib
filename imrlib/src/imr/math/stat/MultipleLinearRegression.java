/*
 * Copyright (c) 2026 Ismael Mosquera Rivera
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

/*
* MultipleLinearRegression.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.matrix.Matrix;
import imr.math.matrix.Vector;

/**
* The <code>MultipleLinearRegression</code> class performs MLR for a given data. <p>
* In such a regression, there are two or more independent variables which affects a single one. <p>
* That is, changes in the independent variables predict the behaviour of the dependent one. <p>
* Actually, we can do SLR ( Simple Linear Regression ) using this class, but you should better use <p>
* the <code>SimpleLinearRegression</code> class to do that kind of task. <p>
* We first set a MLR system from a given data set; afterwrds we are able <p>
* to ask the system to make predictions about our data. <p>
*
* This class uses assertions; in order to enable them, you can use the '-ea' modifier when execute.<p>
* <code>java -ea MyApp</code>
* <p>
* @see imr.math.stat.SimpleLinearRegression a class to do SLR.
*
* @author Ismael Mosquera Rivera.
*
*/
public final class MultipleLinearRegression
{

/**
* Constructor. <p>
* Makes a new instance for a <code>MultipleLinearRegression</code> object. <p>
* @param y
* A vector with observations made from a set of independent variables.
* <p>
* @param x
* A matrix having independent variables.
* <p>
* The size of the vector must be equal than the number of columns of the matrix. <p>
* The matrix can have any number of rows, we get as Beta coefficients as rows has the matrix.
*
*/
public MultipleLinearRegression(Vector y, Matrix x)
{
this.set(y, x);
}

/**
* Constructor. <p>
* Makes a new instance for a <code>MultipleLinearRegression</code> object. <p>
* @param vectorYFilename
* A string with the filename from to load the 'y' vector.
* <p>
* @param matrixXFilename
* A string with the filename from to load the 'x' matrix.
*
*/
public MultipleLinearRegression(String vectorYFilename, String matrixXFilename)
{
this.set(new Vector(vectorYFilename), new Matrix(matrixXFilename));
}

/**
* Sets the required data and builds the given MLR system. <p>
* @param y
* vector 'y' of observations.
* <p>
* @param x
* matrix 'x' of independent variables.
*
*/
public void set(Vector y, Matrix x)
{
	assert(y.size() == x.columns()): "MLR -> set( ... ): vector size must be equal to matrix columns.";
	// get beta values
	_bn = computeBetaValues(y, x);
	// compute mean for each required data
	int n = x.rows();
	double[] x_mean = new double[n];
	for(int i = 0; i < n; i++)
	{
	x_mean[i] = mean(x.getRowVector(i));
	}
	// compute beta0
	double productSum = 0.0;
	for(int i = 0; i < n; i++)
	{
		productSum += _bn.get(i)*x_mean[i];
	}
	_b0 = mean(y) - productSum;
	if(Math.abs(_b0) < THRESHOLD) _b0 = 0.0;
_hasData = true;
}

/**
* Makes a single prediction according to the data in the vector passed as parameter. <p>
* Take in account that the size of the vector must be equal than the available beta coefficients in the system. <p>
* You can use the <code>size()</code> method in order to get such a number. <p>
* @param x
* a vector having the predictors.
* <p>
* @return Predicted value.
*
*/
public double predict(Vector x)
{
	assert(_hasData): "MLR -> predict( ... ): no data set yet.";
assert(x.size() == _bn.size()): "MLR -> predict( ... ): param vector must be same size as the number of available beta coefficients.";
return computePrediction(x);
}

/**
* Predicts values for the dependent variable and stores them in the returned <code>imr.math.matrix.Vector</code> object. <p>
* Take in account that the matrix passed as parameter must have equal number of columns than beta coefficients we have. <p>
* You can use the <code>size()</code> method in order to get such a number. <p>
* The matrix can have any number of rows. <p>
* @param x
* matrix with the predictors.
* <p>
* @return vector having the predicted values.
*
*/
public Vector predict(Matrix x)
{
assert(_hasData): "MLR -> predict( ... ): No data set yet.";
assert(_bn.size() == x.columns()): "MLR -> predict( ... ): matrix must have the same number of columns than beta coefficients are.";
Vector v = new Vector(x.rows());
int n = v.size();
for(int i = 0; i < n; i++)
{
v.set(i, computePrediction(x.getRowVector(i)));
}
return v;
}

/**
* Gets the number of Beta coefficients we have in this MLR system. <p>
* @return number of available beta coefficients.
*
*/
public int size()
{
	if(!_hasData) return 0;
return _bn.size();
}


/*
* This private method computes the Beta coefficients and returns them in a vector object.
*/
private Vector computeBetaValues(Vector y, Matrix x)
{
if(y.size() != x.columns()) return null;
Matrix cx = Matrix.transpose(x);
Matrix cy = y.toColumnMatrix();
return x.mul(cx).inverse().mul(x).mul(cy).transpose().getRowVector(0);
}

/*
* Private helper method to compute the arithmetic mean for a given vector.
*/
private double mean(Vector v)
{
	int n = v.size();
double[] x = new double[n];
for(int i = 0; i < n; i++) x[i] = v.get(i);
return BasicStat.mean(x);
}

/*
* Private helper method to predict for a single row matrix.
*/
private double computePrediction(Vector v)
{
int n = _bn.size();
double sum = 0.0;
for(int i = 0; i < n; i++)
{
sum += _bn.get(i)*v.get(i);
}
return _b0 + sum;
}


{
_hasData = false;
}

private boolean _hasData;
private double _b0; // beta0
private Vector _bn; // beta1, beta2, beta3 ... betan

private static final double THRESHOLD = 1E-6;
}

// END

