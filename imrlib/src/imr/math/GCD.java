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
* GCD.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math;

/**
* This class has only a static method implementing the GCD algorithm.
* <p>
* The implementation is an iterative version of the Euclides algorithm.
* <p>
* @author Ismael Mosquera rivera.
*
*/
public class GCD
{

/**
* This method returns the gcd for the integers passed as parameters.
* <p>
* @param a
* An integer value.
* <p>
* @param b
* An integer value.
* <p>
* @return gcd(a, b)
*
*/
public static int compute(int a, int b)
{
int d;
int r;
int _a = Math.abs(a);
int _b = Math.abs(b);

if(_a < _b)
{
	int tmp = _a;
	_a = _b;
	_b = tmp;
}
if(_b == 0) return _a;
while(true)
{
	if((_a%_b)==0)
	{
	d = _b;
	break;
	}
	r = _a%_b;
	_a = _b;
	_b = r;
}
return d;

}


// Private constructor, so that this class cannot be instantiated.
private GCD() {}

}

// END
