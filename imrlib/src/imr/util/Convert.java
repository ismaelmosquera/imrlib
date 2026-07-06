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
* Convert.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

import imr.math.ComplexNumber;
import imr.math.PolarNumber;
import imr.math.RationalNumber;
import imr.math.matrix.Matrix;
import imr.math.matrix.complex.ComplexMatrix;
import imr.math.matrix.Vector;
import imr.math.matrix.complex.ComplexVector;

/**
* This class has static methods suitable to do some conversions.
*
* <ul>
* <li>Array type conversions.</li>
* <li>Radians to degrees and degrees conversion.</li>
* <li>Complex to Polar and Polar to complex conversion.</li>
* <li> ... </li>
* </ul>
*
* We decided to centralize this functions in order to avoid code redundant.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public class Convert
{

/**
* Polar to Complex conversion.
* <p>
* @param p
* A <code>PolarNumver</code> object.
* <p>
* @return A <code>ComplexNumber</code> object result of the conversion.
*
*/
public static ComplexNumber toComplex(PolarNumber p)
{
	double mag = p.getMagnitude();
double arg = p.getArgument();
	return new ComplexNumber(mag * Math.cos(arg), mag * Math.sin(arg));
}

/**
* Complex to Polar conversion.
* <p>
* @param z
* A <code>ComplexNumver</code> object.
* <p>
* @return A <code>PolarNumber</code> object result of the conversion.
*
*/
public static PolarNumber toPolar(ComplexNumber z)
{
return new PolarNumber(z.magnitude(), z.argument());
}

/**
* Converts radians to degrees ( sexagesimal ).
* <p>
* @param r
* radians value.
* <p>
* @return degrees value result of the conversion.
*
*/
public static double toDegrees(double r)
{
	return r * 180.0 / Math.PI;
}

/**
* Converts degrees ( sexagesimal ) to radians.
* <p>
* @param g
* degrees value.
* <p>
* @return radians value result of the conversion.
*
*/
public static double toRadians(double g)
{
	return g * Math.PI / 180.0;
}


// Array conversion functions.

/**
* Float array to int array conversion.
* <p>
* Take in account that maybe you can lose information with this conversion so, be careful.
* <p>
* @param p
* A float 1-dimensional array.
* <p>
* @return int array result of the conversion.
*
*/
public static int[] toIntArray(float[] p)
{
int n = p.length;
int[] out = new int[n];
for(int i = 0; i < n; i++)
{
out[i] = (int)p[i];
}
return out;
}

/**
* Complex array to int array conversion.
* <p>
* Take in account that maybe you can lose information with this conversion so, be careful.
* <p>
* @param p
* A complex number 1-dimensional array.
* <p>
* @return int array result of the conversion.
*
*/
public static int[] toIntArray(ComplexNumber[] p)
{
int n = p.length;
int[] out = new int[n];
for(int i = 0; i < n; i++)
{
out[i] = (int)p[i].getReal();
}
return out;
}

/**
* Gets a float array from an integer one.
* <p>
* @param p
* An integer 1-dimensional array.
* <p>
* @return float array builded from the integer one passed as parameter.
*
*/
public static float[] toFloatArray(int[] p)
{
int n = p.length;
float[] out = new float[n];
for(int i = 0; i < n; i++)
{
out[i] = (float)p[i];
}
return out;
}

/**
* Gets a float array from a complex number one.
* <p>
* Take in account that maybe you can lose information with this conversion so, be careful.
* <p>
* @param p
* An complex number 1-dimensional array.
* <p>
* @return float array builded from the complex number one passed as parameter.
*
*/
public static float[] toFloatArray(ComplexNumber[] p)
{
int n = p.length;
float[] out = new float[n];
for(int i = 0; i < n; i++)
{
out[i] = (float)p[i].getReal();
}
return out;
}

/**
* Converts double to float array. <p>
* @param p
* A double array.
* <p>
* @return Converted array.
*
*/
public static float[] toFloatArray(double[] p)
{
if(p == null) return null;
float[] out = new float[p.length];
for(int i = 0; i < out.length; i++)
{
out[i] = (float)p[i];
}
return out;
}

/**
* Gets a complex number array from an integer one.
* <p>
* @param p
* An integer 1-dimensional array.
* <p>
* @return Complex array builded from the integer one passed as parameter.
*
*/
public static ComplexNumber[] toComplexArray(int[] p)
{
if(p == null) return null;
int size = p.length;
ComplexNumber[] out = new ComplexNumber[size];
for(int i = 0; i < size; i++)
{
out[i] = new ComplexNumber((double)p[i], 0.0);
}
return out;
}

/**
* Gets a complex number array from a float one.
* <p>
* @param p
* A float 1-dimensional array.
* <p>
* @return Complex array builded from the float one passed as parameter.
*
*/
public static ComplexNumber[] toComplexArray(float[] p)
{
if(p == null) return null;
int size = p.length;
ComplexNumber[] out = new ComplexNumber[size];
for(int i = 0; i < size; i++)
{
out[i] = new ComplexNumber((double)p[i], 0.0);
}
return out;
}

/**
* Converts a rational number array to a float array.
* <p>
* @param q
* A rational number array.
* <p>
* @return A float array builded from the rational passed as parameter.
*
/
*/
public static float[] toFloatArray(RationalNumber[] q)
{
if(q == null || q.length == 0) return null;
int n = q.length;
float[] out = new float[n];
for(int i = 0; i < n; i++)
{
out[i] = q[i].value();
}
return out;
}

/**
* Converts the polar number array passed as parameter to a complex number array.
* <p>
* @param p
* A polar number array.
* <p>
* @return a complex number array builded from the polar one passed as parameter.
*
*/
public static ComplexNumber[] toComplexArray(PolarNumber[] p)
{
if(p == null || p.length == 0) return null;
int n = p.length;
ComplexNumber[] out = new ComplexNumber[n];
for(int i = 0; i < n; i++)
{
	out[i] = p[i].toComplex();
}
return out;
}

/**
* Converts the complex number array passed as parameter to a polar number array.
* <p>
* @param c
* A complex number array.
* <p>
* @return A polar number array builded from the complex one passed as parameter.
*
*/
public static PolarNumber[] toPolarArray(ComplexNumber[] c)
{
if(c == null || c.length == 0) return null;
int n = c.length;
PolarNumber[] out = new PolarNumber[n];
for(int i = 0; i < n; i++)
{
out[i] = c[i].toPolar();
}
return out;
}

/**
* Converts int to double array. <p>
* @param p
* An integer array.
* <p>
* @return converted array.
*
*/
public static double[] toDoubleArray(int[] p)
{
if(p == null) return null;
double[] out = new double[p.length];
for(int i = 0; i < out.length; i++)
{
out[i] = (double)p[i];
}
return out;
}

/**
* Converts float to double array. <p>
* @param p
* A float array.
* <p>
* @return converted array.
*
*/
public static double[] toDoubleArray(float[] p)
{
if(p == null) return null;
double[] out = new double[p.length];
for(int i = 0; i < out.length; i++)
{
out[i] = (double)p[i];
}
return out;
}

/**
* Converts complex to double array. <p>
* @param p
* A complex array.
* <p>
* @return converted array.
*
*/
public static double[] toDoubleArray(ComplexNumber[] p)
{
if(p == null) return null;
double[] out = new double[p.length];
for(int i = 0; i < out.length; i++)
{
	out[i] = p[i].getReal();
}
return out;
}

/**
* Converts double to complex array. <p>
* @param p
* A double array.
* <p>
* @return converted array.
*
*/
public static ComplexNumber[] toComplexArray(double[] p)
{
if(p == null) return null;
ComplexNumber[] out = new ComplexNumber[p.length];
for(int i = 0; i < out.length; i++)
{
out[i] = new ComplexNumber(p[i], 0.0);
}
return out;
}

/**
* Static method to convert a real matrix to a complex matrix. <p>
* @param m
* A real matrix object.
* <p>
* @return A complex matrix builded from the real matrix passed as parameter.
*
*/
public static ComplexMatrix toComplexMatrix(Matrix m)
{
if(m == null) return null;
int r = m.rows();
int c = m.columns();
ComplexMatrix cmat = new ComplexMatrix(r, c);
for(int i = 0; i < r; i++)
{
	for(int j = 0; j < c; j++)
	{
		cmat.set(i, j, new ComplexNumber(m.get(i, j), 0.0));
	}
}
return cmat;
}

/**
* Static method to convert a complex matrix to a real matrix. <p>
* Take in account that you can lose information related to imaginary part. <p>
* @param m
* A complex matrix object.
* <p>
* @return A real matrix builded from the complex matrix passed as parameter.
*
*/
public static Matrix toRealMatrix(ComplexMatrix m)
{
if(m == null) return null;
int r = m.rows();
int c = m.columns();
Matrix out = new Matrix(r, c);
for(int i = 0; i < r; i++)
{
	for(int j = 0; j < c; j++)
	{
		out.set(i, j, m.get(i, j).getReal());
	}
}
return out;
}

/**
* Static method to conver real vectors to complex vectors. <p>
* @param v
* A real vector object.
* <p>
* @return Converted complex vector or null if the operation cannot be done.
*
*/
public static ComplexVector toComplexVector(Vector v)
{
	if(v == null) return null;
	int n = v.size();
	ComplexVector out = new ComplexVector(n);
	for(int i = 0; i < n; i++)
	{
	out.set(i, new ComplexNumber(v.get(i), 0.0));
	}
	return out;
}

/**
* Static method to convert a complex vector to a real vector. <p>
* @param v
* A complex vector object.
* <p>
* Take in account that using such a conversion, you could lose the information about the imaginary part in the content of the complex vector. <p>
* @return Converted real vector or null if the operation cannot be done.
*
*/
public static Vector toRealVector(ComplexVector v)
{
if(v == null) return null;
int n = v.size();
Vector out = new Vector(n);
for(int i = 0; i < n; i++)
{
out.set(i, v.get(i).getReal());
}
return out;
}


// Private constructor so that this class cannot be instantiated.
private Convert() {}

}

// END
