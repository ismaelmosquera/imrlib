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
* TestComplexMatrix.java
*
* Author: Ismael Mosquera Rivera
*/

import imr.math.matrix.complex.*;

/*
* This example demonstrates functionallity implemented in the imr.math.matrix.complex package.
* In that package, you can find classes to perform almost all the features offered by the imr.math.matrix package but,
* in this case, dealing with complex number coefficients instead with real ones.
* So, you can solve linear systems of complex matrices and vectors, compute a lot of operations applied to complex matrices and vectors,
* perform  things like:
* - Cramer's rule.
* - Gaussian elimination.
* - LU decomposition.
* - QR factorization.
* - Matrix diagonalization.
*
* Compute rank, inverse in several ways, minors, determinants of a complex matrix, checking for orthogonality an orthonormality.
* And support for storage among other things.
*
* In this example, not all the features in the package are demonstrated because there are a lot.
* See the API documentation to know more.
*
*/
public class TestComplexMatrix
{
public static void main(String[] args)
{
System.out.println("COMPLEX LINEAR SYSTEMS OF EQUATIONS");
System.out.println();
ComplexMatrix csys = new ComplexMatrix("cmsystem.dat");
System.out.println("Cramers's Rule ( extended system matrix )");
System.out.println("A:");
csys.print();
System.out.println();
System.out.println("Solution:");
ComplexLinearSystemSolver.cramerSolver(csys).print();
System.out.println();
System.out.println("Cramers's Rule ( coefficient matrix and vector )");
System.out.println("A:");
ComplexMatrix cm = new ComplexMatrix("cmcoef.dat");
cm.print();
System.out.println();
System.out.println("V:");
ComplexVector cv = new ComplexVector("cvcoef.dat");
cv.print();
System.out.println();
System.out.println("Solution:");
ComplexLinearSystemSolver.cramerSolver(cm, cv).print();
System.out.println();
System.out.println("Gaussian elimination ( extended system matrix )");
System.out.println("A:");
csys.print();
System.out.println();
System.out.println("Solution:");
ComplexLinearSystemSolver.gaussianSolver(csys).print();
System.out.println();
System.out.println("Gaussian elimination ( coefficient matrix and vector )");
System.out.println("A:");
cm.print();
System.out.println();
System.out.println("V:");
cv.print();
System.out.println();
System.out.println("Solution:");
ComplexLinearSystemSolver.gaussianSolver(cm, cv).print();
System.out.println();
System.out.println("Complex LU");
System.out.println("A:");
cm.print();
System.out.println();
System.out.println("Complex LU decomposition:");
ComplexLU lu = new ComplexLU();
if(lu.decompose(cm))
{
	lu.print();
	}
	else
	{
		System.out.println("ComplexLU decomposition error.");
		System.out.println();
	}
System.out.println("V:");
cv.print();
System.out.println();
System.out.println("Solution:");
lu.solve(cv).print();
System.out.println();
System.out.println("Complex QR factorization");
System.out.println("A:");
cm.print();
System.out.println();
System.out.println("Complex QR");
ComplexQR qr = new ComplexQR();
if(qr.decompose(cm))
{
	qr.print();
}
else
{
	System.out.println("Complex QR factorization error.");
	System.out.println();
}
System.out.println("Q is orthogonal = " + qr.getQ().isOrthogonal());
System.out.println();
System.out.println("A = QR");
qr.getQ().mul(qr.getR()).print();
System.out.println();
System.out.println("V:");
cv.print();
System.out.println();
System.out.println("solution:");
qr.solve(cv).print();
System.out.println();
System.out.println("DIAGONALIZATION");
System.out.println();
System.out.println("A:");
cm.print();
System.out.println();
ComplexMatrixDiagonalization diag = new ComplexMatrixDiagonalization();
if(diag.decompose(cm))
{
	diag.print();
}
else
{
	System.out.println("ComplexMatrixDiagonalization error. ");
}
System.out.println("P is orthogonal = " + diag.getP().isOrthogonal());
System.out.println();
System.out.println("P^-1AP = D");
diag.getPt().mul(cm).mul(diag.getP()).print();
System.out.println();
System.out.println("thus, A = PDP^-1");
diag.getP().mul(diag.getD()).mul(diag.getPt()).print();
System.out.println();
System.out.println("MINORS");
System.out.println("Here some minors features are presented.");
System.out.println("A:");
cm.print();
System.out.println();
System.out.println("Print all principal minors:");
for(int i = 0; i < cm.columns(); i++)
{
	cm.firstMinorMatrix(i).print();
	System.out.println();
}
System.out.println("DETERMINANTS");
System.out.println("Several ways to compute them:");
System.out.println("A:");
cm.print();
System.out.println();
System.out.println("Using adjuncts -> det(A) = " + cm.det());
System.out.println("Using cofactors -> det(A) = " + cm.determinant());
System.out.println("Using ComplexQR -> det(A) = " + qr.det());
System.out.println("Using ComplexMatrixDiagonalization -> det(A) = " + diag.det());
System.out.println();
System.out.println("COMPLEX MATRIX INVERSION");
System.out.println("Several ways to compute them:");
System.out.println("A:");
cm.print();
System.out.println();
System.out.println("Using ComplexLU:");
System.out.println("A^-1");
cm.inverse().print();
System.out.println();
System.out.println("Using cofactor complex matrix builded from minors:");
System.out.println("A^-1");
cm.invertMatrix().print();
System.out.println();
System.out.println("as you can see, they are equal.");
System.out.println("A * A^-1 = I");
cm.mul(cm.inverse()).print();
System.out.println();
System.out.println("COMPLEX EIGENS");
System.out.println("Several ways to compute them.");
System.out.println("A:");
cm.print();
System.out.println();
System.out.println("Using Power Method ( just for max eigen ):");
ComplexEigenFinder.findMaxEigen(cm).print();
System.out.println("Using QR algorithm and Characteristic Polynomial ( hole eigen system )");
System.out.println("ComplexQR Algorithm:");
ComplexEigen[] eigsys = ComplexEigenFinder.findEigenSystem(cm);
ComplexEigen.print(eigsys);
System.out.println("Characteristic Polynomial:");
eigsys = ComplexEigenFinder.eigenSystemFinder(cm);
ComplexEigen.print(eigsys);


System.out.println();
System.out.println("bye.");
}
}

// END
