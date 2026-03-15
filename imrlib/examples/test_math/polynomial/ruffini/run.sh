#
# Simple script to run test Ruffini rule
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestRuffini >& out.txt
echo Output saved to bin/out.txt

