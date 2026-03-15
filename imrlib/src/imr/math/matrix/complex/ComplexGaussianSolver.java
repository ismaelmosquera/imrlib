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
* ComplexGaussianSolver.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix.complex;

/**
* The <code>ComplexGaussianSolver</code> class offers functionallity to solve NxN linear systams of equations with complex number coefficients. <p>
* @author Ismael Mosquera Rivera.
*
*/
public class ComplexGaussianSolver
{

/**
* Static method to solve a NxN system of linear equations with complex number coefficients. <p>
* @param m
* A complex matrix having the data.
* <p>
* @return A complex vector with the solution or null if the operation cannot be done.
*
*/
public static ComplexVector solve(ComplexMatrix m)
{
if(m == null) return null;
if((m.rows()+1) != m.columns()) return null; // malformed parameter
return SysHelper.upperTriangularSystemSolver(SysHelper.gaussianElimination(m, true));
}

/**
* Static method to solve a NxN system of linear equations with complex number coefficients. <p>
* @param m
* A square complex matrix  having the coefficients for the system matrix.
* <p>
* @param v
* A complex vector having the complex number independent coefficients.
*
* @return A complex vector with the solution or null if the operation cannot be done.
*
*/
public static ComplexVector solve(ComplexMatrix m, ComplexVector v)
{
if(m.rows() != m.columns()) return null; // m must be square
if(v.size() != m.rows()) return null; // malformed vector
int n = m.rows();
ComplexMatrix a = new ComplexMatrix(n, n+1);
// Build system matrix
for(int i = 0; i < n; i++)
{
a = a.setColumn(i, m.getColumn(i));
}
a = a.setColumn(n, v);
return SysHelper.upperTriangularSystemSolver(SysHelper.gaussianElimination(a, true));
}


// Private constructor
private ComplexGaussianSolver() {}

}

// END
