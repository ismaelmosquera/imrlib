/*
 * Copyright (c) 2023 Ismael Mosquera Rivera
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
* RandomNumberGenerator.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

/**
* The <code>RandomNumberGenerator</code> class generates random numbers in a [min..max] range.
* It has public static overloaded methods to do the task.
*
* @author: Ismael Mosquera Rivera
*
*/
public final class RandomNumberGenerator
{

/**
* This method generates a random integer number in the [min..max] range.
* @param min Minimum integer value in the range
* @param max Maximum integer value in the range
*
* @return A random integer value in the [min..max] range
*
*/
public static int generate(int min, int max)
{
	assert (min < max): "Bad range: min must be lower than max.";
int range = (max - min) + 1;
int retval = (int)(Math.random() * range);
return retval + min;
}

/**
* This method generates a random floating point number in the [min..max] range.
* @param min Minimum double value in the range
* @param max Maximum double value in the range
*
* @return A random double value in the [min..max] range
*
*/
public static double generate(double min, double max)
{
	assert (min < max): "Bad range: min must be lower than max.";
double range = (max - min);
double retval = (Math.random() * range);
return retval + min;
}


// private constructor so that this class cannot be instantiated
private RandomNumberGenerator()
{}

}

// END
