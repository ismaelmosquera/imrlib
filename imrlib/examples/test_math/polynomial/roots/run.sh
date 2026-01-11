#
# Simple script to run test roots finding
#
cls
cd bin
java -classpath .:../../../../../lib/imr-lib.jar TestRoots >& out.txt
echo Output saved to bin/out.txt
