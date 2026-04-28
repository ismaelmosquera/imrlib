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
* TestCyclotomic.java
*
* Author: Ismael Mosquera rivera.
*/

import imr.util.InputReader;
import imr.math.polynomial.Polynomial;
import imr.math.polynomial.CyclotomicPolynomial;

/*
* This example demonstrates the CyclotomicPolynomial class.
* When execute, a Menu from to choose a few options will be showed.
*
*/
public class TestCyclotomic
{
public static void main(String[] args)
{
	int[] pn = null;
int option, n;

show_menu();
do
{
	System.out.print("Option: ");
	option = InputReader.readInt();
	if(option == 0) option = 7;
	System.out.println();
	switch(option)
	{
		case 1:
		show_menu();
		break;
		case 2:
		System.out.println("Type an Integer value > 0");
		System.out.print("n: ");
		n = InputReader.readInt();
		if(n > 0)
		{
pn = CyclotomicPolynomial.compute(n);
System.out.println();
System.out.println("Print raw:");
System.out.print("P["+n+"] = "); Polynomial.print(pn);
System.out.println("Print formatted:");
System.out.print("P["+n+"] = "); Polynomial.printFormatted(pn);
System.out.println();
		}
		else
		{
			System.out.println();
			System.out.println("Sorry, " + n + " is not a valid parameter; it must be in the range [1 .. n]");
			System.out.println();
		}
		break;
		case 3:
if(pn != null)
{
	System.out.println();
	System.out.println("Type an integer value for the required exponent [0 .. "+(pn.length-1)+"]");
	System.out.print("Exponent: ");
	n = InputReader.readInt();
	System.out.println();
	if(n>=0 && n <pn.length)
	{
		System.out.println("Coefficient = "+pn[n]);
		System.out.println();
	}
	else
	{
		System.out.println("Sorry: out of range; range = [0 .. "+(pn.length-1)+"]");
		System.out.println();
	}
}
else
{
	System.out.println();
	System.out.println("Sorry, there is not a cyclotomic polynomial computed yet.");
	System.out.println();
}
		break;
		case 4:
		option = 0;
		break;
		default:
		System.out.println("Not available.");
		System.out.println();
	}
}while(option != 0);

System.out.println();
System.out.println("bye.");
}

private static void show_menu()
{
System.out.println("1: Show Menu.");
System.out.println("2: Compute Pn(x)");
System.out.println("3: Show coefficient for a given exponent.");
System.out.println("4: Exit.");
System.out.println();
}

}

// END
