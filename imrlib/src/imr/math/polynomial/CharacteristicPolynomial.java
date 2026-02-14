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
* CharacteristicPolynomial.java
*
* imr-lib
*
* author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

import imr.math.matrix.Matrix;

/**
* The <code>CharacteristicPolynomial</code> class finds the characteristic polynomial of a NxN ( square ) real matrix A. <p>
* The roots of such a polynomial are the eigen values of A. <p>
* Once the eigen values are known, you can compute each associated eigen vector solving N homogeneous linear systems of equations.
* @see imr.math.matrix.EigenFinder
* @author Ismael Mosquera Rivera.
*
*/
public class CharacteristicPolynomial
{

/**
* Static method to compute the characteristic polynomial of a square real matrix. <p>
* @param m
* A square real matrix.
* <p>
* @return Characteristic polynomial of A as a floating-point vector, or null if the operation cannot be done.
*
*/
public static float[] compute(Matrix m)
{
	if(m == null) return null;
	if(m.rows() != m.columns()) return null; // m must be square
	int size = m.rows()+1;
	if(size < 3) return null; // order of m must be at least 2
	float[] out = new float[size];
out[0] = (float)(m.scale(-1.0).det());
out[size-2] = -((float)m.trace());
out[size-1] = 1.0f;
if(size == 3) return out;
int n = size-1; // order of the matrix
int p, q;
float t;
for(int k = 2; (n-k) > 0; k++)
{
	p = n;
	q = k;
	t = 0.0f;
	while(q > 0)
	{
		t += out[p--] * (float)(pow(m, q--).trace());
	}
	out[n-k] = (-1.0f/(float)k) * t;
}
return out;
}


// Helper function to compute matrix powers
private static Matrix pow(Matrix m, int n)
{
	Matrix _m = (Matrix)m.clone();
if(n == 1) return _m;
for(int i = 1; i < n; i++)
{
_m = _m.mul(m);
}
return _m;
}

// Private constructor so that this class cannot be instantiated.
private CharacteristicPolynomial() {}

}

// END
