/*
 * Copyright (c) 2024 Ismael Mosquera Rivera
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
* LinearSystemSolver.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera.
*
*/

package imr.math.matrix;

/**
* The <code>LinearSystemSolver</code> class has static methods to solve linear systems of equations in several ways.
* <p>
* Supported methods:
* <ul>
* <li>Cramer's rule.</li>
* <li>Gaussian elimination.</li>
* <li>LU decomposition.</li>
* <li>QR factorization.</li>
* </ul>
* <p>
* @author Ismael Mosquera rivera.
*
*/
public final class LinearSystemSolver
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
public static Vector cramerSolver(Matrix m)
{
return CramerSolver.solve(m);
}

/**
* static method to solve a linear system passing a nxn coeficient matrix and a n coeficient vector as parameter.
* @param m a nxn coeficient matrix.
* @param v a n coeficient vector.
* @return a vector with the solution of the system.
* If the system cannot be solved, this method returns null.
*/
public static Vector cramerSolver(Matrix m, Vector v)
{
return CramerSolver.solve(m, v);
}

/**
	* Static method to solve a linear system with a mxn matrix passed as parameter where n = m+1.
	* @param m a mxn system matrix.
	* @return a vector with the solution of the system.
	* The matrix passed as parameter is the nxn coeficient matrix and the last column has the coeficient vector.
	* So it is a mxn matrix where n = m+1.
	* <p>
	* If the system cannot be solved, this method returns null.
	*/
public static Vector gaussianSolver(Matrix m)
{
return GaussianSolver.solve(m);
}

/**
* static method to solve a linear system passing a nxn coeficient matrix and a n coeficient vector as parameter.
* @param m a nxn coeficient matrix.
* @param v a n coeficient vector.
* @return a vector with the solution of the system.
* If the system cannot be solved, this method returns null.
*/
public static Vector gaussianSolver(Matrix m, Vector v)
{
return GaussianSolver.solve(m, v);
}

/**
* Solves a linear system having a LU decomposition done.
* <p>
* @param lu An already done LU decomposition.
* @param v Coeficient's vector of independent terms.
* <p>
* @return Solution's vector.
*
*/
public static Vector luSolver(LU lu, Vector v)
{
return lu.solve(v);
}

/**
* Solves a linear system having a QR factorization done.
* <p>
* @param qr An already done QR factorization.
* @param v Coeficient's vector of independent terms.
* <p>
* @return Solution's vector.
*
*/
public static Vector qrSolver(QR qr, Vector v)
{
return qr.solve(v);
}

// private constructor so that this class cannot be instantiated.
private LinearSystemSolver() {}
}

// END
