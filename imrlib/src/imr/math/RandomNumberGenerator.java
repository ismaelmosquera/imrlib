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
* The <code>RandomNumberGenerator</code> class generates uniform distributed random numbers in a [min..max] range. <p>
* It has public static overloaded methods to do the task. <p>
* There is also a non uniform distributed method that generates Poisson random numbers.
* <p>
* This class uses assertions; to enable them at execution time you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class RandomNumberGenerator
{

/**
* This method generates a uniform distributed random integer number in the [min..max] range. <p>
* @param min
* Minimum integer value in the range.
* <p>
* @param max
* Maximum integer value in the range.
* <p>
* @return A random integer value in the [min..max] range.
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
* This method generates a uniform distributed random floating point number in the [min..max] range. <p>
* @param min
* Minimum double value in the range.
* <p>
* @param max
* Maximum double value in the range.
* <p>
* @return A random double value in the [min..max] range.
*
*/
public static double generate(double min, double max)
{
	assert (min < max): "Bad range: min must be lower than max.";
double range = (max - min);
double retval = (Math.random() * range);
return retval + min;
}

/**
* Static method to generate a Poisson distributed random number. <p>
* This method uses the Knuth algorithm to do the task. <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return An integer Poisson distributed random number.
*
*/
public static int generate(double lambda)
{
assert(lambda > 0.0): "PoissonRandomNumber -> generate(...): Bad parameter; 'lambda' must be equal or greater than zero.";
double t = Math.exp(- lambda);
double p = 1.0;
int k = 0;
do
{
k++;
p *= Math.random();
}while(p > t);
return k - 1;
}


// private constructor so that this class cannot be instantiated
private RandomNumberGenerator()
{}

}

// END
