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
* Factorial.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*
*/

package imr.math;

/**
* This class has only a static method to compute the factorial for an integer n.
* <p>
* Our static method returns a floating-point value in order to compute factorials for bigger values of n.
*
* @author Ismael Mosquera rivera.
*
*/
public class Factorial
{

/**
* Computes the factorial for the integer value passed as parameter.
* <p>
* @param n
* An integer value greatest or equal to 0.
* <p>
* If n less 0, this method returns -1.
* <p>
* @return factorial as a floating-point value.
*
*/
public static double compute(int n)
{
if(n < 0) return -1.0f;
if(n == 0) return 1.0f;
if(n == 1) return 1.0f;
return (double)n * compute(n-1);
}


// Private constructor, so that this class cannot be instantiated.
private Factorial() {}

}

// END
