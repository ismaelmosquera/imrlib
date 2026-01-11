#
# Simple script to run test Ruffini rule
#
cls
cd bin
java -classpath .:../../../../../lib/imr-lib.jar TestRuffini >& out.txt
echo Output saved to bin/out.txt

