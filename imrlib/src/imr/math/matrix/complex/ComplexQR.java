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
* ComplexQR.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;

/**
* The <code>ComplexQR</code> class offers functionality to perform QR complex matrix factorization for square complex matrices.
* <p>
* The skill chosed is Givens Rotations.
* <p>
* After factorization we get:
* <ul>
* <li>Q, an orthogonal complex matrix.</li>
* <li>R, an upper ( triangular superior ) complex matrix.</li>
* </ul>
* <p>
* If we apply QR factorization to a complex matrix A, then we get:
* <p>
* A = QR.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class ComplexQR implements ComplexMatrixDecomposition
{

/**
* Makes a new instance for a <code>ComplexQR</code> object.
*/
public ComplexQR()
{
	_hasQR = false;
_q = null;
_r = null;
	}

/**
* Performs QR factorization over a square complex matrix passed as parameter.
* <p>
* @param m A square complex matrix to be factorized.
* <p>
* @return true if success or false otherwise.
*
*/
public boolean decompose(ComplexMatrix m)
{
	_hasQR = false;
	if(m == null) return false;
if(m.rows() != m.columns()) return false; // m must be square
int k, n;
ComplexNumber w = null;
ComplexMatrix q = ComplexMatrix.identity(m.rows());
ComplexMatrix q1 = null;
ComplexMatrix r = (ComplexMatrix)m.clone();
n = r.columns();
k = 0; // sentinel
for(int i = 0; i < n-1; i++)
{
for(int j = n-1; j > i; j--)
{
	if(r.get(j, i).magnitude() < SysHelper.THRESHOLD) continue;
	q1 = ComplexMatrix.identity(n);
	w = ComplexNumber.atan2(r.get(j, i).scale(-1.0f), r.get(i, i));
	q1.set(i, i, w.cos());
	q1.set(i, j, w.sin().scale(-1.0f));
	q1.set(j, i, w.sin());
	q1.set(j, j, w.cos());
	r = ComplexMatrix.mul(q1, r);
	q = (k == 0) ? (ComplexMatrix)q1.clone() : ComplexMatrix.mul(q1, q);
	k++;
}
}
_q = ComplexMatrix.transpose(q);
_r = (ComplexMatrix)r.clone();
_hasQR = true;
return true;
}

/**
* Solves a linear system having a ComplexQR factorization done.
* <p>
* @param v Coeficient's complex vector.
* <p>
* @return Solution's complex vector, or null if the operation cannot be done.
*
*/
public ComplexVector solve(ComplexVector v)
{
	if(!_hasQR) return null;
if(v.size() != _r.rows()) return null;
ComplexMatrix q = new ComplexMatrix(v.size(), 1);
for(int i = 0; i < v.size(); i++) q.set(i, 0, v.get(i));
return SysHelper.upperTriangularSystemSolver(SysHelper.systemMatrix(_r, _q.transpose().mul(q).getColumn(0)));
}

/**
* Gets Q complex matrix ( must be orthogonal ).
* <p>
* @return Q complex matrix.
*
*/
public ComplexMatrix getQ()
{
	if(!_hasQR) return null;
return _q;
}

/**
* Gets R complex matrix ( must be triangular superior ).
* <p>
* @return R complex matrix.
*
*/
public ComplexMatrix getR()
{
	if(!_hasQR) return null;
return _r;
}

/**
* Gets the value of the determinant for the factorized complex matrix.
* <p>
* @return determinant, or ComplexNumber.NaN if the operation cannot be done.
*
*/
public ComplexNumber det()
{
	if(!_hasQR) return ComplexNumber.NaN;
ComplexNumber d = new ComplexNumber(1.0f, 0.0f);
for(int i = 0; i < _r.columns(); i++)
{
d = d.mul(_r.get(i, i));
}
return d;
}

/**
* Prints this ComplexQR decomposition to the console.
*
*/
public void print()
{
if(!_hasQR)
{
System.out.println("[]");
return;
}
System.out.println("Q:");
_q.print();
System.out.println();
System.out.println("R:");
_r.print();
System.out.println();
}


private boolean _hasQR;

private ComplexMatrix _q;
private ComplexMatrix _r;

}

// END
