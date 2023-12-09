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
* LinearSystemSolver.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.matrix;

import imr.util.iArray;

/**
* The <code>LinearSystemSolver</code> class defines methods to solve linear systems and make LU decomposition.
* @author Ismael Mosquera Rivera
*/
public final class LinearSystemSolver
{

/**
* Makes a new instance of a <code>LinearSystemSolver</code> object.
* Note that using this constructor no matrix has been set yet.
*/
public LinearSystemSolver(){}

/**
* Makes a new instance of a <code>LinearSystemSolver</code> object.
* @param m a nxn coeficient matrix.
* Using this constructor implies that a LU decomposition of the matrix passed as parameter is made.
*/
public LinearSystemSolver(Matrix m)
{
hasLU = set(m);
}

/**
* Sets a nxn matrix and a LU decomposition is made.
* @param m a nxn coeficient matrix.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
*/
public boolean set(Matrix m)
{
	if(m.rows() != m.columns())
	{
	hasLU = false;
	return false;
	}
upper = (Matrix)m.clone();
int n = upper.rows();
if(permutations == null)
	{
permutations = new int[n];
	}
	else
	{
		permutations = (int[])iArray.resize(permutations,n);
	}
	if(lower == null)
	{
		lower = new Matrix(n,n);
	}
	else
	{
		lower.resize(n,n);
	}
// begin init structures
for(int i=0;i<n;i++)
{
lower.set(i,i,1.0);
permutations[i]=i;
}
// end init structures
int row;
boolean singular=false;
double pivot,tmp,remove;
int i=0;
while(!singular && i<n)
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
singular = (pivot<threshold);
if(!singular)
{
if(i != row)
{
for(int j=0;j<n;j++)
{
tmp = upper.get(i,j);
upper.set(i,j,upper.get(row,j));
upper.set(row,j,tmp);
permutations[i]=row;
permutations[row]=i;
}
}
for(int j=i+1;j<n;j++)
{
remove = -(upper.get(j,i))/upper.get(i,i);
lower.set(j,i,-remove);
for(int k=0;k<n;k++)
{
double t = upper.get(j,k)+remove*upper.get(i,k);
upper.set(j,k,t);
   }
}
}
i++;
}
hasLU = (singular) ? false : true;
return hasLU;
}

/**
* Method to solve a system havind a nxn coeficient matrix set before.
* @param v vector of n coeficients.
* @return a vector with the solution of the system.
* <p>
* If the system cannot be solved, this method returns null.
*/
public Vector solve(Vector v)
{
if(!hasLU || lower.rows()!=v.size()) return null;
Vector x = (Vector)v.clone();
boolean pivoting = false;
for(int i=0;i<x.size();i++)
{
if(permutations[i] != i)
{
pivoting = true;
break;
}
}
if(pivoting)
{
x = handlePermutations(x);
}
return solveUpper(solveLower(x));
}

/**
* Returns a LU decomposition of a nxn coeficient matrix set before.
* @return a <code>LinearSystemSolver.LU</code> object having the LU decomposition.
*/

public LU lu()
{
return new LU();
}

/**
* Static method to solve a linear system with a mxn matrix passed as parameter where n = m+1.
* @param m a mxn system matrix.
* @return a vector with the solution of the system.
* The matrix passed as parameter is the nxn coeficient matrix and the last column has the coeficient vector.
* So it is a mxn matrix where n = m+1.
* <p>
* If the system cannot be solved, this method returns null.
*/
public static Vector solve(Matrix m)
{
	return GaussianSolver.solve(m);
}

/**
* static method to solve a linear system passing a nxn coeficient matrix and a n coeficient vector as parameter.
* @param m a nxn coeficient matrix.
* @param v a n coeficient vector.
* @return a vector with the solution of the system.
* If the system cannot be solved, this method returns null.
*/
public static Vector solve(Matrix m,Vector v)
{
	return GaussianSolver.solve(m, v);
}

private double abs(double x)
{
return (x<0) ? -x : x;
}

private Vector solveLower(Vector v)
{
Vector out = new Vector(v.size());
int n = lower.rows();
out.set(0,v.get(0)/lower.get(0,0));
for(int i=1;i<n;i++)
{
out.set(i,v.get(i));
for(int j=0;j<i;j++)
{
out.set(i,out.get(i)-lower.get(i,j)*out.get(j));
}
out.set(i,out.get(i)/lower.get(i,i));
}
return out;
}

private Vector solveUpper(Vector v)
{
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

private Vector handlePermutations(Vector v)
{
Vector out = new Vector(v.size());
for(int i=0;i<v.size();i++)
{
out.set(i,v.get(permutations[i]));
}
return out;
}

/**
* Inner class to store the LU decomposition.
* @author Ismael Mosquera Rivera
*/
public final class LU
{

/**
* Returns the lower matrix.
* @return the lower matrix.
*/
public Matrix lower()
{
return lower;
}

/**
* Returns the upper matrix.
* @return the upper matrix.
*/
public Matrix upper()
{
return upper;
}

/**
* Returns the permutation vector.
* @return an int[] vector with the permutations.
*/
public int[] permutations()
{
return permutations;
}

private LU(){}
}

{
hasLU = false;
lower = null;
upper = null;
permutations = null;
}

private boolean hasLU;
private Matrix lower;
private Matrix upper;
private int[] permutations;
private static final double threshold = 1e-6;
}

// END

