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
* CramerSolver.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix;


/**
* The <code>CramerSolver</code> class defines static methods to solve linear systems using the Cramer's rule.
* @author Ismael Mosquera Rivera
*/
public final class CramerSolver
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
if(m.rows()+1 != m.columns()) return null;
Matrix a = m.get(0, m.rows()-1, 0, m.rows()-1);
double d = a.det();
if(d==0) return null;
Vector v = new Vector(m.rows());
for(int i = 0; i < m.rows(); i++) v.set(i, m.get(i, m.columns()-1));
Vector result = new Vector(m.rows());
for(int i = 0; i < result.size(); i++) result.set(i, kDet(a, v, i)/d);
return result;
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
double d = m.det();
if(d == 0) return null;
Vector result = new Vector(v.size());
for(int i = 0; i < result.size(); i++) result.set(i, kDet(m, v, i)/d);
return result;
}

private static double kDet(Matrix m, Vector v, int k)
{
	Matrix a = (Matrix)m.clone();
	for(int i = 0; i < a.rows(); i++) a.set(i, k, v.get(i));
return a.det();
}


private CramerSolver(){}
}

// END
