/*
 * Copyright (c) 2024 Ismael Mosquera Rivera
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
* Range.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

/**
* The <code>Range</code> class implements a range between min and max[min .. max]
* <p>
* It is a final class producing immutable objects.
* <p>
* This class uses assertions; you must pass the '-ea' modifier in order to enable assertions.
* <p>
* Example:
* <p>
* <code>java -ea MyApp</code>
* <p>
*
* @author Ismael Mosquera Rivera
*
*/
public final class Range
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>Range</code> object.
* <p>
* @param min Minimum value for this range object.
* @param max Maximum value for this range object.
*
*/
public Range(double min, double max)
{
assert (min < max): "Bad parameters for Range class instantiation: min must be less than max";
_min = min;
_max = max;
}

/**
* Gets the Maximum value for this <code>Range</code> object.
* <p>
* @return Maximum range value.
*
*/
public double getMax()
{
return _max;
}

/**
* Gets the Minimum value for this <code>Range</code> object.
* <p>
* @return Minimum range value.
*
*/
public double getMin()
{
return _min;
}

/**
* Evaluates if the value passed as parameter is in this range. <p>
* @param value
* A value to evaluate.
* <p>
* @return true if in range or false otherwise.
*
*/
public boolean inRange(double value)
{
return inRange(this, value);
}

/**
* Computes the center value for this range object. <p>
* @return center value of this range object.
*
*/
public double centerValue()
{
return centerValue(this);
}
/**
* Computes the width for this range object. <p>
* @return Width of this range object.
*
*/
public double width()
{
return width(this);
}


// Static methods

/**
* Static method to check if a concrete value is in a concrete range. <p>
* @param r
* A <code>Range</code> object.
* <p>
* @param value
* A floating-point value.
* <p>
* @return true if the value is in range or false otherwise.
*
*/
public static boolean inRange(Range r, double value)
{
	return (value >= r.getMin() && value <= r.getMax());
}

/**
* Static method to get the mid value for the <code>Range</code> object passed as parameter. <p>
* @param r
* A <code>Range</code> object.
* <p>
* @return mid value in the range passed as parameter.
*
*/
public static double centerValue(Range r)
{
return (r.getMax()-r.getMin())/2.0 + r.getMin();
}

/**
* Static method to compute the with ( spand ) for the <code>Range</code> object passed as parameter. <p>
* @param r
* A <code>Range</code> object.
* <p>
* @return width of the range passed as parameter.
*
*/
public static double width(Range r)
{
return (r.getMax() - r.getMin());
}

/**
* Static method to get a concrete range object from its mid and width values. <p>
* @param centerValue
* Center of the range object.
* <p>
* @param width
* Spand of the wanted range.
* <p>
* @return A range object according to the passed parameters.
*
*/
public static Range getRange(double centerValue, double width)
{
double d = width / 2.0;
double min = centerValue - d;
double max = centerValue + d;
return new Range(min, max);
}


private final double _min;
private final double _max;
}

// END

