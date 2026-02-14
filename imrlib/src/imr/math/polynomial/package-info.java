/**
* This package provides some classes to do operations with polynomials.
* <p>
* The <code>Polynomial</code> class has methods to perform operations like:
* <ul>
* <li>Addition.</li>
* <li>Substraction.</li>
* <li>Multiplication.</li>
* <li>Scale.</li>
* <li>Derivation.</li>
* <li>Integration.</li>
* <li>Roots finding.</li>
* </ul>
* <p>
* At this moment, finding roots is available for first, second, third and 4th degree polynomials, that is, to solve
* <ul>
* <li>Linear.</li>
* <li>Quadratic.</li>
* <li>Cubic.</li>
* <li>Quartic.</li>
* </ul>
* equations.
* <p>
* The <code>RuffiniRule</code> class implements the algorithm to divide a polynomial by a binomial of type (x - z)
* <p>
* In case that you want to do it by a binomial (x + z), just do it as follows: (x - (-z)).
* <p>
* As expected, the quotient will be of degree n-1, where n is the degree of the polynomial to be divided by some binomial.
* <p>
* After division, you can retrieve the quotient and remainder. You can test the result by performing
* <p>
* <code>P = quotient * divisor + remainder</code>
* <p>
* The <code>LagrangeInterpolator</code> class implements the Lagrange algorithm to do polynomial interpolation.
* <p>
* All methods work with any type of coefficient.
* <ul>
* <li>Integer.</li>
* <li>Real.</li>
* <li>Complex.</li>
* </ul>
* <p>
* The <code>CharacteristicPolynomial</code> class computes the characteristic polynomial for a NxN ( square ) matrix A. <p>
* The roots of such a polynomial are the eigen values of A. <p>
* Once the eigen values are known, you can compute each associated eigen vector solving N homogeneous linear systems. <p>
* To know more about how to use these classes, see the concrete API documentation and the examples.
* <p>
* @author Ismael Mosquera Rivera.
*
*/

package imr.math.polynomial;
