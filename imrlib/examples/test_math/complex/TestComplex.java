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
* TestComplex.java
*
* Author: Ismael Mosquera Rivera
*/

import imr.math.ComplexNumber;

/*
* this example demonstrates all the methods for this complex number class written in Java.
*
* Author: Ismael Mosquera Rivera.
*
*/
public class TestComplex
{
public static void main(String[] args)
{
ComplexNumber c1 = new ComplexNumber(2.0f, 1.0f);
ComplexNumber c2 = new ComplexNumber(1.0f, 4.0f);
ComplexNumber z = null;

System.out.println("ComplexNumber class");
System.out.println();

System.out.println("c1 = " + c1);
System.out.println("c2 = " + c2);
System.out.println();

System.out.println(c1 + " + " + c2 + " = " + c1.add(c2));
System.out.println(c1 + " - " + c2 + " = " + c1.sub(c2));
z = c1.mul(c2);
System.out.println(c1 + " * " + c2 + " = " + z);
c1 = (ComplexNumber)z.clone();
System.out.println(c1 + " / " + c2 + " = " + c1.div(c2));
System.out.println();
c1.setReal(2.0f);
c1.setImag(1.0f);
System.out.println("conjugated(" + c1 + ") = " + c1.conjugated());
c1.setReal(3.0f);
c1.setImag(4.0f);
System.out.println("magnitude(" + c1 + ") = " + c1.magnitude());
System.out.println("argument(" + c1 + ") = " + c1.argument());
System.out.println();
System.out.println("sin(" + c1 + ") = " + c1.sin());
System.out.println("cos(" + c1 + ") = " + c1.cos());
System.out.println("tan(" + c1 + ") = " + c1.tan());
System.out.println();
z = c1.log();
System.out.println("log(" + c1 + ") = " + z);
System.out.println("exp(" + z + ") = " + z.exp());
c1.setReal(2.0f);
c1.setImag(0.0f);
z = c1.pow(10);
System.out.println("pow(" + c1 + ", 10) = " + z);
System.out.println("log2(" + z + ") = " + z.log2());
c1.setReal(1000000.0f);
System.out.println("log10(" + c1 + ") = " + c1.log10());
c1.setReal(1.0f/100.0f);
System.out.println("log10(" + c1 + ") = " + c1.log10());
c1.setReal(2.0f);
c1.setImag(1.0f);
z = c1.square();
System.out.println("square(" + c1 + ") = " + z);
System.out.println("sqrt(" + z + ") = " + z.sqrt());
z = c1.pow(3);
System.out.println("pow(" + c1 + ", 3) = " + z);
System.out.println("curt(" + z + ") = " + z.curt());
c1.setImag(0.0f);
System.out.println("12th root of 2 = " + c1.ithrt(12));
System.out.println();
c1.setReal(-2.0f);
System.out.println("pow(" + c1 + ", -2) = " + c1.pow(-2));
System.out.println();
System.out.println("Euler's identity ( c^(iw = cos(w) + isin(w)))");
System.out.println("e^(iPI) + 1 = 0 The most beauty equation in the Worl!");
System.out.println("e^(iPI) = " + ComplexNumber.eulerIdentity((float)Math.PI));
System.out.println();
System.out.println("Roots of Unity");
int n = 4;
System.out.println("for n = " + n);
ComplexNumber[] ur = ComplexNumber.unityRoots(n);
ComplexNumber.print(ur);
System.out.println("Evaluate roots:");
for(int i = 0; i < n; i++)
{
	System.out.println("root = " + ur[i] + ", result = " + ur[i].pow(n).magnitude());
}

System.out.println();
System.out.println("bye.");
}
}

// END
