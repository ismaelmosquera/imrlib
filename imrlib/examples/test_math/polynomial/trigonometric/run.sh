#
# Simple script to run test trigonometric interpolator
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestTrigonometric >& out.txt
echo Output saved to bin/out.txt

