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

package imr.util;

/**
* The <code>Range</code> class implements a range between min and ma^^x[min .. max]
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
public Range(float min, float max)
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
public float getMax()
{
return _max;
}

/**
* Gets the Minimum value for this <code>Range</code> object.
* <p>
* @return Minimum range value.
*
*/
public float getMin()
{
return _min;
}


private final float _min;
private final float _max;
}

// END

