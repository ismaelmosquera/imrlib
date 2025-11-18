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
* Vector.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.matrix;

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
* The <code>Vector</code> class defines the most common methods applied to vectors.
* In addition, methods to print, load a vector from a file
* and store the content of a vector into a file are also implemented.
* @author Ismael Mosquera Rivera
*/
public final class Vector
{

/**
* Makes a new instance of a <code>Vector</code> object.
* @param size number of elements.
* The object is initialized having size elements and all positions are set to zero.
*/
public Vector(int size)
{
this.size = size;
if(this.size < 1) this.size = 1;
vector = new double[this.size];
for(int i=0;i<this.size;i++) vector[i] = 0.0;
}

/**
* Makes a new instance of a <code>Vector</code> object.
* @param fileName a well formatted file.
* The vector is initialized according the information stored in the file passed as parameter.
* <p>
* The format is as follows:
* <code>
* #elements
v0 v1 v2...vn
* </code>
*
* Example.
*
* A 10 elements vector file:
*
* <code>
* 10
3 4 5 8 6 0 1 0 9 4
* </code>
*/
public Vector(String fileName)
{
load(fileName);
}

/**
* shows the content of a vector
*/
public void print()
{
NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
formatter.setMinimumFractionDigits(2);
formatter.setMaximumFractionDigits(2);
System.out.print("[");
for(int i=0;i<size;i++)
{
	if(i>0) System.out.print(", ");
	System.out.print(formatter.format(vector[i]));
}
System.out.print("]");
System.out.println();
}

/**
* Loads a vector from a file.
* @param fileName a well formatted file.
* The vector is initialized according to the information stored in the file passed as parameter.
* <p>
* To know how such a file format is, see the constructor's section.
*/
public void load(String fileName)
{
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(fileName)));
	in.useLocale(Locale.US);
	size = in.nextInt();
	if(vector == null)
	{
		vector = new double[size];
	}
	else
	{
	resize(size);
	}
	for(int i=0;i<size;i++) vector[i] = in.nextDouble();
}
catch(FileNotFoundException e)
{
	System.err.printf("%s file not found.",fileName);
}
finally
{
	if(in != null) in.close();
}
}

/**
* Stores the content of a vector into a file.
* @param fileName file to write.
* <p>
* To know how such a file format is, see the constructor's section.
*/
public void store(String fileName)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(fileName);
	out.println(size);
	for(int i=0;i<size;i++)
	{
		if(i>0) out.print(" ");
		out.print(vector[i]);
	}
	out.println();
}
catch(IOException e)
{
	System.err.println("Vector IOException: "+e);
}
finally
{
	if(out != null) out.close();
}
}

/**
* Gets a value from a vector.
* @param i index
* @return the value stored in the index position.
*/
public double get(int i)
{
return vector[i];
}

/**
* Sets a value to the specified position.
* @param i index position.
* @param value a value to set.
*/
public void set(int i,double value)
{
vector[i] = value;
}

/**
* Gets the size of the vector, that is, the number of elements.
* @return the size of the vector.
*/
public int size()
{
return size;
}

/**
* Gets a chunk from a vector.
* @param fromIndex first index.
* @param toIndex last index.
* @return a vector which content is the extracted chunk.
* <p>
* If you have a vector and want to get a chunk from index 2 to 4
* <pre>
* Vector chunk = v.get(2,4);
* </pre>
*/
public Vector get(int fromIndex,int toIndex)
{
if(fromIndex < 0 || toIndex > this.size-1 || fromIndex > toIndex) return null;
int newSize = (toIndex-fromIndex)+1;
Vector r = new Vector(newSize);
double[] v = (double[])iArray.get(this.vector,fromIndex,toIndex);
for(int i=0;i < v.length;i++)
{
r.set(i,v[i]);
}
return r;
}

/**
* Resizes a vector.
* @param newSize
* <p>
* If newSize is greater than the vector to be resized, new positions are set to zero.
* <p>
* If it is lower, the vector is truncated to the new size.
*/
public void resize(int newSize)
{
size = newSize;
vector = (double[])iArray.resize(vector,size);
}

/**
* Evaluates the vector to be equal to the one passed as parameter.
* @param v vector to evaluate its equality.
* @return <code>true</code> if both vectors are equal or <code>false</code> otherwise.
*/
public boolean equals(Vector v)
{
	if(size != v.size()) return false;
	for(int i=0;i<size;i++)
	{
	if(vector[i] != v.get(i)) return false;
	}
	return true;
}

/**
* Clones a vector.
* @return the cloned vector.
* <p>
* You must cast to <code>Vector</code>
* <p>
* Vector cloned = (Vector)v.clone();
*/
public Object clone()
{
Vector v = new Vector(size);
for(int i=0;i<size;i++)
{
v.set(i,vector[i]);
}
return v;
}

/**
* Gets a string representation of the vector.
* @return a string representation of the vector.
*/
public String toString()
{
	NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
	formatter.setMinimumFractionDigits(2);
	formatter.setMaximumFractionDigits(2);
String s = "[Vector: size="+size+"]\n";
s += "[";
for(int i=0;i<size;i++)
{
if(i>0) s += ", ";
s += formatter.format(vector[i]);
}
s += "]\n";
return s;
}

/**
* Performs addition a += b
* @param v vector to add.
* <p>
* @return this + v
*
*/
public Vector add(Vector v)
{
return add(this, v);
}

/**
* Performs substraction a -= b
* @param v vector to substract.
* <p>
* @return this + v
*
*/
public Vector sub(Vector v)
{
return sub(this, v);
}

/**
* Scales a vector one-to-one by the value passed as parameter.
* @param value a value to scale.
* <p>
* @return computed vector.
*
*/
public Vector scale(double value)
{
return scale(this, value);
}

/**
* Performs the dot product a*b
* @param v vector to compute dot product.
* @return dot product a*b
*/
public double dot(Vector v)
{
if(size != v.size()) return 0.0;
double r = 0.0;
for(int i=0;i<size;i++)
{
	r += vector[i]*v.get(i);
}
return r;
}

/**
* Performs the cross product a^b
* @param v a vector to compute the cross product.
* @return cross product a^b
*/
public Vector cross(Vector v)
{
	if(size!=3 && size!=v.size()) return null;
	Vector r = new Vector(3);
 r.set(0,vector[1]*v.get(2)-vector[2]*v.get(1));
	r.set(1,vector[2]*v.get(0)-vector[0]*v.get(2));
	r.set(2,vector[0]*v.get(1)-vector[1]*v.get(0));
	return r;
}

/**
* Computes the modulo of a vector.
* @return modulo of the vector
*/
public double modulo()
{
double m = 0.0;
for(int i=0;i<size;i++)
{
	m += (vector[i]*vector[i]);
}
return Math.sqrt(m);
}

/**
* Normalizes a vector.
*/
public void normalize()
{
double m = modulo();
if(m == 0) return;
for(int i=0;i<size;i++)
{
	vector[i] /= m;
}
}

/**
* Static method to perform addition c = a+b
* @param v1 first vector.
* @param v2 second vector.
* @return v1+v2
*/
public static Vector add(Vector v1,Vector v2)
{
if(v1.size() != v2.size()) return v1;
Vector v = new Vector(v1.size());
for(int i=0;i<v.size();i++)
{
v.set(i,v1.get(i)+v2.get(i));
}
return v;
}

/**
* Static method to perform substraction c = a-b
* @param v1 first vector.
* @param v2 second vector.
* @return v1-v2
*/
public static Vector sub(Vector v1,Vector v2)
{
if(v1.size() != v2.size()) return v1;
Vector v = new Vector(v1.size());
for(int i=0;i<v.size();i++)
{
v.set(i,v1.get(i)-v2.get(i));
}
return v;
}

/**
* Static method to scale a vector one-to-one by a value.
* @param v a vector.
* @param value a value to scale.
* @return v.*value
*/
public static Vector scale(Vector v,double value)
{
	Vector r = new Vector(v.size());
	for(int i=0;i<r.size();i++)
	{
	r.set(i,v.get(i)*value);
	}
	return r;
}

/**
* Static method to normalize a vector.
* <p>
* @param v Vector to normalize.
* <p>
* @return normalized vector.
*/
public static Vector normalize(Vector v)
{
Vector out = (Vector)v.clone();
double m = out.modulo();
if(m == 0) return null;
for(int i=0;i<out.size();i++)
{
	 out.set(i, out.get(i)/m);
}
return out;
}



{
size = 0;
vector = null;
}

private int size;
private double[] vector;
}

// END

