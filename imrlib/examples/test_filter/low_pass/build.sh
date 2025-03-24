#
# Simple script to build TestLowPassFilter
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../lib/imr-lib.jar TestLowPassFilter.java
