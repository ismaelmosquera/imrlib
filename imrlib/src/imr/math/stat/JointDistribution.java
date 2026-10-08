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
* JointDistribution.java
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
* This class computes the joint distribution from the probability density function of two variables <p>
* X and Y independent each other. <p>
* Once you have such a joint distribution stored in a <code>imr.math.matrix.Matrix</code> object, <p>
* you can get the marginal distributions for X and Y using some other methods.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class JointDistribution
{

/**
* Static method to compute the joint distribution for independent variables X and Y. <p>
* Each <code>imr.math.matrix.Vector</code> object passed as first and second parameter can be different size. <p>
* @param x
* A <code>Vector</code> object having the data for the pdf of X.
* <p>
* @param y
* A <code>Vector</code> object having the data for the pdf of Y.
* <p>
* @return A <code>Matrix</code> object having the table of the computed joint distribution.
*
*/
public static Matrix compute(Vector x, Vector y)
{
	int nx = x.size();
	int ny = y.size();
Matrix join = new Matrix(nx, ny);
for(int i = 0; i < nx; i++)
{
	for(int j = 0; j < ny; j++)
	{
		join.set(i, j, x.get(i)*y.get(j));
	}
}
return join;
}

/**
* Static method to compute the marginal distribution for X. <p>
* Each entry in the returned <code>Vector</code> object has the sum of each entry in each row from the joint distribution matrix. <p>
* @param p
* A <code>Matrix</code> object having the data of a joint distribution (X, Y)
* <p>
* @return A <code>Vector</code> object with the data for the marginal X distribution.
*
*/
public static Vector marginalX(Matrix p)
{
	int r = p.rows();
	int c = p.columns();
Vector xm = new Vector(p.rows());
double sum = 0.0;
for(int i = 0; i < r; i++)
{
sum = 0.0;
for(int j = 0; j < c; j++)
{
	sum += p.get(i, j);
}
xm.set(i, sum);
}
return xm;
}

/**
* Static method to compute the marginal distribution for Y. <p>
* Each entry in the returned <code>Vector</code> object has the sum of each entry in each column from the joint distribution matrix. <p>
* @param p
* A <code>Matrix</code> object having the data of a joint distribution (X, Y)
* <p>
* @return A <code>Vector</code> object with the data for the marginal Y distribution.
*
*/
public static Vector marginalY(Matrix p)
{
	int r = p.rows();
	int c = p.columns();
Vector ym = new Vector(p.columns());
double sum = 0.0;
for(int i = 0; i < c; i++)
{
sum = 0.0;
for(int j = 0; j < r; j++)
{
	sum += p.get(j, i);
}
ym.set(i, sum);
}
return ym;
}


// Private constructor so that this class cannot be instantiated
private JointDistribution() {}

}

// END
