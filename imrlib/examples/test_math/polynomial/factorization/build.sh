#
# Simple script to build test factorization polynomial example
#
cls
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../../lib/imr-lib.jar TestPolynomialFactorization.java 
