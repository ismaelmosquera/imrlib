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
* Matrix.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.matrix;

import imr.util.iArray;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* The <code>Matrix</code> class defines methods to perform the most common operations applied to matrices.
* In addition, you can print the content of a matrix, load a matrix from a file
* and store the content of a matrix into a file.
* @author Ismael Mosquera Rivera
*/
public final class Matrix
{

/**
* Makes a new instance of a <code>Matrix</code> object
* and initializes it to have r rows and c columns.
* @param r number of rows.
* @param c number of columns.
* All the positions in the matrix are set to zero.
*/
public Matrix(int r,int c)
{
rows = r;
cols = c;
if(rows <1) rows = 1;
if(cols < 1) cols = 1;
matrix = new double[rows][cols];
for(int i=0;i<rows;i++)
{
	for(int j=0;j<cols;j++)
	{
		matrix[i][j] = 0.0;
	}
}
}

/**
* Makes a new instance of a <code>Matrix</code> object.
* @param fileName a well formatted file containing information to load.
* The matrix is initialized according to the content of the file.
* <p>
* The file has to have the following format:
*
*
* <code>
#rows #columns
c0,0 c0,1 c0,2...c0,n
c1,0 c1,1 c1,2...c1,n
c2,0 c2,1 c2,2...c2,n
.
.
.
cm,0 cm,1 cm,2...cm,n
* </code>
* Example.
* <p>
* File formatted to store a 3x3 matrix:
* <code>
* 3 3
* 1 0 2
* 5 3 7
* 4 1 1
* </code>
*
*/
public Matrix(String fileName)
{
load(fileName);
}

/**
* Shows the contents of a matrix.
*/
public void print()
{
	NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
	formatter.setMinimumFractionDigits(2);
	formatter.setMaximumFractionDigits(2);
for(int i=0;i<rows;i++)
{
System.out.print("[");
for(int j=0;j<cols;j++)
{
	if(j>0) System.out.print(", ");
	System.out.print(formatter.format(matrix[i][j]));
}
System.out.print("]");
System.out.println();
}
}

/**
* Loads from a file.
* @param fileName a well formatted file.
* Fills the matrix according to the information stored in the file passed as parameter.
* <p>
* To know how such a file is formatted see the constructor's section.
*/
public void load(String fileName)
{
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(fileName)));
	in.useLocale(Locale.US);
	rows = in.nextInt();
	cols = in.nextInt();
	if(matrix == null)
	{
	matrix = new double[rows][cols];
	}
	else
	{
		resize(rows,cols);
	}
	for(int i=0;i<rows;i++)
	{
		for(int j=0;j<cols;j++)
		{
			matrix[i][j] = in.nextDouble();
		}
	}
}
catch(FileNotFoundException e)
{
	System.err.printf("Matrix FileNotFoundException: %s file not found.%n",fileName);
}
finally
{
		if(in != null) in.close();
}
}

/**
* Stores the contents of a matrix into a file.
* @param fileName a file name.
* To know how such a file is formatted see the constructor's section.
*/
public void store(String fileName)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(fileName);
	out.printf("%d %d%n",rows,cols);
	for(int i=0;i<rows;i++)
	{
		for(int j=0;j<cols;j++)
		{
		if(j>0) out.print(" ");
		out.print(matrix[i][j]);
		}
		out.println();
	}
}
catch(IOException e)
{
	System.err.println("Matrix IOException: "+e);
}
finally
{
		if(out != null) out.close();
}
}

/**
* Returns the number of rows in the matrix.
* @return number of rows.
*/
public int rows()
{
return rows;
}

/**
* Returns the number of columns in the matrix.
* @return the number of columns.
*/
public int columns()
{
return cols;
}

/**
* Gets a value from the matrix.
* @param i row index.
* @param j colum index.
* @return the value stored in M(i,j).
*/
public double get(int i,int j)
{
return matrix[i][j];
}

/**
* stores a value into a matrix.
* @param i row index.
* @param j column index.
* @param value  a value to store.
*/
public void set(int i,int j,double value)
{
matrix[i][j] = value;
}

/**
* Gets a chunk from a matrix.
* @param fromRow first row.
* @param toRow last row.
* @param fromCol first column.
* @param toCol last column.
* @return a matrix containing the requested chunk as content.
* <p>
* Example:
* <p>
* If you hav a matrix A, and want to get a chunk from row 0 to row 1 and from column 0 to column 1.
* <p>
* <code>
*  Matrix chunk = A.get(0,1,0,1);
* </code>
*/
public Matrix get(int fromRow,int toRow,int fromCol,int toCol)
{
if(fromRow < 0 || toRow > rows-1 || fromRow > toRow ||
fromCol <0 || toCol > cols-1 || fromCol > toCol) return null;
int newRows = (toRow-fromRow)+1;
int newCols = (toCol-fromCol)+1;
Matrix r = new Matrix(newRows,newCols);
double[][] m = (double[][])iArray.get(this.matrix,fromRow,toRow,fromCol,toCol);
for(int i=0;i <newRows;i++)
{
for(int j=0;j <newCols;j++)
{
r.set(i,j,m[i][j]);
}
}
return r;
}

/**
* Resizes the matrix to the number of rows and columns passed as parameter.
* @param r number of rows.
* @param c number of columns.
*/
public void resize(int r,int c)
{
rows = r;
cols = c;
matrix = (double[][])iArray.resize(matrix,rows,cols);
}

/**
* Resizes only the rows of a matrix.
* @param r number of rows.
*/
public void resizeRows(int r)
{
rows = r;
matrix = (double[][])iArray.resize(matrix,rows,cols);
}

/**
* Resizes only the columns of a matrix.
* @param c number of columns.
*/
public void resizeColumns(int c)
{
cols = c;
matrix = (double[][])iArray.resize(matrix,rows,cols);
}

/**
* Evaluates if a matrix is equal as the one passed as parameter.
* @param m a matrix to be compared.
* @return <code>true</code> if equal or <code>false</code> otherwise.
*/
public boolean equals(Matrix m)
{
	if(rows != m.rows() || cols != m.columns()) return false;
	for(int i=0;i<rows;i++)
	{
	for(int j=0;j<cols;j++)
	{
		if(matrix[i][j] != m.get(i,j)) return false;
	}
	}
	return true;
}

/**
* Makes a clone of a matrix.
* @return the cloned matrix.
* You must cast to <code>Matrix</code>.
* <p>
* <code>
* Matrix clonedMatrix = (Matrix)m.clone();
* </code>
*/
public Object clone()
{
	Matrix m = new Matrix(rows,cols);
	for(int i=0;i<rows;i++)
	{
	for(int j=0;j<cols;j++)
	{
		m.set(i,j,matrix[i][j]);
	}
	}
	return m;
}

/**
* Returns a string representation of a matrix.
* @return a string representation for a matrix.
*/
public String toString()
{
	NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
	formatter.setMinimumFractionDigits(2);
	formatter.setMaximumFractionDigits(2);
String s = "[Matrix: rows="+rows+", columns="+cols+"]\n";
for(int i=0;i<rows;i++)
{
s += "[";
for(int j=0;j<cols;j++)
{
	if(j>0) s += ", ";
	s += formatter.format(matrix[i][j]);
}
s += "]\n";
}
return s;
}

/**
* Transposes a matrix. A = A^T.
* <p>
* @return Transposed matrix.
*
*/
public Matrix transpose()
{
	return transpose(this);
}

/**
* Gets the inverse of a square NxN matrix. B = A^-1.
* <p>
* @return the inverse for this matrix.
*/
public Matrix inverse()
{
if(rows != cols) return null;
LU solver = new LU();
boolean ok = solver.decompose(this);
if(!ok) return null;
Matrix inv = new Matrix(rows,cols);
for(int j=0;j<rows;j++)
{
Vector x = new Vector(rows);
x.set(j,1.0);
x = solver.solve(x);
for(int i=0;i<rows;i++)
{
inv.set(i,j,x.get(i));
}
}
return inv;
}

/**
* Adds the matrix passed as parameter to this matrix. A += B.
* @param m a matrix to be added.
* <p>
* @return this + m
*
*/
public Matrix add(Matrix m)
{
return add(this, m);
}

/**
* Substracts the matrix passed as parameter to this matrix. A -= B.
* @param m a matrix to be substracted.
* <p>
* @return this - m
*
*/
public Matrix sub(Matrix m)
{
return sub(this, m);
}

/**
* Multiplies the matrix passed as parameter to this matrix. A *= B.
* @param m a matrix to multiply.
* <p>
* @return this * m
*
*/
public Matrix mul(Matrix m)
{
return mul(this, m);
}

/**
* scales all values in the matrix by de value passed as parameter. A /= value.
* @param value Value for scale
* <p>
* @return scaled matrix
*
*/
public Matrix scale(double value)
{
	return scale(this, value);
}

/**
* Gets the trace of this matrix.
* the trace of a square matrix is the sum of its diagonal elements.
* The matrix must be square, otherwise this method returns 0
* <p>
* @return the trace for this matrix.
*
*/
public double trace()
{
if(rows != cols) return 0.0;
double t = 0.0;
for(int i = 0; i < cols; i++)
{
t += this.get(i, i);
}
return t;
}

/**
* Gets a diagonal matrix from this one.
*  <p>
* The matrix must be square.
* <p>
* @return A matrix composed of the elements in the diagonal of this matrix, or null if the matrix is not square.
*
*/
public Matrix diag()
{
if(rows != cols) return null;
Matrix out = new Matrix(rows, cols);
for(int i = 0; i < cols; i++)
{
out.set(i, i, this.get(i, i));
}
return out;
}

/**
* Gets the value of the determinant of a squared matrix.
* @return the value of the determinant.
*/
public double det()
{
if(rows != cols) return 0.0;
return det(this,rows);
}

/**
* Static method to get a n-order identity matrix.
* @param n the matrix order.
* @return the nxn identity matrix.
*/
public static Matrix identity(int n)
{
Matrix m = new Matrix(n,n);
for(int i=0;i<n;i++)
{
for(int j=0;j<n;j++)
{
	if(i == j) m.set(i,j,1.0);
}
}
return m;
}

/**
* Static method to get the transpose of the matrix passed as parameter. B = A^T.
* @param m a matrix to get its transpose.
* @return the transposed matrix.
*/
public static Matrix transpose(Matrix m)
{
	int r = m.rows();
	int c = m.columns();
Matrix t = new Matrix(c,r);
for(int i=0;i<c;i++)
{
for(int j=0;j<r;j++)
{
	t.set(i,j,m.get(j,i));
}
}
return t;
}

/**
* Gets a matrix resulting of adding the matrices passed as parameters. C = A+B.
* @param m1 first matrix.
* @param m2 second matrix.
* @return m1+m2.
*/
public static Matrix add(Matrix m1,Matrix m2)
{
	if(m1.rows()!=m2.rows() || m1.columns()!=m2.columns()) return m1;
Matrix m = new Matrix(m1.rows(),m1.columns());
for(int i=0;i<m.rows();i++)
{
for(int j=0;j<m.columns();j++)
{
	m.set(i,j,m1.get(i,j)+m2.get(i,j));
}
}
return m;
}

/**
* Gets a matrix result of substract the matrices passed as parameters. C = A-B.
* @param m1 first matrix.
* @param m2 second matrix.
* @return m1-m2.
*/
public static Matrix sub(Matrix m1,Matrix m2)
{
	if(m1.rows()!=m2.rows() || m1.columns()!=m2.columns()) return m1;
Matrix m = new Matrix(m1.rows(),m1.columns());
for(int i=0;i<m.rows();i++)
{
for(int j=0;j<m.columns();j++)
{
	m.set(i,j,m1.get(i,j)-m2.get(i,j));
}
}
return m;
}

/**
* Multiplies the matrices passed as parameters. C = A*B.
* @param m1 first matrix.
* @param m2 second matrix.
* @return m1*m2.
*/
public static Matrix mul(Matrix m1,Matrix m2)
{
if(m1.columns() != m2.rows()) return m1;
Matrix m = new Matrix(m1.rows(),m2.columns());
double accum = 0.0;
for(int i=0;i<m.rows();i++)
{
for(int j=0;j<m.columns();j++)
{
	accum = 0.0;
	for(int k=0;k<m1.columns();k++)
	{
		accum += m1.get(i,k)*m2.get(k,j);
	}
	m.set(i,j,accum);
}
}
return m;
}

/**
* scales a matrix passed as parameter by the value passed as second parameter. B = A/value.
* @param m a matrix.
* @param value Value for scale
* @return A scaled by value
*/
public static Matrix scale(Matrix m,double value)
{
Matrix r = new Matrix(m.rows(),m.columns());
for(int i=0;i<r.rows();i++)
{
for(int j=0;j<r.columns();j++)
{
	r.set(i,j,m.get(i,j)*value);
}
}
return r;
}

/**
* Eulers's rotation matrix.
* @param x the x component.
* @param y the y component.
* @param z the z component.
* @param w angle.
* @return a rotation matrix.
* x, y and z are the components of a vector and w is the rotation angle.
*/
public static Matrix rotation(double x,double y,double z,double w)
{
double d = Math.sqrt(x*x+y*y+z*z);
if(d == 0) d = 1.0;

double u1=x/d;
double u2=y/d;
double u3=z/d;

double pos00 = u1*u1+(Math.cos(w)*(1.0-u1*u1));
	 double pos01 = (u1*u2*(1.0-Math.cos(w)))-(u3*Math.sin(w));
	 double pos02 = (u3*u1*(1.0-Math.cos(w)))+(u2*Math.sin(w));
	 double pos10 = (u1*u2*(1.0-Math.cos(w)))+(u3*Math.sin(w));
	 double pos11 = u2*u2+(Math.cos(w)*(1.0-u2*u2));
	 double pos12 = (u2*u3*(1.0-Math.cos(w)))-(u1*Math.sin(w));
	 double pos20 = (u3*u1*(1.0-Math.cos(w)))-(u2*Math.sin(w));
	 double pos21 = (u2*u3*(1.0-Math.cos(w)))+(u1*Math.sin(w));
	 double pos22 = u3*u3+(Math.cos(w)*(1.0-u3*u3));

Matrix out = new Matrix(3,3);

out.set(0,0,pos00);
out.set(0,1,pos01);
out.set(0,2,pos02);
out.set(1,0,pos10);
out.set(1,1,pos11);
out.set(1,2,pos12);
out.set(2,0,pos20);
out.set(2,1,pos21);
out.set(2,2,pos22);

return out;
}

/**
* Gets a row from this matrix.
* <p>
* @param i Index row.
* <p>
* @return A row matrix.
*
*/
public Matrix getRowMatrix(int i)
{
	if(i < 0 || i > rows-1) return null;
	Matrix out = new Matrix(1, cols);
	for(int j = 0; j < cols; j++)
	{
	out.set(0, j, get(i, j));
	}
	return out;
}

/**
* Gets a column from this matrix.
* <p>
* @param j Index column.
* <p>
* @return A column matrix.
*
*/
public Matrix getColumnMatrix(int j)
{
	if(j < 0 || j > cols-1) return null;
	Matrix out = new Matrix(rows, 1);
	for(int i = 0; i < rows; i++)
	{
	out.set(i, 0, get(i, j));
	}
	return out;
}

/**
* Gets a row from a matrix as a vector.
* <p>
* @param i Row index.
* <p>
* @return matrix row as a vector or null if cannot perform the requested operation.
*
*/
public Vector getRowVector(int i)
{
if(i<0 || i>rows-1) return null;
Vector out = new Vector(cols);
for(int j = 0; j < cols; j++)
{
out.set(j, matrix[i][j]);
}
return out;
}

/**
* Gets a column from a matrix as a vector.
* <p>
* @param j Column index.
* <p>
* @return matrix column as a vector or null if cannot perform the requested operation.
*
*/
public Vector getColumnVector(int j)
{
if(j<0 || j>cols-1) return null;
Vector out = new Vector(rows);
for(int i = 0; i < rows; i++)
{
out.set(i, matrix[i][j]);
}
return out;
}

/**
* Check for orthogonality.
* <p>
* @return true if orthogonal or false otherwise.
*
*/
public boolean isOrthogonal()
{
Vector v = null;
for(int i = 0; i < cols-1; i++)
{
	v = getColumnVector(i);
	for(int j = i+1; j < cols; j++)
	{
		if(v.dot(this.getColumnVector(j)) > 1E-6) return false;
	}
}
return true;
}

/**
* Checks for orthonormality.
* <p>
* @return true if orthonormal or 0 otherwise.
*
*/
public boolean isOrthonormal()
{
if(!isOrthogonal()) return false;
for(int i = 0; i < cols; i++)
{
	if(Math.round(getColumnVector(i).modulo()) != 1.0) return false;
}
return true;
}

/**
* Sets the ith row of this matrix.
* <p>
* @param v Vector to be set as a row for this matrix.
* @param row Matrix row to set.
* <p>
* @return Resulting matrix or <code>this</code> if the row cannot be set.
*
*/
public Matrix setRow(Vector v, int row)
{
	if(v == null) return this;
	if(row < 0 || row > this.rows()-1) return this;
	if(v.size() != this.columns()) return this;
	Matrix out = (Matrix)this.clone();
	for(int j = 0; j < out.columns(); j++)
	{
		out.set(row, j, v.get(j));
	}
	return out;
}

/**
* Sets the jth column of this matrix.
* <p>
* @param v Vector to be set as a column.
* @param column Matrix column index to be set.
* <p>
* This method does nothing if the column cannot be set.
*/
public void setColumn(Vector v, int column)
{
if(v == null) return;
	if(column < 0 || column > cols-1) return;
	if(v.size() != rows) return;
	for(int i = 0; i < rows; i++)
	{
		this.set(i, column, v.get(i));
	}
}

/**
* Computes the pseudoinverse of a MxN matrix
* <p>
* using SVD ( Singular Value Decomposition ).
* <p>
* @return pseudoinverse of the matrix passed as parameter.
*
*/
public Matrix pseudoinverse()
{
	return pseudoinverse(this);
}

/**
* Static method to compute the pseudoinverse of a MxN matrix
* <p>
* using SVD ( Singular Value Decomposition ).
* <p>
* @param m matrix to get its pseudoinverse.
 <p>
* @return pseudoinverse of the matrix passed as parameter.
*
*/
public static Matrix pseudoinverse(Matrix m)
{
if(m == null) return null;
SVD svd = new SVD();
svd.decompose(m);
int k = (m.rows() <= m.columns()) ? m.rows() : m.columns();
Matrix sigma_t = transpose(svd.getSigma());
for(int i = 0; i < k; i++)
{
sigma_t.set(i, i, 1.0 / sigma_t.get(i, i));
}
return mul(mul(svd.getV(), sigma_t), transpose(svd.getU()));
}

/**
* Computes the nearest orthogonal matrix of a square NxN matrix
* <p>
* using SVD ( Singular Value Decomposition).
* <p>
* @return Nearest orthogonal matrix of the square matrix passed as parameter.
*
*/
public Matrix nearestOrthogonalMatrix()
{
	if(this.rows() != this.columns()) return null; // this matrix must ve square
SVD svd = new SVD();
svd.decompose(this);
return Matrix.mul(svd.getU(), Matrix.transpose(svd.getV()));
}

/**
* Computes the Forbinius norm of a matrix.
* <p>
* The Forbinius norm can be useful to know how close is a nearest orthogonal matrix O respect to a matrix M.
* <p>
* where
* <p>
* O is the nearest orthogonal matrix of M.
* <p>
* so, the Forbinius norm of O - M.
* <p>
* @return the Forbinius norm of this matrix.
*/
double forbiniusNorm()
{
double sum = 0.0;
for(int i = 0; i < rows; i++)
{
	for(int j = 0; j < cols; j++)
	{
		sum += _square_(this.get(i, j));
	}
}
return Math.sqrt(sum);
}


// Compute minors

/**
* Gets the kth-qth minor of this matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor or NaN if the operation cannot be done.
*
*/
public double minor(int k, int q)
{
	Matrix m = this.minorMatrix(k, q);
	return (m == null) ? Double.NaN : m.det();
}

/**
* Static method to get the kth-qth minor of a square matrix.
* <p>
* @param m
* A square matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor of the matrix passed as parameter or NaN if the operation cannot be done.
*
*/
public static double minor(Matrix m, int k, int q)
{
Matrix _m = minorMatrix(m, k, q);
return (_m == null) ? Double.NaN : _m.det();
}

/**
* Gets the kth-qth minor matrix of this matrix ( this matrix must be square ).
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor matrix or null if the operation cannot be done.
*
*/
public Matrix minorMatrix(int k, int q)
{
return minorMatrix(this, k, q);
}

/**
* Static method to get the kth-qth minor matrix of a square matrix.
* <p>
* @param m
* A square matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @param q
* An integer value for the qth index.
* <p>
* @return kth-qth minor matrix of the matrix passed as parameter or null if the operation cannot be done.
*
*/
public static Matrix minorMatrix(Matrix m, int k, int q)
{
if(m == null) return null;
int r = m.rows();
int c = m.columns();
if(r != c) return null; // m must be square
if(k < 0 || k > r-1) return null; // k is out of range
if(q < 0 || q > r-1) return null; // q is out of range
int order = r;
if(order < 2) return null; // order must be >= 2
Matrix minor = new Matrix(order-1, order-1); // create minor matrix
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
* Gets the kth first minor of this matrix ( this matrix must be square ).
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor or NaN if the operation cannot be done.
*
*/
public double firstMinor(int k)
{
Matrix m = firstMinorMatrix(this, k);
return (m == null) ? Double.NaN : m.det();
}

/**
* Static method to get the kth first minor of a square matrix.
* <p>
* @param m
* A square matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor of the matrix passed as parameter or NaN if the operation cannot be done.
*
*/
public static double firstMinor(Matrix m, int k)
{
Matrix _m = firstMinorMatrix(m, k);
return (_m == null) ? Double.NaN : _m.det();
}

/**
* Gets the kth first minor matrix of this matrix ( this matrix must be square ).
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor matrix or null if the operation cannot be done.
*
*/
public Matrix firstMinorMatrix(int k)
{
return firstMinorMatrix(this, k);
}

/**
* Static method to get the kth first minor matrix of a square matrix.
* <p>
* @param m
* A square matrix.
* <p>
* @param k
* An integer value for the kth index.
* <p>
* @return kth first minor matrix of the matrix passed as parameter or null if the operation cannot be done.
*
*/
public static Matrix firstMinorMatrix(Matrix m, int k)
{
return minorMatrix(m, k, k);
}


// Compute cofactor matrix using minors

/**
* Method to compute the cofactor matrix for this matrix ( the matrix must be square ).
* <p>
* @return cofactor matrix or null if the operation cannot be done.
*
*/
public Matrix cofactorMatrix()
{
	return cofactorMatrix(this);
}

/**
* Static method to compute the cofactor matrix for the square matrix passed as parameter.
* <p>
* @param m
* A square matrix.
* <p>
* @return cofactor matrix or null if the operation cannot be done.
*
*/
public static Matrix cofactorMatrix(Matrix m)
{
if(m == null) return null;
int n = m.rows();
if(n != m.columns()) return null; // m must be square
Matrix out = new Matrix(n, n);
double sign;
for(int i = 0; i < n; i++)
{
for(int j = 0; j < n; j++)
{
	sign = (((i+j)%2) == 0) ? 1.0 : -1.0;
	out.set(i, j, sign*m.minor(i, j));
}
}
return out;
}


// Compute inverse matrix using cofactors

/**
* Method to invert this matrix using cofactors ( this matrix must be square ).
* <p>
* @return inverse matrix or null if the operation cannot be done.
*
*/
public Matrix invertMatrix()
{
	return invertMatrix(this);
}

/**
* Static method to compute the inverse of a square matrix using cofactors .
* <p>
* @param m
* A square matrix.
* @return inverse matrix or null if the operation cannot be done.
*
*/
public static Matrix invertMatrix(Matrix m)
{
if(m == null) return null;
if(m.rows() != m.columns()) return null; // m must be square
double d = m.det();
if(Math.abs(d) < __THRESHOLD__) return null; // det(m) is too close to zero.
double t = 1.0/d;
Matrix _m = m.cofactorMatrix();
return _m.transpose().scale(t);
}

/**
* Computes the determinant of this matrix using cofactors.
* <p>
* Since there is already a method named 'det' to compute determinants in another way, <p>
* we decided call this one 'determinant' <p>
* @return determinant of this matrix, or NaN if the operation cannot be done.
*
*/
public double determinant()
{
return determinant(this);
}

/**
* This static method computes the determinant of the matrix passed as parameter using cofactors.
* <p>
* Since there is already a method named 'det' to compute determinants in another way, <p>
* we decided call this one 'determinant' <p>
* @param m
* A square matrix.
* <p>
* @return determinant of the matrix passed as parameter or NaN if the operation cannot be done.
*
*/
public static double determinant(Matrix m)
{
if(m == null) return Double.NaN;
if(m.rows() != m.columns()) return Double.NaN; // m must be square
double sign = 1.0;
double d = 0.0;
int n = m.rows(); // order of m
for(int j = 0; j < n; j++)
{
	d += sign * m.minor(0, j) * m.get(0, j);
	sign *= -1.0;
}
return d;
}

/**
* Evaluates for symmetry.
* <p>
* @return true if this matrix is symmetrical or false otherwise.
*
*/
public boolean isSymmetrical()
{
return isSymmetrical(this);
}

/**
* Evaluates for symmetry.
* <p>
* @param m
* A square matrix.
* <p>
* @return true if the matrix passed as parameter is symmetrical or false otherwise.
*
*/
public static boolean isSymmetrical(Matrix m)
{
if(m == null) return false;
int n = m.rows();
if(n != m.columns()) return false; // m must be square
boolean retval = true;
for(int i = 0; i < n; i++)
{
for(int j = i+1; j < n; j++)
{
	if(m.get(i, j) != m.get(j, i))
	{
		retval = false;
		break;
	}
}
}
return retval;
}


// Computes the determinant recursively
private double det(Matrix m,int n)
{
double d=0.0,sign=1.0;
int ai,aj;
if(n==1)
{
return m.get(0,0);
}
else
{
Matrix adj = new Matrix(n-1,n-1);
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
d+=sign*m.get(k,0)*det(adj,n-1);
sign*=-1.0;
}
}
return d;
}

private double _square_(double x)
{
return x*x;
}


{
rows = 0;
cols = 0;
matrix = null;
}

private int rows;
private int cols;

private double[][] matrix;

private static final double __THRESHOLD__ = 1E-6;
}

// END
