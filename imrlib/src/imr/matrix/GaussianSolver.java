/*
 * Copyright (c) 2021-2022 Ismael Mosquera Rivera
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
* GaussianSolver.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.matrix;


/**
* The <code>GaussianSolver</code> class defines static methods to solve linear systems using the Gaussian elimination method.
* @author Ismael Mosquera Rivera
*/
public final class GaussianSolver
{

/**
	* Static method to solve a linear system with a mxn matrix passed as parameter where n = m+1.
	* @param m a mxn system matrix.
	* @return a vector with the solution of the system.
	* The matrix passed as parameter is the nxn coeficient matrix and the last column has the coeficient vector.
	* So it is a mxn matrix where n = m+1.
	* <p>
	* If the system cannot be solved, this method returns null.
	*/
	public static Vector solve(Matrix m)
	{
if(m.rows() != m.columns()-1) return null;
return __triangular_system_solver_(__gaussian_elimination_(m));
	}

/**
* static method to solve a linear system passing a nxn coeficient matrix and a n coeficient vector as parameter.
* @param m a nxn coeficient matrix.
* @param v a n coeficient vector.
* @return a vector with the solution of the system.
* If the system cannot be solved, this method returns null.
*/
	public static Vector solve(Matrix m, Vector v)
	{
if(m.rows() != m.columns() || v.size() != m.rows()) return null;
/*
* build extended system matrix
*/
Matrix ext = (Matrix)m.clone();
ext.resizeColumns(m.columns() + 1);
for(int i = 0; i < ext.rows(); i++) ext.set(i, ext.columns()-1, v.get(i));
return __triangular_system_solver_(__gaussian_elimination_(ext));
	}


private static double __abs_(double x)
{
return (x < 0) ? -x : x;
}

private static Matrix __gaussian_elimination_(Matrix m)
{
int row, i, j, k;
double pivot, elim, tmp;
Matrix u = (Matrix)m.clone();
i = 0;
 while(i < u.rows())
 {
	 /* find pivot */
  pivot = __abs_(u.get(i, i));
  row = i;
  for(k = i+1; k < u.rows(); k++)
   {
   if(__abs_(u.get(k, 0)) > pivot)
   {
    pivot = __abs_(u.get(k,0));
    row = k;
   }
}
   /* check for determination */
   if(pivot < THRESHOLD) return null;
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
   elim = -(u.get(j, i)) / u.get(i, i);
   for(k = 0; k < u.columns(); k++)
   {
    u.set(j,k, u.get(j, k) + elim * u.get(i, k));
}
   }
 i++;
}
return u;
}

private static Vector __triangular_system_solver_(Matrix m)
{
int i, j;
Vector x =  new Vector(m.rows());
/*
* compute xn.
*/
x.set(m.rows()-1, m.get(m.rows()-1, m.columns()-1) / m.get(m.rows()-1, m.columns()-2));
/*
* Compute xn-1 xn-2 xn-3 ... x0
*/
for(i = m.rows()-2; i >= 0; i--)
{
	x.set(i, m.get(i, m.rows()));
	for(j = i+1; j < m.rows(); j++) x.set(i, x.get(i) - m.get(i, j) * x.get(j));
x.set(i, x.get(i) / m.get(i, i));
}
return x;
}


	private GaussianSolver(){}
	private static final double THRESHOLD = 1E-6;
}

// END
