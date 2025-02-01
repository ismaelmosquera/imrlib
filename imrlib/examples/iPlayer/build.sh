#
# Simple script to build iPlayer
#
echo deleting old files...
rm bin/*.class
echo compiling...
javac -d bin -classpath .:../../lib/imr-lib.jar iPlayer.java
