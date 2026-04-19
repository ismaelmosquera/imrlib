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
* ComplexPolynomialDivision.java
*
* imr-lib
*
* author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial.division;

import imr.math.ComplexNumber;
import imr.math.polynomial.Polynomial;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
* The <code>ComplexPolynomialDivision</code> class implements functionallity to perform complex polynomial division. <p>
* @author Ismael Mosquera Rivera
*
*/
public final class ComplexPolynomialDivision
{

/**
* Constructor.
*/
public ComplexPolynomialDivision()
{
_q = null;
_r = null;
}

/**
* This method performs complex polynomial division. <p>
* @param p1
* A complex polynomial represented as an array of complex ( divident ).
* <p>
* @param p2
* A complex polynomial represented as an array of complex ( divisor).
 * <p>
 * If this method returns success, you can retrieve the quotient and the remainder calling the <code>quotient()</code> and <code>remainder</code> methods.
 * <p>
 * @return true if success or false otherwise.
 *
 */
public boolean div(ComplexNumber[] p1, ComplexNumber[] p2)
{
if(p1==null || p2==null) return false;
if(p2.length > p1.length) return false;
if(p2.length < 2) return false;

ComplexPolynomial r = new ComplexPolynomial(p1);
ComplexPolynomial d = new ComplexPolynomial(p2);
ComplexPolynomial q = new ComplexPolynomial();
ComplexNumber current = null;
ComplexNumber dcoef = (ComplexNumber)d.leadingCoefficient().clone();
int pd = d.degree();
while(r.degree() >= d.degree())
{
current = r.leadingCoefficient().div(dcoef);
Term t = new Term((ComplexNumber)current.clone(), r.degree()-pd);
q.add(t);
r = new ComplexPolynomial(Polynomial.sub(r.raw(), Polynomial.mul(p2, ComplexPolynomial.raw((ComplexNumber)t.coefficient.clone(), t.exponent))));
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
public ComplexNumber[] quotient()
{
return _q;
}

/**
* Gets the remainder after division. <p>
* @return resulting remainder.
*
*/
public ComplexNumber[] remainder()
{
	return _r;
}

// Class members
private ComplexNumber[] _q; // quotient
private ComplexNumber[] _r; // remainder

// simbolic constant defined just by convinience
private static final float THRESHOLD = 1E-5f;

// Private helper inner class
private static final class ComplexPolynomial
{
/*
* Default constructor.
*/
public ComplexPolynomial()
{
polynomialTerms = new ArrayList<>();
}

/*
* Constructor.
* Builds a ComplexPolynomial object from a raw ComplexNumber array.
*/
public ComplexPolynomial(ComplexNumber[] p)
{
	this();
for(int i = p.length-1; i >= 0; i--)
{
	if(p[i].magnitude() > THRESHOLD) polynomialTerms.add(new Term((ComplexNumber)p[i].clone(), i));
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
public ComplexNumber leadingCoefficient()
{
return (ComplexNumber)polynomialTerms.get(0).coefficient.clone();
}

/*
* Gets the degree of this polynomial.
*/
public int degree()
{
	if(polynomialTerms.size() == 0) return 0;
return polynomialTerms.get(0).exponent;
}

// Build a raw ComplexNumber array having all coefficients.
public ComplexNumber[] raw()
{
ComplexNumber[] out = new ComplexNumber[this.degree()+1];
for(int i = 0; i < out.length; i++) out[i] = (new ComplexNumber(0.0f, 0.0f));
for(Term t : polynomialTerms)
{
	out[t.exponent] = (ComplexNumber)t.coefficient.clone();
}
return out;
}

// build a raw ComplexNumber array for help polynomial multiplication
public static ComplexNumber[] raw(ComplexNumber coefficient, int exponent)
{
ComplexNumber[] out = new ComplexNumber[exponent+1];
for(int i = 0; i < out.length; i++) out[i] = (new ComplexNumber(0.0f, 0.0f));
out[exponent] = (ComplexNumber)coefficient.clone();
return out;
}


// choosed structure to store complex polynomial terms.
private List<Term> polynomialTerms;
}

private static final class Term
{

public Term(ComplexNumber coefficient, int exponent)
{
this.coefficient = coefficient;
this.exponent = exponent;
}


ComplexNumber coefficient;
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
