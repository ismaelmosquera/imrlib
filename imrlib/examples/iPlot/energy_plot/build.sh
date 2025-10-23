#
# Simple script to build iEnergyPlot
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../lib/imr-lib.jar iEnergyPlot.java
