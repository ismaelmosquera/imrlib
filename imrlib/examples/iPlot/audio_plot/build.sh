#
# Simple script to build iAudioPlot
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../../lib/imr-lib.jar iAudioPlot.java
