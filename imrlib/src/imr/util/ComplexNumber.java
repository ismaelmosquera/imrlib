/*
 * Copyright (c) 2023-2024 Ismael Mosquera Rivera
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
* ComplexNumber.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

@SuppressWarnings("unchecked")

/**
* The <code>ComplexNumber</code> class implements the most common operations applied to complex numbers.
* This class is so useful in signal processing.
* A complex number has a real part and an imaginary part:
* (a + bi)
* You have to take in account that the imaginary unit is sqrt(-1) which is just a mathematical artifact
* to represent it, since sqrt(-1) is not defined in the set of real numbers.
* That is, if you attempt to compute such a function, your system will crash, like if you attempt to
* divide by 0 since it is another operation not defined in mathematics.
* So, the complex number set was invented in order to solve that mathematical issue.
*
* @author Ismael Mosquera Rivera
*/
public final class ComplexNumber
{

/**
* Constructor.
* Makes a new instance for a <code>ComplexNumber</code> object.
* Default params:
* real = 1.0f
* imag = 1.0f
*
*/
public ComplexNumber()
{
this(1.0f, 1.0f);
}

/**
* Constructor.
* Makes a new instance for a <code>ComplexNumber</code> object.
* @param real Real part.
* @param imag imaginary part.
*
*/
public ComplexNumber(float real, float imag)
{
_real = real;
_imag = imag;
}

/**
* Constructor.
* Makes a new instance for a <code>ComplexNumber</code> object.
* This is a copy constructor.
* Actually, using this constructor is like to make a clone of the object passed as parameter.
* @param c Complex number from instantiate a new one.
*
*/
public ComplexNumber(final ComplexNumber c)
{
_real = c.getReal();
_imag = c.getImag();
}

/**
* Gets the real part of this <code>ComplexNumber</code> object.
*
* @return real part.
*
*/
public float getReal()
{
return _real;
}

/**
* Gets the imaginary part of this <code>ComplexNumber</code> object.
*
* @return imaginary part.
*
*/
public float getImag()
{
return _imag;
}

/**
* Sets the real part for this <code>ComplexNumber</code> object.
* @param real Real part.
*
*/
public void setReal(float real)
{
_real = real;
}

/**
* Sets the imaginary part for this <code>ComplexNumber</code> object.
* @param imag Imaginary part.
*
*/
public void setImag(float imag)
{
_imag = imag;
}

/**
* Prints this complex number to the console.
* The format is the same as the one returned by the
* <code>String toString()</code> method overriding the one inherited from Object.
*
* ( a, bi )
*
*/
public void print()
{
System.out.println(this);
}

/**
* Loads a <code>ComplexNumber</code> object from a file.
* @param filename File from load the data.
*
* Format:
* [real] [imag]
* example:
* 2.0 9.0
*
* We take the *.dat extension for that kind of file.
*
* @return this
*
*/
public ComplexNumber load(String filename)
{
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	in.useLocale(Locale.US);
	_real = in.nextFloat();
_imag = in.nextFloat();
}
catch(FileNotFoundException e)
{
	System.err.printf("ComplexNumber FileNotFoundException: %s file not found.%n",filename);
}
finally
{
		if(in != null) in.close();
}
return this;
}

/**
* Stores this complex number to a file.
* @param filename File to store the data.
*
* Format:
* [real] [imag]
* example:
* 0.5 1.0
*
* We take the *.dat extension for that kind of file.
*
*/
public void store(String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	out.println(_real+" "+_imag);
}
catch(IOException e)
{
	System.err.println("ComplexNumber IOException: "+e);
}
finally
{
		if(out != null) out.close();
}
}

/**
* Adds a complex number to this one.
* @param c Complex number to be added.
*
* @return this + c
*
*/
public ComplexNumber add(ComplexNumber c)
{
return new ComplexNumber(_real+c.getReal(), _imag+c.getImag());
}

/**
* Substracts a complex number to this one.
* @param c Complex number to be substracted.
*
* @return this - c
*
*/
public ComplexNumber sub(ComplexNumber c)
{
return new ComplexNumber(_real-c.getReal(), _imag-c.getImag());
}

/**
* Multiplies a complex number by this one.
* @param c Complex number to multiply this one.
*
* @return this * c
*
*/
public ComplexNumber mul(ComplexNumber c)
{
float real = _real*c.getReal()-_imag*c.getImag();
float imag = _real*c.getImag()+_imag*c.getReal();
return new ComplexNumber(real, imag);
}

/**
* Divides this complex number by the one passed as parameter.
* @param c Complex number to divide this one.
*
* @return this / c
*
*/
public ComplexNumber div(ComplexNumber c)
{
	        return this.mul(c.reciprocal());
}

/**
* Scales this complex number by a factor.
* @param factor Value to scale this complex number.
*
* @return scaled complex number
*
*/
public ComplexNumber scale(float factor)
{
return new ComplexNumber(_real*factor, _imag*factor);
}

/**
* Gets the magnitude of this complex number.
*
* @return magnitude
*
*/
public float magnitude()
{
return (float)(Math.hypot(_real, _imag));
}

/**
* Gets the argument of this complex number expressed in radians.
*
* @return argument ( angle of the magnitude vector respect the real axis ).
*
*/
public float argument()
{
return (float)(Math.atan2(_imag, _real));
}

/**
* Gets the conjugated of this complex number.
*
* @return conjugated complex number
*
*/
public ComplexNumber conjugated()
{
return new ComplexNumber(_real, -_imag);
}

/**
* Gets the reciprocal of this complex number.
*
* @return reciprocal complex number
*
*/
public ComplexNumber reciprocal()
{
float sq = _real*_real+_imag*_imag;
return new ComplexNumber(_real/sq, -_imag/sq);
}

/**
* Gets the sine of this complex number.
*
* @return sine complex number
*
*/
public ComplexNumber sin()
{
	return new ComplexNumber((float)(Math.sin(_real)*Math.cosh(_imag)), (float)(Math.cos(_real)*Math.sinh(_imag)));
}

/**
* Gets the cosine of this complex number.
*
* @return cosine complex number
*
*/
public ComplexNumber cos()
{
	return new ComplexNumber((float)(Math.cos(_real)*Math.cosh(_imag)), (float)(-Math.sin(_real)*Math.sinh(_imag)));
}

/**
* Gets the tangent of this complex number.
*
* @return tangent complex number
*
*/
public ComplexNumber tan()
{
	return this.sin().div(this.cos());
}

/**
* Gets the exponential of this complex number.
*
* @return exponential complex number
*
*/
public ComplexNumber exp()
{
	return new ComplexNumber((float)(Math.exp(_real)*Math.cos(_imag)), (float)(Math.exp(_real)*Math.sin(_imag)));
}

/*
* Converts this ComplexNumber to polar form.
* <p>
* @return ComplexNumber converted to polar form.
*
*/
public PolarNumber toPolar()
{
return new PolarNumber(this.magnitude(), this.argument());
}

/**
** Gets a <code>ComplexNumber</code> array built from a floating number point vector.
* This method is so useful, mainly, to prepare a signal chunk to compute its FFT.
* It ensures that the returned array of complex numbers will be a power of 2.
* It performs zero padding if necessary.
* In case of zero padding, an extra <code>ComplexNumber</code> object is added to the end of the returned array,
* which acts as a sentinel storing the zero padding quantity added in order that the returned array is, actually, a power of 2 length.
*
* @param v Floating point vector.
*
* @return the prepared array of complex numbers.
*
* @see imr.sound.audio.analysis.Fourier
*
*/
public static ComplexNumber[] getComplexNumberArray(float[] v)
{
int old_size = v.length;
int n = 1 + (int)(Math.log((double)old_size) / Math.log(2.0));
int new_size = (int)(Math.pow(2.0, (double)n));
	int zp = new_size - old_size;
	v = (float[])iArray.resize(v, new_size);
	for(int i = old_size; i < new_size; i++) v[i] = 0.0f;
ComplexNumber[] out = new ComplexNumber[new_size+1];
for(int i = 0; i < new_size; i++)
{
	out[i] = new ComplexNumber(v[i], 0.0f);
}
out[new_size] = new ComplexNumber(0.0f, (float)zp);
return out;
}

// Override some methods inherited from Object.

/**
* Gets a clone of this complex number.
* Take in account that you must cast to <code>ComplexNumber</code> object in order to get the desired result.
* Example:
* ComplexNumber z = (ComplexNumber)x.clone();
*
* @return A clone of this complex number
*
*/
public Object clone()
{
return new ComplexNumber(_real, _imag);
}

/**
* Evaluates this complex number for equality with the one passed as parameter.
* @param obj A complex number to ve evaluated.
*
* @return true if equal or false otherwise
*
*/
public boolean equals(Object obj)
{
ComplexNumber c = (ComplexNumber)obj;
return (_real == c.getReal() && _imag == c.getImag());
}

/**
* Gets a string representation for this complex number.
* Format:
* ( a, bi )
*
* @return A string representing this complex number
*
*/
public String toString()
{
NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
	formatter.setMinimumFractionDigits(2);
	formatter.setMaximumFractionDigits(2);
String s = "( "+formatter.format(_real)+", "+formatter.format(_imag)+"i )";
return s;
}


private float _real;
private float _imag;

}

// END
