#
# Simple script to build imr-lib.jar
#
rm -r lib
mkdir lib
cp -r src/imr lib
cd lib
javac imr/matrix/*.java imr/media/*.java imr/media/audio/*.java imr/media/midi/*.java imr/util/*.java
rm imr/matrix/*.java imr/media/*.java imr/media/audio/*.java imr/media/midi/*.java imr/util/*.java
jar cvf imr-lib.jar imr
rm -r imr
cd ..
