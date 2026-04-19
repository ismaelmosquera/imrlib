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
* CyclotomicPolynomial.java
*
* imr-lib
*
* author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

/**
* The <code>CyclotomicPolynomial</code> class implements an algorithm to compute n-th cyclotomic polynomials. <p>
* Such a polynomial is the unique irreducible one having integer coefficients that divides x^(n-1) and is not a divisor of x^(k-1) for k less n. <p>
* Its roots are n-th primitive roots of unity . <p>
* The coefficients of a cyclotomic polynomial are, mainly, in {0, 1, -1}. <p>
* The first cyclotomic polynomial having other coefficient that in the above set is P[105], which is the product of three consecutive odd primes ( 3*5*7 = 105 ) <p>
* That mentioned cyclotomic polynomial has coefficients equal to -2 at exponent = 7 and 41. <p>
* Notice that the degree for such a polynomials is the value of the Euler's totient function phi(n), <p>
* thus, its size is phi(n) + 1. <p>
* Cyclotomic polynomials are also palindromes except for n=1 and n=2. <p>
* For the general case, we borrowed the idea from the guys contributing in Rosetta Code: https://rosettacode.org <p>
* Thanks a lot felows, you are doing a great task. <p>
* @author Ismael Mosquera Rivera.
*
*/
@SuppressWarnings("unused")
public final class CyclotomicPolynomial
{

/**
* Static method to compute the n-th cyclotomic polynomial. <p>
* We know that for n = 1 pn(x) = (x - 1), for n = 2 Pn(x) = (x + 1) <p>
* If n is prime, then Pn(x) = sum (k=0 to n-1) x^k <p>
* If n = 2p, where p is a prime other than 2, then Pn(x) = sum (k=0 to p-1) (-x)^k <p>
* For the general case, we use the recursive algorithm starting from the basic case and dividing for all Pd(x) where d divides n. <p>
* @param n
* An integer value greater than 0.
* <p>
* @return Pn(x) or null if the operation cannot be done.
*
*/
public static int[] compute(int n)
{
assert(n > 0): "CyclotomicPolynomial -> compute: Bad parameter, n must be greater than zero.";
if(n < 1) return null;
int[] cp = null;
// basic cases
if(n==1 || n==2)
{
cp = new int[2];
if(n == 1)
{
	cp[0] = -1;
	cp[1] = 1;
}
else
{
	cp[0] = 1;
	cp[1] = 1;
}
}
else
{
	if(n == 4)
	{
		// known case, we apply directly
		cp = new int[3];
		cp[0] = cp[2] = 1;
		cp[1] = 0;
	}
	else if(isPrime(n))
{
	// n is prime, so we apply sum (k=0 to n-1) x^k
cp = new int[n];
for(int i = 0; i < n; i++) cp[i] = 1;
}
else if(n!=4 && n%2==0&& isPrime(n/2))
{
	// n equals 2p where p is prime other than 2,
	// we apply sum (k=0 to p-1) (-x)^k
int p = n/2;
cp = new int[p];
int value = 1;
for(int i = 0; i < p; i++)
{
cp[i] = value;
value *= -1;
}
}
else
{
	// General case ( basic definition )
	cp = cyclotomic(n).raw();
	}
}
return cp;
}


// Private helper methods
private static boolean isPrime(int n)
{
if(n < 2) return false;
for(int i = 2; i < n; i++)
{
	if(i*i <= n && n%i == 0) return false;
}
return true;
}

// Static method to get the divisors for the integer value passed as parameter.
private static int[] divisors(int n)
{
	int d = 0;
		int q = (int)Math.sqrt((double)n);
	int[] tmp = new int[n];
	int counter = 0;
	for(int i = 1; i <= q; i++)
	{
	if(n%i == 0)
	{
	tmp[counter++] = i;
	d = n / i;
	if(d!=i && n!=d) tmp[counter++] = d;
	}
	}
	int[] _d = new int[counter];
	for(int i = 0; i < _d.length; i++) _d[i] = tmp[i];
	return _d;
}

// Method to compute the general case.
private static PnX cyclotomic(int n)
{
if(n == 1)
{
            //  Polynomial:  x - 1
            PnX p = new PnX(1, 1, -1, 0);
            return p;
        }
        Map<Integer,Integer> factors = getFactors(n);
        if(factors.containsKey(n))
        {
            //  n prime
            List<Term> termList = new ArrayList<>();
            for(int index = 0; index < n; index++)
            {
                termList.add(new Term(1, index));
            }
            PnX cyclo = new PnX(termList);
            return cyclo;
        }
        else if(factors.size() == 2 && factors.containsKey(2) && factors.get(2) == 1 && factors.containsKey(n/2) && factors.get(n/2) == 1)
        {
            //  n = 2p
            int prime = n/2;
            List<Term> termList = new ArrayList<>();
            int coeff = -1;
            for(int index = 0; index < prime; index++)
            {
                coeff *= -1;
                termList.add(new Term(coeff, index));
            }
            PnX cyclo = new PnX(termList);
            return cyclo;
        }
        else if(factors.size() == 1 && factors.containsKey(2))
        {
            //  n = 2^h
            int h = factors.get(2);
            List<Term> termList = new ArrayList<>();
            termList.add(new Term(1, (int) Math.pow(2, h-1)));
            termList.add(new Term(1, 0));
            PnX cyclo = new PnX(termList);
            return cyclo;
        }
        else if(factors.size() == 1 && ! factors.containsKey(n))
        {
            // n = p^k
            int p = 0;
            for(int prime : factors.keySet())
            {
                p = prime;
            }
            int k = factors.get(p);
            List<Term> termList = new ArrayList<>();
            for(int index = 0; index < p; index++)
            {
                termList.add(new Term(1, index * (int) Math.pow(p, k-1)));
            }
            PnX cyclo = new PnX(termList);
            return cyclo;
        }
        else if(factors.size() == 2 && factors.containsKey(2))
        {
            //  n = 2^h * p^k
            int p = 0;
            for(int prime : factors.keySet())
            {
                if(prime != 2)
                {
                    p = prime;
                }
            }
            List<Term> termList = new ArrayList<>();
            int coeff = -1;
            int twoExp = (int) Math.pow(2, factors.get(2)-1);
            int k = factors.get(p);
            for(int index = 0; index < p; index++)
            {
                coeff *= -1;
                termList.add(new Term(coeff, index * twoExp * (int) Math.pow(p, k-1)));
            }
            PnX cyclo = new PnX(termList);
            return cyclo;
        }
        else if(factors.containsKey(2) && ((n/2) % 2 == 1) && (n/2) > 1)
        {
            //  CP(2m)[x] = CP(-m)[x], n odd integer > 1
            PnX cycloDiv2 = cyclotomic(n/2);
            List<Term> termList = new ArrayList<>();
            for(Term term : cycloDiv2.polynomialTerms)
            {
                termList.add(term.exponent % 2 == 0 ? term : term.negate());
            }
            PnX cyclo = new PnX(termList);
            return cyclo;
        }
        //  General Case
       int[] d = divisors(n);
            PnX cyclo = new PnX(1, n, -1, 0);
            for ( int i : d)
            {
                PnX p = cyclotomic(i);
                cyclo = cyclo.div(p);
            }
            return cyclo;
}


// Private constructor so that this class cannot be instantiated.
private CyclotomicPolynomial() {}


private static final Map<Integer,Map<Integer,Integer>> allFactors = new TreeMap<Integer,Map<Integer,Integer>>();
    static
    {
        Map<Integer,Integer> factors = new TreeMap<Integer,Integer>();
        factors.put(2, 1);
        allFactors.put(2, factors);
    }

    public static final int MAX_ALL_FACTORS = 100000;

    public static final Map<Integer,Integer> getFactors(Integer number) {
        if ( allFactors.containsKey(number) ) {
            return allFactors.get(number);
        }
        Map<Integer,Integer> factors = new TreeMap<Integer,Integer>();
        if ( number % 2 == 0 ) {
            Map<Integer,Integer> factorsdDivTwo = getFactors(number/2);
            factors.putAll(factorsdDivTwo);
            factors.merge(2, 1, (v1, v2) -> v1 + v2);
            if ( number < MAX_ALL_FACTORS )
                allFactors.put(number, factors);
            return factors;
        }
        boolean prime = true;
        long sqrt = (long) Math.sqrt(number);
        for ( int i = 3 ; i <= sqrt ; i += 2 ) {
            if ( number % i == 0 ) {
                prime = false;
                factors.putAll(getFactors(number/i));
                factors.merge(i, 1, (v1, v2) -> v1 + v2);
                if ( number < MAX_ALL_FACTORS )
                    allFactors.put(number, factors);
                return factors;
            }
        }
        if ( prime ) {
            factors.put(number, 1);
            if ( number < MAX_ALL_FACTORS )
                allFactors.put(number, factors);
        }
        return factors;
    }


// Helper inner classes to compute general case

private static final class PnX
{

// Constructors
public PnX()
{
polynomialTerms = new ArrayList<>();
            polynomialTerms.add(new Term());
	}

public PnX(int ... values)
{
	boolean condition = values.length%2 == 0;
	assert(condition): "PnX -> constructor: Bad parameter.";
polynomialTerms = new ArrayList<>();
            for(int i = 0; i < values.length; i += 2)
            {
                Term t = new Term(values[i], values[i+1]);
                polynomialTerms.add(t);
            }
            Collections.sort(polynomialTerms, new TermSorter());
}

public PnX(List<Term> termList) {
            if ( termList.size() == 0 ) {
                //  zero
                termList.add(new Term(0,0));
            }
            else {
                //  Remove zero terms if needed
                for ( int i = 0 ; i < termList.size() ; i++ ) {
                    if ( termList.get(i).coefficient == 0 ) {
                        termList.remove(i);
                    }
                }
            }
            if ( termList.size() == 0 ) {
                //  zero
                termList.add(new Term());
            }
            polynomialTerms = termList;
            Collections.sort(polynomialTerms, new TermSorter());
}
// end constructors

public PnX add(PnX poly)
{
            List<Term> termList = new ArrayList<>();
            int thisCount = polynomialTerms.size();
            int polyCount = poly.polynomialTerms.size();
            while(thisCount > 0 || polyCount > 0)
            {
                Term thisTerm = (thisCount == 0) ? null : polynomialTerms.get(thisCount-1);
                Term polyTerm = (polyCount == 0) ? null : poly.polynomialTerms.get(polyCount-1);
                if(thisTerm == null)
                {
                    termList.add(polyTerm.clone());
                    polyCount--;
                }
                else if(polyTerm == null)
                {
                    termList.add(thisTerm.clone());
                    thisCount--;
                }
                else if(thisTerm.degree() == polyTerm.degree())
                {
                    Term t = thisTerm.add(polyTerm);
                    if(t.coefficient != 0)
                    {
                        termList.add(t);
                    }
                    thisCount--;
                    polyCount--;
                }
                else if(thisTerm.degree() < polyTerm.degree())
                {
                    termList.add(thisTerm.clone());
                    thisCount--;
                }
                else
                {
                    termList.add(polyTerm.clone());
                    polyCount--;
                }
            }
            return new PnX(termList);
        }

public PnX add(Term t)
{
            List<Term> termList = new ArrayList<>();
            boolean added = false;
            for(int index = 0; index < polynomialTerms.size(); index++)
            {
                Term currentTerm = polynomialTerms.get(index);
                if(currentTerm.exponent == t.exponent)
                {
                    added = true;
                    if(currentTerm.coefficient + t.coefficient != 0)
                    {
                        termList.add(currentTerm.add(t));
                    }
                }
                else
                {
                    termList.add(currentTerm.clone());
                }
            }
            if(!added)
            {
                termList.add(t.clone());
            }
            return new PnX(termList);
        }

public PnX mul(Term t)
{
List<Term> termList = new ArrayList<>();
            for(int index = 0; index < polynomialTerms.size(); index++)
            {
                Term currentTerm = polynomialTerms.get(index);
                termList.add(currentTerm.clone().mul(t));
            }
            return new PnX(termList);
}

public PnX div(PnX poly)
{
            PnX q = new PnX();
            PnX r = this;
            int lcv = poly.leadingCoefficient();
            int dv = poly.degree();
            while(r.degree() >= poly.degree())
            {
                int lcr = r.leadingCoefficient();
                // divide coefficients and substrat exponents
                int s = lcr / lcv;
                Term t = new Term(s, r.degree() - dv);
                q = q.add(t);
                r = r.add(poly.mul(t.negate()));
            }
            return q;
        }

public int leadingCoefficient()
{
return polynomialTerms.get(0).coefficient;
}

public int degree()
{
return polynomialTerms.get(0).exponent;
}

// Build a raw int array having all coefficients.
public int[] raw()
{
int[] out = new int[this.degree()+1];
for(int i = 0; i < out.length; i++) out[i] = 0;
for(Term t : polynomialTerms)
{
	out[t.exponent] = t.coefficient;
}
return out;
}

// choosed structure to store cyclotomic polynomial.
private List<Term> polynomialTerms;

}

private static final class Term
{

// Constructors.
public Term()
{
this(0, 0);
}

public Term(int coef, int exp)
{
coefficient = coef;
exponent = exp;
}
// end constructors.

public Term add(Term t)
{
assert(this.exponent == t.exponent): "Term -> add: exponents must be equal.";
// For addition we just add coefficients for equal exponents
return new Term(this.coefficient+t.coefficient, this.exponent);
}

public Term mul(Term t)
{
	// For product we multiply coefficients and add exponents.
return new Term(this.coefficient*t.coefficient, this.exponent+t.exponent);
}


/*
* Self explanatory methods.
*/

public Term negate()
{
return new Term(-this.coefficient, this.exponent);
}

public int degree()
{
return this.exponent;
}

public Term clone()
{
return new Term(this.coefficient, this.exponent);
}

// attributes
int coefficient;
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
