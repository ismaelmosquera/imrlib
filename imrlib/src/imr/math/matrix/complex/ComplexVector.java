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
* ComplexVector.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;

/**
* The <code>ComplexVector</code> class implements the most common operation applied to vectors with complex number coefficients. <p>
* In addition, there is support to storage too. <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class ComplexVector
{

/**
* Constructor. <p>
* Makes a new instance of a <code>ComplexVector</code> class. <p>
* @param size
* An integer value.
* <p>
* All the elements will be set to zero.
*
*/
public ComplexVector(int size)
{
assert(size > 0): "ComplexVector -> constructor (int size): Bad parameter.";
_size = size;
_cvector = new ComplexNumber[_size];
for(int i = 0; i < _cvector.length; i++) _cvector[i] = new ComplexNumber(0.0f, 0.0f);
}

/**
* Constructor. <p>
* @param filename
* A string of the file where to get the complex vector data. <p>
* To know the format for such a file see the <code>load(String filename)</code> method explanation.
*
*/
public ComplexVector(String filename)
{
this.load(filename);
}

/**
* Prints this complex vector to the console.
*
*/
public void print()
{
ComplexNumber.print(_cvector);
}

/**
* Loads a complex vector from a file. <p>
* @param filename
* A String for the file where to get the complex vector data. <p>
* The format for the file is as follows: <p>
* #size ( an integer value )
* r0 i0 <p>
* r1 i1 <p>
* ... <p>
* rn in <p>
* where n equals size-1 <p>
* If loaded successfully, this complex vector will content data according to the one in the concrete file. <p>
* We take the *.dat extension for this kind of file, but since is a text file, you can set any other extension if you wish.
*
*/
public void load(String filename)
{
_cvector = ComplexNumber.loadComplexArray(filename);
_size = _cvector.length;
}

/**
* Stores this complex vector to a file. <p>
* @param filename
* A String to the file where to store this complex vector.
*
*/
public void store(String filename)
{
ComplexNumber.storeComplexArray(_cvector, filename);
}

/**
* Gets a value from this complex vector. <p>
* @param i
* Index from to get a complex number value. <p>
* @return Requested complex number value or ComplexNumber.NaN if the operation cannot be done.
*
*/
public ComplexNumber get(int i)
{
if(i < 0 || i > _size-1) return ComplexNumber.NaN;
return (ComplexNumber)_cvector[i].clone();
}

/**
* Sets a value to this complex vector in a concrete position. <p>
* @param i
* Position index. <p>
* @param z
* A complex number object to set in the ith position. <p>
* If the operation cannot be done, this method does nothing.
*
*/
public void set(int i, ComplexNumber z)
{
	if(z == null) return;
if(i < 0 || i > _size-1) return;
_cvector[i] = (ComplexNumber)z.clone();
}

/**
* Gets the size ( number of elements ) in this complex vector. <p>
* @return size of this complex vector.
*
*/
public int size()
{
return _size;
}

/**
* Adds this complex vector to the one passed as parameter. <p>
* @param v
* A complex vector object.
* <p>
* @return this + v
*
*/
public ComplexVector add(ComplexVector v)
{
return add(this, v);
}

/**
* Substracts the complex vector passed as parameter from this one. <p>
* @param v
* A complex vector object.
* <p>
* @return this - v
*
*/
public ComplexVector sub(ComplexVector v)
{
return sub(this, v);
}

/**
* Scales this complex vector by the complex number passed as parameter. <p>
* @param z
* A complex number object.
* <p>
* @return Scaled complex vector.
*
*/
public ComplexVector scale(ComplexNumber z)
{
return scale(this, z);
}

/**
* Performs the dot product of this complex vector by the one passed as parameter. <p>
* @param v
* A complex vector object.
* <p>
* @return this * v or ComplexNumber.NaN if the operation cannot be done.
*
*/
public ComplexNumber dot(ComplexVector v)
{
return dot(this, v);
}

/**
* Performs the cross product between this complex vector and the one passed as parameter. <p>
* @param v
* A complex vector object.
* <p>
* @return this^v or null if the operation cannot be done.
*
*/
public ComplexVector cross(ComplexVector v)
{
	return cross(this, v);
}

/**
* Computes the module of this complex vector. <p>
* @return Module of this complex vector or ComplexNumber.NaN if the operation cannot be done.
*
*/
public ComplexNumber module()
{
	return module(this);
}

/**
* Normalizes this complex vector. <p>
* @return Normalized complex vector or null if the operation cannot be done.
*
*/
public ComplexVector normalize()
{
	return normalize(this);
}

/**
* Gets a complex row matrix builded from this complex vector. <p>
* @return A complex row matrix builded from this complex vector or null if the operation cannot be done.
*
*/
public ComplexMatrix toRowMatrix()
{
return toRowMatrix(this);
}

/**
* Gets a complex column matrix builded from this complex vector. <p>
* @return A complex column matrix builded from this complex vector or null if the operation cannot be done.
*
*/
public ComplexMatrix toColumnMatrix()
{
return toColumnMatrix(this);
}


// Some methods overrided from Object.

/**
* Makes a clone of this complex vector. <p>
* You must cast the returned value in order to get the desired result. <p>
* Example: <p>
* ComplexVector myComplexVector = (ComplexVector)v.clone(); <p>
* where v is the complex vector to clone. <p>
* @return A clone of this complex vector.
*
*/
public Object clone()
{
ComplexVector out = new ComplexVector(_size);
for(int i = 0; i < _size; i++)
{
out.set(i, (ComplexNumber)_cvector[i].clone());
}
return out;
}

/**
* Evaluates for equality. <p>
* @param v
* A complex vector object.
* <p>
* @return true if this complex vector is equal to the one passed as parameter or false otherwise.
*
*/
public boolean equals(ComplexVector v)
{
if(_size != v.size()) return false; // vectors must have the same size
for(int i = 0; i < _size; i++)
{
if(!_cvector[i].equals(v.get(i))) return false;
}
return true;
}

/**
* Gets a String representation of this complex vector. <p>
* @return A string representation of this complex vector.
*
*/
public String toString()
{
String s = "";
s += "[";
for(int i = 0; i < _size; i++)
{
	if(i > 0) s += ", ";
s += _cvector[i];
}
s += "]";
return s;
}


// Static methods

/**
* Static method to add two complex vectors. <p>
* @param v1
* A complex vector object.
* <p>
* @param v2
* A complex vector object.
* <p>
* @return v1 + v2 or null if the operation cannot be done.
*
*/
public static ComplexVector add(ComplexVector v1, ComplexVector v2)
{
if(v1 == null || v2 == null) return null;
if(v1.size() != v2.size()) return null; // v1 and v2 must have the same size
int n = v1.size();
ComplexVector out = new ComplexVector(n);
for(int i = 0; i < n; i++)
{
out.set(i, v1.get(i).add(v2.get(i)));
}
return out;
}

/**
* Static method to substract two complex vectors. <p>
* @param v1
* A complex vector object.
* <p>
* @param v2
* A complex vector object.
* <p>
* @return v1 - v2 or null if the operation cannot be done.
*
*/
public static ComplexVector sub(ComplexVector v1, ComplexVector v2)
{
if(v1 == null || v2 == null) return null;
if(v1.size() != v2.size()) return null; // v1 and v2 must have the same size
int n = v1.size();
ComplexVector out = new ComplexVector(n);
for(int i = 0; i < n; i++)
{
out.set(i, v1.get(i).sub(v2.get(i)));
}
return out;
}

/**
* Static method to scale the complex vector passed as first parameter by the complex number passed as second parameter. <p>
* @param v
* A complex vector to be scaled.
* <p>
* @param z
* A complex number object to scale the desired complex vector.
* <p>
* @return Scaled complex vector or null if the operation cannot be done.
*
*/
public static ComplexVector scale(ComplexVector v, ComplexNumber z)
{
if(v == null || z == null) return null;
int n = v.size();
ComplexVector out = new ComplexVector(n);
for(int i = 0; i < n; i++)
{
out.set(i, v.get(i).mul(z));
}
return out;
}

/**
* Static method to perform the dot product between two complex vectors. <p>
* @param v1
* A complex vector object.
* <p>
* @param v2
* A complex vector object.
* <p>
* @return v1 * v2 or ComplexNumber.NaN if the operation cannot be done.
*
*/
public static ComplexNumber dot(ComplexVector v1, ComplexVector v2)
{
if(v1 == null || v2 == null) return ComplexNumber.NaN;
if(v1.size() != v2.size()) return ComplexNumber.NaN;
ComplexNumber z = new ComplexNumber(0.0f, 0.0f);
int n = v1.size();
for(int i = 0; i < n; i++)
{
z = z.add(v1.get(i).mul(v2.get(i)));
}
return z;
}

/**
* Static method to Perform the cross product a^b <p>
* @param v1
* A complex vector object.
* <p>
* @param v2
* A complex vector object.
* <p>
* @return cross product a^b or null if the operation cannot be done.
*
*/
public static ComplexVector cross(ComplexVector v1, ComplexVector v2)
{
	if(v1.size() != v2.size()) return null;
	if(v1.size() != 3) return null;
	ComplexVector r = new ComplexVector(3);
 r.set(0, v1.get(1).mul(v2.get(2)).sub(v1.get(2).mul(v2.get(1))));
	r.set(1, v1.get(2).mul(v2.get(0)).sub(v1.get(0).mul(v2.get(2))));
	r.set(2, v1.get(0).mul(v2.get(1)).sub(v1.get(1).mul(v2.get(0))));
	return r;
}

/**
* Static method to compute the module of the complex vector passed as parameter. <p>
* @param v
* A complex vector object.
* <p>
* @return module of the complex vector passed as parameter or ComplexNumber.NaN if the operation cannot be done.
*
*/
public static ComplexNumber module(ComplexVector v)
{
	if(v == null) return ComplexNumber.NaN;
int n = v.size();
ComplexNumber z = new ComplexNumber(0.0f, 0.0f);
for(int i = 0; i < n; i++)
{
z = z.add(v.get(i).square());
}
return z.sqrt();
}

/**
* Static method to normalize the complex vector passed as parameter. <p>
* @param v
* A complex vector object.
* <p>
* @return normalized complex vector or null if the operation cannot be done.
*
*/
public static ComplexVector normalize(ComplexVector v)
{
ComplexNumber m = v.module();
if(m.magnitude() < SysHelper.THRESHOLD) return null; // m is too close to zero
int n = v.size();
ComplexVector out = new ComplexVector(n);
for(int i = 0; i < n; i++)
{
out.set(i, v.get(i).div(m));
}
return out;
}

/**
* Static method to get a complex row matrix from the complex vector passed as parameter. <p>
* @param v
* A complex vector object.
* <p>
* @return A row complex matrix builded from the complex vector passed as parameter or null if the operation cannot be done.
*
*/
public static ComplexMatrix toRowMatrix(ComplexVector v)
{
if(v == null) return null;
int n = v.size();
ComplexMatrix out = new ComplexMatrix(1, n);
for(int i = 0; i < n; i++)
{
out.set(0, i, (ComplexNumber)v.get(i).clone());
}
return out;
}

/**
* Static method to get a complex column matrix from the complex vector passed as parameter. <p>
* @param v
* A complex vector object.
* <p>
* @return A complex column matrix builded from the complex vector passed as parameter or null if the operation cannot be done.
*
*/
public static ComplexMatrix toColumnMatrix(ComplexVector v)
{
	if(v == null) return null;
	int n = v.size();
	ComplexMatrix out = new ComplexMatrix(n, 1);
	for(int i = 0; i < n; i++)
	{
	out.set(i, 0, (ComplexNumber)v.get(i).clone());
	}
	return out;
}


{
_size = 0;
_cvector = null;
}

private int _size;
private ComplexNumber[] _cvector;

}

// END
