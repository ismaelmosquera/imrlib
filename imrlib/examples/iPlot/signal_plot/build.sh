#
# Simple script to build iSignalPlot
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../lib/imr-lib.jar iSignalPlot.java
