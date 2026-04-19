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
* TestArithmetic.java
*
* Author: Ismael Mosquera rivera.
*/

import imr.math.ComplexNumber;
import imr.math.RationalNumber;
import imr.math.polynomial.Polynomial;
import imr.math.polynomial.division.*;

/*
* This example demonstrates some functionallity offered to perform arithmetic polynomial operations.
* We are going to be not so exhaustive, since the example could grow arbitrary huge, but you can be confident
* that all about this subject works properly.
*
*/
public class TestArithmetic
{
public static void main(String[] args)
{
System.out.println("POLYNOMIAL ARITHMETIC EXAMPLE");
System.out.println();

System.out.println("Addition ( int ):");
int[] pint1 = {-2, 0, 1, 5, 0, 13, 8};
int[] pint2 = {0, -3, 0, 1, -2};
System.out.println("Print raw:");
System.out.print("p1 = "); Polynomial.print(pint1);
System.out.print("p2 = "); Polynomial.print(pint2);
System.out.println("Print formatted:");
System.out.print("p1 = "); Polynomial.printFormatted(pint1);
System.out.print("p2 = "); Polynomial.printFormatted(pint2);
System.out.println("Result:");
int[] addint = Polynomial.add(pint1, pint2);
System.out.print("p1 + p2 = "); Polynomial.print(addint);
System.out.print("p1 + p2 = "); Polynomial.printFormatted(addint);
System.out.println();

System.out.println("Substraction ( rational ):");
RationalNumber[] prat1 = RationalNumber.loadRationalArray("prat1.dat");
RationalNumber[] prat2 = RationalNumber.loadRationalArray("prat2.dat");
System.out.println("Print raw:");
System.out.print("p1 = "); Polynomial.print(prat1);
System.out.print("p2 = "); Polynomial.print(prat2);
System.out.println("Print formatted:");
System.out.print("p1 = "); Polynomial.printFormatted(prat1);
System.out.print("p2 = "); Polynomial.printFormatted(prat2);
System.out.println("Result:");
RationalNumber[] subrat = Polynomial.sub(prat1, prat2);
System.out.print("p1 - p2 = "); Polynomial.print(subrat);
System.out.print("p1 - p2 = "); Polynomial.printFormatted(subrat);
System.out.println("After reduce:");
subrat = RationalNumber.reduce(subrat);
System.out.print("p1 - p2 = "); Polynomial.print(subrat);
System.out.print("p1 - p2 = "); Polynomial.printFormatted(subrat);
System.out.println("Storing result p1 - p2 in subrat.dat file");
RationalNumber.storeRationalArray(subrat, "subrat.dat");
System.out.println();

System.out.println("Multiplication / Division ( float ):");
System.out.println("Multiplication ( float ):");
float[] fp1 = {-3.2f, 0.0f, 1.5f, -7.03f, 2.1f};
float[] fp2 = {5.02f, 0.0f, 2.1f};
System.out.println("Print raw:");
System.out.print("p1 = "); Polynomial.print(fp1);
System.out.print("p2 = "); Polynomial.print(fp2);
System.out.println("Result:");
float[] pm = Polynomial.mul(fp1, fp2);
System.out.print("p1 * p2 = "); Polynomial.print(pm);
System.out.print("p1 * p2 = "); Polynomial.printFormatted(pm);
System.out.println();
System.out.println("Division ( float ):");
System.out.println("Print raw:");
System.out.print("Divident -> P = (anterior computation p1 * p2 ) = "); Polynomial.print(pm);
System.out.print("Divisor -> p2 = "); Polynomial.print(fp2);
System.out.println("Print formatted:");
System.out.print("P = "); Polynomial.printFormatted(pm);
System.out.print("p2 = "); Polynomial.printFormatted(fp2);
System.out.println("Result:");
FloatPolynomialDivision fpdiv = new FloatPolynomialDivision();
if(fpdiv.div(pm, fp2))
{
	System.out.println("p1 = p2 * quotient + remainder");
	System.out.println("Notice that since we divide by the multiplicator, the remainder must be 0");
	System.out.println("Raw:");
	System.out.print("quotient = "); Polynomial.print(fpdiv.quotient());
	System.out.print("remainder = "); Polynomial.print(fpdiv.remainder());
	System.out.println("Formatted:");
	System.out.print("quotient = "); Polynomial.printFormatted(fpdiv.quotient());
	System.out.print("remainder = "); Polynomial.printFormatted(fpdiv.remainder());
	float[] rpm = Polynomial.add(Polynomial.mul(fp2, fpdiv.quotient()), fpdiv.remainder());
	System.out.println("Print P raw and formatted:");
	System.out.print("P = "); Polynomial.print(pm);
	System.out.print("P = "); Polynomial.printFormatted(pm);
	System.out.println("( divident ) P = quotient * divisor + remainder:");
	System.out.print("p1 = "); Polynomial.print(rpm);
	System.out.print("p1 = "); Polynomial.printFormatted(rpm);
}
else
{
System.out.print("FloatPolynomialDivision -> Sorry, something was wrong. ");
}
System.out.println();
System.out.println("Division ( Rational ):");
prat1 = RationalNumber.loadRationalArray("prat2.dat");
prat2 = RationalNumber.loadRationalArray("prat1.dat");
System.out.println("Print raw:");
System.out.print("p1 = "); Polynomial.print(prat1);
System.out.print("p2 = "); Polynomial.print(prat2);
System.out.println("Print formatted:");
System.out.print("p1 = "); Polynomial.printFormatted(prat1);
System.out.print("p2 = "); Polynomial.printFormatted(prat2);
System.out.println("Result:");
RationalPolynomialDivision ratdiv = new RationalPolynomialDivision();
if(ratdiv.div(prat1, prat2))
{
	System.out.println("p1 / p2 ( after reduce )");
System.out.print("quotient = "); Polynomial.print(RationalNumber.reduce(ratdiv.quotient()));
System.out.print("remainder = "); Polynomial.print(RationalNumber.reduce(ratdiv.remainder()));
RationalNumber[] rpo = RationalNumber.reduce(prat1);
System.out.println("Print divident reduced polynomial:");
System.out.print("P = "); Polynomial.print(rpo);
System.out.print("P = "); Polynomial.printFormatted(rpo);
RationalNumber[] ratresult = Polynomial.add(Polynomial.mul(prat2, ratdiv.quotient()), ratdiv.remainder());
System.out.println("p1 = p2 * quotient + remainder");
System.out.print("Print raw = "); Polynomial.print(RationalNumber.reduce(ratresult));
System.out.print("Print formatted = "); Polynomial.printFormatted(RationalNumber.reduce(ratresult));
System.out.println();
}
else
{
	System.out.println("Rational division -> Sorry, something was wrong.");
	System.out.println();
}

System.out.println("Division ( Complex ):");
ComplexNumber[] cp1 = ComplexNumber.loadComplexArray("cmplx1.dat");
ComplexNumber[] cp2 = ComplexNumber.loadComplexArray("cmplx2.dat");
System.out.println("Print raw:");
System.out.print("p1 = "); Polynomial.print(cp1);
System.out.print("p2 = "); Polynomial.print(cp2);
System.out.println("Print formatted:");
System.out.print("p1 = "); Polynomial.printFormatted(cp1);
System.out.print("p2 = "); Polynomial.printFormatted(cp2);
System.out.println("Result:");
ComplexPolynomialDivision cpdiv = new ComplexPolynomialDivision();
if(cpdiv.div(cp1, cp2))
{
System.out.println("Print raw:");
System.out.print("quotient = "); Polynomial.print(cpdiv.quotient());
System.out.print("remainder = "); Polynomial.print(cpdiv.remainder());
System.out.println("Print formatted:");
System.out.print("quotient = "); Polynomial.printFormatted(cpdiv.quotient());
System.out.print("remainder = "); Polynomial.printFormatted(cpdiv.remainder());
System.out.println("p1 = p2 * quotient + remainder");
ComplexNumber[] _p = Polynomial.add(Polynomial.mul(cp2, cpdiv.quotient()), cpdiv.remainder());
System.out.print("p1 = "); Polynomial.print(_p);
System.out.print("p1 = "); Polynomial.printFormatted(_p);
}
else
{
System.out.println("ComplexPolynomialDivision -> Sorry, something was wrong.");
System.out.println();
}

System.out.println();
System.out.println("bye.");
}
}

// END
