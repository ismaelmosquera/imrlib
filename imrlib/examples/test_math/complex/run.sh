#
# Simple script to run test complex number
#
cls
cd bin
java -ea -classpath .:../../../../lib/imr-lib.jar TestComplex >& out.txt
echo Output saved to bin/out.txt

