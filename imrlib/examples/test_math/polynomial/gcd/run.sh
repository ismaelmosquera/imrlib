#
# Simple script to run test gcd
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestPolynomialGCD >& out.txt
echo Output saved to bin/out.txt
