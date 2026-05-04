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
* TestPolynomialGCD.java
*
* Author: Ismael Mosquera rivera.
*/

import imr.util.Convert;
import imr.math.ComplexNumber;
import imr.math.polynomial.Polynomial;
import imr.math.polynomial.division.*;

/*
* This example demonstrates the PolynomialGCD class.
* There are examples to compute integer, float and complex polynomials.
*
*/
public class TestPolynomialGCD
{
public static void main(String[] args)
{
System.out.println("Polynomial GCD example");
System.out.println();

System.out.println("Integer");
int[] p0 = Polynomial.Storage.loadInt("p0.dat");
int[] p1 = Polynomial.Storage.loadInt("p1.dat");
int[] p2 = Polynomial.Storage.loadInt("p2.dat");

System.out.print("p0 = "); Polynomial.print(p0);
System.out.print("p1 = "); Polynomial.print(p1);
System.out.print("p2 = "); Polynomial.print(p2);
System.out.println();
int[] pn1 = Polynomial.mul(p0, p2);
int[] pn2 = Polynomial.mul(p1, p2);
System.out.print("pn1 = p0*p2 -> "); Polynomial.print(pn1);
System.out.print("pn2 = p1*p2 -> "); Polynomial.print(pn2);
float[] fp = PolynomialGCD.compute(pn1, pn2);
System.out.print("gcd(pn1, pn2) = "); Polynomial.print(fp);
System.out.print("gcd(pn1, pn2) = "); Polynomial.printFormatted(fp);
System.out.println("Evaluate:");
FloatPolynomialDivision fpd = new FloatPolynomialDivision();
System.out.println("pn1 / gcd");
if(fpd.div(Convert.toFloatArray(pn1), fp))
{
	System.out.println("Raw:");
	System.out.print("quotient = "); Polynomial.print(fpd.quotient());
	System.out.print("remainder = "); Polynomial.print(fpd.remainder());
	System.out.println("Formatted:");
	System.out.print("quotient = "); Polynomial.printFormatted(fpd.quotient());
		System.out.print("remainder = "); Polynomial.printFormatted(fpd.remainder());
}
else
{
System.out.println("Sorry, something was wrong.");
}
System.out.println();
System.out.println("pn2 / gcd");
if(fpd.div(Convert.toFloatArray(pn2), fp))
{
	System.out.println("Raw:");
	System.out.print("qquotient = "); Polynomial.print(fpd.quotient());
	System.out.print("remainder = "); Polynomial.print(fpd.remainder());
	System.out.println("Formatted:");
	System.out.print("qquotient = "); Polynomial.printFormatted(fpd.quotient());
		System.out.print("remainder = "); Polynomial.printFormatted(fpd.remainder());
}
else
{
System.out.println("Sorry, something was wrong.");
}
System.out.println();

System.out.println("Complex");
System.out.println();
ComplexNumber[] cp0 = Polynomial.Storage.loadComplex("cp0.dat");
ComplexNumber[] cp1 = Polynomial.Storage.loadComplex("cp1.dat");
ComplexNumber[] cp2 = Polynomial.Storage.loadComplex("cp2.dat");
System.out.print("cp0 = "); Polynomial.print(cp0);
System.out.print("cp1 = "); Polynomial.print(cp1);
System.out.print("cp2 = "); Polynomial.print(cp2);
System.out.println();
ComplexNumber[] cpn1 = Polynomial.mul(cp0, cp2);
ComplexNumber[] cpn2 = Polynomial.mul(cp1, cp2);
System.out.print("cpn1 = cp0*cp2 -> "); Polynomial.print(cpn1);
System.out.print("cpn2 = pn*p2"); Polynomial.print(cpn2);
System.out.println();
ComplexNumber[] gcd = PolynomialGCD.compute(cpn1, cpn2);
System.out.print("gcd(cpn1, cpn2) = "); Polynomial.print(gcd);
System.out.print("gcd(cpn1, cpn2) = "); Polynomial.printFormatted(gcd);
ComplexPolynomialDivision cpd = new ComplexPolynomialDivision();
System.out.println("pn1 / gcd");
if(cpd.div(cpn1, gcd))
{
	System.out.println("Raw:");
	System.out.print("quotient = "); Polynomial.print(cpd.quotient());
	System.out.print("remainder = "); Polynomial.print(cpd.remainder());
	System.out.print("Formatted:");
	System.out.print("quotient = "); Polynomial.printFormatted(cpd.quotient());
	System.out.print("remainder = "); Polynomial.printFormatted(cpd.remainder());
}
else
{
System.out.println("Sorry, something was wrong.");
}
System.out.println();
System.out.println("pn2 / gcd");
if(cpd.div(cpn2, gcd))
{
	System.out.println("Raw:");
	System.out.print("quotient = "); Polynomial.print(cpd.quotient());
	System.out.print("remainder = "); Polynomial.print(cpd.remainder());
	System.out.println("Formatted:");
	System.out.print("quotient = "); Polynomial.printFormatted(cpd.quotient());
	System.out.print("remainder = "); Polynomial.printFormatted(cpd.remainder());
}
else
{
	System.out.println("Sorry, something was wrong.");
}

System.out.println();
System.out.println("bye.");
}
}

// END
