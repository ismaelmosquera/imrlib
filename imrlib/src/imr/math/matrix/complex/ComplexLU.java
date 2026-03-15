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
* ComplexLU.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.math.matrix.complex;

import imr.util.iArray;
import imr.math.ComplexNumber;

/**
* The <code>ComplexLU</code> class implements LU decomposition for square matrixes of complex number coefficients. <p>
* @author Ismael Mosquera rivera.
*
*/
public final class ComplexLU implements ComplexMatrixDecomposition
{

/**
* Makes a new instance of a <code>ComplexLU</code> object. <p>
* Note that using this constructor no matrix has been set yet.
*/
public ComplexLU()
{
	_lower = null;
	_upper = null;
	_permutations = null;
	_hasLU = false;
	}

/**
* Performs ComplexLU decomposition.
* <p>
* @param m a nxn coeficient complex matrix.
* <p>
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
*
*/
public boolean decompose(ComplexMatrix m)
{
	if(m.rows() != m.columns())
	{
	_hasLU = false;
	return false;
	}
_upper = (ComplexMatrix)m.clone();
int n = _upper.rows();
_permutations = new int[n];
		_lower = ComplexMatrix.identity(n);
// begin init structures
for(int i=0;i<n;i++)
{
_permutations[i]=i;
}
// end init structures
int row;
boolean singular=false;
float pivot;
ComplexNumber tmp = null;
ComplexNumber remove = null;
ComplexNumber t = null;
int i=0;
while(!singular && i<n)
{
pivot = _upper.get(i,i).magnitude();
row=i;
for(int k=i+1;k<n;k++)
{
if(_upper.get(k,0).magnitude() > pivot)
{
pivot = _upper.get(k,0).magnitude();
row=k;
}
}
singular = (pivot<SysHelper.THRESHOLD);
if(!singular)
{
if(i != row)
{
for(int j=0;j<n;j++)
{
tmp = _upper.get(i,j);
_upper.set(i,j, _upper.get(row,j));
_upper.set(row,j, tmp);
_permutations[i] = row;
_permutations[row]=i;
}
}
for(int j=i+1;j<n;j++)
{
remove = _upper.get(j,i).scale(-1.0f).div(_upper.get(i,i));
_lower.set(j,i, remove.scale(-1.0f));
for(int k=0;k<n;k++)
{
t = _upper.get(j,k).add(remove.mul(_upper.get(i,k)));
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
* Method to solve a system having a ComplexLU decomposition done.
* <p>
* @param v complex vector of n coeficients.
* <p>
* @return a complex vector with the solution of the system or null if the operation cannot be done.
*
*/
public ComplexVector solve(ComplexVector v)
{
if(!_hasLU || _lower.rows()!=v.size()) return null;
ComplexVector x = (ComplexVector)v.clone();
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
* Gets the lower complex matrix.
* <p>
* @return lower complex matrix.
*
*/
public ComplexMatrix getLower()
{
	if(!_hasLU) return null;
return _lower;
}

/**
* gets the upper complex matrix.
* <p>
* @return upper complex matrix.
*
*/
public ComplexMatrix getUpper()
{
	if(!_hasLU) return null;
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
	if(!_hasLU) return null;
return _permutations;
}

/**
* Prints this ComplexLU to the console.
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
iArray.print(_permutations, "Permutations: ");
System.out.println();
}


// Helper method to solve lower system.
private ComplexVector solveLower(ComplexVector v)
{
ComplexVector out = new ComplexVector(v.size());
int n = _lower.rows();
out.set(0, v.get(0).div(_lower.get(0,0)));
for(int i=1;i<n;i++)
{
out.set(i, v.get(i));
for(int j=0;j<i;j++)
{
out.set(i, out.get(i).sub(_lower.get(i,j).mul(out.get(j))));
}
out.set(i, out.get(i).div(_lower.get(i,i)));
}
return out;
}

// Helper method to solve upper system.
private ComplexVector solveUpper(ComplexVector v)
{
	return SysHelper.upperTriangularSystemSolver(SysHelper.systemMatrix(_upper, v));
}

// Helper method to handle permutations if necessary.
private ComplexVector handlePermutations(ComplexVector v)
{
ComplexVector out = new ComplexVector(v.size());
for(int i=0;i<v.size();i++)
{
out.set(i,v.get(_permutations[i]));
}
return out;
}


private boolean _hasLU;
private ComplexMatrix _lower;
private ComplexMatrix _upper;
private int[] _permutations;

}

// END

