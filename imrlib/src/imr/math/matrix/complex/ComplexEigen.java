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
* ComplexEigen.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera.
*/

package imr.math.matrix.complex;

import imr.math.ComplexNumber;

/**
* The <code>ComplexEigen</code> class encapsulates a pair eigenvalue/eigenvector of complex number value and vector coefficients.
* <p>
* It also implements methods to access and print data to the console. <p>
*
* @author Ismael Mosquera rivera.
*
*/
public final class ComplexEigen
{

/**
* Makes an instance for a <code>ComplexEigen</code> object. <p>
* @param value The eigenvalue.
* <p>
* @param v The eigenvector.
*
*/
public ComplexEigen(ComplexNumber value, ComplexVector v)
{
_value = (ComplexNumber)value.clone();
_vector = (ComplexVector)v.clone();
}

/**
* Gets the eigenvalue.
* <p>
* @return eigenvalue for this Eigen.
*
*/
public ComplexNumber value()
{
return _value;
}

/**
* Gets the eigenvector.
* <p>
* @return eigenvector for this Eigen.
*
*/
public ComplexVector vector()
{
return _vector;
}

/**
* Prints this eigen to the console.
*
*/
public void print()
{
System.out.println("eigenvalue = " + _value);
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
public static void print(ComplexEigen[] eigsys)
{
if(eigsys == null)
{
System.out.println("[]");
return;
}
for(int i = 0; i < eigsys.length; i++) eigsys[i].print();
}


private ComplexNumber _value;
private ComplexVector _vector;

}

// END
