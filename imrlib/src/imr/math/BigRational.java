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
* BigRational.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

import java.math.BigInteger;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* The <code>BigRational</code> class implements a big rational number capable to operate with big integers. <p>
* This class is so useful when we need to deal with large integer numbers as numerator and denominator in a fraction. <p>
* If you don't need to operate with big numbers, the <code>RationalNumber</code> should be better. <p>
* This class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author "Ismael Mosquera Rivera"
*
*/
@SuppressWarnings("unchecked")
public final class BigRational
{

/**
* Constructor. <p>
* Makes a new instance for a <code>BigRational</code> object. <p>
* This default constructor builds a rational = 0/1
*
*/
public BigRational()
{
_num = new BigInteger("0");
_den = new BigInteger("1");
}

/**
* Constructor. <p>
* Makes a new instance for a <code>BigRational</code> object. <p>
* @param num
* An integer value for the numerator; the denominator will be 1.
*
*/
public BigRational(Integer num)
{
_num = new BigInteger(num.toString());
_den = new BigInteger("1");
}

/**
* Constructor. <p>
* Makes a new instance for a <code>BigRational</code> object. <p>
@param num
* An integer value for the numerator.
* <p>
* @param den
* An integer value for the denominator.
*
*/
public BigRational(Integer num, Integer den)
{
_num = new BigInteger(num.toString());
_den = new BigInteger(den.toString());
}

/**
* Constructor. <p>
* Makes a new instance for a <code>BigRational</code> object. <p>
* @param num
* A string representing the value for the numerator.
* <p>
* @param den
* A string representing the value for the denominator.
*
*/
public BigRational(String num, String den)
{
_num = new BigInteger(num);
_den = new BigInteger(den);
}

/**
* Constructor. <p>
* Makes a new instance for a <code>BigRational</code> object. <p>
* This is a copy constructor. <p>
* @param q
* A <code>BigRational</code> object.
*
*/
public BigRational(BigRational q)
{
this(q.getNumerator().toString(), q.getDenominator().toString());
}

/**
* Gets the numerator of this rational. <p>
* @return Numerator.
*
*/
public BigInteger getNumerator()
{
return _num;
}

/**
* Gets the denominator of this rational. <p>
* @return Denominator.
*
*/
public BigInteger getDenominator()
{
	return _den;
}

/**
* Static method to load a <code>BigRational</code> from a file. <p>
* Format: <p>
* [num] [den]
* <p>
* We decided to use the *.dat extension for such a file, but you can use what you wish, since is a text file. <p>
* @param filename
* A string for the file where the data is.
* <p>
* @return A <code>BigRational</code> object.
*
*/
public static BigRational load(String filename)
{
	BigRational out = null;
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
out = new BigRational(in.nextBigInteger().toString(), in.nextBigInteger().toString());
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
* Static method to store a <code>BigRational</code> in a file. <p>
* Format: <p>
* [num] [den]
* <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @param filename
* A string for the file where to store the rational.
*
*/
public static void store(BigRational q, String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	out.println(q.getNumerator()+" "+q.getDenominator());
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
* Adds this rational to the one passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return this + q
*
*/
public BigRational add(BigRational q)
{
	return add(this, q);
}

/**
* Subtracts  this rational from the one passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return this - q
*
*/
public BigRational sub(BigRational q)
{
	return sub(this, q);
}

/**
* Multiplies this rational by the one passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return this * q
*
*/
public BigRational mul(BigRational q)
{
return mul(this, q);
}

/**
* Divides this rational by the one passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return this / q
*
*/
public BigRational div(BigRational q)
{
return div(this, q);
}

/**
* Gets the absolute value of this rational. <p>
* @return Absolute value of this rational.
*
*/
public BigRational abs()
{
return abs(this);
}

/**
* Negates this rational.
* <p>
* @return this rational negated.
*
*/
public BigRational negate()
{
return negate(this);
}

/**
* Gets the sign of this rational.
* <p>
* @return 0 if zero, 1 if greater than zero or -1 if less than zero.
*
*/
public int sign()
{
return sign(this);
}

/**
* Raises this rational to the integer power passed as parameter. <p>
* @param n
* An integer value for the exponent; it can be zero, less than zero or greater than zero.
* <p>
* @return this^n
*
*/
public BigRational pow(int n)
{
return pow(this, n);
}

/**
* Interpolates a rational between this one and the one passed as parameter. <p>
* @param q
* A <code>bigRational</code> object.
* <p>
* @return Interpolated rational.
*
*/
public BigRational interpolate(BigRational q)
{
return interpolate(this, q);
}

/**
* Reduces this rational to its irreducible form. <p>
* That is, the numerator and denominator of the result rational are coprimes.
* <p>
* @return Reduced rational.
*
*/
public BigRational reduce()
{
return reduce(this);
}

/**
* Gets the decimal value of this rational as a double atomic type.
* <p>
* @return Decimal value as a double atomic for this rational.
*
*/
public double value()
{
return value(this);
}

/**
* Evaluates whether this rational is proper or not.
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
* Static method to add two rationals. <p>
* @param q1
* A <code>BigRational</code> object.
* <p>
* @param q2
* A <code>bigRational</code> object.
* <p>
* @return q1 + q2
*
*/
public static BigRational add(BigRational q1, BigRational q2)
{
BigInteger den = q1.getDenominator().multiply(q2.getDenominator());
BigInteger num = q1.getNumerator().multiply(q2.getDenominator()).add(q2.getNumerator().multiply(q1.getDenominator()));
return (new BigRational(num.toString(), den.toString())).reduce();
}

/**
* Static method to subtract two rationals. <p>
* @param q1
* A <code>BigRational</code> object.
* <p>
* @param q2
* A <code>bigRational</code> object.
* <p>
* @return q1 - q2
*
*/
public static BigRational sub(BigRational q1, BigRational q2)
{
BigInteger den = q1.getDenominator().multiply(q2.getDenominator());
BigInteger num = q1.getNumerator().multiply(q2.getDenominator()).subtract(q2.getNumerator().multiply(q1.getDenominator()));
return (new BigRational(num.toString(), den.toString())).reduce();
}

/**
* Static method to multiply two rationals. <p>
* @param q1
* A <code>BigRational</code> object.
* <p>
* @param q2
* A <code>bigRational</code> object.
* <p>
* @return q1 * q2
*
*/
public static BigRational mul(BigRational q1, BigRational q2)
{
BigInteger num = q1.getNumerator().multiply(q2.getNumerator());
BigInteger den = q1.getDenominator().multiply(q2.getDenominator());
return (new BigRational(num.toString(), den.toString())).reduce();
}

/**
* Static method to divide two rationals. <p>
* @param q1
* A <code>BigRational</code> object.
* <p>
* @param q2
* A <code>bigRational</code> object.
* <p>
* @return q1 / q2
*
*/
public static BigRational div(BigRational q1, BigRational q2)
{
assert(q2.getDenominator().intValue() != 0): "BigRational -> div(...): Bad parameter; divide by zero.";
BigInteger num = q1.getNumerator().multiply(q2.getDenominator());
BigInteger den = q1.getDenominator().multiply(q2.getNumerator());
return (new BigRational(num.toString(), den.toString())).reduce();
}

/**
* Static method to get the absolute value of the rational passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return Absolute value of the rational passed as parameter.
*
*/
public static BigRational abs(BigRational q)
{
BigInteger num = q.getNumerator().abs();
BigInteger den = q.getDenominator().abs();
return (new BigRational(num.toString(), den.toString())).reduce();
}

/**
* Static method to negate the rational passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return Negated rational.
*
*/
public static BigRational negate(BigRational q)
{
return q.mul(new BigRational(-1)).reduce();
}

/**
* Static method to get the sign of the rational passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return Zero if equals zero, 1 if greater than zero or -1 if less than zero.
*
*/
public static int sign(BigRational q)
{
if(q.getNumerator().compareTo(new BigInteger("0")) == 0) return 0;
return ((q.getNumerator().compareTo(new BigInteger("0")) == -1) && (q.getDenominator().compareTo(new BigInteger("0")) == -1) || (q.getNumerator().compareTo(new BigInteger("0")) == 1) && (q.getDenominator().compareTo(new BigInteger("0")) == 1)) ? 1 : -1;
}

/**
* Static method to raise the rational passed as first parameter to the integer power passed as second parameter. <p>
* @param q
* A <code>bigRational</code> object.
* <p>
* @param n
* Exponent.
* <p>
* @return q^n
*
*/
public static BigRational pow(BigRational q, int n)
{
if(n == 0) return new BigRational(1);
if(n < 0)
{
BigRational q1 = new BigRational("1", q.getNumerator().pow(-n).toString());
BigRational q2 = new BigRational("1", q.getDenominator().pow(-n).toString());
return div(q1, q2);
}
BigInteger num = q.getNumerator().pow(n);
BigInteger den = q.getDenominator().pow(n);
return (new BigRational(num.toString(), den.toString())).reduce();
}

/**
* Static method to interpolate two rationals between the first and second ones passed as parameters. <p>
* @param q1
* A <code>BigRational</code> object.
* <p>
* @param q2
* A <code>BigRational</code> object.
* <p>
* @return Interpolated rational.
*
*/
public static BigRational interpolate(BigRational q1, BigRational q2)
{
BigRational r1 = null;
BigRational r2 = null;
if(q1.value() > q2.value())
{
r1 = (BigRational)q2.clone();
r2 = (BigRational)q1.clone();
}
else
{
	r1 = (BigRational)q1.clone();
	r2 = (BigRational)q2.clone();
}
BigRational p = r2.sub(r1).div(new BigRational(2));
BigRational r = r1.add(p);
return new BigRational(r.getNumerator().toString(), r.getDenominator().toString());
}

/**
* Static method to reduce the rational passed as parameter. <p>
* That is, after reduction both the numerator and denominator are coprimes. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return Reduced rational.
*
*/
public static BigRational reduce(BigRational q)
{
BigInteger num = q.getNumerator();
BigInteger den = q.getDenominator();
BigInteger d = num.gcd(den);
num = num.divide(d);
den = den.divide(d);
return new BigRational(num.toString(), den.toString());
}

/**
* Static method to compute the decimal value of the rational passed as parameter. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return Decimal value of the rational passed as parameter as a double atomic type.
*
*/
public static double value(BigRational q)
{
	double n = q.getNumerator().doubleValue();
	double d = q.getDenominator().doubleValue();
	return n/d;
}

/**
* Static method to evaluate whether the rational passed as parameter is proper or not. <p>
* @param q
* A <code>BigRational</code> object.
* <p>
* @return true if proper or false otherwise.
*
*/
public static boolean isProper(BigRational q)
{
	BigRational r = q.abs();
return (r.getNumerator().compareTo(r.getDenominator()) == -1);
}

/**
* Static method to print a rational array to the console. <p>
* @param qarray
* A <code>BigRational</code> array.
*
*/
public static void print(BigRational[] qarray)
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

/**
* Static method to load an array of rationals. <p>
* format: <p>
* #elements <p>
* [num] [den] <p>
* [num] [den] <p>
* ... <p>
* @param filename
* A string for the file from to load the rational array.
* <p>
* @return Rational array.
*
*/
public static BigRational[] loadArray(String filename)
{
	BigRational[] out = null;
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	int n = in.nextInt();
	out = new BigRational[n];
	for(int i = 0; i < n; i++)
	{
		out[i] = new BigRational(in.nextBigInteger().toString(), in.nextBigInteger().toString());
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
* Static method to store an array of rationals. <p>
* format: <p>
* #elements <p>
* [num] [den] <p>
* [num] [den] <p>
* ... <p>
* @param qarray
* A <code>BigRational</code> array.
* <p>
* @param filename
* A string for the file from to load the rational array.
*
*/
public static void storeArray(BigRational[] qarray, String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	int n = qarray.length;
	out.println(n);
	for(int i = 0; i < n; i++)
	{
		out.println(qarray[i].getNumerator()+" "+qarray[i].getDenominator());
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
* Makes a clone of this rational. <p>
* Notice that you must cast in order to get the required object. <p>
* <code>BigRational q = ...</code>
* <p>
* <code>BigRational r = (BigRational)q.clone()</code>
* <p>
* @return Cloned rational.
*
*/
public Object clone()
{
return new BigRational(_num.toString(), _den.toString());
}

/**
* Evaluates for equality. <p>
* Even the parameter is of class Object, you must pass a rational. <p>
* @param obj
* A <code>bigRational</code> object.
* <p>
* @return true if equal or false otherwise.
*
*/
public boolean equals(Object obj)
{
BigRational r = (BigRational)obj;
return (_num.multiply(r.getDenominator()).compareTo(_den.multiply(r.getNumerator())) == 0);
}

/**
* Gets a string representation for this rational.
* <p>
* @return A string representation for this rational.
*
*/
public String toString()
{
String s = "";
s += _num+"/"+_den;
return s;
}


// Class members
private BigInteger _num;  // munerator
private BigInteger _den;  // denominator

}

// END
