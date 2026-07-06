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
* DoublePolynomialDivision.java
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
* The <code>DoublePolynomialDivision</code> class implements functionallity to perform floating-point ( double precission ) polynomial division. <p>
* Notice that using this class you are also able to perform integer polynomial division. <p>
* All you must do is convert integer arrays into a floating-point ( double precission ) ones. <p>
* @see imr.util.Convert
* @author Ismael Mosquera Rivera
*
*/
public final class DoublePolynomialDivision
{

/**
* Constructor.
*/
public DoublePolynomialDivision()
{
_q = null;
_r = null;
}

/**
* This method performs floating-point ( double precission ) polynomial division. <p>
* @param p1
* A floating-point ( double precission ) polynomial represented as an array of doubles ( divident ).
* <p>
* @param p2
* A floating-point ( double precission ) polynomial represented as an array of doubles ( divisor).
 * <p>
 * If this method returns success, you can retrieve the quotient and the remainder calling the <code>quotient()</code> and <code>remainder</code> methods.
 * <p>
 * @return true if success or false otherwise.
 *
 */
public boolean div(double[] p1, double[] p2)
{
if(p1==null || p2==null) return false;
if(p2.length > p1.length) return false;
if(p2.length == 1)
{
if(Math.abs(p2[0]) < 1E-5) return false;
_q = Polynomial.scale(p1, 1.0/p2[0]);
_r = new double[1];
_r[0] = 0.0;
}
else
{
if(p2.length < 2) return false;
DoublePolynomial r = new DoublePolynomial(p1);
DoublePolynomial d = new DoublePolynomial(p2);
DoublePolynomial q = new DoublePolynomial();
double current = 0.0;
double dcoef = d.leadingCoefficient();
int pd = d.degree();
while(r.degree() >= d.degree())
{
current = r.leadingCoefficient() / dcoef;
Term t = new Term(current, r.degree()-pd);
q.add(t);
r = new DoublePolynomial(Polynomial.sub(r.raw(), Polynomial.mul(p2, DoublePolynomial.raw(t.coefficient, t.exponent))));
}
_q = q.raw();
_r = r.raw();
}
return true;
}

/**
* Gets the quotient after division. <p>
* @return resulting quotient.
*
*/
public double[] quotient()
{
return _q;
}

/**
* Gets the remainder agter division. <p>
* @return resulting remainder.
*
*/
public double[] remainder()
{
	return _r;
}

// Class members
private double[] _q; // quotient
private double[] _r; // remainder

// Convenient constant
private static final double THRESHOLD = 1E-5;

// Private helper inner class
private static final class DoublePolynomial
{
/*
* Default constructor.
*/
public DoublePolynomial()
{
polynomialTerms = new ArrayList<>();
}

/*
* Constructor.
* Builds a DoublePolynomial object from a raw float array.
*/
public DoublePolynomial(double[] p)
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
public double leadingCoefficient()
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

// Build a raw double array having all coefficients.
public double[] raw()
{
double[] out = new double[this.degree()+1];
for(int i = 0; i < out.length; i++) out[i] = 0.0;
for(Term t : polynomialTerms)
{
	out[t.exponent] = t.coefficient;
}
return out;
}

// build a raw double array for help polynomial multiplication
public static double[] raw(double coefficient, int exponent)
{
double[] out = new double[exponent+1];
for(int i = 0; i < out.length; i++) out[i] = 0.0;
out[exponent] = coefficient;
return out;
}


// choosed structure to store double polynomial terms.
private List<Term> polynomialTerms;
}

private static final class Term
{

public Term(double coefficient, int exponent)
{
this.coefficient = coefficient;
this.exponent = exponent;
}


double coefficient;
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
