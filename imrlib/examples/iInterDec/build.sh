#
# Simple script to build interpolator / decimator example
#
cls
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../lib/imr-lib.jar iInterDec.java 
