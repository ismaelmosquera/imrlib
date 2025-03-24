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
* EigenFinder.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera.
*/

package imr.matrix;

/**
* The <code>EigenFinder</code> class implements static methods to find eigenvalues and eigenvectors.
* <p>
* You can get just the maximum Eigen of a matrix, or its eigensystem.
*
* @author Ismael Mosquera rivera.
*
*/
public final class EigenFinder
{

/**
* Finds the maximum eigen for a square matrix using the Power Method.
* <p>
* @param m A square matrix.
* <p>
* @return The maximumk Eigen for the matrix passed as parameter.
*
*/
public static Eigen findMaxEigen(Matrix m)
{
if(m.rows() != m.columns()) return null; // m must be square
double value = 0.0;
double x, ant, lim;
int i, j, n, major;
Matrix b = null;
Matrix a = (Matrix)m.clone();
n = a.columns();
Matrix aux = new Matrix(n, 1);
for(i = 0; i < n; i++) aux.set(i, 0, 1.0);
i = 1;
do
{
ant = value;
b = Matrix.mul(a, aux);
major = 0;
for(j = 0; j < n; j++)
{
if(abs(b.get(major, 0)) < abs(b.get(j, 0))) major = j;
}
value = b.get(major, 0);
for(j = 0; j < n; j++)
{
	x = b.get(j, 0);
	b.set(j, 0, x/value);
}
lim = abs(value - ant);
if(lim < THRESHOLD) break; /* done */
for(j = 0; j < n; j++)
{
aux.set(j, 0, b.get(j, 0));
}
i++;
}while(i < MAX_ITERATIONS);
if(i >= MAX_ITERATIONS) return null;
Vector eigenvector = new Vector(n);
for(i = 0; i < n; i++) eigenvector.set(i, b.get(i, 0));
return new Eigen(value, eigenvector);
}

/**
* Finds the eigensystem for a square matrix using the QR Algorithm.
* <p>
* @param m a square matrix.
* <p>
* @return The eigensystem of the matrix passed as parameter.
*
*/
public static Eigen[] findEigenSystem(Matrix m)
{
if(m.rows() != m.columns()) return null; // m must be square
double trace, param;
int n = m.rows();
Matrix upper = null;
Eigen[] eigensys = new Eigen[n];
QR qr = new QR();
qr.decompose(m); // get QR factorization of m
/* Compute eigenvalues */
int k = 0;
while(true)
{
	qr.decompose(Matrix.mul(qr.getR(), qr.getQ()));
		if(done(qr.getQ()) || k > MAX_ITERATIONS) break;
		k++;
}
// eigenvalues are listed in the diagonal of lambda
Matrix lambda = Matrix.mul(qr.getQ(), qr.getR());
Matrix aux = new Matrix(n, n);
	Vector v = new Vector(n);
/* compute eigenvectors and build eigensystem */
for(int i = 0; i < n; i++)
{
	for(int j = 0; j < n; j++)
	{
		aux.set(j, j, lambda.get(i, i));
}
upper = upper_triangular(Matrix.sub(m, aux));
trace = upper.trace()-1.0;
param = (Math.abs(trace) < THRESHOLD) ? 1.0 : (Math.abs(trace)>=1.0) ? 1.0/trace : trace;
v.set(n-1, param);
eigensys[i] = new Eigen(lambda.get(i, i), upper_system_solver(upper, v));
}
return eigensys;
}


private static double abs(double x)
{
	return (x < 0.0) ? -x : x;
}

private static boolean done(Matrix m)
{
for(int i = 0; i < m.rows(); i++)
{
	if(abs(m.get(i, i)) != 1.0) return false;
}
return true;
}

private static Matrix upper_triangular(Matrix m)
{
if(m.rows() != m.columns()) return null;
	Matrix upper = (Matrix)m.clone();
int n = upper.rows();
int row;
double pivot,tmp,remove, t;
int i=0;
while(i<n)
{
pivot = abs(upper.get(i,i));
row=i;
for(int k=i+1;k<n;k++)
{
if(abs(upper.get(k,0))>pivot)
{
pivot = abs(upper.get(k,0));
row=k;
}
}
if(i != row)
{
for(int j=0;j<n;j++)
{
tmp = upper.get(i,j);
upper.set(i,j, upper.get(row,j));
upper.set(row,j,tmp);
}
}
for(int j=i+1;j<n;j++)
{
remove = -(upper.get(j,i))/upper.get(i,i);
for(int k=0;k<n;k++)
{
t = upper.get(j,k)+remove*upper.get(i,k);
upper.set(j,k,t);
   }
}
i++;
}
upper.set(n-1, n-1, 1.0);
return upper;
}

private static Vector upper_system_solver(Matrix m, Vector v)
{
	Matrix upper = (Matrix)m.clone();
	Vector out = new Vector(v.size());
	int n = upper.rows();
	out.set(n-1,v.get(n-1)/upper.get(n-1,n-1));
	for(int i=n-2;i>=0;i--)
	{
	out.set(i,v.get(i));
	for(int j=i+1;j<n;j++)
	{
	out.set(i,out.get(i)-upper.get(i,j)*out.get(j));
	}
	out.set(i,out.get(i)/upper.get(i,i));
	}
	return out;
}

// private constructor so that this class cannot be instantiated.
private EigenFinder() {}

private static final double THRESHOLD = 1E-3;
private static final int MAX_ITERATIONS = 50000;
}

// END
