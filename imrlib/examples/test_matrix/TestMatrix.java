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
* TestMatrix
* Author: Ismael Mosquera rivera
*/

import imr.matrix.*;

public class TestMatrix
{
public static void main(String[] args)
{
// construct a matrix from a file
// it's a mxn matrix where n=m+1
// the mxm is the coeficient matrix
// the last colun of the mxn matrix is the coeficient vector
Matrix m = new Matrix("system.dat");

// print matrix
System.out.println("system matrix:");
m.print();
System.out.println();
// solve using CramerSolver
System.out.println("Solution using CramerSolver:");
// solve
Vector x = LinearSystemSolver.cramerSolver(m);

// show solution
System.out.println("solution:");
x.print();
System.out.println();

// print a chunk from the solution vector index1..2
System.out.println("chunk 1..2 from the solution vector:");
x.get(1,2).print();
System.out.println();

// solve using an alternative static method from CramerSolver
m.load("mcoef.dat");
x.load("vcoef.dat");
System.out.println("Solve system using an alternative method from CramerSolver...");
LinearSystemSolver.cramerSolver(m, x).print();
System.out.println();

// solve using Gaussian elimination
Matrix sm = new Matrix(0, 0);
sm.load("system.dat");
System.out.println("Solve system using Gaussian elimination.");
System.out.println("system matrix:");
sm.print();
System.out.println();
System.out.println("solution:");
LinearSystemSolver.gaussianSolver(sm).print();
System.out.println();

// solve system using an alternative for Gaussian elimination
System.out.println("Solve system using an alternative method for Gaussian elimination");
System.out.println("matrix of coefficients:");
m.print();
System.out.println();
System.out.println("vector of coefficients:");
x.print();
System.out.println();
System.out.println("solution:");
LinearSystemSolver.gaussianSolver(m, x).print();
System.out.println();

// construct an empty matrix
Matrix m1 = new Matrix(0,0);

// load from a file
m1.load("mcoef.dat");

// show loaded matrix
System.out.println("coeficient matrix:");
m1.print();
System.out.println();

// compute inverse
Matrix m2 = m1.inverse();

// show inverse
System.out.println("inverse:");
m2.print();
System.out.println();

System.out.println("Store inverse in mcoef_inv.dat");
m2.store("mcoef_inv.dat");
System.out.println("Inverse matrix stored.");
System.out.println();

// A * A^-1 = I
System.out.println("A * A^-1 = I");
Matrix.mul(m1,m2).print();
System.out.println();

// print just a 2x2 chunk from the identity matrix
System.out.println("2x2 chunk from the identity matrix:");
Matrix.mul(m1,m2).get(0,1,0,1).print();
System.out.println();

// print m1 matrix
System.out.println("coeficient matrix:");
m1.print();
System.out.println();

// get LU decomposition
LU lu = new LU();
lu.decompose(m1);
// Show LU decomposition
System.out.println("LU:");
lu.print();

// print m1 again
System.out.println("coeficient matrix:");
m1.print();
System.out.println();

// load vector from a file
x.load("vcoef.dat");

// show coeficient vector
System.out.println("coeficient vector:");
x.print();
System.out.println();

// solve system
x = LinearSystemSolver.luSolver(lu, x);

// show solution
System.out.println("solution:");
x.print();
System.out.println();

// get a rotation matrix
Matrix r1 = Matrix.rotation(1.0,1.0,0.0,Math.PI/4.0);

// show rotation matrix:
System.out.println("rotation matrix:");
r1.print();
System.out.println();

// the transposed of a rotation matrix is its inverse
Matrix r2 = Matrix.transpose(r1);

// show transposed matrix
System.out.println("transposed of the rotation matrix (inverse):");
r2.print();
System.out.println();

// R * R^-1 = I
System.out.println("R *R^-1 = I");
Matrix.mul(r1,r2).print();
System.out.println();

System.out.println("Compute QR factorization:");
m.load("mcoef.dat");
System.out.println("original matrix (A):");
m.print();
System.out.println();

QR qr = new QR();
qr.decompose(m);
System.out.println("QR:");
qr.print();
System.out.println("A = QR:");
qr.getQ().mul(qr.getR()).print();
System.out.println();
System.out.println("Q is orthogonal = " + qr.getQ().isOrthogonal());
System.out.println("Q is orthonormal = " + qr.getQ().isOrthonormal());
System.out.printf("det(A) = %.2f\n", qr.det());
System.out.println();

System.out.println("solve linear system using QR:");
System.out.println("Vector of coeficients:");
x.load("vcoef.dat");
x.print();
System.out.println();
System.out.println("Solution:");
LinearSystemSolver.qrSolver(qr, x).print();
System.out.println();

System.out.println("EIGEN");
System.out.println("Matrix:");
m.print();
System.out.println();
System.out.println("Find maximum Eigen using the Power Method:");
EigenFinder.findMaxEigen(m).print();
System.out.println("Find eigensystem using the QR Algorithm:");
Eigen.print(EigenFinder.findEigenSystem(m));
System.out.println();

System.out.println("bye.");
	}
}


// END
