#
# Simple script to run test roots finding
#
cls
cd bin
java -ea -classpath .:../../../../../lib/imr-lib.jar TestRoots >& out.txt
echo Output saved to bin/out.txt
