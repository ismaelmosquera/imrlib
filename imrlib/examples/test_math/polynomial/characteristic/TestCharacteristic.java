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
* TestCharacteristic.java
*
* Author: Ismael Mosquera rivera.
*/

import imr.util.Convert;
import imr.math.matrix.Matrix;
import imr.math.ComplexNumber;
import imr.math.polynomial.Polynomial;
import imr.math.polynomial.CharacteristicPolynomial;

import java.text.NumberFormat;
import java.util.Locale;

/*
* This example demonstrates the CharacteristicPolynomial class.
* Such a class computes the characteristic polynomial of a square real matrix.
* In this example, we compute the characteristic polynomial, then we find its roots.
* The roots of the characteristic polynomial are, actually, the eigen values of the matrix.
* Afterwards, we demonstrate that those roots are eigen values of the matrix.
* To do that, we know that det(A-lambdaI) = 0
* where lambda is an eigen value and I is the identity matrix.
*
*/
public class TestCharacteristic
{
public static void main(String[] args)
{
	NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);

	System.out.println("Characteristic Polynomial");
	System.out.println();
System.out.println("A:");
	Matrix m = new Matrix("mcoef.dat");
m.print();
System.out.println();
float[] p = CharacteristicPolynomial.compute(m);
System.out.print("p = "); Polynomial.print(p);
ComplexNumber[] roots = Polynomial.roots(p);
System.out.print("roots = "); ComplexNumber.print(roots);
float[] froots = Convert.toFloatArray(roots);
System.out.print("real roots = "); Polynomial.print(froots);
System.out.println();
System.out.println("Evaluate roots:");
float rt;
for(int i = 0; i < froots.length; i++)
{
rt = froots[i];
System.out.println("root = " + rt + "result = " + formatter.format(Math.abs(p[0]+(p[1]*rt)+(p[2]*(float)Math.pow(rt, 2))+(p[3]*(float)Math.pow(rt, 3)))));
}
System.out.println();
Matrix lambdaI = null;
Matrix a = null;
System.out.println("Evaluate eigen values, det(A-lambdaI) = 0");
for(int i = 0; i < froots.length; i++)
{
lambdaI = Matrix.identity(froots.length);
for(int j = 0; j < froots.length; j++) lambdaI.set(j, j, froots[i]);
System.out.println("lambda = " + froots[i] + " ( ith eigen value ).");
System.out.println("A' = A-lambdaI:");
a = m.sub(lambdaI);
a.print();
System.out.println();
System.out.println("det(A') = " + formatter.format(a.det()));
System.out.println();
}

System.out.println();
System.out.println("bye.");
}
}

// END
