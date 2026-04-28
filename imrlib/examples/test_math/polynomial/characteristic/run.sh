#
# Simple script to run test characteristic polynomial
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestCharacteristic >& out.txt
echo Output saved to bin/out.txt

