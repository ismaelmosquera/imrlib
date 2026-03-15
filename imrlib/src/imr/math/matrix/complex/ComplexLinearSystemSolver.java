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
* ComplexLinearSystemSolver.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera.
*
*/

package imr.math.matrix.complex;

/**
* The <code>ComplexLinearSystemSolver</code> class has static methods to solve linear systems of equations in several ways.
* <p>
* Supported methods:
* <ul>
* <li>Cramer's rule.</li>
* <li>Gaussian elimination.</li>
* <li>LU decomposition.</li>
* <li>QR factorization.</li>
* </ul>
* <p>
* All of them for complex number coefficient matrices.
* <p>
* @author Ismael Mosquera rivera.
*
*/
public final class ComplexLinearSystemSolver
{

/**
	* Static method to solve a linear system with a mxn complex matrix passed as parameter where n = m+1. <p>
	* @param m a mxn system complex matrix. <p>
	* The complex matrix passed as parameter is the nxn coeficient matrix and the last column has the coeficient vector. <p>
	* So it is a mxn complex matrix where n = m+1. <p>
	* @return a complex vector with the solution of the system, or null if the operation cannot be done.
	*
	*/
public static ComplexVector cramerSolver(ComplexMatrix m)
{
return ComplexCramerSolver.solve(m);
}

/**
* static method to solve a linear system passing a nxn coeficient complex matrix and a n coeficient complex vector as parameter. <p>
* @param m a nxn coeficient complex matrix.
* <p>
* @param v a n coeficient complex vector.
* <p>
* @return a complex vector with the solution of the system, or null if the operation cannot be done.
*
*/
public static ComplexVector cramerSolver(ComplexMatrix m, ComplexVector v)
{
return ComplexCramerSolver.solve(m, v);
}

/**
	* Static method to solve a linear system with a mxn complex matrix passed as parameter where n = m+1. <p>
	* @param m a mxn system complex matrix. <p>
	* The complex matrix passed as parameter is the nxn coeficient complex matrix and the last column has the coeficient complex vector. <p>
		* So it is a mxn complex matrix where n = m+1. <p>
	* @return a complex vector with the solution of the system, or null if the operation cannot be done.
	*
	*/
public static ComplexVector gaussianSolver(ComplexMatrix m)
{
return ComplexGaussianSolver.solve(m);
}

/**
* static method to solve a linear system passing a nxn coeficient complex matrix and a n coeficient complex vector as parameter. <p>
* @param m a nxn coeficient complex matrix.
* <p>
* @param v a n coeficient complex vector.
* <p>
* @return a complex vector with the solution of the system, or null if the operation cannot be done.
*
*/
public static ComplexVector gaussianSolver(ComplexMatrix m, ComplexVector v)
{
return ComplexGaussianSolver.solve(m, v);
}

/**
* Solves a linear system having a ComplexLU decomposition done.
* <p>
* @param lu An already done ComplexLU decomposition.
* <p>
* @param v Coeficient's complex vector of independent terms.
* <p>
* @return Solution's complex vector, or null if the operation cannot be done.
*
*/
public static ComplexVector luSolver(ComplexLU lu, ComplexVector v)
{
return lu.solve(v);
}

/**
* Solves a linear system having a ComplexQR factorization done.
* <p>
* @param qr An already done ComplexQR factorization.
* <p>
* @param v Coeficient's complex vector of independent terms.
* <p>
* @return Solution's complex vector, or null if the operation cannot be done.
*
*/
public static ComplexVector qrSolver(ComplexQR qr, ComplexVector v)
{
return qr.solve(v);
}

// private constructor so that this class cannot be instantiated.
private ComplexLinearSystemSolver() {}
}

// END
