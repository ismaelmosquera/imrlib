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
* ComplexEigenFinder.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera.
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;
import imr.math.polynomial.Polynomial;
import imr.math.polynomial.CharacteristicPolynomial;

/**
* The <code>ComplexEigenFinder</code> class implements static methods to find eigenvalues and eigenvectors for complex number coefficients.
* <p>
* You can get just the maximum Eigen of a complex number coefficient matrix, or its eigensystem. <p>
* This class uses assertions. To enable them, use the '-ea' modifier when executing. <p>
* Example: <p>
* <code>java -ea MyApp</code>
* <p>
*
* @author Ismael Mosquera rivera.
*
*/
public final class ComplexEigenFinder
{

/**
* Finds the maximum eigen for a square complex matrix using the Power Method.
* <p>
* @param m A square complex matrix.
* <p>
* @return The maximumk Eigen for the matrix passed as parameter, or null if the operation cannot be done.
*
*/
public static ComplexEigen findMaxEigen(ComplexMatrix m)
{
	if(m == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
float lim;
ComplexNumber value = new ComplexNumber(0.0f, 0.0f);
ComplexNumber x = null;
ComplexNumber ant = null;
int i, j, n, major;
ComplexMatrix b = null;
ComplexMatrix a = (ComplexMatrix)m.clone();
n = a.columns();
ComplexMatrix aux = new ComplexMatrix(n, 1);
for(i = 0; i < n; i++) aux.set(i, 0, new ComplexNumber(1.0f, 0.0f));
i = 1;
do
{
ant = (ComplexNumber)value.clone();
b = ComplexMatrix.mul(a, aux);
major = 0;
for(j = 0; j < n; j++)
{
if(b.get(major, 0).magnitude() < b.get(j, 0).magnitude()) major = j;
}
value = b.get(major, 0);
for(j = 0; j < n; j++)
{
	x = b.get(j, 0);
	b.set(j, 0, x.div(value));
}
lim = value.sub(ant).magnitude();
if(lim < __THRESHOLD__) break; /* done */
for(j = 0; j < n; j++)
{
aux.set(j, 0, b.get(j, 0));
}
i++;
}while(i < MAX_ITERATIONS);
if(i >= MAX_ITERATIONS) return null;
ComplexVector eigenvector = new ComplexVector(n);
for(i = 0; i < n; i++) eigenvector.set(i, b.get(i, 0));
return new ComplexEigen(value, eigenvector);
}

/**
* Static method to find the eigensystem for a square complex matrix using the QR Algorithm.
* <p>
* @param m a square complex matrix.
* <p>
* @return The eigensystem of the complex matrix passed as parameter, or null if the operation cannot be done.
*
*/
public static ComplexEigen[] findEigenSystem(ComplexMatrix m)
{
	if(m == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
int n = m.rows();
ComplexEigen[] eigensys = new ComplexEigen[n];
ComplexQR qr = new ComplexQR();
if(!qr.decompose(m)) return null; // get QR factorization of m, or return false if fat chance.
/* Compute eigenvalues */
int k = 0;
while(true)
{
	qr.decompose(ComplexMatrix.mul(qr.getR(), qr.getQ()));
		if(done(qr.getQ()) || k > MAX_ITERATIONS) break;
		k++;
}
// eigenvalues are listed in the diagonal of the complex matrix values
ComplexMatrix values = ComplexMatrix.mul(qr.getQ(), qr.getR());
ComplexMatrix lambda = null;
/* compute eigenvectors and build eigensystem */
for(int i = 0; i < n; i++)
{
	lambda = ComplexMatrix.identity(n).scale(values.get(i, i));
	eigensys[i] = new ComplexEigen(values.get(i, i), SysHelper.upperTriangularSystemSolver(SysHelper.setHomogeneousSystem(SysHelper.gaussianElimination(m.sub(lambda), false))));

}
arrange(eigensys);
return eigensys;
}

/**
* Static method to compute the eigen system of a NxN ( square ) complex matrix. <p>
* This method finds the characteristic polynomial of A. <p>
* The roots of such a polynomial are the eigen values of A. <p>
* Once the eigen values are known, you can compute each associated eigen vector by solving N homogenious linear systems of equations. <p>
* @param m
* A square complex matrix.
* <p>
* @return The eigensystem of the complex matrix passed as parameter, or null if the operation cannot be done.
*
*/
public static ComplexEigen[] eigenSystemFinder(ComplexMatrix m)
{
if(m == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
int n = m.rows();
ComplexEigen[] eigensys = new ComplexEigen[n];
/* Compute eigenvalues */
ComplexNumber[] p = CharacteristicPolynomial.compute(m);
if(p == null) return null;
ComplexNumber[] values = Polynomial.roots(p); // roots of p are the eigen values of m
// eigenvalues are listed in the diagonal of lambda
	ComplexMatrix lambda = null;
/* compute eigenvectors and build eigensystem */
for(int i = 0; i < n; i++)
{
	lambda = ComplexMatrix.identity(n).scale(values[i]);
eigensys[i] = new ComplexEigen(values[i], SysHelper.upperTriangularSystemSolver(SysHelper.setHomogeneousSystem(SysHelper.gaussianElimination(m.sub(lambda), false))));
}
arrange(eigensys);
return eigensys;
}


// Helper method to know if we already found eigen values using QR algorithm.
private static boolean done(ComplexMatrix m)
{
for(int i = 0; i < m.rows(); i++)
{
	if(m.get(i, i).magnitude() != 1.0f) return false;
}
return true;
}

// Sort eigens from bigger to smaller ( descending order )
private static void arrange(ComplexEigen[] eigens)
{
	ComplexEigen tmp = null;
for(int i=0; i < eigens.length-1; i++)
{
for(int j=i+1; j < eigens.length; j++)
{
if(eigens[i].value().magnitude() < eigens[j].value().magnitude())
{
tmp = eigens[i];
eigens[i] = eigens[j];
eigens[j] = tmp;
}
}
}
}


// private constructor so that this class cannot be instantiated.
private ComplexEigenFinder() {}

private static final float __THRESHOLD__ = 1E-3f;
private static final int MAX_ITERATIONS = 50000;
}

// END
