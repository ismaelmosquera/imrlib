#
# Simple script to run test Lagrange interpolator
#
cls
cd bin
java -classpath .:../../../../../lib/imr-lib.jar TestLagrange >& out.txt
echo Output saved to bin/out.txt

