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
* iArray.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")

/**
* The <code>iArray</code> class defines static methods to manipulate one, two or three dimensional
* arrays of any kind of type included the atomic ones. You can:
* <ul>
* <li>get a chunk from an array</li>
* <li>resize an array</li>
* <li>clone an array</li>
* <li>print the content of an array</li>
* </ul>
* This class uses reflection in the implementation.
* @author Ismael Mosquera Rivera
*/
public class iArray
{

/**
* Gets a chunk from a one-dimensional array.
* @param a a one dimensional array.
* @param fromIndex
* @param toIndex
* @return an array which content is the chunk extracted from the array passed as parameter.
* <p>
* Example:
* <p>
* If you have an array of int and want to get a chunk from index 2 to index 4
* <p>
* <code>
* <pre>
* int[] myArrayOfInt = ...;
* int[] chunk = (int[])iArray.get(myArrayOfInt,2,4);
* </pre>
* </code>
*/
	public static Object get(Object a,int fromIndex,int toIndex)
	{
	Class cl = a.getClass();
	if(!cl.isArray()) return null;
	int size = (toIndex-fromIndex)+1;
	Class componentType = cl.getComponentType();
	Object newArray = Array.newInstance(componentType,size);
	System.arraycopy(a,fromIndex,newArray,0,size);
	return newArray;
	}

/**
* Gets a chunk from a two-dimensional array.
* @param m a two-dimensional array.
* @param fromRow
* @param toRow
* @param fromCol
* @param toCol
* @return a two-dimensional array with the content of the chunk extracted from the array passed as parameter.
* <p>
* Example:
* <p>
* If you have a matrix and want to get a chunk from row 0 to row 3 and from col 1 to col 2
* <p>
* <code>
* <pre>
* double[][] myMatrix = ...;
* double[][] chunk = (double[][])iArray.get(myMatrix,0,3,1,2);
* </pre>
* </code>
*/
public static Object get(Object m,int fromRow,int toRow,int fromCol,int toCol)
{
Class cl = m.getClass();
Class cl2 = Array.get(m,0).getClass();
if(!cl.isArray()||!cl2.isArray()) return null;
int rows = (toRow-fromRow)+1;
int cols = (toCol-fromCol)+1;
Class componentType = cl.getComponentType();
Class componentType2 = cl2.getComponentType();
Object newArray = Array.newInstance(componentType,rows);
for(int i=0;i<rows;i++)
{
Array.set(newArray,i,Array.newInstance(componentType2,cols));
}
for(int i=fromRow;i<=toRow;i++)
{
System.arraycopy(Array.get(m,i),fromCol,Array.get(newArray,i-fromRow),0,cols);
}
return newArray;
}

/**
* Gets a chunk from a three-dimensional array.
* @param c a three-dimensional array.
* @param fromI
* @param toI
* @param fromJ
* @param toJ
* @param fromK
* @param toK
* @return a three-dimensional array with the content of the chunk extracted from the array passed as parameter.
* <p>
* Example:
* <p>
* If you have a cube and want to get a chunk from row 1 to row 5 from col 3 to col 7 and from depth 0 to depth 4
* <p>
* <code>
* <pre>
* float[][][] myCube = ...;
* float[][][] chunk = (float[][][])iArray.get(myCube,1,5,3,7,0,4);
* </pre>
* </code>
*/
public static Object get(Object c,int fromI,int toI,int fromJ,int toJ,int fromK,int toK)
{
	Class cl = c.getClass();
		Class cl2 = Array.get(c,0).getClass();
		Class cl3 = Array.get(Array.get(c,0),0).getClass();
		if(!cl.isArray()||!cl2.isArray()||!cl3.isArray()) return null;
	int sizeI = (toI-fromI)+1;
	int sizeJ = (toJ-fromJ)+1;
	int sizeK = (toK-fromK)+1;
	Class componentType = cl.getComponentType();
		Class componentType2 = cl2.getComponentType();
		Class componentType3 = cl3.getComponentType();
		Object newArray = Array.newInstance(componentType,sizeI);
		for(int s=0;s<sizeI;s++)
		{
			Array.set(newArray,s,Array.newInstance(componentType2,sizeJ));
		}
		for(int s=0;s<sizeI;s++)
		{
		for(int t=0;t<sizeJ;t++)
		{
			Array.set(Array.get(newArray,s),t,Array.newInstance(componentType3,sizeK));
		}
		}
		for(int s=fromI;s<=toI;s++)
		{
			for(int t=fromJ;t<=toJ;t++)
			{
				System.arraycopy(Array.get(Array.get(c,s),t),fromK,Array.get(Array.get(newArray,s-fromI),t-fromJ),0,sizeK);
			}
		}
		return newArray;
}

/**
* Makes a clone of the array passed as parameter.
* It can be one, two or three-dimensional.
* @param a an array to clone.
* @return the cloned array.
* <p>
* Example:
* <p>
* If you have an array of object of any class and want to clone it.
* <p>
* <code>
* <pre>
* MyClass[][] myMatrix = ...;
* MyClass[][] clonedMatrix = (MyClass[][])iArray.clone(myMatrix);
* </pre>
* </code>
*/
public static Object clone(Object a)
{
Class cl1 = a.getClass();
if(!cl1.isArray()) return null;
boolean vector = true;
boolean matrix = false;
boolean cube = false;
int size1 = Array.getLength(a);
int size2 = 0;
int size3 = 0;
Class cl2 = Array.get(a,0).getClass();
if(cl2.isArray())
{
vector = false;
matrix = true;
size2 = Array.getLength(Array.get(a,0));
}
if(matrix)
{
Class cl3 = Array.get(Array.get(a,0),0).getClass();
if(cl3.isArray())
{
	vector = false;
	matrix = false;
	cube = true;
	size3 = Array.getLength(Array.get(Array.get(a,0),0));
}
}
if(vector) return get(a,0,size1-1);
else if(matrix) return get(a,0,size1-1,0,size2-1);
else return get(a,0,size1-1,0,size2-1,0,size3-1);
}

/**
* Resizes the one-dimensional array passed as parameter.
* @param a one-dimensional array.
* @param newSize
* @return the resized array.
* The array passed as parameter does not change unless you asign the result to itself.
* <p>
Example:
* <p>
* <code>
* <pre>
* byte[] byteVector = ...;
* byte[] resizedByteVector = (byte[])iArray.resize(byteVector,12);
* </pre>
* </code>
* <p>
* If the size is greater than the original array, new positions are set to zero.
* If the size is equal to the original You will get a copy of the array.
* If the size is lower, you will get a chunk from 0 to newSize-1 from the original array.
*/
public static Object resize(Object a,int newSize)
{
Class cl = a.getClass();
if(!cl.isArray()) return null;
Class componentType = cl.getComponentType();
int inputSize = Array.getLength(a);
int copySize = (newSize<inputSize) ? newSize : inputSize;
Object newArray = Array.newInstance(componentType,newSize);
System.arraycopy(a,0,newArray,0,copySize);
return newArray;
}

/**
* Resizes the two-dimensional array passed as parameter.
* @param m a two-dimensional array.
* @param rows
* @param cols
* @return the two-dimensional resized array.
* The array passed as parameter does not change unless you asign the result to itself.
* <p>
* Example:
* <p>
* <code>
* <pre>
* short[][] myShortMatrix = ...;
* short[][] resizedShortMatrix = (short[][])iArray.resize(myShortMatrix,7,7);
* </pre>
* </code>
*/
public static Object resize(Object m,int rows,int cols)
{
	Class cl = m.getClass();
	Class cl2 = Array.get(m,0).getClass();
	if(!cl.isArray()||!cl2.isArray()) return null;
	int inputRows = Array.getLength(m);
	int inputCols = Array.getLength(Array.get(m,0));
	int copyRows =(rows<inputRows) ? rows : inputRows;
	int copyCols = (cols<inputCols) ? cols : inputCols;
	Class componentType = cl.getComponentType();
	Class componentType2 = cl2.getComponentType();
	Object newArray = Array.newInstance(componentType,rows);
for(int i=0;i<rows;i++)
{
Array.set(newArray,i,Array.newInstance(componentType2,cols));
}
for(int i=0;i<copyRows;i++)
{
	System.arraycopy(Array.get(m,i),0,Array.get(newArray,i),0,copyCols);
}
	return newArray;
}

/**
* Resizes the three-dimensional array passed as parameter.
* @param i
* @param j
* @param k
* @return the three-dimensional resized array.
* The array passed as parameter does not change unless you asign the result to itself.
* <p>
* Example:
* <p>
* <code>
* <pre>
* MyClass[][][] myCube = ...;
* MyClass[][][] resizedCube = (MyClass[][][])iArray.resize(mycube,12,8,10);
* </pre>
* </code>
*/
public static Object resize(Object c,int i,int j,int k)
{
	Class cl = c.getClass();
	Class cl2 = Array.get(c,0).getClass();
	Class cl3 = Array.get(Array.get(c,0),0).getClass();
	if(!cl.isArray()||!cl2.isArray()||!cl3.isArray()) return null;
	int inputI = Array.getLength(c);
	int inputJ = Array.getLength(Array.get(c,0));
	int inputK = Array.getLength(Array.get(Array.get(c,0),0));
	int copyI = (i<inputI) ? i : inputI;
	int copyJ = (j<inputJ) ? j : inputJ;
	int copyK = (k<inputK) ? k : inputK;
	Class componentType = cl.getComponentType();
	Class componentType2 = cl2.getComponentType();
	Class componentType3 = cl3.getComponentType();
	Object newArray = Array.newInstance(componentType,i);
	for(int s=0;s<i;s++)
	{
		Array.set(newArray,s,Array.newInstance(componentType2,j));
	}
	for(int s=0;s<i;s++)
	{
	for(int t=0;t<j;t++)
	{
		Array.set(Array.get(newArray,s),t,Array.newInstance(componentType3,k));
	}
	}
	for(int s=0;s<copyI;s++)
	{
		for(int t=0;t<copyJ;t++)
		{
			System.arraycopy(Array.get(Array.get(c,s),t),0,Array.get(Array.get(newArray,s),t),0,copyK);
		}
	}
	return newArray;
}

/**
* Prints the content of the array passed as parameter.
* It can be one,two, or three-dimensional.
* @param a an array.
* @param label a text label to show.
* <p>
* Example:
* <p>
* If you have an array and want to print it.
* <p>
* <code>
* <pre>
* long[] myArray = ...;
* iArray.print(myArray, "vector");
* </pre>
* </code>
*/
public static void print(Object a,String label)
{
Class cl1 = a.getClass();
if(!cl1.isArray()) return;
boolean vector = true;
boolean matrix = false;
boolean cube = false;
int size1 = Array.getLength(a);
int size2 = 0;
int size3 = 0;
Class cl2 = Array.get(a,0).getClass();
if(cl2.isArray())
{
vector = false;
matrix = true;
size2 = Array.getLength(Array.get(a,0));
}
if(matrix)
{
Class cl3 = Array.get(Array.get(a,0),0).getClass();
if(cl3.isArray())
{
	vector = false;
	matrix = false;
	cube = true;
	size3 = Array.getLength(Array.get(Array.get(a,0),0));
}
}
if(vector)
{
	System.out.print(label+"[");
	for(int i=0;i<size1;i++)
	{
	if(i>0) System.out.print(", ");
	System.out.print(Array.get(a,i).toString());
	}
	System.out.print("]");
	System.out.println();
}
else if(matrix)
{
	System.out.println(label);
	for(int i=0;i<size1;i++)
	{
		System.out.print("[");
		for(int j=0;j<size2;j++)
		{
		if(j>0) System.out.print(", ");
		System.out.print(Array.get(Array.get(a,i),j).toString());
		}
		System.out.print("]");
		System.out.println();
	}
}
else
{
	for(int i=0;i<size1;i++)
	{
		System.out.println(label+": slice #"+(i+1));
		for(int j=0;j<size2;j++)
		{
			System.out.print("[");
			for(int k=0;k<size3;k++)
			{
			if(k>0) System.out.print(", ");
			System.out.print(Array.get(Array.get(Array.get(a,i),j),k).toString());
			}
			System.out.print("]");
			System.out.println();
		}
	}
}
}

private iArray(){}
}

// END
