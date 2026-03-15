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
* MatrixDiagonalization.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera.
*/

package imr.math.matrix;

/**
* This class performs diagonalization of a square matrix A. <p>
* We must find a P orthogonal matrix and a D diagonal matrix, so that: <p>
* A = PDP^-1
* <p>
* Thus:
* P^-1AP = D
* <p>
* Take in account that maybe it could not be possible to find such a P and D matrices. <p>
* In that case, we say that the matrix A is defective. <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class MatrixDiagonalization implements MatrixDecomposition
{

/**
* Constructor. <p>
* Makes a new instance for a <code>MatrixDiagonalization</code> object.
*
*/
public MatrixDiagonalization()
{
_hasDiagonalization = false;
_p = null;
_d = null;
_pt = null;
}

/**
* Method to perform the required decomposition ( PDP^-1 ). <p>
* You can retrieve the matrices P, D and P^-1 ( Pt ) using the methods below. <p>
* @param m
* A square real matrix object.
* <p>
* @return true if success or false otherwise.
*
*/
public boolean decompose(Matrix m)
{
	_hasDiagonalization = false;
_p = null;
_d = null;
_pt = null;
if(m.rows() != m.columns()) return false; // m must be square
Eigen[] eigsys = EigenFinder.findEigenSystem(m);
if(eigsys == null) return false;
int n = eigsys.length;
double[] x = new double[n];
for(int i = 0; i < n; i++) x[i] = eigsys[i].value();
if(multiplicity(x)) return false;
_p = new Matrix(n, n);
_d = new Matrix(n, n);
for(int i = 0; i < n; i++)
{
_p.setColumn(Vector.normalize(eigsys[i].vector()), i);
_d.set(i, i, x[i]);
}
_pt = _p.transpose();
	_hasDiagonalization = true;
	return true;
}

/**
* Gets the P matrix. <p>
* @return P matrix ( P must be orthogonal ).
*
*/
public Matrix getP()
{
return _p;
}

/**
* Gets the D matrix. <p>
* @return D matrix ( D must be a diagonal matrix ).
*
*/
public Matrix getD()
{
return _d;
}

/**
* Gets the P^-1 matrix ( the inverse matrix of P ), since P is orthogonal, its inverse is its transposed. <p>
* @return P^-1 matrix.
*
*/
public Matrix getPt()
{
return _pt;
}

/**
* Computes the determinant of the matrix A ( original matrix before decomposition ). <p>
* @return Determinant of A or Double.NaN if not available.
*
*/
public double det()
{
if(!_hasDiagonalization) return Double.NaN;
int n = _d.rows();
double d = 1.0;
for(int i = 0; i < n; i++) d *= _d.get(i, i);
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
private boolean multiplicity(double[] v)
{
boolean retval = false;
int n = v.length;
double current;
for(int i = 0; i < n-1; i++)
{
current = v[i];
for(int j = i+1; j < n; j++)
{
if(v[j] == current)
{
	retval = true;
	break;
}
}
}
return retval;
}


private boolean _hasDiagonalization;

private Matrix _p;
private Matrix _d;
private Matrix _pt;

}

// END
