/*
 * Copyright (c) 2025 Ismael Mosquera Rivera
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
* PolarNumber.java
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
* The <code>PolarNumber</code> class offers functionallity to perform basic operations with polar numbers.
* <p>
* In addition, it also has support to storage among other things.
* <p>
* A polar number is a pair [magnitude, phase], where the magnitude represents the module of the pair,
* <p>
* and the phase or argument is the angle respect to the real axis in the complex plane.
* <p>
* @author Ismael Mosquera rivera.
*
*/
public final class PolarNumber
{

/**
* Constructor.
* Makes a new instance for a <code>PolarNumber</code> object.
* Default params:
* magnitude = 1.0f
* argument = 1.0f
*
*/
public PolarNumber()
{
this(1.0f, 1.0f);
}

/**
* Constructor.
* Makes a new instance for a <code>PolarNumber</code> object.
* @param mag Magnitude.
* @param arg Argument.
*
*/
public PolarNumber(float mag, float arg)
{
_mag = mag;
_phase = arg;
}

/**
* Constructor.
* Makes a new instance for a <code>PolarNumber</code> object.
* This is a copy constructor.
* Actually, using this constructor is like to make a clone of the object passed as parameter.
* @param p Polar number from instantiate a new one.
*
*/
public PolarNumber(final PolarNumber p)
{
_mag = p.getMagnitude();
_phase = p.getArgument();
}


/**
* Gets the magnitude of this polar number.
* <p>
* @return magnitude.
*
*/
public float getMagnitude()
{
return _mag;
}

/**
* Gets the argument of this polar number.
* <p>
* @return argument.
*/
public float getArgument()
{
return _phase;
}

/**
* Sets the magnitude for this polar number.
* <p>
* @param mag Magnitude to set.
*
*/
public void setMagnitude(float mag)
{
this._mag = mag;
}

/**
* Sets the argument for this polar number.
* <p>
* @param arg Argument to set.
*
*/
public void setArgument(float arg)
{
this._phase = arg;
}

/**
* Prints this polar number to the console.
* <p>
* The format is the same as the one returned by the
* <p>
* <code>String toString()</code> method overriding the one inherited from Object.
* <p>
* less-than mag, phase greater-than
*
*/
public void print()
{
System.out.println(this);
}

/**
* Loads a <code>PolarNumber</code> object from a file.
* <p>
* @param filename File from load the data.
* <p>
* Format:
* <p>
* [magnitude] [argument]
* <p>
* example:
* <p>
* 2.0 9.0
* <p>
* We take the *.dat extension for that kind of file.
* <p>
* but you can use any extension since it is just a text file.
* <p>
* @return this
*
*/
public PolarNumber load(String filename)
{
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	in.useLocale(Locale.US);
	_mag = in.nextFloat();
_phase = in.nextFloat();
}
catch(FileNotFoundException e)
{
	System.err.printf("PolarNumber FileNotFoundException: %s file not found.%n",filename);
}
finally
{
		if(in != null) in.close();
}
return this;
}

/**
* Stores this polar number to a file.
* <p>
@param filename File to store the data.
* <p>
* Format:
* <p>
* [magnitude] [phase]
* <p>
* example:
* <p>
* 0.5 1.0
* <p>
* We take the *.dat extension for that kind of file.
* <p>
* but you can use any extension since it is jus a text file.
*
*/
public void store(String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	out.println(_mag+" "+_phase);
}
catch(IOException e)
{
	System.err.println("PolarNumber IOException: "+e);
}
finally
{
		if(out != null) out.close();
}
}

/**
* Adds the polar number passed as parameter to this one.
* <p>
* @param p A <code>PolarNumber</code> object.
* <p>
* @return this + p
*
*/
public PolarNumber add(PolarNumber p)
{
return add(this, p);
}

/**
* Substracts the polar number passed as parameter from this one.
* <p>
* @param p A <code>PolarNumber</code> object.
* <p>
* @return this - p
*
*/
public PolarNumber sub(PolarNumber p)
{
return sub(this, p);
}

/**
* Multiplies this polar number by the one passed as parameter.
* <p>
* @param p A <code>PolarNumber</code> object.
* <p>
* @return this * p
*
*/
public PolarNumber mul(PolarNumber p)
{
return mul(this, p);
}

/**
* Divides this polar number by the one passed as parameter.
* <p>
* @param p A <code>PolarNumber</code> object.
* <p>
* @return this / p
*
*/
public PolarNumber div(PolarNumber p)
{
return div(this, p);
}


// Static methods

/**
* Adds two polar numbers.
* <p>
* @param p1 A <code>PolarNumber</code> object.
* <p>
* @param p2 A <code>PolarNumber</code> object.
* <p>
* @return p1 + p2
*
*/
public static PolarNumber add(PolarNumber p1, PolarNumber p2)
{
return p1.toComplex().add(p2.toComplex()).toPolar();
}

/**
* Substracts two polar numbers.
* <p>
* @param p1 A <code>PolarNumber</code> object.
* <p>
* @param p2 A <code>PolarNumber</code> object.
* <p>
* @return p1 - p2
*
*/
public static PolarNumber sub(PolarNumber p1, PolarNumber p2)
{
return p1.toComplex().sub(p2.toComplex()).toPolar();
}

/**
* Multiplies two polar numbers.
* <p>
* @param p1 A <code>PolarNumber</code> object.
* <p>
* @param p2 A <code>PolarNumber</code> object.
* <p>
* @return p1 * p2
*
*/
public static PolarNumber mul(PolarNumber p1, PolarNumber p2)
{
float mag = p1.getMagnitude() * p2.getMagnitude();
float phase = p1.getArgument() + p2.getArgument();
return new PolarNumber(mag, phase);
}

/**
* Divides two polar numbers.
* <p>
* @param p1 A <code>PolarNumber</code> object.
* <p>
* @param p2 A <code>PolarNumber</code> object.
* <p>
* @return p1 / p2
*
*/
public static PolarNumber div(PolarNumber p1, PolarNumber p2)
{
	float mag = p1.getMagnitude() / p2.getMagnitude();
	float phase = p1.getArgument() - p2.getArgument();
return new PolarNumber(mag, phase);
}

/**
* Converts this polar number to complex.
* <p>
* @return converted complex from this polar.
*
*/
public ComplexNumber toComplex()
{
float real = _mag * (float)Math.cos((double)_phase);
float imag = _mag * (float)Math.sin((double)_phase);
return new ComplexNumber(real, imag);
}


// Override some methods inherited from Object.

/**
* Gets a clone of this polar number.
* <p>
* Take in account that you must cast to <code>PolarNumber</code> object in order to get the desired result.
* <p>
* Example:
* <p>
* PolarNumber p = (PolarNumber)x.clone();
* <p>
* @return A clone of this polar number
*
*/
public Object clone()
{
return new PolarNumber(_mag, _phase);
}

/**
* Evaluates this polar number for equality with the one passed as parameter.
* <p>
* @param obj A polar number to ve evaluated.
* <p>
* @return true if equal or false otherwise
*
*/
public boolean equals(Object obj)
{
PolarNumber p = (PolarNumber)obj;
return (_mag == p.getMagnitude() && _phase == p.getArgument());
}

/**
* Gets a string representation for this polar number.
* <p>
* Format:
* <p>
* less-than mag, phase greater-than
* <p>
* @return A string representing this polar number
*
*/
public String toString()
{
NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
	formatter.setMinimumFractionDigits(2);
	formatter.setMaximumFractionDigits(2);
String s = "< "+formatter.format(_mag)+", "+formatter.format(_phase)+" >";
return s;
}


private float _mag;
private float _phase;

}

// END

