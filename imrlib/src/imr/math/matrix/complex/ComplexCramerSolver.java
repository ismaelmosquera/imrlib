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
* ComplexCramerSolver.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;

/**
* The <code>ComplexCramerSolver</code> class offers functionallity to solve NxN linear systems of equations of complex number coefficients. <p>
* @author Ismael Mosquera Rivera.
*
*/
public class ComplexCramerSolver
{

/**
* Static method to solve the linear system of equations represented by the complex matrix passed as parameter. <p>
* @param m
* A complex matrix representing the linear systam of equations.
* <p>
* The complex matrix passed as parameter must be MxN dimension, where M+1 = N. <p>
* @return A complex vector having the solution for the linear system or null if the operation cannot be done.
*
*/
public static ComplexVector solve(ComplexMatrix m)
{
	if(m == null) return null;
if((m.rows()+1) != m.columns()) return null; // malformed matrix
// build data
int n = m.rows();
ComplexMatrix a = new ComplexMatrix(n, n);
for(int i = 0; i < n; i++)
{
a = a.setColumn(i, m.getColumn(i));
}
ComplexVector x = m.getColumn(n);
return applyCramerRule(a, x);
}

/**
* Static method to solve a NxN linear system of equations with complex coefficients. <p>
* @param m
* A square matrix having the coefficients for the system matrix.
* <p>
* @param v
* A complex vector of independent complex number coefficients.
* <p>
* The complex vector 'v' must have the same size as rows in the coefficient complex matrix.
* <p>
* @return A complex vector with the system solution or null if the operation cannot be done.
*
*/
public static ComplexVector solve(ComplexMatrix m, ComplexVector v)
{
	if(m == null || v == null) return null;
if(m.rows() != m.columns()) return null; // m must ve square
if(v.size() != m.rows()) return null; // malformed vector
return applyCramerRule(m, v);
}


// Private static method to apply Cramer's rule
private static ComplexVector applyCramerRule(ComplexMatrix m, ComplexVector v)
{
ComplexNumber d = m.det();
if(d.magnitude() < SysHelper.THRESHOLD) return null; // det(m) is too close to zero
ComplexVector out = new ComplexVector(v.size());
int n = out.size();
for(int i = 0; i < n; i++)
{
out.set(i, m.setColumn(i, v).det().div(d));
}
return out;
}


// Private constructor so that this class cannot be instantiated
private ComplexCramerSolver() {}

}

// END
