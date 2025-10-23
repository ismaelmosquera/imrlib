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
* Pair.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

/**
* The <code>Pair</code> class implements an inmutable pair class.
* <p>
* It takes 2 generic parameters as a parametric class. F ( first ), S ( second ).
*
* @author Ismael Mosquera Rivera
*
*/
public final class Pair<F,S>
{

/**
* Constructor.
* <p>
* @param first First element of this pair.
* @param second Second element of this pair.
*
*/
public Pair(F first,S second)
{
this.first = first;
this.second = second;
}

/**
* Gets the first element of this pair.
*
* @return First element of this pair.
*
*/
public F getFirst()
{
return first;
}

/**
* Gets the second element of this pair.
*
* @return Second element of this pair.
*
*/
public S getSecond()
{
return second;
}


private final F first;
private final S second;
}

// END
