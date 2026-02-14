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
* TestRoots.java
*
* Author: Ismael Mosquera Rivera.
*
*/

import imr.util.Convert;
import imr.math.ComplexNumber;
import imr.math.RationalNumber;
import imr.math.polynomial.Polynomial;


/*
* This example shows how to compute polynomial roots.
* You can find roots for first, second, third and 4th degree polynomials.
* Roots can be computed for polynomials with any numeric type of coefficient:
* - Integer.
* - Real.
* - Complex.
*
* We coded examples for first and second degree polynomials with integer coefficients,
* third degree polynomials of rational ( included in real ) coefficients,
* 4th degree polynomials with complex coefficients.
*
* Anyway, the Polynomial class has overloaded methods to compute roots for first, second, third and 4th degree
* for any type of numeric coefficient.
*
* Author: Ismael Mosquera rivera.
*
*/
public class TestRoots
{
public static void main(String[] args)
{
System.out.println("Polynomial roots");
System.out.println();

ComplexNumber[] pz = null;
ComplexNumber[] roots = null;
ComplexNumber rt = null;
ComplexNumber c0 = null;
ComplexNumber c1 = null;
ComplexNumber c2 = null;
ComplexNumber c3 = null;
ComplexNumber c4 = null;

System.out.println("Linear:");
int[] pn0 = {-1, 2}; // 2x - 1 = 0
System.out.print("Pn = "); Polynomial.print(pn0);
roots = Polynomial.roots(pn0);
System.out.print("roots = "); Polynomial.print(roots);
pz = Convert.toComplexArray(pn0);
System.out.println("Evaluate roots:");
for(int i = 0; i < roots.length; i++)
{
rt = roots[i];
c0 = pz[0];
c1 = pz[1].mul(rt);
System.out.println("root = " + rt + ", result = " + c0.add(c1));
}
System.out.println();

System.out.println("Quadratic:");
int[] pn1 = {1, 0, 1}; // x^2 + 1 = 0
System.out.print("Pn = "); Polynomial.print(pn1);
roots = Polynomial.roots(pn1);
System.out.print("roots = "); Polynomial.print(roots);
pz = Convert.toComplexArray(pn1);
System.out.println("Evaluate roots:");
for(int i = 0; i < roots.length; i++)
{
rt = roots[i];
c0 = pz[0];
c1 = pz[1].mul(rt);
c2 = pz[2].mul(rt.square());
System.out.println("root = " + rt + ", result = " + c0.add(c1).add(c2));
}
System.out.println();

System.out.println("Cubic:");
RationalNumber[] q = RationalNumber.loadRationalArray("p3rational.dat");
float[] pr = Convert.toFloatArray(q); // 2/1x^3 + 3/2x^2 0/1x - 4/3 = 0
System.out.print("Pr = "); RationalNumber.print(q);
roots = Polynomial.roots(pr);
System.out.print("roots = "); Polynomial.print(roots);
pz = Convert.toComplexArray(pr);
System.out.println("Evaluate roots:");
for(int i = 0; i < roots.length; i++)
{
rt = roots[i];
c0 = pz[0];
c1 = pz[1].mul(rt);
c2 = pz[2].mul(rt.square());
c3 = pz[3].mul(rt.pow(3));
System.out.println("root = " + rt + ", result = " + c0.add(c1).add(c2).add(c3));
}
System.out.println();

System.out.println("Quartic:");
pz = ComplexNumber.loadComplexArray("p4complex.dat"); // c4x^4 + c3x^3 + c2x^2 + c1x + c0 = 0
System.out.print("Pz = "); Polynomial.print(pz);
roots = Polynomial.roots(pz);
System.out.print("roots = "); Polynomial.print(roots);
System.out.println("Evaluate roots:");
for(int i = 0; i < roots.length; i++)
{
rt = roots[i];
c0 = pz[0];
c1 = pz[1].mul(rt);
c2 = pz[2].mul(rt.square());
c3 = pz[3].mul(rt.pow(3));
c4 = pz[4].mul(rt.pow(4));
System.out.println("root = " + rt + ", result = " + c0.add(c1).add(c2).add(c3).add(c4));
}
ComplexNumber.storeComplexArray(roots, "p4roots.dat");
System.out.println("Complex roots saved to bin/p4roots.dat");

System.out.println();
System.out.println("bye.");
}
}

// END
