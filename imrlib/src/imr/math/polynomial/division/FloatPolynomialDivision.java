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
* FloatPolynomialDivision.java
*
* imr-lib
*
* author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial.division;

import imr.math.polynomial.Polynomial;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
* The <code>FloatPolynomialDivision</code> class implements functionallity to perform floating-point polynomial division. <p>
* Notice that using this class you are also able to perform integer polynomial division. <p>
* All you must do is convert integer arrays into a floating-point ones. <p>
* @see imr.util.Convert
* @author Ismael Mosquera Rivera
*
*/
public final class FloatPolynomialDivision
{

/**
* Constructor.
*/
public FloatPolynomialDivision()
{
_q = null;
_r = null;
}

/**
* This method performs floating-point polynomial division. <p>
* @param p1
* A floating-point polynomial represented as an array of floats ( divident ).
* <p>
* @param p2
* A floating-point polynomial represented as an array of floats ( divisor).
 * <p>
 * If this method returns success, you can retrieve the quotient and the remainder calling the <code>quotient()</code> and <code>remainder</code> methods.
 * <p>
 * @return true if success or false otherwise.
 *
 */
public boolean div(float[] p1, float[] p2)
{
if(p1==null || p2==null) return false;
if(p2.length > p1.length) return false;
if(p2.length < 2) return false;

FloatPolynomial r = new FloatPolynomial(p1);
FloatPolynomial d = new FloatPolynomial(p2);
FloatPolynomial q = new FloatPolynomial();
float current = 0.0f;
float dcoef = d.leadingCoefficient();
int pd = d.degree();
while(r.degree() >= d.degree())
{
current = r.leadingCoefficient() / dcoef;
Term t = new Term(current, r.degree()-pd);
q.add(t);
r = new FloatPolynomial(Polynomial.sub(r.raw(), Polynomial.mul(p2, FloatPolynomial.raw(t.coefficient, t.exponent))));
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
public float[] quotient()
{
return _q;
}

/**
* Gets the remainder agter division. <p>
* @return resulting remainder.
*
*/
public float[] remainder()
{
	return _r;
}

// Class members
private float[] _q; // quotient
private float[] _r; // remainder

// Convenient constant
private static final float THRESHOLD = 1E-5f;

// Private helper inner class
private static final class FloatPolynomial
{
/*
* Default constructor.
*/
public FloatPolynomial()
{
polynomialTerms = new ArrayList<>();
}

/*
* Constructor.
* Builds a FloatPolynomial object from a raw float array.
*/
public FloatPolynomial(float[] p)
{
	this();
for(int i = p.length-1; i >= 0; i--)
{
	if(Math.abs(p[i]) > THRESHOLD) polynomialTerms.add(new Term(p[i], i));
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
public float leadingCoefficient()
{
return polynomialTerms.get(0).coefficient;
}

/*
* Gets the degree of this polynomial.
*/
public int degree()
{
	if(polynomialTerms.size() == 0) return 0;
return polynomialTerms.get(0).exponent;
}

// Build a raw float array having all coefficients.
public float[] raw()
{
float[] out = new float[this.degree()+1];
for(int i = 0; i < out.length; i++) out[i] = 0.0f;
for(Term t : polynomialTerms)
{
	out[t.exponent] = t.coefficient;
}
return out;
}

// build a raw float array for help polynomial multiplication
public static float[] raw(float coefficient, int exponent)
{
float[] out = new float[exponent+1];
for(int i = 0; i < out.length; i++) out[i] = 0.0f;
out[exponent] = coefficient;
return out;
}


// choosed structure to store float polynomial terms.
private List<Term> polynomialTerms;
}

private static final class Term
{

public Term(float coefficient, int exponent)
{
this.coefficient = coefficient;
this.exponent = exponent;
}


float coefficient;
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
