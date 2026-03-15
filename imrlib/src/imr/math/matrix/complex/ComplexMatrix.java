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
* ComplexMatrix.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;

import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* The <code>ComplexMatrix</code> class implements functionallity to operate with matrices of complex number coefficients. <p>
* In addition there is support to storage. <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class ComplexMatrix
{

/**
* Constructor. <p>
* Makes a new instance for a <code>ComplexMatrix</code> object. <p>
* @param rows
* Number of rows for the matrix.
* <p>
* @param columns
* Number of columns for the matrix.
*
*/
	public ComplexMatrix(int rows, int columns)
	{
	_rows = rows;
	_columns = columns;
	assert(_rows > 0 && _columns > 0): "ComplexMatrix -> constructor(int, int): Bad parameter.";
	_cmatrix = new ComplexNumber[_rows][_columns];
for(int i = 0; i < _rows; i++)
{
	for(int j = 0; j < _columns; j++)
	{
		_cmatrix[i][j] = new ComplexNumber(0.0f, 0.0f);
	}
}
	}

/**
* Constructor. <p>
* @param filename
* A string object name for a file where the matrix is stored.
*
*/
public ComplexMatrix(String filename)
{
load(filename);
}

/**
* Prints this complex matrix to the console.
*
*/
public void print()
{
for(int i = 0; i < _rows; i++)
{
	System.out.print("[");
	for(int j = 0; j < _columns; j++)
	{
if(j > 0) System.out.print(", ");
System.out.print(_cmatrix[i][j]);
	}
	System.out.println("]");
}
}

/**
* Loads from a file.
* @param fileName
* a well formatted file.
* <p>
* Fills the matrix according to the information stored in the file passed as parameter. <p>
* The format for such a complex matrix is as follows: <p>
* #rows #columns ( two integer values, one for number of rows and another for number of columns ). <p>
* r00 i00 r01 i01 ... r0n i0n <p>
* r10 i10 r11 i11 ... r1n i1n <p>
* ...
* rn0 in1 ... rnn inn <p>
* where r and i and the real and imaginary part for each complex number. <p>
* Look at the examples to know more about it.
*
*/
public void load(String fileName)
{
	float real, imag;
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(fileName)));
	in.useLocale(Locale.US);
	_rows = in.nextInt();
	_columns = in.nextInt();
	if(_cmatrix != null) _cmatrix = null;
	_cmatrix = new ComplexNumber[_rows][_columns];
	for(int i = 0; i < _rows; i++)
	{
		for(int j = 0; j < _columns; j++)
		{
			real = in.nextFloat();
			imag = in.nextFloat();
			_cmatrix[i][j] = new ComplexNumber(real, imag);
		}
	}
}
catch(FileNotFoundException e)
{
	System.err.printf("ComplexMatrix FileNotFoundException: %s file not found.%n",fileName);
}
finally
{
		if(in != null) in.close();
}
}

/**
* Stores the contents of a matrix into a file.
* @param fileName
* A file name.
*
*/
public void store(String fileName)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(fileName);
	out.printf("%d %d%n", _rows, _columns);
	for(int i = 0; i < _rows; i++)
	{
		for(int j = 0; j < _columns; j++)
		{
		if(j > 0) out.print(" ");
		out.print(_cmatrix[i][j].getReal() + " " + _cmatrix[i][j].getImag());
		}
		out.println();
	}
}
catch(IOException e)
{
	System.err.println("ComplexMatrix IOException: "+e);
}
finally
{
		if(out != null) out.close();
}
}

/**
* Gets the number of rows in this matrix. <p>
* @return Number of rows.
*
*/
public int rows()
{
return _rows;
}

/**
* Gets the number of columns in this matrix. <p>
* @return Number of columns.
*
*/
public int columns()
{
	return _columns;
}

/**
* Gets a value from this matrix. <p>
* @param i
* ith row from to get the value.
* <p>
* @param j
* jth column from to get the value.
* <p>
* @return M(i, j) value.
*
*/
public ComplexNumber get(int i, int j)
{
assert(i >= 0 && i < _rows): "ComplexMatrix -> get(int, int): Bad parameter.";
assert(j >= 0 && j < _columns): "ComplexMatrix -> get(int, int): Bad parameter.";
return _cmatrix[i][j];
}

/**
* Sets a value to this matrix. <p>
* @param i
* row index.
* <p>
* @param j
* column index.
* <p>
* @param value
* Value to set.
*
*/
public void set(int i, int j, ComplexNumber value)
{
assert(i >= 0 && i < _rows): "ComplexMatrix -> set(int, int, double): Bad parameter.";
assert(j >= 0 && j < _columns): "ComplexMatrix -> set(int, int, double): Bad parameter.";
	_cmatrix[i][j] = value;
}

/**
* Adds the matrix passed as parameter to this one. <p>
* @param m
* A complex matrix object.
* <p>
* @return this + m
*
*/
public ComplexMatrix add(ComplexMatrix m)
{
return add(this, m);
}

/**
* Substracts the matrix passed as parameter to this one. <p>
* @param m
* A complex matrix object.
* <p>
* @return this - m
*
*/
public ComplexMatrix sub(ComplexMatrix m)
{
	return sub(this, m);
}

/**
* Multiplies this complex matrix by the one passed as parameter. <p>
* @param m
* A complex matrix object.
*  <p>
* @return this * m
*
*/
public ComplexMatrix mul(ComplexMatrix m)
{
return mul(this, m);
}

/**
* Scales this matrix by the complex number passed as parameter. <p>
* @param factor
* A complex number to scale this matrix.
* <p>
* @return this scaled by factor.
*
*/
public ComplexMatrix scale(ComplexNumber factor)
{
return scale(this, factor);
}

/**
* Computes the trace for this complex matrix. <p>
* @return trace of this complex matrix.
*
*/
public ComplexNumber trace()
{
return trace(this);
}

/**
* Computes the determinant of this complex matrix. <p>
* @return Determinant of this complex matrix.
*
*/
public ComplexNumber det()
{
	return det(this);
}

/**
* Gets the transpose of this complex matrix. <p>
* @return Transposed complex matrix.
*
*/
public ComplexMatrix transpose()
{
return transpose(this);
}

/**
* Gets the kth-qth minor complex matrix of this complex matrix ( this complex matrix must be square ).
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor complex matrix or null if the operation cannot be done.
*
*/
public ComplexMatrix minorMatrix(int k, int q)
{
return minorMatrix(this, k, q);
}

/**
* Gets the kth-qth minor of this complex matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor or ComplexNumber.NaN if the operation cannot be done.
*
*/
public ComplexNumber minor(int k, int q)
{
	ComplexMatrix m = this.minorMatrix(k, q);
	return (m == null) ? ComplexNumber.NaN : m.det();
}

/**
* Gets the kth first ( principal ) minor complex matrix of this complex matrix ( this complex matrix must be square ).
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor complex matrix or null if the operation cannot be done.
*
*/
public ComplexMatrix firstMinorMatrix(int k)
{
return firstMinorMatrix(this, k);
}

/**
* Gets the kth first ( principal ) minor of this complex matrix ( this complex matrix must be square ).
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor or ComplexNumber.NaN if the operation cannot be done.
*
*/
public ComplexNumber firstMinor(int k)
{
ComplexMatrix m = firstMinorMatrix(this, k);
return (m == null) ? ComplexNumber.NaN : m.det();
}

/**
* Method to compute the cofactor complex matrix for this complex matrix ( the complex matrix must be square ).
* <p>
* @return cofactor complex matrix or null if the operation cannot be done.
*
*/
public ComplexMatrix cofactorMatrix()
{
	return cofactorMatrix(this);
}

/**
* Method to invert this complex matrix using cofactors ( this complex matrix must be square ).
* <p>
* @return inverse complex matrix or null if the operation cannot be done.
*
*/
public ComplexMatrix invertMatrix()
{
	return invertMatrix(this);
}

/**
* Computes the determinant of this complex matrix using cofactors.
* <p>
* Since there is already a method named 'det' to compute determinants in another way, <p>
* we decided call this one 'determinant' <p>
* @return determinant of this complex matrix, or ComplexNumber.NaN if the operation cannot be done.
*
*/
public ComplexNumber determinant()
{
return determinant(this);
}

/**
* Gets the ith row of this complex matrix as a complex vector. <p>
* @param i
* ith row index.
* <p>
* @return ith complex vector from this ith row complex matrix or null if the operation cannot be done.
*
*/
public ComplexVector getRow(int i)
{
return getRow(this, i);
}

/**
* Gets the jth column from this complex matrix as a complex vector. <p>
* @param j
* jth column index.
* <p>
* @return jth column as a complex vector or null if the operation cannot be done.
*
*/
public ComplexVector getColumn(int j)
{
	return getColumn(this, j);
}

/**
* Sets the ith row of this complex matrix according to the complex vector passed as parameter. <p>
* @param i
* ith row index to set.
* <p>
* @param v
* A complex vector object.
* <p>
* @return Updated complex matrix.
*
*/
public ComplexMatrix setRow(int i, ComplexVector v)
{
return setRow(this, i, v);
}

/**
* Sets the jth column of this complex matrix according to the complex vector passed as parameter. <p>
* @param j
* jth column index to set.
* <p>
* @param v
* A complex vector object.
* <p>
* @return Updated complex matrix.
*
*/
public ComplexMatrix setColumn(int j, ComplexVector v)
{
	return setColumn(this, j, v);
}

/**
* Checks whether this complex matrix is orthogonal. <p>
* @return true if orthogonal or false otherwise
*
*/
public boolean isOrthogonal()
{
return isOrthogonal(this);
}

/**
* Checks whether this complex matrix is orthonormal. <p>
* @return true if orthonormal or false otherwise.
*
*/
public boolean isOrthonormal()
{
return isOrthonormal(this);
}

/**
* Evaluates for symmetry. <p>
* @return true if this complex matrix is symmetric or false otherwise.
*
*/
public boolean isSymmetric()
{
	return isSymmetric(this);
}

/**
* Evaluates whether this complex matrix is Hermitian. <p>
* @return true if Hermitian or false otherwise.
*
*/
public boolean isHermitian()
{
	return isHermitian(this);
}

/**
* Computes the rank of this complex matrix. <p>
* @return rank of this matrix or -1 if the operation cannot be done.
*
*/
public int rank()
{
return rank(this);
}

/**
* Evaluates whether this complex matrix is fully rank.
* <p>
* @return true if fully rank or false otherwise.
*
*/
public boolean isFullyRank()
{
return isFullyRank(this);
}

/**
* Gets the inverse of this complex matrix. <p>
* @return Inverse of this complex matrix or null if the operation cannot be done.
*
*/
public ComplexMatrix inverse()
{
	return inverse(this);
}

/**
* Divides this complex matrix by the one passed as parameter. <p>
* @param m
* A complex matrix object.
* <p>
* @return this / m or null if the operation cannot be done.
*
*/
public ComplexMatrix div(ComplexMatrix m)
{
return div(this, m);
}

/**
* Raises this square complex matrix to the integer power passed as parameter. <p>
* The power can be negative, positive or zero. <p>
* @param n
* An integer value.
* <p>
* @return this^n or null if the operation cannot be done.
*
*/
public ComplexMatrix pow(int n)
{
return pow(this, n);
}


// Static methods

/**
* Static method to add two matrices. <p>
* @param m1
* A complex matrix object.
* <p>
* @param m2
* A complex matrix object.
* <p>
* @return m1 + m2
*
*/
public static ComplexMatrix add(ComplexMatrix m1, ComplexMatrix m2)
{
	int m = m1.rows();
	int n = m1.columns();
assert(m == m2.rows() && n == m2.columns()): "ComplexMatrix -> add(m1, m2): Bad parameter.";
ComplexMatrix out = new ComplexMatrix(m, n);
for(int i = 0; i < m; i++)
{
	for(int j = 0; j < n; j++)
	{
		out.set(i, j, m1.get(i, j).add(m2.get(i, j)));
	}
}
return out;
}

/**
* Static method to substract two matrices. <p>
* @param m1
* A complex matrix object.
* <p>
* @param m2
* A complex matrix object.
* <p>
* @return m1 - m2
*
*/
public static ComplexMatrix sub(ComplexMatrix m1, ComplexMatrix m2)
{
	int m = m1.rows();
	int n = m1.columns();
assert(m == m2.rows() && n == m2.columns()): "ComplexMatrix -> add(m1, m2): Bad parameter.";
ComplexMatrix out = new ComplexMatrix(m, n);
for(int i = 0; i < m; i++)
{
	for(int j = 0; j < n; j++)
	{
		out.set(i, j, m1.get(i, j).sub(m2.get(i, j)));
	}
}
return out;
}

/**
* Static method to multiply two matrices. <p>
* @param m1
* A complex matrix object.
* <p>
* @param m2
* A complex matrix object.
* <p>
* @return m1 * m2
*
*/
public static ComplexMatrix mul(ComplexMatrix m1, ComplexMatrix m2)
{
if(m1.columns() != m2.rows()) return null;
ComplexMatrix out = new ComplexMatrix(m1.rows(),m2.columns());
ComplexNumber accum;
for(int i=0;i<out.rows();i++)
{
for(int j=0;j<out.columns();j++)
{
	accum = new ComplexNumber(0.0f, 0.0f);
	for(int k=0;k<m1.columns();k++)
	{
		accum = accum.add(m1.get(i,k).mul(m2.get(k,j)));
	}
	out.set(i,j,accum);
}
}
return out;
}

/**
* Static method to scale the matrix passed as first parameter by the complex number passed as second parameter. <p>
* @param m
* A complex matrix object.
* <p>
* @param factor
* A complex number to scale the complex matrix.
* <p>
* @return Scaled complex matrix.
*
*/
public static ComplexMatrix scale(ComplexMatrix m, ComplexNumber factor)
{
assert(m != null): "ComplexMatrix -> scale(ComplexMatrix, ComplexNumber): Bad parameter.";
int r = m.rows();
int c = m.columns();
ComplexMatrix out = new ComplexMatrix(r, c);
for(int i = 0; i < r; i++)
{
for(int j = 0; j < c; j++)
{
	out.set(i, j, m.get(i, j).mul(factor));
}
}
return out;
}

/**
* Static method to build an identity complex matrix. <p>
* @param n
* Order for the complex identity matrix.
* <p>
* @return Identity complex matrix of order n.
*
*/
public static ComplexMatrix identity(int n)
{
assert(n > 0): "ComplexMatrix -> identity(int): Bad parameter.";
ComplexMatrix out = new ComplexMatrix(n, n);
for(int i = 0; i < n; i++)
{
	out.set(i, i, new ComplexNumber(1.0f, 0.0f));
}
return out;
}

/**
* Static method to compute the trace of the complex matrix passed as parameter. <p>
* @param m
* A complex matrix object.
* <p>
* @return trace of the complex matrix passed as parameter.
*
*/
public static ComplexNumber trace(ComplexMatrix m)
{
int n = m.rows();
if(m == null || m.columns() != n) return ComplexNumber.NaN;
ComplexNumber tr = new ComplexNumber(0.0f, 0.0f);
for(int i = 0; i < n; i++)
{
tr = tr.add(m.get(i, i));
}
return tr;
}

/**
* Static method to compute the determinant of the complex matrix passed as parameter. <p>
* @param m
* A complex matrix object.
* <p>
* @return Determinant of the complex matrix passed as parameter.
*
*/
public static ComplexNumber det(ComplexMatrix m)
{
int n = m.rows();
if(m.columns() != n) return ComplexNumber.NaN;
return det(m, n);
}

/**
* Static method to get the transpose of the complex matrix passed as parameter. B = A^T. <p>
* @param m a matrix to get its transpose.
* <p>
* @return the transposed complex matrix.
*
*/
public static ComplexMatrix transpose(ComplexMatrix m)
{
	if(m == null) return null;
	int r = m.rows();
	int c = m.columns();
ComplexMatrix t = new ComplexMatrix(c,r);
for(int i=0;i<c;i++)
{
for(int j=0;j<r;j++)
{
	t.set(i,j, m.get(j,i));
}
}
return t;
}

/**
* Static method to get the kth-qth minor complex matrix of a square complex matrix.
* <p>
* @param m
* A square complex matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor complex matrix of the complex matrix passed as parameter or null if the operation cannot be done.
*
*/
public static ComplexMatrix minorMatrix(ComplexMatrix m, int k, int q)
{
if(m == null) return null;
int r = m.rows();
int c = m.columns();
if(r != c) return null; // m must be square
if(k < 0 || k > r-1) return null; // k is out of range
if(q < 0 || q > r-1) return null; // q is out of range
int order = r;
if(order < 2) return null; // order must be >= 2
ComplexMatrix minor = new ComplexMatrix(order-1, order-1); // create minor complex matrix
int ai, aj, j;
int n = order;
for(int t=0;t<n;t++)
{
ai=0;
for(int i=0;i<n;i++)
{
aj=0;
for(j=0;j<n;j++)
if(i != k && j!= q)
{
minor.set(ai,aj,m.get(i,j));
aj++;
}
if(i != k && j != q) ai++;
}
}
return minor;
}

/**
* Static method to get the kth-qth minor of a square complex matrix.
* <p>
* @param m
* A square complex matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor of the complex matrix passed as parameter or ComplexNumber.NaN if the operation cannot be done.
*
*/
public static ComplexNumber minor(ComplexMatrix m, int k, int q)
{
ComplexMatrix _m = minorMatrix(m, k, q);
return (_m == null) ? ComplexNumber.NaN : _m.det();
}

/**
* Static method to get the kth first ( principal ) minor complex matrix of a square complex matrix.
* <p>
* @param m
* A square complex matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor complex matrix of the complex matrix passed as parameter or null if the operation cannot be done.
*
*/
public static ComplexMatrix firstMinorMatrix(ComplexMatrix m, int k)
{
return minorMatrix(m, k, k);
}

/**
* Static method to get the kth first ( principal ) minor of a square complex matrix.
* <p>
* @param m
* A square complex matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor of the complex matrix passed as parameter or ComplexNumber.NaN if the operation cannot be done.
*
*/
public static ComplexNumber firstMinor(ComplexMatrix m, int k)
{
ComplexMatrix _m = firstMinorMatrix(m, k);
return (_m == null) ? ComplexNumber.NaN : _m.det();
}

/**
* Static method to compute the cofactor complex matrix for the square complex matrix passed as parameter.
* <p>
* @param m
* A square complex matrix.
* <p>
* @return cofactor complex matrix or null if the operation cannot be done.
*
*/
public static ComplexMatrix cofactorMatrix(ComplexMatrix m)
{
if(m == null) return null;
int n = m.rows();
if(n != m.columns()) return null; // m must be square
ComplexMatrix out = new ComplexMatrix(n, n);
float sign;
for(int i = 0; i < n; i++)
{
for(int j = 0; j < n; j++)
{
	sign = (((i+j)%2) == 0) ? 1.0f : -1.0f;
	out.set(i, j, m.minor(i, j).scale(sign));
}
}
return out;
}

/**
* Static method to compute the inverse of a square complex matrix using cofactors .
* <p>
* @param m
* A square complex matrix.
* @return inverse complex matrix or null if the operation cannot be done.
*
*/
public static ComplexMatrix invertMatrix(ComplexMatrix m)
{
if(m == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
ComplexNumber d = m.det();
if(d.magnitude() < SysHelper.THRESHOLD) return null; // det(m) is too close to zero.
ComplexMatrix _m = m.cofactorMatrix();
return _m.transpose().scale(d.reciprocal());
}

/**
* This static method computes the determinant of the complex matrix passed as parameter using cofactors.
* <p>
* Since there is already a method named 'det' to compute determinants in another way, <p>
* we decided call this one 'determinant' <p>
* @param m
* A square complex matrix.
* <p>
* @return determinant of the complex matrix passed as parameter or ComplexNumber.NaN if the operation cannot be done.
*
*/
public static ComplexNumber determinant(ComplexMatrix m)
{
if(m == null) return ComplexNumber.NaN;
if(m.rows() != m.columns()) return ComplexNumber.NaN; // m must be square
float sign = 1.0f;
ComplexNumber d = new ComplexNumber(0.0f, 0.0f);
int n = m.rows(); // order of m
for(int j = 0; j < n; j++)
{
	d = d.add(m.minor(0, j).scale(sign).mul(m.get(0, j)));
	sign *= -1.0f;
}
return d;
}

/**
* Static method to get the ith row from the complex matrix passed as parameter as a complex vector. <p>
* @param m
* A complex matrix object.
* <p>
* @param i
* Row index.
* <p>
* @return ith row as a complex vector or null if the operation cannot be done.
*
*/
public static ComplexVector getRow(ComplexMatrix m, int i)
{
if(m == null) return null;
if(i < 0 || i > m.rows()-1) return null; // ith index out of range.
int n = m.columns();
ComplexVector out = new ComplexVector(n);
for(int j = 0; j < n; j++)
{
out.set(j, (ComplexNumber)m.get(i, j).clone());
}
return out;
}

/**
* Static method to get the jth column of the complex matrix passed as parameter as a complex vector. <p>
* @param m
* A complex matrix object.
* <p>
* @param j
* jth index.
* <p>
* @return jth column as a complex vector or null if the operation cannot be done.
*
*/
public static ComplexVector getColumn(ComplexMatrix m, int j)
{
if(m == null) return null;
if(j < 0 || j > m.columns()-1) return null; // jth index out of range
int n = m.rows();
ComplexVector  out = new ComplexVector(n);
for(int i = 0; i < n; i++)
{
out.set(i, (ComplexNumber)m.get(i, j).clone());
}
return out;
}

/**
* Static method to set the ith row of the complex matrix passed as parameter from a complex vector. <p>
* @param m
* A complex matrix object.
* <p>
* @param i
* ith row index.
* <p>
* @param v
* A complex vector object.
* <p>
* @return Resulting complex matrix after doing or null if the operation cannot be done.
*
*/
public static ComplexMatrix setRow(ComplexMatrix m, int i, ComplexVector v)
{
if(m == null || v == null) return null;
if(i < 0 || i > m.rows()-1) return null; // ith row out of range
if(v.size() != m.columns()) return null; // bad vector size
ComplexMatrix out = (ComplexMatrix)m.clone();
int n = out.columns();
for(int j = 0; j < n; j++)
{
out.set(i, j, (ComplexNumber)v.get(j).clone());
}
return out;
}

/**
* Static method to set the jth column of the complex matrix passed as parameter from a complex vector. <p>
* @param m
* A complex matrix object.
* <p>
* @param j
* jth column index.
* <p>
* @param v
* A complex vector object.
* <p>
* @return Resulting complex matrix after doing or null if the operation cannot be done.
*
*/
public static ComplexMatrix setColumn(ComplexMatrix m, int j, ComplexVector v)
{
if(m == null || v == null) return null;
if(j < 0 || j > m.columns()-1) return null; // jth index out of range
if(v.size() != m.rows()) return null; // bad vector size
ComplexMatrix out = (ComplexMatrix)m.clone();
int n = out.rows();
for(int i = 0; i < n; i++)
{
out.set(i, j, (ComplexNumber)v.get(i).clone());
}
return out;
}

/**
* Static method to check whether the complex matrix passed as parameter is orthogonal. <p>
* @param m
* A complex matrix object.
* <p>
* @return true if orthogonal or false otherwise
*
*/
public static boolean isOrthogonal(ComplexMatrix m)
{
if(m == null) return false;
if(m.rows() != m.columns()) return false; // m must be square
ComplexVector v = null;
int n = m.columns();
for(int i = 0; i < n-1; i++)
{
v = m.getColumn(i);
for(int j = i+1; j < n; j++)
{
	if(v.dot(m.getColumn(j)).magnitude() > __ORTHOGONAL__ ) return false;
}
}
return true;
}

/**
* Static method to check whether the complex matrix passed as parameter is orthonormal. <p>
* @param m
* A complex matrix.
* <p>
* @return true if orthonormal or false otherwise
*
*/
public static boolean isOrthonormal(ComplexMatrix m)
{
	if(!m.isOrthogonal()) return false; // m must be orthogonal
	int n = m.columns();
	for(int i = 0; i < n; i++)
	{
	if(m.getColumn(i).module().magnitude() != 1.0f) return false;
	}
	return true;
}

/**
* Static method to evaluate whether the complex square matrix passed as parameter is symmetric. <p>
* @param m
* A square complex matrix.
* <p>
* @return true if symmetric or false otherwise.
*
*/
public static boolean isSymmetric(ComplexMatrix m)
{
if(m == null) return false;
if(m.rows() != m.columns()) return false; // m must be square
int n = m.rows();
for(int i = 0; i < n; i++)
{
	for(int j = i+1; j < n; j++)
	{
		if(!m.get(i, j).equals(m.get(j, i))) return false;
	}
}
return true;
}

/**
* Static method to evaluate whether the complex matrix passed as parameter is Hermitian. <p>
* @param m
* A square complex matrix.
* <p>
* @return true if Hermitian or false otherwise.
*
*/
public static boolean isHermitian(ComplexMatrix m)
{
if(m == null) return false;
if(m.rows() != m.columns()) return false; // m must be square
int n = m.rows();
for(int i = 0; i < n; i++)
{
	for(int j = i+1; j < n; j++)
	{
		if(!m.get(i, j).equals(m.get(j, i).conjugated())) return false;
	}
}
return true;
}

/**
* Computes the rank of the complex matrix passed as parameter. <p>
* @param m
* A complex matrix.
* <p>
* @return rank of the matrix or -1 if the operation cannot be done.
*
*/
public static int rank(ComplexMatrix m)
{
if(m == null) return -1;
ComplexMatrix a = (m.rows() > m.columns()) ? m.transpose() : (ComplexMatrix)m.clone();
a = SysHelper.gaussianElimination(a, false);
int n = a.rows();
int _rank = 0;
for(int i = 0; i < n; i++)
{
if(a.get(i, i).magnitude() > SysHelper.THRESHOLD) _rank++;
}
return _rank;
}

/**
* Static method to evaluate whether the complex matrix passed as parameter is fully rank. <p>
* @param m
* A complex matrix.
* <p>
* @return true if fully rank or false otherwise.
*
*/
public static boolean isFullyRank(ComplexMatrix m)
{
int rank = rank(m);
if(rank == -1) return false;
return (rank == Math.min(m.rows(), m.columns()));
}


/**
* static method to compute the inverse of the square complex matrix passed as parameter. <p>
* This method uses complex LU decomposition to achieve the goal. <p>
* @param m
* A complex square matrix.
* <p>
* @return Inverse of the complex matrix passed as parameter or null if the operation cannot be done.
*
*/
public static ComplexMatrix inverse(ComplexMatrix m)
{
if(m == null) return null;
int n = m.rows();
if(n != m.columns()) return null; // m must be square
ComplexLU clu = new ComplexLU();
boolean flag = clu.decompose(m); // flag checks for singularity
if(!flag) return null; // fat chance
ComplexMatrix out = new ComplexMatrix(n, n);
ComplexVector v = new ComplexVector(n);
for(int i = 0; i < n; i++)
{
	v.set(i, new ComplexNumber(1.0f, 0.0f));
	out = out.setColumn(i, clu.solve(v));
	v.set(i, new ComplexNumber(0.0f, 0.0f));
}
return out;
}

/**
* Static method to divide two square complex matrices. <p>
* @param m1
* A square complex matrix.
* <p>
* @param m2
* A square complex matrix.
* <p>
* @return m1 / m2 or null if the operation cannot be done.
*
*/
public static ComplexMatrix div(ComplexMatrix m1, ComplexMatrix m2)
{
if(m1 == null || m2 == null) return null;
if(m1.rows() != m1.columns() || m2.rows() != m2.columns()) return null;
if(m1.rows() != m2.rows()) return null;
if(m2.det().magnitude() < SysHelper.THRESHOLD) return null; // det m2 is too close to zero.
return m1.mul(m2.inverse());
}

/**
* Static method to raise the square complex matrix passed as first parameter by the integer power passed as second parameter. <p>
* @param m
* A square complex matrix.
* <p>
* @param k
* An integer value.
* <p>
* The integer power can be negative, positive or zero.
* <p>
* @return m^k
*
*/
public static ComplexMatrix pow(ComplexMatrix m, int k)
{
if(m == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
if(k == 0) return ComplexMatrix.identity(m.rows());
ComplexMatrix _m = (ComplexMatrix)m.clone();
int q = Math.abs(k);
for(int j = 1; j < q; j++)
{
_m = _m.mul(m);
}
return (k < 0) ? _m.inverse() : _m;
}


// Some methods overrided from Object.

/**
* Makes a clone of a complex matrix. <p>
* @return the cloned matrix.
* <p>
* You must cast to <code>ComplexMatrix</code>.
* <p>
* <code>
* ComplexMatrix clonedMatrix = (ComplexMatrix)m.clone();
* </code>
*
*/
public Object clone()
{
	ComplexMatrix m = new ComplexMatrix(_rows,_columns);
	for(int i= 0;i < _rows; i++)
	{
	for(int j = 0; j < _columns; j++)
	{
		m.set(i,j, _cmatrix[i][j]);
	}
	}
	return m;
}

/**
* Evaluates for equality. <p>
* @param m
* A complex matrix.
* <p>
* @return true if this complex matrix is equal to the one passed as parameter or false otherwise.
*
*/
public boolean equals(ComplexMatrix m)
{
if(_rows != m.rows() || _columns != m.columns()) return false; // both matrices must be the same dimension
boolean retval = true;
for(int i = 0; i < _rows; i++)
{
for(int j = 0; j < _columns; j++)
{
	if(!_cmatrix[i][j].equals(m.get(i, j)))
	{
		retval = false;
		break;
	}
}
}
return retval;
}

/**
* Gets a string representation of this complex matrix. <p>
* @return A string representation of this complex matrix.
*
*/
public String toString()
{
String s = "";
for(int i = 0; i < _rows; i++)
{
	s += "[";
	for(int j = 0; j < _columns; j++)
	{
		if(j > 0) s += ", ";
		s += _cmatrix[i][j];
	}
	s += "]\r\n";
}
return s;
}


// Private helper method to compute determinant recursively.
private static ComplexNumber det(ComplexMatrix m, int n)
{
ComplexNumber d=new ComplexNumber(0.0f, 0.0f);
float sign=1.0f;
int ai,aj;
if(n==1)
{
return m.get(0,0);
}
else
{
ComplexMatrix adj = new ComplexMatrix(n-1, n-1);
for(int k=0;k<n;k++)
{
ai=0;
for(int i=0;i<n;i++)
{
aj=0;
for(int j=1;j<n;j++)
if(i != k)
{
adj.set(ai,aj,m.get(i,j));
aj++;
}
if(i != k) ai++;
}
d = d.add(m.get(k,0).mul(det(adj, n-1)).scale(sign));
sign*= -1.0f;
}
}
return d;
}


{
_rows = 0;
_columns = 0;
_cmatrix = null;
}

private int _rows;
private int _columns;
private ComplexNumber[][] _cmatrix;

private static final float __ORTHOGONAL__ = 1E-3f;
}

// END
