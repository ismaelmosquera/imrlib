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
* ComplexMatrixDiagonalization.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera.
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;

/**
* This class performs diagonalization of a square complex matrix A. <p>
* We must find a P orthogonal complex matrix and a D diagonal complex matrix, so that: <p>
* A = PDP^-1
* <p>
* Thus:
* P^-1AP = D
* <p>
* Take in account that maybe it could not be possible to find such a P and D matrices. <p>
* In that case, we say that the matrix A is defective. <p>
* This class uses assertions. To enable them, use the '-ea' modifier when executing. <p>
* Example: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class ComplexMatrixDiagonalization implements ComplexMatrixDecomposition
{

/**
* Constructor. <p>
* Makes a new instance for a <code>ComplexMatrixDiagonalization</code> object.
*
*/
public ComplexMatrixDiagonalization()
{
_hasDiagonalization = false;
_p = null;
_d = null;
_pt = null;
}

/**
* Method to perform the required decomposition ( PDP^-1 ). <p>
* You can retrieve the complex matrices P, D and P^-1 ( Pt ) using the methods below. <p>
* @param m
* A square complex matrix object.
* <p>
* @return true if success or false otherwise.
*
*/
public boolean decompose(ComplexMatrix m)
{
	_hasDiagonalization = false;
if(m.rows() != m.columns()) return false; // m must be square
assert(m.rows() > 1 && m.rows() <= 4): "ComplexMatrixDiagonalization -> decompose(ComplexMatrix): At this moment, complex matrix diagonalization is only available for 4th order matrices as much.";
ComplexEigen[] eigsys = ComplexEigenFinder.eigenSystemFinder(m);
if(eigsys == null) return false; // Fat chance.
int n = eigsys.length;
ComplexNumber[] x = new ComplexNumber[n];
for(int i = 0; i < n; i++) x[i] = eigsys[i].value();
if(multiplicity(x)) return false; // eigen multiplicity could not be supported.
_p = new ComplexMatrix(n, n);
_d = new ComplexMatrix(n, n);
for(int i = 0; i < n; i++)
{
_p = _p.setColumn(i, eigsys[i].vector().normalize());
_d.set(i, i, x[i]);
}
_pt = _p.transpose();
	_hasDiagonalization = true;
	return true;
}

/**
* Gets the P complex matrix. <p>
* @return P complex matrix ( P must be orthogonal ).
*
*/
public ComplexMatrix getP()
{
	if(!_hasDiagonalization) return null;
return _p;
}

/**
* Gets the D complex matrix. <p>
* @return D complex matrix ( D must be a diagonal matrix ).
*
*/
public ComplexMatrix getD()
{
	if(!_hasDiagonalization) return null;
return _d;
}

/**
* Gets the P^-1 complex matrix ( the inverse matrix of P ), since P is orthogonal, its inverse is its transposed. <p>
* @return P^-1 complex matrix.
*
*/
public ComplexMatrix getPt()
{
	if(!_hasDiagonalization) return null;
return _pt;
}

/**
* Computes the determinant of the complex matrix A ( original matrix before decomposition ). <p>
* @return Determinant of A or Complex.NaN if not available.
*
*/
public ComplexNumber det()
{
if(!_hasDiagonalization) return ComplexNumber.NaN;
int n = _d.rows();
ComplexNumber d = new ComplexNumber(1.0f, 0.0f);
for(int i = 0; i < n; i++) d = d.mul(_d.get(i, i));
return d;
}

/**
* Prints the result of the diagonalization to the console.
*
*/
public void print()
{
	if(!_hasDiagonalization)
	{
	System.out.println("[]");
	return;
	}
System.out.println("P:");
_p.print();
System.out.println();
System.out.println("D:");
_d.print();
System.out.println();
System.out.println("P^-1:");
_pt.print();
System.out.println();
}


// Helper method to check for eigen value multiplicity
private boolean multiplicity(ComplexNumber[] v)
{
boolean retval = false;
int n = v.length;
ComplexNumber current = null;
for(int i = 0; i < n-1; i++)
{
current = v[i];
for(int j = i+1; j < n; j++)
{
if(v[j].equals(current))
{
	retval = true;
	break;
}
}
}
return retval;
}


private boolean _hasDiagonalization;

private ComplexMatrix _p;
private ComplexMatrix _d;
private ComplexMatrix _pt;

}

// END
