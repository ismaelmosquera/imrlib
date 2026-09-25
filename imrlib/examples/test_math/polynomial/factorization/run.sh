#
# Simple script to run test factorization polynomial
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestPolynomialFactorization >& out.txt
echo Output saved to bin/out.txt

