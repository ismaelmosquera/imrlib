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
* TestPolynomialFactorization.java
*
* Author: Ismael Mosquera rivera.
*/

import imr.util.Convert;
import imr.math.ComplexNumber;
import imr.math.polynomial.Polynomial;
import imr.math.polynomial.PolynomialFactorization;
import imr.math.polynomial.BesselPolynomial;

/*
* This example demonstrates the PolynomialFactorization class, which computes polynomial factorization into binomials.
* That is, Pn(x) = (x + an)(x + an-1) ... (x +a1)
* In this test, we use 2nd, 3rd and 5th order polynomials for integer, real and complex coefficients respectively.
* In addition, we also compute polynomial factorization for a reverse 4th degree Bessel polynomial.
*
*/
public class TestPolynomialFactorization
{
public static void main(String[] args)
{
System.out.println("Polynomial Factorization Example");
System.out.println();
System.out.println("2nd order:");
int[] pi = Polynomial.Storage.loadInt("poly2.dat");
System.out.println("P(x)");
System.out.print("Print raw: "); Polynomial.print(pi);
System.out.print("Print formatted: "); Polynomial.printFormatted(pi);
ComplexNumber[][] bi = PolynomialFactorization.compute(pi);
System.out.println("Factorization");
System.out.println("Print raw:");
PolynomialFactorization.print(bi);
System.out.println("Print formatted:");
PolynomialFactorization.printFormatted(bi);
ComplexNumber[] p = PolynomialFactorization.binomialProduct(bi);
System.out.println("Result after binomial product");
System.out.print("Print raw: "); Polynomial.print(Convert.toIntArray(p));
System.out.print("Print formatted: "); Polynomial.printFormatted(Convert.toIntArray(p));
System.out.println();
System.out.println("3rd order:");
double[] pr = Polynomial.Storage.loadDouble("poly3.dat");
System.out.println("P(x)");
System.out.print("Print raw: "); Polynomial.print(pr);
System.out.print("Print formatted: "); Polynomial.printFormatted(pr);
bi = PolynomialFactorization.compute(pr);
System.out.println("Factorization");
System.out.println("Print raw:");
PolynomialFactorization.print(bi);
System.out.println("Print formatted:");
PolynomialFactorization.printFormatted(bi);
p = PolynomialFactorization.binomialProduct(bi);
System.out.println("Result after binomial product");
System.out.print("Print raw: "); Polynomial.print(Convert.toDoubleArray(p));
System.out.print("Print formatted: "); Polynomial.printFormatted(Convert.toDoubleArray(p));
System.out.println();
System.out.println("5th order:");
ComplexNumber[] pc = Polynomial.Storage.loadComplex("poly5.dat");
System.out.println("P(x)");
System.out.print("Print raw: "); Polynomial.print(pc);
System.out.print("Print formatted: "); Polynomial.printFormatted(pc);
bi = PolynomialFactorization.compute(pc);
System.out.println("Factorization");
System.out.println("Print raw:");
PolynomialFactorization.print(bi);
System.out.println("Print formatted:");
PolynomialFactorization.printFormatted(bi);
p = PolynomialFactorization.binomialProduct(bi);
System.out.println("Result after binomial product");
System.out.print("Print raw: "); Polynomial.print(p);
System.out.print("Print formatted: "); Polynomial.printFormatted(p);
System.out.println();
System.out.println("Now let's try with a reversed 4th order Bessel polynomial.");
System.out.println("Maybe as you already know, the leading coefficient of a reverse Bessel polynomial is always equal to 1.");
double[] b = BesselPolynomial.computeReverse(4);
System.out.println("Bn=4(x)");
System.out.print("Print raw: "); Polynomial.print(b);
System.out.print("Print formatted: "); Polynomial.printFormatted(b);
bi = PolynomialFactorization.compute(b);
System.out.println("Factorization");
System.out.println("Print raw:");
PolynomialFactorization.print(bi);
System.out.println("Print formatted:");
PolynomialFactorization.printFormatted(bi);
p = PolynomialFactorization.binomialProduct(bi);
System.out.println("Result after binomial product");
System.out.print("Print raw: "); Polynomial.print(p);
System.out.print("Print formatted: "); Polynomial.printFormatted(p);

System.out.println();
System.out.println("bye.");
}
}

// END
