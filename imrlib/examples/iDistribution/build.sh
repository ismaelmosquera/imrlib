#
# Simple script to build statistic distributions example
#
cls
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../lib/imr-lib.jar iDistribution.java 
