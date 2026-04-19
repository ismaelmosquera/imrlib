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
* Fibonacci.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

/**
* This class computes the Fibonacci number for n greater 0, and Fibbonacci sequence of n elements for n greater 0. <p>
* @author Ismael Mosquera Rivera.
*
*/
public class Fibonacci
{

/**
* Static method to compute the Fibbonacci number for the integer parameter n. <p>
* If n less 0 this method returns -1.
* <p>
* If n = 0 this method returns 0. <p>
* @param n
* An Integer value.
* <p>
* @return Fibonacci number for the integer parameter.
*
*/
public static int number(int n)
{
if(n<0) return -1;
if(n==0) return 0;
if(n==1) return 1;
return number(n-1)+number(n-2);
}

/**
* Static method to compute the [1..n] Fibonacci sequence. <p>
* If n less or equals 0 this method returns an empty integer array.
* <p>
* @param n
* An integer value.
* <p>
* Example: Fibonacci.sequence(7) = [1, 1, 2, 3, 5, 8, 13]
* <p>
* @return Fibonacci sequence for the integer passed as parameter.
*
*/
public static int[] sequence(int n)
{
if(n <= 0) return new int[0];
int[] out = null;
if(n == 1)
{
out = new int[1];
out[0] = 1;
return out;
}
if(n == 2)
{
out = new int[2];
out[0] = out[1] = 1;
return out;
}
out = new int[n];
out[0] = out[1] = 1;
for(int i = 2; i < n; i++) out[i] = out[i-1]+out[i-2];
return out;
}


// Private constructor so that this class cannot be instantiated
private Fibonacci() {}

}

// END
