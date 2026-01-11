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
* TestRuffini.java
*
* Author: Ismael Mosquera Rivera.
*
*/

import imr.util.Convert;
import imr.math.ComplexNumber;
import imr.math.polynomial.Polynomial;
import imr.math.polynomial.RuffiniRule;

/*
* This example shows how to use the <code>RuffiniRule</code> class which implements the Ruffini's algorithm
* <p>
* to perform the division: P(x) / (x - a)
* <p>
* where P(x) is a polynomial of any degree, and (x - a) is a binomial.
* <p>
* So, the degree of the quotient is always n-1, where n is the degree of the polynomial to be divided.
* <p>
* As you can guess, the remainder will be always an array of just one position ( size = 1 ).
* <p><p>
* @author Ismael Mosquera Rivera.
*
*/
public class TestRuffini
{
public static void main(String[] args)
{
	RuffiniRule ruf = new RuffiniRule();

System.out.println("Ruffini's rule");
System.out.println();

System.out.println("Integer coefficients:");
int[] pn = {-2, 0, 4, 2, 3};
int[] dn = {1, 1};
System.out.print("Pn = "); Polynomial.print(pn);
System.out.print("dn = "); Polynomial.print(dn);
ruf.compute(pn, -dn[0]); // pn / (x - (-n)) = Pn / (x + n)
// Retrieve quotient and remainder as int arrays in order to check result.
int[] qn = Convert.toIntArray(ruf.quotient());
int[] rn = Convert.toIntArray(ruf.remainder());
// Print quotient and remainder to the console.
System.out.print("quotient = "); Polynomial.print(qn);
System.out.print("remainder = "); Polynomial.print(rn);
// Check result.
System.out.println("Pn = quotient * divisor + remainder:");
Polynomial.print(Polynomial.add(Polynomial.mul(qn, dn), rn));
System.out.println();

System.out.println("Real coefficients:");
float[] pr = {1.3f, 5.02f, 0.0f, 0.25f, 2.5f, -0.5f};
float[] dr = {2.35f, 1.0f};
System.out.print("Pr = "); Polynomial.print(pr);
System.out.print("dr = "); Polynomial.print(dr);
ruf.compute(pr, dr[0]); // Pr / (x - r)
// adjust value for (x - r)
dr[0] *= -1.0f;
// Retrieve quotient and remainder as float arrays in order to check result.
float[] qr = Convert.toFloatArray(ruf.quotient());
float[] rr = Convert.toFloatArray(ruf.remainder());
// Print quotient and remainder to the console.
System.out.print("quotient = "); Polynomial.print(qr);
System.out.print("remainder = "); Polynomial.print(rr);
// Check result.
System.out.println("Pr = quotient * divisor + remainder:");
Polynomial.print(Polynomial.add(Polynomial.mul(qr, dr), rr));
System.out.println();

System.out.println("Complex coefficients:");
ComplexNumber[] pz = new ComplexNumber[4];
pz[0] = new ComplexNumber(2.0f, 1.0f);
pz[1] = new ComplexNumber(1.2f, 3.5f);
pz[2] = new ComplexNumber(3.6f, 5.1f);
pz[3] = new ComplexNumber(1.5f, -0.5f);

ComplexNumber[] dz = new ComplexNumber[2];
dz[0] = new ComplexNumber(-4.5f, 3.1f);
dz[1] = new ComplexNumber(1.0f, 0.0f);
System.out.print("pz = "); Polynomial.print(pz);
System.out.print("dz = "); Polynomial.print(dz);
ruf.compute(pz, dz[0]);
ruf.print();
dz[0] = dz[0].scale(-1.0f); // adjust for tessting
System.out.println("Pz = quotient * divisor + remainder:");
Polynomial.print(Polynomial.add(Polynomial.mul(ruf.quotient(), dz), ruf.remainder()));

System.out.println();
System.out.println("bye.");
}
}

// END
