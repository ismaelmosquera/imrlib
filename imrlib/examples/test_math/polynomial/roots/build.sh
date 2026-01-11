#
# Simple script to build test roots finding example
#
cls
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../../lib/imr-lib.jar TestRoots.java 
