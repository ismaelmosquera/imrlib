#
# Simple script to build TestBandPassFilter
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../lib/imr-lib.jar TestBandPassFilter.java
