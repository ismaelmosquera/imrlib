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
* LU.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.matrix;

import imr.util.iArray;
/**
* The <code>LU</code> class implements LU decomposition.
* <p>
* @author Ismael Mosquera rivera.
*
*/
public final class LU implements MatrixDecomposition
{

/**
* Makes a new instance of a <code>LU</code> object.
* Note that using this constructor no matrix has been set yet.
*/
public LU()
{
	_lower = null;
	_upper = null;
	_permutations = null;
	_hasLU = false;
	}

/**
* Performs LU decomposition.
* <p>
* @param m a nxn coeficient matrix.
* <p>
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
*/
public boolean decompose(Matrix m)
{
	if(m.rows() != m.columns())
	{
	_hasLU = false;
	return false;
	}
_upper = (Matrix)m.clone();
int n = _upper.rows();
if(_permutations == null)
	{
_permutations = new int[n];
	}
	else
	{
		_permutations = (int[])iArray.resize(_permutations,n);
	}
	if(_lower == null)
	{
		_lower = new Matrix(n,n);
	}
	else
	{
		_lower.resize(n,n);
	}
// begin init structures
for(int i=0;i<n;i++)
{
_lower.set(i,i,1.0);
_permutations[i]=i;
}
// end init structures
int row;
boolean singular=false;
double pivot,tmp,remove;
int i=0;
while(!singular && i<n)
{
pivot = abs(_upper.get(i,i));
row=i;
for(int k=i+1;k<n;k++)
{
if(abs(_upper.get(k,0))>pivot)
{
pivot = abs(_upper.get(k,0));
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
tmp = _upper.get(i,j);
_upper.set(i,j,_upper.get(row,j));
_upper.set(row,j,tmp);
_permutations[i]=row;
_permutations[row]=i;
}
}
for(int j=i+1;j<n;j++)
{
remove = -(_upper.get(j,i))/_upper.get(i,i);
_lower.set(j,i,-remove);
for(int k=0;k<n;k++)
{
double t = _upper.get(j,k)+remove*_upper.get(i,k);
_upper.set(j,k,t);
   }
}
}
i++;
}
_hasLU = (singular) ? false : true;
return _hasLU;
}

/**
* Method to solve a system having a LU decomposition done.
* <p>
* @param v vector of n coeficients.
* <p>
* @return a vector with the solution of the system.
* <p>
* If the system cannot be solved, this method returns null.
*/
public Vector solve(Vector v)
{
if(!_hasLU || _lower.rows()!=v.size()) return null;
Vector x = (Vector)v.clone();
boolean pivoting = false;
for(int i=0;i<x.size();i++)
{
if(_permutations[i] != i)
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
* Gets the lower matrix.
* <p>
* @return lower matrix.
*
*/
public Matrix getLower()
{
return _lower;
}

/**
* gets the upper matrix.
* <p>
* @return upper matrix.
*
*/
public Matrix getUpper()
{
	return _upper;
}

/**
* Gets the permutation vector.
* <p>
* @return permutations vector.
*
*/
public int[] getPermutations()
{
return _permutations;
}

/**
* Prints this LU to the console.
*
*/
public void print()
{
if(!_hasLU)
{
System.out.println("[]");
return;
}
System.out.println("Lower:");
_lower.print();
System.out.println();
System.out.println("Upper:");
_upper.print();
System.out.println();
System.out.println("Permutations:");
iArray.print(_permutations, "");
System.out.println();
}


private double abs(double x)
{
return (x<0) ? -x : x;
}

private Vector solveLower(Vector v)
{
Vector out = new Vector(v.size());
int n = _lower.rows();
out.set(0,v.get(0)/_lower.get(0,0));
for(int i=1;i<n;i++)
{
out.set(i,v.get(i));
for(int j=0;j<i;j++)
{
out.set(i,out.get(i)-_lower.get(i,j)*out.get(j));
}
out.set(i,out.get(i)/_lower.get(i,i));
}
return out;
}

private Vector solveUpper(Vector v)
{
Vector out = new Vector(v.size());
int n = _upper.rows();
out.set(n-1,v.get(n-1)/_upper.get(n-1,n-1));
for(int i=n-2;i>=0;i--)
{
out.set(i,v.get(i));
for(int j=i+1;j<n;j++)
{
out.set(i,out.get(i)-_upper.get(i,j)*out.get(j));
}
out.set(i,out.get(i)/_upper.get(i,i));
}
return out;
}

private Vector handlePermutations(Vector v)
{
Vector out = new Vector(v.size());
for(int i=0;i<v.size();i++)
{
out.set(i,v.get(_permutations[i]));
}
return out;
}


private boolean _hasLU;
private Matrix _lower;
private Matrix _upper;
private int[] _permutations;
private static final double threshold = 1e-6;
}

// END

