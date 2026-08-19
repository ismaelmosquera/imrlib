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
* PolynomialInterpolator.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

/**
* This is an implementation as a functional interface in order to enable polymorphism for polynomial interpolators. <p>
* Any polynomial interpolator should implement this interface. <p>
*
* @see imr.math.polynomial.LagrangeInterpolator
* @see imr.math.polynomial.TrigonometricInterpolator
*
* @author Ismael Mosquera Rivera.
*
*/
public interface PolynomialInterpolator
{

/**
* Gets an array of interpolated y-coordinates associated to the x-coordinates array passed as parameter. <p>
* @param x
* An array having the x-coordinates from to compute its y-coordinates
* <p>
* @return An array having the interpolated y-values.
*
*/
public double[] interpolate(double[] x);

}

// END
