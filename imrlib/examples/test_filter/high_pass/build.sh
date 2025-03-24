#
# Simple script to build TestHighPassFilter
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../lib/imr-lib.jar TestHighPassFilter.java
