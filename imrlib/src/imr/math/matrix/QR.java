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
* QR.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix;

/**
* The <code>QR</code> class offers functionality to perform QR matrix factorization for square matrices.
* <p>
* The method chosed is Givens Rotations.
* <p>
* After factorization we get:
* <ul>
* <li>Q, an orthogonal matrix.</li>
* <li>R, an upper ( triangular superior ) matrix.</li>
* </ul>
* <p>
* If we apply QR factorization to a matrix A, then we get:
* <p>
* A = QR.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class QR implements MatrixDecomposition
{

/**
* Makes a new instance for a <code>QR</code> object.
*/
public QR()
{
_q = null;
_r = null;
	}

/**
* Performs QR factorization over a square matrix passed as parameter.
* <p>
* @param m A square matrix to be factorized.
* <p>
* @return true if success or false otherwise.
*
*/
public boolean decompose(Matrix m)
{
if(m.rows() != m.columns()) return false; // m must be square
int k, n;
double w;
Matrix q = Matrix.identity(m.rows());
Matrix q1 = null;
Matrix r = (Matrix)m.clone();
n = r.columns();
k = 0; // sentinel
for(int i = 0; i < n-1; i++)
{
for(int j = n-1; j > i; j--)
{
	if(r.get(j, i) == 0.0) continue;
	q1 = Matrix.identity(n);
	w = Math.atan2(-(r.get(j, i)), r.get(i, i));
	q1.set(i, i, Math.cos(w));
	q1.set(i, j, -(Math.sin(w)));
	q1.set(j, i, Math.sin(w));
	q1.set(j, j, Math.cos(w));
	r = Matrix.mul(q1, r);
	q = (k == 0) ? (Matrix)q1.clone() : Matrix.mul(q1, q);
	k++;
}
}
_q = Matrix.transpose(q);
_r = (Matrix)r.clone();
return true;
}

/**
* Solves a linear system having a QR factorization done.
* <p>
* @param v Coeficient's vector.
* <p>
* @return Solution's vector.
*
*/
public Vector solve(Vector v)
{
if(v.size() != _r.rows()) return null;
Matrix q = new Matrix(v.size(), 1);
for(int i = 0; i < v.size(); i++) q.set(i, 0, v.get(i));
return solveUpper(Matrix.transpose(_q).mul(q).getColumnVector(0));
}

/**
* Gets Q matrix ( must be orthogonal ).
* <p>
* @return Q matrix.
*
*/
public Matrix getQ()
{
return _q;
}

/**
* Gets R matrix ( must be triangular superior ).
* <p>
* @return R matrix.
*
*/
public Matrix getR()
{
return _r;
}

/**
* Gets the value of the determinant for the factorized matrix.
* <p>
* @return determinant.
*
*/
public double det()
{
if(_r == null) return 0.0;
double d = 1.0;
for(int i = 0; i < _r.columns(); i++)
{
d *= _r.get(i, i);
}
return d;
}

/**
* Prints this QR decomposition to the console.
*
*/
public void print()
{
if(_q == null)
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


private Vector solveUpper(Vector v)
{
Vector out = new Vector(v.size());
int n = _r.rows();
out.set(n-1,v.get(n-1)/_r.get(n-1,n-1));
for(int i=n-2;i>=0;i--)
{
out.set(i,v.get(i));
for(int j=i+1;j<n;j++)
{
out.set(i,out.get(i)-_r.get(i,j)*out.get(j));
}
out.set(i,out.get(i)/_r.get(i,i));
}
return out;
}


private Matrix _q;
private Matrix _r;
}

// END
