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
 * Y		ou should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

/*
* RationalPolynomialDivision.java
*
* imr-lib
*
* author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial.division;

import imr.math.RationalNumber;
import imr.math.polynomial.Polynomial;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
* The <code>RationalPolynomialDivision</code> class implements functionallity to perform rational polynomial division. <p>
* @author Ismael Mosquera Rivera
*
*/
public final class RationalPolynomialDivision
{

/**
* Constructor.
*/
public RationalPolynomialDivision()
{
_q = null;
_r = null;
}

/**
* This method performs rational polynomial division. <p>
* @param p1
* A rational polynomial represented as an array of rationals ( divident ).
* <p>
* @param p2
* A rational polynomial represented as an array of rationals ( divisor).
 * <p>
 * If this method returns success, you can retrieve the quotient and the remainder calling the <code>quotient()</code> and <code>remainder</code> methods.
 * <p>
 * @return true if success or false otherwise.
 *
 */
public boolean div(RationalNumber[] p1, RationalNumber[] p2)
{
if(p1==null || p2==null) return false;
if(p2.length > p1.length) return false;
if(p2.length < 2) return false;

RationalPolynomial r = new RationalPolynomial(p1);
RationalPolynomial d = new RationalPolynomial(p2);
RationalPolynomial q = new RationalPolynomial();
RationalNumber current = null;
RationalNumber dcoef = (RationalNumber)d.leadingCoefficient().clone();
int pd = d.degree();
while(r.degree() >= d.degree())
{
current = r.leadingCoefficient().div(dcoef);
Term t = new Term((RationalNumber)current.clone(), r.degree()-pd);
q.add(t);
r = new RationalPolynomial(Polynomial.sub(r.raw(), Polynomial.mul(p2, RationalPolynomial.raw((RationalNumber)t.coefficient.clone(), t.exponent))));
}
_q = q.raw();
_r = r.raw();

return true;
}

/**
* Gets the quotient after division. <p>
* @return resulting quotient.
*
*/
public RationalNumber[] quotient()
{
return _q;
}

/**
* Gets the remainder after division. <p>
* @return resulting remainder.
*
*/
public RationalNumber[] remainder()
{
	return _r;
}

// Class members
private RationalNumber[] _q; // quotient
private RationalNumber[] _r; // remainder

// Private helper inner class
private static final class RationalPolynomial
{
/*
* Default constructor.
*/
public RationalPolynomial()
{
polynomialTerms = new ArrayList<>();
}

/*
* Constructor.
* Builds a RationalPolynomial object from a raw RationalNumber array.
*/
public RationalPolynomial(RationalNumber[] p)
{
	this();
for(int i = p.length-1; i >= 0; i--)
{
	if(Math.abs(p[i].getNumerator()) != 0) polynomialTerms.add(new Term((RationalNumber)p[i].clone(), i));
}
Collections.sort(polynomialTerms, new TermSorter());
}

/*
* Adds a new term to the polynomial term list.
*/
public void add(Term t)
{
polynomialTerms.add(t);
}

/*
* Gets the leading coefficient from this polynomial.
* The leading coefficient is the one of major exponent.
*/
public RationalNumber leadingCoefficient()
{
return (RationalNumber)polynomialTerms.get(0).coefficient.clone();
}

/*
* Gets the degree of this polynomial.
*/
public int degree()
{
	if(polynomialTerms.size() == 0) return 0;
return polynomialTerms.get(0).exponent;
}

// Build a raw RationalNumber array having all coefficients.
public RationalNumber[] raw()
{
RationalNumber[] out = new RationalNumber[this.degree()+1];
for(int i = 0; i < out.length; i++) out[i] = (new RationalNumber());
for(Term t : polynomialTerms)
{
	out[t.exponent] = (RationalNumber)t.coefficient.clone();
}
return out;
}

// build a raw RationalNumber array for help polynomial multiplication
public static RationalNumber[] raw(RationalNumber coefficient, int exponent)
{
RationalNumber[] out = new RationalNumber[exponent+1];
for(int i = 0; i < out.length; i++) out[i] = (new RationalNumber());
out[exponent] = (RationalNumber)coefficient.clone();
return out;
}


// choosed structure to store rational polynomial terms.
private List<Term> polynomialTerms;
}

private static final class Term
{

public Term(RationalNumber coefficient, int exponent)
{
this.coefficient = coefficient;
this.exponent = exponent;
}


RationalNumber coefficient;
int exponent;
}

private static final class TermSorter implements Comparator<Term>
{

public int compare(Term t1, Term t2)
{
return (t2.exponent - t1.exponent);
}
}

}

// END
