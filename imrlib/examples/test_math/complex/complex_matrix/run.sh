#
# Simple script to run test complex matrix
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestComplexMatrix >& out.txt
echo Output saved to bin/out.txt

