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
* Eigen.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera.
*/

package imr.math.matrix;

import java.text.NumberFormat;
import java.util.Locale;

/**
* The <code>Eigen</code> class encapsulates a pair eigenvalue/eigenvector.
* <p>
* It also implements methods to access and print data to the console.
*
* @author Ismael Mosquera rivera
*
*/
public final class Eigen
{

/**
* Makes an instance for a <code>Eigen</code> object.
* @param value The eigenvalue.
* @param v The eigenvector.
*
*/
public Eigen(double value, Vector v)
{
_value = value;
_vector = (Vector)v.clone();
}

/**
* Gets the eigenvalue.
* <p>
* @return eigenvalue for this Eigen.
*
*/
public double value()
{
return _value;
}

/**
* Gets the eigenvector.
* <p>
* @return eigenvector for this Eigen.
*
*/
public Vector vector()
{
return _vector;
}

/**
* Prints this eigen to the console.
*
*/
public void print()
{
	NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
	formatter.setMinimumFractionDigits(2);
	formatter.setMaximumFractionDigits(2);
System.out.println("eigenvalue = " + formatter.format(_value));
System.out.println("eigenvector:");
_vector.print();
System.out.println();
}

/**
* Prints an eigensystem to the console.
* <p>
* @param eigsys An eigensystem to print.
*
*/
public static void print(Eigen[] eigsys)
{
if(eigsys == null)
{
System.out.println("[]");
return;
}
for(int i = 0; i < eigsys.length; i++) eigsys[i].print();
}


private double _value;
private Vector _vector;
}

// END
