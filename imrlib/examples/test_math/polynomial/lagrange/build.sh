#
# Simple script to build test Lagrange interpolator example
#
cls
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../../lib/imr-lib.jar TestLagrange.java 
