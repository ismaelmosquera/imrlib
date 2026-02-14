#
# Simple script to run test characteristic polynomial
#
cls
cd bin
java -classpath .:../../../../../lib/imr-lib.jar TestCharacteristic >& out.txt
echo Output saved to bin/out.txt

