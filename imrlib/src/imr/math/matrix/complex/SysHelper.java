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
* SysHelper.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;
import imr.math.RandomNumberGenerator;

/**
* The <code>SysHelper</code> class implements some static methods useful to do common operations. <p>
* This class has package scope, since its methods are used just for other classes inside this package: imr.math.matrix.complex
* <p>
* We decided to centralize all of this funcionallity in this class in order to make compact code. <p>
* @author Ismael Mosquera Rivera.
*
*/
class SysHelper
{

/**
* Static method to perform Gaussian elimination for the complex matrix passed as parameter. <p>
* This class is able to do the task checking or not for indetermination. <p>
* This method uses pivoting in the elimination process so, the pivots are placed in decreassign order from the top. <p>
* @param m
* A complex matrix.
* <p>
* @param undet
* A boolean flag to tel the method if you want to check for indetermination. <p>
* <code>true</code> if checking is wanted or <code>false</code> otherwise.
* <p>
* @return A complex matrix result of the Gaussian elimination or null if the operation cannot be done.
*
*/
static ComplexMatrix gaussianElimination(ComplexMatrix m, boolean undet)
{
int row, i, j, k;
float pivot;
ComplexNumber elim = null;
ComplexNumber tmp = null;
ComplexMatrix u = (ComplexMatrix)m.clone();
i = 0;
 while(i < u.rows())
 {
	 /* find pivot */
  pivot = u.get(i, i).magnitude();
  row = i;
  for(k = i+1; k < u.rows(); k++)
   {
   if(u.get(k, 0).magnitude() > pivot)
   {
    pivot = u.get(k,0).magnitude();
    row = k;
   }
}
   /* check for determination if requested */
   if(undet)
   {
   if(pivot < THRESHOLD) return null;
}
   if(i != row) /* swap rows if necessary */
  {
    for(j = 0; j < u.columns(); j++)
    {
     tmp = u.get(i, j);
     u.set(i, j, u.get(row, j));
     u.set(row, j, tmp);
}
}
  for(j = i+1; j < u.rows(); j++)
  {
	  /* find zeros */
   elim = u.get(j, i).scale(-1.0f).div(u.get(i, i));
   for(k = 0; k < u.columns(); k++)
   {
    u.set(j,k, u.get(j, k).add(elim.mul(u.get(i, k))));
}
   }
 i++;
}
return u;
}

/**
* This static method solves a previously builded linear equations system. <p>
* @param m
* A complex matrix ready to strait forward solve.
* <p>
* @return A complex vector with the solution or null if the operation cannot be done.
*
*/
static ComplexVector upperTriangularSystemSolver(ComplexMatrix m)
{
	if(m == null) return null;
int i, j;
ComplexVector x =  new ComplexVector(m.rows());
/*
* compute xn.
*/
x.set(m.rows()-1, m.get(m.rows()-1, m.columns()-1).div(m.get(m.rows()-1, m.columns()-2)));
/*
* Compute xn-1 xn-2 xn-3 ... x0
*/
for(i = m.rows()-2; i >= 0; i--)
{
	x.set(i, m.get(i, m.rows()));
	for(j = i+1; j < m.rows(); j++) x.set(i, x.get(i).sub(m.get(i, j).mul(x.get(j))));
x.set(i, x.get(i).div(m.get(i, i)));
}
return x;
}

/**
* This static method extends the data passed as parameters in order to get a system matrix. <p>
* @param m
* A coefficient complex matrix.
* <p>
* @param v
* A complex vector having the independent coefficients.
* <p>
* @return A extended complex matrix of the hole system or null if the operation cannot be done.
*
*/
static ComplexMatrix systemMatrix(ComplexMatrix m, ComplexVector v)
{
if(m == null || v == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
if(v.size() != m.rows()) return null; // malformed vector
int n = m.rows();
ComplexMatrix out = new ComplexMatrix(n, n+1);
for(int i = 0; i < n; i++)
{
	out = out.setColumn(i, m.getColumn(i));
}
out = out.setColumn(n, v);
return out;
}

/**
* This static method prepares a complex matrix to solve a homogeneous linear system of equations. <p>
* @param m
* A complex square singular matrix.
* <p>
* @return A complex extended matrix ready to be solved as a normal linear system or null if the operation cannot be done.
*
*/
static ComplexMatrix setHomogeneousSystem(ComplexMatrix m)
{
if(m == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
float param = 1.0f / (float)RandomNumberGenerator.generate(1, 10); // compute parameter
int n = m.rows();
ComplexMatrix a = (ComplexMatrix)m.clone();
a.set(n-1, n-1, new ComplexNumber(1.0f, 0.0f));
ComplexVector v = new ComplexVector(n);
v.set(n-1, new ComplexNumber(param, 0.0f));
return systemMatrix(a, v);
}


// Private constructor
private SysHelper() {}

/**
* This constant has package scope.
*
*/
static final float THRESHOLD = 1E-6f;
}

// END
