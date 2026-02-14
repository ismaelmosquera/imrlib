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
* Convert.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

import imr.math.ComplexNumber;
import imr.math.PolarNumber;
import imr.math.RationalNumber;

/**
* This class has static methods suitable to do some conversions.
*
* <ul>
* <li>Array type conversions.</li>
* <li>Radians to degrees and degrees conversion.</li>
* <li>Complex to Polar and Polar to complex conversion.</li>
* </ul>
*
* We decided to centralize this functions in order to avoid code redundant.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public class Convert
{

/**
* Polar to Complex conversion.
* <p>
* @param p
* A <code>PolarNumver</code> object.
* <p>
* @return A <code>ComplexNumber</code> object result of the conversion.
*
*/
public static ComplexNumber toComplex(PolarNumber p)
{
	float mag = p.getMagnitude();
float arg = p.getArgument();
	return new ComplexNumber(mag * (float)Math.cos((double)arg), mag * (float)Math.sin((double)arg));
}

/**
* Complex to Polar conversion.
* <p>
* @param z
* A <code>ComplexNumver</code> object.
* <p>
* @return A <code>PolarNumber</code> object result of the conversion.
*
*/
public static PolarNumber toPolar(ComplexNumber z)
{
return new PolarNumber(z.magnitude(), z.argument());
}

/**
* Converts radians to degrees ( sexagesimal ).
* <p>
* @param r
* radians value.
* <p>
* @return degrees value result of the conversion.
*
*/
public static float toDegrees(float r)
{
	return r * 180.0f / (float)Math.PI;
}

/**
* Converts degrees ( sexagesimal ) to radians.
* <p>
* @param g
* degrees value.
* <p>
* @return radians value result of the conversion.
*
*/
public static float toRadians(float g)
{
	return g * (float)Math.PI / 180.0f;
}


// Array conversion functions.

/**
* Float array to int array conversion.
* <p>
* Take in account that maybe you can lose information with this conversion so, be careful.
* <p>
* @param p
* A float 1-dimensional array.
* <p>
* @return int array result of the conversion.
*
*/
public static int[] toIntArray(float[] p)
{
int n = p.length;
int[] out = new int[n];
for(int i = 0; i < n; i++)
{
out[i] = (int)p[i];
}
return out;
}

/**
* Complex array to int array conversion.
* <p>
* Take in account that maybe you can lose information with this conversion so, be careful.
* <p>
* @param p
* A complex number 1-dimensional array.
* <p>
* @return int array result of the conversion.
*
*/
public static int[] toIntArray(ComplexNumber[] p)
{
int n = p.length;
int[] out = new int[n];
for(int i = 0; i < n; i++)
{
out[i] = (int)p[i].getReal();
}
return out;
}

/**
* Gets a float array from an integer one.
* <p>
* @param p
* An integer 1-dimensional array.
* <p>
* @return float array builded from the integer one passed as parameter.
*
*/
public static float[] toFloatArray(int[] p)
{
int n = p.length;
float[] out = new float[n];
for(int i = 0; i < n; i++)
{
out[i] = (float)p[i];
}
return out;
}

/**
* Gets a float array from a complex number one.
* <p>
* Take in account that maybe you can lose information with this conversion so, be careful.
* <p>
* @param p
* An complex number 1-dimensional array.
* <p>
* @return float array builded from the complex number one passed as parameter.
*
*/
public static float[] toFloatArray(ComplexNumber[] p)
{
int n = p.length;
float[] out = new float[n];
for(int i = 0; i < n; i++)
{
out[i] = p[i].getReal();
}
return out;
}

/**
* Gets a complex number array from an integer one.
* <p>
* @param p
* An integer 1-dimensional array.
* <p>
* @return Complex array builded from the integer one passed as parameter.
*
*/
public static ComplexNumber[] toComplexArray(int[] p)
{
if(p == null) return null;
int size = p.length;
ComplexNumber[] out = new ComplexNumber[size];
for(int i = 0; i < size; i++)
{
out[i] = new ComplexNumber((float)p[i], 0.0f);
}
return out;
}

/**
* Gets a complex number array from a float one.
* <p>
* @param p
* A float 1-dimensional array.
* <p>
* @return Complex array builded from the float one passed as parameter.
*
*/
public static ComplexNumber[] toComplexArray(float[] p)
{
if(p == null) return null;
int size = p.length;
ComplexNumber[] out = new ComplexNumber[size];
for(int i = 0; i < size; i++)
{
out[i] = new ComplexNumber(p[i], 0.0f);
}
return out;
}

/**
* Converts a rational number array to a float array.
* <p>
* @param q
* A rational number array.
* <p>
* @return A float array builded from the rational passed as parameter.
*
/
*/
public static float[] toFloatArray(RationalNumber[] q)
{
if(q == null || q.length == 0) return null;
int n = q.length;
float[] out = new float[n];
for(int i = 0; i < n; i++)
{
out[i] = q[i].value();
}
return out;
}

/**
* Converts the polar number array passed as parameter to a complex number array.
* <p>
* @param p
* A polar number array.
* <p>
* @return a complex number array builded from the polar one passed as parameter.
*
*/
public static ComplexNumber[] toComplexArray(PolarNumber[] p)
{
if(p == null || p.length == 0) return null;
int n = p.length;
ComplexNumber[] out = new ComplexNumber[n];
for(int i = 0; i < n; i++)
{
	out[i] = p[i].toComplex();
}
return out;
}

/**
* Converts the complex number array passed as parameter to a polar number array.
* <p>
* @param c
* A complex number array.
* <p>
* @return A polar number array builded from the complex one passed as parameter.
*
*/
public static PolarNumber[] toPolarArray(ComplexNumber[] c)
{
if(c == null || c.length == 0) return null;
int n = c.length;
PolarNumber[] out = new PolarNumber[n];
for(int i = 0; i < n; i++)
{
out[i] = c[i].toPolar();
}
return out;
}


// Private constructor so that this class cannot be instantiated.
private Convert() {}

}

// END
