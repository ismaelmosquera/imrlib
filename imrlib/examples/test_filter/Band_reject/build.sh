#
# Simple script to build TestBandRejectFilter
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../lib/imr-lib.jar TestBandRejectFilter.java
