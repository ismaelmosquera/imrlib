#
# Simple script to run test arithmetic
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestArithmetic >& out.txt
echo Output saved to bin/out.txt
