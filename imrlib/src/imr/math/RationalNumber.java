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
* RationalNumber.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* The <code>RationalNumber</code> class offers functionallity to operate with rational numbers.
* <p>
* The result of any operation is also a rational number.
* <p>
* A rational number is a number with the form: a/b where a and b are integer numbers.
* <p>
* To know more about it, see the API documentation.
* <p>
* This class uses assertions; to enable assertions use the '-ea' modifier when executing:
* <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
@SuppressWarnings("unchecked")
public final class RationalNumber
{

/**
* Constructor.
* <p>
* Makes a new instance for a rational number.
* <p>
* by default, numerator = 0 and denominator = 1
*
*/
public RationalNumber()
{
_numerator = 0;
_denominator = 1;
}

/**
* Constructor.
* <p>
* Makes a new instance for a rational number.
* <p>
* @param numerator
* An integer value.
* <p>
* @param denominator
* An integer distinct of 0.
*
*/
public RationalNumber(int numerator, int denominator)
{
assert (denominator != 0): "RationalNumber constructor: Bad parameter, denominator must be not zero.";
_numerator = numerator;
_denominator = denominator;
}

/**
* Constructor.
* <p>
* This is a copy constructor.
* <p>
* @param q
* A rational number object.
*
*/
public RationalNumber(RationalNumber q)
{
_numerator = q.getNumerator();
_denominator = q.getDenominator();
}

/**
* Gets the numerator from this rational number.
* <p>
* @return numerator.
*
*/
public int getNumerator()
{
return _numerator;
}

/**
* Gets the denominator from this rational number.
* <p>
* @return denominator.
*
*/
public int getDenominator()
{
	return _denominator;
}

/**
* Sets this rational number.
* <p>
* @param numerator
* An integer value.
* <p>
* @param denominator
* An integer value.
*
*/
public void set(int numerator, int denominator)
{
	assert (denominator != 0): "RationalNumber set: Bad parameter, denominator must be not zero.";
_numerator = numerator;
_denominator = denominator;
}

/**
* Sets the numerator for this rational number.
* <p>
* @param numerator
* An integer value.
*
*/
public void setNumerator(int numerator)
{
	_numerator = numerator;
}

/**
* Sets the denominator for this rational number.
* <p>
* @param denominator
* An integer value.
*
*/
public void setDenominator(int denominator)
{
	assert (denominator != 0): "RationalNumber setDenominator: Bad parameter, denominator must be not zero.";
	_denominator = denominator;
}

/**
* Prints this rational number to the console.
*
*/
public void print()
{
System.out.println(this);
}

/**
* Loads a <code>RationalNumber</code> object from a file.
* <p>
* @param filename File from load the data.
* <p>
* Format:
* <p>
* [numerator] [denominator]
* <p>
* Example:
* <p>
* 2 9
* <p>
* We take the *.dat extension for that kind of file.
* <p>
* @return this
*
*/
public RationalNumber load(String filename)
{
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	_numerator = in.nextInt();
_denominator = in.nextInt();
}
catch(FileNotFoundException e)
{
	System.err.printf("RationalNumber FileNotFoundException: %s file not found.%n",filename);
}
finally
{
		if(in != null) in.close();
}
return this;
}

/**
* Stores this rational number to a file.
* <p>
* @param filename
* File to store the data.
* <p>
* Format:
* <p>
* [numerator] [denominator]
* <p>
* Example:
* <p>
* 5 1
* <p>
* We take the *.dat extension for that kind of file.
*
*/
public void store(String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	out.println(_numerator+" "+_denominator);
}
catch(IOException e)
{
	System.err.println("RationalNumber IOException: "+e);
}
finally
{
		if(out != null) out.close();
}
}

/**
* Adds this rational number to the one passed as parameter.
* <p>
* @param q
* A rational number object.
* <p>
* @return this + q
*
*/
public RationalNumber add(RationalNumber q)
{
	return add(this, q);
}

/**
* Substracts the rational number passed as parameter to this one.
* <p>
* @param q
* A rational number object.
* <p>
* @return this - q
*
*/
public RationalNumber sub(RationalNumber q)
{
return sub(this, q);
}

/**
* Multiplies this rational number by the one passed as parameter.
* <p>
* @param q
* A rational number object.
* <p>
* @return this * q
*
*/
public RationalNumber mul(RationalNumber q)
{
	return mul(this, q);
}

/**
* Divides this rational number by the one passed as parameter.
* <p>
* @param q
* A rational number object.
* <p>
* @return this / q
*
*/
public RationalNumber div(RationalNumber q)
{
return div(this, q);
}

/**
* Raises this rational number to the power passed as parameter.
* <p>
* @param p
* An integer value ( power to raise this rational number ).
* <p>
* @return this^p
*
*/
public RationalNumber pow(int p)
{
	return pow(this, p);
}

/**
* Simplifies this rational number.
* <p>
* @return simplified rational.
*
*/
public RationalNumber reduce()
{
return reduce(this);
}

/**
* Gets the floating-point value related to this rational number.
* <p>
* @return a/b
*
*/
public float value()
{
	return value(this);
}

/**
* Evaluates if this rational is proper.
* <p>
* @return true if proper or false otherwise.
*
*/
public boolean isProper()
{
return isProper(this);
}


// Static methods

/**
* Adds two rational numbers.
* <p>
* @param q1
* A rational number object.
* <p>
* @param q2
* A rational number object.
* <p>
* @return q1 + q2
*
*/
public static RationalNumber add(RationalNumber q1, RationalNumber q2)
{
	int num = q2.getDenominator()*q1.getNumerator() + q1.getDenominator()*q2.getNumerator();
int den = q1.getDenominator() * q2.getDenominator();
return new RationalNumber(num, den);
}

/**
* Substracts two rational numbers.
* <p>
* @param q1
* A rational number object.
* <p>
* @param q2
* A rational number object.
* <p>
* @return q1 - q2
*
*/
public static RationalNumber sub(RationalNumber q1, RationalNumber q2)
{
	int num = q2.getDenominator()*q1.getNumerator() - q1.getDenominator()*q2.getNumerator();
int den = q1.getDenominator() * q2.getDenominator();
return new RationalNumber(num, den);
}

/**
* Multiplies two rational numbers.
* <p>
* @param q1
* A rational number object.
* <p>
* @param q2
* A rational number object.
* <p>
* @return q1 * q2
*
*/
public static RationalNumber mul(RationalNumber q1, RationalNumber q2)
{
	int num = q1.getNumerator() * q2.getNumerator();
	int den = q1.getDenominator() * q2.getDenominator();
	return new RationalNumber(num, den);
}

/**
* Divides two rational numbers.
* <p>
* @param q1
* A rational number object.
* <p>
* @param q2
* A rational number object.
* <p>
* @return q1 / q2
*
*/
public static RationalNumber div(RationalNumber q1, RationalNumber q2)
{
	int num = q1.getNumerator() * q2.getDenominator();
	int den = q1.getDenominator() * q2.getNumerator();
	return new RationalNumber(num, den);
}

/**
* Raises the rational number passed as first parameter to the power passed as second parameter.
* <p>
* @param q
* A rational number object.
* <p>
* @param p
* An integer value ( power to raise the rational ).
* <p>
* @return q^p
*
*/
public static RationalNumber pow(RationalNumber q, int p)
{
int num = 0;
int den = 1;
if(p == 0)
{
	num = 1;
	den = 1;
}else if(p == 1)
{
	num = q.getNumerator();
	den = q.getDenominator();
}else if(p < 0)
{
num = (int)(Math.pow((double)q.getDenominator(), (double)Math.abs((double)p)));
den = (int)(Math.pow((double)q.getNumerator(), (double)Math.abs((double)p)));
}else
{
num = (int)(Math.pow((double)q.getNumerator(), (double)p));
den = (int)(Math.pow((double)q.getDenominator(), (double)p));
}
return new RationalNumber(num, den);
}

/**
* Simplifies the rational number passed as parameter.
* <p>
* @param q
* A rational number object.
* <p>
* @return simplified rational number.
*
*/
public static RationalNumber reduce(RationalNumber q)
{
int a = q.getNumerator();
int b = q.getDenominator();
int d = GCD.compute(a, b);
return new RationalNumber(a/d, b/d);
}

/**
* Gets the value ( a/b ) for the  rational number passed as parameter.
* <p>
* @param q
* A rational number object.
* <p>
* @return a/b
*
*/
public static float value(RationalNumber q)
{
return (float)q.getNumerator() / (float)q.getDenominator();
}

/**
* Evaluates if the rational number passed as parameter is proper or not.
* <p>
* @param q
* A rational number object.
* <p>
* @return true if proper or false otherwise.
*
*/
public static boolean isProper(RationalNumber q)
{
	return (q.getNumerator() <= q.getDenominator());
}

/**
* Loads a rational number array from a file.
* <p>
* File format:
* <p>
* #size ( array size ) <p>
* n0 d0 <p>
* n1 d1 <p>
* .. <p>
* ni di <p>
* where i = size-1
* <p>
* @param filename
* A file from to load the rational number array.
* <p>
* @return loaded rational number array.
*
*/
public static RationalNumber[] loadRationalArray(String filename)
{
	RationalNumber[] out = null;
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	int n = in.nextInt();
	out = new RationalNumber[n];
	int num = 0;
	int den = 1;
	for(int i = 0; i < n; i++)
	{
		num = in.nextInt();
		den = in.nextInt();
		out[i] = new RationalNumber(num, den);
	}
}
catch(FileNotFoundException e)
{
	System.err.printf("RationalNumber FileNotFoundException: %s file not found.%n",filename);
}
finally
{
		if(in != null) in.close();
}
return out;
}

/**
* Stores a rational number array to a file.
* <p>
* File format:
* <p>
* #size ( array size ) <p>
* n0 d0 <p>
* n1 d1 <p>
* .. <p>
* ni di <p>
* where i = size-1
* <p>
* @param qarray
* A rational number array to be stored.
* <p>
* @param filename
* A file to store the rational number array.
*
*/
public static void storeRationalArray(RationalNumber[] qarray, String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	int num = 0;
	int den = 1;
	int n = qarray.length;
	out.println(n);
	for(int i = 0; i < n; i++)
	{
	num = qarray[i].getNumerator();
	den = qarray[i].getDenominator();
		out.println(num+" "+den);
	}
}
catch(IOException e)
{
	System.err.println("RationalNumber IOException: "+e);
}
finally
{
		if(out != null) out.close();
}
}

/**
* Prints a rational number array to the console.
* <p>
* @param qarray
* A rational number array.
*
*/
public static void print(RationalNumber[] qarray)
{
if(qarray == null || qarray.length == 0)
{
System.out.println("[]");
return;
}
System.out.print("[");
for(int i = 0; i < qarray.length; i++)
{
if(i > 0) System.out.print(", ");
System.out.print(qarray[i]);
}
System.out.println("]");
}


// Override some methods inherited from Object

/**
* Makes a clone of this rational number.
* <p>
* Since this method returns Object, you must cast it in order to get the desired object.
* <p>
* <code>RationalNumber q = (RationalNumber)rn.clone()</code>
* <p>
* @return A clone of this rational number.
*
*/
public Object clone()
{
return new RationalNumber(_numerator, _denominator);
}

/**
* Evaluates for equality.
* <p>
* @param object
* An instance of a rational number to evaluate for equality.
* <p>
* @return true if equals or false otherwise.
*
*/
public boolean equals(Object object)
{
RationalNumber q = (RationalNumber)object;
return (_numerator*q.getDenominator() == _denominator*q.getNumerator());
}

/**
* Gets a string representation of this rational number.
* <p>
* @return a string representation for this rational number.
*
*/
public String toString()
{
String s = "";
s += _numerator + "/" + _denominator;
return s;
}


private int _numerator;
private int _denominator;

}

// END
