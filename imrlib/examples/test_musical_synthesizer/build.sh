#
# Simple script to build test musical instrument example
#
cls
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../lib/imr-lib.jar TestMusicalInstrument.java 
