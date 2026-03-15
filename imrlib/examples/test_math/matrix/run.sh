#
# Simple script to run test matrix
#
cls
cd bin
java -ea -classpath .:../../../../lib/imr-lib.jar TestMatrix >& out.txt
echo Output saved to bin/out.txt

