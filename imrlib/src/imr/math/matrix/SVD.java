/*
 * Copyright (c) 2025 Ismael Mosquera Rivera
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
* SVD.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix;

/**
* The <code>SVD</code> provides functionallity to perform Singular Value Decomposition for MxN matrices.
* <p>
* A SVD is as follows:
* <p>
* M = UDV^t
* <p>
* where
* <ul>
* <li>U is an orthogonal matrix having the left singular vectors of M.</li>
* <li>Sigma is a diagonal matrix having the singular values of M.</li>
* <li>V is an orthogonal matrix having the right singular vectors of M.</li>
* </ul>
* <p>
* @author Ismael Mosquera Rivera
*
*/
public final class SVD implements MatrixDecomposition
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>SVD</code> object.
*/
public SVD()
{
_u = null;
_sigma = null;
_v = null;
}

/**
* This method performs Singular Value Decomposition.
* <p>
* @param m Matrix to decompose.
* <p>
* You'd better check if the product VDV^t equals M; if not, it means that the factorization cannot be done properly for the matrix M.
* <p>
* @return <code>true</code> if done or <code>false</code> otherwise.
*
*/
public boolean decompose(Matrix m)
{
	if(m == null) return false;
int n;
 _u = new Matrix(m.rows(), m.rows());
_sigma = new Matrix(m.rows(), m.columns());
_v = new Matrix(m.columns(), m.columns());
// compute left and right eigen
Eigen[] left = EigenFinder.findEigenSystem(Matrix.mul(m, Matrix.transpose(m)));
Eigen[] right = EigenFinder.findEigenSystem(Matrix.mul(Matrix.transpose(m), m));
// compute UDV
n = left.length;
for(int i = 0; i < n; i++)
{
_u = setColumn(_u, Vector.normalize(left[i].vector()), i);
}
n = right.length;
for(int i = 0; i < n; i++)
{
_v = setColumn(_v, Vector.normalize(right[i].vector()), i);
}
int min = getMin(m.rows(), m.columns());
if(min == LEFT_SIDE)
{
	n = left.length;
	for(int i = 0; i < n; i++)
	{
_sigma.set(i, i, Math.sqrt(left[i].value()));
}
}
else
{
	n = right.length;
		for(int i = 0; i < n; i++)
		{
	_sigma.set(i, i, Math.sqrt(right[i].value()));
	}
}
return true;
}

/**
* Prints SVD to the console.
*
*/
public void print()
{
if(_u == null)
{
System.out.println("[]");
return;
}
System.out.println("U:");
_u.print();
System.out.println();
System.out.println("sigma:");
_sigma.print();
System.out.println();
System.out.println("V:");
_v.print();
System.out.println();
}

/**
* Gets the <code>U</code> matrix.
* <p>
* @return U matrix.
*
*/
public Matrix getU()
{
return _u;
}

/**
* Gets the <code>Sigma</code> diagonal matrix.
* <p>
* @return Sigma matrix.
*
*/
public Matrix getSigma()
{
return _sigma;
}

/**
* Gets the <code>V</code> matrix.
* <p>
* @return V matrix.
*
*/
public Matrix getV()
{
return _v;
}


private int getMin(int m, int n)
{
return (m <= n) ? LEFT_SIDE : RIGHT_SIDE;
}

private Matrix setColumn(Matrix m, Vector v, int j)
{
	int n = m.rows();
	Matrix out = (Matrix)m.clone();
	for(int i = 0; i < n; i++)
	{
		out.set(i, j, v.get(i));
	}
	return out;
}


private Matrix _u;
private Matrix _sigma;
private Matrix _v;

private static final int LEFT_SIDE = 1;
private static final int RIGHT_SIDE = 0;
}

// END
