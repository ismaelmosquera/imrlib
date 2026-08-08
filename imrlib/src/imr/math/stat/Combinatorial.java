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
* Combinatorial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.Factorial;

/**
* The <code>Combinatorial</code> class has static methods in order to compute some combinatorial functions.
* <ul>
* <li>c - combinations.</li>
* <li>cr - combinations with repetition.</li>
* <li>p - permutations.</li>
* <li>pr - permutations with repetition.</li>
* <li>v - variations.</li>
* <li>vr - variations with repetition.</li>
* </ul>
*
* @author Ismael Mosquera Rivera.
*
*/
public final class Combinatorial
{

/**
* Combinations. <p>
* Computes for the combinatorial number ( m/n ) 'called m over n'. <p>
* That is, the number of combinations taken m elements by groups of n elements. <p>
* @param m
* An integer value.
* <p>
* @param n
* An integer object.
* <p>
* @return number of combinations.
*
*/
public static double c(int m,int n)
{
return Factorial.compute(m) / (Factorial.compute(n)*Factorial.compute(m-n));
}

/**
* Combinations with repetition allowed. <p>
* This method computes the number of combinations, but allowing repetitions. <p>
* @param m
* An integer value.
* <p>
* @param n
* An integer value.
* <p>
* @return Number of combinations allowing repetitions.
*
*/
public static double cr(int m,int n)
{
return Factorial.compute(m+n-1) / (Factorial.compute(n)*Factorial.compute(m-1));
}

/**
* Permutations. <p>
* Computes the number of permutations for n elements. <p>
* @param n
* An integer value.
* <p>
* @return Number of permutations for n elements.
*
*/
public static double p(int n)
{
return Factorial.compute(n);
}

/**
* Permutations with repetition allowed. <p>
* Computes permutations with repetitions according to the array passed as parameter. <p>
* @param a
* An array of integer values.
* <p>
* @return Number of permutations allowing repetitions.
*
*/
public static double pr(int[] a)
{
int n = 0;
double d = 1.0;
for(int i=0;i < a.length;i++)
{
	n += a[i];
	d *= Factorial.compute(a[i]);
}
return Factorial.compute(n) / d;
}

/**
* Variations. <p>
* computes the number of variations according to v(m/n). <p>
* @param m
* An integer value.
* <p>
* @param n
* An integer value.
* <p>
* @return Number of variations for (m,n).
*
*/
public static double v(int m,int n)
{
return Factorial.compute(m) / Factorial.compute(m-n);
}

/**
* Variations with repetition allowed. <p>
* Computes the number of variations allowing repetitions for v(m,n). <p>
* @param m
* An integer value.
* <p>
* @param n
* An integer value.
* <p>
* @return Number of variations allowing repetitions for ( m,n )
*
*/
public static double vr(int m,int n)
{
return Math.pow((double)m, (double)n);
}


/*
* private constructor
*/
private Combinatorial(){}

}

//END
